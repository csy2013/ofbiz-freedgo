/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *******************************************************************************/
package org.ofbiz.entity.cache.redis.util ;


import javolution.util.FastList;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generalized caching utility. Provides a number of caching features:
 * <ul>
 *   <li>Limited or unlimited element capacity
 *   <li>If limited, removes elements with the LRU (Least Recently Used) algorithm
 *   <li>Keeps track of when each element was loaded into the cache
 *   <li>Using the expireTime can report whether a given element has expired
 *   <li>Counts misses and hits
 * </ul>
 *
 */
@SuppressWarnings("serial")
public class JedisUtilCache<K, V> implements Serializable  {

    private JedisUtilCache(){}

    private static final JedisUtilCache single = new JedisUtilCache();
    //静态工厂方法
    public static JedisUtilCache getInstance() {
        return single;
    }

    public static final String module = JedisUtilCache.class.getName();

    private static final Set<String> utilCacheTable = new HashSet<String>();

    /** An index number appended to utilCacheTable names when there are conflicts. */
    private final static ConcurrentHashMap<String, AtomicInteger> defaultIndices = new ConcurrentHashMap<String, AtomicInteger>();
    private static JedisPoolConfig poolConfig = null;
    // 保存不同的数据库连接
    private ConcurrentHashMap<String, RedisCachePool> redisPoolMap = new ConcurrentHashMap<String, RedisCachePool>();



    public ConcurrentHashMap<String, RedisCachePool> getRedisPoolMap() {
        if (redisPoolMap.size() < 1) {
            initConfig();
            initPoolMap();
        }
        return redisPoolMap;
    }
    /**
     * @Description:共享的poolconfig
     * @return:void
     */
    private void initConfig() {
        poolConfig = new JedisPoolConfig();
        Properties props= UtilProperties.getProperties("redis.properties");
        poolConfig.setTestOnBorrow(props.getProperty("testwhileidle").equals("true"));
        poolConfig.setTestWhileIdle(props.getProperty("testonborrow").equals("true"));
        poolConfig.setMaxIdle(Integer.parseInt(props.getProperty("maxidle")));
        poolConfig.setMaxTotal(Integer.parseInt(props.getProperty("maxtotal")));
        poolConfig.setMinIdle(Integer.parseInt(props.getProperty("minidle")));
        poolConfig.setMaxWaitMillis(Integer.parseInt(props.getProperty("maxwaitmillis")));
    }

    private void initPoolMap() {
        try {
            Properties props= UtilProperties.getProperties("redis.properties");
            String redisdbtype = props.getProperty("redisdbtype");
            String redisdbnumber = props.getProperty("redisdbnumber");
            String host = props.getProperty("host");
            String port = props.getProperty("port");
            String timeout = props.getProperty("timeout");
            String passwords = props.getProperty("passwords");
            if (null != redisdbtype && null != redisdbnumber) {
                String[] dbs = redisdbtype.split(",");
                String[] numbers = redisdbnumber.split(",");
                for (int i = 0; i < dbs.length; i++) {
                    // 得到redis连接池对象
                    JedisPool jedisPool = new JedisPool(poolConfig, host, Integer.parseInt(port), Integer.parseInt(timeout));
                    // 存放不同redis数据库
                    redisPoolMap.put(dbs[i], new RedisCachePool(Integer.parseInt(numbers[i]), jedisPool));
                }
            }
        } catch (Exception e) {
            // log.error("redisCacheManager初始化失败！" + e.getLocalizedMessage());
        }
    }

    /**
     * @Description: 得到jedis连接
     * @param dbtypeName
     * @return:Jedis
     */
    public Jedis getResource(RedisDataBaseType dbtypeName) {
        Jedis jedisResource = null;

        RedisCachePool pool = redisPoolMap.get(dbtypeName.toString());
        if (pool != null) {
            jedisResource = pool.getResource();
        }
        return jedisResource;
    }

    /**
     * @Description: 返回连接池
     * @param dbtypeName
     * @param jedis
     * @return:void
     */
    public void returnResource(RedisDataBaseType dbtypeName, Jedis jedis) {
        RedisCachePool pool = redisPoolMap.get(dbtypeName.toString());
        if (pool != null)
            pool.returnResource(jedis);
    }

    private static String getNextDefaultIndex(String cacheName) {
        AtomicInteger curInd = defaultIndices.get(cacheName);
        if (curInd == null) {
            defaultIndices.putIfAbsent(cacheName, new AtomicInteger(0));
            curInd = defaultIndices.get(cacheName);
        }
        int i = curInd.getAndIncrement();
        return i == 0 ? "" : Integer.toString(i);
    }




    public static void clearCache(String cacheName) {

    }

    public static String findStringCache(String cacheName) {
        RedisCachePool pool = null;
        Jedis jedis = null;
        String value = "";
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            value = jedis.get(cacheName);
            utilCacheTable.add(cacheName);
        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            pool.returnResource(jedis);
        }
        return value;
    }


    public static List<GenericValue> findEntityListCache(String cacheName,String keyName,Delegator delegator){
        RedisCachePool pool = null;
        Jedis jedis = null;
        List<GenericValue>  values = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            String varStr = jedis.hget(cacheName,keyName);
            values = (List<GenericValue>) JedisXmlSerializer.deserialize(varStr,delegator);
            utilCacheTable.add(cacheName);
        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            assert pool != null;
            pool.returnResource(jedis);
        }
        return values;
    }

    /**
     *查找某个key 对应的entity
     * @param cacheName
     * @param primaryId
     * @param delegator
     * @return
     */
    public static GenericValue findEntityCache(String cacheName,String primaryId,Delegator delegator){
        RedisCachePool pool = null;
        Jedis jedis = null;
        String value = "";
        GenericValue genericValue = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            value = jedis.hget(cacheName, primaryId);
            if(value!=null) {
                if (value.equals("[null-entity-value]")) {
                    genericValue = GenericValue.NULL_VALUE;
                } else {
                    genericValue = (GenericValue) JedisXmlSerializer.deserialize(value, delegator);
                }
            }
        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            pool.returnResource(jedis);
        }
        return genericValue;
    }

    /**
     * 某个entity的缓存
     * @param cacheName
     * @param delegator
     * @return
     */
    public static List<GenericValue> findEntityAllCache(String cacheName,Delegator delegator){
        RedisCachePool pool = null;
        Jedis jedis = null;

        List<GenericValue> values = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            List hvals = jedis.hvals(cacheName);
            if(UtilValidate.isNotEmpty(hvals)){
                values = FastList.newInstance();
                for (int i = 0; i < hvals.size(); i++) {
                    String s = (String) hvals.get(i);
                    values.add((GenericValue) JedisXmlSerializer.deserialize(s, delegator));
                }
                utilCacheTable.add(cacheName);
            }

        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            pool.returnResource(jedis);
        }
        return values;
    }

    /**
     * 获取entity-list的所有缓存
     * @param cacheName
     * @return
     */
    public static Map<String,String> findEntityListAllCache(String cacheName,Delegator delegator) {
        RedisCachePool pool = null;
        Jedis jedis = null;

        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            Map<String,String> hvals = jedis.hgetAll(cacheName);
            utilCacheTable.add(cacheName);
            return hvals;

        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            pool.returnResource(jedis);
        }
        return null;

    }

    /**
     * 在condition GenericValues 中查找PK对应的GenericValue
     * @param cacheName
     * @param conditionStr
     * @param key
     * @param delegator
     */
    public static GenericValue findConditionEntityByKey(String cacheName, String conditionStr, String key, Delegator delegator) {
        RedisCachePool pool = null;
        Jedis jedis = null;

        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            String hvals = jedis.hget(cacheName,conditionStr);
            if(UtilValidate.isNotEmpty(hvals)){
                List<GenericValue>  valueList  = (List<GenericValue>) JedisXmlSerializer.deserialize(hvals, delegator);
                if(UtilValidate.isNotEmpty(valueList)){
                    for (int i = 0; i < valueList.size(); i++) {
                        GenericValue entity = valueList.get(i);
                        if(entity.getPrimaryKey().toString().equals(key)){

                            return entity;
                        }
                    }
                    utilCacheTable.add(cacheName);
                }

            }

        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            pool.returnResource(jedis);
        }
        return null;
    }

    /**
     * redis存储entity
     * @param cacheName
     * @param primaryId
     * @param entity
     * @return
     */
    public static GenericValue putEntityCache(String cacheName,String primaryId, GenericValue entity) {

        RedisCachePool pool = null;
        Jedis jedis = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            Transaction transation = jedis.multi();
            transation.hset(cacheName,primaryId,JedisXmlSerializer.serialize(entity));
            transation.exec();
            utilCacheTable.add(cacheName);
        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            assert pool != null;
            pool.returnResource(jedis);
        }
        return entity;
    }

    /**
     * redis存储 [null-entity-value]
     * @param cacheName
     * @param primaryId
     * @param value
     * @return
     */
    public static GenericValue putNullEntityCache(String cacheName,String primaryId, String value) {
        RedisCachePool pool = null;
        Jedis jedis = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            Transaction transation = jedis.multi();
            transation.hset(cacheName,primaryId,value);
            transation.exec();
        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            assert pool != null;
            pool.returnResource(jedis);
        }
        return GenericValue.NULL_VALUE;
    }

    /**
     * redis存储entityList
     * @param cacheName
     * @param condition
     * @param entities
     * @return
     */
    public static List<GenericValue>  putEntityListCache(String cacheName,String condition, List<GenericValue> entities) {
        RedisCachePool pool = null;
        Jedis jedis = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            Transaction transation = jedis.multi();
            transation.hset(cacheName, condition, JedisXmlSerializer.serialize(entities));
            transation.exec();
            utilCacheTable.add(cacheName);
        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            assert pool != null;
            pool.returnResource(jedis);
        }
        return entities;
    }

    /**
     * 删除entity 对应的缓存
     * @param cacheName
     * @param pk
     * @param delegator
     * @return
     */
    public static GenericValue removeEntity(String cacheName,String pk,Delegator delegator) {
        RedisCachePool pool = null;
        Jedis jedis = null;
        GenericValue value = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            String  xmlValue = jedis.hget(cacheName, pk);
            value = (GenericValue) JedisXmlSerializer.deserialize(xmlValue,delegator);
            Transaction transation = jedis.multi();
            transation.hdel(cacheName, pk);
            transation.exec();
        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            assert pool != null;
            pool.returnResource(jedis);
        }
        return value;
    }

    /**
     * 删除entityList 对应的缓存
     * @param cacheName
     * @param condition
     * @param delegator
     * @return List<GenericValue>
     */
    public static List<GenericValue> removeEntityList(String cacheName,String condition,Delegator delegator) {
        RedisCachePool pool = null;
        Jedis jedis = null;
        List<GenericValue> values = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();

            String  xmlValue = jedis.hget(cacheName, condition);
            values = (List<GenericValue> ) JedisXmlSerializer.deserialize(xmlValue,delegator);
            jedis.hdel(cacheName,condition);

        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            assert pool != null;
            pool.returnResource(jedis);
        }
        return values;
    }

    /**
     * 删除entityList 对应的缓存
     * @param cacheName
     * @return List<GenericValue>
     */
    public static List<GenericValue> removeAllEntityList(String cacheName) {
        RedisCachePool pool = null;
        Jedis jedis = null;
        List<GenericValue> values = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            Transaction transation = jedis.multi();
            transation.del(cacheName);
//            transation.hdel(cacheName);
            transation.exec();
        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            assert pool != null;
            pool.returnResource(jedis);
        }
        return values;
    }

    /**
     * 删除entity对应的缓存
     * @param cacheName

     */
    public static void removeEntityAll(String cacheName) {
        RedisCachePool pool = null;
        Jedis jedis = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            Transaction transation = jedis.multi();
            transation.del(cacheName);
            transation.exec();
        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            assert pool != null;
            pool.returnResource(jedis);
        }

    }


    public static <V, K> V removeConditionByKey(String cacheName, String conditionStr, K key, Delegator delegator) {
        RedisCachePool pool = null;
        Jedis jedis = null;
        List<GenericValue> values = null;
        GenericValue retEntity = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            Transaction transation = jedis.multi();
            String  xmlValue = jedis.hget(cacheName, conditionStr);
            values = (List<GenericValue> ) JedisXmlSerializer.deserialize(xmlValue,delegator);
            List retVal = FastList.newInstance();
            if(UtilValidate.isNotEmpty(values)){
                for (int i = 0; i < values.size(); i++) {
                    GenericValue entity = values.get(i);
                    if(!entity.getPrimaryKey().toString().equals(key)){
                         retVal.add(entity);
                    }else{
                        retEntity = entity;
                    }
                }
            }
            transation.hset(cacheName, conditionStr,JedisXmlSerializer.serialize(retVal));
            transation.exec();
        } catch (Exception e) {
            Debug.logImportant("error : " + cacheName, module);
        }
        finally {
            assert pool != null;
            pool.returnResource(jedis);
        }
        return (V)retEntity;
    }

    /** Removes all elements from this cache */
    public static void clearAllCaches() {
        if(UtilValidate.isNotEmpty(utilCacheTable)){
        RedisCachePool pool = null;
        Jedis jedis = null;
        try {
            pool = (RedisCachePool) JedisUtilCache.getInstance().getRedisPoolMap().get(RedisDataBaseType.defaultType.toString());
            jedis = pool.getResource();
            Transaction transation = jedis.multi();
            for(String cache:utilCacheTable){
                transation.del(cache);
            }
            transation.exec();
        } catch (Exception e) {
            Debug.logImportant("error : " + e.getMessage(), module);
        }
        finally {
            assert pool != null;
            pool.returnResource(jedis);
        }
        // We make a copy since clear may take time


        }

    }

}

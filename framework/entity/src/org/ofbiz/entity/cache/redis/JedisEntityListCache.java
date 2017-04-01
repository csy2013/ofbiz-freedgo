package org.ofbiz.entity.cache.redis;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.cache.redis.util.JedisUtilCache;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.model.ModelEntity;
import org.ofbiz.entity.util.EntityUtil;

import java.util.List;

/**
 * Created by changsy on 16/3/29.
 * redis 内部 key-List
 * key:entitycache.entity-list.default.UserPreference
 * value: hashmap<key:查询条件，value:结果>
 */
public class JedisEntityListCache extends JedisAbstractEntityConditionCache<Object, List<GenericValue>> {

    public static final String module = JedisEntityListCache.class.getName();

    public JedisEntityListCache(String delegatorName) {
        super(delegatorName, "entity-list");
    }

    public List<GenericValue> get(String entityName, EntityCondition condition) {
        String conditionStr =  getFrozenConditionKey(condition)!=null?getFrozenConditionKey(condition).toString():"{null}";
        return JedisUtilCache.findEntityListCache(getCacheName(entityName),conditionStr,getDelegator());
//        return  get(entityName, condition, null);
    }

    public List<GenericValue> get(String entityName, EntityCondition condition, List<String> orderBy) {
        List<GenericValue> conditionCache  = get(entityName,condition);
        if (conditionCache == null) return null;
        if(UtilValidate.isNotEmpty(orderBy)) {
            return EntityUtil.orderBy(conditionCache, orderBy);
        }else{
            return conditionCache;
        }
    }

    public void put(String entityName, EntityCondition condition, List<GenericValue> entities) {
        this.put(entityName, condition, null, entities);
    }

    public List<GenericValue> put(String entityName, EntityCondition condition, List<String> orderBy, List<GenericValue> entities) {
        ModelEntity entity = this.getDelegator().getModelEntity(entityName);
        if (entity.getNeverCache()) {
            Debug.logWarning("Tried to put a value of the " + entityName + " entity in the cache but this entity has never-cache set to true, not caching.", module);
            return null;
        }
        for (GenericValue memberValue : entities) {
            memberValue.setImmutable();
        }
//        Map<Object, List<GenericValue>> conditionCache = getOrCreateConditionCache(entityName, getFrozenConditionKey(condition));
        String conditionStr =  getFrozenConditionKey(condition)!=null?getFrozenConditionKey(condition).toString():"{null}";
        return JedisUtilCache.putEntityListCache(getCacheName(entityName), conditionStr, entities);
    }

    public List<GenericValue> remove(String entityName, EntityCondition condition, List<String> orderBy) {
        //orderby 没有缓存
        return get(entityName,condition,orderBy);
//        return super.remove(entityName, condition,orderBy);
    }



}

package org.ofbiz.entity.cache.redis;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericPK;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.cache.redis.util.JedisUtilCache;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.model.ModelEntity;

import java.util.Iterator;
import java.util.List;

/**
 * Created by changsy on 16/3/29.
 * redis 定义成 key -map
 * key:entitycache.entity.default.entityName
 * map<key:primaryId,value:(entity:ofbiz XmlSerializer string)
 */
public class JedisEntityCache extends  JedisAbstractCache<GenericPK, GenericValue> {

    public static final String module = JedisEntityCache.class.getName();
    public JedisEntityCache(String delegatorName) {
        super(delegatorName, "entity");
    }

    public GenericValue get(GenericPK pk) {
        return JedisUtilCache.findEntityCache(getCacheName(pk.getEntityName()), pk.toString(),getDelegator());
    }

    public GenericValue put(GenericValue entity) {
        if (entity == null) return null;
        return put(entity.getPrimaryKey(), entity);
    }

    public GenericValue put(GenericPK pk, GenericValue entity) {
        if (pk.getModelEntity().getNeverCache()) {
            Debug.logWarning("Tried to put a value of the " + pk.getEntityName() + " entity in the BY PRIMARY KEY cache but this entity has never-cache set to true, not caching.", module);
            return null;
        }
        if (entity == null) {
            entity = GenericValue.NULL_VALUE;
        } else {
            // before going into the cache, make this value immutable
            entity.setImmutable();
        }
        GenericValue value = get(pk);
        if(UtilValidate.isNotEmpty(value)){
            remove(pk);
        }
        String key = getCacheName(entity.getEntityName());
        String primaryId = pk.toString();
        if(entity.getEntityName().equals("[null-entity-value]")){
            return JedisUtilCache.putNullEntityCache(key, primaryId, "[null-entity-value]");
        }else {
            return JedisUtilCache.putEntityCache(key, primaryId, entity);
        }
    }

    public void remove(String entityName, EntityCondition condition) {
        String key = getCacheName(entityName);
        List<GenericValue> cacheLineKeys = JedisUtilCache.findEntityAllCache(key, getDelegator());
        if (cacheLineKeys == null) return;
        for (GenericValue entity: cacheLineKeys) {
            if (entity == null) continue;
            if (condition.entityMatches(entity)) JedisUtilCache.removeEntity(getCacheName(entity.getEntityName()),entity.getPrimaryKey().toString(),getDelegator());
        }
    }

    public GenericValue remove(GenericValue entity) {
         remove(entity.getPrimaryKey());
         return entity;
    }

    public GenericValue remove(GenericPK pk) {
         GenericValue  entityCache = JedisUtilCache.findEntityCache(pk.getEntityName(),pk.toString(),getDelegator());
//        if (Debug.verboseOn()) Debug.logVerbose("Removing from EntityCache with PK [" + pk + "], will remove from this cache: " + (entityCache == null ? "[No cache found to remove from]" : entityCache.getName()), module);
        if (entityCache == null) return null;
        GenericValue retVal = JedisUtilCache.removeEntity(pk.getEntityName(),pk.toString(),getDelegator());
        ModelEntity model = pk.getModelEntity();
        if (model != null) {
            Iterator<String> it = model.getViewConvertorsIterator();
            while (it.hasNext()) {
                String targetEntityName = it.next();
                JedisUtilCache.clearCache(getCacheName(targetEntityName));
            }
        }
        if (Debug.verboseOn()) Debug.logVerbose("Removing from EntityCache with PK [" + pk + "], found this in the cache: " + retVal, module);
        return retVal;
    }
}
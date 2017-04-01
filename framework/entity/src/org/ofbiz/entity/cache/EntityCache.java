package org.ofbiz.entity.cache;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.cache.UtilCache;
import org.ofbiz.entity.GenericPK;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.model.ModelEntity;

import java.util.Iterator;

public class EntityCache extends AbstractCache<GenericPK, GenericValue> {
    public static final String module = EntityCache.class.getName();

    public EntityCache(String delegatorName) {
        super(delegatorName, "entity");
    }

    public GenericValue get(GenericPK pk) {
        UtilCache<GenericPK, GenericValue> entityCache = getCache(pk.getEntityName());
        if (entityCache == null) return null;
        return entityCache.get(pk);
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
        UtilCache<GenericPK, GenericValue> entityCache = getOrCreateCache(pk.getEntityName());
        return entityCache.put(pk, entity);
    }

    public void remove(String entityName, EntityCondition condition) {
        UtilCache<GenericPK, GenericValue> entityCache = getCache(entityName);
        if (entityCache == null) return;
        for (GenericPK pk: entityCache.getCacheLineKeys()) {
            GenericValue entity = entityCache.get(pk);
            if (entity == null) continue;
            if (condition.entityMatches(entity)) entityCache.remove(pk);
        }
    }

    public GenericValue remove(GenericValue entity) {
        return remove(entity.getPrimaryKey());
    }

    public GenericValue remove(GenericPK pk) {
        UtilCache<GenericPK, GenericValue> entityCache = getCache(pk.getEntityName());
        if (Debug.verboseOn()) Debug.logVerbose("Removing from EntityCache with PK [" + pk + "], will remove from this cache: " + (entityCache == null ? "[No cache found to remove from]" : entityCache.getName()), module);
        if (entityCache == null) return null;
        GenericValue retVal = entityCache.remove(pk);
        ModelEntity model = pk.getModelEntity();
        if (model != null) {
            Iterator<String> it = model.getViewConvertorsIterator();
            while (it.hasNext()) {
                String targetEntityName = it.next();
                UtilCache.clearCache(getCacheName(targetEntityName));
            }
        }
        if (Debug.verboseOn()) Debug.logVerbose("Removing from EntityCache with PK [" + pk + "], found this in the cache: " + retVal, module);
        return retVal;
    }
}

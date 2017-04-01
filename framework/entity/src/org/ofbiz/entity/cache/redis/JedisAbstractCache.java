package org.ofbiz.entity.cache.redis;

import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.DelegatorFactory;
import org.ofbiz.entity.cache.redis.util.JedisUtilCache;

/**
 * Created by changsy on 16/3/29.
 */
public abstract class JedisAbstractCache <K, V> {

    protected String delegatorName, id;

    protected JedisAbstractCache(String delegatorName, String id) {
        this.delegatorName = delegatorName;
        this.id = id;
    }

    public Delegator getDelegator() {
        return DelegatorFactory.getDelegator(this.delegatorName);
    }

    public void remove(String entityName) {
        JedisUtilCache.removeEntityAll(getCacheName(entityName));
    }

    public void clear() {
//        UtilCache.clearCachesThatStartWith(getCacheNamePrefix());
    }

    public String getCacheNamePrefix() {
        return "entitycache." + id + "." + delegatorName + ".";
    }

    public String[] getCacheNamePrefixes() {
        return new String[]{
                "entitycache." + id + ".${delegator-name}.",
                "entitycache." + id + "." + delegatorName + "."
        };
    }

    public String getCacheName(String entityName) {
        return getCacheNamePrefix() + entityName;
    }

    public String[] getCacheNames(String entityName) {
        String[] prefixes = getCacheNamePrefixes();
        String[] names = new String[prefixes.length * 2];
        for (int i = 0; i < prefixes.length; i++) {
            names[i] = prefixes[i] + "${entity-name}";
        }
        for (int i = prefixes.length, j = 0; j < prefixes.length; i++, j++) {
            names[i] = prefixes[j] + entityName;
        }
        return names;
    }


}

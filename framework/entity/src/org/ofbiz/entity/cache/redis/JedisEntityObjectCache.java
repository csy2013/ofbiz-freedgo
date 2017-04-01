package org.ofbiz.entity.cache.redis;

import org.ofbiz.entity.condition.EntityCondition;

/**
 * Created by changsy on 16/3/29.
 */
public class JedisEntityObjectCache  extends JedisAbstractEntityConditionCache<String, Object> {

    public static final String module = JedisEntityObjectCache.class.getName();

    public JedisEntityObjectCache(String delegatorName) {
        super(delegatorName, "object-list");
    }

    @Override
    public Object get(String entityName, EntityCondition condition, String name) {
        return super.get(entityName, condition, name);
    }

    @Override
    public Object put(String entityName, EntityCondition condition, String name, Object value) {
        return super.put(entityName, getFrozenConditionKey(condition), name, value);
    }

    @Override
    public Object remove(String entityName, EntityCondition condition, String name) {
        return super.remove(entityName, condition, name);
    }

}

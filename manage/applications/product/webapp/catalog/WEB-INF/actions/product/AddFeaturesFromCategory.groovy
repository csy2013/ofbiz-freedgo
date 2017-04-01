import org.ofbiz.entity.condition.EntityCondition

context.productFeatureAndAppls = delegator.findList('ProductFeatureAndAppl',
        EntityCondition.makeCondition([productId : productId]), null,
        ['sequenceNum', 'productFeatureApplTypeId', 'productFeatureTypeId', 'description'], null, false)

context.productFeatureCategories = delegator.findList('ProductFeatureCategory', null, null, ['description'], null, false)

context.productFeatureApplTypes = delegator.findList('ProductFeatureApplType', null, null, ['description'], null, true)

context.productFeatureGroups = delegator.findList('ProductFeatureGroup', null, null, ['description'], null, false)

context.productFeatureTypes = delegator.findList('ProductFeatureType', null, null, ['description'], null, true)

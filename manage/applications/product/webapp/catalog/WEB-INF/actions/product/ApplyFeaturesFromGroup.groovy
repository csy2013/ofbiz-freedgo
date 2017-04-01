import org.ofbiz.entity.GenericEntity

productFeatureGroupId = parameters.get("productFeatureGroupId")
if (productFeatureGroupId) {
    productFeatureGroup = delegator.findOne("ProductFeatureGroup", [productFeatureGroupId : productFeatureGroupId], false)
    productFeatures = []
    productFeatureGroupAppls = productFeatureGroup.getRelated("ProductFeatureGroupAppl", ['sequenceNum'])
    for (pFGAi = productFeatureGroupAppls.iterator(); pFGAi;) {
        productFeatureGroupAppl = (GenericEntity)pFGAi.next()
        productFeature = (GenericEntity)productFeatureGroupAppl.getRelatedOne("ProductFeature")
        productFeature.set("defaultSequenceNum", productFeatureGroupAppl.getLong("sequenceNum"))
        productFeatures.add(productFeature)
    }
    context.productFeatureGroup = productFeatureGroup
    context.productFeatures = productFeatures

    // this will not break out the product features by view size
    context.listSize = productFeatures.size()
    context.highIndex = productFeatures.size()
}

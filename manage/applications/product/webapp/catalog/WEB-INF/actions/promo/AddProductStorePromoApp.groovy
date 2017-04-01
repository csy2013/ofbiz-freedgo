import org.ofbiz.entity.GenericValue

productPromoId = request.getParameter("productPromoId")
if (!productPromoId) {
    productPromoId = request.getAttribute("productPromoId")
}
promoAppls = delegator.findByAnd("ProductStorePromoAppl",[productPromoId:productPromoId])
returnList = []
productStores = delegator.findByAnd("ProductStore", null)
if(promoAppls){
    for (int i = 0; i < productStores.size(); i++) {
        GenericValue productStore = productStores.get(i)
        String productStoreId = productStore.productStoreId
        boolean hasStore = false
        for (int j = 0; j < promoAppls.size(); j++) {
            GenericValue promoAppl = promoAppls.get(j)
            if(promoAppl.productStoreId.equals(productStoreId)){
                hasStore = true
            }
        }
        if(!hasStore){
            returnList.add(productStore)
        }
    }
    context.productStores = returnList

} else{
    context.productStores = productStores
}
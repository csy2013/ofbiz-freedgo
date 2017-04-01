
gTreeTable = []
// Get the Catalogs
productCategories = delegator.findByAnd("ProductCategory", [isTop:'Y'])
if (productCategories) {
    productCategories.each { productCategory ->
        gProdCatalogMap = [:]
        gProdCatalogMap.id = productCategory.getString("productCategoryId")
        gProdCatalogMap.name = productCategory.getString("categoryName")
        gProdCatalogMap.parent = productCategory.getString("isTop")
        gProdCatalogMap.type = "category"
        gProdCatalogMap.level = "1"
        gProdCatalogMap.isCatalog = "false"
        gProdCatalogMap.isCategoryType = "true"
        gTreeTable.add(gProdCatalogMap)
    }
}

context.gTreeTable = gTreeTable


context.prodCatalogCategoryTypes = delegator.findList("ProdCatalogCategoryType", null, null, null, null, false)

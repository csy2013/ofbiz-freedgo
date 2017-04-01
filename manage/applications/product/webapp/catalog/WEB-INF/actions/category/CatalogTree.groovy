import org.ofbiz.entity.util.EntityUtil

// Put the result of CategoryWorker.getRelatedCategories into the separateRootType function as attribute.
// The separateRootType function will return the list of category of given catalog.
// PLEASE NOTE : The structure of the list of separateRootType function is according to the JSON_DATA plugin of the jsTree.

List separateRootType(roots) {
    if(roots) {
        prodRootTypeTree = []

        roots.each { root ->
            prodCateMap = [:]
            gProdCateMap = [:]
            productCategory = root.getRelatedOne("ProductCategory")
            prodCateMap.productCategoryId = productCategory.getString("productCategoryId")
            prodCateMap.categoryName = productCategory.getString("categoryName")
            prodCateMap.isCatalog = false
            prodCateMap.isCategoryType = true

            prodRootTypeTree.add(prodCateMap)
        }
        return prodRootTypeTree
    }
}

void separateProdCateRootType(roots,gProdCatalogMap) {
    if(roots) {
        roots.each { root ->
            gProdCatalogMap.id = productCategory.getString("productCategoryId")
            gProdCatalogMap.name = productCategory.getString("categoryName")
            gProdCatalogMap.isCatalog = "false"
            gProdCatalogMap.isCategoryType = "true"
            gProdCatalogMap.level = "2"
            gProdCatalogMap.type = "category"
        }

    }
}

completedTree =  []
gTreeTable = []
// Get the Catalogs
prodCatalogs = delegator.findList("ProdCatalog", null, null, null, null, false)
if (prodCatalogs) {
    prodCatalogs.each { prodCatalog ->
        prodCatalogMap = [:]
        gProdCatalogMap = [:]
        prodCatalogMap.productCategoryId = prodCatalog.getString("prodCatalogId")
        prodCatalogMap.categoryName = prodCatalog.getString("catalogName")
        prodCatalogMap.isCatalog = true
        prodCatalogMap.isCategoryType = false

        gProdCatalogMap.id = prodCatalog.getString("prodCatalogId")
        gProdCatalogMap.name = prodCatalog.getString("catalogName")
        gProdCatalogMap.parent = "0"
        gProdCatalogMap.type = "catalog"
        gProdCatalogMap.level = "1"
        gProdCatalogMap.isCatalog = "true"
        gProdCatalogMap.isCategoryType = "false"

        prodCatalogCategories = EntityUtil.filterByDate(delegator.findByAnd("ProdCatalogCategory", ["prodCatalogId" : prodCatalog.prodCatalogId]))
        if (prodCatalogCategories) {
            prodCatalogMap.child = separateRootType(prodCatalogCategories)
            separateProdCateRootType(prodCatalogCategories,gProdCatalogMap)
        }
        completedTree.add(prodCatalogMap)
        gTreeTable.add(gProdCatalogMap)
    }
}
println "completedTree = $completedTree"
// The complete tree list for the category tree
context.completedTree = completedTree
context.gTreeTable = gTreeTable
stillInCatalogManager = true
productCategoryId = null
prodCatalogId = null
showProductCategoryId = null

// Reset tree condition check. Are we still in the Catalog Manager ?. If not , then reset the tree.
if ((parameters.productCategoryId != null) || (parameters.showProductCategoryId != null)) {
    stillInCatalogManager = false
    productCategoryId = parameters.productCategoryId
    showProductCategoryId = parameters.showProductCategoryId
} else if (parameters.prodCatalogId != null) {
    stillInCatalogManager = false
    prodCatalogId = parameters.prodCatalogId
}
context.prodCatalogCategoryTypes = delegator.findList("ProdCatalogCategoryType", null, null, null, null, false)
context.stillInCatalogManager = stillInCatalogManager
context.productCategoryId = productCategoryId
context.prodCatalogId = prodCatalogId
context.showProductCategoryId = showProductCategoryId

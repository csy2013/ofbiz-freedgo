import org.ofbiz.entity.condition.EntityCondition
import org.ofbiz.entity.condition.EntityOperator
import org.ofbiz.product.catalog.CatalogWorker
import org.ofbiz.product.feature.ParametricSearch
import org.ofbiz.product.product.ProductSearchSession

searchCategoryId = parameters.SEARCH_CATEGORY_ID
if (!searchCategoryId) {
    currentCatalogId = CatalogWorker.getCurrentCatalogId(request)
    searchCategoryId = CatalogWorker.getCatalogSearchCategoryId(request, currentCatalogId)
}
searchCategory = delegator.findByPrimaryKey("ProductCategory", [productCategoryId : searchCategoryId])

productFeaturesByTypeMap = ParametricSearch.makeCategoryFeatureLists(searchCategoryId, delegator)
productFeatureTypeIdsOrdered = new TreeSet(productFeaturesByTypeMap.keySet()) as List
if(productFeatureTypeIdsOrdered) {
    context.productFeatureTypes = delegator.findList("ProductFeatureType", EntityCondition.makeCondition("productFeatureTypeId", EntityOperator.IN, productFeatureTypeIdsOrdered), null, null, null, false)
}

searchOperator = parameters.SEARCH_OPERATOR
if (!"AND".equals(searchOperator) && !"OR".equals(searchOperator)) {
  searchOperator = "OR"
}

searchConstraintStrings = ProductSearchSession.searchGetConstraintStrings(false, session, delegator)
searchSortOrderString = ProductSearchSession.searchGetSortOrderString(false, request)

context.searchCategoryId = searchCategoryId
context.searchCategory = searchCategory
context.productFeaturesByTypeMap = productFeaturesByTypeMap
context.productFeatureTypeIdsOrdered = productFeatureTypeIdsOrdered
context.searchOperator = searchOperator
context.searchConstraintStrings = searchConstraintStrings
context.searchSortOrderString = searchSortOrderString

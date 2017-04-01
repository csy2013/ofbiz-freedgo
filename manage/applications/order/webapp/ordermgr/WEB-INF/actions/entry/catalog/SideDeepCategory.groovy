import javolution.util.FastMap
import org.ofbiz.product.catalog.CatalogWorker
import org.ofbiz.product.category.CategoryWorker

CategoryWorker.getRelatedCategories(request, "topLevelList", CatalogWorker.getCatalogTopCategoryId(request, CatalogWorker.getCurrentCatalogId(request)), true)
curCategoryId = parameters.category_id ?: parameters.CATEGORY_ID ?: ""

request.setAttribute("curCategoryId", curCategoryId)
CategoryWorker.setTrail(request, curCategoryId)

categoryList = request.getAttribute("topLevelList")
//println "curCategoryId ================================== $categoryList"
if (categoryList) {
    catContentWrappers = FastMap.newInstance()
    CategoryWorker.getCategoryContentWrappers(catContentWrappers, categoryList, request)
    context.catContentWrappers = catContentWrappers
}

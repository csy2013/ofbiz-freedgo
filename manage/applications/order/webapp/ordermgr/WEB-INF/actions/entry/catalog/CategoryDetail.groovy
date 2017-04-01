import org.ofbiz.base.util.UtilProperties
import org.ofbiz.base.util.UtilValidate
import org.ofbiz.entity.GenericValue
import org.ofbiz.entity.condition.EntityCondition
import org.ofbiz.entity.util.EntityTypeUtil
import org.ofbiz.product.catalog.CatalogWorker
import org.ofbiz.product.category.CategoryContentWrapper
import org.ofbiz.product.store.ProductStoreWorker

productCategoryId = request.getAttribute("productCategoryId")
context.productCategoryId = productCategoryId

viewSize = parameters.VIEW_SIZE
viewIndex = parameters.VIEW_INDEX
currentCatalogId = CatalogWorker.getCurrentCatalogId(request)

// set the default view size
defaultViewSize = request.getAttribute("defaultViewSize") ?: UtilProperties.getPropertyValue("widget", "widget.form.defaultViewSize", "2")
context.defaultViewSize = defaultViewSize
// set the limit view
limitView = request.getAttribute("limitView") ?: true
context.limitView = limitView

// get the product category & members
andMap = [productCategoryId : productCategoryId,
        viewIndexString : viewIndex,
        viewSizeString : viewSize,
        defaultViewSize : defaultViewSize,
        limitView : limitView]
andMap.put("prodCatalogId", currentCatalogId)
andMap.put("checkViewAllow", true)
if (context.orderByFields) {
    andMap.put("orderByFields", context.orderByFields)
} else {
    andMap.put("orderByFields", ["sequenceNum", "productId"])
}
catResult = dispatcher.runSync("getProductCategoryAndLimitedMembers", andMap)

productCategory = catResult.productCategory
productCategoryMembers = catResult.productCategoryMembers

// Prevents out of stock product to be displayed on site
productStore = ProductStoreWorker.getProductStore(request)
if(productStore) {
    if("N".equals(productStore.showOutOfStockProducts)) {
        productsInStock = []
        productCategoryMembers.each { productCategoryMember ->
            product = delegator.findByPrimaryKeyCache("Product", [productId : productCategoryMember.productId])
            boolean isMarketingPackage = EntityTypeUtil.hasParentType(delegator, "ProductType", "productTypeId", product.productTypeId, "parentTypeId", "MARKETING_PKG")
            context.isMarketingPackage = (isMarketingPackage? "true": "false")
            if (isMarketingPackage) {
                resultOutput = dispatcher.runSync("getMktgPackagesAvailable", [productId : productCategoryMember.productId])
                availableInventory = resultOutput.availableToPromiseTotal
                if(availableInventory > 0) { 
                    productsInStock.add(productCategoryMember)
                }
            } else {
                facilities = delegator.findList("ProductFacility", EntityCondition.makeCondition([productId : productCategoryMember.productId]), null, null, null, false)
                availableInventory = 0.0
                if (facilities) {
                    facilities.each { facility ->
                        lastInventoryCount = facility.lastInventoryCount
                        if (lastInventoryCount != null) {
                            availableInventory += lastInventoryCount
                        }
                    }
                    if (availableInventory > 0) {
                        productsInStock.add(productCategoryMember)
                    }
                }
            }
        }
        context.productCategoryMembers = productsInStock
    } else {
        context.productCategoryMembers = productCategoryMembers
    }
}
context.productCategory = productCategory
context.viewIndex = catResult.viewIndex
context.viewSize = catResult.viewSize
context.lowIndex = catResult.lowIndex
context.highIndex = catResult.highIndex
context.listSize = catResult.listSize

// set this as a last viewed
// DEJ20070220: WHY is this done this way? why not use the existing CategoryWorker stuff?
LAST_VIEWED_TO_KEEP = 10 // modify this to change the number of last viewed to keep
lastViewedCategories = session.getAttribute("lastViewedCategories")
if (!lastViewedCategories) {
    lastViewedCategories = []
    session.setAttribute("lastViewedCategories", lastViewedCategories)
}
lastViewedCategories.remove(productCategoryId)
lastViewedCategories.add(0, productCategoryId)
while (lastViewedCategories.size() > LAST_VIEWED_TO_KEEP) {
    lastViewedCategories.remove(lastViewedCategories.size() - 1)
}

// set the content path prefix
contentPathPrefix = CatalogWorker.getContentPathPrefix(request)
context.put("contentPathPrefix", contentPathPrefix)

// little routine to see if any members have a quantity > 0 assigned
members = context.get("productCategoryMembers")
if (UtilValidate.isNotEmpty(members)) {
    for (i = 0; i < members.size(); i++) {
        productCategoryMember = (GenericValue) members.get(i)
        if (productCategoryMember.get("quantity") != null && productCategoryMember.getDouble("quantity").doubleValue() > 0.0) {
            context.put("hasQuantities", new Boolean(true))
            break
        }
    }
}

CategoryContentWrapper categoryContentWrapper = new CategoryContentWrapper(productCategory, request)
context.put("categoryContentWrapper", categoryContentWrapper)

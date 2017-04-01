import org.ofbiz.base.util.UtilDateTime
import org.ofbiz.base.util.UtilProperties
import org.ofbiz.base.util.string.FlexibleStringExpander

context.nowTimestampString = UtilDateTime.nowTimestamp().toString()

// make the image file formats
imageFilenameFormat = UtilProperties.getPropertyValue('catalog', 'image.filename.format')
imageServerPath = FlexibleStringExpander.expandString(UtilProperties.getPropertyValue("catalog", "image.server.path"), context)
while(imageServerPath.endsWith("/")) imageServerPath = imageServerPath.substring(0,imageServerPath.length()-1)
imageUrlPrefix = UtilProperties.getPropertyValue('catalog', 'image.url.prefix')
context.imageFilenameFormat = imageFilenameFormat
context.imageServerPath = imageServerPath
context.imageUrlPrefix = imageUrlPrefix

filenameExpander = FlexibleStringExpander.getInstance(imageFilenameFormat)
context.imageNameSmall = imageUrlPrefix + "/" + filenameExpander.expandString([location: 'products', id: productId, type: 'small'])
context.imageNameMedium = imageUrlPrefix + "/" + filenameExpander.expandString([location: 'products', id: productId, type: 'medium'])
context.imageNameLarge = imageUrlPrefix + "/" + filenameExpander.expandString([location: 'products', id: productId, type: 'large'])
context.imageNameDetail = imageUrlPrefix + "/" + filenameExpander.expandString([location: 'products', id: productId, type: 'detail'])
context.imageNameOriginal = imageUrlPrefix + "/" + filenameExpander.expandString([location: 'products', id: productId, type: 'original'])

// Start ProductContent stuff


productContent = null
if (product) {
    productContent = product.getRelated('ProductContent', null, ['productContentTypeId'])
}
context.productContent = productContent
// End ProductContent stuff

tryEntity = true
if (request.getAttribute("_ERROR_MESSAGE_")) {
    tryEntity = false
}
if (!product) {
    tryEntity = false
}

if ("true".equalsIgnoreCase((String) request.getParameter("tryEntity"))) {
    tryEntity = true
}
context.tryEntity = tryEntity





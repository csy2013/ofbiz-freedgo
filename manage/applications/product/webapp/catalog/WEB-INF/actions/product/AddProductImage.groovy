import javolution.util.FastMap
import org.ofbiz.base.util.HttpRequestFileUpload
import org.ofbiz.base.util.UtilDateTime
import org.ofbiz.base.util.UtilMisc
import org.ofbiz.base.util.UtilProperties
import org.ofbiz.base.util.string.FlexibleStringExpander
import org.ofbiz.product.image.ScaleImage


String productId = request.getParameter("productId")
if (productId) {
context.nowTimestampString = UtilDateTime.nowTimestamp().toString()

// make the image file formats
imageFilenameFormat = UtilProperties.getPropertyValue('catalog', 'image.filename.format')
imageServerPath = FlexibleStringExpander.expandString(UtilProperties.getPropertyValue("catalog", "image.server.path"), context)
while(imageServerPath.endsWith("/")) imageServerPath = imageServerPath.substring(0,imageServerPath.length()-1)
imageUrlPrefix = FlexibleStringExpander.expandString(UtilProperties.getPropertyValue('catalog', 'image.url.prefix'), context)
if(imageUrlPrefix.endsWith("/")) imageUrlPrefix = imageUrlPrefix.substring(0,imageUrlPrefix.length()-1)

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

    product = delegator.findByPrimaryKey("Product",UtilMisc.toMap("productId",productId))
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

// UPLOADING STUFF
    forLock = new Object()
    contentType = null
    String fileType = request.getParameter("upload_file_type")
    if (fileType) {

        context.fileType = fileType

        fileLocation = filenameExpander.expandString([location: 'products', id: productId, type: fileType])
        filePathPrefix = ""
        filenameToUse = fileLocation
        if (fileLocation.lastIndexOf("/") != -1) {
            filePathPrefix = fileLocation.substring(0, fileLocation.lastIndexOf("/") + 1)
            // adding 1 to include the trailing slash
            filenameToUse = fileLocation.substring(fileLocation.lastIndexOf("/") + 1)
        }

        int i1
        if (contentType && (i1 = contentType.indexOf("boundary=")) != -1) {
            contentType = contentType.substring(i1 + 9)
            contentType = "--" + contentType
        }

        defaultFileName = filenameToUse + "_temp"
        uploadObject = new HttpRequestFileUpload()
        uploadObject.setOverrideFilename(defaultFileName)
        uploadObject.setSavePath(imageServerPath + "/" + filePathPrefix)
        uploadObject.doUpload(request)

        clientFileName = uploadObject.getFilename()
        if (clientFileName) {
            context.clientFileName = clientFileName
        }
        String fileExt = ""
        if (clientFileName && clientFileName.length() > 0) {
            if (clientFileName.lastIndexOf(".") > 0 && clientFileName.lastIndexOf(".") < clientFileName.length()) {
                filenameToUse += clientFileName.substring(clientFileName.lastIndexOf("."))
                fileExt = clientFileName.substring(clientFileName.lastIndexOf("."))
            } else {
                filenameToUse += ".jpg"
                fileExt = ".jpg"
            }

            context.clientFileName = clientFileName
            context.filenameToUse = filenameToUse

            characterEncoding = request.getCharacterEncoding()
            imageUrl = imageUrlPrefix + "/" + filePathPrefix + java.net.URLEncoder.encode(filenameToUse, characterEncoding)

            try {
                file = new File(imageServerPath + "/" + filePathPrefix, defaultFileName)
                file1 = new File(imageServerPath + "/" + filePathPrefix, filenameToUse)
                try {
                    // Delete existing image files
                    File targetDir = new File(imageServerPath + "/" + filePathPrefix)
                    // Images are ordered by productId (${location}/${id}/${viewtype}/${sizetype})
                    if (!filenameToUse.startsWith(productId + ".")) {
                        File[] files = targetDir.listFiles(); for (File file : files) {
                            if (file.isFile() &&
                                    file.getName().contains(filenameToUse.substring(0, filenameToUse.indexOf(".") + 1)) && !fileType.equals("original")) {
                                file.delete()
                            } else if (file.isFile() && fileType.equals("original") && !file.getName().equals(defaultFileName)) {
                                file.delete()
                            }
                        }
                        // Images aren't ordered by productId (${location}/${viewtype}/${sizetype}/${id}) !!! BE CAREFUL !!!
                    } else {
                        File[] files = targetDir.listFiles(); for (File file : files) {
                            if (file.isFile() && !file.getName().equals(defaultFileName) && file.getName().startsWith(productId + ".")) file.delete()
                        }
                    }
                }
                catch (Exception e) {
                    System.out.
                            println("error deleting existing file (not neccessarily a problem)")
                }
                file.renameTo(file1)
            } catch (Exception e) {
                e.printStackTrace()
            }


            if (imageUrl && imageUrl.length() > 0) {
                context.imageUrl = imageUrl
                product.set(fileType + "ImageUrl", imageUrl)

                // call scaleImageInAllSize
                if (fileType.equals("original")) {

                    String uploadType = UtilProperties.getPropertyValue("content", "content.image.upload.type")
                    result = ScaleImage.scaleImageInAllSize(context, filenameToUse, "main", "0","products",productId)
                    if (uploadType.equals("qiniu")) {
                        String saveImagePath = imageUrlPrefix + "/" + filePathPrefix
                        String saveImageKey = (saveImagePath + fileType + fileExt)
                        filePath = imageServerPath + "/" + filePathPrefix + fileType + fileExt
                        println "filePath = $filePath"
                        println "saveImageKey = $saveImageKey"
                        if (uploadType.equals("qiniu")) {
                            dispatcher.runAsync("coverUpload", UtilMisc.toMap("filePath", filePath, "fileKey", saveImageKey))
                        }

                        Map<String, String> imageUrlMap = FastMap.newInstance()
                        for (String sizeType : ScaleImage.sizeTypeList) {
                            if (sizeType.equals("small")) {
                                imageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/200")
                            } else if (sizeType.equals("medium")) {
                                imageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/400")
                            } else if (sizeType.equals("large")) {
                                imageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/800")
                            } else if (sizeType.equals("detail")) {
                                imageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/600")
                            } else if (sizeType.equals("thumbnail")) {
                                imageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/220")
                            }
                        }
                        result.put("imageUrlMap", imageUrlMap)
                        result.put("responseMessage","success")
                    }
                    if (result.containsKey("responseMessage") && result.get("responseMessage").equals("success")) {
                        imgMap = result.get("imageUrlMap")
                        imgMap.each() { key, value ->
                            product.set(key + "ImageUrl", value)
                        }
                    }
                }

                product.store()
            }
        }
    }

    return "success"
}

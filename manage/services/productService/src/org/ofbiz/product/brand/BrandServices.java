package org.ofbiz.product.brand;

import javolution.util.FastList;
import javolution.util.FastMap;
import org.jdom.JDOMException;
import org.ofbiz.base.util.*;
import org.ofbiz.base.util.string.FlexibleStringExpander;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.product.image.ScaleImage;
import org.ofbiz.product.product.ProductContentWrapper;
import org.ofbiz.service.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by changsy on 16/5/4.
 */
public class BrandServices {
    private static final String module = BrandServices.class.getName();

    public static final String resource = "ProductUiLabels";
    public static final String resourceError = "ProductErrorUiLabels";

    public static Map<String, Object> updateLogoForBrand(DispatchContext dctx, Map<String, ? extends Object> context)
            throws IOException, JDOMException {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        Map<String, Object> retObj = FastMap.newInstance();
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Delegator delegator = dctx.getDelegator();
        String brandId = (String) context.get("brandId");
        String brandContentTypeId = (String) context.get("brandContentTypeId");
        ByteBuffer imageData = (ByteBuffer) context.get("uploadedFile");
        Locale locale = (Locale) context.get("locale");

        if (UtilValidate.isNotEmpty(context.get("_uploadedFile_fileName"))) {
            String imageFilenameFormat = UtilProperties.getPropertyValue("catalog", "image.filename.brand.format");
            String imageServerPath = FlexibleStringExpander.expandString(UtilProperties.getPropertyValue("catalog", "image.server.path"), context);
            while(imageServerPath.endsWith("/")) imageServerPath = imageServerPath.substring(0,imageServerPath.length()-1);
            String imageUrlPrefix = FlexibleStringExpander.expandString(UtilProperties.getPropertyValue("catalog", "image.url.prefix"), context);
            if(imageUrlPrefix.endsWith("/")) imageUrlPrefix = imageUrlPrefix.substring(0,imageUrlPrefix.length()-1);

            FlexibleStringExpander filenameExpander = FlexibleStringExpander.getInstance(imageFilenameFormat);
            String fileLocation = filenameExpander.expandString(UtilMisc.toMap("location", "brands", "id", brandId, "sizetype", "original"));
            String filePathPrefix = "";
            String filenameToUse = fileLocation;
            if (fileLocation.lastIndexOf("/") != -1) {
                filePathPrefix = fileLocation.substring(0, fileLocation.lastIndexOf("/") + 1); // adding 1 to include the trailing slash
                filenameToUse = fileLocation.substring(fileLocation.lastIndexOf("/") + 1);
            }

            List<GenericValue> fileExtension = FastList.newInstance();
            try {
                fileExtension = delegator.findByAnd("FileExtension", UtilMisc.toMap("mimeTypeId", context.get("_uploadedFile_contentType")));
            } catch (GenericEntityException e) {
                Debug.logError(e, module);
                return ServiceUtil.returnError(e.getMessage());
            }

            GenericValue extension = EntityUtil.getFirst(fileExtension);
            if (extension != null) {
                filenameToUse += "." + extension.getString("fileExtensionId");
                retObj.put("name", filenameToUse);
                retObj.put("type", extension.getString("fileExtensionId"));
            }
            retObj.put("delete_url", "removeProductImages");
            retObj.put("delete_type", "POST");
            retObj.put("brandContentTypeId", brandContentTypeId);
            retObj.put("brandId", brandId);
            retObj.put("size", imageData.array().length);
            /* Write the new image file */
            String targetDirectory = imageServerPath + "/" + filePathPrefix;
            try {
                File targetDir = new File(targetDirectory);
                // Create the new directory
                if (!targetDir.exists()) {
                    boolean created = targetDir.mkdirs();
                    if (!created) {
                        String errMsg = UtilProperties.getMessage(resource, "ScaleImage.unable_to_create_target_directory", locale) + " - " + targetDirectory;
                        Debug.logFatal(errMsg, module);
                        return ServiceUtil.returnError(errMsg);
                    }
                } else if (!filenameToUse.contains(brandId)) {
                    try {
                        File[] files = targetDir.listFiles();
                        for (File file : files) {
                            if (file.isFile()) file.delete();
                        }
                    } catch (SecurityException e) {
                        Debug.logError(e, module);
                    }
                } else {
                    try {
                        File[] files = targetDir.listFiles();
                        for (File file : files) {
                            if (file.isFile() && file.getName().startsWith(brandId))
                                file.delete();
                        }
                    } catch (SecurityException e) {
                        Debug.logError(e, module);
                    }
                }
            } catch (NullPointerException e) {
                Debug.logError(e, module);
            }
            // Write
            String filePath = imageServerPath + "/" + fileLocation + "." + extension.getString("fileExtensionId");
            try {
                File file = new File(filePath);
                try {
                    RandomAccessFile out = new RandomAccessFile(file, "rw");
                    out.write(imageData.array());
                    out.close();
                } catch (FileNotFoundException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                            "ProductImageViewUnableWriteFile", UtilMisc.toMap("fileName", file.getAbsolutePath()), locale));
                } catch (IOException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                            "ProductImageViewUnableWriteBinaryData", UtilMisc.toMap("fileName", file.getAbsolutePath()), locale));
                }
            } catch (NullPointerException e) {
                Debug.logError(e, module);
            }

            //文件上传到服务器之后(备份使用)，选择是否上传到云存储
            String uploadType = UtilProperties.getPropertyValue("content", "content.image.upload.type");
            /* scale Image in different sizes */
            Map<String, Object> resultResize = FastMap.newInstance();
            Map<String, String> qiniuImageUrlMap = FastMap.newInstance();
            String saveImagePath = imageUrlPrefix + "/" + filePathPrefix;
            String saveImageKey = (saveImagePath + "original." + extension.getString("fileExtensionId"));
            try {
                resultResize.putAll(ScaleImage.scaleImageInAllSize(context, filenameToUse, "main", "0", "brands", brandId));
                if (uploadType.equals("qiniu")) {
                    //异步调用qiniu 上传文件的服务器
                    dispatcher.runAsync("coverUpload", UtilMisc.toMap("filePath", filePath, "fileKey", saveImageKey));
                    for (String sizeType : ScaleImage.sizeTypeList) {
                        if (sizeType.equals("small")) {
                            qiniuImageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/200");
                        } else if (sizeType.equals("medium")) {
                            qiniuImageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/400");
                        } else if (sizeType.equals("large")) {
                            qiniuImageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/800");
                        } else if (sizeType.equals("detail")) {
                            qiniuImageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/600");
                        } else if (sizeType.equals("thumbnail")) {
                            qiniuImageUrlMap.put(sizeType, saveImageKey + "?imageView2/2/w/220");
                        }
                    }

                }
            } catch (IOException e) {
                Debug.logError(e, "Scale additional image in all different sizes is impossible : " + e.toString(), module);
                return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                        "ProductImageViewScaleImpossible", UtilMisc.toMap("errorString", e.toString()), locale));
            } catch (JDOMException e) {
                Debug.logError(e, "Errors occur in parsing ImageProperties.xml : " + e.toString(), module);
                return ServiceUtil.returnError(UtilProperties.getMessage(resource,
                        "ProductImageViewParsingError", UtilMisc.toMap("errorString", e.toString()), locale));
            } catch (ServiceValidationException e) {
                e.printStackTrace();
                return ServiceUtil.returnError(e.getMessage());
            } catch (ServiceAuthException e) {
                e.printStackTrace();
                return ServiceUtil.returnError(e.getMessage());
            } catch (GenericServiceException e) {
                e.printStackTrace();
                return ServiceUtil.returnError(e.getMessage());
            }

            String imageUrl = imageUrlPrefix + "/" + fileLocation + "." + extension.getString("fileExtensionId");
            /* store the imageUrl version of the image, for backwards compatibility with code that does not use scaled versions */
            result = addImageResource(dispatcher, delegator, context, imageUrl, saveImageKey, brandContentTypeId);

            if (ServiceUtil.isError(result)) {
                return result;
            }
            GenericValue product = null;
            ProductContentWrapper contentWrapper = null;



            /* now store the image versions created by ScaleImage.scaleImageInAllSize */
            /* have to shrink length of productContentTypeId, as otherwise value is too long for database field */
            Map<String, String> imageUrlMap = UtilGenerics.checkMap(resultResize.get("imageUrlMap"));
            for (String sizeType : ScaleImage.sizeTypeList) {
                imageUrl = imageUrlMap.get(sizeType);
                String qiniuImageUrl = qiniuImageUrlMap.get(sizeType);
                if (UtilValidate.isNotEmpty(imageUrl)) {
                    result = addImageResource(dispatcher, delegator, context, imageUrl, qiniuImageUrl, sizeType);
                    if (ServiceUtil.isError(result)) {
                        return result;
                    }
                }
            }

        }
        result.put("retObj", retObj);
        return result;
    }

    private static Map<String, Object> addImageResource(LocalDispatcher dispatcher, Delegator delegator, Map<String, ? extends Object> context,
                                                        String imageUrl, String qiniuImageUrl, String brandContentTypeId) {
        brandContentTypeId = brandContentTypeId.toUpperCase() + "_IMAGE_URL";
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        String brandId = (String) context.get("brandId");

        if (UtilValidate.isNotEmpty(imageUrl) && imageUrl.length() > 0) {
            String contentId = (String) context.get("contentId");

            Map<String, Object> dataResourceCtx = FastMap.newInstance();
            dataResourceCtx.put("objectInfo", imageUrl);
            dataResourceCtx.put("qiniuObjectInfo", qiniuImageUrl);
            dataResourceCtx.put("isPublic", "Y");
            dataResourceCtx.put("dataResourceName", context.get("_uploadedFile_fileName"));
            dataResourceCtx.put("userLogin", userLogin);

            Map<String, Object> brandContentCtx = FastMap.newInstance();
            brandContentCtx.put("brandId", brandId);
            brandContentCtx.put("brandContentTypeId", brandContentTypeId);
            brandContentCtx.put("thruDate", context.get("thruDate"));
            brandContentCtx.put("userLogin", userLogin);

            if (UtilValidate.isNotEmpty(contentId)) {
                GenericValue content = null;
                try {
                    content = delegator.findOne("Content", UtilMisc.toMap("contentId", contentId), false);
                } catch (GenericEntityException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                }

                if (content != null) {
                    GenericValue dataResource = null;
                    try {
                        dataResource = content.getRelatedOne("DataResource");
                    } catch (GenericEntityException e) {
                        Debug.logError(e, module);
                        return ServiceUtil.returnError(e.getMessage());
                    }

                    if (dataResource != null) {
                        dataResourceCtx.put("dataResourceId", dataResource.getString("dataResourceId"));
                        try {
                            dispatcher.runSync("updateDataResource", dataResourceCtx);
                        } catch (GenericServiceException e) {
                            Debug.logError(e, module);
                            return ServiceUtil.returnError(e.getMessage());
                        }
                    } else {
                        dataResourceCtx.put("dataResourceTypeId", "OFBIZ_URL_FILE");
                        dataResourceCtx.put("mimeTypeId", context.get("_uploadedFile_contentType"));
                        Map<String, Object> dataResourceResult = FastMap.newInstance();
                        try {
                            dataResourceResult = dispatcher.runSync("createDataResource", dataResourceCtx);
                        } catch (GenericServiceException e) {
                            Debug.logError(e, module);
                            return ServiceUtil.returnError(e.getMessage());
                        }

                        Map<String, Object> contentCtx = FastMap.newInstance();
                        contentCtx.put("contentId", contentId);
                        contentCtx.put("dataResourceId", dataResourceResult.get("dataResourceId"));
                        contentCtx.put("userLogin", userLogin);
                        try {
                            dispatcher.runSync("updateContent", contentCtx);
                        } catch (GenericServiceException e) {
                            Debug.logError(e, module);
                            return ServiceUtil.returnError(e.getMessage());
                        }
                    }

                    brandContentCtx.put("contentId", contentId);

                }
            } else {
                dataResourceCtx.put("dataResourceTypeId", "OFBIZ_URL_FILE");
                dataResourceCtx.put("mimeTypeId", context.get("_uploadedFile_contentType"));
                Map<String, Object> dataResourceResult = FastMap.newInstance();
                try {
                    dataResourceResult = dispatcher.runSync("createDataResource", dataResourceCtx);
                } catch (GenericServiceException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                }

                Map<String, Object> contentCtx = FastMap.newInstance();
                contentCtx.put("contentTypeId", "DOCUMENT");
                contentCtx.put("dataResourceId", dataResourceResult.get("dataResourceId"));
                contentCtx.put("userLogin", userLogin);
                Map<String, Object> contentResult = FastMap.newInstance();
                try {
                    contentResult = dispatcher.runSync("createContent", contentCtx);
                } catch (GenericServiceException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                }

                brandContentCtx.put("contentId", contentResult.get("contentId"));
                try {
                    dispatcher.runSync("createBrandContent", brandContentCtx);
                } catch (GenericServiceException e) {
                    Debug.logError(e, module);
                    return ServiceUtil.returnError(e.getMessage());
                }
            }
        }
        return ServiceUtil.returnSuccess();
    }

    public static final Map<String, Object> deleteBrand(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String brandId = (String) context.get("brandId");
        Delegator delegator = dcx.getDelegator();
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        LocalDispatcher dispatcher = dcx.getDispatcher();
        if (UtilValidate.isNotEmpty(brandId)) {
            try {
                List<GenericValue> contentList = delegator.findByAnd("BrandContent", UtilMisc.toMap("brandId", brandId));
                delegator.removeByAnd("BrandContent", UtilMisc.toMap("brandId", brandId));
                if (UtilValidate.isNotEmpty(contentList)) {
                    for (int i = 0; i < contentList.size(); i++) {
                        GenericValue content = contentList.get(i);
                        result = dispatcher.runSync("removeContentAndRelated", UtilMisc.toMap("contentId", content.get("contentId"), "userLogin", userLogin));
                        if (ServiceUtil.isError(result)) {
                            return ServiceUtil.returnError(ServiceUtil.getErrorMessage(result));
                        }
                    }
                }
                delegator.removeByAnd("BrandProductAppl", UtilMisc.toMap("brandId", brandId));
                delegator.removeByAnd("BrandCategoryAppl", UtilMisc.toMap("brandId", brandId));
                delegator.removeByAnd("Brand", UtilMisc.toMap("brandId", brandId));
            } catch (GenericEntityException e) {
                e.printStackTrace();
                return ServiceUtil.returnError(e.getMessage());
            } catch (GenericServiceException e) {
                e.printStackTrace();
                return ServiceUtil.returnError(e.getMessage());
            }
        }
        return result;
    }

    public static final Map<String, Object> addBrandProduct(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String brandId = (String) context.get("brandId");
        String productId = (String) context.get("productId");
        Delegator delegator = dcx.getDelegator(); 
        if (UtilValidate.isNotEmpty(brandId) && UtilValidate.isNotEmpty(productId)) {
            try {
                GenericValue brandProduct = delegator.findByPrimaryKey("BrandProductAppl", UtilMisc.toMap("brandId", brandId, "productId", productId));
                if (UtilValidate.isNotEmpty(brandProduct)) {
                    return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,"BrandProductAppl.product_has_exists",(Locale)context.get("locale")));
                } else {
                    brandProduct = delegator.makeValue("BrandProductAppl", UtilMisc.toMap("productId", productId, "brandId", brandId));
                    brandProduct.create();
                }
            } catch (GenericEntityException e) {
                e.printStackTrace();
                return ServiceUtil.returnError(e.getMessage());
            }
        }
        return result;
    }

    public static final Map<String, Object> addBrandCategory(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String brandId = (String) context.get("brandId");
        String categoryId = (String) context.get("categoryId");
        Delegator delegator = dcx.getDelegator(); 
        if (UtilValidate.isNotEmpty(brandId) && UtilValidate.isNotEmpty(categoryId)) {
            try {
                GenericValue brandCategory = delegator.findByPrimaryKey("BrandCategoryAppl", UtilMisc.toMap("brandId", brandId, "categoryId", categoryId));
                if (UtilValidate.isNotEmpty(brandCategory)) {
                    return ServiceUtil.returnError(UtilProperties.getMessage(resourceError,"BrandCategoryAppl.category_has_exists",(Locale)context.get("locale")));
                } else {
                    brandCategory = delegator.makeValue("BrandCategoryAppl", UtilMisc.toMap("categoryId", categoryId, "brandId", brandId));
                    brandCategory.create();
                }
            } catch (GenericEntityException e) {
                e.printStackTrace();
                return ServiceUtil.returnError(e.getMessage());
            }
        }
        return result;
    }

    public static final Map<String, Object> deleteBrandProduct(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String brandId = (String) context.get("brandId");
        String productId = (String) context.get("productId");
        Delegator delegator = dcx.getDelegator();
         try{
                delegator.removeByAnd("BrandProductAppl", UtilMisc.toMap("brandId", brandId,"productId",productId));
            } catch (GenericEntityException e) {
                e.printStackTrace();
                return ServiceUtil.returnError(e.getMessage());
            }

        return result;
    }


    public static final Map<String, Object> deleteBrandCategory(DispatchContext dcx, Map<String, ? extends Object> context) {
        Map<String, Object> result = ServiceUtil.returnSuccess();
        String brandId = (String) context.get("brandId");
        String categoryId = (String) context.get("categoryId");
        Delegator delegator = dcx.getDelegator();
        try{
            delegator.removeByAnd("BrandCategoryAppl", UtilMisc.toMap("brandId", brandId,"categoryId",categoryId));
        } catch (GenericEntityException e) {
            e.printStackTrace();
            return ServiceUtil.returnError(e.getMessage());
        }

        return result;
    }
}

package org.ofbiz.content.filemanage;


import com.google.gson.Gson;
import javolution.util.FastList;
import javolution.util.FastMap;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.HttpHeaders;
import org.ofbiz.base.util.*;
import org.ofbiz.content.ContentTypes;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.util.EntityUtil;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by csy on 2015/3/11.
 */
public class ContentFileManageEvents {
    public static final String module = ContentFileManageEvents.class.getName();
    
    public static void manager(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String reqType = UtilHttp.getParameterByName(request, "t", "");
        if (reqType.equals("init")) {
            directory(request, response);
        } else if (reqType.equals("upload")) {
            upload(request, response);
        } else {
            String directory = UtilHttp.getParameterByName(request, "directory", "");
            if (directory != null && (!directory.equals(""))) {
                files(request, response);
            }
        }
    }
    
    public void folders(HttpServletRequest request, HttpServletResponse response) throws Exception {
        StringBuffer buffer = new StringBuffer();
        List list = new ArrayList();
        recursiveFolders("", list);
        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                String s = (String) list.get(i);
                buffer.append("<option value='" + s + "'>" + s + "</option>");
            }
            
        }
        response.setContentType(ContentTypes.TEXT_PLAIN);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        PrintWriter pw = response.getWriter();
        pw.write(buffer.toString());
        pw.close();
        pw.flush();
        
    }
    
    
    private void recursiveFolders(String dir, List list) throws Exception {
        String directory = UtilProperties.getPropertyValue("content.properties", "content.image.path.prefix");
        File[] files = new File(directory + "/" + dir).listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isDirectory()) {
                    if (!dir.equals("")) {
                        dir = dir + "/";
                        list.add(dir + file.getName());
                    } else
                        list.add("/" + file.getName());
                    recursiveFolders(dir + "/" + file.getName(), list);
                }
            }
        }
    }
    
    
    public static void directory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        String reqDir = UtilHttp.getParameterByName(request, "directory", "");
        String directory = UtilProperties.getPropertyValue("content.properties", "content.image.path.prefix");
        directory = directory + (UtilHttp.getTenantByURL(request).equals("default") ? "" : "/" + UtilHttp.getTenantByURL(request));
        if (!reqDir.equals("")) directory = directory + "/" + reqDir;
        Delegator delegator = (Delegator) request.getAttribute("delegator");
        File dir = new File(directory);
        String[] list = FileUtil.listDirs(dir);
        List dirList = FastList.newInstance();
        if (list != null && list.length > 0) {
            for (int i = 0; i < list.length; i++) {
                String fileName = list[i];
                boolean isOk = false;
                boolean subTree = true;
                if (reqDir.equals("")) {
                    subTree = false;
                    String[] allowViewPaths = UtilProperties.getPropertyValues("content.properties", "content.image.view.path");
                    if (ArrayUtil.contains(allowViewPaths, fileName)) {
                        isOk = true;
                    }
                }
                if ((subTree || (isOk)) && (!fileName.equals(".svn"))) {
                    File file = new File(directory + "/" + fileName);
                    String data = file.getName();
                    String absolutePath = file.getAbsolutePath();
                    Map map = FastMap.newInstance();
                    if (data.equals("products")) data = "产品图片";
                    if (data.equals("categories")) data = "分类图片";
                    if (data.equals("catalog")) data = "目录图片";
                    if (data.equals("datasource")) data = "图片池";
                    if (reqDir.equals("/products")) {
                        GenericValue product = delegator.findByPrimaryKey("Product", UtilMisc.toMap("productId", data));
                        if (UtilValidate.isNotEmpty(product)) {
                            data = (String) product.get("productName");
                            map.put("data", data);
                        }
                    } else if (reqDir.equals("/categories")) {
                        GenericValue cate = delegator.findByPrimaryKey("ProductCategory", UtilMisc.toMap("productCategoryId", data));
                        if (UtilValidate.isNotEmpty(cate)) {
                            data = (String) cate.get("categoryName");
                            map.put("data", data);
                        }
                    } else {
                        map.put("data", data);
                    }
//                  $json[$i]['attributes']['directory']
                    Map attrObj = FastMap.newInstance();
                    attrObj.put("directory", reqDir + "/" + file.getName());
                    map.put("attributes", attrObj);
                    if (file.isDirectory() && FileUtil.listDirs(file).length > 0) {
                        String children = " ";
                        map.put("children", children);
                    }
                    dirList.add(map);
                }
                
            }
        }
        
        String json = new Gson().toJson(dirList);
        response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        PrintWriter pw = response.getWriter();
        pw.write(json);
        pw.close();
        pw.flush();
    }
    
    public static void files(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String reqDir = UtilHttp.getParameterByName(request, "directory", "");
        String directory = UtilProperties.getPropertyValue("content.properties", "content.image.path.prefix");
        directory = directory + (UtilHttp.getTenantByURL(request).equals("default") ? "" : "/" + UtilHttp.getTenantByURL(request));
        if (!reqDir.equals("")) directory = directory + "/" + reqDir;
        String[] allowed = new String[]{".jpg", ".jpeg", ".png", ".gif"};
        File dirFile = new File(directory);
        File[] files = dirFile.listFiles();
        List array = FastList.newInstance();
        if (files != null && files.length > 0) {
            String fileType = "";
            
            for (File file : files) {
                String fileName = file.getName();
                if (file.isFile()) {
                    fileType = fileName.substring(fileName.lastIndexOf("."));
                    if (ArrayUtil.contains(allowed, fileType)) {
                        double size = FileUtil.getBytes(file).length;
                        int i = 0;
                        String[] suffix = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"};
                        while ((size / 1024) > 1) {
                            size = size / 1024;
                            i++;
                        }
                        BufferedImage bufferedImage = ImageIO.read(file);
                        int width = bufferedImage.getWidth();
                        int height = bufferedImage.getHeight();
                        Map jsonObject = FastMap.newInstance();
                        jsonObject.put("file", (UtilHttp.getTenantByURL(request).equals("default") ? "" : "/" + UtilHttp.getTenantByURL(request)) + reqDir + "/" + fileName);
                        jsonObject.put("filename", fileName);
                        if (fileName.startsWith("detail.")) {
                            jsonObject.put("name", "详细图");
                        } else if (fileName.startsWith("large.")) {
                            jsonObject.put("name", "大图");
                        } else if (fileName.startsWith("medium.")) {
                            jsonObject.put("name", "中图");
                        } else if (fileName.startsWith("original.")) {
                            jsonObject.put("name", "原始图片");
                        } else if (fileName.startsWith("small.")) {
                            jsonObject.put("name", "小图");
                        } else if (fileName.startsWith("thumbnail.")) {
                            jsonObject.put("name", "缩微图");
                        } else {
                            jsonObject.put("name", fileName);
                        }
                        Locale locale = UtilHttp.getLocale(request);
                        jsonObject.put("size", UtilFormatOut.formatAmount(size, locale) + suffix[i]);
                        jsonObject.put("thumb", (UtilHttp.getTenantByURL(request).equals("default") ? "" : "/" + UtilHttp.getTenantByURL(request)) + reqDir + "/" + fileName);
                        jsonObject.put("width", width);
                        jsonObject.put("height", height);
                        array.add(jsonObject);
                    }
                }
                
                
            }
            
            
        }
        String json = new Gson().toJson(array);
        response.setContentType(ContentTypes.APPLICATION_TEXT);
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        PrintWriter pw = response.getWriter();
        pw.write(json);
        pw.close();
        pw.flush();
    }
    
    
    public static void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {

//          <set field="createImage.dataTemplateTypeId" value="NONE"/>
//      <set field="createImage.contentName" from-field="parameters.articleName"/>
//      <set field="createImage.description" from-field="parameters.description"/>
//      <set field="createImage.statusId" from-field="parameters.statusId"/>
//      <set field="createImage.partyId" from-field="userLogin.partyId"/>
//      <set field="createImage.isPublic" value="Y"/>
//      <set field="createImage.uploadedFile" from-field="parameters.uploadedFile"/>
//      <set field="createImage._uploadedFile_fileName" from-field="parameters._uploadedFile_fileName"/>
//      <set field="createImage._uploadedFile_contentType" from-field="parameters._uploadedFile_contentType"/>
//      <call-service service-name="createContentFromUploadedFile" in-map-name="createImage">
//        <result-to-field result-name="contentId" field="imageContentId"/>
//      </call-service>
//
        
        String directory = "";
        String imageName = "";
        JSONObject object = new JSONObject();
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);//检查输入请求是否为multipart表单数据。
        if (isMultipart == true) {
            FileItemFactory factory = new DiskFileItemFactory();//为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);
            Iterator<FileItem> itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = itr.next();
                //检查当前项目是普通表单项目还是上传文件。
                if (item.isFormField()) {//如果是普通表单项目，显示表单内容。
                    String fieldName = item.getFieldName();
                    String value = item.getString();
                    if (fieldName.equals("directory")) {
                        directory = value;
                    }
                }
            }
            for (int i = 0; i < items.size(); i++) {
                FileItem fileItem = items.get(i);
                if (!fileItem.isFormField())
                    imageName = fileItem.getName();
                long size = fileItem.getSize();
                String fileType = fileItem.getContentType();
                Locale locale = request.getLocale();
                if (!imageName.equals("") && fileType != null) {
                    if (size > 30000000) {
                        object.put("error", UtilProperties.getMessage("ContentErrorUiLabels", "filemanage.error_file_size", locale));
                    } else if (imageName.length() < 3 || imageName.length() > 255) {
                        object.put("error", UtilProperties.getMessage("ContentErrorUiLabels", "filemanage.error_filename", locale));
                    } else {
                        
                        String[] allowed = new String[]{"image/jpeg", "image/pjpeg", "image/png", "image/x-png", "image/gif", "application/x-shockwave-flash"};
                        String baseDir = UtilProperties.getPropertyValue("content.properties", "content.image.path.prefix");
                        String tenantId = UtilHttp.getTenantByURL(request);
                        String baseUploadDir = baseDir + File.separator + directory;
                        String imageUrl = File.separator+"images" + directory;
                        if (!tenantId.equals("default")) {
                            baseUploadDir = baseDir + File.separator + tenantId + directory;
                            imageUrl = File.separator+"images" + File.separator + tenantId + directory+File.separator+imageName;
                        }
                        File uploadFilePath = new File(baseUploadDir);
                        if ((!uploadFilePath.exists()) || (!uploadFilePath.isDirectory())) {
                            object.put("error", UtilProperties.getMessage("ContentErrorUiLabels", "filemanage.error_directory", locale));
                        } else if (!ArrayUtil.contains(allowed, fileType)) {
                            object.put("error", UtilProperties.getMessage("ContentErrorUiLabels", "filemanager.error_file_type", locale));
                        } else {
                            LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
                            Delegator delegator = (Delegator) request.getAttribute("delegator");
                            String[] typeAllowed = new String[]{".jpg", ".jpeg", ".png", ".gif", ".flv"};
                            String imageType = imageName.substring(imageName.lastIndexOf("."));
                            if (!ArrayUtil.contains(typeAllowed, imageType)) {
                                object.put("error", UtilProperties.getMessage("ContentErrorUiLabels", "filemanage.error_file_type", locale));
                            } else {
                                //文件上传到服务器之后(备份使用)，选择是否上传到云存储
                                String uploadType = UtilProperties.getPropertyValue("content", "content.image.upload.type");
                                String imageTypes = fileItem.getContentType();
                                List<GenericValue> fileExtension = FastList.newInstance();
                                try {
                                    fileExtension = delegator.findByAnd("FileExtension", UtilMisc.toMap("mimeTypeId", imageTypes));
                                } catch (GenericEntityException e) {
                                    Debug.logError(e, module);
                                    
                                }
                                
                                File imageFile = new File(baseUploadDir + "/" + imageName);
                                FileCopyUtils.copy(fileItem.get(),imageFile);
//                                UtilIO.writeObject(imageFile, fileItem.get());
                                
                                GenericValue extension = EntityUtil.getFirst(fileExtension);
                                if (uploadType.equals("qiniu")) {
                                    //异步调用qiniu 上传文件的服务器
                                    String saveImagePath = imageFile.getAbsolutePath();
                                    String saveImageKey = (baseUploadDir + "original." + extension.getString("fileExtensionId"));
                                    dispatcher.runAsync("coverUpload", UtilMisc.toMap("filePath", saveImagePath, "fileKey", saveImageKey));
                                    
                                }
                                
                                GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
                                //createDataResource
                                Map<String, Object> serviceIn = FastMap.newInstance();
                                serviceIn.put("userLogin", userLogin);
                                serviceIn.put("imageUrl", imageUrl);
                                serviceIn.put("qiniuImageUrl", "");
                                serviceIn.put("_uploadedFile_fileName", imageName);
                                serviceIn.put("_uploadedFile_contentType", imageTypes);
                                Map<String, Object> ret = dispatcher.runSync("addImageResourceToContent", serviceIn);
                                if(ServiceUtil.isSuccess(ret)) {
                                    object.put("success", UtilProperties.getMessage("ContentErrorUiLabels", "filemanage.text_uploaded", locale));
                                }else{
                                    object.put("error", ServiceUtil.getErrorMessage(ret));
                                }
    
                            }
                            
                        }
                    }
                }
                
            }
            response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
            response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            PrintWriter pw = response.getWriter();
            pw.write(object.toString());
            pw.close();
            pw.flush();
            
        }
        
    }
}

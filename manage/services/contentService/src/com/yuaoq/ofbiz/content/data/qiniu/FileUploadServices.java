package com.yuaoq.ofbiz.content.data.qiniu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Recorder;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.ServiceUtil;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * Created by changsy on 16/4/26.
 */
public class FileUploadServices {

    protected static final String module = FileUploadServices.class.getName();

    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static  final String ACCESS_KEY = UtilProperties.getPropertyValue("content","img.qiniu.access.key");
    private static final String SECRET_KEY = UtilProperties.getPropertyValue("content","img.qiniu.secret.key");
    //要上传的空间
    private  static final String bucketname = UtilProperties.getPropertyValue("content","img.qiniu.bucket.name");
    //上传到七牛后保存的文件名

    //上传文件的路径




    // 覆盖上传
    public static String getCoverUpToken(Auth auth,String key){
        //<bucket>:<key>，表示只允许用户上传指定key的文件。在这种格式下文件默认允许“修改”，已存在同名资源则会被本次覆盖。
        //如果希望只能上传指定key的文件，并且不允许修改，那么可以将下面的 insertOnly 属性值设为 1。
        //第三个参数是token的过期时间
        return auth.uploadToken(bucketname, key, 3600, new StringMap().put("insertOnly", 0));
    }


    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public static String getSimpleUpToken(Auth auth){
        return auth.uploadToken(bucketname);
    }

    // 覆盖上传
    public static String getBreakUpToken(Auth auth){
        return auth.uploadToken(bucketname);
    }
    /**
     *
     * @throws IOException
     */
    public static Map<String,Object> simpleUpload(DispatchContext dcx,Map<String,? extends Object> context) {
        Map<String,Object> result = ServiceUtil.returnSuccess();
        String filePath = (String) context.get("filePath");
        String key = (String) context.get("fileKey");
        try {
            //密钥配置
            Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            //创建上传对象
            UploadManager uploadManager = new UploadManager();

            //调用put方法上传
            Response res = uploadManager.put(filePath, key, getSimpleUpToken(auth));
            //打印返回的信息
            String response = (res.bodyString());
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(new StringReader(response));
            JsonObject object = element.getAsJsonObject();
            if(object!=null){
                JsonElement hashE = object.get("hash");
                JsonElement keyE =object.get("key");
                result.put("hash",hashE.getAsString());
                result.put("filename",keyE.getAsString());
            }
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return result;
    }

    /**
     * 覆盖上传
     * @throws IOException
     */
    public static Map<String,Object> coverUpload(DispatchContext dcx,Map<String,? extends Object> context) throws IOException {
        Map<String,Object> result = ServiceUtil.returnSuccess();
        String filePath = (String) context.get("filePath");
        String key = (String) context.get("fileKey");
        try {
            //密钥配置
            Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            //创建上传对象
            UploadManager uploadManager = new UploadManager();

            //调用put方法上传，这里指定的key和上传策略中的key要一致
            Response res = uploadManager.put(filePath, key, getCoverUpToken(auth,key));
            //打印返回的信息 ({"hash":"FolTaLh5p8Ce1cAKxoE0B8xygI25","key":"original.png"}
            System.out.println(res.bodyString());
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(new StringReader(res.bodyString()));
            JsonObject object = element.getAsJsonObject();
            if(object!=null){
                JsonElement hashE = object.get("hash");
                JsonElement keyE =object.get("key");
                result.put("hash",hashE.getAsString());
                result.put("filename",keyE.getAsString());
            }
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return result;
    }

    // 断点上传 (需要修改)

    public static Map<String,Object> breakUpload(DispatchContext dcx,Map<String,? extends Object> context) throws IOException{
        String filePath = (String) context.get("filePath");
        String key = (String) context.get("fileKey");
        Map<String,Object> result = ServiceUtil.returnSuccess();
        //设置断点记录文件保存在指定文件夹或的File对象
        String recordPath = filePath;

        try {
            //密钥配置
            Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
            //创建上传对象
            //实例化recorder对象
            Recorder recorder = new FileRecorder(recordPath);
            //实例化上传对象，并且传入一个recorder对象
            UploadManager uploadManager = new UploadManager(recorder);

            //调用put方法上传
            Response res = uploadManager.put(recordPath, key, getBreakUpToken(auth));
            //打印返回的信息
            System.out.println(res.bodyString());

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(new StringReader(res.bodyString()));
            JsonObject object = element.getAsJsonObject();
            if(object!=null){
                JsonElement hashE = object.get("hash");
                JsonElement keyE =object.get("key");
                result.put("hash",hashE.getAsString());
                result.put("filename",keyE.getAsString());
            }
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                //响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return result;
    }

}

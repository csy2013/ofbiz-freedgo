package com.yuaoq.yabiz.daojia.model.json.product.productDetail;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/4.
 */
public class ProductComment {
    public String totalScore;
    public String goodRating;
    public String commentNum;
    public boolean hasMore;
    /**
     * commentName : j***z
     * commentScore : 5
     * commentTime : 2016.07.23 08:04
     * commentDesc : 品质量俱佳，强烈推荐。
     * littleImg : []
     * bigCommentImg : []
     */
    
    public List<CommentList> commentList;
    
    public static ProductComment objectFromData(String str) {
        
        return new Gson().fromJson(str, ProductComment.class);
    }
    
    public static ProductComment objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ProductComment.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ProductComment> arrayProductCommentFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ProductComment>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}

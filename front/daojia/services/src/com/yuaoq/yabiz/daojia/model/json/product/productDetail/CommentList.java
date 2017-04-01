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
public class CommentList {
    public String commentName;
    public String commentScore;
    public String commentTime;
    public String commentDesc;
    public List<?> littleImg;
    public List<?> bigCommentImg;
    
    public CommentList(String commentName, String commentScore, String commentTime, String commentDesc, List<?> littleImg, List<?> bigCommentImg) {
        this.commentName = commentName;
        this.commentScore = commentScore;
        this.commentTime = commentTime;
        this.commentDesc = commentDesc;
        this.littleImg = littleImg;
        this.bigCommentImg = bigCommentImg;
    }
    
    public static CommentList objectFromData(String str) {
        
        return new Gson().fromJson(str, CommentList.class);
    }
    
    public static CommentList objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), CommentList.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<CommentList> arrayCommentListFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<CommentList>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
}

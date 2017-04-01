package com.yuaoq.yabiz.daojia.model.json.common.IndexPage;

import java.util.Map;

/**
 * Created by changsy on 16/8/29.
 */
public class FloorCellData {
    
    public String to;
    /**
     * activityId : 12901
     * imgOrder : 1
     */
    
    public Map params;
    public String imgUrl;
    public String userAction;
    public String title;
    public String words;
    public FloorCellData(String to, Map params, String imgUrl, String userAction) {
        this.to = to;
        this.params = params;
        this.imgUrl = imgUrl;
        this.userAction = userAction;
    }
    public FloorCellData(String to, Map params, String imgUrl, String userAction, String title,String words) {
        this.to = to;
        this.params = params;
        this.imgUrl = imgUrl;
        this.userAction = userAction;
        this.title = title;
        this.words = words;
    }
    public FloorCellData(String to, Map params, String imgUrl, String userAction, String title) {
        this.to = to;
        this.params = params;
        this.imgUrl = imgUrl;
        this.userAction = userAction;
        this.title = title;
    }
}

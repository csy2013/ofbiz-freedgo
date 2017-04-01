package com.yuaoq.yabiz.daojia.model.json.common.act;

/**
 * Created by changsy on 2016/10/19.
 */
public class Act1DataParams {
    
    Long shareId;
    String storeId;
    String url;
    
    public Act1DataParams(Long shareId, String storeId, String url) {
        this.shareId = shareId;
        this.storeId = storeId;
        this.url = url;
    }
}

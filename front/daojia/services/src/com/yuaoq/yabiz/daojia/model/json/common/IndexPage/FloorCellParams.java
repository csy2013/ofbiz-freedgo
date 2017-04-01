package com.yuaoq.yabiz.daojia.model.json.common.IndexPage;

/**
 * Created by changsy on 16/8/29.
 */
public class FloorCellParams {
    public String activityId;
    public String imgOrder;
    public String channelId;
    public FloorCellParams(String activityId, String imgOrder) {
        this.activityId = activityId;
        this.imgOrder = imgOrder;
        
    }
    public FloorCellParams(String channelId) {
        this.channelId = channelId;
    }
    
}

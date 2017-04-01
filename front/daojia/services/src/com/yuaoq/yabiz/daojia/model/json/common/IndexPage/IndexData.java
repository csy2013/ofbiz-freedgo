package com.yuaoq.yabiz.daojia.model.json.common.IndexPage;

import java.util.List;

/**
 * Created by changsy on 16/8/29.
 */
public class IndexData {
    
    
    public String floorStyle;
    public int index;
    public long timer;
    public String styleTpl;
    public boolean edge;
    public boolean channelPage;
    /**
     * floorCellData : {"to":"activityDetail","params":{"activityId":"12901","imgOrder":"1"},"imgUrl":"https://img30.360buyimg.com/mobilecms/jfs/t3142/282/983539982/95103/ba5d1434/57c3a01cN494a99be.jpg","userAction":"{\"imgUrl\":\"https://img30.360buyimg.com/mobilecms/jfs/t3142/282/983539982/95103/ba5d1434/57c3a01cN494a99be.jpg\",\"activityId\":\"12901\",\"floorStyle \":\"banner\",\"index\":1,\"imgOrder\":\"1\"}"}
     * floorCellType : common
     */
    
    public List<IndexSubData> data;
    
    
    public IndexData(String floorStyle, int index, long timer, String styleTpl, boolean edge, boolean channelPage, List<IndexSubData> data) {
        this.floorStyle = floorStyle;
        this.index = index;
        this.timer = timer;
        this.styleTpl = styleTpl;
        this.edge = edge;
        this.channelPage = channelPage;
        this.data = data;
    }
}

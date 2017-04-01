package com.yuaoq.yabiz.daojia.model.json.common.IndexPage;

/**
 * Created by changsy on 16/8/29.
 */
public class IndexSubData {
    
    /**
     * to : activityDetail
     * params : {"activityId":"12901","imgOrder":"1"}
     * imgUrl : https://img30.360buyimg.com/mobilecms/jfs/t3142/282/983539982/95103/ba5d1434/57c3a01cN494a99be.jpg
     * userAction : {"imgUrl":"https://img30.360buyimg.com/mobilecms/jfs/t3142/282/983539982/95103/ba5d1434/57c3a01cN494a99be.jpg","activityId":"12901","floorStyle ":"banner","index":1,"imgOrder":"1"}
     */
    
    public FloorCellData floorCellData;
    public String floorCellType;
    public String seckillType;
    public SecKillData dataObj;
    
    public IndexSubData(FloorCellData floorCellData, String floorCellType) {
        this.floorCellData = floorCellData;
        this.floorCellType = floorCellType;
        
    }
    
    public IndexSubData(String floorCellType, String seckillType, SecKillData dataObj) {
        this.floorCellType = floorCellType;
        this.seckillType = seckillType;
        this.dataObj = dataObj;
    }
}

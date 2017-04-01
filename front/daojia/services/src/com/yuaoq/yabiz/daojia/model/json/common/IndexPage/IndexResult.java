package com.yuaoq.yabiz.daojia.model.json.common.IndexPage;

import java.util.List;

/**
 * Created by changsy on 16/8/29.
 */
public class IndexResult {
    
    /**
     * tel : 4000-020-020
     * pageSize : 16
     * recommend : false
     * storeConfig : {"pageSize":10,"recommendStore":true}
     */
    
    public IndexConfig config;
    public boolean login;
    /**
     * city : 南京市
     * longitude : 118.696
     * latitude : 32.164
     * areaCode : 904
     * districtCode : 50647
     * address : 江苏省南京市浦口区浦泗路
     * district : 浦口区
     * title : 国电南京自动化公司
     * adcode : 320111
     */
    
    public LbsAddress lbsAddress;
    /**
     * floorStyle : banner
     * floorTitle : {}
     * data : [{"floorCellData":{"to":"activityDetail","params":{"activityId":"12901","imgOrder":"1"},"imgUrl":"https://img30.360buyimg.com/mobilecms/jfs/t3142/282/983539982/95103/ba5d1434/57c3a01cN494a99be.jpg","userAction":"{\"imgUrl\":\"https://img30.360buyimg.com/mobilecms/jfs/t3142/282/983539982/95103/ba5d1434/57c3a01cN494a99be.jpg\",\"activityId\":\"12901\",\"floorStyle \":\"banner\",\"index\":1,\"imgOrder\":\"1\"}"},"floorCellType":"common"},{"floorCellData":{"to":"activityDetail","params":{"activityId":"13616","imgOrder":"2"},"imgUrl":"https://img30.360buyimg.com/mobilecms/jfs/t3094/312/816329795/101297/46b9b28b/57bfaaa0N567cb14b.jpg","userAction":"{\"imgUrl\":\"https://img30.360buyimg.com/mobilecms/jfs/t3094/312/816329795/101297/46b9b28b/57bfaaa0N567cb14b.jpg\",\"activityId\":\"13616\",\"floorStyle \":\"banner\",\"index\":1,\"imgOrder\":\"2\"}"},"floorCellType":"common"},{"floorCellData":{"to":"activityDetail","params":{"activityId":"16096","imgOrder":"3"},"imgUrl":"https://img30.360buyimg.com/mobilecms/jfs/t3235/222/677017513/83899/557fc087/57bd3ba0Na6e4f979.jpg","userAction":"{\"imgUrl\":\"https://img30.360buyimg.com/mobilecms/jfs/t3235/222/677017513/83899/557fc087/57bd3ba0Na6e4f979.jpg\",\"activityId\":\"16096\",\"floorStyle \":\"banner\",\"index\":1,\"imgOrder\":\"3\"}"},"floorCellType":"common"}]
     * index : 1
     * timer : 1468497522000
     * styleTpl : tpl1
     * edge : false
     * channelPage : false
     */
    
    public List<Object> data;
    public IndexResult(IndexConfig config, boolean login, LbsAddress lbsAddress, List<Object> data) {
        this.config = config;
        this.login = login;
        this.lbsAddress = lbsAddress;
        this.data = data;
    }
    
}


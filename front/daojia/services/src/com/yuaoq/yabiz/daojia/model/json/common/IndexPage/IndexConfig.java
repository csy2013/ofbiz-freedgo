package com.yuaoq.yabiz.daojia.model.json.common.IndexPage;

import com.yuaoq.yabiz.daojia.model.json.common.StoreConfig;

/**
 * Created by changsy on 16/8/29.
 */
public class IndexConfig {
    
    public String tel;
    public int pageSize;
    public boolean recommend;
    /**
     * pageSize : 10
     * recommendStore : true
     */
    
    
    public StoreConfig storeConfig;
    
    public IndexConfig(String tel, int pageSize, boolean recommend, StoreConfig storeConfig) {
        this.tel = tel;
        this.pageSize = pageSize;
        this.recommend = recommend;
        this.storeConfig = storeConfig;
    }
    
    
}

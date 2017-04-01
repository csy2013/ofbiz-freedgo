package com.yuaoq.yabiz.daojia.model.json.common;

/**
 * Created by changsy on 16/8/29.
 */
public class StoreConfig {
    
    public int pageSize;
    public boolean recommendStore;
    public StoreConfig(int pageSize, boolean recommendStore) {
        this.pageSize = pageSize;
        this.recommendStore = recommendStore;
    }
}

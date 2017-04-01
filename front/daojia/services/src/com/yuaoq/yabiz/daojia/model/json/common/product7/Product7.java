package com.yuaoq.yabiz.daojia.model.json.common.product7;

import com.yuaoq.yabiz.daojia.model.json.common.product5.BusyAttrMaps;
import com.yuaoq.yabiz.daojia.model.json.common.product5.FloorTitle;
import com.yuaoq.yabiz.daojia.model.json.common.product5.Product5Data;

import java.util.List;

/**
 * Created by changsy on 2016/10/19.
 */
public class Product7 {
    
    public String floorStyle;
    public FloorTitle floorTitle;
    public boolean edge;
    public String styleTpl;
    public int index;
    public long timer;
    public boolean channelPage;
    public List<Product7Data> data;
    
    public Product7(String floorStyle, FloorTitle floorTitle, boolean edge, String styleTpl, int index, long timer, boolean channelPage, List<Product7Data> data) {
        this.floorStyle = floorStyle;
        this.floorTitle = floorTitle;
        this.edge = edge;
        this.styleTpl = styleTpl;
        this.index = index;
        this.timer = timer;
        this.channelPage = channelPage;
        this.data = data;
    }
}

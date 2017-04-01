package com.yuaoq.yabiz.daojia.model.json.product.productSearch;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuaoq.yabiz.daojia.model.json.BaseResult;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changsy on 16/9/2.
 */
public class ProductSearch extends BaseResult {
    
    
    /**
     * id : productsearch.search
     * code : 0
     * msg : 成功
     * detail : null
     * result : {"solution":"chengdu_divide","templeType":"3","showCart":true,"showSearch":true,"searchResultVOList":[{"skuId":"2002983434","skuName":"蒜泥青菜","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2644/240/3255427831/335234/2ae5c72e/57873456N906264e0.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":965,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983434\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"5.00","mkPrice":"暂无报价","realTimePrice":"5.00","promotion":1},{"skuId":"2002983316","skuName":"肉沫海带丝","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2488/107/131585626/370207/5b18ebcc/55f139cdN08560b96.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":987,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983316\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"5.00","mkPrice":"暂无报价","realTimePrice":"5.00","promotion":1},{"skuId":"2002983291","skuName":"炒猪柳","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2665/40/3205572354/291786/754c2269/578727e1N3710f415.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":983,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983291\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"10.00","mkPrice":"暂无报价","realTimePrice":"10.00","promotion":1},{"skuId":"2002983289","skuName":"豌豆虾仁","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2653/342/3298931446/241176/27a563fd/5787279aN754ca05b.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":976,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983289\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"12.00","mkPrice":"暂无报价","realTimePrice":"12.00","promotion":1},{"skuId":"2002983237","skuName":"三杯鸡","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2581/2/3238225444/295933/8b010bde/578720a7Nd12638a4.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":962,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983237\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"13.00","mkPrice":"暂无报价","realTimePrice":"13.00","promotion":1},{"skuId":"2002983437","skuName":"肉沫蒸鸡蛋","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2986/266/1590453768/119301/fc0a6ee0/57873498Nb5982492.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":989,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983437\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"5.00","mkPrice":"暂无报价","realTimePrice":"5.00","promotion":1},{"skuId":"2002983436","skuName":"卤豆腐","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2881/340/3303629981/263370/d36812dc/5787347aN1f451190.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":966,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983436\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"4.00","mkPrice":"暂无报价","realTimePrice":"4.00","promotion":1},{"skuId":"2002983419","skuName":"草菇烧木耳","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2665/53/3253184669/298329/2e202583/578733a8N767002fe.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":963,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983419\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"6.00","mkPrice":"暂无报价","realTimePrice":"6.00","promotion":1},{"skuId":"2002983311","skuName":"包菜粉丝","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2680/60/3262640688/251293/87e4d8fd/57872931N3a867c8d.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":938,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983311\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"5.00","mkPrice":"暂无报价","realTimePrice":"5.00","promotion":1}],"page":1,"count":19,"storeId":"11027324","storeName":"苏客中式餐饮-华侨城店","orgCode":"77458","user_action":"{\"key\":\"\",\"query\":{\"catId\":\"4135633\",\"key\":\"\",\"page\":1,\"pageSize\":10,\"promotLable\":\"\",\"sortType\":\"sort_default\",\"storeId\":\"11027324\"},\"skunum\":\"19\",\"storeid\":\"11027324\"}","onlinedebugId":null,"promptWord":null,"promptTips":"","anchoredProduct":{"skuId":"2002983426","skuName":"干煸四季豆","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":980,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":null,"catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"8.00","mkPrice":"暂无报价","realTimePrice":"8.00","promotion":1},"storePageType":"2"}
     * success : true
     */
    
    public Object detail;
    /**
     * solution : chengdu_divide
     * templeType : 3
     * showCart : true
     * showSearch : true
     * searchResultVOList : [{"skuId":"2002983434","skuName":"蒜泥青菜","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2644/240/3255427831/335234/2ae5c72e/57873456N906264e0.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":965,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983434\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"5.00","mkPrice":"暂无报价","realTimePrice":"5.00","promotion":1},{"skuId":"2002983316","skuName":"肉沫海带丝","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2488/107/131585626/370207/5b18ebcc/55f139cdN08560b96.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":987,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983316\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"5.00","mkPrice":"暂无报价","realTimePrice":"5.00","promotion":1},{"skuId":"2002983291","skuName":"炒猪柳","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2665/40/3205572354/291786/754c2269/578727e1N3710f415.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":983,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983291\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"10.00","mkPrice":"暂无报价","realTimePrice":"10.00","promotion":1},{"skuId":"2002983289","skuName":"豌豆虾仁","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2653/342/3298931446/241176/27a563fd/5787279aN754ca05b.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":976,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983289\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"12.00","mkPrice":"暂无报价","realTimePrice":"12.00","promotion":1},{"skuId":"2002983237","skuName":"三杯鸡","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2581/2/3238225444/295933/8b010bde/578720a7Nd12638a4.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":962,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983237\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"13.00","mkPrice":"暂无报价","realTimePrice":"13.00","promotion":1},{"skuId":"2002983437","skuName":"肉沫蒸鸡蛋","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2986/266/1590453768/119301/fc0a6ee0/57873498Nb5982492.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":989,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983437\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"5.00","mkPrice":"暂无报价","realTimePrice":"5.00","promotion":1},{"skuId":"2002983436","skuName":"卤豆腐","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2881/340/3303629981/263370/d36812dc/5787347aN1f451190.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":966,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983436\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"4.00","mkPrice":"暂无报价","realTimePrice":"4.00","promotion":1},{"skuId":"2002983419","skuName":"草菇烧木耳","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2665/53/3253184669/298329/2e202583/578733a8N767002fe.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":963,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983419\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"6.00","mkPrice":"暂无报价","realTimePrice":"6.00","promotion":1},{"skuId":"2002983311","skuName":"包菜粉丝","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2680/60/3262640688/251293/87e4d8fd/57872931N3a867c8d.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":938,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":"{\"sku_id\":\"2002983311\",\"solution\":\"chengdu_divide\",\"store_id\":\"11027324\"}","catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"5.00","mkPrice":"暂无报价","realTimePrice":"5.00","promotion":1}]
     * page : 1
     * count : 19
     * storeId : 11027324
     * storeName : 苏客中式餐饮-华侨城店
     * orgCode : 77458
     * user_action : {"key":"","query":{"catId":"4135633","key":"","page":1,"pageSize":10,"promotLable":"","sortType":"sort_default","storeId":"11027324"},"skunum":"19","storeid":"11027324"}
     * onlinedebugId : null
     * promptWord : null
     * promptTips :
     * anchoredProduct : {"skuId":"2002983426","skuName":"干煸四季豆","imgUrl":"https://img30.360buyimg.com/n2//jfs/t2791/111/3271013738/222926/462ed8f5/578733feN01340fe7.jpg","storeId":"11027324","orgCode":"77458","fixedStatus":true,"preName":null,"incart":false,"incartCount":0,"stockCount":980,"tags":[],"miaoshaInfo":{"miaoshaRemainTime":null,"miaoshaRemainName":null,"syntime":null,"words":null,"status":null,"miaosha":false,"button":"","miaoShaSate":0},"buriedPoint":null,"saleStatus":true,"showCartButton":true,"userActionSku":null,"catId":"","standard":"","funcIndicatins":"","aging":"-1","venderId":"77458","basicPrice":"8.00","mkPrice":"暂无报价","realTimePrice":"8.00","promotion":1}
     * storePageType : 2
     */
    
    public SearchResult result;
    
    public ProductSearch(String id, String code, String msg, boolean success) {
        super(id, code, msg, success);
    }
    
    public ProductSearch(String id, String code, String msg, boolean success, Object detail, SearchResult result) {
        super(id, code, msg, success);
        this.detail = detail;
        this.result = result;
    }
    
    public ProductSearch(String id, String code, String msg, boolean success, Object detail) {
        super(id, code, msg, success);
        this.detail = detail;
    }
    
    public static ProductSearch objectFromData(String str) {
        
        return new Gson().fromJson(str, ProductSearch.class);
    }
    
    public static ProductSearch objectFromData(String str, String key) {
        
        try {
            JSONObject jsonObject = new JSONObject(str);
            
            return new Gson().fromJson(jsonObject.getString(str), ProductSearch.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static List<ProductSearch> arrayProductSearchFromData(String str) {
        
        Type listType = new TypeToken<ArrayList<ProductSearch>>() {
        }.getType();
        
        return new Gson().fromJson(str, listType);
    }
    
    
}

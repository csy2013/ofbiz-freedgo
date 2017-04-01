 1、页面模板：
1、floorStyle:

ball： 首页球图 ,tlp1,
banner： 轮播图,tlp1;ball 球,tlp2:正方形
floorBanner： 多个图片不轮播，需要手动

"act1" : 1个活动图片  tpl5
"act2" ：一行2个栏目 tlp1
"act3"：一行3个栏目 tlp1
"product5"  tlp3
"product5" ："floorTitle": {
              "floorName": "附近商家"
            },
product6:styleTpl=='tpl1'通过floorName 传入
product7： styleTpl=='tpl1' 通过floorName 传入 tlp1
product2：附件外卖
product3：无

2、styleTpl
tpl1,tpl2,tpl3,tpl4,tpl5,tpl6,tpl7

ball：   tlp1,
banner：   tlp1;ball 球,tlp2:正方形
floorBanner：  tlp1

"act1" : 1个活动图片  tlp1,tlp2,tpl5,tpl6,tpl7
"act2" ：一行2个栏目 tlp1,tlp2,tpl5,tpl6,tpl7
"act3"：一行3个栏目 tlp1,tlp2,tpl5,tpl6,tpl7
"product5"  tlp3
"product5" ： tlp2,tlp3 时可以加 busyAttrMaps 商品信息
product6:  styleTpl=='tpl1'通过floorName 传入
product7： styleTpl=='tpl1' 通过floorName 传入 tlp1 ,商家信息
product2：附件外卖
product3：无

图片的to地址

				web: e,
                channelPage: "#channelPage/channelId:" + b.channelId + "/channelName:" + encodeURIComponent(c),
                wmdj: "#restaurant",
                storeList: "#storeList/storetypeId:" + (b.venderIndustryType || ""),
                outPlat: 1 == b.busyNum &&
                "#daojialist" || 2 == b.busyNum && "#elemelist",
                storeListByKey: "javascript:void 0;",
                store: "#storeHome/storeId:" + b.storeId + "/orgCode:" + b.orgCode,
                productDetail: "#goodsDetails/skuId:" + b.skuId + "/storeId:" + b.storeId + "/orgCode:" + b.orgCode,
                activityDetail: "#activityPage/actNativeId:" + b.activityId,
                activityBusy: "#activityPage/actNativeId:" + b.activityId + "/storeId:" + b.storeId + "/activityBusy:true",
                fightGroup: "#pintuanList",
                home: "#index",
                msdjStore: "#daojiamenu/rid:" + b.restaurantId + "/title:" + encodeURIComponent(b.restaurantName)



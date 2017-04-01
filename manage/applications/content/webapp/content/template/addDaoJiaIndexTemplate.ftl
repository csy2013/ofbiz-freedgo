<!-- Bootstrap -->


<link href="/images/themes/common/common.css" rel="stylesheet">
<link href="/images/themes/common/daojiaIndex.css" rel="stylesheet">
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- Include all compiled plugins (below), or include individual files as needed -->


<script type="text/javascript" src="/images/jquery/plugins/artDialog/artDialog.source.js"></script>
<script type="text/javascript" src="/images/jquery/plugins/artDialog/iframeTools.js"></script>
<script type="text/javascript" src="/images/themes/common/sidebar-roll.js"></script>
<script src="/images/themes/common/Swipe7.js"></script>


<!-- 内连接 -->
<!--<script src="./common/inner_join.js"></script>-->
<script type="text/javascript">
    $(function () {
        //初始化xml
        initXml();

        //设置轮播广告图片选择框
        $("#rollAdvChoose").click(function () {
            var num = 5 - $(".slide").length;
            if (num > 0) {
                art.dialog.open('<@ofbizUrl>fileChoose</@ofbizUrl>', {
                    id: 'rollAdv',
                    lock: true,
                    width: '800px',
                    height: '600px',
                    title: '选择图片',
                    top: '30%',
                    fixed: true
                });
            } else {
                toastr.warning('轮播广告已满5张!');
            }
        });

        queryMobProduct(1);
        queryMobStore(1);
        queryMobActivity(1);
        queryMobSubject(1);
    });
    //查找商品


</script>
<!-- 加载xml和xsl结束 -->


<script type="text/javascript">
    $(function () {
        $('.swipe7').each(function () {
            Swipe7(this, {auto: 3300});
        })
    })

</script>

<div class="page_body container-fluid">
    <div class="row">
        <div class="main">
            <div class="main_cont" style="height: 800px;">
                <div class="common_data" style="padding-left:100px;">
                    <!-- 首页内容 -->
                    <div class="col-sm-10 col-md-11" style="float:none;">
                        <div class="edit_box">
                            <div class="ip_pre">
                                <div class="ip_head"></div>
                                <div class="ip_body">
                                    <div class="wx_head">
                                        <h1>
                                            <span onclick="showBasicEdit()">基本信息编辑</span>
                                        </h1>
                                    </div>
                                    <div daojia="">
                                        <section class="current">
                                            <article class="scrolling">
                                                <div class="scroller">
                                                    <div class="be hasBottom" style="min-height:684px" node-type="index-page">
                                                        <div id="ip_cont" class="z ip_cont ui-sortable">
                                                            <div class="app_item app_cube">
                                                                <div id="index_1" class="app_cont item swipe7 a0 am">
                                                                    <ul class="swipe7list">
                                                                        <li class="swipe7item"
                                                                            onclick=""
                                                                            correct-index="0"><a href="#activityPage/actNativeId:13616">
                                                                            <img src="https://img30.360buyimg.com/mobilecms/jfs/t3082/43/2941714439/67876/aed77d9/57ea2044Ne7c9a8c1.jpg"
                                                                                 onerror=""></a>
                                                                        </li>
                                                                        <li class="swipe7item"
                                                                            onclick=""
                                                                            correct-index="1"><a href="#activityPage/actNativeId:13616">
                                                                            <img src="https://img30.360buyimg.com/mobilecms/jfs/t3082/43/2941714439/67876/aed77d9/57ea2044Ne7c9a8c1.jpg"
                                                                                 onerror=""></a>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                                <div class="app_edit" style="width: 100%; height: 100%;">
                                                                    <div class="app_btns">
                                                                        <a href="javascript:" class="edit" onclick="updateRollAdv('1')">
                                                                            编辑
                                                                        </a>
                                                                        <a href="javascript:" class="delete" onclick="delRollAdv('1')">删除</a>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="d5">
                                                                <div class="d2"><i class="d3 "></i></div>
                                                                <div class="d6">
                                                                    <div class="d7 location"><p>弘阳·旭日华庭(西区)</p><i class="cp cn"></i></div>
                                                                </div>
                                                            </div>
                                                            <style>
                                                                .a2 a {
                                                                    height: 90px;
                                                                }
                                                            </style>
                                                            <div class="app_item app_cube">
                                                                <div id="index_2" class="app_cont item a2 ballNum5">
                                                                    <a class="" href="#channelPage/channelId:3983/channelName:%E8%B6%85%E5%B8%82%E7%94%9F%E9%B2%9C"
                                                                       clstag="pageclick|keycount|home_click_channel_20160714_1"><img
                                                                            src="https://img30.360buyimg.com/mobilecms/jfs/t3259/199/3210611413/6807/548fc63/57ede34dN9112b163.png"
                                                                    >超市生鲜</a><a
                                                                        class=""
                                                                        href="#channelPage/channelId:8/channelName:%E5%A4%96%E5%8D%96%E7%BE%8E%E9%A3%9F"
                                                                        clstag="pageclick|keycount|home_click_channel_20160714_2"><img
                                                                        src="https://img30.360buyimg.com/mobilecms/jfs/t3190/43/3202516813/7039/457efda1/57ede3dcNa2d45e25.png"
                                                                >外卖美食</a><a
                                                                        class=""
                                                                        href="#channelPage/channelId:1118/channelName:%E9%B2%9C%E8%8A%B1%E8%9B%8B%E7%B3%95"
                                                                        clstag="pageclick|keycount|home_click_channel_20160714_3"><img
                                                                        src="https://img30.360buyimg.com/mobilecms/jfs/t3145/73/3195945826/8636/231e7f27/57ede4a0N8e41e5cb.png"
                                                                >鲜花蛋糕</a><a
                                                                        class=""
                                                                        href="#channelPage/channelId:9/channelName:%E4%B8%8A%E9%97%A8%E6%9C%8D%E5%8A%A1"
                                                                        clstag="pageclick|keycount|home_click_channel_20160714_4"><img
                                                                        src="https://img30.360buyimg.com/mobilecms/jfs/t3268/347/3185041054/7627/6fc68446/57ede4e5N1761467c.png"
                                                                >上门服务</a><a
                                                                        class=""
                                                                        href="#channelPage/channelId:7/channelName:%E5%8C%BB%E8%8D%AF%E5%81%A5%E5%BA%B7"
                                                                        clstag="pageclick|keycount|home_click_channel_20160714_5"><img
                                                                        src="https://img30.360buyimg.com/mobilecms/jfs/t3202/365/3278461779/6959/db1c405d/57ede520Nea5bca7a.png"
                                                                >医药健康</a>
                                                                </div>
                                                                <div class="app_edit" style="width: 100%; height: 100%;">
                                                                    <div class="app_btns">
                                                                        <a href="javascript:" class="edit" onclick="updateRollAdv('2')">
                                                                            编辑
                                                                        </a>
                                                                        <a href="javascript:" class="delete" onclick="delRollAdv('2')">删除</a>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="app_item app_cube">
                                                                <div id="index_3" class="app_cont item swipe7 bd am a1">
                                                                    <ul class="swipe7list">
                                                                        <li class="swipe7item">
                                                                            <a
                                                                                    href="https://daojia.jd.com/activity/reportActivity/index.html?tbactId=18132&amp;cityId=904&amp;city=%E5%8D%97%E4%BA%AC%E5%B8%82&amp;lng=118.72342&amp;lat=32.13103">
                                                                                <img
                                                                                        src="https://img30.360buyimg.com/mobilecms/jfs/t3148/18/3032631079/36914/d779d3be/57eb5f33N7ea3150d.jpg"
                                                                                ></a>
                                                                        </li>

                                                                    </ul>
                                                                </div>
                                                                <div class="app_edit" style="width: 100%; height: 100%;">
                                                                    <div class="app_btns">
                                                                        <a href="javascript:" class="edit" onclick="updateRollAdv('3')">
                                                                            编辑
                                                                        </a>
                                                                        <a href="javascript:" class="delete" onclick="delRollAdv('3')">删除</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="app_item app_cube">
                                                                <div id="index_4" class="app_cont item">
                                                                    <ul class="triple-floor-3 a1">
                                                                        <li class="miaoSha num1 tripleMiaoSha"><a
                                                                                href="#grabList/longitude:118.72342/latitude:32.13103/city:%E5%8D%97%E4%BA%AC%E5%B8%82"><span
                                                                                class="cu"><i
                                                                                class="triple-icon3 num1"></i>限时秒杀</span><b class="b3" node-type="r1" data-remain="3559"
                                                                                                                            data-address="{&quot;city&quot;:&quot;南京市&quot;,&quot;longitude&quot;:118.72342,&quot;latitude&quot;:32.13103,&quot;areaCode&quot;:904,&quot;districtCode&quot;:50647,&quot;address&quot;:&quot;江苏省南京市浦口区大桥北路9号丽岛路&quot;,&quot;district&quot;:&quot;浦口区&quot;,&quot;title&quot;:&quot;弘阳·旭日华庭(西区)&quot;,&quot;adcode&quot;:&quot;320111&quot;,&quot;cityId&quot;:904}"
                                                                                                                            data-syntime="60" data-grab="true"
                                                                                                                            data-flag="4"><i>00</i>:<i>58</i>:<i>22</i></b><span
                                                                                class="cs"><img class=""
                                                                                                src="https://img30.360buyimg.com/n2/jfs/t2989/190/482155352/453577/989cff4b/575ccf6fNe1f15319.jpg"
                                                                                                onerror="this.src=;&quot;//static-o2o.360buyimg.com/daojia/new/images/show_default.png&quot;"><img
                                                                                class="bf"
                                                                                src="https://img30.360buyimg.com/n2/jfs/t1561/256/1048784921/222207/aab6fabb/55b9f633Nae7c7ab2.jpg"
                                                                                onerror="this.src=;&quot;//static-o2o.360buyimg.com/daojia/new/images/show_default.png&quot;"><img
                                                                                class=""
                                                                                src="https://img30.360buyimg.com/n2/jfs/t1483/361/1094578038/59244/5575a66b/55e029deNd4ea9932.jpg"
                                                                                onerror="this.src=;&quot;//static-o2o.360buyimg.com/daojia/new/images/show_default.png&quot;"></span></a>
                                                                        </li>
                                                                        <li class="common num2"><a href="#activityPage/actNativeId:14929"
                                                                                                   onclick="lg('user_action','1.0',{&quot;imgUrl&quot;:&quot;https://img30.360buyimg.com/mobilecms/jfs/t2836/208/3850888587/14068/3a50b58/579b1725N489aa40a.png&quot;,&quot;activityId&quot;:&quot;14929&quot;,&quot;floorStyle &quot;:&quot;act3&quot;,&quot;index&quot;:5})"><span
                                                                                class="cu"><i class="triple-icon3 num2"></i>一毛疯抢</span><span class="ct">运费0元起</span><img
                                                                                src="https://img30.360buyimg.com/mobilecms/jfs/t2836/208/3850888587/14068/3a50b58/579b1725N489aa40a.png"
                                                                                onerror="this.src=;&quot;//static-o2o.360buyimg.com/daojia/new/images/show_default.png&quot;"></a>
                                                                        </li>
                                                                        <li class="common num3"><a
                                                                                href="https://daojia.jd.com/activity/specialActivity/index.html?needPin=yes&amp;busyactId=14960&amp;cityId=904&amp;city=%E5%8D%97%E4%BA%AC%E5%B8%82&amp;lng=118.72342&amp;lat=32.13103"
                                                                                onclick="lg('user_action','1.0',{&quot;imgUrl&quot;:&quot;https://img30.360buyimg.com/mobilecms/jfs/t3019/180/441649800/3717/962cbcce/57a010b4Nce613f1f.png&quot;,&quot;floorStyle &quot;:&quot;act3&quot;,&quot;index&quot;:5,&quot;shareId&quot;:1475376575829,&quot;url&quot;:&quot;https://daojia.jd.com/activity/specialActivity/index.html?needPin=yes&amp;busyactId=14960&quot;})"><span
                                                                                class="cu"><i class="triple-icon3 num3"></i>美食满立减</span><span class="ct">半价享不停</span><img
                                                                                src="https://img30.360buyimg.com/mobilecms/jfs/t3019/180/441649800/3717/962cbcce/57a010b4Nce613f1f.png"
                                                                                onerror="this.src=;&quot;//static-o2o.360buyimg.com/daojia/new/images/show_default.png&quot;"></a>
                                                                        </li>
                                                                    </ul>
                                                                </div>
                                                                <div class="app_edit" style="width: 100%; height: 100%;">
                                                                    <div class="app_btns">
                                                                        <a href="javascript:" class="edit" onclick="updatemf(4)"> 编辑</a>
                                                                        <a href="javascript:" class="delete" onclick="delmf(4)">删除</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="app_item app_cube">
                                                                <div id="index_5" class="app_cont item ba">
                                                                    <div class="bb entrance-total2"><a class="bc entrance-num0 k" href="#activityPage/actNativeId:15300"
                                                                                                       onclick="lg('user_action','1.0',{&quot;imgUrl&quot;:&quot;https://img30.360buyimg.com/mobilecms/jfs/t3178/272/2645199883/5935/3662db2/57e4c342N6aa369f7.png&quot;,&quot;activityId&quot;:&quot;15300&quot;,&quot;floorStyle &quot;:&quot;act2&quot;,&quot;index&quot;:6})"><span>新人特权</span>
                                                                        <span class="subtitle">50元红包</span><img
                                                                                src="https://img30.360buyimg.com/mobilecms/jfs/t3178/272/2645199883/5935/3662db2/57e4c342N6aa369f7.png"
                                                                                onerror="this.src=;&quot;//static-o2o.360buyimg.com/daojia/new/images/show_default.png&quot;"></a><a
                                                                            class="bc entrance-num1 " href="#storeHome/storeId:10055645/orgCode:74597"
                                                                            onclick="lg('user_action','1.0',{&quot;imgUrl&quot;:&quot;https://img30.360buyimg.com/mobilecms/jfs/t2791/275/3213895803/3615/5fe01608/5785b0f6Nede7fa41.png&quot;,&quot;floorStyle &quot;:&quot;act2&quot;,&quot;index&quot;:6,&quot;orgCode&quot;:&quot;74597&quot;,&quot;storeId&quot;:&quot;10055645&quot;})"><span>永辉超市</span>
                                                                        <span class="subtitle">天天平价</span><img
                                                                                src="https://img30.360buyimg.com/mobilecms/jfs/t2791/275/3213895803/3615/5fe01608/5785b0f6Nede7fa41.png"
                                                                                onerror="this.src=;&quot;//static-o2o.360buyimg.com/daojia/new/images/show_default.png&quot;"></a>
                                                                    </div>
                                                                </div>
                                                                <div class="app_edit" style="width: 100%; height: 100%;">
                                                                    <div class="app_btns">
                                                                        <a href="javascript:" class="edit" onclick="updateRollAdv('5')">
                                                                            编辑
                                                                        </a>
                                                                        <a href="javascript:" class="delete" onclick="delRollAdv('5')">删除</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="app_item app_cube">

                                                                <div id="index_6" class="app_cont item swipe7 bd am a1" swipe="">
                                                                    <ul class="swipe7list" style="width: 400%; left: -100%;">
                                                                        <li class="swipe7item"
                                                                            onclick="lg(&quot;user_action&quot;,&quot;1.0&quot;,{&quot;click_id&quot;:&quot;home_click_active_20160714_1&quot;,&quot;imgUrl&quot;:&quot;https://img30.360buyimg.com/mobilecms/jfs/t3049/332/3201891224/34073/e9e71d7a/57eda5d3N6209e41e.jpg&quot;,&quot;floorStyle &quot;:&quot;floorBanner&quot;,&quot;index&quot;:100,&quot;shareId&quot;:1475376575829,&quot;url&quot;:&quot;https://daojia.jd.com/activity/reportActivity/index.html?tbactId=17612&quot;})"
                                                                            correct-index="0" style="width: 25%;"><a
                                                                                href="https://daojia.jd.com/activity/reportActivity/index.html?tbactId=17612&amp;cityId=904&amp;city=%E5%8D%97%E4%BA%AC%E5%B8%82&amp;lng=118.72342&amp;lat=32.13103"><img
                                                                                src="https://img30.360buyimg.com/mobilecms/jfs/t3049/332/3201891224/34073/e9e71d7a/57eda5d3N6209e41e.jpg"
                                                                                onerror="this.src=;&quot;//static-o2o.360buyimg.com/daojia/new/images/index_banner_default_2.0.png&quot;"></a>
                                                                        </li>
                                                                        <li class="swipe7item"
                                                                            onclick="lg(&quot;user_action&quot;,&quot;1.0&quot;,{&quot;click_id&quot;:&quot;home_click_active_20160714_1&quot;,&quot;imgUrl&quot;:&quot;https://img30.360buyimg.com/mobilecms/jfs/t3196/121/3171313094/38736/d2bfa5b8/57ef7ec6N27ef5799.jpg&quot;,&quot;floorStyle &quot;:&quot;floorBanner&quot;,&quot;index&quot;:100,&quot;shareId&quot;:1475376575829,&quot;url&quot;:&quot;https://daojia.jd.com/activity/reportActivity/index.html?tbactId=17966&quot;})"
                                                                            correct-index="1" style="width: 25%;"><a
                                                                                href="https://daojia.jd.com/activity/reportActivity/index.html?tbactId=17966&amp;cityId=904&amp;city=%E5%8D%97%E4%BA%AC%E5%B8%82&amp;lng=118.72342&amp;lat=32.13103"><img
                                                                                src="https://img30.360buyimg.com/mobilecms/jfs/t3196/121/3171313094/38736/d2bfa5b8/57ef7ec6N27ef5799.jpg"
                                                                                onerror="this.src=;&quot;//static-o2o.360buyimg.com/daojia/new/images/index_banner_default_2.0.png&quot;"></a>
                                                                        </li>
                                                                        <li class="swipe7item"
                                                                            onclick="lg(&quot;user_action&quot;,&quot;1.0&quot;,{&quot;click_id&quot;:&quot;home_click_active_20160714_1&quot;,&quot;imgUrl&quot;:&quot;https://img30.360buyimg.com/mobilecms/jfs/t3049/332/3201891224/34073/e9e71d7a/57eda5d3N6209e41e.jpg&quot;,&quot;floorStyle &quot;:&quot;floorBanner&quot;,&quot;index&quot;:100,&quot;shareId&quot;:1475376575829,&quot;url&quot;:&quot;https://daojia.jd.com/activity/reportActivity/index.html?tbactId=17612&quot;})"
                                                                            correct-index="2" style="width: 25%;"><a
                                                                                href="https://daojia.jd.com/activity/reportActivity/index.html?tbactId=17612&amp;cityId=904&amp;city=%E5%8D%97%E4%BA%AC%E5%B8%82&amp;lng=118.72342&amp;lat=32.13103"><img
                                                                                src="https://img30.360buyimg.com/mobilecms/jfs/t3049/332/3201891224/34073/e9e71d7a/57eda5d3N6209e41e.jpg"
                                                                                onerror="this.src=;&quot;//static-o2o.360buyimg.com/daojia/new/images/index_banner_default_2.0.png&quot;"></a>
                                                                        </li>
                                                                        <li class="swipe7item"
                                                                            onclick="lg(&quot;user_action&quot;,&quot;1.0&quot;,{&quot;click_id&quot;:&quot;home_click_active_20160714_1&quot;,&quot;imgUrl&quot;:&quot;https://img30.360buyimg.com/mobilecms/jfs/t3196/121/3171313094/38736/d2bfa5b8/57ef7ec6N27ef5799.jpg&quot;,&quot;floorStyle &quot;:&quot;floorBanner&quot;,&quot;index&quot;:100,&quot;shareId&quot;:1475376575829,&quot;url&quot;:&quot;https://daojia.jd.com/activity/reportActivity/index.html?tbactId=17966&quot;})"
                                                                            correct-index="3" style="width: 25%;"><a
                                                                                href="https://daojia.jd.com/activity/reportActivity/index.html?tbactId=17966&amp;cityId=904&amp;city=%E5%8D%97%E4%BA%AC%E5%B8%82&amp;lng=118.72342&amp;lat=32.13103"><img
                                                                                src="https://img30.360buyimg.com/mobilecms/jfs/t3196/121/3171313094/38736/d2bfa5b8/57ef7ec6N27ef5799.jpg"
                                                                                onerror="this.src=;&quot;//static-o2o.360buyimg.com/daojia/new/images/index_banner_default_2.0.png&quot;"></a>
                                                                        </li>
                                                                    </ul>
                                                                    <div class="swipe7Indicator"><span class="swipe7IndicatorItem current"></span><span
                                                                            class="swipe7IndicatorItem "></span></div>
                                                                </div>

                                                                <div class="app_edit" style="width: 100%; height: 100%;">
                                                                    <div class="app_btns">
                                                                        <a href="javascript:" class="edit" onclick="updatemf(6)"> 编辑</a>
                                                                        <a href="javascript:" class="delete" onclick="delmf(6)">删除</a>
                                                                    </div>
                                                                </div>
                                                            </div>


                                                        </div>
                                                    </div>
                                            </article>
                                        </section>
                                    </div>
                                </div>
                                <div class="plugin_add">
                                    <h4>添加模块</h4>

                                    <div class="plugin_list container-fluid" style="margin-bottom:30px;">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <a href="javascript:" onclick="showRollAdv()">轮播广告</a>
                                            </div>
                                            <div class="col-xs-6">
                                                <a href="javascript:" class="plugin_cube" onclick="showMF()">魔方</a>
                                            </div>
                                            <div class="col-xs-6">
                                                <a href="javascript:" onclick="showAdv()">图片广告</a>
                                            </div>
                                            <div class="col-xs-6">
                                                <a href="javascript:" onclick="showGoods()">商品</a>
                                            </div>


                                        </div>
                                    </div>
                                </div>
                                <div style="height:50px;"></div>
                            </div>

                            <style>
                                .bottom_bar {
                                    width: 350px;
                                    background: rgba(0, 0, 0, 0.4);
                                    position: fixed;
                                    bottom: 10px;
                                    padding: 5px 20px;
                                    z-index: 9999
                                }
                            </style>

                            <div class="bottom_bar">
                                <button type="button" class="btn btn-primary btn-sm" onclick="saveAllMod();">保存模板</button>
                            </div>


                            <!-- 魔方 -->
                            <div id="cubeEdit" class="edit_area cube_edit" style="display: none;">
                                <div class="arrow"></div>
                                <div class="edit_cont">
                                    <form id="mfForm" role="form" class="form_cube">
                                        <input type="hidden" name="CSRFToken" id="CSRFToken" value="d1982fa9-f063-4662-84b5-9b1182dd997a">
                                        <input type="hidden" name="rsv" value="3d0b57039f9a711b0f46add1982f1200">
                                        <input type="hidden" id="appCountId" name="appCountId" value="">
                                        <input type="hidden" name="merchantId" value="-1">

                                        <div class="form_group cube_box">
                                            <div class="form_cont">
                                                <div class="cube container-fluid">
                                                    <div class="row">
                                                        <div class="col-xs-6 1-1">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 1-2">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 1-3">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 1-4">
                                                            <span></span>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-xs-6 2-1">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 2-2">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 2-3">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 2-4">
                                                            <span></span>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-xs-6 3-1">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 3-2">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 3-3">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 3-4">
                                                            <span></span>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-xs-6 4-1">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 4-2">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 4-3">
                                                            <span></span>
                                                        </div>
                                                        <div class="col-xs-6 4-4">
                                                            <span></span>
                                                        </div>
                                                    </div>
                                                </div>
                                                <p class="text-muted">请点击选择起点，滑动光标选择区域，再次点击确定所选区域，选取过程中可右击取消</p>
                                            </div>
                                        </div>
                                        <div class="app-submit" align="center" style="margin-top: 15px;">
                                            <input id="submitBtn" type="button" onclick="addMF()" value=" 确 定 ">
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <!-- 广告 -->
                            <div id="advEdit" class="edit_area cube_edit" style="display: none;">
                                <div class="arrow"></div>
                                <div class="edit_cont">
                                    <form id="advForm" role="form" class="form_cube">
                                        <input type="hidden" name="CSRFToken" value="d1982fa9-f063-4662-84b5-9b1182dd997a">
                                        <input type="hidden" name="rsv" value="3d0b57039f9a711b0f46add1982f1200">
                                        <input type="hidden" id="imgAdvId" name="imgAdvId" value="">
                                        <input type="hidden" name="merchantId" value="-1">
                                        <img id="advImg" alt="" src="" width="100%">

                                        <div class="imgEdit imgAdvEdit" style="display: block;">
                                            <a href="javascript:" class="img_close" onclick="closeAdvEdit()">
                                                <span class="glyphicon glyphicon-remove"></span>
                                            </a>

                                            <div class="form_group">
                                                <div class="fh">选择图片：</div>
                                                <div class="form_cont fg_box">
                                                    <input class="form_cont advChoose ui-button ui-widget ui-state-default ui-corner-all" type="button" value="选择图片"
                                                           style="margin-bottom: 10px;" role="button" aria-disabled="false">
                                                    <input class="form-control" type="hidden" id="imgAdvSrc" name="imgAdvSrc" value="">
                                                    <span id="imgAdvSrcTip"></span>
                                                </div>
                                            </div>
                                            <div class="form_group fg_box nlink-wp clearfix">
                                                <div class="fh">链接地址：</div>
                                                <div class="form_cont dd-box">
                                                    <!-- <a href="#ctModal" role="button" onclick="md('imgAdvHref')">链接地址</a> -->
                                                    <div class="lk-sel lk-chs"><span class="sel-word">功能链接</span>
                                                        <ul>
                                                            <li class="s-selected"><a data-href="lk-01">功能链接</a></li>
                                                            <li><a data-href="lk-02">关键字</a></li>
                                                        </ul>
                                                    </div>
                                                    <div class="lk-sel gn-chs"><span class="sel-word">--请选择--</span>
                                                        <ul>
                                                            <li class="s-selected"><a>--请选择--</a></li>
                                                            <li><a data-href="choose-goods">分类/商品</a></li>

                                                        </ul>
                                                    </div>

                                                    <input class="form-control custom-input" type="text" id="imgAdvHref" name="imgAdvHref" style="margin-top: 10px;">
                                                    <span id="imgAdvHrefTip"></span>
                                                </div>
                                            </div>
                                            <div class="form_group fg_box nlink-wp clearfix none">
                                                <div class="fh">已选择：</div>
                                                <div class="form_cont dd-box"><span class="sel-tags"></span>
                                                    <a class="del-tags" href="javascript:">删除</a></div>
                                            </div>
                                        </div>
                                        <div class="app-submit" align="center" style="margin-top: 15px;">
                                            <input type="button" onclick="saveAdv()" value=" 确 定 ">
                                        </div>
                                    </form>
                                </div>
                            </div>

                            <!-- 轮播广告 -->
                            <div id="rollAdvEdit" class="edit_area cube_edit" style="display: none;">
                                <div class="arrow"></div>
                                <div class="edit_cont">
                                    <form id="rollAdvForm" action="<@ofbizUrl>createWebSiteTemplate</@ofbizUrl>" role="form" class="form_cube">
                                        <div id="slides" style="margin-bottom:10px;">
                                            <div class="slides_wp clearfix">
                                            </div>
                                            <!--/slides_wp-->
                                            <a id="sd_prev" href="javascript:"></a>
                                            <a id="sd_next" href="javascript:"></a>
                                        </div>
                                        <!--/slides-->
                                        <input class="form_cont" id="rollAdvChoose" type="button" value="添加图片">
                                        <label class="lb-word">点击图片可编辑</label>

                                        <div class="app-submit" align="center" style="margin-top: 15px;">
                                            <input type="button" onclick="saveRollAdv()" value=" 确 定 ">
                                        </div>
                                    </form>

                                </div>
                            </div>
                            <!-- 微信站点设置 -->


                            <!-- 商品 -->
                            <div id="goodsEdit" class="edit_area cube_edit" style="width: 480px; display: none;">
                                <div class="arrow"></div>
                                <div class="edit_cont">
                                    <div class="choose-style">
                                        <form id="goodsForm" action="http://kstore.qianmi.com/boss/saveGoodsMob.htm" method="post">
                                            <input type="hidden" name="CSRFToken" value="d1982fa9-f063-4662-84b5-9b1182dd997a">
                                            <input type="hidden" name="rsv" value="3d0b57039f9a711b0f46add1982f1200">
                                            <input type="hidden" name="merchantId" value="-1">
                                            <input type="hidden" id="goodsmodId" name="goodsmodId" value="">

                                        <#--<div class="style-wp">
                                            <label class="mr20"><input type="radio" name="style" value="gd-01" checked="checked">样式一</label>
                                            <img alt="" src="./common/style_01.jpg" style="width: 50%;">
                                        </div>
                                        <div class="style-wp">
                                            <label class="mr20"><input type="radio" name="style" value="gd-02">样式二</label>
                                            <img alt="" src="./common/style_02.jpg" style="width: 50%;">
                                        </div>
                                        <div class="style-wp">
                                            <label class="mr20"><input type="radio" name="style" value="gd-03">样式三</label>
                                            <img alt="" src="./common/style_03.jpg" style="width: 50%;">
                                        </div>-->

                                            <dl class="choose-goods clearfix">
                                                <dt>选择商品：</dt>
                                                <dd>
                                                    <div class="gds-show"></div>
                                                    <a id="choooseProduct" class="ch-gd ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" href="javascript:"
                                                       role="button" aria-disabled="false"><span class="ui-button-text"><span class="ui-button-text"><i></i></span></span></a>
                                                </dd>
                                            </dl>

                                            <div class="sd_ok app-submit" align="center" style="margin-top: 15px;">
                                                <input type="button" onclick="saveGoods()" value=" 确 定 ">
                                            </div>
                                        </form>
                                    </div>
                                </div>
                                <!--/edit_cont-->
                            </div>
                            <!--/advEdit-->


                        </div>
                    </div>


                </div>

            </div>
        </div>
    </div>


</div>


<style>
    #goodsModal .modal-dialog {
        background: #fff;
        border-radius: 3px;
    }

    #goodsModal .nav-tabs {
        border: 0;
        display: block;
        height: 25px;
        line-height: 25px;
    }

    #goodsModal .nav-tabs > li > a {
        padding: 3px 10px;
        font-size: 14px;
        border: 0;
        border-left: 1px solid #ccc;
    }

    #goodsModal .nav-tabs li:first-child a {
        border-left: 0;
    }

    #goodsModal .nav-tabs > li.active > a {
        font-weight: bold;
    }

    #goodsModal .nav-tabs > li > a:hover {
    }

    #goodsModal .close {
        position: absolute;
        right: 10px;
        top: 20px;
    }

    #subjectModal .modal-dialog {
        background: #fff;
        border-radius: 3px;
    }

    #subjectModal .nav-tabs > li > a {
        padding: 3px 10px;
        font-size: 14px;
        border: 0;
        border-left: 1px solid #ccc;
    }

    #subjectModal .nav-tabs li:first-child a {
        border-left: 0;
    }

    #subjectModal .nav-tabs > li.active > a {
        font-weight: bold;
    }

    #subjectModal .nav-tabs > li > a:hover {
    }

    #subjectModal .close {
        position: absolute;
        right: 10px;
        top: 20px;
    }

    #storeModal .modal-dialog {
        background: #fff;
        border-radius: 3px;
    }

    #storeModal .nav-tabs > li > a {
        padding: 3px 10px;
        font-size: 14px;
        border: 0;
        border-left: 1px solid #ccc;
    }

    #storeModal .nav-tabs li:first-child a {
        border-left: 0;
    }

    #storeModal .nav-tabs > li.active > a {
        font-weight: bold;
    }

    #storeModal .nav-tabs > li > a:hover {
    }

    #storeModal .close {
        position: absolute;
        right: 10px;
        top: 20px;
    }

    #activityModal .modal-dialog {
        background: #fff;
        border-radius: 3px;
    }

    #activityModal .nav-tabs > li > a {
        padding: 3px 10px;
        font-size: 14px;
        border: 0;
        border-left: 1px solid #ccc;
    }

    #activityModal .nav-tabs li:first-child a {
        border-left: 0;
    }

    #activityModal .nav-tabs > li.active > a {
        font-weight: bold;
    }

    #activityModal .nav-tabs > li > a:hover {
    }

    #activityModal .close {
        position: absolute;
        right: 10px;
        top: 20px;
    }


</style>
<div id="goodsModal" class="modal fade">
    <div class="modal-dialog modal-lg">
        <input type="hidden" id="linkId" value="rollImgAdvHref1">
        <div class="tab-content">
            <div class="modal-header">
                <ul class="nav-tabs">
                    <li class="active"><a href="javascript:void(0)" data-toggle="tab" onclick="queryMobProduct(0)" aria-expanded="true">商品列表</a></li>
                </ul><!--/nav-->
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            </div><!--/modal-header-->
            <div class="modal-body" style="max-height:600px;overflow-y: scroll;">
                <table class="ct-tbs w table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>商品标识</th>
                        <th>商品名称</th>
                        <th>商品图片</th>
                        <th>商品描述</th>
                        <th style="width:150px;">
                            <!-- <form class="form-search fr">
                            </form> -->
                            <input type="text" id="chooseGoodsSearch" placeholder="请输入商品名" class="input-medium search-query" style="color:black;">
                            <input type="submit" class="btn btn-default btn-sm" onclick="queryMobProduct()" value="搜索">
                        </th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table><!--/ct-tbs-->
            </div><!--/modal-body-->
            <div class="modal-footer">
                <ul class="pagination pagination-sm pagination-right">

                </ul><!--/pagination-->
            </div><!--/modal-footer-->
        </div><!--/goods-->
    </div><!--/tab-content-->
</div>
<div id="subjectModal" class="modal fade">
    <div class="modal-dialog modal-lg">
        <input type="hidden" id="linkId">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        </div>
        <div class="modal-body" id="subjects" style="max-height:300px;overflow-y: scroll;">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th width="45%">专题标识</th>
                    <th width="45%">专题名称</th>
                    <th>专题描述</th>
                    <th width="22%">
                        <!-- <form class="form-search fr">
                        </form> -->
                        <input type="text" id="chooseSubjectSearch" placeholder="请输入专题名" class="input-medium search-query" style="color:black;">
                        <input type="submit" class="btn btn-default btn-sm" onclick="queryMobSubject(1)" value="搜索">
                    </th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table><!--/ct-tbs-->
        </div><!--/modal-body-->
        <div class="modal-footer">
            <ul class="pagination pagination-sm pagination-right">
            </ul><!--/pagination-->
        </div><!--/modal-footer-->
    </div>
</div>
<div id="activityModal" class="modal fade">
    <div class="modal-dialog modal-lg">
        <input type="hidden" id="linkId">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        </div>
        <div class="modal-body" id="subjects" style="max-height:300px;overflow-y: scroll;">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>活动标识</th>
                    <th>活动名称</th>
                    <th>活动描述</th>
                    <th width="22%">
                        <!-- <form class="form-search fr">
                        </form> -->
                        <input type="text" id="chooseActivitySearch" placeholder="请输入活动名" class="input-medium search-query" style="color:black;">
                        <input type="submit" class="btn btn-default btn-sm" onclick="queryMobActivity(1)" value="搜索">
                    </th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table><!--/ct-tbs-->
        </div><!--/modal-body-->
        <div class="modal-footer">
            <ul class="pagination pagination-sm pagination-right">
            </ul><!--/pagination-->
        </div><!--/modal-footer-->
    </div>
</div>
<div id="storeModal" class="modal fade">
    <div class="modal-dialog modal-lg">
        <input type="hidden" id="linkId" value="rollImgAdvHref1">
        <div class="tab-content">
            <div class="modal-header">
                <ul class="nav-tabs">
                    <li class="active"><a href="javascript:void(0)" data-toggle="tab" onclick="queryMobStore(0)" aria-expanded="true">店铺列表</a></li>
                </ul><!--/nav-->
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            </div><!--/modal-header-->
            <div class="modal-body" style="max-height:600px;overflow-y: scroll;">
                <table class="ct-tbs w table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>店铺标识</th>
                        <th>店铺名称</th>
                        <th>店铺LOGO</th>
                        <th>所属地市</th>
                        <th>所属区县</th>
                        <th style="width:150px;">
                            <!-- <form class="form-search fr">
                            </form> -->
                            <input type="text" id="chooseStoresSearch" placeholder="请输入店铺名" class="input-medium search-query" style="color:black;">
                            <input type="submit" class="btn btn-default btn-sm" onclick="queryMobStore()" value="搜索">
                        </th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table><!--/ct-tbs-->
            </div><!--/modal-body-->
            <div class="modal-footer">
                <ul class="pagination pagination-sm pagination-right">

                </ul><!--/pagination-->
            </div><!--/modal-footer-->
        </div><!--/goods-->
    </div><!--/tab-content-->
</div>

<script type="text/javascript">
    //初始化xml
    function initXml() {
        $(".z").html("");
        //拖拽
        $("#ip_cont").sortable({
            placeholder: "ui-state-highlight",
            axis: "y",
            //当用户停止排序和DOM的位置发生了变化
            update: function (event, ui) {
                var sort = 0;
                var itemArray = [];
                $(".item").each(function () {
                    var _this = $(this);
                    itemArray[sort] = _this.prop("id");
                    sort++;
                });
            }
        });
        $("#ip_cont").disableSelection();

        $(".plugin_list a").click(function () {
            $(".edit_area").addClass("plugin_box");
            $(".plugin_box").css("top", $(".app_selected").offset().top + 150 + "px");
        });
    }
    //打开设置轮播广告模块
    function showRollAdv() {
        //清空
        $("#cubeEdit").hide();
        $('#advEdit').hide();
        $("#goodsEdit").hide();
        $("#rollAdvEdit").show();
        var id, arrApp = [];
        if ($("#ip_cont .app_cont").length > 0) {
            $("#ip_cont .app_cont").each(function () {
                arrApp.push($(this).attr("id").substring(6));
            });
            id = Math.max.apply(null, arrApp) + 1;
        } else {
            id = 1;
        }
        $("#ip_cont").find(".app_selected").removeClass("app_selected");
        var _item = '<div class="app_item app_cube app_selected" style="max-height: 320px;;">' +
                '<div class="app_cont item swipe7 a0 am" id="index_' + id + '">' +
                '<div class="app_edit" style="width: 100%; height: 100%;"><div class="app_btns">' +
                '<a href="javascript:;" class="edit" onclick="updateRollAdv(' + id + ')">编辑</a>' +
                '<a href="javascript:;" class="delete" onclick="delRollAdv(' + id + ')">删除</a></div></div></div></div>';
        $("#ip_cont").append(_item);

    }


    //删除轮播广告
    function delRollAdv(rollAdvid) {
        //隐藏
        $("#cubeEdit").hide();
        $('#advEdit').hide();
        $("#goodsEdit").hide();
        $("#rollAdvEdit").hide();
        $("#ip_cont").find(".app_selected").removeClass("app_selected");
        $("#index_" + rollAdvid).parent(".app_item").remove();
    }

    //删除魔方
    function delmf(id) {
        //隐藏
        $("#advEdit").hide();
        $("#cubeEdit").hide();
        $("#rollAdvEdit").hide();
        $("#textEdit").hide();
        $("#fullRollEdit").hide();
        $("#basicEdit").hide();
        $("#goodsEdit").hide();
        $("#blankBoxEdit").hide();

        $("#ip_cont").find(".app_selected").removeClass("app_selected");
        $("#app_cont" + id).parents(".app_item").remove();
    }

    /*
     * 保存选择的图片信息
     * 选择图片控件回调函数
     */
    function saveChoooseImage(path, id) {
        path = "http://changsy.cn/" + path;
        var s = id.split("#imgEdit");
        if (id == "adv") {//广告
            $("#imgAdvSrc").val(path);
            $("#advImg").attr("src", path);
            $(".app_selected .img_adv img").attr("src", path);
        } else if (id == "rollAdv") {//轮播
            var paths = String(path).split(",");
            var rSlide;
            for (var i = 0; i < paths.length; i++) {
                chooseRollAdv(paths[i]);
                var swipeItem = '<li class="swipe7item" onclick="" correct-index="0"><a href=""><img src="' + paths[i] + '"/></a></li>';
                console.log(swipeItem);
                //创建轮播图 appselect
                if ($(".app_selected .app_cont").has('.swipe7list').length) {
                    $(".app_selected .app_cont").find('.swipe7list').append(swipeItem);
                } else {
                    $(".app_selected .app_edit").before('<ul class="swipe7list">' + swipeItem + '</ul>');
                }
                //run swipe7
                $('.swipe7').each(function () {
                    Swipe7(this, {auto: 3300});
                })

            }
        } else if (id.indexOf("rollImgAdvSrc") > -1) {//重新上传轮播
            var _index = id.substring(id.length - 1),
                    _src = $("." + id.replace(/rollImgAdvSrc/, "rolls")).attr("src");
            $("#" + id).val(path);
            $("." + id.replace(/rollImgAdvSrc/, "rolls")).attr("src", path);
            $(".app_selected .swiper-slide").each(function () {
                if ($(this).find("img").attr("src") == _src) {
                    $(this).find("img").attr("src", path);
                }
            });

        } else {//魔方
            $(id + " .imgsrc").val(path);
            $(id + " .imgsrctip").val(path);
            $(id + " .imgsrc").change();
            var _id = "img" + id.substring(8),
                    _width = $("#" + _id + "_width").val() / 2,
                    _height = $("#" + _id + "_height").val() / 2,
                    _top = parseInt($("#" + _id).css("top")) / 90 * 80,
                    _left = (parseInt($("#" + _id).css("left")) + 1) / 90 * 80 - 1,
                    _href = $("#" + _id + "_href").val();
            $(".app_selected .app_cont #sa" + _id).parent().remove();
            $(".app_selected .app_cont").append('<a href="' + _href + '"><img class="imgapp" id="sa' + _id + '" imgid="' + _id + '" alt="" src="' + path + '" width="' + _width + '" height="' + _height + '" style="position:absolute; left:' + _left + 'px; top:' + _top + 'px;"></a>');
            mfHeight();
        }

    }

    function chooseRollAdv(url) {
        var _src = url;
        var slides = $("#rollAdvForm").find(".slide");
        var count = slides.length + 1;
        _cont = '<div class="slide clearfix">' +
                '<a class="view_img fl rollAgain' + count + '" href="javascript:;"><img class="slide_img rolls' + count + '" alt="" src="' + _src + '"><span>重新上传</span></a>' +
                '<dl class="nlink-wp fl clearfix mt20 ml20">' +
                //'<dt>图片路径：</dt><dd><input class="form-control imgAdvSrc" type="text" name="imgAdvSrc" readonly="readonly"></dd>' +
                '<dt class="dt-box">链接地址：</dt><dd class="dd-box">' +
                '<div class="lk-sel lk-chs"><span class="sel-word"></span><ul><li class="s-selected"><a data-href="lk-01">功能链接</a></li><li><a data-href="lk-02">关键字</a></li></ul></div>' +
                '<div class="lk-sel gn-chs"><span class="sel-word"></span><ul>' +
                '<li class="s-selected"><a>--请选择--</a></li>' +
                '<li><a data-href="choose-activity">活动页</a></li>' +
                '<li><a data-href="choose-subject">专题页</a></li>' +
                '<li><a data-href="choose-stores">店铺</a></li>' +
                '<li><a data-href="choose-goods">某个商品</a></li>' +
                '</ul></div>' +
                '<input class="form-control custom-input" type="text" id="rollImgAdvHref' + count + '" name="rollImgAdvHref"><div class="sel-tags"></div>' +
                '<input type="hidden" id="rollImgAdvSrc' + count + '" name="rollImgAdvSrc">' +
                '</dd></dl>' +
                '<dl class="nlink-wp fl clearfix ml20 none"><dt class="dt-box">已选择：</dt><dd class="dd-box"><span class="sel-tags"></span><a class="del-tags" href="javascript:;">删除</a></dd></dl>' +
                '<a class="remove-slide" href="javascript:;" onclick="remove_sd(this)"><span class="glyphicon glyphicon-remove"></span></a></div>';

        $(".slides_wp").append(_cont).find(".slide_img rolls" + count).prop("src", _src);
        $("#rollImgAdvSrc" + count).val(_src);

        rbundLink('rollImgAdvHref' + count);
        //设置重新上传图片选择框
        $(".rollAgain" + count).click(function () {
            art.dialog.open('<@ofbizUrl>fileChoose</@ofbizUrl>', {
                id: "rollImgAdvSrc" + count,
                lock: true,
                width: '800px',
                height: '600px',
                title: '选择图片'
            });
        });
    }

    //重新绑定内连接
    function rbundLink(id) {

        $("#" + id).parents(".dd-box").find(".lk-chs a").click(function () {
            var _sel = $(this).parents(".lk-chs");
            $(this).parent("li").siblings(".s-selected").removeClass("s-selected");
            $(this).parent("li").addClass("s-selected");
            if ($(this).attr("data-href") == "lk-02") {
                _sel.next().hide();
                //_sel.next().next(".custom-input").show();
                $("#" + id).show().val("");
                $("#" + id).parents(".nlink-wp").next(".nlink-wp").find(".sel-tags").html("");
                $("#" + id).parents(".nlink-wp").next(".nlink-wp").hide();
            } else {
                _sel.next().show().find("li:first a").click();
                //_sel.next().next(".custom-input").hide();
                $("#" + id).hide();
            }
        });

        $("#" + id).parents(".dd-box").find(".gn-chs a").click(function () {
            var _sel = $(this).parents(".gn-chs"),
            _txt = $(this).text();
            $(this).parent("li").siblings(".s-selected").removeClass("s-selected");
            $(this).parent("li").addClass("s-selected");
            if ($(this).attr("data-href") == "choose-goods") {
                showGoods(id);
            } else if ($(this).attr("data-href") == "choose-activity") {
                showActivities(id);
            } else if ($(this).attr("data-href") == "choose-stores") {
                showStores(id);
            } else if ($(this).attr("data-href") == "choose-subject") {
                showSubjects(id);
            } else if ($(this).attr("data-href") == "choose-keys") {
                $("#" + id).val($(this).attr("data-href"));
                $("#" + id).parents(".nlink-wp").next(".nlink-wp").show().find(".sel-tags").html("<em>" + _txt + "</em>");
            } else if (!$(this).attr("data-href")) {
                $("#" + id).parents(".nlink-wp").next(".nlink-wp").find(".sel-tags").html("");
                $("#" + id).parents(".nlink-wp").next(".nlink-wp").hide();
            } else {
                //_sel.next(".custom-input").val($(this).attr("data-href"));
                $("#" + id).val($(this).attr("data-href"));
                $("#" + id).parents(".nlink-wp").next(".nlink-wp").show().find(".sel-tags").html("<em>" + _txt + "</em>");
            }
        });
        sel(id);
    }

    function sel(id) {
        $(".lk-sel").each(function () {
            var _this = $(this),
                    _word = _this.find(".sel-word"),
                    _ul = _this.find("ul");
            _word.text(_this.find(".s-selected a").text());
            _word.click(function () {
                _ul.show();
            });
            _this.bind("mouseleave", function () {
                _ul.hide();
            });
            _ul.find("a").click(function () {
                _word.text($(this).text());
                _ul.hide();
            });
        });
        if (typeof(id) != "undefined") {
            //  $(".del-tags").each(function () {
            $(".del-tags").click(function () {
                //if($(this).attr("attr-value")==id){
                $("#" + id).val("");
                $(this).prev(".sel-tags").html("");

                $(this).parents(".nlink-wp").prev(".nlink-wp").find(".gn-chs li:first a").click();
                $(this).parents(".nlink-wp").hide();
                //}
            });
            //  });
        }
    }

    function remove_sd(t) {
        var _src = $(t).parents(".add_slides").find(".view_img img").attr("src");
        $(t).parents(".slide").remove();
        $('.app_selected .swipe7list').html("");
        $("#slides .slide").each(function (index) {
            var imgSrc = $(this).find(".view_img img").attr("src");
            console.log(index, imgSrc);
            $('.app_selected .swipe7list').append('<li class="swipe7item" onclick="" correct-index="' + index + '"><a href=""><img src="' + imgSrc + '"/></a></li>');
        });
        $('.swipe7').each(function () {
            Swipe7(this, {auto: 3300});
        })
    }
    // 显示专题列表弹出框
    function showSubjects(id) {
        $("#linkId").val(id);
        $("#subjectModal").modal("show");
    }
    //显示内连接弹框
    function showGoods(id) {
        $("#linkId").val(id);
        $("#goodsModal").modal("show");
        //cc(t);
    }
    // 显示店铺弹出框
    function showStores(id) {
        $("#linkId").val(id);
        $("#storeModal").modal("show");
    }
    // 显示店铺弹出框
    function showActivities(id) {
        $("#linkId").val(id);
        $("#activityModal").modal("show");
    }
    //更新错误提示框的状态
    function updateTips(t, tip) {
        tip.text(t).addClass("ui-state-highlight");
    }
    //验证特殊字符，将调试显示到页面中
    function checkSpecSymb(inputobj, Tipobj) {
        var regexp = new RegExp("[''\\[\\]<>?\\\\!]");
        if (regexp.test($("#" + inputobj).val())) {
            $("#" + inputobj).addClass("ui-state-error");
            updateTips("输入的内容包含特殊字符!", $("#" + Tipobj));
            $("#" + inputobj).focus();
            return false;
        }
        else {
            $("#" + Tipobj).text("").removeClass("ui-state-highlight");
            $("#" + inputobj).removeClass("ui-state-error");
            return true;
        }
    }
    //验证特殊字符
    function checkSpecSymb(inputobj) {
        var regexp = new RegExp("[''\\[\\]<>?\\\\!]");
        if (regexp.test($("#" + inputobj).val())) {
            return false;
        } else {
            return true;
        }
    }
    function cc(t,type) {
        var _cont = $(t).parents("tr").find(".link").text(),
         _tit = $(t).parents("tr").find("td:first").text();
        $("#" + $("#linkId").val()).val(_cont+","+type);
        $("#" + $("#linkId").val()).parents(".form_group").next(".nlink-wp").show().find(".sel-tags").html("<em>" + _tit + "</em>");
        $(".close").click();
    }

    //提交轮播广告
    function saveRollAdv() {
        var flag = true;
        var slides = $("#rollAdvForm #slides .slide");
        if (slides.length < 1) {
            toastr.warning('请先至少添加一张图片!');
            flag = flag && false;
        }
        for (var i = 0; i < slides.length; i++) {
            var href = $(slides[i]).find(".form-control");
            if (href.val() != "") {
                if (!checkSpecSymb(href.prop("id"))) {
                    toastr.error('第' + (i + 1) + '张图片链接地址有特殊字符');
                    flag = flag && false;
                }
            }
        }
        if (flag) {
            $("#rollAdvEdit .slide").each(function () {
                var n = $(this).find(".slide_img").attr("class").substring(15),
                        src = $("#rollImgAdvHref" + n).val(),
                        _img = $(this).find(".slide_img").attr("src");
                $(".app_selected .swiper-slide").each(function () {
                    if ($(this).find("img").attr("src") == _img) {
                        $(this).find("a").attr("href", src);
                    }
                });
            });
            console.log($('#rollAdvForm').serialize());
            $.ajax({
                url : '<@ofbizUrl>createWebSiteTemplate</@ofbizUrl>',
                async : false,
                type: 'post',
                data: $('#rollAdvForm').serialize(),
                success : function(data){
                    console.log(data);
                }

        });
            $("#rollAdvEdit").hide();
        }
    }


    //获取商品信息
    //Ajax获取移动版货品
    function queryMobProduct(pageNo){
        var url = "<@ofbizUrl>LookupProduct</@ofbizUrl>";
        var name = $("#chooseGoodsSearch").val();
        if(!pageNo) pageNo=1;
        var params = {name:name,pageNo:pageNo-1};
        $.ajax({
            url : url,
            async : false,
            type: 'post',
            data: params,
            success : function(data){
                console.log(data);
                //清空
                $("#goodsModal .modal-body").find("tbody").html("");
                //重新赋值
                for(var i=0;i<data.resultData.list.length;i++){
                    var goods = data.resultData.list[i];
                    var html = '<tr>'
                            +'<td class="link">'+goods.productId+'</td>'
                            +'<td>'+goods.productName+'</td>'
                            +'<td><img alt="" src="'+goods.mediumImageUrl+'" width="50px" height="50px"></td>';
                    var desc ='';
                    if(goods.description) desc = goods.description;
                    html = html +'<td>'+desc+'</td>';

                    html = html+'<td><button class="ct-choose" onclick="cc(this,\'goods\');">选择</button></td></tr>';
                    $("#goodsModal .modal-body").find("tbody").append(html);
                }
                //设置分页
                //设置当前页码 pageNo
                //起始页码startNo大于1显示“《”
                //结束页码endNo小于总页数totalPages显示“》”
                var pagination = $("#goodsModal .modal-footer").find(".pagination");
                $(pagination).html("");
                var pageHtml = '';
                if((pageNo-2)>1){
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobProduct('+(pageNo-2)+')">&laquo;</a></li>';
                }
                for(var i=2;i>0;i--){
                    var up = (pageNo-i);
                    if(up>0){
                        pageHtml = pageHtml+'<li><a href="#" onclick="queryMobProduct('+up+')">'+up+'</a></li>';

                    }
                }
                pageHtml = pageHtml+'<li class="active"><a href="#">'+pageNo+'</a></li>';
                for(var i=0;i<(data.resultData.listSize-((pageNo+i)*10)) && i<2;i++){
                    console.log('pageNo:'+pageNo,"i=",i);
                    var down = (pageNo+(i+1));
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobProduct('+down+')">'+down+'</a></li>';
                }
                if(((pageNo*10)+20)<data.resultData.listSize){
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobProduct('+(pageNo+2)+')">&raquo;</a></li>';
                }
                $(pagination).html(pageHtml);
            }
        });
        initTable();

    }
    //获取商品信息
    //Ajax获取移动版货品
    function queryMobStore(pageNo){
        var url = "<@ofbizUrl>LookupStore</@ofbizUrl>";
        var name = $("#chooseStoresSearch").val();
        if(!pageNo) pageNo=1;
        var params = {name:name,pageNo:pageNo-1};
        $.ajax({
            url : url,
            async : false,
            type: 'post',
            data: params,
            success : function(data){
                console.log(data);
                //清空
                $("#storeModal .modal-body").find("tbody").html("");
                //重新赋值
                for(var i=0;i<data.resultData.list.length;i++){
                    var store = data.resultData.list[i];
                    var html = '<tr>'
                            +'<td class="link">'+store.storeId+'</td>'
                            +'<td>'+store.storeName+'</td>'
                            +'<td><img alt="" src="'+store.logo+'" width="50px" height="50px"></td>';
                    var cityName = "";
                    var countyName = "";
                    if(store.cityName) cityName = store.cityName;
                    if(store.countyName)   countyName = store.countyName;
                    html = html +'<td>'+ cityName+'</td>';
                    html = html +'<td>'+ countyName+'</td>';

                    var valId=  store.storeId;
                    console.log(store);
                    html = html+'<td><button class="ct-choose" onclick="cc(this,\'store\');">选择</button></td></tr>';
                    $("#storeModal .modal-body").find("tbody").append(html);
                }
                //设置分页
                //设置当前页码 pageNo
                //起始页码startNo大于1显示“《”
                //结束页码endNo小于总页数totalPages显示“》”
                var pagination = $("#storeModal .modal-footer").find(".pagination");
                $(pagination).html("");
                var pageHtml = '';
                if((pageNo-2)>1){
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobStore('+(pageNo-2)+')">&laquo;</a></li>';
                }
                for(var i=2;i>0;i--){
                    var up = (pageNo-i);
                    if(up>0){
                        pageHtml = pageHtml+'<li><a href="#" onclick="queryMobStore('+up+')">'+up+'</a></li>';

                    }
                }
                pageHtml = pageHtml+'<li class="active"><a href="#">'+pageNo+'</a></li>';
                for(var i=0;i<(data.resultData.listSize-((pageNo+i)*10)) && i<2;i++){
                    console.log('pageNo:'+pageNo,"i=",i);
                    var down = (pageNo+(i+1));
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobStore('+down+')">'+down+'</a></li>';
                }
                if(((pageNo*10)+20)<data.resultData.listSize){
                    pageHtml = pageHtml+'<li><a href="#" onclick="queryMobStore('+(pageNo+2)+')">&raquo;</a></li>';
                }
                $(pagination).html(pageHtml);
            }
        });
        initTable();

    }
    //获取商品信息
    //Ajax获取移动版货品
    function queryMobSubject(pageNo){
        var url = "<@ofbizUrl>LookupSpecialPage</@ofbizUrl>";
        var name = $("#chooseSubjectSearch").val();
        if(!pageNo) pageNo=1;
        var params = {name:name,pageNo:pageNo-1,type:'subject'};
        $.ajax({
            url : url,
            async : false,
            type: 'post',
            data: params,
            success : function(data){
                console.log(data);
                //清空
                $("#subjectModal .modal-body").find("tbody").html("");
                //重新赋值
                if(data&&data.resultData&&data.resultData.list) {
                    for (var i = 0; i < data.resultData.list.length; i++) {
                        var page = data.resultData.list[i];
                        var html = '<tr>'
                                + '<td class="link">' + page.specialPageId + '</td>'
                                + '<td>' + page.pageName + '</td>';

                        var desc = '';
                        if (page.pageDescription) desc = page.pageDescription;
                        html = html + '<td>' + desc + '</td>';
                        html = html + '<td><button class="ct-choose" onclick="cc(this,\'subject\');">选择</button></td></tr>';
                        $("#subjectModal .modal-body").find("tbody").append(html);
                    }
                    //设置分页
                    //设置当前页码 pageNo
                    //起始页码startNo大于1显示“《”
                    //结束页码endNo小于总页数totalPages显示“》”
                    var pagination = $("#subjectModal .modal-footer").find(".pagination");
                    $(pagination).html("");
                    var pageHtml = '';
                    if ((pageNo - 2) > 1) {
                        pageHtml = pageHtml + '<li><a href="#" onclick="queryMobSubject(' + (pageNo - 2) + ')">&laquo;</a></li>';
                    }
                    for (var i = 2; i > 0; i--) {
                        var up = (pageNo - i);
                        if (up > 0) {
                            pageHtml = pageHtml + '<li><a href="#" onclick="queryMobSubject(' + up + ')">' + up + '</a></li>';

                        }
                    }
                    pageHtml = pageHtml + '<li class="active"><a href="#">' + pageNo + '</a></li>';
                    for (var i = 0; i < (data.resultData.listSize - ((pageNo + i) * 10)) && i < 2; i++) {
                        console.log('pageNo:' + pageNo, "i=", i);
                        var down = (pageNo + (i + 1));
                        pageHtml = pageHtml + '<li><a href="#" onclick="queryMobSubject(' + down + ')">' + down + '</a></li>';
                    }
                    if (((pageNo * 10) + 20) < data.resultData.listSize) {
                        pageHtml = pageHtml + '<li><a href="#" onclick="queryMobSubject(' + (pageNo + 2) + ')">&raquo;</a></li>';
                    }
                    $(pagination).html(pageHtml);
                }
            }
        });
        initTable();

    }
    //获取商品信息
    //Ajax获取移动版货品
    function queryMobActivity(pageNo){
        var url = "<@ofbizUrl>LookupSpecialPage</@ofbizUrl>";
        var name = $("#chooseActivitySearch").val();
        if(!pageNo) pageNo=1;
        var params = {name:name,pageNo:pageNo-1};
        $.ajax({
            url : url,
            async : false,
            type: 'post',
            data: params,
            success : function(data){
                console.log(data);
                //清空
                $("#activityModal .modal-body").find("tbody").html("");
                //重新赋值
                if(data&&data.resultData&&data.resultData.list) {
                    for (var i = 0; i < data.resultData.list.length; i++) {
                        var page = data.resultData.list[i];
                        var html = '<tr>'
                                + '<td class="link">' + page.specialPageId + '</td>'
                                + '<td>' + page.pageName + '</td>';

                        var desc = '';
                        if (page.pageDescription) desc = page.pageDescription;
                        html = html + '<td>' + desc + '</td>';
                        html = html + '<td><button class="ct-choose" onclick="cc(this,\'activity\');">选择</button></td></tr>';
                        $("#activityModal .modal-body").find("tbody").append(html);
                    }
                    //设置分页
                    //设置当前页码 pageNo
                    //起始页码startNo大于1显示“《”
                    //结束页码endNo小于总页数totalPages显示“》”
                    var pagination = $("#activityModal .modal-footer").find(".pagination");
                    $(pagination).html("");
                    var pageHtml = '';
                    if ((pageNo - 2) > 1) {
                        pageHtml = pageHtml + '<li><a href="#" onclick="queryMobActivity(' + (pageNo - 2) + ')">&laquo;</a></li>';
                    }
                    for (var i = 2; i > 0; i--) {
                        var up = (pageNo - i);
                        if (up > 0) {
                            pageHtml = pageHtml + '<li><a href="#" onclick="queryMobActivity(' + up + ')">' + up + '</a></li>';

                        }
                    }
                    pageHtml = pageHtml + '<li class="active"><a href="#">' + pageNo + '</a></li>';
                    for (var i = 0; i < (data.resultData.listSize - ((pageNo + i) * 10)) && i < 2; i++) {
                        console.log('pageNo:' + pageNo, "i=", i);
                        var down = (pageNo + (i + 1));
                        pageHtml = pageHtml + '<li><a href="#" onclick="queryMobActivity(' + down + ')">' + down + '</a></li>';
                    }
                    if (((pageNo * 10) + 20) < data.resultData.listSize) {
                        pageHtml = pageHtml + '<li><a href="#" onclick="queryMobActivity(' + (pageNo + 2) + ')">&raquo;</a></li>';
                    }
                    $(pagination).html(pageHtml);
                }
            }
        });
        initTable();

    }

    function initTable(){
        $(".level-show").click(function(){
            var _this = $(this);
            _this.siblings(".level-hide").css("display","inline-block");
            _this.hide();
            _this.parents("tr").nextUntil(".level-1",".level-2").show();
        });
        $(".level-hide").click(function(){
            var _this = $(this);
            _this.siblings(".level-show").css("display","inline-block");
            _this.hide();
            _this.parents("tr").nextUntil(".level-1").hide();
        });
        $(".level-show2").click(function(){
            var _this = $(this);
            _this.siblings(".level-hide2").css("display","inline-block");
            _this.hide();
            _this.parents("tr").nextUntil(".level-2").show();
        });
        $(".level-hide2").click(function(){
            var _this = $(this);
            _this.siblings(".level-show2").css("display","inline-block");
            _this.hide();
            _this.parents("tr").nextUntil(".level-2 ").hide();
        });
        // $(".level-1").each(function(){
        // 	if($(this).find(".level-show").length > 0) {
        // 		$(this).find(".ct-choose").hide();
        // 	};
        // });

        $(".ct-choose").click(function(){
            var _cont = $(this).parents("tr").find(".link").text();
            $(".ctCont").text(_cont);
            $(".close").click();
        });
    }

</script>
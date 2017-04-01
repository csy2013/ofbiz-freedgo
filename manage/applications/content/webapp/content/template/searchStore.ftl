<style>
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
</style>

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
    $(function(){
        queryMobStore(1);
    });
    //获取商品信息
    //Ajax获取移动版货品
    function queryMobStore(pageNo) {
        var url = "<@ofbizUrl>LookupStore</@ofbizUrl>";
        var name = $("#chooseStoresSearch").val();
        if (!pageNo) pageNo = 1;
        var params = {name: name, pageNo: pageNo - 1};
        $.ajax({
            url: url,
            async: false,
            type: 'post',
            data: params,
            success: function (data) {
                console.log(data);
//清空
                $("#storeModal .modal-body").find("tbody").html("");
//重新赋值
                for (var i = 0; i < data.resultData.list.length; i++) {
                    var store = data.resultData.list[i];
                    var html = '<tr>'
                            + '<td class="link">' + store.storeId + '</td>'
                            + '<td>' + store.storeName + '</td>'
                            + '<td><img alt="" src="' + store.logo + '" width="50px" height="50px"></td>';
                    var cityName = "";
                    var countyName = "";
                    if (store.cityName) cityName = store.cityName;
                    if (store.countyName)   countyName = store.countyName;
                    html = html + '<td>' + cityName + '</td>';
                    html = html + '<td>' + countyName + '</td>';

                    var valId = store.storeId;
                    console.log(store);
                    html = html + '<td><button class="ct-choose" onclick="cc(this,\'store\');">选择</button></td></tr>';
                    $("#storeModal .modal-body").find("tbody").append(html);
                }
                //设置分页
                //设置当前页码 pageNo
                //起始页码startNo大于1显示“《”
                //结束页码endNo小于总页数totalPages显示“》”
                var pagination = $("#storeModal .modal-footer").find(".pagination");
                $(pagination).html("");
                var pageHtml = '';
                if ((pageNo - 2) > 1) {
                    pageHtml = pageHtml + '<li><a href="#" onclick="queryMobStore(' + (pageNo - 2) + ')">&laquo;</a></li>';
                }
                for (var i = 2; i > 0; i--) {
                    var up = (pageNo - i);
                    if (up > 0) {
                        pageHtml = pageHtml + '<li><a href="#" onclick="queryMobStore(' + up + ')">' + up + '</a></li>';

                    }
                }
                pageHtml = pageHtml + '<li class="active"><a href="#">' + pageNo + '</a></li>';
                for (var i = 0; i < (data.resultData.listSize - ((pageNo + i) * 10)) && i < 2; i++) {
                    console.log('pageNo:' + pageNo, "i=", i);
                    var down = (pageNo + (i + 1));
                    pageHtml = pageHtml + '<li><a href="#" onclick="queryMobStore(' + down + ')">' + down + '</a></li>';
                }
                if (((pageNo * 10) + 20) < data.resultData.listSize) {
                    pageHtml = pageHtml + '<li><a href="#" onclick="queryMobStore(' + (pageNo + 2) + ')">&raquo;</a></li>';
                }
                $(pagination).html(pageHtml);
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

<link href="/images/themes/common/common.css" rel="stylesheet">
<script src="/images/themes/coloradmin/plugins/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="/images/themes/coloradmin/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

<link rel="stylesheet" href="/images/themes/coloradmin/plugins/bootstrap/css/bootstrap.min.css" type="text/css"/>


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
</table>
<script type="text/javascript">
    $(function () {
        queryMobProduct(1);
    });
    //获取商品信息
    //Ajax获取移动版货品
    function queryMobProduct(pageNo) {
        var url = "<@ofbizUrl>LookupProduct</@ofbizUrl>";
        var name = $("#chooseGoodsSearch").val();
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
                $("#goodsModal .modal-body").find("tbody").html("");
//重新赋值
                for (var i = 0; i < data.resultData.list.length; i++) {
                    var goods = data.resultData.list[i];
                    var html = '<tr>'
                            + '<td class="link">' + goods.productId + '</td>'
                            + '<td>' + goods.productName + '</td>'
                            + '<td><img alt="" src="' + goods.mediumImageUrl + '" width="50px" height="50px"></td>';
                    var desc = '';
                    if (goods.description) desc = goods.description;
                    html = html + '<td>' + desc + '</td>';

                    html = html + '<td><button class="ct-choose" onclick="cc(this,\'goods\');">选择</button></td></tr>';
                    $("#goodsModal .modal-body").find("tbody").append(html);
                }
//设置分页
//设置当前页码 pageNo
//起始页码startNo大于1显示“《”
//结束页码endNo小于总页数totalPages显示“》”
                var pagination = $("#goodsModal .modal-footer").find(".pagination");
                $(pagination).html("");
                var pageHtml = '';
                if ((pageNo - 2) > 1) {
                    pageHtml = pageHtml + '<li><a href="#" onclick="queryMobProduct(' + (pageNo - 2) + ')">&laquo;</a></li>';
                }
                for (var i = 2; i > 0; i--) {
                    var up = (pageNo - i);
                    if (up > 0) {
                        pageHtml = pageHtml + '<li><a href="#" onclick="queryMobProduct(' + up + ')">' + up + '</a></li>';

                    }
                }
                pageHtml = pageHtml + '<li class="active"><a href="#">' + pageNo + '</a></li>';
                for (var i = 0; i < (data.resultData.listSize - ((pageNo + i) * 10)) && i < 2; i++) {
                    console.log('pageNo:' + pageNo, "i=", i);
                    var down = (pageNo + (i + 1));
                    pageHtml = pageHtml + '<li><a href="#" onclick="queryMobProduct(' + down + ')">' + down + '</a></li>';
                }
                if (((pageNo * 10) + 20) < data.resultData.listSize) {
                    pageHtml = pageHtml + '<li><a href="#" onclick="queryMobProduct(' + (pageNo + 2) + ')">&raquo;</a></li>';
                }
                $(pagination).html(pageHtml);
            }
        });
        initTable();

    }
    function initTable() {
        $(".level-show").click(function () {
            var _this = $(this);
            _this.siblings(".level-hide").css("display", "inline-block");
            _this.hide();
            _this.parents("tr").nextUntil(".level-1", ".level-2").show();
        });
        $(".level-hide").click(function () {
            var _this = $(this);
            _this.siblings(".level-show").css("display", "inline-block");
            _this.hide();
            _this.parents("tr").nextUntil(".level-1").hide();
        });
        $(".level-show2").click(function () {
            var _this = $(this);
            _this.siblings(".level-hide2").css("display", "inline-block");
            _this.hide();
            _this.parents("tr").nextUntil(".level-2").show();
        });
        $(".level-hide2").click(function () {
            var _this = $(this);
            _this.siblings(".level-show2").css("display", "inline-block");
            _this.hide();
            _this.parents("tr").nextUntil(".level-2 ").hide();
        });
        // $(".level-1").each(function(){
        // 	if($(this).find(".level-show").length > 0) {
        // 		$(this).find(".ct-choose").hide();
        // 	};
        // });

        $(".ct-choose").click(function () {
            var _cont = $(this).parents("tr").find(".link").text();
            $(".ctCont").text(_cont);
            $(".close").click();
        });
    }
</script>
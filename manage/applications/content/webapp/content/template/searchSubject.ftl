<style>
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

</style>

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
<script type="text/javascript">
    $(function(){
        queryMobSubject(1);
    });
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

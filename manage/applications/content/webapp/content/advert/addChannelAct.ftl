<link href="/images/themes/common/common.css" rel="stylesheet">
<script type="text/javascript" src="/images/jquery/plugins/artDialog/artDialog.source.js"></script>
<script type="text/javascript" src="/images/jquery/plugins/artDialog/iframeTools.js"></script>
<form action="<@ofbizUrl>createChannelAct</@ofbizUrl>" method="post">
    <input type="hidden" name="templateId" value="${parameters.specialPageId}">
    <div class="row">
        <div class="control-label col-md-1">图片名称:</div>
        <div class="col-md-2"><input type="text" class="form-control input-sm" name="advertName"></div>
        <div class="control-label col-md-2"> 序号:</div>
        <div class="col-md-1"><input type="text" class="form-control input-sm" name="sequenceNum"></div>
        <div class="col-md-1">样式</div>
        <div class="col-md-1">
            <select id="tplStyle" name="tplStyle" class="tplStyle">
            <option value="tpl1">样式一</option>
            <option value="tpl2">样式二</option>
            <option value="tpl3">样式三</option>
            <option value="tpl4">样式四</option>
            <option value="tpl5">样式五</option>
            <option value="tpl6">样式六</option>
            <option value="tpl7">样式七</option>
        </select>
        </div>

        <div class="col-md-2">图片数量</div>
        <div class="col-md-1">
            <select id="picNum" name="picNum" class="picNumStyle">
                <option value="2">2</option>
                <option value="4">4</option>
                <option value="6">6</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                <option value="12">12</option>
            </select>
        </div>
    </div>
    <br/>
    <div class="table-responsive">
        <table id="data-table" class="acttable table table-striped table-bordered">
            <thead>
            <tr>
                <th> 序号</th>
                <th> 图片</th> <th>名称</th>
                <th>副标题</th>
                <th>链接类型</th>
                <th>链接取值</th>
            </tr>
            <#list  1..2 as seq>
            <tr class="picbody">
                <td>${seq}</td>

                <td>
                    <input type="hidden" id="upload${seq}_imgSrc" name="upload${seq}_imgSrc"/>
                    <input type="hidden" id="upload${seq}_sequenceNum" name="upload${seq}_sequenceNum" value="${seq}"/>
                    <a href="javascript:void(0)" id="upload${seq}" class="uploadbanner btn btn-primary">选择图片</a>
                </td>
                <td> <input type="text" class="form-control" id="upload${seq}_advertContentName" name="upload${seq}_advertContentName" value=""/> </td>
                <td> <input type="text" class="form-control" id="upload${seq}_description" name="upload${seq}_description" value=""/> </td>
                <td>
                    <select id="upload${seq}_linkType" name="upload${seq}_linkType" class="uploadLinkType">
                        <option value="activityDetail">活动页面</option>
                        <option value="channelPage">专题页面</option>
                        <option value="storeList">店铺列表</option>
                        <option value="storeListByKey">关键字搜索</option>
                        <option value="store">店铺</option>
                        <option value="productDetail">产品详细</option>
                        <option value="web">链接地址</option>
                    </select>
                </td>
                <td>
                    <input type="text" name="upload${seq}_val" id="upload${seq}_val" class="upload_val form-control" size="8"/>
                </td>
            </tr>
            </#list>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</form>
<script type="application/javascript">

    $(function(){
        $(".uploadbanner").live('click',function () {
            art.dialog.open('<@ofbizUrl>fileChoose</@ofbizUrl>', {
                    id: this.id,
                    lock: true,
                    width: '800px',
                    height: '600px',
                    title: '选择图片',
                    top: '30%',
                    fixed: true
                });
            }) ;

        $(".uploadLinkType").live('change',function () {
            $(this).parent().next().find('input').val('');
        });

        $(".upload_val").live('click',function () {
            var idStr = this.id;
            var ids = idStr.split("_");
            var id = ids[0];
            var type = $('#' + id + "_linkType").val() ? $('#' + id + "_linkType").val() : '';
            if (type === 'channelPage') {
                art.dialog.open('<@ofbizUrl>LookupSubject</@ofbizUrl>', {
                    id: this.id,
                    lock: true,
                    width: '800px',
                    height: '600px',
                    title: '选择专题',
                    top: '30%',
                    fixed: true
                });
            } else if (type === 'storeList') {
                art.dialog.open('<@ofbizUrl>LookupProductStoreList</@ofbizUrl>', {
                    id: this.id,
                    lock: true,
                    width: '800px',
                    height: '600px',
                    title: '选择店铺列表',
                    top: '30%',
                    fixed: true
                });
            } else if (type === 'storeListByKey') {

            } else if (type === 'store') {
                art.dialog.open('<@ofbizUrl>LookupProductStore</@ofbizUrl>', {
                    id: this.id,
                    lock: true,
                    width: '800px',
                    height: '600px',
                    title: '选择某个店铺',
                    top: '30%',
                    fixed: true
                });
            } else if (type === 'productDetail') {
                art.dialog.open('<@ofbizUrl>LookupProduct</@ofbizUrl>', {
                    id: this.id,
                    lock: true,
                    width: '800px',
                    height: '600px',
                    title: '选择商品',
                    top: '30%',
                    fixed: true
                });
            } else if (type === 'activityDetail') {
                art.dialog.open('<@ofbizUrl>LookupActivity</@ofbizUrl>', {
                    id: this.id,
                    lock: true,
                    width: '800px',
                    height: '600px',
                    title: '选择活动',
                    top: '30%',
                    fixed: true
                });
            }

        });

        $('.picNumStyle').change(function(){
            var num = $(this).val();
            console.log(num);
            $('.picbody').remove();
            for (var j=0;j<num;j++) {
                var html = '<tr class="picbody"> <td>'+(j+1)+'</td> <td> <input type="hidden" id="upload'+(j+1)+'_imgSrc" name="upload'+(j+1)+'_imgSrc"/> <input type="hidden" id="upload'+(j+1)+'_sequenceNum" name="upload'+(j+1)+'_sequenceNum" value="'+(j+1)+'"/>'+
                        '<a href="javascript:void(0)" id="upload'+(j+1)+'" class="uploadbanner btn btn-primary">选择图片</a> </td> <td> <input type="text"  class="form-control" id="upload'+(j+1)+'_advertContentName" name="upload'+(j+1)+'_advertContentName" value=""/> </td>'+
                        '<td> <input type="text"  class="form-control" id="upload'+(j+1)+'_description" name="upload'+(j+1)+'_description" value=""/> </td><td> <select id="upload'+(j+1)+'_linkType" name="upload'+(j+1)+'_linkType" class="uploadLinkType">'+
                        '<option value="activityDetail">活动页面</option> <option value="channelPage">专题页面</option> <option value="storeList">店铺列表</option> <option value="storeListByKey">关键字搜索</option>'+
                        '<option value="store">店铺</option> <option value="productDetail">产品详细</option><option value="web">链接地址</option> </select> </td> <td> <input type="text" name="upload'+(j+1)+'_val" id="upload'+(j+1)+'_val" class="upload_val form-control" size="8"/></td></tr>';

                $('.acttable').append(html);
            }

        });
    });


    function saveChoooseImage(path, id) {
        $('#' + id).before("<img src='" + path + "' width='150' />");
        $('#' + id + "_imgSrc").val(path);
        $('#' + id + "_sequenceNum").val(id.substr(6));
        path = "http://changsy.cn/" + path;
    }


    function saveChooseValue(value, id) {
        $('#' + id).val(value);
    }

    function saveMutilChooseValue(value, id) {
        var val = $('#' + id).val();
        if (val) {
            $('#' + id).val(val + "," + value);
        } else {
            $('#' + id).val(value);
        }

    }








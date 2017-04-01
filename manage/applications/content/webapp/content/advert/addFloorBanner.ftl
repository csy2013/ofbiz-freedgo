<link href="/images/themes/common/common.css" rel="stylesheet">
<script type="text/javascript" src="/images/jquery/plugins/artDialog/artDialog.source.js"></script>
<script type="text/javascript" src="/images/jquery/plugins/artDialog/iframeTools.js"></script>
<form action="<@ofbizUrl>createFloorBanner</@ofbizUrl>" method="post">
    <input type="hidden" name="templateId" value="${parameters.siteIndexTemplateId}">
    <div class="row">
        <div class="control-label col-md-2"> 广告图名称:</div>
        <div class="col-md-2"><input type="text" class="form-control input-sm" name="advertName"></div>
        <div class="control-label col-md-2"> 序号:</div>
        <div class="col-md-2"><input type="text" class="form-control input-sm" name="sequenceNum"></div>
        <div class="col-md-2">样式</div>
        <div class="col-md-2">
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
    </div>
    <br/>
    <div class="table-responsive">
        <table id="data-table" class="table table-striped table-bordered">
            <thead>
            <tr>
                <th> 序号</th>
                <th> 图片</th>
                <th> 链接类型</th>
                <th>链接取值</th>
            </tr>
            <#list  1..6 as seq>
            <tr>
                <td>${seq}</td>
                <td>
                    <input type="hidden" id="upload${seq}_imgSrc" name="upload${seq}_imgSrc"/>
                    <input type="hidden" id="upload${seq}_sequenceNum" name="upload${seq}_sequenceNum" value="${seq}"/>
                    <a href="javascript:void(0)" id="upload${seq}" class="uploadbanner btn btn-primary">选择图片</a>
                </td>
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
    $(".uploadbanner").click(function () {

        var num = 5 - $(".slide").length;
        if (num > 0) {
            console.log(this.id);
            art.dialog.open('<@ofbizUrl>fileChoose</@ofbizUrl>', {
                id: this.id,
                lock: true,
                width: '800px',
                height: '600px',
                title: '选择图片',
                top: '30%',
                fixed: true
            });
        } else {
            alert('轮播广告已满5张!');
        }
    });

    $(".uploadLinkType").change(function () {
        $(this).parent().next().find('input').val('');
    });

    $(".upload_val").click(function () {
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








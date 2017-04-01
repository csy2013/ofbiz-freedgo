<link href="/images/themes/common/common.css" rel="stylesheet">
<script type="text/javascript" src="/images/jquery/plugins/artDialog/artDialog.source.js"></script>
<script type="text/javascript" src="/images/jquery/plugins/artDialog/iframeTools.js"></script>

<form action="<@ofbizUrl>updateActivityAct1</@ofbizUrl>" method="post">
    <input type="hidden" name="templateId" value="${specialPageId}">
    <input type="hidden" name="advertId" value="${advertId}">
    <div class="row">
        <div class="control-label col-md-2"> 活动图片名称:</div>
        <div class="col-md-2"><input type="text" class="form-control input-sm" name="advertName" value="${templateContent.advertName?if_exists}"></div>
        <div class="control-label col-md-2"> 序号:</div>
        <div class="col-md-2"><input type="text" class="form-control input-sm" name="sequenceNum" value="${templateContent.sequenceNum}"></div>
        <div class="col-md-2">样式</div>
        <div class="col-md-2">
            <select id="tplStyle" name="tplStyle" class="tplStyle">
                <option <#if templateContent.tplStyle?default("") == 'tpl1'>selected</#if> value="tpl1">样式一</option>
                <option <#if templateContent.tplStyle?default("") == 'tpl2'>selected</#if> value="tpl2">样式二</option>
                <option <#if templateContent.tplStyle?default("") == 'tpl3'>selected</#if> value="tpl3">样式三</option>
                <option <#if templateContent.tplStyle?default("") == 'tpl4'>selected</#if> value="tpl4">样式四</option>
                <option <#if templateContent.tplStyle?default("") == 'tpl5'>selected</#if> value="tpl5">样式五</option>
                <option <#if templateContent.tplStyle?default("") == 'tpl6'>selected</#if> value="tpl6">样式六</option>
                <option <#if templateContent.tplStyle?default("") == 'tpl7'>selected</#if> value="tpl7">样式七</option>
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
            <#list  advertContents as advertContent>
            <tr>
                <td>${advertContent.sequenceNum}</td>
                <td>
                    <input type="hidden" name="upload${advertContent.sequenceNum}_advertContentId" value="${advertContent.advertContentId}"/>
                    <input type="hidden" id="upload${advertContent.sequenceNum}_imgSrc" name="upload${advertContent.sequenceNum}_imgSrc" value="${advertContent.imgSrc?if_exists}"/>
                    <input type="hidden" id="upload${advertContent.sequenceNum}_sequenceNum" name="upload${advertContent.sequenceNum}_sequenceNum"
                           value="${advertContent.sequenceNum?if_exists}"/>
                    <#if advertContent.imgSrc?has_content><img src="${advertContent.imgSrc}" width="150"/> </#if>
                    <a href="javascript:void(0)" id="upload${advertContent.sequenceNum}" class="uploadbanner btn btn-primary">选择图片</a>
                </td>
                <td>
                    <select id="upload${advertContent.sequenceNum}_linkType" name="upload${advertContent.sequenceNum}_linkType" class="uploadLinkType">
                        <option <#if advertContent.relationTypeId?default("") =='activityDetail'>selected</#if> value="activityDetail">活动页面</option>
                        <option <#if advertContent.relationTypeId?default("")  =='channelPage'>selected</#if> value="channelPage">专题页面</option>
                        <option <#if advertContent.relationTypeId?default("") =='storeList'>selected</#if> value="storeList">店铺列表</option>
                        <option <#if advertContent.relationTypeId?default("")  =='storeListByKey'>selected</#if> value="storeListByKey">关键字搜索</option>
                        <option <#if advertContent.relationTypeId?default("") =='store'>selected</#if> value="store">店铺</option>
                        <option <#if advertContent.relationTypeId?default("")  =='productDetail'>selected</#if> value="productDetail">产品详细</option>
                        <option <#if advertContent.relationTypeId?default("")  =='web'>selected</#if> value="web">链接地址</option>
                    </select>
                </td>
                <td>
                    <input type="text" name="upload${advertContent.sequenceNum}_val" id="upload${advertContent.sequenceNum}_val" class="upload_val form-control" size="8"
                           value="${advertContent.relationId?if_exists}"/>
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

        } else if (type === 'web') {

        }else if (type === 'store') {
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
        $('#' + id).parent().find('img').remove();
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








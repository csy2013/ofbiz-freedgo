<link href="/images/themes/common/common.css" rel="stylesheet">
<script type="text/javascript" src="/images/jquery/plugins/artDialog/artDialog.source.js"></script>
<script type="text/javascript" src="/images/jquery/plugins/artDialog/iframeTools.js"></script>
<form action="<@ofbizUrl>editAct2</@ofbizUrl>" method="post">
    <input type="hidden" name="templateId" value="${parameters.siteIndexTemplateId}">
    <input type="hidden" name="advertId" value="${advertId}">
    <div class="row">
        <div class="control-label col-md-2">活动图片名称:</div>
        <div class="col-md-2"><input type="text" class="form-control input-sm" name="advertName" value="${templateContent.advertName}"></div>
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
                <th>序号</th>
                <th>图片</th>
                <th>标题</th>
                <th>副标题</th>
                <th>链接类型</th>
                <th>链接取值</th>
            </tr>
            <#assign uploadItemSeq = 0 >
            <#assign index = 0>
            <#list  advertContents as advertContent>
                <#assign uploadType = advertContent.advertContentTypeId?default("act2")>
            <#--获取最大的subnum-->

                <#if uploadType == "act2">
                <input type="hidden" name="upload${advertContent.sequenceNum}_type" id="upload${advertContent.sequenceNum}_type" value="${advertContent.advertContentTypeId}"/>
                <tr>
                    <td>${advertContent.sequenceNum}<br/>
                        是否秒杀
                        <select id="upload${advertContent.sequenceNum}_miaosha" name="upload${advertContent.sequenceNum}_miaosha" class="upload_miaosha">
                            <option <#if uploadType?default("") == 'miaosha'>selected</#if> value="y">是</option>
                            <option <#if uploadType?default("") == 'act2'>selected</#if> value="n">否</option>
                        </select>
                    </td>
                    <td>
                        <div id="upload${advertContent.sequenceNum}_div">
                            <input type="hidden" id="upload${advertContent.sequenceNum}_imgSrc" name="upload${advertContent.sequenceNum}_1_imgSrc" value="${advertContent.imgSrc}"/>
                            <input type="hidden" id="upload${advertContent.sequenceNum}_sequenceNum" name="upload${advertContent.sequenceNum}_1_sequenceNum"
                                   value="${advertContent.sequenceNum}"/>
                            <img src="${advertContent.imgSrc}" width="150"/>
                            <a href="javascript:void(0)" id="upload${advertContent.sequenceNum}" class="uploadbanner btn btn-primary">选择图片</a>
                        </div>
                        <table class="table table-bordered upload_ma" id="upload${advertContent.sequenceNum}_ma" style="display: none;">
                            <tr>
                                <td>
                                    <input type="hidden" id="upload${advertContent.sequenceNum}_1_imgSrc" name="upload${advertContent.sequenceNum}_1_imgSrc"/>
                                    <input type="hidden" id="upload${advertContent.sequenceNum}_1_sequenceNum" name="upload${advertContent.sequenceNum}_1_sequenceNum"
                                           value="${advertContent.sequenceNum}"/>
                                    <a href="javascript:void(0)" id="upload${advertContent.sequenceNum}_1" class="uploadbanner btn btn-primary">选择图片</a>

                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="hidden" id="upload${advertContent.sequenceNum}_2_imgSrc" name="upload${advertContent.sequenceNum}_2_imgSrc"/>
                                    <input type="hidden" id="upload${advertContent.sequenceNum}_2_sequenceNum" name="upload${advertContent.sequenceNum}_2_sequenceNum"
                                           value="${advertContent.sequenceNum}"/>
                                    <a href="javascript:void(0)" id="upload${advertContent.sequenceNum}_2" class="uploadbanner btn btn-primary">选择图片</a>

                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <input type="hidden" id="upload${advertContent.sequenceNum}_3_imgSrc" name="upload${advertContent.sequenceNum}_3_imgSrc"/>
                                    <input type="hidden" id="upload${advertContent.sequenceNum}_3_sequenceNum" name="upload${advertContent.sequenceNum}_3_sequenceNum"
                                           value="${advertContent.sequenceNum}"/>
                                    <a href="javascript:void(0)" id="upload${advertContent.sequenceNum}_3" class="uploadbanner btn btn-primary">选择图片</a>

                                </td>
                            </tr>
                        </table>
                    </td>
                    <td><input type="text" name="upload${advertContent.sequenceNum}_advertContentName" id="upload${advertContent.sequenceNum}_advertContentName"
                               class="form-control" value="${advertContent.advertContentName?if_exists}"/></td>
                    <td><input type="text" name="upload${advertContent.sequenceNum}_description" id="upload${advertContent.sequenceNum}_description" class="form-control" value="${advertContent.description?if_exists}"/></td>

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
                <#elseif uploadType == "miaosha">
                    <#assign conts = delegator.findByAnd("AdvertContent",Static["org.ofbiz.base.util.UtilMisc"].toMap("advertId",advertContent.advertId,"sequenceNum",advertContent.sequenceNum),Static["org.ofbiz.base.util.UtilMisc"].toList("-subGroupNum"))>
                    <#assign len = conts.get(0).get("subGroupNum")>


                    <#if uploadItemSeq != advertContent.sequenceNum>
                        <#assign index = 1>
                    <input type="hidden" name="upload${advertContent.sequenceNum}_type" id="upload${advertContent.sequenceNum}_type" value="${advertContent.description}"/>
                    <tr>
                        <td>${advertContent.sequenceNum}<br/>
                            是否秒杀
                            <select id="upload${advertContent.sequenceNum}_miaosha" name="upload${advertContent.sequenceNum}_miaosha" class="upload_miaosha">
                                <option <#if uploadType?default("") == 'miaosha'>selected</#if> value="y">是</option>
                                <option <#if uploadType?default("") == 'act2'>selected</#if> value="n">否</option>
                            </select>
                        </td>

                    </#if>
                    <#if uploadItemSeq != advertContent.sequenceNum>

                    <td>
                        <div id="upload${advertContent.sequenceNum}_div" style="display: none;">
                            <input type="hidden" id="upload${advertContent.sequenceNum}_imgSrc" name="upload${advertContent.sequenceNum}_1_imgSrc"/>
                            <input type="hidden" id="upload${advertContent.sequenceNum}_sequenceNum" name="upload${advertContent.sequenceNum}_1_sequenceNum"
                                   value="${advertContent.sequenceNum}"/>
                            <a href="javascript:void(0)" id="upload${advertContent.sequenceNum}" class="uploadbanner btn btn-primary">选择图片</a>
                        </div>
                    <table class="table table-bordered upload_ma" id="upload${advertContent.sequenceNum}_ma">
                    <tr>
                        <td>
                            <input type="hidden" id="upload${advertContent.sequenceNum}_1_imgSrc" name="upload${advertContent.sequenceNum}_1_imgSrc"
                                   value="${advertContent.imgSrc}"/>
                            <input type="hidden" id="upload${advertContent.sequenceNum}_1_sequenceNum" name="upload${advertContent.sequenceNum}_1_sequenceNum"
                                   value="${advertContent.sequenceNum}"/>
                            <img src="${advertContent.imgSrc}" width="150"/>
                            <a href="javascript:void(0)" id="upload${advertContent.sequenceNum}_1" class="uploadbanner btn btn-primary">选择图片</a>

                        </td>
                    </tr>
                    <#else>
                        <#assign index = index+1>
                    <tr>
                        <td>
                            <input type="hidden" id="upload${advertContent.sequenceNum}_${index}_imgSrc" name="upload${advertContent.sequenceNum}_${index}_imgSrc"
                                   value="${advertContent.imgSrc}"/>
                            <input type="hidden" id="upload${advertContent.sequenceNum}_${index}_sequenceNum" name="upload${advertContent.sequenceNum}_${index}_sequenceNum"
                                   value="${advertContent.sequenceNum}"/>
                            <img src="${advertContent.imgSrc}" width="150"/>
                            <a href="javascript:void(0)" id="upload${advertContent.sequenceNum}_${index}" class="uploadbanner btn btn-primary">选择图片</a>
                        </td>
                    </tr>
                    </#if>

                    <#if (index == len) && (uploadItemSeq == advertContent.sequenceNum)>
                    </table></td>
                        <td><input type="text" name="upload${advertContent.sequenceNum}_advertContentName" id="upload${advertContent.sequenceNum}_advertContentName"
                                   class="form-control" value="${advertContent.advertContentName?if_exists}"/></td>
                        <td><input type="text" name="upload${advertContent.sequenceNum}_description" id="upload${advertContent.sequenceNum}_description" class="form-control" value="${advertContent.description?if_exists}"/></td>

                    </#if>

                    <#if (uploadItemSeq == advertContent.sequenceNum) && (index==len)>
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
                        <#assign index = 0>
                    </#if>
                    <#assign uploadItemSeq = advertContent.sequenceNum>
                </#if>
            </#list>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</form>
<script type="application/javascript">
    $(function () {
        $(".uploadbanner").click(function () {

            art.dialog.open('<@ofbizUrl>fileChoose</@ofbizUrl>', {
                id: this.id,
                lock: true,
                width: '800px',
                height: '600px',
                title: '选择图片',
                top: '30%',
                fixed: true
            });

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

        $('.upload_miaosha').change(function () {

            var ye = $(this).val();
            console.log(ye);
            if (ye == 'y') {
                var id = this.id;
                var seq = id.substr(6, 1);
                console.log(seq);
                $('#upload' + seq + "_div").hide();
                $('#upload' + seq + "_ma").show();
                $('#upload' + seq + "_type").val('miaosha');
            } else if (ye == 'n') {
                var id = this.id;
                var seq = id.substr(6, 1);
                $('#upload' + seq + "_div").show();
                $('#upload' + seq + "_ma").hide();
                $('#upload' + seq + "_type").val('act2');
            }
        })

    });


    function saveChoooseImage(path, id) {
        console.log(path, id);
        $('#' + id).parent().find('img').remove();
        $('#' + id).before("<img src='" + path + "' width='150' />");
        $('#' + id + "_imgSrc").val(path);
//        $('#' + id + "_sequenceNum").val(id.substr(6));
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
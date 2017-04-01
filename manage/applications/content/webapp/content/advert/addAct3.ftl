<link href="/images/themes/common/common.css" rel="stylesheet">
<script type="text/javascript" src="/images/jquery/plugins/artDialog/artDialog.source.js"></script>
<script type="text/javascript" src="/images/jquery/plugins/artDialog/iframeTools.js"></script>
<form action="<@ofbizUrl>createAct3</@ofbizUrl>" method="post">
    <input type="hidden" name="templateId" value="${parameters.siteIndexTemplateId}">
    <div class="row">
        <div class="control-label col-md-2">活动图片名称:</div>
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
                <th>序号</th>
                <th>图片</th>
                <th>标题</th>
                <th>副标题</th>
                <th>链接类型</th>
                <th>链接取值</th>
            </tr>
            <#list  1..3 as seq>
            <tr>
                <input type="hidden" name="upload${seq}_type" id="upload${seq}_type"  value="act3"/>
                <td>${seq}<br/>
                    是否秒杀
                    <select id="upload${seq}_miaosha" name="upload${seq}_miaosha" class="upload_miaosha">
                        <option value="y">是</option>
                        <option value="n" selected>否</option>
                    </select>
                </td>
                <td>
                    <div id="upload${seq}_div">
                        <input type="hidden" id="upload${seq}_imgSrc" name="upload${seq}_1_imgSrc"/>
                        <input type="hidden" id="upload${seq}_sequenceNum" name="upload${seq}_1_sequenceNum" value="${seq}"/>
                        <a href="javascript:void(0)" id="upload${seq}" class="uploadbanner btn btn-primary">选择图片</a>
                    </div>
                    <table class="table table-bordered upload_ma" id="upload${seq}_ma">
                        <tr>
                            <td>
                                <input type="hidden" id="upload${seq}_1_imgSrc" name="upload${seq}_1_imgSrc"/>
                                <input type="hidden" id="upload${seq}_1_sequenceNum" name="upload${seq}_1_sequenceNum" value="${seq}"/>
                                <a href="javascript:void(0)" id="upload${seq}_1" class="uploadbanner btn btn-primary">选择图片</a>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="hidden" id="upload${seq}_2_imgSrc" name="upload${seq}_2_imgSrc"/>
                                <input type="hidden" id="upload${seq}_2_sequenceNum" name="upload${seq}_2_sequenceNum" value="${seq}"/>
                                <a href="javascript:void(0)" id="upload${seq}_2" class="uploadbanner btn btn-primary">选择图片</a>

                            </td>
                        </tr>
                        <tr>
                            <td>
                                <input type="hidden" id="upload${seq}_3_imgSrc" name="upload${seq}_3_imgSrc"/>
                                <input type="hidden" id="upload${seq}_3_sequenceNum" name="upload${seq}_3_sequenceNum" value="${seq}"/>
                                <a href="javascript:void(0)" id="upload${seq}_3" class="uploadbanner btn btn-primary">选择图片</a>

                            </td>
                        </tr>
                    </table>

                </td>
                <td><input type="text" name="upload${seq}_advertContentName" id="upload${seq}_advertContentName" class="form-control"/> </td>
                <td><input type="text" name="upload${seq}_description" id="upload${seq}_description" class="form-control"/> </td>
                <td>
                    <select id="upload${seq}_linkType" name="upload${seq}_linkType" class="uploadLinkType">
                        <option value="activityDetail">活动页面</option>
                        <option value="channelPage">专题页面</option>
                        <option value="storeList">店铺列表</option>
                        <option value="storeListByKey">关键字搜索</option>
                        <option value="store">店铺</option>
                        <option value="productDetail">产品详细</option>
                        <option value="web">链接地址</option>
                        <option value="miaosha">秒杀</option>
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

        $('.upload_ma').hide();

        $('.upload_miaosha').change(function () {
            var ye = $(this).val();
            console.log(ye);
            if(ye == 'y'){
                var id = this.id;
                var seq = id.substr(6,1);
                console.log(seq);
                $('#upload'+seq+"_div").hide();
                $('#upload'+seq+"_ma").show();
                $('#upload'+seq+"_type").val('miaosha');
            }else if(ye == 'n'){
                var id = this.id;
                var seq = id.substr(6,1);
                $('#upload'+seq+"_div").show();
                $('#upload'+seq+"_ma").hide();
                $('#upload'+seq+"_type").val('act3');
            }
        })

    });


    function saveChoooseImage(path, id) {
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








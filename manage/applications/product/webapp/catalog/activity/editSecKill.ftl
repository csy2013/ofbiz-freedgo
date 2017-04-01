<form class="form-horizontal dp-validateForm" role="form" method="post" name="addSecKill" action="<@ofbizUrl>addGroupOrder</@ofbizUrl>">
    <input type="hidden" name="activityId" id="activityId" value="${request.getParameter('activityId')}"/>
    <input type="hidden" name="activityPayType" value="FULL_PAY" id="activityPayType"/>
<@htmlScreenTemplate.renderScreenletBegin id="" title="团购活动基本信息" collapsible="" showMore=true/>

    <div class=" pull-right">
    <#if security.hasEntityPermission("PRODPROMO_SECKILL", "_UPDATE", session)>
    <#--<button class="btn btn-danger btn-flat" onclick="deleteSecKill(${request.getParameter("activityId")})">删除</button>-->
        <button class="btn btn-success btn-flat" onclick="updateSecKill()">修改</button></#if>
    </div>

    <div class="row">
        <div class="form-group col-sm-6">
            <label for="title" class="col-sm-3 control-label">使用店铺</label>

            <div class="col-sm-9">
                <select class="form-control" id="productStoreId" multiple name="productStoreId">
                <#list productStores as productStore>
                    <option value="${(productStore.productStoreId)?if_exists}">${(productStore.storeName)?if_exists}</option>
                </#list>
                </select>

                <p class="dp-error-msg"></p>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-sm-6">
            <label for="title" class="col-sm-3 control-label">活动类型</label>

            <div class="col-sm-9">
                <select class="form-control" id="activityType" name="activityType" disabled>
                <#list activityTypeEnums as activityTypeEnum>
                    <option value="${(activityTypeEnum.enumId)?if_exists}"
                            <#if activityTypeEnum.enumId == 'SEC_KILL'>selected</#if>>${(activityTypeEnum.get("description",locale))?if_exists}</option>
                </#list>
                </select>

                <p class="dp-error-msg"></p>
            </div>
        </div>
        <div class="form-group col-sm-6">
            <label for="subTitle" class="col-sm-3 control-label">活动状态</label>

            <div class="col-sm-9">
                <select class="form-control" id="activityAuditStatus" name="activityAuditStatus" disabled>
                <#list activityStatusEnums as activityStatusEnum>
                    <option value="${(activityStatusEnum.enumId)?if_exists}">${(activityStatusEnum.get("description",locale))?if_exists}</option>
                </#list>
                </select>

                <p class="dp-error-msg"></p>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-sm-6" data-type="required" data-mark="活动编码">
            <label for="title" class="col-sm-3 control-label"><i class="required-mark">*</i>活动编码</label>

            <div class="col-sm-9">
                <input type="text" class="form-control dp-vd" id="activityCode" name="activityCode">

                <p class="dp-error-msg"></p>
            </div>
        </div>
        <div class="form-group col-sm-6" data-type="required" data-mark="活动名称">
            <label for="subTitle" class="col-sm-3 control-label"><i class="required-mark">*</i>活动名称</label>

            <div class="col-sm-9">
                <input type="text" class="form-control dp-vd" id="activityName" name="activityName">

                <p class="dp-error-msg"></p>
            </div>
        </div>
    </div>


            <div class="row">
                <div class="form-group col-sm-6" data-type="linkLt" id="publishDateGroup" data-compare-link="startTimeGroup" data-mark="发布时间" data-compare-mark="销售开始时间">
                    <label for="publishDate" class="col-sm-3 control-label"><i class="required-mark">*</i>发布时间</label>

            <div class="input-group date form_datetime col-sm-9 p-l-15 p-r-15" data-link-field="startTime">
            <@htmlTemplate.renderDateTimeField name="publishDate" id="publishDate"/>
            </div>
            <div class="dp-error-msg col-sm-offset-2 col-sm-10"></div>
        </div>

                <div id="endGroup" class="form-group col-sm-6" data-type="linkGt" data-compare-link="publishDateGroup" data-mark="下架时间" data-compare-mark="发布时间">
                    <label for="endDate" class="col-sm-3 control-label"><i class="required-mark">*</i>下架时间</label>

            <div class="input-group date form_datetime col-sm-9 p-l-15 p-r-15" data-link-field="endTime">
            <@htmlTemplate.renderDateTimeField name="endDate" id="endDate"/>
            </div>
            <div class="dp-error-msg col-sm-offset-2 col-sm-10"></div>
        </div>
    </div>


    <div class="row">
                <div class="form-group col-sm-6" data-type="linkLt" id="startTimeGroup" data-compare-link="endTimeGroup" data-mark="销售开始时间" data-compare-mark="销售结束时间">
            <label for="startTime" class="col-sm-3 control-label"><i class="required-mark">*</i>销售开始时间</label>

            <div class="input-group date form_datetime col-sm-9 p-l-15 p-r-15" data-link-field="startTime">
            <@htmlTemplate.renderDateTimeField name="activityStartDate" id="activityStartDate"/>
            </div>
            <div class="dp-error-msg col-sm-offset-2 col-sm-10"></div>
        </div>
                <div id="endTimeGroup" class="form-group col-sm-6" data-type="linkLt" data-compare-link="endGroup" data-mark="销售结束时间" data-compare-mark="下架时间">
            <label for="endTime" class="col-sm-3 control-label"><i class="required-mark">*</i>销售结束时间</label>

            <div class="input-group date form_datetime col-sm-9 p-l-15 p-r-15" data-link-field="endTime">
            <@htmlTemplate.renderDateTimeField name="activityEndDate" id="activityEndDate"/>
            </div>
            <div class="dp-error-msg col-sm-offset-2 col-sm-10"></div>
        </div>
    </div>


    <div class="row">
        <div class="form-group col-sm-6" data-type="required" data-mark="单个ID限购数量">
            <label for="title" class="col-sm-3 control-label"><i class="required-mark">*</i>单个ID限购数量</label>

            <div class="col-sm-9">
                <input type="text" class="form-control dp-vd" id="limitQuantity" name="limitQuantity">

                <p class="dp-error-msg"></p>
            </div>
        </div>
        <div class="form-group col-sm-6" data-type="required" data-mark="活动总数量">
            <label for="subTitle" class="col-sm-3 control-label"><i class="required-mark">*</i>活动总数量</label>

            <div class="col-sm-9">
                <input type="text" class="form-control dp-vd" id="activityQuantity" name="activityQuantity">

                <p class="dp-error-msg"></p>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="form-group col-sm-6">
            <label for="title" class="col-sm-3 control-label"><i class="required-mark">*</i>团购商品</label>

            <div class="col-sm-9">
                <@htmlTemplate.lookupField formName="addSecKill" position="center" name="productId" id="productId" fieldFormName="LookupProduct"/>
                <p class="dp-error-msg"></p>
            </div>

        </div>

        <div class="form-group col-sm-6">
            <label for="subTitle" class="col-sm-3 control-label"><i class="required-mark">*</i>配送方式</label>

            <div class="col-sm-9">
                <select class="form-control" id="shipmentType" name="shipmentType">
                <#list activityShipmentEnums as activityShipmentEnum>
                    <option value="${(activityShipmentEnum.enumId)?if_exists}">${(activityShipmentEnum.get("description",locale))?if_exists}</option>
                </#list>
                </select>

                <p class="dp-error-msg"></p>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="row">
            <div class="form-group col-sm-6">
                <label for="subTitle" class="col-sm-3 control-label"><i class="required-mark">*</i>秒杀价格</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control dp-vd" name="productPrice" id="productPrice">

                    <p class="dp-error-msg"></p>
                </div>

            </div>
            <div class="form-group col-sm-6">
                <label for="title" class="col-sm-3 control-label">最多可抵扣的积分</label>

                <div class="col-sm-9">
                    <input type="text" class="form-control dp-vd" name="scoreValue" id="scoreValue">

                    <p class="dp-error-msg"></p>
                </div>
            </div>
        <#--秒杀支付条件为全部-->
        </div>
    </div>

    <div class="row">
                <div class="form-group col-sm-6" data-type="linkLt" data-compare-link="virutalEndTimeGroup" data-mark="虚拟商品有效期自" data-compare-mark="虚拟商品有效期至">
            <label for="virtualStartTime" class="col-sm-3 control-label"><i class="required-mark">*</i>虚拟商品有效期自</label>

            <div class="input-group date form_datetime col-sm-9 p-l-15 p-r-15" data-link-field="startTime">
            <@htmlTemplate.renderDateTimeField name="virtualProductStartDate" id="virtualProductStartDate"/>
            </div>
            <div class="dp-error-msg col-sm-offset-2 col-sm-10"></div>
        </div>
        <div id="virtualEndTimeGroup" class="form-group col-sm-6">
            <label for="endTime" class="col-sm-3 control-label"><i class="required-mark">*</i>虚拟商品有效期至</label>

            <div class="input-group date form_datetime col-sm-9 p-l-15 p-r-15" data-link-field="endTime">
            <@htmlTemplate.renderDateTimeField name="virtualProductEndDate" id="virtualProductEndDate"/>
            </div>
            <div class="dp-error-msg col-sm-offset-2 col-sm-10"></div>
        </div>
    </div>


    <div class="row">
        <div class="form-group col-sm-12">
            <label class="col-sm-2 control-label">团购选项</label>

            <div class="col-sm-10">
                <div class="checkbox clearfix">
                    <label class="col-sm-3" title="随时退"><input name="isAnyReturn" id="isAnyReturn" type="checkbox" value="Y">随时退</label>
                    <label class="col-sm-3" title="支持过期退"><input name="isSupportOverTimeReturn" id="isSupportOverTimeReturn" value="Y" type="checkbox">支持过期退</label>
                    <label class="col-sm-3" title="动可积分"><input name="isSupportScore" id="isSupportScore" type="checkbox" value="Y">活动可积分</label>
                    <label class="col-sm-3" title="退货返回积分"><input name="isSupportReturnScore" id="isSupportReturnScore" value="Y" type="checkbox">退货返回积分</label>
                    <label class="col-sm-3" title="推荐到首页"><input name="isShowIndex" id="isShowIndex" type="checkbox" value="Y">推荐到首页</label>
                </div>
                <div class="dp-error-msg"></div>
            </div>
        </div>
    </div>

    <div class="row p-l-10 p-r-10">
        <div class="form-group">
            <label for="seo" class="col-sm-3 control-label">活动描述</label>
            <div class="col-sm-6">
                <textarea class="form-control" name="activityDesc" id="activityDesc" rows="6"></textarea>
                <p class="dp-error-msg"></p>
            </div>
        </div>
    </div>
<@htmlScreenTemplate.renderScreenletEnd/>
<@htmlScreenTemplate.renderScreenletBegin id="" title="团购规则设置" showMore=true/>
    <button class="btn-default pull-right" onclick="addGroupOrderRule();">新增</button>


    <div class="table-responsive no-padding">
        <table class="table table-hover" id="groupOrderRuleTable">
            <tr>
                <th>序号</th>
                <th>阶梯编号</th>
                <th>团购人数</th>
                <th>团购价格</th>
                <th>操作</th>
            </tr>
        </table>
    </div>
<@htmlScreenTemplate.renderScreenletEnd/>

<@htmlScreenTemplate.renderScreenletBegin id="" title="参加的会员等级" showMore=true/>

    <div class="table-responsive no-padding">
        <table class="table table-hover" id="partyLevelTable">
            <tr>
                <th>序号</th>
                <th>会员等级</th>
                <th>参加标志</th>
            </tr>
        <#list partyLevels as partyLevel>
            <tr>
                <td>${partyLevel_index}</td>
                <td>${partyLevel.levelName}</td>
                <td><input class="js-checkchild" type="checkbox" name="partyLevels" value="${partyLevel.levelId}:${partyLevel.levelName}"></td>
            </tr>
        </#list>

        </table>
    </div>
<@htmlScreenTemplate.renderScreenletEnd/>
<@htmlScreenTemplate.renderScreenletBegin id="" title="参加的社区" showMore=true/>
    <div class="box-body table-responsive no-padding">
        <table class="table table-hover" id="communitTable">
            <tr>
                <th>序号</th>
                <th>社区编号</th>
                <th>社区名称</th>
                <th>操作</th>
            </tr>
        <#list communities as communit>
            <tr>
                <td>${communit_index}</td>
                <td>${communit.code}</td>
                <td>${communit.name}</td>
                <td><input class="js-checkchild" type="checkbox" name="areas" value="${communit.communityId}:${communit.name}"></td>
            </tr>
        </#list>
        </table>
    </div>
<@htmlScreenTemplate.renderScreenletEnd/>
</form>
<!-- 提示弹出框start -->
<div id="modal_msg" class="modal fade " tabindex="-1" role="dialog" aria-labelledby="modal_add_title">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="modal_msg_title">操作提示</h4>
            </div>
            <div class="modal-body">
                <h4 id="modal_msg_body"></h4>
            </div>
            <div class="modal-footer">
                <button id="ok" type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
            </div>
        </div>
    </div>
</div><!-- 提示弹出框end -->

<!-- add user Modal -->
<div id="modal_add" class="modal fade " tabindex="-1" role="dialog" aria-labelledby="modal_add_title">>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增团购规则</h4>
            </div>
            <div class="modal-body">


                <div class="form-group" data-type="required" data-mark="阶梯编号">
                    <label for="orderGouprRuleId" class="control-label col-sm-3"><span class="text-danger">*</span>阶梯编号</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control required" name="orderGouprRuleId" id="orderGouprRuleId">
                    </div>
                    <span id="usertip"></span>
                </div>
                <div class="form-group" data-type="required" data-mark="团购人数">
                    <label for="orderGouprRulePersonNum" class="control-label col-sm-3"><span class="text-danger">*</span>团购人数</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control required" name="orderGouprRulePersonNum" id="orderGouprRulePersonNum">
                    </div>
                </div>

                <div class="form-group" data-type="required" data-mark="团购价格">
                    <label for="orderGouprRulePersonAmount" class="control-label col-sm-3"><span class="text-danger">*</span>团购价格</label>

                    <div class="col-sm-9">
                        <input type="text" class="form-control required" name="orderGouprRulePersonAmount" id="orderGouprRulePersonAmount">
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveSecKillRule();">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){

    //加载团购信息
    $.ajax(
            {
                url: "productActivityDetail",
                type: "POST",
                data: {activityId: ${request.getParameter('activityId')}},
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    if (data) {
                        if (data.productStoreIds) {
                            for (var i = 0; i < data.productStoreIds.length; i++) {
                                var productStoreId = data.productStoreIds[i];
                                $('#productStoreId').val(productStoreId);
                            }
                        }
                        $('#activityTypee').val(data.activityTypee);
                        $('#activityAuditStatus').val(data.activityAuditStatus);
                        $('#activityCode').val(data.d_activityCode);
                        $('#activityName').val(data.d_activityName);
                        $('#activityDesc').val(data.d_activityDesc);
                        $('#publishDate_i18n').val(data.d_publishDate);
                        $('#endDate_i18n').val(data.d_endDate);
                        $('#publishDate').val(data.d_publishDate);
                        $('#endDate').val(data.d_endDate);
                        $('#activityStartDate_i18n').val(data.d_activityStartDate);
                        $('#activityEndDate_i18n').val(data.d_activityEndDate);
                        $('#activityStartDate').val(data.d_activityStartDate);
                        $('#activityEndDate').val(data.d_activityEndDate);
                        $('#limitQuantity').val(data.d_limitQuantity);
                        $('#activityQuantity').val(data.d_activityQuantity);
                        $("input[name='productId']").val(data.productId);
                        $('#shipmentType').val(data.shipmentType);
                        $('#scoreValue').val(data.d_scoreValue);
                        $('#activityPayType').val(data.activityPayType);

                        $('#productPrice').val(data.d_productPrice);
                        $('#d_virtualProductStartDate').val(data.d_virtualProductStartDate);
                        $('#d_virtualProductEndDate').val(data.d_virtualProductEndDate);
                        $('#virtualProductStartDate_i18n').val(data.d_virtualProductStartDate);
                        $('#virtualProductEndDate_i18n').val(data.d_virtualProductEndDate);
                        if (data.d_isAnyReturn == 'Y') {
                            $('#isAnyReturn').eq(0).attr("checked", true);
                        }
                        if (data.d_isSupportOverTimeReturn == 'Y') {
                            $('#isSupportOverTimeReturn').eq(0).attr("checked", true);
                        }
                        if (data.d_isSupportScore == 'Y') {
                            $('#isSupportScore').eq(0).attr("checked", true);
                        }
                        if (data.d_isSupportReturnScore == 'Y') {
                            $('#isSupportReturnScore').eq(0).attr("checked", true);
                        }
                        if (data.d_isShowIndex == 'Y') {
                            $('#isShowIndex').eq(0).attr("checked", true);
                        }
                        if (data.d_productActivityPartyLevels) {
                            for (var i = 0; i < data.d_productActivityPartyLevels.length; i++) {
                                var obj = data.d_productActivityPartyLevels[i];
                                var levelId = obj.levelId;
                                var levelName = obj.levelName;
                                $("input[name='partyLevels']").each(function (i, obj) {
                                    console.log(obj);
                                    console.log(levelId + ":" + levelName);
                                    var val = $(obj).val();
                                    if (val == levelId + ":" + levelName) {
                                        $(obj).attr("checked", true);
                                    }
                                })
                            }

                        }


                        if (data.d_productActivityAreas) {
                            for (var i = 0; i < data.d_productActivityAreas.length; i++) {
                                var obj = data.d_productActivityAreas[i];
                                var communityId = obj.communityId;
                                var communityName = obj.communityName;
                                $("input[name='areas']").each(function (i, obj) {
                                    var val = $(obj).val();
                                    if (val == communityId + ":" + communityName) {
                                        $(obj).attr("checked", true);
                                    }
                                })
                            }
                        }
                    }
                },
                error: function (data) {
                    //设置提示弹出框内容
                    tipLayer("操作失败！");

                }
            }
    )

    })
    ;


    function updateSecKill() {
        event.preventDefault();


        var levels = "";
        $("input[name='partyLevels']").each(function () {
            if (this.checked) {
                console.log($(this).val());
                levels += $(this).val() + ","
            }
        });

        if (levels == '') {
            tipLayer("请设置参加的会员等级！");

            return;
        }
        if (levels.substring(levels.length - 1) == ',') {
            levels = levels.substring(0, levels.length - 1);
        }

        console.log("levels=" + levels);

        var areas = "";
        $("input[name='areas']").each(function () {
            if (this.checked) {
                areas += $(this).val() + ","
            }
        });

        if (areas == '') {
            tipLayer("请设置参加的社区！");

            return;
        }
        if (areas.substring(areas.length - 1) == ',') {
            areas = areas.substring(0, areas.length - 1);
        }

        console.log("areas=" + areas);
        var isAnyReturn = 'N';
        if ($('#isAnyReturn').is(':checked')) {
            isAnyReturn = 'Y';
        }
        var isSupportOverTimeReturn = 'N';
        if ($('#isSupportOverTimeReturn').is(':checked')) {
            isSupportOverTimeReturn = 'Y';
        }
        var isSupportScore = 'N';
        if ($('#isSupportScore').is(':checked')) {
            isSupportScore = 'Y';
        }
        var isSupportReturnScore = 'N';
        if ($('#isSupportReturnScore').is(':checked')) {
            isSupportReturnScore = 'Y';
        }
        var isShowIndex = 'N';
        if ($('#isShowIndex').is(':checked')) {
            isShowIndex = 'Y';
        }
        console.log($('#isAnyReturn'));
        console.log($('#isSupportOverTimeReturn'));
        console.log($('#isSupportScore'));
        console.log($('#isSupportReturnScore').attr('checked'));
        console.log($('#isShowIndex').attr('checked'));
        console.log($('#activityPayType').val());

        if ($('#activityPayType').val() == 'PART_PAY') {

            if ($('#productPrice').val() == '') {
                alert('请输入定金价格');
                $('#productPrice').focus();
                return ''
            }
        }

        var content = CKEDITOR.instances['activityDesc'].FContent.getData();
        console.log(content);

        $.ajax({
            url: "updateSecKill",
            type: "POST",
            async: false,
            data: {
                activityId: $('#activityId').val(),
                productStoreIds: $('#productStoreId').val().join(','),
                activityCode: $('#activityCode').val(),
                activityType: $('#activityType').val(),
                activityAuditStatus: $('#activityAuditStatus').val(),
                activityStatus: $('#activityStatus').val(),
                activityName: $('#activityName').val(),
                publishDate: $('#publishDate').val(),
                endDate: $('#endDate').val(),
                activityStartDate: $('#activityStartDate').val(),
                activityEndDate: $('#activityEndDate').val(),
                limitQuantity: $('#limitQuantity').val(),
                activityQuantity: $('#activityQuantity').val(),
                productId: $("input[name='productId']").val(),
                shipmentType: $('#shipmentType').val(),
                activityPayType: $('#activityPayType').val(),
                scoreValue: $('#scoreValue').val(),
                productPrice: $('#productPrice').val(),
                virtualProductStartDate: $('#virtualProductStartDate').val(),
                virtualProductEndDate: $('#virtualProductEndDate').val(),
                isAnyReturn: isAnyReturn,
                isSupportOverTimeReturn: isSupportOverTimeReturn,
                isSupportScore: isSupportScore,
                isSupportReturnScore: isSupportReturnScore,
                isShowIndex: isShowIndex,
                activityDesc: $('#activityDesc').val(),
                productActivityPartyLevels: levels,
                productActivityAreas: areas

            },
            dataType: "json",
            success: function (data) {
                //设置提示弹出框内容
                tipLayer("操作成功！");

                //提示弹出框隐藏事件，隐藏后重新加载当前页面
                $('#tipLayer').on('hide.bs.modal', function () {
                    window.location.href = '<@ofbizUrl>findSecKill</@ofbizUrl>';
                })
            },
            error: function (data) {

                //设置提示弹出框内容
                tipLayer("操作失败！");

                $('#tipLayer').on('hide.bs.modal', function () {
                    window.location.reload();
                })
            }
        })
    }

    function deleteSecKill(id) {
        $.ajax({
            url: 'deleteSecKill',
            data: {activityId: id},
            type: "POST",
            async: false,
            dataType: "json",
            success: function (data) {
                if (data) {
                    //设置提示弹出框内容
                    tipLayer("操作成功！");

                    //提示弹出框隐藏事件，隐藏后重新加载当前页面
                    $('#tipLayer').on('hide.bs.modal', function () {
                        window.location.href=<@ofbizUrl>findSecKill</@ofbizUrl>;
                    })
                }
            },
            error: function (data) {

                //设置提示弹出框内容
                tipLayer("操作失败！");

                $('#tipLayer').on('hide.bs.modal', function () {
                    window.location.reload();
                })
            }

        })
    }


</script>

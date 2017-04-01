<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.secKillList}" collapsible="" showMore=true/>

        <form class="form-inline clearfix" role="form" method="post" action="<@ofbizUrl>findSecKill</@ofbizUrl>">
            <input type="hidden" name="lookupFlag" value="Y">
            <input type="hidden" name="activityType" value="SEC_KILL"/>
            <div class="form-group">
                <div class="input-group m-b-10">
                    <span class="input-group-addon">${uiLabelMap.activityCode}</span>
                    <input type="text" class="form-control" name="activityCode" placeholder="${uiLabelMap.activityCode}" value="${activityCode?default("")}">
                </div>
                <div class="input-group m-b-10">
                    <span class="input-group-addon">${uiLabelMap.activityName}</span>
                    <input type="text" class="form-control" name="activityName" placeholder="${uiLabelMap.activityName}" value="${activityName?default("")}">
                </div>
                <div class="input-group m-b-10">
                    <span class="input-group-addon">${uiLabelMap.activityAuditStatus}</span>
                    <select class="form-control" id="activityAuditStatus" name="activityAuditStatus">
                    <#list activityStatusEnums as activityStatusEnum>
                        <option value="${(activityStatusEnum.enumId)?if_exists}"
                                <#if (activityAuditStatus?default("")== activityStatusEnum.enumId)||(activityStatusEnum.enumId =='ACTY_AUDIT_INIT')>selected</#if>>${(activityStatusEnum.get("description",locale))?if_exists}</option>
                    </#list>
                    </select>
                </div>
                <div class="input-group m-b-10">
                    <span class="input-group-addon">${uiLabelMap.activityStartDate}</span>

            <div class="input-group date form_datetime col-sm-10 p-l-15 p-r-15" data-link-field="activityStartDate">
            <@htmlTemplate.renderDateTimeField name="activityStartDate" id="activityStartDate"/>
            </div>
        </div>
        <div class="input-group m-b-10">
            <span class="input-group-addon">${uiLabelMap.activityEndDate}</span>

            <div class="input-group date form_datetime col-sm-10 p-l-15 p-r-15" data-link-field="activityEndDate">
            <@htmlTemplate.renderDateTimeField name="activityEndDate" id="activityEndDate"/>
            </div>
        </div>
    </div>
    <div class="input-group pull-right">
    <#if security.hasEntityPermission("PRODPROMO_GROUPORDER", "_VIEW", session)>
        <button class="btn btn-success btn-flat">查询</button></#if>
    </div>
</form>
<hr/>
<div class="cut-off-rule bg-gray"></div>
<div class="row m-b-10">
    <div class="col-sm-6">
        <div class="dp-tables_btn">
        <#if security.hasEntityPermission("PRODPROMO_SECKILL", "_ADD", session)>
            <a class="btn btn-primary" href="<@ofbizUrl>addSecKill</@ofbizUrl>" ;>
            ${uiLabelMap.CommonAdd}
            </a>
        </#if>
        </div>
    </div>
<#if listIt?has_content>

            <div class="col-sm-6">
                <div class="dp-tables_length">
                    <label>
                        每页显示
                        <select id="dp-tables_length" name="tables_length" class="form-control input-sm"
                                onchange="location.href='${commonUrl}&amp;VIEW_SIZE='+this.value+'&amp;VIEW_INDEX=0'">
                            <option value="1" <#if viewSize ==1>selected</#if>>1</option>
                            <option value="2" <#if viewSize==2>selected</#if>>2</option>
                            <option value="3" <#if viewSize==3>selected</#if>>3</option>
                            <option value="4" <#if viewSize==4>selected</#if>>4</option>
                        </select>
                        条
                    </label>
                </div>

            </div>
        </#if>
        </div>


    <#if groupList?has_content>
        <div class="row">
            <div class="col-sm-12">
        <table class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th><input class="js-allcheck" type="checkbox"></th>
                        <th>${uiLabelMap.activityCode}
                            <#if orderFiled?default("") =='activityCode'>
                                <#if orderBy =='DESC'>
                            <a href="${commonUrl}ORDER_FILED=activityCode&amp;ORDER_BY=ASC"></a>
                                <#else>
                            <a href="${commonUrl}ORDER_FILED=activityCode&amp;ORDER_BY=ASC"
                                </#if>
                            <#else>
                        <a href="${commonUrl}ORDER_FILED=activityCode&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>

                        <th>${uiLabelMap.activityName}
                            <#if orderFiled?default("") =='activityName'>
                                <#if orderBy =='DESC'>
                            <a href="${commonUrl}ORDER_FILED=activityName&amp;ORDER_BY=ASC"></a>
                                <#else>
                            <a href="${commonUrl}ORDER_FILED=activityName&amp;ORDER_BY=ASC"
                                </#if>
                            <#else>
                        <a href="${commonUrl}ORDER_FILED=activityName&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>

                        <th>${uiLabelMap.activityType}
                            <#if orderFiled?default("") =='activiType'>
                                <#if orderBy =='DESC'>
                            <a href="${commonUrl}ORDER_FILED=activityType&amp;ORDER_BY=ASC"></a>
                                <#else>
                            <a href="${commonUrl}ORDER_FILED=activityType&amp;ORDER_BY=ASC"
                                </#if>
                            <#else>
                        <a href="${commonUrl}ORDER_FILED=activityType&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>
                        <th>${uiLabelMap.activityStartDate}
                            <#if orderFiled?default("") =='activiStartDate'>
                                <#if orderBy =='DESC'>
                            <a href="${commonUrl}ORDER_FILED=activityStartDate&amp;ORDER_BY=ASC"></a>
                                <#else>
                            <a href="${commonUrl}ORDER_FILED=activityStartDate&amp;ORDER_BY=ASC"
                                </#if>
                            <#else>
                        <a href="${commonUrl}ORDER_FILED=activityStartDate&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>
                        <th>${uiLabelMap.activityEndDate}
                            <#if orderFiled?default("") =='activiEndDate'>
                                <#if orderBy =='DESC'>
                            <a href="${commonUrl}ORDER_FILED=activityEndDate&amp;ORDER_BY=ASC"></a>
                                <#else>
                            <a href="${commonUrl}ORDER_FILED=activityEndDate&amp;ORDER_BY=ASC"
                                </#if>
                            <#else>
                        <a href="${commonUrl}ORDER_FILED=activityEndDate&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>

                        <th>${uiLabelMap.activityAuditStatus}
                            <#if orderFiled?default("") =='activiAuditStatus'>
                                <#if orderBy =='DESC'>
                            <a href="${commonUrl}ORDER_FILED=activityAuditStatus&amp;ORDER_BY=ASC"></a>
                                <#else>
                            <a href="${commonUrl}ORDER_FILED=activityAuditStatus&amp;ORDER_BY=ASC"
                                </#if>
                            <#else>
                        <a href="${commonUrl}ORDER_FILED=activityAuditStatus&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>

                        <th>${uiLabelMap.hasGroup}
                            <#if orderFiled?default("") =='hasGroup'>
                                <#if orderBy =='DESC'>
                            <a href="${commonUrl}ORDER_FILED=hasGroup&amp;ORDER_BY=ASC"></a>
                                <#else>
                            <a href="${commonUrl}ORDER_FILED=hasGroup&amp;ORDER_BY=ASC"
                                </#if>
                            <#else>
                        <a href="${commonUrl}ORDER_FILED=hasGroup&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>

                        <th>${uiLabelMap.leaveQuantity}
                            <#if orderFiled?default("") =='leaveQuantity'>
                                <#if orderBy =='DESC'>
                            <a href="${commonUrl}ORDER_FILED=leaveQuantity&amp;ORDER_BY=ASC"></a>
                                <#else>
                            <a href="${commonUrl}ORDER_FILED=leaveQuantity&amp;ORDER_BY=ASC"
                                </#if>
                            <#else>
                        <a href="${commonUrl}ORDER_FILED=leaveQuantity&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>

                        <th>${uiLabelMap.CommonEmptyHeader}</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list groupList as partyRow>
                        <tr>
                            <td><input class="js-checkchild" type="checkbox"></td>
                            <td><#if partyRow.containsKey("activityCode")>${partyRow.activityCode?default("N/A")}</#if></td>
                            <td><#if partyRow.containsKey("activityName")>${partyRow.activityName?default("N/A")}</#if></td>
                            <td><#if partyRow.containsKey("activityType")>
                              <#list activityTypeEnums as activityTypeEnum>
                                <#if activityTypeEnum.enumId == partyRow.activityType >${(activityTypeEnum.get("description",locale))?if_exists}</#if>
                            </#list>
                            </#if>
                            </td>
                            <td><#if partyRow.containsKey("activityStartDate")>${partyRow.activityStartDate?default("N/A")?string('yyyy-MM-dd hh:mm')}</#if></td>
                            <td><#if partyRow.containsKey("activityEndDate")>${partyRow.activityEndDate?default("N/A")}</#if></td>
                            <td><#if activityAuditStatus?default(partyRow.containsKey("activityAuditStatus"))?has_content>
                             <#list activityStatusEnums as activityStatusEnum>
                                <#if activityStatusEnum.enumId == partyRow.activityAuditStatus >${(activityStatusEnum.get("description",locale))?if_exists}</#if>
                            </#list>
                        </#if>
                            <td><#if partyRow.containsKey("hasGroup")>${partyRow.hasGroup?default("N/A")}</#if></td>
                            <td><#if partyRow.containsKey("leaveQuantity")>${partyRow.leaveQuantity?default("N/A")}</#if></td>


                            <td>
                                <div class="btn-group">
                                    <#if security.hasEntityPermission("PRODPROMO_SECKILL", "_DETAIL", session)>
                                        <button type="button" class="btn btn-danger btn-sm" onclick="activityDetail('${partyRow.activityId?default("N/A")}')">查看</button>
                                    </#if>
                                    <button type="button" class="btn btn-danger btn-sm dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                        <span class="caret"></span>
                                        <span class="sr-only">Toggle Dropdown</span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <#if (security.hasEntityPermission("PRODPROMO_SECKILL", "_UPDATE", session) && (activityAuditStatus == 'ACTY_AUDIT_INIT' ||
                                        activityAuditStatus == 'ACTY_AUDIT_PASS'||activityAuditStatus == 'ACTY_AUDIT_NOPASS'||activityAuditStatus == 'ACTY_AUDIT_PUBING'
                                        ||activityAuditStatus == 'ACTY_AUDIT_UNBEGIN'))>
                                            <li><a href="<@ofbizUrl>editSecKill?activityId=${partyRow.activityId}</@ofbizUrl>">编辑</a></li>
                                        </#if>
                                        <#if (security.hasEntityPermission("PRODPROMO_SECKILL", "_UPDATE", session) && (partyRow.activityAuditStatus == 'ACTY_AUDIT_INIT'))>
                                            <li><a href="#" onclick="auditSecKill(${partyRow.activityId})">审批</a></li>
                                        </#if>
                                        <#if (security.hasEntityPermission("PRODPROMO_SECKILL", "_DELETE", session) && (activityAuditStatus == 'ACTY_AUDIT_INIT'||
                                        activityAuditStatus == 'ACTY_AUDIT_PASS'||activityAuditStatus == 'ACTY_AUDIT_NOPASS'||activityAuditStatus == 'ACTY_AUDIT_PUBING'
                                        || activityAuditStatus == 'ACTY_AUDIT_UNBEGIN'))>
                                            <li><a href="#" onclick="deleteSecKill(${partyRow.activityId})">删除</a></li>
                                        </#if>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>


    <#--<#if viewIndex ==-->
        <#include "component://common/webcommon/includes/htmlTemplate.ftl"/>
        <#assign viewIndexFirst = 0/>
        <#assign viewIndexPrevious = viewIndex - 1/>
        <#assign viewIndexNext = viewIndex + 1/>
        <#assign viewIndexLast = Static["org.ofbiz.base.util.UtilMisc"].getViewLastIndex(groupListSize, viewSize) />

        <#assign messageMap = Static["org.ofbiz.base.util.UtilMisc"].toMap("lowCount", lowIndex, "highCount", highIndex, "total", groupListSize)/>
        <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage("CommonUiLabels", "CommonDisplaying", messageMap, locale)/>
        <@nextPrev commonUrl=commonUrl ajaxEnabled=false javaScriptEnabled=false paginateStyle="nav-pager" paginateFirstStyle="nav-first" viewIndex=viewIndex highIndex=highIndex
        listSize=groupListSize viewSize=viewSize ajaxFirstUrl="" firstUrl="" paginateFirstLabel="" paginatePreviousStyle="nav-previous" ajaxPreviousUrl="" previousUrl="" paginatePreviousLabel=""
        pageLabel="" ajaxSelectUrl="" selectUrl="" ajaxSelectSizeUrl="" selectSizeUrl="" commonDisplaying=commonDisplaying paginateNextStyle="nav-next" ajaxNextUrl="" nextUrl=""
        paginateNextLabel="" paginateLastStyle="nav-last" ajaxLastUrl="" lastUrl="" paginateLastLabel="" paginateViewSizeLabel="" />

    <#else>
        <div class="row">
            <div class="col-sm-10">
        <h3>无数据</h3>
            </div>
        </div>
    </#if>

    <!-- /.box-body -->
<@htmlScreenTemplate.renderScreenletEnd/>


<!-- 提示弹出框start -->


<!-- 提示弹出框start -->
<div id="modal_audit" class="modal fade " tabindex="-1" role="dialog" aria-labelledby="modal_audit_title">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="modal_msg_title">秒杀审批</h4>
            </div>
            <div class="modal-body">
                <input type="hidden" name="auditactivityId" id="auditactivityId">
                <div class="box-body">
                    <div class="row">
                        <div class="form-group col-sm-6">
                            <label for="title" class="col-sm-5 control-label">审批结果:</label>

                            <div class="col-sm-7" id="">
                                <label class="labe">
                                    <input type="radio" name="auditResult" value="N"/>审批不通过
                                </label>
                                <label class="labe">
                                    <input type="radio" name="auditResult" value="Y"/>审批通过
                                </label>
                            </div>
                        </div>
                    </div>


                </div>
                <div class="modal-footer">
                    <button id="ok" type="button" class="btn btn-primary" onclick="doAudit();" data-dismiss="modal">确定</button>
                </div>
            </div>
        </div>
    </div>
    </div>
    <!-- 提示弹出框end -->

    <!-- edit user Modal -->
    <div id="modal_detail" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="modal_detail_title">>
        <div class="modal-dialog" style="width: 800px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <div class="text-center">
                    <#--<button type="button" id="aduitBtn" class="btn btn-primary" onclick="audit();">审批</button>-->
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    </div>
                </div>
                <div class="modal-body">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <h3 class="box-title">秒杀活动基本信息</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <div class="form-group col-sm-6">
                                    <label for="title" class="col-sm-5 control-label">使用店铺:</label>

                                    <div class="col-sm-7" id="d_productStoreName"></div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="form-group col-sm-6">
                                    <label for="title" class="col-sm-5 control-label">活动类型:</label>

                                    <div class="col-sm-7" id="d_activityTypeName">

                                    </div>
                                </div>
                                <div class="form-group col-sm-6">
                                    <label for="subTitle" class="col-sm-5 control-label">活动状态:</label>

                                    <div class="col-sm-7" id="d_activityAuditStatusName">

                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-sm-6" data-type="required" data-mark="活动编码">
                                    <label for="title" class="col-sm-5 control-label"><i class="required-mark">*</i>活动编码:</label>

                                    <div class="col-sm-7" id="d_activityCode">

                                    </div>
                                </div>
                                <div class="form-group col-sm-6" data-type="required" data-mark="活动名称">
                                    <label for="subTitle" class="col-sm-5 control-label"><i class="required-mark">*</i>活动名称:</label>

                                    <div class="col-sm-7" id="d_activityName">


                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="form-group col-sm-6">
                                    <label for="publishDate" class="col-sm-5 control-label"><i class="required-mark">*</i>发布时间:</label>

                                    <div class="col-sm-7" id="d_publishDate">

                                    </div>
                                </div>

                                <div id="endGroup" class="form-group col-sm-6">
                                    <label for="endDate" class="col-sm-5 control-label">下架时间:</label>

                                    <div class="col-sm-7" id="d_endDate">

                                    </div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="form-group col-sm-6">
                                    <label for="startTime" class="col-sm-5 control-label">销售开始时间:</label>

                                    <div class="col-sm-7" id="d_activityStartDate">

                                    </div>
                                </div>
                                <div id="endTimeGroup" class="form-group col-sm-6">
                                    <label for="endTime" class="col-sm-5 control-label">销售结束时间:</label>

                                    <div class="col-sm-7" id="d_activityEndDate">

                                    </div>

                                </div>
                            </div>


                            <div class="row">
                                <div class="form-group col-sm-6">
                                    <label for="title" class="col-sm-5 control-label">单个ID限购数量:</label>

                                    <div class="col-sm-7" id="d_limitQuantity">
                                    </div>
                                </div>
                                <div class="form-group col-sm-6">
                                    <label for="subTitle" class="col-sm-5 control-label">活动总数量:</label>

                                    <div class="col-sm-7" id="d_activityQuantity">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-sm-6">
                                    <label for="title" class="col-sm-5 control-label">秒杀商品:</label>

                                    <div class="col-sm-7" id="d_productName">
                                    </div>

                                </div>

                                <div class="form-group col-sm-6">
                                    <label for="subTitle" class="col-sm-5 control-label"><i class="required-mark">*</i>配送方式:</label>

                                    <div class="col-sm-7" id="d_shipmentTypeName">

                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-sm-6">
                                    <label for="title" class="col-sm-5 control-label">最多可抵扣的积分:</label>

                                    <div class="col-sm-7" id="d_scoreValue">
                                    </div>

                                </div>

                                <div class="form-group col-sm-6">
                                    <label for="subTitle" class="col-sm-5 control-label">参团支付条件:</label>

                                    <div class="col-sm-6" id="d_activityPayTypeName">

                                    </div>
                                    <div class="col-sm-3" id="d_productPrice">
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="form-group col-sm-6">
                                    <label for="virtualStartTime" class="col-sm-5 control-label"><i class="required-mark">*</i>虚拟商品有效期自:</label>

                                    <div class="col-sm-7" id="d_virtualProductStartDate"></div>
                                </div>
                                <div id="virtualEndTimeGroup" class="form-group col-sm-6">
                                    <label for="endTime" class="col-sm-5 control-label"><i class="required-mark">*</i>虚拟商品有效期至:</label>

                                    <div class="col-sm-7" id="d_virtualProductEndDate"></div>
                                </div>
                            </div>


                            <div class="row">
                                <div class="form-group col-sm-12">
                                    <label class="col-sm-2 control-label">秒杀选项:</label>

                                    <div class="col-sm-10">
                                        <div class="checkbox clearfix">
                                            <label class="col-sm-3" title="随时退" id="d_isAnyReturn">随时退</label>
                                            <label class="col-sm-3" title="支持过期退" id="d_isSupportOverTimeReturn">支持过期退</label>
                                            <label class="col-sm-3" title="动可积分" id="d_isSupportScore">活动可积分</label>
                                            <label class="col-sm-3" title="退货返回积分" id="d_isSupportReturnScore">退货返回积分</label>
                                            <label class="col-sm-3" title="推荐到首页" id="d_isShowIndex">推荐到首页</label>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="row p-l-10 p-r-10">
                                <div class="form-group">
                                    <label for="seo" class="col-sm-5 control-label">活动描述:</label>

                                    <div class="col-sm-6" id="d_activityDesc">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="box box-info">
                        <div class="box-header">
                            <h3 class="box-title">参加的会员等级</h3>

                        </div>
                        <div class="box-body table-responsive no-padding">
                            <table class="table table-hover" id="d_productActivityPartyLevels">
                                <tr>
                                    <th>序号</th>
                                    <th>会员等级</th>
                                    <th>会员等级名称</th>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="box box-info">
                        <div class="box-header">
                            <h3 class="box-title">参加的社区</h3>
                        </div>
                        <div class="box-body table-responsive no-padding">
                            <table class="table table-hover" id="d_productActivityAreas">
                                <tr>
                                    <th>序号</th>
                                    <th>社区编号</th>
                                    <th>社区名称</th>

                                </tr>

                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">

                </div>
            </div>
        </div>
    </div>

<script type="text/javascript">
    function activityDetail(id) {
        $.ajax({
            url: "productActivityDetail",
            type: "POST",
            data: {activityId: id},
            dataType: "json",
            success: function (data) {
                if (data) {
                    $('#d_productStoreName').text(data.d_productStoreName);
                    $('#d_activityTypeName').text(data.d_activityTypeName);
                    $('#d_activityAuditStatusName').text(data.d_activityAuditStatusName);

                        if (data.activityAuditStatus) {
                            if (data.activityAuditStatus == 'ACTY_AUDIT_INIT') {
                                $('#aduitBtn').show();
                            } else {
                                $('#aduitBtn').hide();
                            }
                        } else {
                            $('#aduitBtn').hide();
                        }

                        $('#d_activityCode').text(data.d_activityCode);
                        $('#d_activityName').text(data.d_activityName);
                        $('#d_publishDate').text(data.d_publishDate);
                        $('#d_endDate').text(data.d_endDate);
                        $('#d_activityStartDate').text(data.d_activityStartDate);
                        $('#d_activityEndDate').text(data.d_activityEndDate);
                        $('#d_limitQuantity').text(data.d_limitQuantity);
                        $('#d_activityQuantity').text(data.d_activityQuantity);
                        $('#d_productName').text(data.d_productName);
                        $('#d_shipmentTypeName').text(data.d_shipmentTypeName);
                        $('#d_scoreValue').text(data.d_scoreValue);
                        $('#d_activityPayTypeName').text(data.d_activityPayTypeName);
                        $('#d_productPrice').text(data.d_productPrice);
                        $('#d_virtualProductStartDate').text(data.d_virtualProductStartDate);
                        $('#d_virtualProductEndDate').text(data.d_virtualProductEndDate);
                        if (data.d_isAnyReturn == 'N') {
                            $('#d_isAnyReturn').hide();
                        }
                        if (data.d_isSupportOverTimeReturn == 'N') {
                            $('#d_isSupportOverTimeReturn').hide();
                        }
                        if (data.d_isSupportScore == 'N') {
                            $('#d_isSupportScore').hide();
                        }
                        if (data.d_isSupportReturnScore == 'N') {
                            $('#d_isSupportReturnScore').hide();
                        }
                        if (data.d_isShowIndex == 'N') {
                            $('#d_isShowIndex').hide();
                        }


                        $('#d_activityDesc').text(data.d_activityDesc);
                        $('#d_productSecKillRules').find('tr').each(function (i) {
                            if (i > 0) {
                                $(this).remove()
                            }
                        });
                        if (data.d_productSecKillRules) {
                            for (var i = 0; i < data.d_productSecKillRules.length; i++) {
                                var trObj = "";
                                trObj += "<tr>";
                                var obj = data.d_productSecKillRules[i];
                                trObj += "<td>" + (i + 1) + "</td>";
                                trObj += "<td>" + obj.seqId + "</td>";
                                trObj += "<td>" + obj.orderQuantity + "</td>";
                                trObj += "<td>" + obj.orderPrice + "</td>";
                                trObj += "</tr>";
                                $('#d_productSecKillRules').find('tr').parent().append(trObj);
                            }
                        }


                        $('#d_productActivityPartyLevels').find('tr').each(function (i) {
                            if (i > 0) {
                                $(this).remove()
                            }
                        });
                        if (data.d_productActivityPartyLevels) {
                            for (var i = 0; i < data.d_productActivityPartyLevels.length; i++) {
                                var trObj = "";
                                trObj += "<tr>";
                                var obj = data.d_productActivityPartyLevels[i];
                                trObj += "<td>" + (i + 1) + "</td>";
                                trObj += "<td>" + obj.levelId + "</td>";
                                trObj += "<td>" + obj.levelName + "</td>";
                                trObj += "</tr>";
                                $('#d_productActivityPartyLevels').find('tr').parent().append(trObj);
                            }

                        }


                        $('#d_productActivityAreas').find('tr').each(function (i) {
                            if (i > 0) {
                                $(this).remove()
                            }
                        });
                        if (data.d_productActivityAreas) {
                            for (var i = 0; i < data.d_productActivityAreas.length; i++) {
                                var trObj = "";
                                trObj += "<tr>";
                                var obj = data.d_productActivityAreas[i];
                                trObj += "<td>" + (i + 1) + "</td>";
                                trObj += "<td>" + obj.communityId + "</td>";
                                trObj += "<td>" + obj.communityName + "</td>";
                                trObj += "</tr>";
                                $('#d_productActivityAreas').find('tr').parent().append(trObj);
                            }
                        }

                        $('#modal_detail').modal('show');
                    }
                },
                error: function (data) {
                    //设置提示弹出框内容
                    tipLayer("操作失败！");

                }
            })
        }

        function deleteSecKill(id) {

            $.confirmLayer({
                msg: '确定删除该秒杀信息',
                confirm: function () {
                    $.ajax({
                        url: 'deleteSecKill',
                        data: {activityId: id, pass: 'Y'},
                        type: "POST",
                        async: false,
                        dataType: "json",
                        success: function (data) {
                            if (data) {
                                //设置提示弹出框内容
                                tipLayer("操作成功！");

                                //提示弹出框隐藏事件，隐藏后重新加载当前页面
                                $('#tipLayer').on('hide.bs.modal', function () {
                                    window.location.href='<@ofbizUrl>findSecKill</@ofbizUrl>';
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
            })

        }


        function auditSecKill(id) {
            $('#modal_audit').modal('show');
            console.log(id);
            $('#auditactivityId').val(id)


        }

        function doAudit() {
            var hasSel = 0;
            var val = '';
            $("input[name='auditResult']").each(function () {
                if ($(this).is(':checked')) {
                    hasSel = 1;
                    val = $(this).val();

                }
            });
            console.log( $('#auditactivityId').val());
            if (!hasSel) {
                alert('请选择审批结果')
            } else {
                $.ajax({
                    url: 'auditGroupOrder',
                    data: {activityId: $('#auditactivityId').val(), pass: val},
                    type: "POST",
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        if (data) {
                            //设置提示弹出框内容
                            tipLayer("操作成功！");

                            //提示弹出框隐藏事件，隐藏后重新加载当前页面
                            $('#tipLayer').on('hide.bs.modal', function () {
                                window.location.href = '<@ofbizUrl>findSecKill</@ofbizUrl>';
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
        }


</script>
<div id="modal_msg" class="modal fade " tabindex="-1" role="dialog" aria-labelledby="modal_msg_title">
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

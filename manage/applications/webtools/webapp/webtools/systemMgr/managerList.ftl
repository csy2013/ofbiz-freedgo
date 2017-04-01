<#assign commonUrl = "userList?lookupFlag=Y"+ paramList +"&">

<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.searchCondition}" collapsible="" showMore=true/>
    <form class="form-inline clearfix" role="form" method="post" action="<@ofbizUrl>userList</@ofbizUrl>">
            <input type="hidden" name="lookupFlag" value="Y">

            <div class="form-group">
                <div class="input-group m-b-10">
                    <span class="input-group-addon">${uiLabelMap.userLoginName}</span>
                    <input type="text" class="form-control" name="userLoginId" placeholder="${uiLabelMap.userLoginName}" value="">
                </div>
                <div class="input-group m-b-10">
                    <span class="input-group-addon">${uiLabelMap.userName}</span>
                    <input type="text" class="form-control" name="userName" placeholder="${uiLabelMap.userName}" value="">
                </div>
            </div>
            <div class="input-group pull-right">
            <#--<#if security.hasEntityPermission("SYSTEMMGR_USER", "_VIEW", session)>-->
                <button class="btn btn-success btn-flat">${uiLabelMap.CommonView}</button>
            <#--</#if>-->
            </div>
        </form>
       <hr/>
        <div class="row m-b-10">
            <div class="col-sm-6">
                <div class="dp-tables_btn">
                <#--<#if security.hasEntityPermission("SYSTEMMGR_USER", "_CREATE", session)>-->
                    <button class="btn btn-primary" onclick="addUser()" ;>
                    ${uiLabelMap.CommonAdd}
                    </button>
                <#--</#if>-->
                </div>
            </div>
            </div>
       <#-- <#if userList?has_content>

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
        </div>-->

    <#if userList?has_content>
        <div class="row">
            <div class="col-sm-12">
                <table class="table table-bordered table-hover js-checkparent">
                    <thead>
                    <tr>
                        <th><input class="js-allcheck" type="checkbox"></th>
                        <th>${uiLabelMap.CommonUsername}
                            <#if orderFiled == 'userLoginId'>
                                <#if orderBy == 'DESC'>
                                    <a class="fa fa-sort-amount-desc" href="${commonUrl}ORDER_FILED=userLoginId&amp;ORDER_BY=ASC"></a>
                                <#else>
                                    <a class="fa fa-sort-amount-asc" href="${commonUrl}ORDER_FILED=userLoginId&amp;ORDER_BY=DESC"
                                </#if>
                            <#else>
                                <a class="fa fa-sort-amount-desc" href="${commonUrl}ORDER_FILED=userLoginId&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>
                        <th>${uiLabelMap.userName}
                            <#if orderFiled == 'name'>
                                <#if orderBy == 'DESC'>
                                    <a class="fa fa-sort-amount-desc" href="${commonUrl}ORDER_FILED=name&amp;ORDER_BY=ASC"></a>
                                <#else>
                                    <a class="fa fa-sort-amount-asc" href="${commonUrl}ORDER_FILED=name&amp;ORDER_BY=DESC"
                                </#if>
                            <#else>
                                <a class="fa fa-sort-amount-desc" href="${commonUrl}ORDER_FILED=name&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>
                        <th>${uiLabelMap.isEffect}
                            <#if orderFiled == 'enabled'>
                                <#if orderBy == 'DESC'>
                                    <a class="fa fa-sort-amount-desc" href="${commonUrl}ORDER_FILED=enabled&amp;ORDER_BY=ASC"></a>
                                <#else>
                                    <a class="fa fa-sort-amount-asc" href="${commonUrl}ORDER_FILED=enabled&amp;ORDER_BY=DESC"
                                </#if>
                            <#else>
                                <a class="fa fa-sort-amount-desc" href="${commonUrl}ORDER_FILED=enabled&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>
                        <th>${uiLabelMap.FormFieldTitle_lastLoginTime}
                            <#if orderFiled == 'lastLoginTime'>
                                <#if orderBy == 'DESC'>
                                    <a class="fa fa-sort-amount-desc" href="${commonUrl}ORDER_FILED=lastLoginTime&amp;ORDER_BY=ASC"></a>
                                <#else>
                                    <a class="fa fa-sort-amount-asc" href="${commonUrl}ORDER_FILED=lastLoginTime&amp;ORDER_BY=DESC"
                                </#if>
                            <#else>
                                <a class="fa fa-sort-amount-desc" href="${commonUrl}ORDER_FILED=lastLoginTime&amp;ORDER_BY=ASC"></a>
                            </#if>
                        </th>
                        <th>${uiLabelMap.systemMgrAction}</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list userList as partyRow>

                        <tr>
                            <td><input class="js-checkchild" type="checkbox"></td>
                            <td><#if partyRow.containsKey("userLoginId")>${partyRow.userLoginId?default("N/A")}</#if></td>
                            <td><#if partyRow.containsKey("name")>${partyRow.name?default("N/A")}</#if></td>
                            <td>
                                <#if partyRow.containsKey("enabled")>
                                    <#if partyRow.enabled?default("N") == 'Y'>
                                ${uiLabelMap.CommonEnabled}
                                <#else>
                                ${uiLabelMap.CommonDisabled}
                                </#if>
                                </#if>
                            </td>
                            <td>${partyRow.lastLoginTime?if_exists}</td>
                            <td>
                                <div class="btn-group">
                                    <#--<#if security.hasEntityPermission("SYSTEMMGR_USER", "_DETAIL", session)>-->
                                        <button type="button" class="btn btn-danger btn-sm" onclick="userDetail('${partyRow.userLoginId?default("N/A")}')">查看</button>
                                    <#--</#if>-->
                                    <button type="button" class="btn btn-danger btn-sm dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                        <span class="caret"></span>
                                        <span class="sr-only">Toggle Dropdown</span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <#--<#if security.hasEntityPermission("SYSTEMMGR_USER", "_UPDATE", session)>-->
                                            <li><a href="#" onclick="updateUser('${partyRow.userLoginId?default("N/A")}')">编辑</a></li>
                                        <#--</#if>-->
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>



        <#include "component://common/webcommon/includes/htmlTemplate.ftl"/>
        <#assign viewIndexFirst = 0/>
        <#assign viewIndexPrevious = viewIndex - 1/>
        <#assign viewIndexNext = viewIndex + 1/>
        <#assign viewIndexLast = Static["org.ofbiz.base.util.UtilMisc"].getViewLastIndex(userListSize, viewSize) />

        <#assign messageMap = Static["org.ofbiz.base.util.UtilMisc"].toMap("lowCount", lowIndex, "highCount", highIndex, "total", userListSize)/>
        <#assign commonDisplaying = Static["org.ofbiz.base.util.UtilProperties"].getMessage("CommonUiLabels", "CommonDisplaying", messageMap, locale)/>
        <@nextPrev commonUrl=commonUrl ajaxEnabled=false javaScriptEnabled=false paginateStyle="nav-pager" paginateFirstStyle="nav-first" viewIndex=viewIndex highIndex=highIndex
        listSize=userListSize viewSize=viewSize ajaxFirstUrl="" firstUrl="" paginateFirstLabel="" paginatePreviousStyle="nav-previous" ajaxPreviousUrl="" previousUrl="" paginatePreviousLabel=""
        pageLabel="" ajaxSelectUrl="" selectUrl="" ajaxSelectSizeUrl="" selectSizeUrl="" commonDisplaying=commonDisplaying paginateNextStyle="nav-next" ajaxNextUrl="" nextUrl=""
        paginateNextLabel="" paginateLastStyle="nav-last" ajaxLastUrl="" lastUrl="" paginateLastLabel="" paginateViewSizeLabel="" />

    <#else>
        <div class="row">
            <div class="col-sm-10">
                <h3>${uiLabelMap.UserNoPartiesFound}</h3>
            </div>
        </div>
    </#if>
<@htmlScreenTemplate.renderScreenletEnd/>
<!-- add user Modal -->
<div id="modal_add" class="modal fade " tabindex="-1" role="dialog" aria-labelledby="modal_add_title">>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">${uiLabelMap.AddUserAction}</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="addForm" method="post" action="<@ofbizUrl>addManager</@ofbizUrl>" enctype="multipart/form-data">

                    <div class="form-group">
                        <label for="partyUserName" class="control-label col-sm-3"><span class="text-danger">*</span>${uiLabelMap.PartyUserName}：</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control required" name="userName" id="userName">
                        </div>
                        <span id="usertip"></span>
                    </div>
                    <div class="form-group">
                        <label for="PartyNewPassword" class="control-label col-sm-3"><span class="text-danger">*</span>${uiLabelMap.PartyNewPassword}：</label>

                        <div class="col-sm-9">
                            <input type="password" class="form-control required" name="password" id="password">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="PartyNewPasswordVerify" class="control-label col-sm-3"><span class="text-danger">*</span>${uiLabelMap.PartyNewPasswordVerify}：</label>

                        <div class="col-sm-9">
                            <input type="password" class="form-control required" name="rePassword" id="rePassword">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="PartyName" class="control-label col-sm-3">${uiLabelMap.PartyName}：</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="name" id="name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="PartyContactMobilePhoneNumber" class="control-label col-sm-3">${uiLabelMap.PartyContactMobilePhoneNumber}：</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="mobile" id="mobile">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="PartyRole" class="control-label col-sm-3"><span class="text-danger">*</span>${uiLabelMap.PartyRole}：</label>

                        <div class="col-sm-9">
                            <select name="groupId" id="groupId" class="form-control">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="uploadedFile" class="control-label col-sm-3">${uiLabelMap.userUploadPhoto}：</label>

                        <div class="col-sm-9">
                            <img height="50" alt="" src="" id="img" style="height:50px;width:50px;">
                            <input style="margin-left:5px;" type="file" id="uploadedFile" name="uploadedFile" value="${uiLabelMap.CommonSelect}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="FormFieldTitle_roleTypeId" class="control-label col-sm-3">${uiLabelMap.isEffect}：</label>

                        <div class="col-sm-9">
                            <label class="radio-inline">
                                <input type="radio" name="enabled" id="open1" value="Y" checked> ${uiLabelMap.CommonTrue}
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="enabled" id="open2" value="N"> ${uiLabelMap.CommonFalse}
                            </label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="saveUser();">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<#--修改管理员-->
<div id="modal_edit" class="modal fade " tabindex="-1" role="dialog" aria-labelledby="modal_add_title">>
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">${uiLabelMap.ModifyUserAction}</h4>
            </div>
            <div class="modal-body">
                <form role="form" class="form-horizontal" id="editForm" method="post" action="<@ofbizUrl>updateManager</@ofbizUrl>" enctype="multipart/form-data">
                    <input type="hidden" name="partyId" id="edit_partyId"/>
                    <input type="hidden" name="userLoginId" id="edit_userLoginId"/>
                    <input type="hidden" name="oldGroupId" id="edit_oldGroupId"/>
                    <input type="hidden" name="oldMobile" id="edit_oldMobile">
                    <input type="hidden" name="contactMechId" id="edit_contactMechId"/>

                    <div class="form-group">
                        <label for="partyUserName" class="control-label col-sm-3"><span class="text-danger">*</span>${uiLabelMap.PartyUserName}：</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control required" name="userName" id="edit_userName" readonly>
                        </div>
                        <span id="usertip"></span>
                    </div>
                    <div class="form-group">
                        <label for="PartyNewPassword" class="control-label col-sm-3"><span class="text-danger">*</span>${uiLabelMap.PartyNewPassword}：</label>

                        <div class="col-sm-9">
                            <input type="password" class="form-control" name="password" id="edit_password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="PartyNewPasswordVerify" class="control-label col-sm-3"><span class="text-danger">*</span>${uiLabelMap.PartyNewPasswordVerify}：</label>

                        <div class="col-sm-9">
                            <input type="password" class="form-control" name="rePassword" id="edit_rePassword">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="PartyName" class="control-label col-sm-3">${uiLabelMap.PartyName}：</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="name" id="edit_name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="PartyContactMobilePhoneNumber" class="control-label col-sm-3">${uiLabelMap.PartyContactMobilePhoneNumber}：</label>

                        <div class="col-sm-9">
                            <input type="text" class="form-control" name="mobile" id="edit_mobile">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="PartyRole" class="control-label col-sm-3"><span class="text-danger">*</span>${uiLabelMap.PartyRole}：</label>

                        <div class="col-sm-9">
                            <select name="groupId" id="edit_groupId"  class="form-control">
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="uploadedFile" class="control-label col-sm-3">${uiLabelMap.userUploadPhoto}：</label>

                        <div class="col-sm-9">
                            <img height="50" alt="" src="" id="edit_img" style="height:50px;width:50px;">
                            <input style="margin-left:5px;" type="file" id="edit_uploadedFile" name="uploadedFile" value="${uiLabelMap.CommonSelect}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="FormFieldTitle_roleTypeId" class="control-label col-sm-3">${uiLabelMap.isEffect}：</label>

                        <div class="col-sm-9">
                            <label class="radio-inline">
                                <input type="radio" name="enabled" id="edit_open1" value="Y" checked> ${uiLabelMap.CommonTrue}
                            </label>
                            <label class="radio-inline">
                                <input type="radio" name="enabled" id="edit_open2" value="N"> ${uiLabelMap.CommonFalse}
                            </label>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="editUser();">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">


    function addUser() {
        $("#userName").val('');
        $('#password').val('');
        $('#rePassword').val('');
        $('#name').val('');
        $('#groupId').val('');
        $("#mobile").val('');
        $("#uploadedFile").attr("src", '');
        $("input[name=enabled]:eq(0)").attr("checked", 'checked');
        $('#addForm').attr('action', '<@ofbizUrl>addManager</@ofbizUrl>');
        loadAuthority();
//        $("#addForm").validate();
        $('#modal_add').modal('show');
    }


    function saveUser() {
//        if ($("#addForm").valid()) {
            $.ajax({
                type: 'post',
                url: '<@ofbizUrl>checkManagerExist</@ofbizUrl>',
                data: {userLoginId: $("#userName").val()},
                async: false,
                success: function (data) {
                    if (data && data.userLogin) {
//                            $("#usertip").html('<label for="userName" generated="true" class="error" style="display: inline-block;">管理员名称已存在</label>');
                        $("#userName").addClass('error');
                        //设置提示弹出框内容
                        tipLayer('管理员名称已存在！');
                    } else {
                        $("#usertip").html('');
                        $("#userName").removeClass('error');
                        $("#addForm").submit();

                    }
                }
            });
//        }
    }


    function editUser() {
//        if ($("#editForm").valid()) {
            $("#editForm").submit();
//        }
    }

    function updateUser(userName) {
        $("#edit_userName").removeAttr("readonly");
        $('#edit_name').removeAttr('readonly');
        $('#password').removeAttr('readonly');
        $('#rePassword').removeAttr('readonly');
        $("#edit_mobile").removeAttr('readonly');
        $("#edit_uploadedFile").removeAttr('disabled');
        $('#edit_groupId').removeAttr('disabled');
        $("input[name=enabled]").removeAttr('disabled');

        $("#edit_userName").val('');
        $('#edit_password').val('');
        $('#edit_rePassword').val('');
        $('#edit_name').val('');
        $('#edit_groupId').val('');
        $("#edit_mobile").val('');
        $("#edit_uploadedFile").attr("src", '');
        $("input[name=enabled]:eq(0)").attr("checked", 'checked');
        $('#editForm').attr('action', '<@ofbizUrl>updateManager</@ofbizUrl>');
        doSearchManager(userName);
        loadEditAuthority();
//        $("#editForm").validate();
        $('#modal_edit').modal('show');
    }

    function doSearchManager(id) {
        $.ajax({
            type: 'post',
            url: '<@ofbizUrl>queryManager</@ofbizUrl>',
            data: {userLoginId: id},
            success: function (data) {
                console.log(data);
//                    console.log(data.userLoginAndSecurityGroup.groupId)
                if (data && data.person && data.userLogin) {
                    $("#edit_userName").val(data.userLogin.userLoginId);
                    $('#edit_name').val(data.person.name);
                    $("#edit_userLoginId").val(data.userLogin.userLoginId);
                    $("#edit_partyId").val(data.userLogin.partyId);
                    if (data.userLoginAndSecurityGroup) {
                        loadEditAuthority(data.userLoginAndSecurityGroup.groupId);
                    }
                    if (data.userMobileContact) {
                        $("#edit_mobile").val(data.userMobileContact.contactNumber);
                    }
                    if (data.userLogin.enabled == 'N') {
                        $('#edit_open2').attr("checked", 'checked');
                    }
                    if (data.partyContent && data.partyContent.dataResourceId) {
                        $("#edit_img").attr('src', "<@ofbizUrl>personLogo?imgId="+ data.partyContent.dataResourceId</@ofbizUrl>);
                    }
                    if(data.userLoginAndSecurityGroup) {
                        $("#edit_oldGroupId").val(data.userLoginAndSecurityGroup.groupId);
                    }
                    if (data.userMobileContact) {
                        $("#edit_oldMobile").val(data.userMobileContact.contactNumber);
                    }

//                    console.log(data.userMobileContact.contactMechId)
                    if (data.userMobileContact) {
                        $("#edit_contactMechId").val(data.userMobileContact.contactMechId);
                    }
                }
            }
        });
    }


    function userDetail(id) {
        doSearchManager(id);
        $("#edit_userName").attr("readonly", true);
        $('#edit_name').attr('readonly', true);
        $('#password').attr('readonly', true);
        $('#rePassword').attr('readonly', true);
        $('#edit_groupId').val('');
        $("#edit_mobile").attr('readonly', true);
        $("#edit_uploadedFile").attr('disabled', true);
        $('#edit_groupId').attr('disabled', true);
        $("input[name=enabled]").attr('disabled', true);
        $('#modal_edit').modal('show');
    }
    /**加载权限列表*/
    function loadAuthority() {
        $.post("<@ofbizUrl>queryAllSecurityGroup</@ofbizUrl>", function (data) {
            console.log(data);
            var options = "";
            for (var i = 0; i < data.securityGroups.length; i++) {
                var auth = data.securityGroups[i];
                options += "<option value='" + auth.groupId + "'>" + auth.description + "</option>";
            }
            $('#groupId').html(options);
            /* 为选定的select下拉菜单开启搜索提示 END */
        });


    }

    /**加载权限列表*/
    function loadEditAuthority(groupId) {
        $.post("<@ofbizUrl>queryAllSecurityGroup</@ofbizUrl>", function (data) {
            console.log(data);
            var options = "";
            for (var i = 0; i < data.securityGroups.length; i++) {
                var auth = data.securityGroups[i];
                if (groupId == auth.groupId) {
                    options += "<option selected value='" + auth.groupId + "'>" + auth.description + "</option>";
                } else {
                    options += "<option value='" + auth.groupId + "'>" + auth.description + "</option>";
                }
            }
            $('#edit_groupId').html(options);
            /* 为选定的select下拉菜单开启搜索提示 END */
        });


    }


</script>
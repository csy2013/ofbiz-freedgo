<#macro renderScreenBegin>
<!DOCTYPE html>
</#macro>

<#macro renderScreenEnd>
</#macro>

<#macro renderSectionBegin boundaryComment>
    <#if boundaryComment?has_content>
    <!-- ${boundaryComment} -->
    </#if>
</#macro>

<#macro renderSectionEnd boundaryComment>
    <#if boundaryComment?has_content>
    <!-- ${boundaryComment} -->
    </#if>
</#macro>

<#macro renderContainerBegin id style autoUpdateLink autoUpdateInterval>
    <#if autoUpdateLink?has_content>
    <script type="text/javascript">ajaxUpdateAreaPeriodic('${id}', '${autoUpdateLink}', '', '${autoUpdateInterval}');</script>
    </#if>
<div<#if id?has_content> id="${id}"</#if><#if style?has_content> class="${style}"<#else> class=""</#if>>
</#macro>
<#macro renderContainerEnd></div></#macro>
<#macro renderContentBegin editRequest enableEditValue editContainerStyle><#if editRequest?has_content && enableEditValue == "true">
<div class=${editContainerStyle}></#if></#macro>
<#macro renderContentBody></#macro>
<#macro renderContentEnd urlString editMode editContainerStyle editRequest enableEditValue>
    <#if editRequest?exists && enableEditValue == "true">
        <#if urlString?exists><a href="${urlString}">${editMode}</a><#rt/></#if>
        <#if editContainerStyle?exists></div><#rt/></#if>
    </#if>
</#macro>
<#macro renderSubContentBegin editContainerStyle editRequest enableEditValue><#if editRequest?exists && enableEditValue == "true">
<div class="${editContainerStyle}"></#if></#macro>
<#macro renderSubContentBody></#macro>
<#macro renderSubContentEnd urlString editMode editContainerStyle editRequest enableEditValue>
    <#if editRequest?exists && enableEditValue == "true">
        <#if urlString?exists><a href="${urlString}">${editMode}</a><#rt/></#if>
        <#if editContainerStyle?exists></div><#rt/></#if>
    </#if>
</#macro>

<#macro renderHorizontalSeparator id style>
<hr<#if id?has_content> id="${id}"</#if><#if style?has_content> class="${style}"</#if>/></#macro>

<#macro renderLabel text id style>
    <#if text?has_content>
    <#-- If a label widget has one of the h1-h6 styles, then it is considered block level element.
         Otherwise it is considered an inline element. -->
        <#assign idText = ""/>
        <#if id?has_content><#assign idText = " id=\"${id}\""/></#if>

        <#if style?has_content>
            <#if style=="h1">
                <h3${idText}>${text}</h3>
            <#elseif style=="h2">
                <h3${idText}>${text}</h3>
            <#elseif style=="h3">
                <h3${idText}>${text}</h3>
            <#elseif style=="h4">
                <h4${idText}>${text}</h4>
            <#elseif style=="h5">
                <h5${idText}>${text}</h5>
            <#elseif style=="h6">
                <h6${idText}>${text}</h6>
            <#elseif style=="sub-title">
            <small>${text}</small>
            <#elseif style=="success">
            <div class="alert alert-success fade in m-b-15">
            ${text}
                <span class="close" data-dismiss="alert">×</span>
            </div>
            <#elseif style=="info">
            <div class="alert alert-info fade in m-b-15">
            ${text}
                <span class="close" data-dismiss="alert">×</span>
            </div>
            <#elseif style=="alert">
            <div class="alert alert-warning fade in m-b-15">
            ${text}
                <span class="close" data-dismiss="alert">×</span>
            </div>
            <#elseif style=="danger">
            <div class="alert alert-danger fade in m-b-15">
            ${text}
                <span class="close" data-dismiss="alert">×</span>
            </div>
            <#else>
                <span${idText} class="${style}">${text}</span>
            </#if>
        <#else>
            <#--<span${idText} class="h4 text-primary text-default">${text}</span>-->
            ${text}
        </#if>

    </#if>
</#macro>

<#macro renderLink parameterList targetWindow target uniqueItemName linkType actionUrl id style name height width linkUrl text imgStr isEnd isFirst>
    <#if "ajax-window" != linkType>
        <#if "hidden-form" == linkType>
        <form method="post" action="${actionUrl}" <#if targetWindow?has_content>target="${targetWindow}"</#if>
              onsubmit="submitFormDisableSubmits(this)" name="${uniqueItemName}"><#rt/>
            <#list parameterList as parameter>
                <input name="${parameter.name}" value="${parameter.value}" type="hidden"/><#rt/>
            </#list>
        </form><#rt/>
        </#if>
        <#if style =='breadcrumb active'>
        <li class="active">
        <#elseif style == 'breadcrumb'>
        <li>
        </#if>
        <a <#if id?has_content>id="${id}"</#if>
           <#if (style !='breadcrumb') && (style != 'breadcrumb active')>class="btn btn-primary round btn-sm"
           <#else>
           </#if>
           <#if name?has_content>name="${name}"</#if>
           <#if targetWindow?has_content>target="${targetWindow}"</#if>
           href="<#if "hidden-form"==linkType>javascript:document.${uniqueItemName}.submit()<#else>${linkUrl}</#if>"><#rt/>
            <#if imgStr?has_content>${imgStr}</#if>
            <#if style=='breadcrumb active'>
                <strong>${text}</strong>
            <#elseif text?has_content>${text}
            </#if>
        </a>
        <#if style =='breadcrumb'>
        </li>
        <#elseif style == 'breadcrumb active'>
        </li>
        </#if>

    <#else>
    <div id="${uniqueItemName}"></div>
    <a href="javascript:void(0);" id="${uniqueItemName}_link"
       <#if style?has_content>class="${style}"</#if>><#if text?has_content>${text}</#if></a>
    <script type="text/javascript">
        function getRequestData() {
            var data = {
                <#list parameterList as parameter>
                    "${parameter.name}": "${parameter.value}",
                </#list>
                "presentation": "layer"
            };

            return data;
        }
        jQuery("#${uniqueItemName}_link").click(function () {
            jQuery("#${uniqueItemName}").dialog("open");
        });
        jQuery("#${uniqueItemName}").dialog({
            autoOpen: false,
                <#if text?has_content>title: "${text}",</#if>
            height: ${height},
            width: ${width},
            modal: true,
            open: function () {
                jQuery.ajax({
                    url: "${target}",
                    type: "POST",
                    data: getRequestData(),
                    success: function (data) {
                        jQuery("#${uniqueItemName}").html(data);
                    }
                });
            }
        });
    </script>
    </#if>
<#--</div>-->
<#--<#if isEnd></div></#if>-->
</#macro>
<#macro renderImage src id style wid hgt border alt urlString>
    <#if src?has_content>
    <img <#if id?has_content>id="${id}"</#if><#if style?has_content> class="${style}"</#if><#if wid?has_content> width="${wid}"</#if><#if hgt?has_content>
         height="${hgt}"</#if><#if border?has_content>
         border="${border}"</#if>
         alt="<#if alt?has_content>${alt}</#if>" src="${urlString}"/>
    </#if>
</#macro>

<#macro renderContentFrame fullUrl width height border>
<iframe src="${fullUrl}" width="${width}" height="${height}" <#if border?has_content>border="${border}"</#if> />
</#macro>

<#macro renderScreenletBegin id title style="" collapsible="" saveCollapsed="" collapsibleAreaId="" expandToolTip="" collapseToolTip="" fullUrlString="" padded="" menuString="" showMore=true collapsed=true javaScriptEnabled=false addHeadBarHtml="">

<div class="box box-info" <#if id?has_content> id="${id}"</#if>><#rt/>
    <#if showMore>
        <#if title?has_content>
            <div class="box-header with-border">
                <#if title?has_content><h3 class="box-title">${title}</h3></#if>
                <#if collapsed>
                    <div class="box-tools pull-right">
                        <#if addHeadBarHtml?exists >${addHeadBarHtml}</#if>
                        <button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>

                    <#--<button class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>-->
                    </div>
                </#if>
            </div>
        </#if>
    <#--
    <#if !collapsed>
    ${menuString}
    </#if>
     -->
        <#if menuString?has_content>
            <div class="box-body">${menuString}</div>
        </#if>
    </#if>
<div class="box-body"
    <#if collapsibleAreaId?has_content> id="${collapsibleAreaId}" </#if>>
</#macro>

<#macro renderScreenletSubWidget></#macro>
<#macro renderScreenletEnd></div></div></#macro>
<#macro renderScreenletPaginateMenu lowIndex actualPageSize ofLabel listSize paginateLastStyle lastLinkUrl paginateLastLabel paginateNextStyle nextLinkUrl paginateNextLabel paginatePreviousStyle paginatePreviousLabel previousLinkUrl paginateFirstStyle paginateFirstLabel firstLinkUrl>
<li class="${paginateLastStyle}<#if !lastLinkUrl?has_content> disabled</#if>"><#if lastLinkUrl?has_content><a href="${lastLinkUrl}">${paginateLastLabel}</a><#else>${paginateLastLabel}</#if>
</li>
<li class="${paginateNextStyle}<#if !nextLinkUrl?has_content> disabled</#if>"><#if nextLinkUrl?has_content><a href="${nextLinkUrl}">${paginateNextLabel}</a><#else>${paginateNextLabel}</#if>
</li>
    <#if (listSize?number > 0) >
    <li>${lowIndex?number + 1} - ${lowIndex?number + actualPageSize?number} ${ofLabel} ${listSize}</li><#rt/></#if>
<li class="${paginatePreviousStyle?default("nav-previous")}<#if !previousLinkUrl?has_content> disabled</#if>"><#if previousLinkUrl?has_content><a
        href="${previousLinkUrl}">${paginatePreviousLabel}</a><#else>${paginatePreviousLabel}</#if></li>
<li class="${paginateFirstStyle?default("nav-first")}<#if !firstLinkUrl?has_content> disabled</#if>"><#if firstLinkUrl?has_content><a
        href="${firstLinkUrl}">${paginateFirstLabel}</a><#else>${paginateFirstLabel}</#if></li>
</#macro>

<#macro renderPortalPageBegin originalPortalPageId portalPageId confMode="false" addColumnLabel="Add column" addColumnHint="Add a new column to this portal">
    <#if confMode == "true">
    <a class="btn btn-primary round btn-sm" href="javascript:document.addColumn_${portalPageId}.submit()" title="${addColumnHint}">${addColumnLabel}</a> <b>PortalPageId: ${portalPageId}</b>
    <form method="post" action="addPortalPageColumn" name="addColumn_${portalPageId}">
        <input name="portalPageId" value="${portalPageId}" type="hidden"/>
    </form>
    </#if>
<div class="row">
</#macro>
<#macro renderPortalPageEnd>
</div>
</#macro>

<#macro renderPortalPageColumnBegin originalPortalPageId portalPageId columnSeqId confMode="false" width="auto" delColumnLabel="Delete column" delColumnHint="Delete this column" addPortletLabel="Add portlet" addPortletHint="Add a new portlet to this column" colWidthLabel="Col. width:" setColumnSizeHint="Set column size" avg="6">
    <#assign columnKey = portalPageId+columnSeqId>
    <#assign columnKeyFields = '<input name="portalPageId" value="' + portalPageId + '" type="hidden"/><input name="columnSeqId" value="' + columnSeqId + '" type="hidden"/>'>
<script type="text/javascript">
    if (typeof SORTABLE_COLUMN_LIST != "undefined") {
        if (SORTABLE_COLUMN_LIST == null) {
            SORTABLE_COLUMN_LIST = "#portalColumn_${columnSeqId}";
        } else {
            SORTABLE_COLUMN_LIST += ", #portalColumn_${columnSeqId}";
        }
    }
</script>
<form method="post" action="deletePortalPageColumn" name="delColumn_${columnKey}">
${columnKeyFields}
</form>
<form method="post" action="addPortlet" name="addPortlet_${columnKey}">
${columnKeyFields}
</form>

<form method="post" action="editPortalPageColumnWidth" name="setColumnSize_${columnKey}">
${columnKeyFields}
</form>
<div class="col-lg-${avg} portal-column<#if confMode == "true">-config</#if> connectedSortable" style="vertical-align: top; " id="portalColumn_${columnSeqId}">
    <#if confMode == "true">
        <div class="btn-toolbar">
            <div class="btn-group">
                <a class="btn btn-primary btn-sm" href="javascript:document.delColumn_${columnKey}.submit()" title="${delColumnHint}">${delColumnLabel}</a>
                <a class="btn btn-primary btn-sm" role="button" href="javascript:document.addPortlet_${columnKey}.submit()" title="${addPortletHint}">${addPortletLabel}</a>
                <a class="btn btn-primary btn-sm" href="javascript:document.setColumnSize_${columnKey}.submit()" title="${setColumnSizeHint}">${colWidthLabel}: ${width}</a>
            </div>
        </div>
        <br class="clear"/>
    </#if>
</#macro>

<#macro renderPortalPageColumnEnd>
</div>
</#macro>

<#macro renderPortalPagePortletBegin originalPortalPageId portalPageId portalPortletId portletSeqId prevPortletId="" prevPortletSeqId="" nextPortletId="" nextPortletSeqId="" columnSeqId="" prevColumnSeqId="" nextColumnSeqId="" confMode="false" delPortletHint="Remove this portlet" editAttribute="false" editAttributeHint="Edit portlet parameters">
    <#assign portletKey = portalPageId+portalPortletId+portletSeqId>
    <#assign portletKeyFields = '<input name="portalPageId" value="' + portalPageId + '" type="hidden"/><input name="portalPortletId" value="' + portalPortletId + '" type="hidden"/><input name="portletSeqId" value="' + portletSeqId  + '" type="hidden"/>'>
<div id="PP_${portletKey}" name="portalPortlet" class="noClass" portalPageId="${portalPageId}" portalPortletId="${portalPortletId}" columnSeqId="${columnSeqId}"
     portletSeqId="${portletSeqId}">
    <#if confMode == "true">
    <div class="portlet-config" id="PPCFG_${portletKey}">
        <div class="portlet-config-title-bar">
            <ul>
                <li class="title">Portlet : [${portalPortletId}]</li>
                <li class="remove">
                    <form method="post" action="deletePortalPagePortlet" name="delPortlet_${portletKey}">
                    ${portletKeyFields}
                    </form>
                    <a href="javascript:document.delPortlet_${portletKey}.submit()" title="${delPortletHint}">&nbsp;&nbsp;&nbsp;</a>
                </li>
                <#if editAttribute == "true">
                    <li class="edit">
                        <form method="post" action="editPortalPortletAttributes" name="editPortlet_${portletKey}">
                        ${portletKeyFields}
                        </form>
                        <a href="javascript:document.editPortlet_${portletKey}.submit()" title="${editAttributeHint}">&nbsp;&nbsp;&nbsp;</a>
                    </li>
                </#if>
                <#if prevColumnSeqId?has_content>
                    <li class="move-left">
                        <form method="post" action="updatePortletSeqDragDrop" name="movePortletLeft_${portletKey}">
                            <input name="o_portalPageId" value="${portalPageId}" type="hidden"/>
                            <input name="o_portalPortletId" value="${portalPortletId}" type="hidden"/>
                            <input name="o_portletSeqId" value="${portletSeqId}" type="hidden"/>
                            <input name="destinationColumn" value="${prevColumnSeqId}" type="hidden"/>
                            <input name="mode" value="DRAGDROPBOTTOM" type="hidden"/>
                        </form>
                        <a href="javascript:document.movePortletLeft_${portletKey}.submit()">&nbsp;&nbsp;&nbsp;</a></li>
                </#if>
                <#if nextColumnSeqId?has_content>
                    <li class="move-right">
                        <form method="post" action="updatePortletSeqDragDrop" name="movePortletRight_${portletKey}">
                            <input name="o_portalPageId" value="${portalPageId}" type="hidden"/>
                            <input name="o_portalPortletId" value="${portalPortletId}" type="hidden"/>
                            <input name="o_portletSeqId" value="${portletSeqId}" type="hidden"/>
                            <input name="destinationColumn" value="${nextColumnSeqId}" type="hidden"/>
                            <input name="mode" value="DRAGDROPBOTTOM" type="hidden"/>
                        </form>
                        <a href="javascript:document.movePortletRight_${portletKey}.submit()">&nbsp;&nbsp;&nbsp;</a></li>
                </#if>
                <#if prevPortletId?has_content>
                    <li class="move-up">
                        <form method="post" action="updatePortletSeqDragDrop" name="movePortletUp_${portletKey}">
                            <input name="o_portalPageId" value="${portalPageId}" type="hidden"/>
                            <input name="o_portalPortletId" value="${portalPortletId}" type="hidden"/>
                            <input name="o_portletSeqId" value="${portletSeqId}" type="hidden"/>
                            <input name="d_portalPageId" value="${portalPageId}" type="hidden"/>
                            <input name="d_portalPortletId" value="${prevPortletId}" type="hidden"/>
                            <input name="d_portletSeqId" value="${prevPortletSeqId}" type="hidden"/>
                            <input name="mode" value="DRAGDROPBEFORE" type="hidden"/>
                        </form>
                        <a href="javascript:document.movePortletUp_${portletKey}.submit()">&nbsp;&nbsp;&nbsp;</a></li>
                </#if>
                <#if nextPortletId?has_content>
                    <li class="move-down">
                        <form method="post" action="updatePortletSeqDragDrop" name="movePortletDown_${portletKey}">
                            <input name="o_portalPageId" value="${portalPageId}" type="hidden"/>
                            <input name="o_portalPortletId" value="${portalPortletId}" type="hidden"/>
                            <input name="o_portletSeqId" value="${portletSeqId}" type="hidden"/>
                            <input name="d_portalPageId" value="${portalPageId}" type="hidden"/>
                            <input name="d_portalPortletId" value="${nextPortletId}" type="hidden"/>
                            <input name="d_portletSeqId" value="${nextPortletSeqId}" type="hidden"/>
                            <input name="mode" value="DRAGDROPAFTER" type="hidden"/>
                        </form>
                        <a href="javascript:document.movePortletDown_${portletKey}.submit()">&nbsp;&nbsp;&nbsp;</a></li>
                </#if>
            </ul>
            <br class="clear"/>
        </div>
    </#if>
</#macro>

<#macro renderPortalPagePortletEnd confMode="false">
</div>
    <#if confMode == "true">
    </div>
    </#if>
</#macro>
<#macro renderColumnContainerBegin id style ></#macro>
<#macro renderColumnContainerEnd></#macro>
<#macro renderColumnBegin id style >
    <#if style?has_content>
        <#if style== "body-title">
            <h1>
        <#elseif style == "breadcrumb">
        <ol class="breadcrumb">
        <#else>
        </#if>
    <#else>
    </#if>
</#macro>
<#macro renderColumnEnd id style>
    <#if style?has_content>
        <#if style == "body-title">
        </h1>
        <#elseif style == "breadcrumb">
        </ol>
        <#else>
        </#if>
    <#else>
    </#if>
</#macro>

<#--增加modal Field-->
<#macro renderModalPage id name buttonType="" buttonStyle="" buttonSpanStyle="" modalUrl=""  className=""  alert="false" description="" ajaxEnabled="true" presentation="layer"  height="" width=""  position="" fadeBackground="true" clearText="" modalMsg="" modalTitle="" targetParameterIter="" returnUrl="" returnParameters="" modalType="form-submit" modalStyle="">
    <#if Static["org.ofbiz.widget.ModelWidget"].widgetBoundaryCommentsEnabled(context)>
    <!-- @renderLookupField -->
    </#if>
    <#if buttonType=="text-link">
    <a class="btn btn-primary btn-sm" href="javascript:" onclick="${id}_doModal();"><#if title?has_content>${description}</#if></a>
    <#elseif buttonType=="image">
    <input type="image" src="${imgSrc}"  <#if name?has_content> name="${name}"</#if><#if description?has_content> alt="${description}"</#if> onclick="${id}_doModal(this);"/>
    <#elseif buttonType=="custom">
        <a href="javascript:" onclick="${id}_doModal(this);" class="${buttonStyle}">
            ${buttonSpanStyle?if_exists}
        ${description}</a>
    <#else>
    <input class="btn btn-primary btn-sm" type="button" <#if name?exists> name="${name}"</#if><#if description?has_content>value="${description}"</#if>  onclick="${id}_doModal(this);"/>
    </#if>
    <#assign modalTitle = StringUtil.wrapString(modalTitle)>
    <#assign modalMsg = StringUtil.wrapString(modalMsg)>
<script type="text/javascript">
    function ${id}_doModal(obj) {
        modalLayer({
            msg: '${modalMsg}',
            title: '${modalTitle}',
            bodyUrl: '${modalUrl}',
            target: obj,
            modalType:'${modalType}',
            modalStyle:'${modalStyle}',
            <#if (targetParameterIter?has_content) && (targetParameterIter!='null')>
                queryArgs: {${targetParameterIter}},
            </#if>
            confirm: function () {
                if ($("#modalLayer")) {
                    var form = $("#modalLayer form:last");
                    if ($(form).attr('data-parsley-validate')) {
                        if($(form).parsley().validate()){
                            doSubmit(form,obj);
                        }
                    } else {
                    doSubmit(form, obj);
                    }
                }
            }

        })
    }

    var doSubmit = function (form, obj) {
        modalDisableAllButton(document.getElementById("modalLayer"));
        if ($(form).attr('enctype') === 'multipart/form-data') {
            var formData = new FormData(form[0]);
            $.ajax({
                url: $(form).attr("action"),
                type: 'post',
                dataType: 'json',
                data: formData,
                processData: false,
                contentType: false,
                beforeSend: function () {
                }
            }).done(function (data) {
                if (data) {
                    modalEnableAllButton(document.getElementById("modalLayer"));
                    //设置提示弹出框内容
                    if(data._ERROR_MESSAGE_){
                        tipLayer({msg: data._ERROR_MESSAGE_, target: obj});
                    }
                    else if (data._ERROR_MESSAGE_LIST_) {
                        console.log(data._ERROR_MESSAGE_LIST_);
                        var msg = "";
                        for (var i = 0; i < data._ERROR_MESSAGE_LIST_.length; i++) {
                            msg += data._ERROR_MESSAGE_LIST_[i] + "<br/>";

                        }
                        var body = "<div class='alert alert-danger m-b-0'><h4><i class='fa fa-info-circle'></i> 操作失败</h4><p>" + msg + "</p></div>";
                        tipLayer({msg: body, target: obj});
                    } else {
                        tipLayer({msg: "操作成功！", target: obj});
                        $('#tipLayer').on('hide.bs.modal', function () {
                            <#if returnUrl =="">
                                window.location.reload(true);
                            <#else>
                                window.location.href = '${returnUrl}?${returnParameters}';
                            </#if>

                        })
                    }
                }
            })
                    .fail(function () {
                        modalEnableAllButton(document.getElementById("modalLayer"));
                        //设置提示弹出框内容
                        var body = "<div class='alert alert-danger m-b-0'><h4><i class='fa fa-info-circle'></i> 操作失败</h4></div>";
                        tipLayer({msg: body, target: obj});
                    });

        } else {
            $.ajax({
                url: $(form).attr("action"),
                type: "POST",
                async: false,
                dataType: "json",
                data: $(form).serialize(),
                success: function (data) {
                    if (data) {
                        modalEnableAllButton(document.getElementById("modalLayer"));
                        //设置提示弹出框内容
                        if(data._ERROR_MESSAGE_){
                            tipLayer({msg: data._ERROR_MESSAGE_, target: obj});
                        } else if (data._ERROR_MESSAGE_LIST_) {
                            console.log(data._ERROR_MESSAGE_LIST_);
                            var msg = "";
                            for (var i = 0; i < data._ERROR_MESSAGE_LIST_.length; i++) {
                                msg += data._ERROR_MESSAGE_LIST_[i] + "<br/>";

                            }
                            var body = "<div class='alert alert-danger m-b-0'><h4><i class='fa fa-info-circle'></i> 操作失败</h4><p>" + msg + "</p></div>";
                            tipLayer({msg: body, target: obj});
                        } else {
                            tipLayer({msg: '操作成功', target: obj});
                            $('#tipLayer').on('hide.bs.modal', function () {
                                <#if returnUrl =="">
                                    window.location.reload(true);
                                <#else>
                                    window.location.href = '${returnUrl}?${returnParameters}';
                                </#if>

                            })
                        }
                    }
                },
                error: function (data) {
                    modalEnableAllButton(document.getElementById("modalLayer"));
                    //设置提示弹出框内容
                    var body = "<div class='alert alert-danger m-b-0'><h4><i class='fa fa-info-circle'></i> 操作失败</h4></div>";
                    tipLayer({msg: body, target: obj});
                }
            })
        }
    }
</script>
</#macro>
<#--End 增加modal Field-->

<#--breadcrumb-->
<#macro renderColumnContainerBegin id style ></#macro>
<#macro renderColumnContainerEnd></#macro>
<#macro renderColumnBegin id style >
    <#if style?has_content>
        <#if style== "body-title">

        <h1 class="page-header">
        <#elseif style == "breadcrumb">

        <ol class="breadcrumb">
        <#else>
        </#if>
    <#else>
    </#if>
</#macro>
<#macro renderColumnEnd id style>
    <#if style?has_content>
        <#if style == "body-title">
        </h1>
        <#elseif style == "breadcrumb">
            </ol>
        <#else>
        </#if>
    <#else>
    </#if>
</#macro>
<#--end breadcrumb-->

<#macro renderConfirmField id name  confirmUrl buttonType="" className="" alert="false" description="" buttonStyle="" buttonSpanStyle="" ajaxEnabled="" presentation="layer" height="" width=""  position="" fadeBackground="true" clearText=""   confirmMessage="" confirmTitle="" targetParameterIter="" returnUrl="" returnParameters="">
    <#if Static["org.ofbiz.widget.ModelWidget"].widgetBoundaryCommentsEnabled(context)>
    <!-- @renderLookupField -->
    </#if>
    <#if buttonType=="text-link">
    <a class="btn btn-primary btn-sm" href="" onclick="${id}_doConfrim();"><#if title?has_content>${description}</#if></a>
    <#elseif buttonType=="image">
    <input type="image" src="${imgSrc}"  <#if name?has_content> name="${name}"</#if><#if description?has_content> alt="${description}"</#if> onclick="${id}_doConfrim(this);"/>
    <#elseif buttonType=="custom">
    <a href="javascript:" onclick="${id}_doConfirm(this);" class="${buttonStyle}">
    ${buttonSpanStyle?if_exists}
        ${description}</a>
    <#else>
    <input class="btn btn-primary btn-sm" type="button" <#if name?exists> name="${name}"</#if><#if description?has_content>value="${description}"</#if>
           onclick="${id}_doConfirm(this);"/>
    </#if>

<script type="text/javascript">
    function ${id}_doConfirm(obj) {
        confirmLayer({
            msg: '${confirmMessage}',
            title: '${confirmTitle}',
            target: obj,
            confirm: function () {
                $.ajax({
                    url: '${confirmUrl}',
                    data: {${targetParameterIter}},
                    type: "POST",
                    async: false,
                    dataType: "json",
                    success: function (data) {
                        if (data) {
                            if (data) {
                                //设置提示弹出框内容
                                if (data._ERROR_MESSAGE_LIST_) {
                                    console.log(data._ERROR_MESSAGE_LIST_);
                                    var msg = "";
                                    for (var i = 0; i < data._ERROR_MESSAGE_LIST_.length; i++) {
                                        msg += data._ERROR_MESSAGE_LIST_[i] + "<br/>";
                                    }
                                    var body = "<div class='alert alert-danger m-b-0'><h4><i class='fa fa-info-circle'></i> 操作失败</h4><p>" + msg + "</p></div>";
                                    tipLayer({msg: body, target: obj});
                                } else if(data._ERROR_MESSAGE_) {
                                    var body = "<div class='alert alert-danger m-b-0'><h4><i class='fa fa-info-circle'></i> 操作失败</h4><p>" + data._ERROR_MESSAGE_ + "</p></div>";
                                    tipLayer({msg: body, target: obj});
                                }
                                else {
                                    tipLayer({msg: "操作成功！", target: obj});
                                    $('#tipLayer').on('hide.bs.modal', function () {
                                        <#if returnUrl=="">
                                            window.location.reload(true);
                                        <#else>
                                            window.location.href = '${returnUrl}<#if returnParameters !="">?${returnParameters}</#if>';
                                        </#if>

                                    })
                                }
                            }
                        }
                    },
                    error: function (data) {
                        //设置提示弹出框内容
                        tipLayer({msg: '操作失败', target: obj});
                    }
                })
            }
        })

    }
</script>


</#macro>

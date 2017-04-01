<#include "component://common/webcommon/includes/htmlScreenTemplate.ftl"/>
<#if orderHeader?has_content>

<div class="${panelStyle}">
    <div class="${panelHeadingStyle}">
        <#if security.hasEntityPermission("ORDERMGR", "_NOTE", session)>
            <div class="${panelHeadingBarStyle}">
                 <@htmlScreenTemplate.renderModalPage id="OrderNotesCreateNew" name="OrderNotesCreateNew" buttonType="custom" buttonStyle="btn btn-primary btn-xs"   modalTitle="新建备忘录" description="新建" modalUrl="createnewnote" targetParameterIter="orderId:'${orderId}'"/>
                <#--<a href="<@ofbizUrl>createnewnote?${paramString}</@ofbizUrl>" class="btn btn-primary btn-xs">${uiLabelMap.OrderNotesCreateNew}</a>-->
            </div>
        </#if>
        <h4 class="${panelTitleStyle}">&nbsp;${uiLabelMap.OrderNotes} </h4>
    </div>
    <div class="${panelBodyStyle}">
        <#if orderNotes?has_content>
        <div class="table-responsive">
            <table class="table" cellspacing='0'>
                <th>创建者</th>
                <th>创建时间</th>
                <th>内容</th>
                <th>状态</th>
                <th>修改</th>
                <#list orderNotes as note>
                    <tr>
                        <td>
                            <#if note.noteParty?has_content>
                                <div>&nbsp;${uiLabelMap.CommonBy}&nbsp;${Static["org.ofbiz.party.party.PartyHelper"].getPartyName(delegator, note.noteParty, true)}
                                </div>
                            </#if>

                        </td>
                        <td>
                        <div>&nbsp; ${uiLabelMap.CommonAt}&nbsp;<#if note.noteDateTime?has_content>${Static["org.ofbiz.base.util.UtilFormatOut"].formatDateTime(note.noteDateTime, "", locale, timeZone)!}</#if>
                        </div>
                        </td>
                        <td>
                                ${note.noteInfo?replace("\n", "<br/>")}
                        </td>
                        <td>
                        ${uiLabelMap.OrderPrintableNote}
                        </td>
                        <td>
                            <#if note.internalNote?if_exists == "N">

                                <form name="privateNotesForm_${note_index}" method="post" action="<@ofbizUrl>updateOrderNote</@ofbizUrl>">
                                    <input type="hidden" name="orderId" value="${orderId}"/>
                                    <input type="hidden" name="noteId" value="${note.noteId}"/>
                                    <input type="hidden" name="internalNote" value="Y"/>
                                    <a href="javascript:document.privateNotesForm_${note_index}.submit()" class="btn btn-primary btn-sm">${uiLabelMap.OrderNotesPrivate}</a>
                                </form>
                            </#if>
                            <#if note.internalNote?if_exists == "Y">

                                <form name="publicNotesForm_${note_index}" method="post" action="<@ofbizUrl>updateOrderNote</@ofbizUrl>">
                                    <input type="hidden" name="orderId" value="${orderId}"/>
                                    <input type="hidden" name="noteId" value="${note.noteId}"/>
                                    <input type="hidden" name="internalNote" value="N"/>
                                    <a href="javascript:document.publicNotesForm_${note_index}.submit()" class="btn btn-primary btn-sm">${uiLabelMap.OrderNotesPublic}</a>
                                </form>
                            </#if>
                        </td>
                    </tr>
                </#list>
            </table>
        <#else>
            &nbsp;${uiLabelMap.OrderNoNotes}.
        </#if>

    </div>
    </div>
</div>
</#if>

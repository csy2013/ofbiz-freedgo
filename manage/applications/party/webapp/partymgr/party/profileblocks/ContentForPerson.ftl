<div id="partyContent" class="screenlet">
    <div class="screenlet-title-bar">
        <ul>
            <li class="h3">${uiLabelMap.PartyContent}</li>
        </ul>
        <br class="clear" />
    </div>
    <div class="screenlet-body">
    ${screens.render("component://party/widget/partymgr/ProfileScreens.xml#ContentList")}
        <hr />
        <div class="label">${uiLabelMap.PartyAttachContent}</div>
        <form id="uploadPartyContent" method="post" enctype="multipart/form-data" action="<@ofbizUrl>uploadPartyContent</@ofbizUrl>">
            <input type="hidden" name="dataCategoryId" value="PERSONAL"/>
            <input type="hidden" name="contentTypeId" value="DOCUMENT"/>
            <input type="hidden" name="statusId" value="CTNT_PUBLISHED"/>
            <input type="hidden" name="partyId" value="${partyId}" id="contentPartyId"/>
            <input type="file" name="uploadedFile" class="required error" size="25"/>
            <div>
                <select name="partyContentTypeId" class="required error">
                    <option value="">${uiLabelMap.PartySelectPurpose}</option>
                <#list partyContentTypes as partyContentType>
                    <option value="${partyContentType.partyContentTypeId}">${partyContentType.get("description", locale)?default(partyContentType.partyContentTypeId)}</option>
                </#list>
                </select>
            </div>
            <div class="label">${uiLabelMap.PartyIsPublic}</div>
            <select name="isPublic">
                <option value="N">${uiLabelMap.CommonNo}</option>
                <option value="Y">${uiLabelMap.CommonYes}</option>
            </select>
            <select name="roleTypeId">
                <option value="">${uiLabelMap.PartySelectRole}</option>
            <#list roles as role>
                <option value="${role.roleTypeId}" <#if role.roleTypeId == "_NA_">selected="selected"</#if>>${role.get("description", locale)?default(role.roleTypeId)}</option>
            </#list>
            </select>
            <input type="submit" value="${uiLabelMap.CommonUpload}" />
        </form>
        <div id='progress_bar'><div></div></div>
    </div>
</div>


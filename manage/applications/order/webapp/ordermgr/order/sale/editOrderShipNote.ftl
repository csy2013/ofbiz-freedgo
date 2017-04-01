<form id="updateShippingInstructionsForm_${shipGroup.shipGroupSeqId}" name="updateShippingInstructionsForm" method="post"
      action="<@ofbizUrl>setShippingInstructions</@ofbizUrl>">
    <input type="hidden" name="orderId" value="${orderHeader.orderId}"/>
    <input type="hidden" name="shipGroupSeqId" value="${shipGroup.shipGroupSeqId}"/>
<#if shipGroup.shippingInstructions?has_content>
    <table>
        <tr>
            <td id="instruction">
                <label>${shipGroup.shippingInstructions}</label>
            </td>
            <td>
                <a href="javascript:editInstruction('${shipGroup.shipGroupSeqId}');" class="btn btn-primary btn-sm"
                   id="editInstruction_${shipGroup.shipGroupSeqId}">${uiLabelMap.CommonEdit}</a>
            </td>
        </tr>
    </table>
<#else>
    <a href="javascript:addInstruction('${shipGroup.shipGroupSeqId}');" class="btn btn-primary btn-sm"
       id="addInstruction_${shipGroup.shipGroupSeqId}">${uiLabelMap.CommonAdd}</a>
</#if>
    <a href="javascript:saveInstruction('${shipGroup.shipGroupSeqId}');" class="btn btn-primary btn-sm"
       id="saveInstruction_${shipGroup.shipGroupSeqId}"
       style="display:none">${uiLabelMap.CommonSave}</a>
    <textarea name="shippingInstructions" id="shippingInstructions_${shipGroup.shipGroupSeqId}" style="display:none" rows="0"
              cols="0">${shipGroup.shippingInstructions?if_exists}</textarea>
</form>
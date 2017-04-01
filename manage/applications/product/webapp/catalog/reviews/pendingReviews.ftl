<script language="JavaScript" type="text/javascript">
    function changeReviewStatus(statusId) {
        document.selectAllForm.statusId.value = statusId;
        document.selectAllForm.submit();
    }
</script>
 <@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductReviewsPendingApproval}"/>
        <#if !pendingReviews?has_content>
            <h3>${uiLabelMap.ProductReviewsNoPendingApproval}</h3>
        <#else>
            <form method='post' action='<@ofbizUrl>updateProductReview</@ofbizUrl>' name="selectAllForm" class="form-horizontal">
                <input type="hidden" name="_useRowSubmit" value="Y" />
                <input type="hidden" name="_checkGlobalScope" value="Y" />
                <input type="hidden" name="statusId" value="" />
                <div align="right" >
                    <input type="button" class="btn btn-primary" value="${uiLabelMap.CommonUpdate}" onclick="changeReviewStatus('PRR_PENDING')" />
                    <input type="button" class="btn btn-primary" value="${uiLabelMap.ProductPendingReviewUpdateAndApprove}" onclick="changeReviewStatus('PRR_APPROVED')" />
                    <input type="button" class="btn btn-primary" value="${uiLabelMap.CommonDelete}" onclick="changeReviewStatus('PRR_DELETED')" />
                </div>
                <br/>
                <table cellspacing="0" class="table table-bordered table-responsive">
                  <tr class="header-row">
                    <th><b>${uiLabelMap.ProductPendingReviewDate}</b></th>
                    <th><b>评论人</b></th>
                    <th><b>${uiLabelMap.CommonIsAnonymous}</b></th>
                    <th><b>${uiLabelMap.ProductProductId}</b></th>
                    <th><b>${uiLabelMap.ProductRating}</b></th>
                    <th><b>${uiLabelMap.CommonStatus}</b></th>
                    <th><b>${uiLabelMap.ProductReviews}</b></th>
                    <th align="right">
                        <span class="label">${uiLabelMap.CommonAll}</span>
                        <input type="checkbox" name="selectAll" value="${uiLabelMap.CommonY}" onclick="toggleAll(this, 'selectAllForm');highlightAllRows(this, 'review_tableRow_', 'selectAllForm');" />
                    </th>
                  </tr>
                <#assign rowCount = 0>
                <#assign rowClass = "2">
                <#list pendingReviews as review>

                <#if review.userLoginId?has_content>
                <#assign postedUserLogin = review.getRelatedOne("UserLogin")>
                <#if postedUserLogin.partyId?has_content>
                <#assign party = postedUserLogin.getRelatedOne("Party")>
                <#assign partyTypeId = party.get("partyTypeId")>

                <#if partyTypeId == "PERSON"|| partyTypeId == 'EMPLOYEE'>
                    <#assign postedPerson = postedUserLogin.getRelatedOne("Person")>
                <#else>
                    <#assign postedPerson = postedUserLogin.getRelatedOne("PartyGroup")>
                </#if>
                </#if>
                </#if>
                  <tr id="review_tableRow_${rowCount}" valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                      <td>
                          <input type="hidden" name="productReviewId_o_${rowCount}" value="${review.productReviewId}" />
                          ${review.postedDateTime?string("yyyy-MM-dd HH:mm")?if_exists}
                      </td>
                      <td>
                      <#if review.userLoginId?has_content && postedPerson?has_content>

                        <#if postedPerson.firstName?has_content && postedPerson.lastName?has_content>
                            ${postedPerson.firstName} ${postedPerson.lastName}
                        <#else>
                            ${postedPerson.groupName}
                        </#if>

                      <#else>
                          &nbsp;
                      </#if>
                      </td>
                      <td>
                          <select name='postedAnonymous_o_${rowCount}' class="form-control">
                              <option value="${review.postedAnonymous?default("N")}">${review.postedAnonymous?default("N")}</option>
                              <option value="${review.postedAnonymous?default("N")}">----</option>
                              <option value="N">${uiLabelMap.CommonN}</option>
                              <option value="Y">${uiLabelMap.CommonY}</option>
                          </select>
                      </td>
                      <td>${review.getRelatedOne("Product").internalName?if_exists}<br /><a class="buttontext" href="<@ofbizUrl>EditProduct?productId=${review.productId}</@ofbizUrl>">${review.productId}</a></td>
                      <td>
                          <input type="text" size='3' name="productRating_o_${rowCount}" value="${review.productRating?if_exists?string}" />
                      </td>
                      <td>${review.getRelatedOne("StatusItem").get("description", locale)}</td>
                      <td>
                         <textarea name="productReview_o_${rowCount}" rows="5" cols="30" wrap="hard" class="form-control">${review.productReview?if_exists}</textarea>
                      </td>
                      <td align="right">
                        <input type="checkbox" name="_rowSubmit_o_${rowCount}" value="Y" onclick="checkToggle(this, 'selectAllForm');highlightRow(this,'review_tableRow_${rowCount}');" />
                      </td>
                  </tr>
                <#assign rowCount = rowCount + 1>
                <#-- toggle the row color -->
                <#if rowClass == "2">
                    <#assign rowClass = "1">
                <#else>
                    <#assign rowClass = "2">
                </#if>
                </#list>
                <input type="hidden" name="_rowCount" value="${rowCount}" />
                </table>
            </form>
        </#if>
<@htmlScreenTemplate.renderScreenletEnd/>

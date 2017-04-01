<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.OrderOrderList}"/>

<div class="table-responsive">
    <table class="table table-striped table-bordered">
            <tr class="header-row">
                <th>${uiLabelMap.OrderOrderId}</th>
                <th>${uiLabelMap.FormFieldTitle_orderPickSheetPrintedDate}</th>
                <th>${uiLabelMap.ProductVerified}</th>
            </tr>
            <#if orders?has_content>
                <#list orders?sort_by("pickSheetPrintedDate") as order>
                    <tr>
                        <td><a href="/ordermgr/control/orderview?orderId=${order.orderId?if_exists}" class="buttontext" target="_blank">${order.orderId?if_exists}</a></td>
                        <td><#if order.pickSheetPrintedDate?exists>${order.pickSheetPrintedDate}</#if>&nbsp;</td>
                        <td><#if "Y" == order.isVerified>${uiLabelMap.CommonY}</#if></td>
                    </tr>
                </#list>
            <#else>
                <tr><td colspan="4"><h3>${uiLabelMap.OrderNoOrderFound}</h3></td></tr>
            </#if>
        </table>
    </div>
<@htmlScreenTemplate.renderScreenletEnd/>
<style>
    .order_flow {
        width: 100%;
        padding: 10px;
    }
    .order_flow ul {
        margin-bottom: 5px;
    }

    .order_flow ul li {
        display: block;
        width: 140px;
        float: left;
    }

    .order_flow ul li .bar {
        position: relative;
        height: 20px;
    }

    .order_flow ul li p {
        display: block;
        text-align: center;
    }

    p {
        margin: 0 0 10px;
    }

    .order_flow ul li .time {
        color: #ccc;
        width:130px;
    }

    .order_flow ul .active .bar i {
        background-color: #298DCC;
        color: #fff;
    }

    .order_flow ul li .bar i {
        display: block;
        position: absolute;
        z-index: 2;
        width: 20px;
        height: 20px;
        line-height: 20px;
        left: 50%;
        top: 50%;
        margin-left: -10px;
        margin-top: -10px;
        border-radius: 50%;
        background-color: #e6e6e6;
        font-style: normal;
        font-size: 12px;
    }

    .order_flow ul li:first-child .bar:after {
        width: 50%;
        margin-left: 70px;
    }
    .order_flow ul li:last-child .bar:after {
        width: 50%;
    }
    .order_flow ul .active .bar:after {
        background-color: #298DCC;
    }

    .order_flow ul li .bar:after {
        content: ' ';
        display: block;
        width: 100%;
        height: 4px;
        background-color: #e6e6e6;
        position: absolute;
        top: 50%;
        margin-top: -2px;
        z-index: 1;
    }
    .order_flow ul:after {
        content: '';
        display: block;
        width: 100%;
        height: 1px;
        clear: both;
    }
</style>
<@htmlScreenTemplate.renderScreenletBegin id="OrderActionsPanel" title=""/>
<#assign statusItems = delegator.findByAnd("StatusItem",{"statusTypeId":"ORDER_STATUS"},["sequenceId"]) >
<#assign hasCancel = "N">
<#list orderHeaderStatuses as ordStatus>
    <#if ordStatus.get("statusId") == 'ORDER_REJECTED' || ordStatus.get("statusId") == 'ORDER_CANCELLED'>
        <#assign hasCancel = "Y">
    </#if>
</#list>

<#assign seq = 1>
<#--${orderHeaderStatuses}-->
<div class="order_flow">
    <ul id="status">
    <#list statusItems as statusItem>
        <#if orderHeaderStatuses?exists>
            <#assign hasDo = false>
            <#list orderHeaderStatuses as orderStatus>
                <#if orderStatus.get("statusId") == statusItem.get("statusId")>
                    <#assign hasDo = true>
                    <li class="active">
                        <p class="name">${statusItem.get("description")}</p>
                        <p class="bar"><i>${seq}</i></p>
                        <p class="time">${orderStatus.get("statusDatetime")?string("yyyy-MM-dd HH:mm:ss")}</p>
                    </li>
                    <#assign seq = seq+1>
                </#if>
            </#list>
            <#if !hasDo && hasCancel == "N" && (statusItem.statusId != "ORDER_REJECTED") && (statusItem.statusId != "ORDER_CANCELLED")>
                <li>
                    <p class="name">${statusItem.get("description")}</p>
                    <p class="bar"><i>${seq}</i></p>
                    <p class="time"></p>
                </li>
                <#assign seq = seq+1>
            </#if>

        </#if>
    </#list>
    </ul>
</div>
<@htmlScreenTemplate.renderScreenletEnd/>




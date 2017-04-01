<#if productPromoId?exists && productPromo?exists>
<div class="alert alert-warning fade in m-b-15" id="promoActionMsg">
    <#--<span class="close" data-dismiss="alert">×</span>-->
</div>
<form action="<@ofbizUrl>createProductPromoCond</@ofbizUrl>" class="form-horizontal" method="post">
    <input type="hidden" name="productPromoId" value="${productPromoId}"/>
    <input type="hidden" name="productPromoRuleId" value="${productPromoRuleId}"/>
    <div class="form-group">
        <label class="col-md-5 control-label">选择条件类型(必填):</label>
        <div class="col-md-7">
            <select class="form-control" name="inputParamEnumId" id="cond_inputParamEnumId">
                <#list inputParamEnums as inputParamEnum>
                    <#if inputParamEnum.enumId != 'PPIP_ORDER_SHIPTOTAL' && inputParamEnum.enumId != 'PPIP_LPMUP_AMT' && inputParamEnum.enumId != 'PPIP_LPMUP_PER'
                    && inputParamEnum.enumId != 'PPIP_SERVICE' && inputParamEnum.enumId != 'PPIP_ROLE_TYPE' && inputParamEnum.enumId != 'PPIP_NEW_ACCT' &&inputParamEnum.enumId != 'PPIP_ORST_LAST_YEAR' >
                    <option value="${(inputParamEnum.enumId)?if_exists}">${(inputParamEnum.get("description",locale))?if_exists}</option>
                    </#if>
                </#list>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 control-label">选择条件操作(必填):</label>

        <div class="col-md-3">
            <select class="form-control" name="operatorEnumId">
                <#list condOperEnums as condOperEnum>
                    <option value="${(condOperEnum.enumId)?if_exists}">${(condOperEnum.get("description",locale))?if_exists}</option>
                </#list>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.ProductConditionValue}(必填):</label>

        <div class="col-md-7">
            <input class="form-control" type="text" size="2" name="condValue"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.CommonOther}:</label>

        <div class="col-md-7">

            <input class="form-control" type="text" size="10" name="otherValue"/>
        </div>
    </div>

    <#--<div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.OrderSelectShippingMethod}:</label>

        <div class="col-md-7">
            <select name="carrierShipmentMethod" class="form-control">
                <option value="">--${uiLabelMap.OrderSelectShippingMethod}--</option>
                <#list carrierShipmentMethods as carrierShipmentMethod>
                    <#assign shipmentMethodType = carrierShipmentMethod.getRelatedOneCache("ShipmentMethodType")>
                    <option value="${carrierShipmentMethod.partyId?if_exists}@${carrierShipmentMethod.shipmentMethodTypeId?if_exists}">${carrierShipmentMethod.partyId?if_exists}
                        &nbsp;${shipmentMethodType.get("description")?if_exists}</option>
                </#list>
            </select>
        </div>
    </div>-->
    <div class="form-group">
        <label class="col-md-5 control-label">&nbsp;</label>

        <div class="col-md-7">

            <input type="submit" value="${uiLabelMap.ProductCreateCondition}"/>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(function(){

        $("#cond_inputParamEnumId").change(function(){
            changeCondOption()
        });

        changeCondOption()
    });

    function changeCondOption(){
        if($('#cond_inputParamEnumId').val()==='PPIP_ORDER_TOTAL'){
            
            $("input[name='otherValue']").parent().parent().hide();
            $('#promoActionMsg').html("购物车小计：<br/>1)对应产品请在促销产品中选择");

        }else if($('#cond_inputParamEnumId').val()==='PPIP_PRODUCT_TOTAL'){

            $("input[name='otherValue']").parent().parent().hide();
            $('#promoActionMsg').html("产品总数：<br/>1)产品在购物车数量<br/>2)对应商品请在促销产品中选择<br/>3)如果没有选择产品默认购物车所有产品数量");

        }else if($('#cond_inputParamEnumId').val()==='PPIP_PRODUCT_AMOUNT'){
            
            $("input[name='otherValue']").parent().parent().hide();
            $('#promoActionMsg').html("X 产品金额：<br/>1)指定产品在购物车金额<br/>2)对应商品请在促销产品中选择<br/>3)如果没有选择产品默认购物车所有产品金额");

        }else if($('#cond_inputParamEnumId').val()==='PPIP_PRODUCT_QUANT'){
            
            $("input[name='otherValue']").parent().parent().hide();
            $('#promoActionMsg').html("X 产品数量:<br/>1)指定产品在购物车数量<br/>2)对应商品请在促销产品中选择<br/>3)如果没有选择产品默认购物车所有产品数量");
        }else if($('#cond_inputParamEnumId').val()==='PPIP_PARTY_ID'){
            
            $("input[name='otherValue']").parent().parent().hide();
            $('#promoActionMsg').html("会员标示:<br/>1)指定会员标识<br/>2)无需选择产品");
        }else if($('#cond_inputParamEnumId').val()==='PPIP_PARTY_GRP_MEM'){
            
            $("input[name='otherValue']").parent().parent().hide();
            $('#promoActionMsg').html("会员组标示:<br/>1)指定会员组标识<br/>2)无需选择产品");
        }else if($('#cond_inputParamEnumId').val()==='PPIP_PARTY_CLASS'){
            
            $("input[name='otherValue']").parent().parent().hide();
            $('#promoActionMsg').html("会员分类标示:<br/>1)指定会员分类标识<br/>2)无需选择产品");
        }else if($('#cond_inputParamEnumId').val()==='PPIP_ORST_HIST'){
            $("[name='carrierShipmentMethod'").parent().parent().hide();
            $("input[name='otherValue']").parent().parent().show();
            $("input[name='condValue']").parent().parent().show();
            $("input[name='condValue']").parent().prev().text('月数');
            $("input[name='otherValue']").parent().prev().text('订单小计数');
            $('#promoActionMsg').html("订单小计 X 在过去 Y 个月里:<br/>1)订单金额<br/>2)填入过去Y月<br/>3)无需选择产品");
        }else if($('#cond_inputParamEnumId').val()==='PPIP_RECURRENCE'){
            
            $("input[name='otherValue']").parent().parent().hide();
            $('#promoActionMsg').html("快乐时光:<br/>1)指定时间段进行打折促销<br/>2)具体定时任务处理需要录入专业数量<br/>3)无需选择产品");
        }else if($('#cond_inputParamEnumId').val()==='PPIP_ORST_YEAR'){
            
            $("input[name='otherValue']").parent().parent().hide();
            $('#promoActionMsg').html("今年以来的订单小计X:<br/>1)录入今年以来的订单小计<br/>2)无需选择产品");
        }else if($('#cond_inputParamEnumId').val()==='PPIP_ORST_YEAR'){
            $("input[name='condValue']").parent().prev().text('今年以来订单小计数');
            $("input[name='otherValue']").parent().parent().hide();
            $('#promoActionMsg').html("今年以来的订单小计X:<br/>1)录入今年以来的订单小计<br/>2)无需选择产品");
        }else if($('#cond_inputParamEnumId').val()==='PPIP_GRPODR_TOTAL'){

            $("input[name='otherValue']").parent().parent().show();
            $("input[name='condValue']").parent().parent().show();
            $("input[name='otherValue']").parent().prev().text('最大订单数');
            $("input[name='condValue']").parent().prev().text('最小订单数');

            $('#promoActionMsg').html("团购有效订单数:<br/>1)录入订单的数量范围<br/>2)促销中选择对应的产品，目前支持一个产品选择");
        }else if($('#cond_inputParamEnumId').val()==='SALE_TIME_BTW'){

            $("input[name='otherValue']").parent().parent().show();
            $("input[name='condValue']").parent().parent().show();
            $("input[name='otherValue']").parent().prev().text('销售结束时间');
            $("input[name='condValue']").parent().prev().text('销售开始时间');

            $('#promoActionMsg').html("销售时间区间:<br/>1)录入销售开始时间<br/>2)时间格式yyyy-MM-dd HH:mm:ss 例:2015-01-09 01:29:40");
        }
    }

</script>

</#if>
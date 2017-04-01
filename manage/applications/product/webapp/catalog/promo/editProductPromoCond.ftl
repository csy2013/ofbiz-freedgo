<#if productPromoId?exists && productPromoCond?exists>
    <#assign otherValue = productPromoCond.otherValue?if_exists>
    <#if otherValue?has_content && otherValue.contains("@")>
        <#assign strarr = otherValue.split("@")>
        <#assign shipmentMethodTypeId = strarr[1]>
        <#assign partyId = strarr[0]>
    <#--${partyId}|${shipmentMethodTypeId}-->
    </#if>
<div class="alert alert-warning fade in m-b-15" id="promoActionMsg">
    <span class="close" data-dismiss="alert">×</span>
</div>
<form action="<@ofbizUrl>updateProductPromoCond</@ofbizUrl>" class="form-horizontal" method="post">
    <input type="hidden" name="productPromoId" value="${productPromoCond.productPromoId}"/>
    <input type="hidden" name="productPromoRuleId" value="${productPromoCond.productPromoRuleId}"/>
    <input type="hidden" name="productPromoCondSeqId" value="${productPromoCond.productPromoCondSeqId}"/>
    <#--<input type="hidden" name="otherValue" value="${otherValue}"/>-->
    <div class="form-group">
        <label class="col-md-5 control-label">选择条件类型(必填):</label>

        <div class="col-md-7">
            <select class="form-control" name="inputParamEnumId" id="edit_inputParamEnumId">
                <#list inputParamEnums as inputParamEnum>
                    <#if inputParamEnum.enumId != 'PPIP_ORDER_SHIPTOTAL' && inputParamEnum.enumId != 'PPIP_LPMUP_AMT' && inputParamEnum.enumId != 'PPIP_LPMUP_PER'
                    && inputParamEnum.enumId != 'PPIP_SERVICE' && inputParamEnum.enumId != 'PPIP_ROLE_TYPE' >
                        <option value="${(inputParamEnum.enumId)?if_exists}"
                                <#if (inputParamEnum.enumId) == productPromoCond.inputParamEnumId>selected</#if>>${(inputParamEnum.get("description",locale))?if_exists}</option>
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
                    <option value="${(condOperEnum.enumId)?if_exists}"
                            <#if (condOperEnum.enumId) == productPromoCond.operatorEnumId>selected</#if>>${(condOperEnum.get("description",locale))?if_exists}</option>
                </#list>
            </select>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.ProductConditionValue}(必填):</label>

        <div class="col-md-7">
            <input class="form-control" id="edit_condValue" type="text" size="2" name="condValue" value="${productPromoCond.condValue?if_exists}"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.CommonOther}:</label>

        <div class="col-md-7">
            <input class="form-control" id="edit_otherValue" type="text" size="10" name="otherValue" value="${otherValue}"/>
        </div>
    </div>

<#-- <div class="form-group">
     <label class="col-md-5 control-label">${uiLabelMap.OrderSelectShippingMethod}:</label>

     <div class="col-md-7">
         <select name="carrierShipmentMethod" class="form-control">
             <option value="">--${uiLabelMap.OrderSelectShippingMethod}--</option>
             <#list carrierShipmentMethods as carrierShipmentMethod>
                 <#assign shipmentMethodType = carrierShipmentMethod.getRelatedOneCache("ShipmentMethodType")>
                 <#assign optVal = carrierShipmentMethod.partyId?if_exists +"@"+carrierShipmentMethod.shipmentMethodTypeId?if_exists>
                 <option value="${optVal}" <#if (carrierShipmentMethod.partyId?if_exists ==partyId?if_exists)&&(carrierShipmentMethod.shipmentMethodTypeId?if_exists==shipmentMethodTypeId?if_exists) > selected</#if>>
                 ${carrierShipmentMethod.partyId?if_exists}&nbsp;${shipmentMethodType.get("description")?if_exists}</option>
             </#list>
         </select>
     </div>
 </div>-->

    <div class="form-group">
        <label class="col-md-5 control-label">&nbsp;</label>

        <div class="col-md-7">

            <input type="submit" value="${uiLabelMap.ProductUpdateCondition}"/>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(function(){

        $("#edit_inputParamEnumId").change(function(){
            changeCondOption();
        });

        changeCondOption();
    });
    function changeCondOption(){
        if($('#edit_inputParamEnumId').val()==='PPIP_ORDER_TOTAL'){

            $("#edit_otherValue").parent().parent().hide();
            $('#promoActionMsg').html("购物车小计：<br/>1)对应产品请在促销产品中选择");

        }else if($('#edit_inputParamEnumId').val()==='PPIP_PRODUCT_TOTAL'){

            $("#edit_otherValue").parent().parent().hide();
            $('#promoActionMsg').html("产品总数：<br/>1)产品在购物车数量<br/>2)对应商品请在促销产品中选择<br/>3)如果没有选择产品默认购物车所有产品数量");

        }else if($('#edit_inputParamEnumId').val()==='PPIP_PRODUCT_AMOUNT'){

            $("#edit_otherValue").parent().parent().hide();
            $('#promoActionMsg').html("X 产品金额：<br/>1)指定产品在购物车金额<br/>2)对应商品请在促销产品中选择<br/>3)如果没有选择产品默认购物车所有产品金额");

        }else if($('#edit_inputParamEnumId').val()==='PPIP_PRODUCT_QUANT'){

            $("#edit_otherValue").parent().parent().hide();
            $('#promoActionMsg').html("X 产品数量:<br/>1)指定产品在购物车数量<br/>2)对应商品请在促销产品中选择<br/>3)如果没有选择产品默认购物车所有产品数量");
        }else if($('#edit_inputParamEnumId').val()==='PPIP_PARTY_ID'){

            $("#edit_otherValue").parent().parent().hide();
            $('#promoActionMsg').html("会员标示:<br/>1)指定会员标识<br/>2)无需选择产品");
        }else if($('#edit_inputParamEnumId').val()==='PPIP_PARTY_GRP_MEM'){

            $("#edit_otherValue").parent().parent().hide();
            $('#promoActionMsg').html("会员组标示:<br/>1)指定会员组标识<br/>2)无需选择产品");
        }else if($('#edit_inputParamEnumId').val()==='PPIP_PARTY_CLASS'){

            $("#edit_otherValue").parent().parent().hide();
            $('#promoActionMsg').html("会员分类标示:<br/>1)指定会员分类标识<br/>2)无需选择产品");
        }else if($('#edit_inputParamEnumId').val()==='PPIP_ORST_HIST'){
            $("[name='carrierShipmentMethod'").parent().parent().hide();
            $("#edit_otherValue").parent().parent().show();
            $("#edit_condValue").parent().parent().show();
            $("#edit_condValue").parent().prev().text('月数');
            $("#edit_otherValue").parent().prev().text('订单小计数');
            $('#promoActionMsg').html("订单小计 X 在过去 Y 个月里:<br/>1)订单金额<br/>2)填入过去Y月<br/>3)无需选择产品");
        }else if($('#edit_inputParamEnumId').val()==='PPIP_RECURRENCE'){

            $("#edit_otherValue").parent().parent().hide();
            $('#promoActionMsg').html("快乐时光:<br/>1)指定时间段进行打折促销<br/>2)具体定时任务处理需要录入专业数量<br/>3)无需选择产品");
        }else if($('#edit_inputParamEnumId').val()==='PPIP_ORST_YEAR'){

            $("#edit_otherValue").parent().parent().hide();
            $('#promoActionMsg').html("今年以来的订单小计X:<br/>1)录入今年以来的订单小计<br/>2)无需选择产品");
        }else if($('#edit_inputParamEnumId').val()==='PPIP_ORST_YEAR'){
            $("#edit_condValue").parent().prev().text('今年以来订单小计数');
            $("#edit_otherValue").parent().parent().hide();
            $('#promoActionMsg').html("今年以来的订单小计X:<br/>1)录入今年以来的订单小计<br/>2)无需选择产品");
        }else if($('#edit_inputParamEnumId').val()==='PPIP_GRPODR_TOTAL'){

            $("#edit_otherValue").parent().parent().show();
            $("#edit_condValue").parent().parent().show();
            $("#edit_otherValue").parent().prev().text('最大订单数');
            $("#edit_condValue").parent().prev().text('最小订单数');

            $('#promoActionMsg').html("团购有效订单数:<br/>1)录入订单的数量范围<br/>2)促销中选择对应的产品，目前支持一个产品选择");
        }else if($('#edit_inputParamEnumId').val()==='SALE_TIME_BTW'){

            $("#edit_otherValue").parent().parent().show();
            $("#edit_condValue").parent().parent().show();
            $("#edit_otherValue").parent().prev().text('销售结束时间');
            $("#edit_condValue").parent().prev().text('销售开始时间');

            $('#promoActionMsg').html("销售时间区间:<br/>1)录入销售开始时间<br/>2)时间格式yyyy-MM-dd HH:mm:ss 例:2015-01-09 01:29:40");
        }
    }
</script>
</#if>
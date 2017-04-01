<#if productPromoId?exists && productPromoAction?exists>
<div class="alert alert-warning fade in m-b-15" id="promoActionMsg">
    <span class="close" data-dismiss="alert">×</span>
</div>
<#--${productPromoAction}-->
<form action="<@ofbizUrl>updateProductPromoAction</@ofbizUrl>" class="form-horizontal" method="post" name="updateProductPromoAction">
    <input type="hidden" name="productPromoId" value="${productPromoAction.productPromoId}"/>
    <input type="hidden" name="productPromoRuleId" value="${productPromoAction.productPromoRuleId}"/>
    <input type="hidden" name="productPromoActionSeqId" value="${productPromoAction.productPromoActionSeqId}"/>
    <input type="hidden" name="productPromoCondSeqId" value="${productPromoAction.productPromoCondSeqId}"/>
    <div class="form-group">
        <label class="col-md-5 control-label">选择执行动作(必填):</label>
        <div class="col-md-7">
            <select class="form-control" name="productPromoActionEnumId">
                <#list productPromoActionEnums as productPromoActionEnum>
                    <#if productPromoActionEnum.enumId != 'PROMO_SHIP_CHARGE' && productPromoActionEnum.enumId != 'PROMO_SERVICE' && productPromoActionEnum.enumId != 'PROMO_TAX_PERCENT' >
                        <option value="${(productPromoActionEnum.enumId)?if_exists}"
                            <#if productPromoActionEnum.enumId?if_exists ==productPromoAction.productPromoActionEnumId?if_exists>selected</#if>>${(productPromoActionEnum.get("description",locale))?if_exists}</option>
                    </#if>
                </#list>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.ProductQuantity}(必填):</label>

        <div class="col-md-3">
            <input type="text" class="form-control" size="5" name="quantity" value="${productPromoAction.quantity?default(1)}"/>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.ProductAmount}(必填):</label>

        <div class="col-md-7">
            <input type="text" size="5" class="form-control"  name="amount" value="${productPromoAction.amount?if_exists}"/>
        </div>
    </div>
    <#--<div class="form-group">
        <label class="col-md-5 control-label"> ${uiLabelMap.ProductItemId}:</label>
        <div class="col-md-7">
            <@htmlTemplate.lookupField formName="updateProductPromoAction" position="center" name="productId" value="${productPromoAction.productId?if_exists}" id="productId" fieldFormName="LookupProduct"/>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-5 control-label">会员标识:</label>

        <div class="col-md-7">
            &lt;#&ndash;<input type="text" size="10" class="form-control" name="partyId" value="${productPromoAction.partyId}"/>&ndash;&gt;
            <@htmlTemplate.lookupField formName="updateProductPromoAction" value="${productPromoAction.partyId?if_exists}" position="center" name="partyId" id="partyId" fieldFormName="LookupPartyName"/>
        </div>
    </div>-->
   <#-- <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.UseCartQuantity}:</label>

        <div class="col-md-7">
            <select name="useCartQuantity" class="form-control">
                <option value="N" <#if productPromoAction.useCartQuantity=='N'>selected</#if>>${uiLabelMap.CommonN}</option>
                <option value="Y" <#if productPromoAction.useCartQuantity=='Y'>selected</#if>>${uiLabelMap.CommonY}</option>
            </select>
        </div>
    </div>-->
    <input type="hidden" name="useCartQuantity" value="N"/>
    <div class="form-group">
        <label class="col-md-5 control-label">&nbsp;</label>
        <div class="col-md-7">
            <input type="submit" value="${uiLabelMap.ProductUpdateActionition}"/>
        </div>
    </div>
</form>
<script type="text/javascript">
    $(function(){
        $("[name='productPromoActionEnumId']").change(function(){
            changeActionOption();
        });
        changeActionOption();
    });

    function changeActionOption(){
        if($("[name='productPromoActionEnumId']").val()==='PROMO_GWP'){
            $("input[name='amount']").parent().parent().hide();
            $('#promoActionMsg').html("购物有礼：<br/>1)选择礼物数量<br/>2)对应礼物请在促销产品中选择");
        }else if($("[name='productPromoActionEnumId']").val()==='PROMO_PROD_DISC'){
            $("input[name='amount']").parent().prev().text('折扣(百分比)');
            $("input[name='amount']").parent().parent().show();
            $("input[name='quantity']").parent().parent().hide();
            $('#promoActionMsg').html("x产品获得Y%折扣：<br/>1)选择打折商品数量<br/>2)对应打折商品请在促销产品中选择");
        }else if($("[name='productPromoActionEnumId']").val()==='PROMO_PROD_AMDISC'){
            $("input[name='amount']").parent().prev().text('折扣金额');
            $("input[name='amount']").parent().parent().show();
            $("input[name='quantity']").parent().parent().hide();
            $('#promoActionMsg').html("x产品减去Y元：<br/>1)选择打折商品数量<br/>2)对应打折商品请在促销产品中选择");
        }else if($("[name='productPromoActionEnumId']").val()==='PROMO_ORDER_PERCENT'){
            $("input[name='amount']").parent().prev().text('订单折扣百分比');
            $("input[name='amount']").parent().parent().show();
            $("input[name='quantity']").parent().parent().hide();
            $('#promoActionMsg').html("订单百分比折扣:<br/>1)输入订单打折的百分比<br/>2)无需选择产品");
        }else if($("[name='productPromoActionEnumId']").val()==='PROMO_ORDER_AMOUNT'){
            $("input[name='amount']").parent().prev().text('去掉的金额');
            $("input[name='amount']").parent().parent().show();
            $("input[name='quantity']").parent().parent().hide();
            $('#promoActionMsg').html("订单金额去掉零头:<br/>1)输入订单去掉零头<br/>2)无需选择产品");
        }else if($("[name='productPromoActionEnumId']").val()==='PROMO_PROD_SPPRC'){
            $("input[name='amount']").parent().parent().hide();
            $("input[name='quantity']").parent().parent().hide();

            $('#promoActionMsg').html("产品按 [特别促销] 价格:<br/>1)对应商品按特别促销价，<br/>请在促销产品中选择");
        }else if($("[name='productPromoActionEnumId']").val()==='PROMO_PROD_ASGPC'){
            $("input[name='amount']").parent().prev().text('指定产品价格');
            $("input[name='amount']").parent().parent().show();
            $("input[name='quantity']").parent().parent().hide();
            $('#promoActionMsg').html("指定产品价格:<br/>1)输入产品指定价格<br/>2)对应打折商品请在促销产品中选择");

        }else if($("[name='productPromoActionEnumId']").val()==='PROMO_PROD_PRICE'){
            $("input[name='amount']").parent().prev().text('价格扣减');
            $("input[name='amount']").parent().parent().show();
            $("input[name='quantity']").parent().parent().hide();
            $('#promoActionMsg').html("商品价格扣减:<br/>1)输入扣减的金额<br/>2)对应打折商品请在促销产品中选择3)默认只扣减一次");
        }
    }

</script>
</#if>
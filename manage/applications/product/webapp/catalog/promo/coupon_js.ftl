<script type="text/javascript">
    $(function () {
        $('select[name="promoType"]').change(function () {
             console.log($(this).val());
            var promoType = $(this).val();
            if(promoType === 'COUPON_TYPE_SUB'){
                var subHtml = "<div class='form-group m-b-xs'><label class='col-md-5 col-lg-5 control-label' id='EditCoupon_orderAmount_title' for='EditCoupon_orderAmount'>用户下单金额</label> <div class='col-md-7 col-lg-7'> " +
                        "<div class='input-group'> <input type='text' name='orderAmount' class='form-control input-sm' size='25' id='EditCoupon_orderAmount' data-parsley-required='true'" +
                        " data-parsley-pattern='^[0-9]+(.[0-9]{2})?$'></div></div> </div><div class='form-group m-b-xs'> <label class='col-md-5 col-lg-5 control-label' id='EditCoupon_subtractAmount_title'" +
                        " for='EditCoupon_subtractAmount'>优惠金额</label><div class='col-md-7 col-lg-7 '> <div class='input-group'> <input type='text' name='subtractAmount' " +
                        "class='form-control input-sm' size='25'id='EditCoupon_subtractAmount' data-parsley-required='true' data-parsley-pattern='^[0-9]+(.[0-9]{2})?$'></div></div> </div>";
                $("input[name='orderAmount']").parent().parent().parent().remove();
                $("input[name='subtractAmount']").parent().parent().parent().remove();
                $("input[name='discountAmount']").parent().parent().parent().remove();
                $("input[name='productId']").parent().parent().parent().remove();
                $('select[name="promoType"]').parent().parent().parent().after(subHtml);

            }else if(promoType === 'COUPON_TYPE_DISC'){
                var discHtml = "<div class='form-group m-b-xs'><label class='col-md-5 col-lg-5 control-label' id='EditCoupon_orderAmount_title' for='EditCoupon_orderAmount'>用户下单金额</label> <div class='col-md-7 col-lg-7'> " +
                        "<div class='input-group'> <input type='text' name='orderAmount' class='form-control input-sm' size='25' id='EditCoupon_orderAmount' data-parsley-required='true'" +
                        " data-parsley-pattern='^[0-9]+(.[0-9]{2})?$'></div></div> </div><div class='form-group m-b-xs'><label class='col-md-5 col-lg-5 control-label' " +
                        "id='EditCoupon_discountAmount_title' for='EditCoupon_discountAmount'>优惠折扣</label> <div class='col-md-7 col-lg-7 '> <div class='input-group'>" +
                        "<input type='text' name='discountAmount' class='form-control input-sm' size='25' id='EditCoupon_discountAmount' data-parsley-required='true' " +
                        "data-parsley-type='number'></div></div> </div>";
                $("input[name='orderAmount']").parent().parent().parent().remove();
                $("input[name='subtractAmount']").parent().parent().parent().remove();
                $("input[name='discountAmount']").parent().parent().parent().remove();
                $("input[name='productId']").parent().parent().parent().remove();
                $('select[name="promoType"]').parent().parent().parent().after(discHtml);


            }else if(promoType === 'COUPON_TYPE_GIFT'){
                var giftHtml = "<div class='form-group m-b-xs'><label class='col-md-5 col-lg-5 control-label' id='EditCoupon_orderAmount_title' for='EditCoupon_orderAmount'>用户下单金额</label> <div class='col-md-7 col-lg-7'> " +
                        "<div class='input-group'> <input type='text' name='orderAmount' class='form-control input-sm' size='25' id='EditCoupon_orderAmount' data-parsley-required='true'" +
                        " data-parsley-pattern='^[0-9]+(.[0-9]{2})?$'></div></div> </div><div class='form-group m-b-xs'><label class='col-md-5 col-lg-5 control-label' id='EditCoupon_productId_title'>赠送商品</label>" +
                        "<div class='col-md-7 col-lg-7 '><script type='text/javascript'>jQuery(document).ready(function () " +
                        "{if (!jQuery(\"form[name='EditCoupon']\").length) {alert('Developer: for lookups to work you must provide a form name!')}});<\/script><div class='input-group'><div id='0_lookupId_EditCoupon_productId_auto'></div><input type='text' class='form-control input-sm ui-autocomplete-input' name='productId' size='25' id='0_lookupId_EditCoupon_productId' placeholder='必须的' data-parsley-required='true' autocomplete='off'><span class='input-group-btn'><button id='0_lookupId_button' name='0_lookupId_button' class='btn btn-default btn-sm'><i class='fa fa-search fa-fw'></i></button></span></div><script type='text/javascript'>jQuery(document).ready(function () {new ConstructBootstrapModalLookup('LookupProduct', 'EditCoupon_productId', document.EditCoupon.productId, null, 'EditCoupon', '', '', 'topcenter', 'true', 'LookupProduct?ajaxLookup=Y,EditCoupon_productId,/catalog/control/LookupProduct,ajaxLookup=Y&amp;_LAST_VIEW_NAME_=AddCouponPromo&amp;searchValueFieldName=productId', true, 'layer', '2', '300');});<\/script></div></div>";
                $("input[name='orderAmount']").parent().parent().parent().remove();
                $("input[name='subtractAmount']").parent().parent().parent().remove();
                $("input[name='discountAmount']").parent().parent().parent().remove();
                $("input[name='productId']").parent().parent().parent().remove();
                $('select[name="promoType"]').parent().parent().parent().after(giftHtml);


            }else if(promoType === 'COUPON_TYPE_SUB1'){
                var sub1Html = "<div class='form-group m-b-xs'><label class='col-md-5 col-lg-5 control-label' id='EditCoupon_subtractAmount_title'" +
                        " for='EditCoupon_subtractAmount'>优惠金额</label><div class='col-md-7 col-lg-7 '> <div class='input-group'> <input type='text' name='subtractAmount' " +
                        "class='form-control input-sm' size='25'id='EditCoupon_subtractAmount' data-parsley-required='true' data-parsley-pattern='^[0-9]+(.[0-9]{2})?$'></div></div> </div>";
                $("input[name='orderAmount']").parent().parent().parent().remove();
                $("input[name='subtractAmount']").parent().parent().parent().remove();
                $("input[name='discountAmount']").parent().parent().parent().remove();
                $("input[name='productId']").parent().parent().parent().remove();
                $('select[name="promoType"]').parent().parent().parent().after(sub1Html);


            }

        });
    });
</script>
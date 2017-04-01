
<script language="JavaScript" type="text/javascript">
    function toggle(e) {
        e.checked = !e.checked;
    }
    function checkToggle(e) {
        var cform = document.salesentryform;
        if (e.checked) {
            var len = cform.elements.length;
            var allchecked = true;
            for (var i = 0; i < len; i++) {
                var element = cform.elements[i];
                if (element.name == "selectedItem" && !element.checked) {
                    allchecked = false;
                }
                cform.selectAll.checked = allchecked;
            }
        } else {
            cform.selectAll.checked = false;
        }
    }
    function toggleAll() {
        var cform = document.salesentryform;
        var len = cform.elements.length;
        for (var i = 0; i < len; i++) {
            var e = cform.elements[i];
            if (e.name == "selectedItem") {
                toggle(e);
            }
        }
    }
    function removeSelected() {
        var cform = document.salesentryform;
        cform.removeSelected.value = true;
        cform.submit();
    }
    function addToList() {
        var cform = document.salesentryform;
        cform.action = "<@ofbizUrl>addBulkToShoppingList</@ofbizUrl>";
        cform.submit();
    }
    function gwAll(e) {
        var cform = document.salesentryform;
        var len = cform.elements.length;
        var selectedValue = e.value;
        if (selectedValue == "") {
            return;
        }

        var cartSize = ${shoppingCartSize};
        var passed = 0;
        for (var i = 0; i < len; i++) {
            var element = cform.elements[i];
            var ename = element.name;
            var sname = ename.substring(0, 16);
            if (sname == "option^GIFT_WRAP") {
                var options = element.options;
                var olen = options.length;
                var matching = -1;
                for (var x = 0; x < olen; x++) {
                    var thisValue = element.options[x].value;
                    if (thisValue == selectedValue) {
                        element.selectedIndex = x;
                        passed++;
                    }
                }
            }
        }
        if (cartSize > passed && selectedValue != "NO^") {
            showErrorAlert("${uiLabelMap.CommonErrorMessage2}", "${uiLabelMap.OrderSelectedGiftNotAvailableForAll}");
        }
        cform.submit();
    }
    function quicklookup_popup(element) {
        target = element;  // note: global var target comes from fieldlookup.js
        var searchTerm = element.value;
        var obj_lookupwindow = window.open('LookupProduct?productId_op=like&amp;productId_ic=Y&amp;productId=' + searchTerm, 'FieldLookup', 'width=700,height=550,scrollbars=yes,status=no,resizable=yes,top=' + my + ',left=' + mx + ',dependent=yes,alwaysRaised=yes');
        obj_lookupwindow.opener = window;
        obj_lookupwindow.focus();
    }


    function submitForm(form, mode, value) {
        if (mode == "DN") {
            // done action; checkout
            form.action = "<@ofbizUrl>salecheckout</@ofbizUrl>";
            form.submit();
        } else if (mode == "CS") {
            // continue shopping
            form.action = "<@ofbizUrl>updateCheckoutOptionsForSale/showcart</@ofbizUrl>";
            form.submit();
        } else if (mode == "NA") {
            // new address
        <#--window.location.href="<@ofbizUrl>updateCheckoutOptionsForSaleForSale/editcontactmech?DONE_PAGE=quickcheckout&partyId=${shoppingCart.getPartyId()}&preContactMechTypeId=POSTAL_ADDRESS&contactMechPurposeTypeId=SHIPPING_LOCATION</@ofbizUrl>";-->
            form.action = "<@ofbizUrl>updateCheckoutOptionsForSale/editcontactmech?DONE_PAGE=quickcheckout&preContactMechTypeId=POSTAL_ADDRESS&contactMechPurposeTypeId=SHIPPING_LOCATION</@ofbizUrl>";
            form.submit();
        } else if (mode == "EA") {
            // edit address
            form.action = "<@ofbizUrl>updateCheckoutOptionsForSale/editcontactmech?DONE_PAGE=quickcheckout&partyId=${shoppingCart.getPartyId()}&contactMechId="+ value+"</@ofbizUrl>";
            form.submit();
        } else if (mode == "NC") {
            // new credit card
            form.action = "<@ofbizUrl>updateCheckoutOptionsForSale/editcreditcard?DONE_PAGE=quickcheckout&partyId=${shoppingCart.getPartyId()}</@ofbizUrl>";
            form.submit();
        } else if (mode == "EC") {
            // edit credit card
            form.action = "<@ofbizUrl>updateCheckoutOptionsForSale/editcreditcard?DONE_PAGE=quickcheckout&partyId=${shoppingCart.getPartyId()}&paymentMethodId="+ value+"</@ofbizUrl>";
            form.submit();
        } else if (mode == "GC") {
            // edit gift card
            form.action = "<@ofbizUrl>updateCheckoutOptionsForSale/editgiftcard?DONE_PAGE=quickcheckout&partyId=${shoppingCart.getPartyId()}&paymentMethodId="+ value+"</@ofbizUrl>";
            form.submit();
        } else if (mode == "NE") {
            // new eft account
            form.action = "<@ofbizUrl>updateCheckoutOptionsForSale/editeftaccount?DONE_PAGE=quickcheckout&partyId=${shoppingCart.getPartyId()}</@ofbizUrl>";
            form.submit();
        } else if (mode == "EE") {
            // edit eft account
            form.action = "<@ofbizUrl>updateCheckoutOptionsForSale/editeftaccount?DONE_PAGE=quickcheckout&partyId=${shoppingCart.getPartyId()}&paymentMethodId="+ value+"</@ofbizUrl>";
            form.submit();
        } else if (mode == "SP") {
            // split payment
            form.action = "<@ofbizUrl>updateCheckoutOptionsForSale/checkoutpayment?partyId=${shoppingCart.getPartyId()}</@ofbizUrl>";
            form.submit();
        } else if (mode == "SA") {
            // selected shipping address
            form.action = "<@ofbizUrl>updateCheckoutOptionsForSale/quickcheckout</@ofbizUrl>";
            form.submit();
        } else if (mode == "SC") {
            // selected ship to party
            form.action = "<@ofbizUrl>cartUpdateShipToCustomerParty</@ofbizUrl>";
            form.submit();
        } else if (mode == "MC") {
            // selected ship to party
            form.action = "<@ofbizUrl>salemodifycart</@ofbizUrl>";
            form.submit();
        } else if (mode == "DC") {
            // selected ship to party
            form.removeSelected.value = true;
            form.action = "<@ofbizUrl>salemodifycart</@ofbizUrl>";
            form.submit();
        }
    }

    $(function () {
        FormWizardValidation.init();
    });


    var handleBootstrapWizardsValidation = function () {
        "use strict";
        $("#wizard").bwizard({
            <#if request.getPathInfo().indexOf('dealsaleorderentry')!=-1>
            activeIndex:1,
            </#if>
            validating: function (e, t) {
                if (t.index == 0) {
                    if (false === $('form[name="salesentryform"]').parsley().validate("wizard-step-1")) {
                        return false
                    }
                } else if (t.index == 1) {
                    if (false === $('form[name="salesentryform"]').parsley().validate("wizard-step-2")) {
                        return false
                    }
                } else if (t.index == 2) {
                    if (false === $('form[name="salesentryform"]').parsley().validate("wizard-step-3")) {
                        return false
                    }
                } else if (t.index == 3) {
                    if (false === $('form[name="salesentryform"]').parsley().validate("wizard-step-4")) {
                        return false
                    }
                }
            }

        })
    };
    var FormWizardValidation = function () {
        "use strict";
        return {
            init: function () {
                handleBootstrapWizardsValidation()
            }
        }
    }();

    function quicklookup(element) {
    <#if shoppingCart.getOrderType() == "PURCHASE_ORDER">
        window.location = '<@ofbizUrl>LookupBulkAddSupplierProducts</@ofbizUrl>?productId=' + element.value;
    <#else>
        window.location = '<@ofbizUrl>LookupBulkAddProducts</@ofbizUrl>?productId=' + element.value;

    </#if>
    }

</script>

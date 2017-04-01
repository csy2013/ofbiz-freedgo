function isPlaceholderSupport() {
    return 'placeholder' in document.createElement('input');
}


$(function() {
    var num = 0;
    var flag = false;
    var len=$('.changeBg .img').length;
    var localBag="base";

    var skin = $.cookie('skin');
    if(skin!=null){
        $('.changeBg .box .img').each(function(index,element){
            if(skin == $(this).attr('data-bg')){
                changeSkin(index);
                return false;
            }
        });
    }else{
        changeSkin(0);
    }

    /**
     * 切换皮肤
     */
    function changeSkin(num){
        $('.changeBg .box .img').css('opacity', 0).eq(num).css('opacity', 1);
        localBag=$('.changeBg .box .img').eq(num).attr('data-bg')

        $('.changeBg .box .img').each(function(index,element){
            $(document.body).removeClass($(this).attr('data-bg'));
        });
        $(document.body).addClass(localBag);
        $(document.body).attr('skin',localBag);
        $('#faceWord').html($('.changeBg .box .img').eq(num).attr('data-word'))
    }

    //顶部图层
    $('.topMiddle .prev').click(function() {
        --num;
        if (num<0) {
            num=len-1;
        }
        changeSkin(num);
    })
    $('.topMiddle .next').click(function() {
        ++num;
        if (num>=(len)) {
            num=0;
        }
        changeSkin(num);
    });


    $("#changeBig").click(function() {
        var skin = $(document.body).attr('skin');
        if(skin!=null){
            $('.changeBg .box .img').each(function(index,element){
                if(skin == $(this).attr('data-bg')){
                    num = index;
                }
            });
        }

        $('.changeBg').slideDown('slow', function() {
            $('.topMiddle .save').show();
        })
    })
    $('.topMiddle .save').click(function() {
        //localBg变量就是存的当前背景
        $('.topMiddle .save').hide();
        $('.changeBg').slideUp('slow', function() {
            $('.topMiddle .save').hide();
        })
        // 保存到cookie中
        $.cookie('skin', $(document.body).attr('skin'));
    })

    //login
    $('.title .trolley').hover(function() {
        $(this).find('.chartBar').addClass("hover").find('.loginBar').show();
    }, function() {
        $(this).find('.chartBar').removeClass("hover").find('.loginBar').hide();
    })

    //sidebar
    $('.left .section .list').hover(function() {
        var now = $(this).find('.menu');
        if (now.length) {
            $(this).addClass("hover")
            now.show();
        }
    }, function() {
        var now = $(this).find('.menu');
        if (now.length) {
            $(this).removeClass("hover")
            now.hide();
        }
    })

    //
    $('.nav .event').hover(function() {
        var now = $(this).index();
        if (now.length) {
            $(this).addClass("hover")
            now.show();
        } else {

        }
    }, function() {
        var now = $(this).index();
        if (now.length) {
            $(this).removeClass("hover")
            now.hide();
        }
    })


    if(!isPlaceholderSupport()){
        var elem= $('.searchWarpper').find('input[type=text]');
        elem.each(function(index,elem){
            if($(this).attr('value')==''||$(this).attr('value')==null){
                $(this).attr('value',$(this).attr('placeholder'))
            }
        });
        elem.on('blur',function(){
            if($(this).val().length!=0){
                return
            }else{
                $(this).attr('value',$(this).attr('placeholder'))
            }
        })
        elem.on('focus',function(){
            if($(this).attr('placeholder')==$(this).attr('value')){
                $(this).val('');
            }
        })
    }

    // 搜索类型下拉框
    $('.searchType').hover(
        function () {
            $(this).addClass("hover");
        },function () {
            $(this).removeClass("hover");
        }
    );
    $('.searchType ul li').click(function() {
        $('.searchType span').html($(this).find('a').html());
        $('#SEARCH_TYPE').val($(this).attr('searchType'))
        $('.searchType').removeClass("hover");
    })

    $('.qq .goTop').click(function() {
        $(window, document).prop("scrollTop", 0)
        $(window, document).scrollTop(0);
    })

    $('.qq .ask').click(function() {
        if (flag) {
            $('.qq .qqWindow').slideUp()
        } else {
            $('.qq .qqWindow').slideDown()
        }
        flag = !flag;
    })
    $('.qq .qqWindow').click(function(e){
        e.stopPropagation();
    })
    $('.floatSearch .closed').click(function(){
        $('.floatSearch').hide();
        // session中记录
        $.post("closeFloatSearch");
    });


    // 头部购物车
    (function(){
        // 重新排列购物车ItemIndex
        function resortMiniCartIndex(){
            $('#mini-cart .shoppingList').each(function(index, element) {
                var item = $(this);
                item.attr('id','mini_cart_item_'+index)
                item.find('.checkbox').val(index);
                item.find('.quantity-text').attr('name','update_'+index);
            });
        }
        // minicart操作
        $('#mini-cart .selectAll').live("click",function(){
            if($(this).attr("checked")){
                // 如果当前是选中状态，点击应该取消上面所有
                $("#mini-cart .checkbox").attr('checked',true);
            }else{
                // 如果当前是未选中状态，点击应该选择上面所有
                $("#mini-cart .checkbox").attr('checked',false);//全选
            }
        })
        // 删除minicart某一项
        $('#mini-cart .promise411 .removeList').live("click",function(){
            var lineIndex = $('#mini-cart .promise411 .removeList').index(this);
            var DELETE_INDEX = "DELETE_"+lineIndex;
            $.post("/control/removeFromCart?"+DELETE_INDEX+"=true",
                function(data){
                    if('success'!=data.responseMessage){
                        alert(data.errorMessage);
                        return;
                    }
                    // 更改购物车数量
                    $('.title .trolley .chartBar .number').html(data.cart.size+'<b></b>');
                    if(data.cart.size==0){
                        $('#mini-cart').html('<p>购物车是空的</p>');
                    }else{
                        // 删除该项
                        $('#mini-cart .shoppingList').eq(lineIndex).remove();
                        // 重新排列购物车ItemIndex
                        resortMiniCartIndex();
                    }
                }, "json"
            );
        })
        // 批量删除
        $('#mini-cart .removeAll').live("click",function(){
            var selectedIndexs = new Array();
            $('#mini-cart .checkbox').each(function(index, element) {
                if($(this).attr("checked")){
                    selectedIndexs.push(index);
                }
            });
            if(selectedIndexs.length<=0){
                alert('至少选择一项');
                return;
            }

            $.post("/control/updateCartItem?"+$('#minicart').serialize()+"&removeSelected=true",
                function(data){
                    if('success'!=data.responseMessage){
                        alert(data.errorMessage);
                        return;
                    }
                    // 更改购物车数量
                    $('.title .trolley .chartBar .number').html(data.cart.size+'<b></b>');
                    if(data.cart.size==0){
                        $('#mini-cart').html('<p>购物车是空的</p>');
                    }else{
                        // 删除选中的项目
                        for (var i=0,len=selectedIndexs.length; i<len; i++) {
                            // 删除该项
                            $('#mini_cart_item_'+selectedIndexs[i]).remove();
                        }
                        // 重新排列购物车ItemIndex
                        resortMiniCartIndex();
                    }
                }, "json"
            );
        })
        // decrement减操作
        $('#mini-cart .decrement').live("click",function(){
            var lineIndex = $('#mini-cart .decrement').index(this);
            var currentQuantity = $('#mini-cart .quantity-text').eq(lineIndex).val();
            if(currentQuantity!=''){
                currentQuantity = parseInt(currentQuantity);
            }
            if(currentQuantity=='' || currentQuantity<=1){
                $('#mini-cart .quantity-text').eq(lineIndex).val(1);
                return;
            }
            var changeToQuantity = currentQuantity-1;
            $('#mini-cart .quantity-text').eq(lineIndex).val(changeToQuantity);
            if(changeToQuantity>1){
                $(this).removeClass('disabled');
            }else{
                $(this).addClass('disabled');
            }
            $.post("/control/updateCartItem?"+$('#minicart').serialize(),
                function(data){
                    if('success'!=data.responseMessage){
                        $('#mini-cart .quantity-text').eq(lineIndex).val(currentQuantity);
                        alert(data.errorMessage);
                        return;
                    }
                    $('.title .trolley .chartBar .number').html(data.cart.size+'<b></b>');
                    $('#mini-cart .shoppingList .price').eq(lineIndex).html(accounting.formatMoney(data.cart.items[lineIndex].displayItemSubTotal));
                }, "json"
            );
        })
        // increment加操作
        $('#mini-cart .increment').live("click",function(){
            var lineIndex = $('#mini-cart .increment').index(this);
            var currentQuantity = $('#mini-cart .quantity-text').eq(lineIndex).val();
            if(currentQuantity!=''){
                currentQuantity = parseInt(currentQuantity);
            }
            if(currentQuantity=='' || currentQuantity<=1){
                $('#mini-cart .quantity-text').eq(lineIndex).val(1);
            }
            var changeToQuantity = currentQuantity+1;
            $('#mini-cart .quantity-text').eq(lineIndex).val(changeToQuantity);
            if(changeToQuantity>1){
                $('#mini-cart .decrement').eq(lineIndex).removeClass('disabled');
            }
            $.post("/control/updateCartItem?"+$('#minicart').serialize(),
                function(data){
                    if('success'!=data.responseMessage){
                        $('#mini-cart .quantity-text').eq(lineIndex).val(currentQuantity);
                        alert(data.errorMessage);
                        return;
                    }
                    $('.title .trolley .chartBar .number').html(data.cart.size+'<b></b>');
                    $('#mini-cart .shoppingList .price').eq(lineIndex).html(accounting.formatMoney(data.cart.items[lineIndex].displayItemSubTotal));
                }, "json"
            );
        })
        // 修改购物车项目数量操作
        $('#mini-cart .quantity-form .quantity-text').blur(function(){
            var lineIndex = $('#mini-cart .quantity-form .quantity-text').index(this);
            var oldQuantity = $('#mini-cart .quantity-hide').eq(lineIndex).val();
            var changeToQuantity = $('#mini-cart .quantity-text').eq(lineIndex).val();
            if(oldQuantity!=''){
                oldQuantity = parseInt(oldQuantity);
            }
            if(changeToQuantity!=''){
                changeToQuantity = parseInt(changeToQuantity);
            }
            if(oldQuantity=='' || oldQuantity<=1){
                oldQuantity = 1;
            }
            if(changeToQuantity=='' || changeToQuantity<=1){
                changeToQuantity = 1;
            }
            if(oldQuantity == changeToQuantity){
                return;
            }
            if(changeToQuantity>1){
                $('#mini-cart .decrement').eq(lineIndex).removeClass('disabled');
            }else{
                $('#mini-cart .decrement').eq(lineIndex).addClass('disabled');
            }
            $.post("updateCartItem?"+$('#minicart').serialize(),
                function(data){
                    if('success'!=data.responseMessage){
                        $('#mini-cart .quantity-text').eq(lineIndex).val(oldQuantity);
                        alert(data.errorMessage);
                        return;
                    }
                    $('.title .trolley .chartBar .number').html(data.cart.size+'<b></b>');
                    $('#mini-cart .quantity-hide').eq(lineIndex).val(changeToQuantity);
                    $('#mini-cart .shoppingList .price').eq(lineIndex).html(accounting.formatMoney(data.cart.items[lineIndex].displayItemSubTotal));
                }, "json"
            );
        })

    })();

});


function IsNum(num){
    var reNum=/^\d*$/;
    return(reNum.test(num));
}

/**
 * 设为首页
 * 加入收藏夹
 * @type
 */
var HomepageFavorite = {
    Homepage: function () {
        if (document.all) {
            document.body.style.behavior = 'url(#default#homepage)';
            document.body.setHomePage(window.location.href);
        }
        else if (window.sidebar) {
            if (window.netscape) {
                try {
                    netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
                } catch (e) {
                    alert("该操作被浏览器拒绝，如果想启用该功能，请在地址栏内输入 about:config,然后将项 signed.applets.codebase_principal_support 值该为true");
                    history.go(-1);
                }
            }
            var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
            prefs.setCharPref('browser.startup.homepage', window.location.href);
        }
    },
    Favorite: function Favorite(weburl, webname) {
        if(document.all){
            window.external.AddFavorite(weburl,webname);
        }else{
            if(window.sidebar){
                window.sidebar.addPanel(webname,weburl,"");
            }else{
                alert("\u5bf9\u4e0d\u8d77\uff0c\u60a8\u7684\u6d4f\u89c8\u5668\u4e0d\u652f\u6301\u6b64\u64cd\u4f5c!\n\u8bf7\u60a8\u4f7f\u7528\u83dc\u5355\u680f\u6216Ctrl+D\u6536\u85cf\u672c\u7ad9\u3002");
            }
        }
    }
}

function ajaxCheckUserLogin(){
    var url = '/control/checkUserLogin';
    jQuery.post(url, function(data){
        if("Y" == data.IS_LOGIN){
            if(typeof(data.trueName) == "undefined" || data.trueName==null || data.trueName == ''){
                $('#showName').prepend(data.userLoginId);
            }else{
                $('#showName').prepend(data.trueName);
            }
            $('.dyn-content').addClass('fn-hide');
            $('#static-userlogin').removeClass('fn-hide');
            if(data.isSupplier){
                $('#static-suppllier').removeClass('fn-hide');
            }
            if(data.isCustomer){
                $('#static-customer').removeClass('fn-hide');
            }
        }else{
            // 原本登录链接就是显示的，无需做什么
        }
        refreshShoppingCart();
    })
    return false;
}
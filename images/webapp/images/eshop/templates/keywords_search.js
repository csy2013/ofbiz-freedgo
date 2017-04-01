define(function (require) {
    var helpers = require('./$helpers');
    var Render = function ($data) {
        'use strict';
        var $helpers = this,
            listSize = $data.listSize,
            i = $data.i,
            productValues = $data.productValues,
            $escape = $helpers.$escape,
            $string = $helpers.$string,
            $formatMoney = $helpers.$formatMoney,
            $out = '';
        $out += ' <tr> <td class="notice-loading-more" colspan="8"> <div class="msg"><span>正在加载中，请稍后~~</span></div> </td> </tr> ';
        if (listSize > 0) {
            $out += ' ';
            for (i = 0; i < productValues.length; i++) {
                $out += ' <tr> <td class="first"> <span class="imgBox"> <a href="product?product_id=';
                $out += $escape($string(productValues[i].productId));
                $out += '" target="_blank"> <img src="';
                $out += $escape($string(productValues[i].smallImageUrl));
                $out += '" width="70" height="70"/> </a> </span> <span class="word"> <p><a href="product?product_id=';
                $out += $escape($string(productValues[i].productId));
                $out += '" target="_blank">';
                $out += $escape($string(productValues[i].productName));
                $out += '</a></p> <p class="series">';
                $out += $escape("xxxx");
                $out += '</p> </span> </td> <td> ';
                $out += $escape("ffff");
                $out += ' </td> <td> ';
                $out += $escape($string(productValues[i].brandName));
                $out += ' </td> <td> ';
                $out += $escape($string(productValues[i].apsisPartUnitName));
                $out += ' </td> <td> ';
                $out += $escape($string(productValues[i].supplierName));
                $out += ' </td> <td> <p class="a">';
                $out += $escape($string($formatMoney(productValues[i].competitivePrice)));
                $out += '</p> <p class="red">';
                $out += $escape($string($formatMoney(productValues[i].defaultPrice)));
                $out += '</p> </td> <td> ';
                $out += $escape($string(productValues[i].availableToPromiseTotal));
                if(productValues[i].availableToPromiseTotal > 0){
                    $out += ' </td> <td> <p class="in"> <a class="u-font-btn add_cart" dataId="';
                }else{
                    $out += ' </td> <td> <p class="in"> <a class="u-font-btn add_cart_disabled" dataId="';
                }
                $out += $escape($string(productValues[i].productId));
                $out += '" href="javascript:;">加入购物车</a> </p> <p class="join"> <a href="javascript:;" class="favorites" data="';
                $out += $escape($string(productValues[i].productId));
                $out += '">[收藏]</a> </p> </td> </tr> ';
            }
            $out += ' ';
        } else {
            $out += ' <tr> <td colspan="8"> <p class="no-data">没有符合条件的商品</p> </td> </tr> ';
        }
        $out += ' ';
        return new String($out)
    };
    Render.prototype = helpers;
    return function (data) {
        return new Render(data) + '';
    }
});
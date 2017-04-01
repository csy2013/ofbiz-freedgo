/*
=======================================================================================================================
@name: 图片浏览插件
@date: 2014-06-16
@author: Spring 
@qq: 1563482488
@desc: 注意HTML结构,代码依赖于jQuery，版本：1.7.2
=======================================================================================================================
*/

;(function($) {
$.fn.imgViewer = function(ops){
    var defaults = $.extend({
            slideSpeed: 300, //切换速度
            visiblesmallImgLiLen: 7,
            prevBtn: '.prev',
            nextBtn: '.next'
        }, ops);

    return $(this).each(function(){
        var that          = $(this), //外部对象
            prevBtn       = that.find(defaults.prevBtn), //上一张按钮
            nextBtn       = that.find(defaults.nextBtn), //下一张按钮
            bigImg        = that.find('.bigImg img'), //大图图片集合
            smallImg      = that.find('.smallImg-m'), //小图的div
            loadHolder    = that.find('.loadPlaceholder'), //加载动画图
            imgTitle      = that.find('.imgViewer-title'), //当前标题
            imgPageCurr   = that.find('.imgViewer-page .curr'), //当前标题
            imgPageCount  = that.find('.imgViewer-page .count'), //当前标题
            imgDesc       = that.find('.imgViewer-desc'), //当前标题
            smallImgW     = smallImg.innerWidth(), //小图的内宽度
            smallImgUl    = smallImg.find('.smallImg-ul'), //小图的ul
            smallImgLi    = smallImgUl.find('li'), //小图的item
            smallImgLiW   = smallImgLi.eq(0).outerWidth(), //第一个小图的外宽度
            smallImgLiMr  = parseInt(smallImgLi.eq(0).css('margin-right')), //第一个小图的外边距
            smallImgLiLen = smallImgLi.length, //小图的个数
            visibleItem   = smallImgLi.find('img'), //小图集合
            visibleImg    = [0,0], //游标的左右两点(索引)
            slideDistance = smallImgLiW + smallImgLiMr, //切换距离 
            onIndex       = 0, //当前小图的索引,初始=0
            maxIndex      = smallImgLiLen - 1, //最后一个小图的索引
            lastIndex     = 0, //最后一次小图的索引
            maxLeft       = -(smallImgLiLen - defaults.visiblesmallImgLiLen)*slideDistance, //最远左边距 负值
            maxVernier    = smallImgLiLen - (smallImgLiLen - defaults.visiblesmallImgLiLen),
            lastLeft      = 0; //上一次的ul的左边距

        //初始化
        smallImgUl.width( smallImgLiLen * slideDistance + 'px' );
        visibleImg[1] = smallImgLiLen > defaults.visiblesmallImgLiLen ? (defaults.visiblesmallImgLiLen - 1) : maxIndex;
        $('<style>.bigImg{position:relative;}.bigImg img{position:absolute;top:0;left:0;}</style>').insertBefore(that);
        imgPageCurr.text('1');
        imgPageCount.text(smallImgLiLen);
        //scrollLeft
        prevBtn.click(function(){
            onIndex -= 1;
            if(onIndex < 0){ onIndex = 0; return false;}
            animation('right');
        });
        //scrollRight
        nextBtn.click(function(){
            onIndex += 1;
            if(onIndex > maxIndex){ onIndex = maxIndex; return false; }
            animation('left');
        });
        //switch Images
        smallImgLi.click(function(){
            onIndex = smallImgLi.index($(this));
            if(onIndex != lastIndex){ 
                judgeSlide(onIndex);
                showBigImg(onIndex);
                lastIndex = onIndex;
            }
        });
        function showBigImg(index){
            loadHolder.fadeIn('fast');

            var src = $.trim(smallImgLi.eq(index).find('.data-img img').attr('src')),
                tit = smallImgLi.eq(index).find('.data-title').text(),
                des = smallImgLi.eq(index).find('.data-desc').html(),
                obt = that.find('.imgViewer-desc'),
                img = that.find('.imgViewer-img .currPic img');

            if(!src.length) return false;

            loadImg(src,function(){
                $('<img src="'+ src +'" style="display:none;" />').insertAfter(img)
                .fadeIn(600,function(){
                    img.remove();
                });
                
                loadHolder.fadeOut('fast');
            });

            imgPageCurr.text(index + 1);
            imgTitle.text(tit);
            imgDesc.html(des);

            smallImgLi.eq(index).addClass('on').siblings('li').removeClass('on');
        }
        function animation(dir){
            showBigImg(onIndex);
            judgeSlide(onIndex);
            lastIndex = onIndex;
        }
        function judgeSlide(onIndex){
            switch(onIndex){
                case visibleImg[0]:
                    if(onIndex <= 0){lastIndex = onIndex;onIndex = 0;showBigImg(onIndex);break;}
                    smallImgUl.animate({'marginLeft':'+='+ slideDistance+'px'},defaults.slideSpeed);
                    visibleImg[0] --; visibleImg[1] --;
                    lastLeft  += slideDistance;
                    break;
                case visibleImg[1]:
                    if(onIndex >= maxIndex){lastIndex = onIndex;onIndex = maxIndex;showBigImg(onIndex);break;}
                    smallImgUl.animate({'marginLeft':'-='+ slideDistance+'px'},defaults.slideSpeed);
                    visibleImg[0] ++; visibleImg[1] ++;
                    lastLeft  -= slideDistance;
                    break;
            }
        }
    });

}

})(jQuery); 
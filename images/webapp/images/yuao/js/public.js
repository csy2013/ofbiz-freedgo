var config = {
    version: 1.0,
    siteWidth: 1210,
    windowW: $(window).width(),
    windowH: $(window).height()
};
$(function() {
    // 当分辨率在1024~1280、1280~1440的时候
    if(config.windowW <= 1460){
		$('html').attr({'class':'screen1280'});
    }
	if(config.windowW <= 1210){
        $('html').attr({'class':'screen1024'});
    }
    if(config.windowW >= 1460){
        $('html').attr({'class':'screen1460'});
    }

    function hasClassFn(){
        if($('html').hasClass('screen1280')){

            $('.fullSlide .bd li').each(function() {
                $(this).css('background','url("'+$(this).attr('data-img-m')+'") 50% 0px no-repeat')
            });

            $('.fullSlide').css('height',$('.fullSlide').attr('height-m')+'px')

        }else if($('html').hasClass('screen1460')){

            $('.fullSlide .bd li').each(function() {
                $(this).css('background','url("'+$(this).attr('data-img-l')+'") 50% 0px no-repeat')
            });

            $('.fullSlide').css('height',$('.fullSlide').attr('height-l')+'px')

        }else if($('html').hasClass('screen1024')){

            $('.fullSlide .bd li').each(function() {
                $(this).css('background','url("'+$(this).attr('data-img-s')+'") 50% 0px no-repeat')
             });

            $('.fullSlide').css('height',$('.fullSlide').attr('height-s')+'px')
        }

    }
  hasClassFn();


    $(window).resize(function(){
        var windowW = $(window).width();
        if(windowW <= 1210){
			$('html').attr({'class':'screen1024'});
        }else if(windowW <= 1460 && windowW >= 1211){
			$('html').attr({'class':'screen1280'});
        }else{
			$('html').attr({'class':'screen1460'});
        }
         
        hasClassFn();

    });

    /*---S sideMenu 左菜单滑动---*/ 
    var j_sideMenu = $('#j_sideMenu'),
        sideMenuNavItems = j_sideMenu.find('li'),
        startItem = j_sideMenu.find('.on'),
        startTop = sideMenuNavItems.index(startItem) * 89,
        j_sideMenuNav_on = $('#j_sideMenu_on');
    j_sideMenuNav_on.css('top',startTop);
    sideMenuNavItems.hover(function(){
      var that = $(this),
          index = that.index();
      that.addClass('hover').siblings('li').removeClass('hover');
      j_sideMenuNav_on.stop().animate({'top':index*89},300);
    },function(){
        $(this).removeClass('hover');
    });
    j_sideMenu.bind('mouseout',function(){
        startItem.addClass('hover');
        j_sideMenuNav_on.stop().animate({'top':startTop},300);
    });
    /*---E sideMenu 左菜单滑动---*/

    /*---S toTop 返回顶部---*/
    var toTop = $('.toTop');
    toTop.toTop({           
        showHeight: 0,
        speed: 500,
        button: '.btn-toTop'
    });
    var j_a_scanLife = toTop.find('.toTop-scanLife'),
        j_d_scanLife = $('#j_scanLife');
    j_a_scanLife.click(function(){
        j_d_scanLife.toggle('fast');
        return false;
    });
    /*---E toTop 返回顶部---*/

     /*---S friendlinks 友情链接---*/
    // var btn_show_Fris = $('#btn_show_Fris');
    // var j_hidden_friendlinks = $('#j_hidden_friendlinks');
    // btn_show_Fris.bind('click',function(){
    //     j_hidden_friendlinks.slideToggle();
    // });
    /*---E friendlinks 友情链接---*/

    // 导航菜单 --> 核心产品

    var Offer_Right=$(window).width()-($('.nav').offset().left+$('.nav').outerWidth());
      $('.J_NavCode').width($(window).width());
      $('.J_NavCode').css({'right':-Offer_Right+'px'});

      $(window).resize(function(){
        var Offer_Right=$(window).width()-($('.nav').offset().left+$('.nav').outerWidth());
        $('.J_NavCode').width($(window).width());
        $('.J_NavCode').css({'right':-Offer_Right+'px'})
    });

    //导航菜单 --> 下拉展开
    (function(){
        var menus = $('.menu > li');
        var hoverTimer, outTimer; 

        function hoverOut(){
            $('.J_NavCode').parents('dt').siblings().children('.dd').stop(false,true).hide(); 
            $('.J_NavCode').stop(false,true).slideUp(400).css('z-index','99'); 
            $('.J_NavCode').parents('li').removeClass('on')
        }
      menus.hover(function(){

            clearTimeout(outTimer);       
      
                 if($('.J_NavCode').is(':visible')){
 
                    $(this).children('.dd').stop(false,true).show().css('z-index','100');
                }else{
                    $(this).children('.dd').stop(false,true).slideDown(400).css('z-index','100');
                }
                

                $(this).addClass('on').siblings().removeClass('on');
                // $(this).children('.dd').stop(false,true).slideDown(400).css('z-index','100');
           
            
        },function () {
              
                $(this).children('.dd').css('z-index','99');
                $(this).siblings().children('.dd').stop(false,true).hide();
                outTimer = setTimeout(hoverOut, 500);
            

             // $(this).siblings().children('.dd').stop(false,true).hide()
             // $(this).children('.dd').stop(false,true).slideUp(400).css('z-index','99');
            // setTimeout("alert(111)",2000)
            // $(this).siblings().children('.dd').stop(false,true).hide()
            // $(this).children('.dd').stop(false,true).slideUp(400).css('z-index','99');
           
        });

    })();

    $('.J_Core_ArrowUp').bind('click', function(event){
         
         if (!$('.J_NavCode').is(':animated')){

            $('.J_NavCode').slideUp(400);
             
         }

        
    });

    //导航菜单 --> 核心产品 --> 切换
    $('.coremenu').taber({
        tabNav: '.coremenu-tabNav', //选项卡头部列表项class
        target: 'data-id', //绑定的目标id
        tabCon: '.tabCon',//选项卡主体列表项class
        onEvent: 'mouseover'
    });

    // 我要留言
    (function(){
        var btn_show = $('.btn-leaveMessage'),
            btn_send = $('.form-leaveMessage .btn-send'),
            btn_cancel = $('.form-leaveMessage .btn-cancel'),
            elem = btn_show.parent().siblings('.form');
            
        setIEPlaceholder();
//  btn_show.add(btn_send).add(btn_cancel).on('click', function(event){  去除提交时，自动收起表单  2016-09-09
        btn_show.add(btn_cancel).on('click', function(event){
            elem.slideToggle();
            btn_show.toggle();
            
        });
    })();

});


/*----------functions-------------*/
//设置IE8 placeholder属性
function setIEPlaceholder(){
  //判断浏览器是否支持placeholder属性
  var supportPlaceholder='placeholder'in document.createElement('input'),
 
  placeholder=function(input){
 
    var text = input.attr('placeholder'),
    defaultValue = input.val();
 
    if(text!==''){
 
      input.val(text).addClass("phcolor");

      input.focus(function(){
 
          if(input.val() == text){
       
            $(this).val("");
          }
        });
     
      
        input.blur(function(){
     
          if(input.val() == ""){
           
            $(this).val(text).addClass("phcolor");
          }
        });
     
        //输入的字符不为灰色
        input.keydown(function(){
      
          $(this).removeClass("phcolor");
        });
    }else{
        input.val(defaultValue)
    }
 
  };
 
  //当浏览器不支持placeholder属性时，调用placeholder函数
  if(!supportPlaceholder){
 
    $('input').each(function(){
 
      if($(this).attr("type") == "text"){
 
        placeholder($(this));
      }
    });

    $('textarea').each(function(){
        placeholder($(this));
    });
  }

}
//取得事件对象
function getEvent(e) {
    return e || window.event
}
//取得事件目标对象
function getTarget(e) {
    var e=e||window.event;
    return e.target||e.srcElement;
}
/*加入收藏 AddFavorite(window.location,document.title);*/
function AddFavorite(sURL, sTitle) {
    sURL = encodeURI(sURL); 
    try{   
        window.external.addFavorite(sURL, sTitle);   
    }catch(e) {   
        try{   
            window.sidebar.addPanel(sTitle, sURL, "");   
        }catch (e) {   
            alert("加入收藏失败，请使用Ctrl+D进行添加,或手动在浏览器里进行设置.");
        }   
    }
}
/*设为首页 SetHome(window.location);*/
function SetHome(url){
    if (document.all) {
        document.body.style.behavior='url(#default#homepage)';
        document.body.setHomePage(url);
    }else{
        alert("您好,您的浏览器不支持自动设置页面为首页功能,请您手动在浏览器里设置该页面为首页!");
    }
}
/*选项卡切换函数*/
function taber(nav,con){
    $(nav).addClass('on').siblings('li').removeClass('on');
    $(con).show().siblings('div').hide();
}
/*展开列表函数*/
function toggleList(con,style){
  var con = $(con),
      sty = style;

  if(sty == 'slide'){
    con.slideToggle('fast');
  }else{
    con.toggle();
  }
}
/*
name：显示对象，打开对象
desc：参数为id号，数量无限，例如：show('id1','id2'...)
*/
function show(){
    var arg = arguments;
    for(var i in arg){
        var one = typeof(arg[i]) === 'string'?document.getElementById(arg[i]):arg[i];
        one.style.display = 'block';
    }
}
/*
name：隐藏对象，关闭对象
desc：参数为id号，数量无限，例如：hide('id1','id2'...)
*/
function hide(){
    var arg = arguments;
    for(var i in arg){
        var one = typeof(arg[i]) === 'string'?document.getElementById(arg[i]):arg[i];
        one.style.display = 'none';
    }
}
/*
name：切换对象
desc：参数为id号，数量无限，例如：toggle('id1','id2'...)
*/
function toggle(){
    var arg = arguments;
    for(var i in arg){
        var one = typeof(arg[i]) === 'string'?document.getElementById(arg[i]):arg[i];
        one.style.display == 'block' ? one.style.display = 'none' : one.style.display = 'block';
    }
}
/*
name：加载图片
desc：url(目标url),callback(图片加载完成执行的函数)
*/
function loadImg(url,callback){
  var url = url,
      img = new Image();

  img.onload = function(){
    if(typeof(callback) == 'function') {callback.call(img);}
    img.onload = null;
  };
  img.src = url;
}


/*S------------JQuery微插件---------------*/
(function($) {  
$.fn.extend({ 
    /*
    ===================================================================================
    name：淡显幻灯片(banner)
    make: xiaohe (qq)1563482488
    date：2013.03.15
    desc：注意html结构
    ===================================================================================
    */ 
    banner: function(ops) {
        var defaults = $.extend({
            speed: 600,    // 当前的索引
            time: 6000     // 切换间隔
        }, ops);

        return this.each(function() {
            var _this = $(this),
                _imgs = _this.find('.currPic a'),
                _img0 = _this.find('.currPic a:first'),
                _ctrl = _this.find('.currTitle li'),
                _indx = 0;
            //初始化
            _img0.show().siblings('a').hide();
            //自动开始
            var timer = setInterval(IntervalShow, defaults.time);
            //滑过控制按钮
            _ctrl.mouseover(function() {
                _indx = _ctrl.index(this);
                myShow(_indx);
            });
            //滑入停止动画，滑出开始动画
            _this.hover(function() {
                if (timer) {
                    clearInterval(timer);
                    timer = null;
                }
            },function() {
                timer = setInterval(IntervalShow, defaults.time);
            });
            //切换函数
            function myShow(i) {
                _ctrl.eq(i).addClass("on").siblings("li").removeClass("on");
                _imgs.eq(i).stop(true, true).fadeIn(defaults.speed).siblings("a").fadeOut(defaults.speed);
            }
            //定时切换
            function IntervalShow() {
                myShow(_indx);
                _indx++;
                if (_indx == 6) { _indx = 0; }
            }
        });
    },
    /*
    ===================================================================================
    name：选项卡切换(不带延迟加载图片)
    make: xiaohe (qq)1563482488
    date：2013.11.01
    desc：html结构
    ===================================================================================
    */
    taber: function(ops) {
        var defaults = {           
            tabNavs: '.tabNavs', //选项卡头部
            tabNav: '.tabNav', //选项卡头部列表项class
            target: 'data-id', //绑定的目标id
            tabCon: '.tabCon',//选项卡主体列表项class
            onEvent: 'click', //出发事件，mouseover/click
        };
        var options = $.extend(defaults,ops);

        return this.each(function(){
            var _this = $(this),
                event = options.onEvent == 'mouseover'?'mouseover':'click';
            _this.find(options.tabNav).bind(event,function(){
                var that = $('#'+ $(this).attr(options.target));
                $(this).addClass('on').siblings(options.tabNav).removeClass('on');   // 切换tab栏选中状态
                that.addClass('on').siblings(options.tabCon).removeClass('on');   // 切换内容
             });
        });
    },
    /*
    ===================================================================================
    name：点击返回顶部
    make: xiaohe (qq)1563482488
    date：2013.11.01
    desc：暂只支持div下ul li的滚动
    ===================================================================================
    */
    toTop: function(ops) {
        var defaults = {           
            showHeight: 150,
            speed: 1000,
            button: 'btn-toTop'
        };
        var options = $.extend(defaults,ops);

        var _this = $(this);
        $(window).scroll(function(){
            var scrolltop = $(this).scrollTop();     
            if(scrolltop >= options.showHeight){
                _this.fadeIn('fast');
            }else{_this.hide();}
        });
        _this.find(defaults.button).click(function(){
            $("html,body").animate({scrollTop: 0}, options.speed); 
        });
    },
    hSlider: function(ops) {
        var config=$.extend({
            slideSpeed:200,//滑动速度
            intervalTime:5000,//滑动间隔时间
            autoSlide:true,//是否为自动滑动
            slideStep:1,
            visibleItem: 4,
            slideDirection:'left',
            prevBtn:'',//控制按钮prev,例如:$('#prevBtn');
            nextBtn:''//控制按钮next,例如:$('#nextBtn');
        }, ops);

        return this.each(function(){
            var outer = $(this),
                inner = outer.find('.sliderInner'),
                item  = inner.find('.item'),
                prevBtn = outer.find(config.prevBtn),
                nextBtn = outer.find(config.nextBtn);

            if(item.length <= config.visibleItem){
                prevBtn.remove();
                nextBtn.remove();
                // return false;
            }else{
                
                var intervalSlide,
                    intervalSlideStatic = false,
                    stepWidth = parseInt(item.eq(0).outerWidth())+parseInt(item.eq(0).css('margin-left'))+parseInt(item.eq(0).css('margin-right')),
                    innerWidth = item.length*stepWidth,
                    liCloneL = item.clone(),
                    liCloneR = liCloneL.clone();

                if(config.autoSlide) _startInterval();
                inner.append(liCloneL).prepend(liCloneR).css({'width':3*innerWidth+'px','margin-left':'-'+innerWidth+'px'}); 

                prevBtn.click(function(){
                    intervalSlideStatic=false;
                    _stopInterval();
                    _doScroll('right',1,_startInterval);
                });
                nextBtn.click(function(){
                    intervalSlideStatic=false;
                    _stopInterval();
                    _doScroll('left',1,_startInterval);
                }); 
            }

            function _doScroll(direction,step,callback){
                var dir = direction,
                    step = step,
                    callback = callback;

                circulation = dir == 'right' ? ':right' : ':left';
                dir = dir == 'right' ? '+='+ stepWidth : '-='+stepWidth;
                step = step ? step : config.slideStep;
                inner.animate({'marginLeft':dir+'px'},config.slideSpeed,function(){
                    if (circulation === ':right') {
                        inner.find('.item:last').prependTo(inner);
                        inner.css('margin-left','-='+ stepWidth);
                    }else{
                        inner.find('.item:first').appendTo(inner);
                        inner.css('margin-left','+='+ stepWidth);
                    }
                  if(config.autoSlide && callback){callback();}
                });
            }
            function _stopInterval(){
                clearInterval(intervalSlide);
            }
            function _startInterval(){
              if (!intervalSlideStatic) {
                intervalSlide = setInterval(_doScroll, config.intervalTime);
                intervalSlideStatic = true;
              }
            }
        });
    },
    /*
    ===================================================================================
    name：鼠标hover延迟执行
    make: xiaohe (qq)1563482488
    date：2014.07.01
    desc：实例：$("#test").hoverDelay({hoverEvent: function(){alert("经过我！");}});
    ===================================================================================
    */ 
    hoverDelay: function(ops){        
        var defaults = {
            hoverDuring: 200,   //鼠标经过的延时时间         
            outDuring: 200,     //鼠标移开的延时时间        
            hoverEvent: function(){
                $.noop();       //鼠标经过执行的方法          
            },            
            outEvent: function(){
                $.noop();       //鼠标移开执行的方法           
            }        
        };        
        var sets = $.extend(defaults,ops || {}); 

        var hoverTimer, outTimer;        
        return $(this).each(function(){
            $(this).hover(function(){
                clearTimeout(outTimer);                
                hoverTimer = setTimeout(sets.hoverEvent, sets.hoverDuring);            
            },function(){
                clearTimeout(hoverTimer);
                outTimer = setTimeout(sets.outEvent, sets.outDuring);
            });
        });
    },
    /*
    ===================================================================================
    name：固定元素（多用于详情页面的tab）
    make: xiaohe (qq)1563482488
    date：2014.04.01
    desc：html结构
    ===================================================================================
    */ 
    fixeder: function(ops) {
        var defaults = $.extend({
            data: 'data-target', //捆绑的对象
            onClass: 'fixed' // 被执行的对象
        }, ops);

        return this.each(function () {
            var _this = $(this),
                target = $('#' +_this.attr(defaults.data)),
                top = _this.offset().top;
            $(window).scroll(function(){
                var lt = $(this).scrollTop() - top;
                if (lt >= 0) {_this.addClass(defaults.onClass);target.show();}
                else {_this.removeClass(defaults.onClass);target.hide();}
            });
        });
    },
    /*
    ===================================================================================
    name：jQuery滑动锚点（多用于详情页面的tab）
    make: xiaohe (qq)1563482488
    date：2014.07.23
    desc：html结构
    ===================================================================================
    */ 
    anchor: function(ops) {
        var defaults = $.extend({
            item: '.tabNav', //描点集合
            data: 'data-anchorID', //捆绑的对象
            adjust: 0, // 调整上下差距
            time: 800  // 滚动时间
        }, ops);

        return this.each(function () {
            var parent = $(this),
                item = parent.find(defaults.item);
            item.click(function(){
                var that = $(this),
                    target = $('#' + that.attr(defaults.data)),
                    tOff = target.offset(),
                    tTop = tOff.top;
                var top = tTop + defaults.adjust;
                $('html,body').animate({'scrollTop':top},defaults.time,function(){
                    that.addClass('on').siblings(defaults.item).removeClass('on');
                });
            });
        });
    }

});    
})(jQuery); 
/*E---JQuery插件---*/

<!--kennen add 2016-04-06 start-->

//首页核心产品滚动
function indexImportantPro(id,speed,time){
	if($(id).size()<0){return;}
	var pageSize=Math.ceil($(id+' #sliderInner .item').size()/3);
	var interval;
	var curIndex=0;
	for(var i=0;i<pageSize;i++){
		if(i==0){
			$(id+' .sliderDot').html($(id+' .sliderDot').html()+'<a class="on">&nbsp;</a>');
		}else{
			$(id+' .sliderDot').html($(id+' .sliderDot').html()+'<a>&nbsp;</a>');
		}
	}
	$(id+' .sliderDot a').click(function(){
		curIndex=$(this).index();
		play(curIndex);
	});


    $('.J_P_Next').click(function(){
          
        var  p_index=$(id+' .sliderDot a.on').index()+1;

        if(p_index>=3){
            return false;
        } 

       $(id+' .sliderDot a').eq(p_index).addClass('on').siblings().removeClass('on');

        $(id+' #sliderInner').animate({'margin-left':-($(id+' .scroller').outerWidth()+40)*p_index},speed);

    });

    $('.J_P_Prev').click(function(){
          
        var  p_index=$(id+' .sliderDot a.on').index()-1;


        if(p_index<0){
            return false;
        } 

       $(id+' .sliderDot a').eq(p_index).addClass('on').siblings().removeClass('on');

        $(id+' #sliderInner').animate({'margin-left':-($(id+' .scroller').outerWidth()+40)*p_index},speed);

    });
	function play(index){
		$(id+' .sliderDot a').removeClass('on');
		$(id+' .sliderDot a').eq(index).addClass('on');
		$(id+' #sliderInner').animate({'margin-left':-($(id+' .scroller').width()+40)*index},speed,'easeInOutCubic');
	}	
	//初始化
	interval=setInterval(interlPlay,time);
	function interlPlay(){
		if(curIndex<pageSize-1){
			curIndex=curIndex+1;
		}else{
			curIndex=0;
		}
		play(curIndex);
	}
	$(id).mouseenter(function(){
		clearInterval(interval);
	}).mouseleave(function(){
		interval=setInterval(interlPlay,time);
	});
}

// kennen add 2016-04-06 end


































/**
 * Created by Raby on 2015/10/26.
 */
$.fn.wheel = function (callback) {
  return this.each(function () {
    $(this).on('mousewheel DOMMouseScroll', function (e) {
      e.delta = null;
      if (e.originalEvent) {
        if (e.originalEvent.wheelDelta) e.delta = e.originalEvent.wheelDelta / -40;
        if (e.originalEvent.deltaY) e.delta = e.originalEvent.deltaY;
        if (e.originalEvent.detail) e.delta = e.originalEvent.detail;
      }

      if (typeof callback == 'function') {
        callback.call(this, e);
      }
    });
  });
};

$('.menu').wheel(function (e) {
  e.preventDefault();
  var xHeight = $('.sidebar').height() - $('.menu').height();
  if(e.delta > 0){
    if(parseInt($(this).css('top'))<= e.delta && parseInt($(this).css('top'))>=xHeight){
      var theTop = parseInt($(this).css('top')) - e.delta;
      $(this).css({
        top: theTop
      });
    }
  }
  else{
    if(parseInt($(this).css('top'))<= e.delta && parseInt($(this).css('top'))>=xHeight + e.delta){
      var theTop = parseInt($(this).css('top')) - e.delta;
      $(this).css({
        top: theTop
      });
    }
  }

});
$('.sub_menu_in').wheel(function (e) {
  e.preventDefault();
  var xHeight = $('.sub_menu').height() - $('.sub_menu_in').height();
  if(e.delta > 0){
    if(parseInt($(this).css('top'))<= e.delta && parseInt($(this).css('top'))>=xHeight){
      var theTop = parseInt($(this).css('top')) - e.delta;
      $(this).css({
        top: theTop
      });
    }
  }
  else{
    if(parseInt($(this).css('top'))<= e.delta && parseInt($(this).css('top'))>=xHeight + e.delta){
      var theTop = parseInt($(this).css('top')) - e.delta;
      $(this).css({
        top: theTop
      });
    }
  }

});

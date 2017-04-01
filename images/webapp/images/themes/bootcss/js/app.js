// Custom scripts

//Define datatable default
$(function () {


    $('.jDatatable').dataTable({
        "paginate": false,
        "bInfo": false,
        // responsive: true,
        //"dom": 'T<"clear">lfrtip',
        //"tableTools": {
        //    "sSwfPath": "/images/jquery/plugins/dataTables/swf/copy_csv_xls_pdf.swf"
    });

    $('.jDatatable').removeClass('basic-table').addClass('table').addClass('table-striped').addClass('table-bordered')
        .addClass('table-hover');

    $('.jBootstable').removeClass('basic-table').addClass('table');
});


$(document).ready(function () {

    // MetsiMenu
    $('#side-menu').metisMenu();

    // Collapse ibox function
    $('.collapse-link').click(function () {
        var ibox = $(this).closest('div.ibox');
        var button = $(this).find('i');
        var content = ibox.find('div.ibox-content');
        content.slideToggle(200);
        button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
        ibox.toggleClass('').toggleClass('border-bottom');
        setTimeout(function () {
            ibox.resize();
            ibox.find('[id^=map-]').resize();
        }, 50);
    });

    //Collapse Screenlet function
    $('.screenlet-title-bar').find('li.expanded a,li.collapsed a').click(function (e) {
        e.preventDefault();
        var ibox = $(this).closest('div.screenlet');
        var button = $(this).parent();
        var content = ibox.find('div.screenlet-body');
        content.slideToggle(200);
        button.toggleClass('collapsed').toggleClass('expanded');
        ibox.toggleClass('').toggleClass('border-bottom');
        setTimeout(function () {
            ibox.resize();
            ibox.find('[id^=map-]').resize();
        }, 50);
    });

    // Close ibox function
    $('.close-link').click(function () {
        var content = $(this).closest('div.ibox');
        content.remove();
    });

    // Small todo handler
    $('.check-link').click(function () {
        var button = $(this).find('i');
        var label = $(this).next('span');
        button.toggleClass('fa-check-square').toggleClass('fa-square-o');
        label.toggleClass('todo-completed');
        return false;
    });

     //Append config box / Only for demo purpose
    $.get("/images/themes/bootcss/skin-config.html", function (data) {
        $('body').append(data);
    });

    // minimalize menu
    $('.navbar-minimalize').click(function () {
        //通过设置服务器session bodystyle
        var t = 'default';
        $("body").toggleClass("mini-navbar");
        if ($("body").hasClass('mini-navbar')) {
            t = 'Y'
        } else {
            t = "N"
        }
        var pathName = (window.location.pathname);
        var index = pathName.substr(1).indexOf("/");
        var result = pathName.substr(0,index+1);
        var http  =window.location.protocol+"//";
        var host = window.location.host;
        //contextPath.substr
        $.get(http+host+result+"/control/setBodyStyle?mini=" + t, function (data) {
            console.log(data)
        });
        SmoothlyMenu();
    });




    // tooltips
    $('.tooltip-demo').tooltip({
        selector: "[data-toggle=tooltip]",
        container: "body"
    });

    // Move modal to body
    // Fix Bootstrap backdrop issu with animation.css
    $('.modal').appendTo("body");

    // Full height of sidebar
    function fix_height() {
        var heightWithoutNavbar = $("body > #wrapper").height() - 61;
        $(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");
    }

    fix_height();

    // Fixed Sidebar
    // unComment this only whe you have a fixed-sidebar
    //    $(window).bind("load", function() {
    //        if($("body").hasClass('fixed-sidebar')) {
    //            $('.sidebar-collapse').slimScroll({
    //                height: 'auto',
    //                railOpacity: 0.9,
    //            });
    //        }
    //    })

    $(window).bind("load resize click scroll", function () {
        if (!$("body").hasClass('body-small')) {
            fix_height();
        }
    });

    $("[data-toggle=popover]")
        .popover();
});


// For demo purpose - animation css script
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function () {
            element.addClass('animated ' + animation);
        },
        function () {
            //wait for animation to finish before removing classes
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}

// Minimalize menu when screen is less than 768px
$(function () {
    $(window).bind("load resize", function () {
        if ($(this).width() < 769) {
            $('body').addClass('body-small')
        } else {
            $('body').removeClass('body-small')
        }
    })
});

function SmoothlyMenu() {
    if (!$('body').hasClass('mini-navbar') || $('body').hasClass('body-small')) {
        // Hide menu in order to smoothly turn on when maximize menu
        $('#side-menu').hide();
        // For smoothly turn on menu
        setTimeout(
            function () {
                $('#side-menu').fadeIn(500);
            }, 100);
    } else if ($('body').hasClass('fixed-sidebar')) {
        $('#side-menu').hide();
        setTimeout(
            function () {
                $('#side-menu').fadeIn(500);
            }, 300);
    } else {
        // Remove all inline style from jquery fadeIn function to reset menu state
        $('#side-menu').removeAttr('style');
    }
}

// Dragable panels
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable(
        {
            handle: handle,
            connectWith: connect,
            tolerance: 'pointer',
            forcePlaceholderSize: true,
            opacity: 0.8,
        })
        .disableSelection();
}





// 提示弹窗
function tipLayer(option) {
    var defaultOption = option || {msg: ''},
        message = defaultOption.msg || '';
    target = option.target;
    if (!$('#tipLayer').size()) {
        var tipLayerContent = '<div id="tipLayer" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">'
            + '<div class="modal-dialog modal-sm">'
            + '<div class="modal-content">'
            + '<div class="modal-header">'
            + '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>'
            + '<h4 class="modal-title" id="mySmallModalLabel">提示</h4></div>'
            + '<div class="modal-body tipLayerMsg"></div>'
            + '</div></div></div>';

        /*if ($(target).parents(".box")) {
         if ($(target).parents(".box").children('.box-body')) {
         $(target).parents(".box").children('.box-body').append(tipLayerContent);
         }
         }else if($(target).parents(".ibox")) {
         if ($(target).parents(".ibox").children('.ibox-content')) {
         $(target).parents(".ibox").children('.ibox-content').append(tipLayerContent);
         }
         }
         else if ($(target).parents(".panel").children('.panel-body')) {
         $(target).parents(".panel").children('.panel-body').append(tipLayerContent);
         } else {
         $('body').append(tipLayerContent);
         }*/
        $('body').append(tipLayerContent);
    }
    $('#tipLayer').modal('show').find('.tipLayerMsg').html(message);
}
// 确认弹窗
function confirmLayer(option) {
    var defaultOption = option || {msg: '', title: ''},
        msg = defaultOption.msg || '';
    title = defaultOption.title || '';
    target = option.target;
    if (!$('#confirmLayer').size()) {
        var confirmLayerContent = '<div id="confirmLayer" name="confirmLayer" class="modal fade">'
            + '<div class="modal-dialog">'
            + '<div class="modal-content">'
            + '<div class="modal-header">'
            + '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
            + '<h4 class="modal-title confirmLayerTitle">确认</h4></div>'
            + '<div class="modal-body confirmLayerMsg"></div>'
            + '<div class="modal-footer">'
            + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
            + '<button type="button" class="btn btn-primary confirm-btn">确认</button></div></div></div></div>';
        $(target).after(confirmLayerContent);
        /*if ($(target).parents(".box")) {
         if ($(target).parents(".box").children('.box-body')) {
         $(target).parents(".box").children('.box-body').append(confirmLayerContent);
         }
         }else if($(target).parents(".ibox")) {
         if ($(target).parents(".ibox").children('.ibox-content')) {
         $(target).parents(".ibox").children('.ibox-content').append(confirmLayerContent);
         }
         }
         else if ($(target).parents(".panel").children('.panel-body')) {
         $(target).parents(".panel").children('.panel-body').append(confirmLayerContent);
         } else {
         $('body').append(confirmLayerContent);
         }*/


        /* if ($(target).parents(".panel").children('.panel-body')) {
         $(target).parents(".panel").children('.panel-body').append(confirmLayerContent);
         } else {
         $('body').append(confirmLayerContent);
         }*/
        $('body').append(confirmLayerContent);
    }

    $('#confirmLayer').find('.confirmLayerTitle').html(title);
    $('#confirmLayer').modal('show').find('.confirmLayerMsg').html(msg);

    if (typeof defaultOption.confirm === 'function') {
        var obj = $('.confirm-btn', '#confirmLayer');
        $(obj).click(option.confirm);
    }
}

function modalLayer(option) {
    var defaultOption = option || {title: '', msg: '', bodyUrl: '', queryArgs: ''},
        msg = defaultOption.msg || '';
    title = defaultOption.title || '';
    bodyUrl = defaultOption.bodyUrl || '';
    queryArgs = defaultOption.queryArgs || '';
    target = option.target;
    modalType = option.modalType;
    modalStyle = option.modalStyle;
    var modalLayerContent = '<div id="modalLayer" class="modal inmodal fade" name="modalLayer">'
        + '<div class="modal-dialog ' + modalStyle + '">'
        + '<div class="modal-content animated">'
        + '<div class="modal-header">'
        + '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
        + '<h4 class="modal-title modalLayerTitle">确认</h4></div>'
        + '<div class="modal-body"></div>'
        + '<div class="modal-footer">';

    if (modalType === 'form-submit') {
        modalLayerContent += '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
            + '<button type="button" id="submitBtn" class="btn btn-primary modal-btn">确认</button></div></div></div></div>';
    } else if (modalType === "view") {
        modalLayerContent += '<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>';
    }
    //$(target).after(modalLayerContent);
    /* if ($(target).parents(".panel").children('.panel-body')) {
     $(target).parents(".panel").children('.panel-body').find('#modalLayer').remove().end().append(modalLayerContent);
     } else {
     $('body').find('#modalLayer').remove().end().append(modalLayerContent);
     }*/

    /*if ($(target).parents(".box")) {
     if ($(target).parents(".box").children('.box-body')) {
     $(target).parents(".box").children('.box-body').append(modalLayerContent);
     }
     }else if($(target).parents(".ibox")) {
     if ($(target).parents(".ibox").children('.ibox-content')) {
     $(target).parents(".ibox").children('.ibox-content').append(modalLayerContent);
     }
     }
     else if ($(target).parents(".panel").children('.panel-body')) {
     $(target).parents(".panel").children('.panel-body').append(modalLayerContent);
     } else {
     $('body').append(modalLayerContent);
     }*/
    $('body').append(modalLayerContent);

    $('#modalLayer').find('.modalLayerTitle').text(title);
    if (bodyUrl !== '') {
        $.ajax({
            type: "post",
            url: bodyUrl,
            data: queryArgs,
            timeout: AJAX_REQUEST_TIMEOUT,
            cache: false,
            dataFilter: function (data, dataType) {
                waitSpinnerHide();
                return data;
            },
            success: function (data) {
                $('#modalLayer').modal('show').find('.modal-body').html(data);
                //bootstrap modal-btn 增加form 提交disabled事件
                if ($('#modalLayer form')) {
                    var modalForm = $('#modalLayer form');
                    //取消form 提交 onsubmit 事件
                    $(modalForm).unbind("onsubmit");
                    //删除from中的submit元素
                    if ((modalType === 'form-submit')) {
                        modalRemoveSubmit(modalForm);
                    }
                }
                if (typeof defaultOption.confirm === 'function' && modalType === 'form-submit') {
                    var obj = $('#submitBtn').on('click', option.confirm);
                }

            },
            error: function (xhr, reason, exception) {
                if (exception != 'abort') {
                    alert("An error occurred while communicating with the server:\n\n\nreason=" + reason + "\n\nexception=" + exception);
                }
                location.reload(true);
            }
        });
    }
    $('#modalLayer').on('shown.bs.modal', function () {
        /*var form = $("#modalLayer form:first");
         if ($(form).attr('data-parsley-validate')) {
         $(form).parsley().on("form:success", function () {
         doSubmit(form, target);
         })
         }*/
    });

    $('#modalLayer').on('hide.bs.modal', function () {
        if ($('#modalLayer')) {
            $('#modalLayer').remove();
        }
    });

    $("#modalLayer").on("hidden.bs.modal", function () {
        if (modalType === 'form-submit') {
            var form = $("#modalLayer form:first");
            if ($(form).attr('data-parsley-validate')) {
                $(form).parsley().off("form:success")
            }
            $(this).removeData();
            $('#submitBtn').off('click');
        }
    });
}

function lookupModalLayer(option) {
    var defaultOption = option || {title: '', msg: '', id: ''},
        msg = defaultOption.msg || '';
    title = defaultOption.title || '';
    id = defaultOption.id || 'lookupModal';
    target = option.target;
    if (!$('#' + id).size()) {
        var modalLayerContent = '<div id=' + id + ' class="modal fade" name="lookupModal">'
            + '<div class="modal-dialog">'
            + '<div class="modal-content">'
            + '<div class="modal-header">'
            + '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
            + '<h4 class="modal-title modalLayerTitle">查找页面</h4></div>'
            + '<div class="modal-body"></div>'
            + '<div class="modal-footer">'
            + '</div></div></div></div>';
        //if($(target)) {
        //    $(target).append(modalLayerContent);
        //}else{
        //$(target).append(modalLayerContent);
        $('body').append(modalLayerContent);
        //}
        $('#lookupModal').find('.modalLayerTitle').text(title);
    }
}
// 获取IE版本，若非IE则返回0
function getIeVersion() {
    var agent = navigator.userAgent.toLowerCase(),
        regStr_ie = /msie [\d.]+;/gi,
        browser = '';
    //IE
    if (agent.indexOf("msie") > 0) browser = agent.match(regStr_ie);

    return +(browser).replace(/[^0-9.]/ig, '');
}

// ICO 全局事件
;(function($,window,document,undefined){
    // 表单校验
    $.fn.dpValidate = function(option) {
        var errorNum = 0;

        if (option.validate) {
            $(this).on('submit',function(){
                errorNum = 0;

                $(this).find('.form-group').each(function(){
                    var type = $(this).data('type'),
                        mark = $(this).data('mark') || '',
                        msg = $(this).data('msg') || '',
                        relation = $(this).data('relation'),
                        isRelation;

                    if (relation) isRelation = relationValidata(relation);

                    if (type && !isRelation) {
                        if (type.indexOf(',')>-1) {
                            var typeArray = type.split(',');

                            for (var i=0; i < typeArray.length; i++) {
                                validateType($(this),typeArray[i],mark,msg);
                            }
                        } else {
                            validateType($(this),type,mark,msg);
                        }
                    }
                });

                if (option.console) console.log('共有'+errorNum+'个错误');
                if (!errorNum && typeof option.callback === 'function') option.callback();
                return false;
            });
        }

        if (option.clear) $(this).find('.form-group').removeClass('has-error').find('.dp-error-msg').empty();

        function validateType ($el,type,mark,msg) {
            var $error = $el.find('.dp-error-msg'),
                errorTxt = $error.text();

            switch (type) {
                case 'required':
                {
                    var value = $el.find('.dp-vd').val();

                    if (!value) {
                        errorNum++;
                        $el.addClass('has-error');
                        if (msg) $error.text(errorTxt+msg);
                        else $error.text(errorTxt+mark+'不能为空');
                    }
                }
                    break;

                case 'min':
                {
                    var value = $el.find('.dp-vd').val(),
                        number = +$el.data('number');

                    if (value.length < number) {
                        errorNum++;
                        $el.addClass('has-error');
                        if (msg) $error.text(errorTxt+msg);
                        else $error.text(errorTxt+mark+'不能少于'+number+'位');
                    }
                }
                    break;

                case 'max':
                {
                    var value = $el.find('.dp-vd').val(),
                        number = +$el.data('number');

                    if (value.length > number) {
                        errorNum++;
                        $el.addClass('has-error');
                        if (msg) $error.text(errorTxt+msg);
                        else $error.text(errorTxt+mark+'不能多于'+number+'位');
                    } else {
                        $el.removeClass('has-error');
                        $error.empty();
                    }
                }
                    break;

                case 'range':
                {
                    var value = $el.find('.dp-vd').val(),
                        number = $el.data('number') || '0,0',
                        numberArray = number.split(',');

                    if (value.length < +numberArray[1] && value.length > +numberArray[0]) {
                        errorNum++;
                        $el.addClass('has-error');
                        if (msg) $error.text(errorTxt+msg);
                        else $error.text(errorTxt+'请输入'+numberArray[0]+'位到'+numberArray[1]+'位的'+mark);
                    }
                }
                    break;

                case 'format':
                {
                    var value = $el.find('.dp-vd').val() || '',
                        reg = $el.data('reg') || '';

                    reg = eval(reg);

                    if (!reg.test(value)) {
                        errorNum++;
                        $el.addClass('has-error');
                        if (msg) $error.text(errorTxt+msg);
                        else $error.text(errorTxt+mark+'格式不正确');
                    }
                }
                    break;

                case 'minCheck':
                {
                    var checked = $el.find(':checked').size(),
                        number = +$el.data('number');

                    if (checked < number) {
                        errorNum++;
                        $el.addClass('has-error');
                        if (msg) $error.text(errorTxt+msg);
                        else $error.text(errorTxt+'请至少选择'+number+'个'+mark);
                    }
                }
                    break;

                case 'maxCheck':
                {
                    var checked = $el.find(':checked').size(),
                        number = +$el.data('number');

                    if (checked > number) {
                        errorNum++;
                        $el.addClass('has-error');
                        if (msg) $error.text(errorTxt+msg);
                        else $error.text(errorTxt+mark+'不能多于'+number+'个');
                    }
                }
                    break;

                case 'linkGt':
                {
                    var value = $el.find('.dp-vd').val(),
                        compare = $el.data('compare-link') || '',
                        $compare = $('#'+compare),
                        compareMark = $el.data('compare-mark') || $compare.data('mark') || '',
                        compareValue = $compare.find('.dp-vd').val();

                    if (value <= compareValue) {
                        errorNum++;
                        $el.addClass('has-error');
                        if (msg) $error.text(errorTxt+msg);
                        else $error.text(errorTxt+mark+'必须大于'+compareMark);
                    }
                }
                    break;

                case 'linkLt':
                {
                    var value = $el.find('.dp-vd').val(),
                        compare = $el.data('compare-link') || '',
                        $compare = $('#'+compare),
                        compareMark = $el.data('compare-mark') || $compare.data('mark') || '',
                        compareValue = $compare.find('.dp-vd').val();

                    if (value >= compareValue) {
                        errorNum++;
                        $el.addClass('has-error');
                        if (msg) $error.text(msg);
                        else $error.text(mark+'必须小于'+compareMark);
                    }
                }
                    break;

                case 'linkEq':
                {
                    var value = $el.find('.dp-vd').val(),
                        compare = $el.data('compare-link') || '',
                        $compare = $('#'+compare),
                        compareMark = $el.data('compare-mark') || $compare.data('mark') || '',
                        compareValue = $compare.find('.dp-vd').val();

                    if (value === compareValue) {
                        $el.removeClass('has-error');
                        $error.empty();
                    } else {
                        errorNum++;
                        $el.addClass('has-error');
                        if (msg) $error.text(errorTxt+msg);
                        else $error.text(errorTxt+mark+'必须等于'+compareMark);
                    }
                }
                    break;
            }
        }
        function relationValidata (relation) {
            var $relation,
                relationFailNum = 0;

            if (relation.indexOf(',')>-1) {
                var relationArray = relation.split(',');

                for (var i=0; i < relationArray.length; i++) {
                    $relation = $('#'+relationArray[i]);
                    if ($relation.hasClass('has-error')) relationFailNum++;
                }
            } else {
                $relation = $('#'+relation);
                if ($relation.hasClass('has-error')) relationFailNum++;
            }

            return relationFailNum;
        }
    };

    $.extend({
        // 提示弹窗
        tipLayer: function(msg) {
            var message = msg || '';

            if (!$('#tipLayer').size()) {
                var tipLayerContent = '<div id="tipLayer" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">'
                    + '<div class="modal-dialog modal-sm">'
                    + '<div class="modal-content">'
                    + '<div class="modal-header">'
                    + '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>'
                    + '<h4 class="modal-title" id="mySmallModalLabel">提示</h4></div>'
                    + '<div class="modal-body tipLayerMsg"></div>'
                    + '</div></div></div>';

                $('body').append(tipLayerContent);
            }

            $('#tipLayer').modal('show').find('.tipLayerMsg').text(message);
        },
        // 确认弹窗
        confirmLayer: function(option) {
            var defaultOption = option || { msg: ''},
                msg = defaultOption.msg || '';

            if (!$('#confirmLayer').size()) {
                var confirmLayerContent = '<div id="confirmLayer" class="modal fade">'
                    + '<div class="modal-dialog">'
                    + '<div class="modal-content">'
                    + '<div class="modal-header">'
                    + '<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
                    + '<h4 class="modal-title">确认</h4></div>'
                    + '<div class="modal-body confirmLayerMsg"></div>'
                    + '<div class="modal-footer">'
                    + '<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                    + '<button type="button" class="btn btn-primary confirm-btn">确认</button></div></div></div></div>';

                $('body').append(confirmLayerContent);
            }

            $('#confirmLayer').modal('show').find('.confirmLayerMsg').text(msg);

            if (typeof defaultOption.confirm === 'function') $('.confirm-btn','#confirmLayer').click(option.confirm);
        },
        // 获取IE版本，若非IE则返回0
        getIeVersion: function() {
            var agent = navigator.userAgent.toLowerCase(),
                regStr_ie = /msie [\d.]+;/gi,
                browser = '';

            //IE
            if(agent.indexOf("msie") > 0) browser = agent.match(regStr_ie);

            return +(browser).replace(/[^0-9.]/ig,'');
        }
    });

    $(function(){
        // 全选 && 反选
        (function(){
            $(document).on('change','.js-allcheck',function(){
                var $parent = $(this).closest('.js-checkparent');

                if ($(this).prop('checked')) $parent.find('.js-checkchild').prop('checked',true);
                else $parent.find('.js-checkchild').prop('checked',false);
            });

            $(document).on('change','.js-checkchild',function(){
                var $parent = $(this).closest('.js-checkparent');

                if ($parent.find('.js-checkchild').size() === $parent.find('.js-checkchild:checked').size()) $parent.find('.js-allcheck').prop('checked',true);
                else $parent.find('.js-allcheck').prop('checked',false);
            });
        })();

        // 列表相关
        (function(){
            // 排序
            $('.js-sort-list').on('click','.js-sort',function(){
                var key = $(this).data('key'),
                    url = window.location.href,
                    type = !$(this).hasClass('text-muted') && $(this).hasClass('fa-sort-amount-desc') ? 'asc' : 'desc';

                window.location.href = buildUrl(buildUrl(url,'sortField',key),'sortType',type);
                return false;
            });

            function buildUrl(url,key,value) {
                if (url.indexOf(key) > -1) {
                    var urlArray =  url.split(key),
                        afterUrl = urlArray[1].indexOf('&') > -1
                            ? urlArray[1].substring(urlArray[1].indexOf('&'))
                            : '';

                    url = urlArray[0]+key+'='+value+afterUrl;
                } else {
                    if (url.indexOf('?') === -1) url += '?'+key+'='+value;
                    else url += '&'+key+'='+value;
                }

                return url;
            }
        })();

        // 表单验证
        (function(){
            $('.dp-validateForm').dpValidate({
                validate: true,
                console: true
            });
        })();

        // 图片选择上传
        (function(){
            var chooseImgfn = function (serverImgId,imgFileArray,imgTypeArray,imgNameArray,serverImgClassify,serverImgPage) {
                this.serverImgId = serverImgId;
                this.imgFileArray = imgFileArray;
                this.imgTypeArray = imgTypeArray;
                this.imgNameArray = imgNameArray;
                this.serverImgClassify = serverImgClassify;
                this.serverImgPage = serverImgPage;
            };

            var chooseImgObj;

            $.extend({
                // 图片选择上传
                chooseImage: {
                    // 初始化
                    int: function(option) {
                        var $imgLay = $('#img-layer'),
                            defaultOption = {
                                serverChooseNum: 5,
                                getServerImgUrl: '',
                                submitLocalImgUrl: '',
                                submitServerImgUrl: '',
                                submitNetworkImgUrl: ''
                            };

                        option = option || defaultOption;

                        if (!$imgLay.size()) {
                            var ieVersion = $.getIeVersion(),
                                localTab,
                                chooseImageLayer,
                                userId = option.userId ? 'data-userid="'+option.userId+'"' : '',
                                chooseNum = option.serverChooseNum || 5;

                            if (!ieVersion || ieVersion > 9) {
                                localTab = '<div id="chooseOriginalBtn" class="btn btn-primary">选择图片</div>'
                                    + '<div class="checkbox dp-localimg-check"><label>'
                                    + '<input id="dp-isOriginal" type="checkbox" checked>'
                                    + '是否上传原图</label></div>'
                                    + '<input id="chooseOriginalInput" class="dp-localimg-input" type="file" accept="image/png,image/jpg,image/jpeg,image/bmp" multiple>';
                            } else {
                                localTab = '<p>您的浏览器不支持本地上传</p>';
                            }

                            chooseImageLayer = '<div id="img-layer" class="img-layer-bg" '+userId+' data-url="'+option.getServerImgUrl+'">'
                                + '<div class="img-layer">'
                                + '<div class="layer-hd">选择图片</div>'
                                + '<div class="layer-bd">'
                                + '<div class="nav-tabs-custom m-0">'
                                + '<ul class="nav nav-tabs">'
                                + '<li class="active">'
                                + '<a href="#local" data-toggle="tab" aria-expanded="false">上传本地图片</a></li>'
                                + '<li><a href="#server" data-toggle="tab" aria-expanded="false">选择图库图片</a></li>'
                                + '<li><a href="#network" data-toggle="tab" aria-expanded="false">添加网络图片</a></li></ul>'
                                + '<div class="tab-content">'
                                + '<div id="local" class="tab-pane active" data-url="'+option.submitLocalImgUrl+'">'
                                + '<div class="chooseImg-btn-box">'
                                + localTab + '</div>'
                                + '<div class="chooseImg-show-box row"></div></div>'
                                + '<div id="server" class="tab-pane form-horizontal" data-url="'+option.submitServerImgUrl+'">'
                                + '<div class="form-group">'
                                + '<label class="col-sm-2 control-label">图片分类</label>'
                                + '<div class="col-sm-10">'
                                + '<select class="form-control imgClassify" ></select></div></div>'
                                + '<div class="server-img-list row" data-num="'+chooseNum+'"></div>'
                                + '<div class="server-img-paging"></div></div>'
                                + '<div id="network" class="tab-pane form-horizontal" data-url="'+option.submitNetworkImgUrl+'">'
                                + '<div class="form-group">'
                                + '<label for="networkImage" class="col-sm-2 control-label">图片地址</label>'
                                + '<div class="col-sm-10">'
                                + '<input id="networkImage" name="networkImage" type="text" class="form-control"></div></div>'
                                + '<div class="network-imgshow">'
                                + '<p>图片预览</p></div></div></div></div></div>'
                                + '<div class="layer-ft">'
                                + '<span class="imgLayer-errormsg"></span>'
                                + '<button type="button" class="btn btn-default m-r-5 img-cancel-btn">取消</button>'
                                + '<button type="button" class="btn btn-primary img-submit-btn">保存</button></div></div></div>';

                            $('body').append(chooseImageLayer);
                        } else {
                            $imgLay.find('#chooseOriginalInput').val('');
                            $imgLay.find('.imgLayer-errormsg').empty();
                            $imgLay.find('.chooseImg-show-box').empty();
                            $imgLay.find('.network-imgshow').empty();
                            $imgLay.find('.chooseImg-btn-box').show();
                        }

                        chooseImgObj = new chooseImgfn([],[],[],[],[],1,1);
                    },
                    // 显示
                    show: function() {
                        var $imgLay = $('#img-layer'),
                            url = $imgLay.data('url');

                        if (url) {
                            var getServerImg = $.ajax({
                                url: url,
                                type: 'post',
                                dataType: 'json',
                                data: {
                                    page: 1,
                                    classify: 1
                                },
                                timeout: 5000,
                                beforeSend: function() {

                                }
                            })
                                .done(function(data){
                                    if (data.status) {
                                        var imglist = '',
                                            pagelist = '',
                                            classifylist = '<option value="1">未分类</option>';

                                        if (data.classifylist) {
                                            for (var c = 0; c<data.classifylist.length; c++) {
                                                classifylist += '<option value="'+data.classifylist[c].id+'">'+data.classifylist[c].title+'</option>';
                                            }
                                        }

                                        if (data.imglist) {
                                            for (var i = 0; i<data.imglist.length; i++) {
                                                imglist += '<label class="server-img-item col-sm-4">'
                                                    + '<input type="checkbox" value="'+data.imglist[i].id+'">'
                                                    + '<img src="'+data.imglist[i].url+'"></label>';
                                            }
                                        }

                                        if (data.maxPageNum) {
                                            var prev = data.curPageNum === 1 ? '<a class="prev disabled" href="javascript:void(0);">&lt; 上一页</a>' : '<a class="prev" data-page="'+(data.curPageNum-1)+'" href="javascript:void(0);">&lt; 上一页</a>',
                                                next = data.curPageNum === data.maxPageNum ? '<a class="next disabled" href="#">下一页 &gt;</a>' : '<a class="next" data-page="'+(data.curPageNum+1)+'" href="javascript:void(0);">下一页 &gt;</a>';

                                            pagelist += prev;

                                            if (data.maxPageNum > 6) {

                                                if (data.curPageNum >=1 && data.curPageNum <= 4) {
                                                    for (var p = 1; p <= 4; p++) {
                                                        if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                                        else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                                    }

                                                    pagelist += '<span>……</span>'
                                                        + '<a data-page="'+data.maxPageNum+'" href="javascript:void(0);">'+data.maxPageNum+'</a>';
                                                } else if (data.curPageNum >= data.maxPageNum-4 && data.curPageNum <= data.maxPageNum) {
                                                    pagelist += '<a data-page="1" href="javascript:void(0);">1</a>'
                                                        + '<span>……</span>';

                                                    for (var p = data.maxPageNum-4; p <= data.maxPageNum; p++) {
                                                        if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                                        else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                                    }
                                                } else if (data.curPageNum > 4) {
                                                    pagelist += '<a data-page="1" href="javascript:void(0);">1</a>'
                                                        + '<span>……</span>'
                                                        + '<a class="active" href="javascript:void(0);">'+data.curPageNum+'</a>'
                                                        + '<a data-page="'+(data.curPageNum+1)+'" href="javascript:void(0);">'+(data.curPageNum+1)+'</a>'
                                                        + '<span>……</span>'
                                                        + '<a data-page="'+data.maxPageNum+'" href="javascript:void(0);">'+data.maxPageNum+'</a>';
                                                }
                                            } else {
                                                for (var p = 1; p < data.maxPageNum; p++) {
                                                    if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                                    else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                                }
                                            }

                                            pagelist +=  next + '<form class="server-img-paging-form" action="">向第'
                                                + '<input type="text">页'
                                                + '<button type="submit">跳转</button></form>';
                                        }

                                        $imgLay.find('.server-img-list').html(imglist).siblings('.server-img-paging').html(pagelist);
                                        $imgLay.find('.imgClassify').html(classifylist);

                                        $imgLay.show();
                                    } else {
                                        $imgLay.show();
                                    }
                                })
                                .fail(function(){
                                    $imgLay.show();
                                })
                                .always(function(XMLHttpRequest,status){
                                    if (status === 'timeout') {
                                        getServerImg.abort();
                                        $imgLay.show();
                                    }
                                });
                        } else {
                            $imgLay.show();
                        }
                    },
                    // 隐藏
                    hide: function() {
                        var $imgLay = $('#img-layer');

                        $imgLay.hide();
                        $imgLay.find('.nav-tabs li:eq(0)').addClass('active').siblings().removeClass('active');
                        $imgLay.find('.tab-pane').eq(0).addClass('active').siblings().removeClass('active');
                        $.chooseImage.int();
                    },
                    // 预览
                    preview: function(option) {
                        // 定义图片最大宽度
                        var max_Width = option.max_Width || 1200;
                        // 定义图片最大高度
                        var max_Height = option.max_Height || 960;
                        // 定义压缩方式
                        var compressType = option.type || 'none';
                        // 定义压缩后图片格式
                        var imgType = option.imgType || 'image/jpeg';
                        // 定义开始事件
                        var beforeMethod = option.beforeMethod || function () {};
                        // 定义结束事件
                        var endMethod = option.endMethod || function () {};

                        // 创建Image对象
                        var image = new Image();

                        beforeMethod();

                        image.onload = function () {
                            // 创建 canvas DOM
                            var canvas = document.createElement('canvas');
                            var ctx = canvas.getContext("2d");

                            // 图片尺寸压缩
                            switch (compressType) {
                                case 'height_Auto':
                                {
                                    //如果高度超标，则等比缩放
                                    if (image.width > max_Width) {
                                        image.width *= max_Height / image.height;
                                        image.height = max_Height;
                                    }
                                }
                                    break;

                                case 'width_Auto':
                                {
                                    //如果宽度超标，则等比缩放
                                    if (image.width > max_Width) {
                                        image.height *= max_Width / image.width;
                                        image.width = max_Width;
                                    }
                                }
                                    break;

                                case 'clip':
                                {
                                    // 如果宽度或高度超标，则裁剪
                                    if (image.width > max_Width) image.width = max_Width;
                                    if (image.height > max_Height) image.height = max_Height;
                                }
                                    break;

                                default:
                                    break;
                            }

                            canvas.width = image.width;
                            canvas.height = image.height;
                            ctx.drawImage(image, 0, 0, image.width, image.height);

                            // 获取压缩后图片URL方便回传
                            var url = canvas.toDataURL(imgType);
                            endMethod(url);
                        };

                        image.src = option.url;
                    },
                    // 提交
                    choose: function(obj,callback){
                        var $imgLay = $('#img-layer'),
                            type = $imgLay.find('.tab-pane.active').attr('id'),
                            url = $imgLay.find('.tab-pane.active').data('url'),
                            dataObj = new FormData();

                        switch (type) {
                            case 'local':
                            {
                                for (var x = 0; x < obj.imgFileArray.length; x++) {
                                    dataObj.append('imgFile'+x,obj.imgFileArray[x]);
                                }

                                for (var y = 0; y < obj.imgTypeArray.length; y++) {
                                    dataObj.append('imgType'+y,obj.imgFileArray[y]);
                                }

                                for (var z = 0; z < obj.imgNameArray.length; z++) {
                                    dataObj.append('imgName'+z,obj.imgFileArray[z]);
                                }
                            }
                                break;

                            case 'server':
                            {
                                dataObj.append('serverImgId',obj.serverImgId);
                            }
                                break;

                            case 'network':
                            {
                                dataObj.append('networkImgUrl',$('#networkImage').val());
                            }
                                break;

                            default:
                                break;
                        }

                        $.ajax({
                            url: url,
                            type: 'post',
                            dataType: 'json',
                            data: dataObj,
                            processData: false,
                            contentType: false,
                            beforeSend: function(){

                            }
                        })
                            .done(function(data){
                                if (data.status) {
                                    if (typeof callback === 'function') callback(data.image);
                                    $.chooseImage.hide();
                                } else {
                                    $imgLay.find('.imgLayer-errormsg').text(data.info);
                                }
                            })
                            .fail(function(){
                                $imgLay.find('.imgLayer-errormsg').text('传输失败');
                            });
                    },
                    // 获取数据
                    getImgData: function(obj) {
                        chooseImgObj = obj || chooseImgObj;
                        return chooseImgObj;
                    }
                }
            });

            // 打开本地文件选择组件
            $('body').on('click','#chooseOriginalBtn',function(){
                $('#chooseOriginalInput').click();
            });

            // 选择本地图片预览
            $('body').on('change','#chooseOriginalInput',function(e){
                var $error = $('#img-layer').find('.imgLayer-errormsg');

                if (this.value) {
                    if (e.target.files.length > 5) {
                        $error.text('最多只能同时选择5张图片');
                        return false;
                    }

                    for (var i=0; i < e.target.files.length;i++) {
                        if (e.target.files[i].size > 5242880) {
                            $error.text('所选图片不能大于5M');
                            return false;
                        }
                        var imgType = e.target.files[i].type;
                        var imgName = e.target.files[i].name;
                        var freader = new FileReader();

                        freader.readAsDataURL(e.target.files[i]);
                        freader.onload = function (e) {
                            var img = new Image;
                            img.src = e.target.result;

                            img.onload = function () {
                                var compressType = $('#dp-isOriginal').prop('checked') ? 'none' : 'width_Auto';
                                $.chooseImage.preview({
                                    url: img.src,
                                    imgType: imgType,
                                    compressType: compressType,
                                    endMethod: function(url){
                                        var imgcontent = '<div class="col-sm-4"><img src="'+url+'"></div>';
                                        $('#img-layer').find('.chooseImg-show-box').append(imgcontent);
                                        $('#img-layer').find('.chooseImg-btn-box').hide();
                                        chooseImgObj.imgFileArray.push(url);
                                        chooseImgObj.imgTypeArray.push(imgType);
                                        chooseImgObj.imgNameArray.push(imgName);
                                    }
                                });
                            }
                        }
                    }
                }
            });

            // 点击分页跳转
            $('body').on('click','.server-img-paging a',function(){
                var $imgLay = $('#img-layer'),
                    $error = $imgLay.find('.imgLayer-errormsg'),
                    url = $imgLay.data('url'),
                    page = $(this).data('page');

                chooseImgObj.serverImgPage = page;

                if (page) {
                    $.ajax({
                        url: url,
                        type: 'post',
                        dataType: 'json',
                        data: {
                            page: chooseImgObj.serverImgPage,
                            classify: chooseImgObj.serverImgClassify
                        },
                        beforeSend: function() {

                        }
                    })
                        .done(function(data){
                            if (data.status) {
                                var imglist = '',
                                    pagelist = '';

                                if (data.imglist) {
                                    for (var i = 0; i<data.imglist.length; i++) {
                                        var boxChecked = '';
                                        for (var x = 0; x<chooseImgObj.serverImgId.length; x++) {
                                            if (chooseImgObj.serverImgId[x] === data.imglist[i].id) {
                                                boxChecked = 'checked';
                                                break;
                                            }
                                        }

                                        imglist += '<label class="server-img-item col-sm-4">'
                                            + '<input type="checkbox" value="'+data.imglist[i].id+'" '+boxChecked+'>'
                                            + '<img src="'+data.imglist[i].url+'"></label>';
                                    }
                                }

                                if (data.maxPageNum) {
                                    var prev = data.curPageNum === 1 ? '<a class="prev disabled" href="javascript:void(0);">&lt; 上一页</a>' : '<a class="prev" data-page="'+(data.curPageNum-1)+'" href="javascript:void(0);">&lt; 上一页</a>',
                                        next = data.curPageNum === data.maxPageNum ? '<a class="next disabled" href="#">下一页 &gt;</a>' : '<a class="next" data-page="'+(data.curPageNum+1)+'" href="javascript:void(0);">下一页 &gt;</a>';

                                    pagelist += prev;

                                    if (data.maxPageNum > 6) {

                                        if (data.curPageNum >=1 && data.curPageNum <= 4) {
                                            for (var p = 1; p <= 4; p++) {
                                                if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                                else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                            }

                                            pagelist += '<span>……</span>'
                                                + '<a data-page="'+data.maxPageNum+'" href="javascript:void(0);">'+data.maxPageNum+'</a>';
                                        } else if (data.curPageNum >= data.maxPageNum-4 && data.curPageNum <= data.maxPageNum) {
                                            pagelist += '<a data-page="1" href="javascript:void(0);">1</a>'
                                                + '<span>……</span>';

                                            for (var p = data.maxPageNum-4; p <= data.maxPageNum; p++) {
                                                if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                                else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                            }
                                        } else if (data.curPageNum > 4) {
                                            pagelist += '<a data-page="1" href="javascript:void(0);">1</a>'
                                                + '<span>……</span>'
                                                + '<a class="active" href="javascript:void(0);">'+data.curPageNum+'</a>'
                                                + '<a data-page="'+(data.curPageNum+1)+'" href="javascript:void(0);">'+(data.curPageNum+1)+'</a>'
                                                + '<span>……</span>'
                                                + '<a data-page="'+data.maxPageNum+'" href="javascript:void(0);">'+data.maxPageNum+'</a>';
                                        }
                                    } else {
                                        for (var p = 1; p < data.maxPageNum; p++) {
                                            if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                            else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                        }
                                    }

                                    pagelist +=  next + '<form class="server-img-paging-form" action="">向第'
                                        + '<input type="text">页'
                                        + '<button type="submit">跳转</button></form>';
                                }

                                $imgLay.find('.server-img-list').html(imglist).siblings('.server-img-paging').html(pagelist);
                            } else {
                                $error.text('查询失败');
                            }
                        })
                        .fail(function(){
                            $error.text('查询失败');
                        });
                }
            });

            // 跳转到指定页
            $('body').on('submit','.server-img-paging-form',function(){
                var $imgLay = $('#img-layer'),
                    $error = $imgLay.find('.imgLayer-errormsg'),
                    url = $imgLay.data('url'),
                    page = $(this).find('input').val(),
                    reg = /^[1-9]\d*$/;

                if (!reg.test(page)) $error.text('页数只能为正整数');
                else {
                    $error.text('');
                    chooseImgObj.serverImgPage = page;

                    $.ajax({
                        url: url,
                        type: 'post',
                        dataType: 'json',
                        data: {
                            page: chooseImgObj.serverImgPage,
                            classify: chooseImgObj.serverImgClassify
                        },
                        beforeSend: function() {

                        }
                    })
                        .done(function(data){
                            if (data.status) {
                                var imglist = '',
                                    pagelist = '';

                                if (data.imglist) {
                                    for (var i = 0; i<data.imglist.length; i++) {
                                        var boxChecked = '';
                                        for (var x = 0; x<chooseImgObj.serverImgId.length; x++) {
                                            if (chooseImgObj.serverImgId[x] === data.imglist[i].id) {
                                                boxChecked = 'checked';
                                                break;
                                            }
                                        }

                                        imglist += '<label class="server-img-item col-sm-4">'
                                            + '<input type="checkbox" value="'+data.imglist[i].id+'" '+boxChecked+'>'
                                            + '<img src="'+data.imglist[i].url+'"></label>';
                                    }
                                }

                                if (data.maxPageNum) {
                                    var prev = data.curPageNum === 1 ? '<a class="prev disabled" href="javascript:void(0);">&lt; 上一页</a>' : '<a class="prev" data-page="'+(data.curPageNum-1)+'" href="javascript:void(0);">&lt; 上一页</a>',
                                        next = data.curPageNum === data.maxPageNum ? '<a class="next disabled" href="#">下一页 &gt;</a>' : '<a class="next" data-page="'+(data.curPageNum+1)+'" href="javascript:void(0);">下一页 &gt;</a>';

                                    pagelist += prev;

                                    if (data.maxPageNum > 6) {

                                        if (data.curPageNum >=1 && data.curPageNum <= 4) {
                                            for (var p = 1; p <= 4; p++) {
                                                if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                                else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                            }

                                            pagelist += '<span>……</span>'
                                                + '<a data-page="'+data.maxPageNum+'" href="javascript:void(0);">'+data.maxPageNum+'</a>';
                                        } else if (data.curPageNum >= data.maxPageNum-4 && data.curPageNum <= data.maxPageNum) {
                                            pagelist += '<a data-page="1" href="javascript:void(0);">1</a>'
                                                + '<span>……</span>';

                                            for (var p = data.maxPageNum-4; p <= data.maxPageNum; p++) {
                                                if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                                else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                            }
                                        } else if (data.curPageNum > 4) {
                                            pagelist += '<a data-page="1" href="javascript:void(0);">1</a>'
                                                + '<span>……</span>'
                                                + '<a class="active" href="javascript:void(0);">'+data.curPageNum+'</a>'
                                                + '<a data-page="'+(data.curPageNum+1)+'" href="javascript:void(0);">'+(data.curPageNum+1)+'</a>'
                                                + '<span>……</span>'
                                                + '<a data-page="'+data.maxPageNum+'" href="javascript:void(0);">'+data.maxPageNum+'</a>';
                                        }
                                    } else {
                                        for (var p = 1; p < data.maxPageNum; p++) {
                                            if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                            else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                        }
                                    }

                                    pagelist +=  next + '<form class="server-img-paging-form" action="">向第'
                                        + '<input type="text">页'
                                        + '<button type="submit">跳转</button></form>';
                                }

                                $imgLay.find('.server-img-list').html(imglist).siblings('.server-img-paging').html(pagelist);
                            } else {
                                $error.text('查询失败');
                            }
                        })
                        .fail(function(){
                            $error.text('查询失败');
                        });
                }

                return false;
            });

            // 切换图库分类
            $('body').on('change','.imgClassify',function(){
                var classify = this.value,
                    $imgLay = $('#img-layer'),
                    $error = $imgLay.find('.imgLayer-errormsg'),
                    url = $imgLay.data('url');

                chooseImgObj.serverImgClassify = classify;
                chooseImgObj.serverImgPage = 1;

                $.ajax({
                    url: url,
                    type: 'post',
                    dataType: 'json',
                    data: {
                        page: chooseImgObj.serverImgPage,
                        classify: chooseImgObj.serverImgClassify
                    },
                    beforeSend: function() {

                    }
                })
                    .done(function(data){
                        if (data.status) {
                            var imglist = '',
                                pagelist = '';

                            if (data.imglist) {
                                for (var i = 0; i<data.imglist.length; i++) {
                                    var boxChecked = '';
                                    for (var x = 0; x<chooseImgObj.serverImgId.length; x++) {
                                        if (chooseImgObj.serverImgId[x] === data.imglist[i].id) {
                                            boxChecked = 'checked';
                                            break;
                                        }
                                    }

                                    imglist += '<label class="server-img-item col-sm-4">'
                                        + '<input type="checkbox" value="'+data.imglist[i].id+'" '+boxChecked+'>'
                                        + '<img src="'+data.imglist[i].url+'"></label>';
                                }
                            }

                            if (data.maxPageNum) {
                                var prev = data.curPageNum === 1 ? '<a class="prev disabled" href="javascript:void(0);">&lt; 上一页</a>' : '<a class="prev" data-page="'+(data.curPageNum-1)+'" href="javascript:void(0);">&lt; 上一页</a>',
                                    next = data.curPageNum === data.maxPageNum ? '<a class="next disabled" href="#">下一页 &gt;</a>' : '<a class="next" data-page="'+(data.curPageNum+1)+'" href="javascript:void(0);">下一页 &gt;</a>';

                                pagelist += prev;

                                if (data.maxPageNum > 6) {

                                    if (data.curPageNum >=1 && data.curPageNum <= 4) {
                                        for (var p = 1; p <= 4; p++) {
                                            if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                            else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                        }

                                        pagelist += '<span>……</span>'
                                            + '<a data-page="'+data.maxPageNum+'" href="javascript:void(0);">'+data.maxPageNum+'</a>';
                                    } else if (data.curPageNum >= data.maxPageNum-4 && data.curPageNum <= data.maxPageNum) {
                                        pagelist += '<a data-page="1" href="javascript:void(0);">1</a>'
                                            + '<span>……</span>';

                                        for (var p = data.maxPageNum-4; p <= data.maxPageNum; p++) {
                                            if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                            else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                        }
                                    } else if (data.curPageNum > 4) {
                                        pagelist += '<a data-page="1" href="javascript:void(0);">1</a>'
                                            + '<span>……</span>'
                                            + '<a class="active" href="javascript:void(0);">'+data.curPageNum+'</a>'
                                            + '<a data-page="'+(data.curPageNum+1)+'" href="javascript:void(0);">'+(data.curPageNum+1)+'</a>'
                                            + '<span>……</span>'
                                            + '<a data-page="'+data.maxPageNum+'" href="javascript:void(0);">'+data.maxPageNum+'</a>';
                                    }
                                } else {
                                    for (var p = 1; p < data.maxPageNum; p++) {
                                        if (p === data.curPageNum) pagelist += '<a class="active" href="javascript:void(0);">'+p+'</a>';
                                        else pagelist += '<a data-page="'+p+'" href="javascript:void(0);">'+p+'</a>';
                                    }
                                }

                                pagelist +=  next + '<form class="server-img-paging-form" action="">向第'
                                    + '<input type="text">页'
                                    + '<button type="submit">跳转</button></form>';
                            }

                            $imgLay.find('.server-img-list').html(imglist).siblings('.server-img-paging').html(pagelist);
                        } else {
                            $error.text('查询失败');
                        }
                    })
                    .fail(function(){
                        $error.text('查询失败');
                    });
            });

            // 选择图库图片
            $('body').on('change','.server-img-item :checkbox',function(){
                var $error = $('#img-layer').find('.imgLayer-errormsg'),
                    num = $('#img-layer').find('.server-img-list').data('num'),
                    id = $(this).val();


                if ($(this).prop('checked')) {
                    if (chooseImgObj.serverImgId.length == num ) {
                        $error.text('最多只能选择'+num+'张图片');
                        $(this).prop('checked',false);
                    } else {
                        chooseImgObj.serverImgId.push(id);
                    }
                } else {
                    chooseImgObj.serverImgId = chooseImgObj.serverImgId.filter(function(item){ return item != id; });
                    $error.text('');
                }
            });

            // 网络图片预览
            $('body').on('propertychange input','#networkImage',function(){
                var url = this.value,
                    $networkbox = $('#img-layer').find('.network-imgshow');

                if ($networkbox.find('img').size()) $networkbox.find('img').attr('src',url);
                else $networkbox.append('<img src="'+url+'">');
            });

            // 关闭组件弹窗
            $('body').on('click','.img-cancel-btn',function(){
                $.chooseImage.hide();
            });
        })();
    });
})(jQuery,window,document);
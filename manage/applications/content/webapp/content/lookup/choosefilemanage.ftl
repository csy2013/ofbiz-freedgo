<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="en" xml:lang="en">
<head>
    <title>文件查看</title>
    <link rel="stylesheet" type="text/css" href="<@ofbizContentUrl>/images/jquery/ui/css/ui-lightness/jquery-ui-1.8.16.custom.css</@ofbizContentUrl>"/>
    <script type="text/javascript" src="<@ofbizContentUrl>/images/jquery/jquery-1.7.min.js</@ofbizContentUrl>"></script>
    <script type="text/javascript" src="<@ofbizContentUrl>/images/jquery/plugins/jstree0.0.9/jquery.tree.js</@ofbizContentUrl>"></script>
    <script type="text/javascript" src="<@ofbizContentUrl>/images/jquery/plugins/upload/ajaxupload.js</@ofbizContentUrl>"></script>
    <script type="text/javascript" src="<@ofbizContentUrl>/images/jquery/ui/js/jquery-ui-1.8.16.custom.min.js</@ofbizContentUrl>"></script>
    <script type="text/javascript" src="<@ofbizContentUrl>/images/jquery/ui/development-bundle/external/jquery.bgiframe-2.1.2.js</@ofbizContentUrl>"></script>
    <script type="text/javascript" src="<@ofbizContentUrl>/images/jquery/plugins/artDialog/artDialog.source.js</@ofbizContentUrl>"></script>
    <script type="text/javascript" src="<@ofbizContentUrl>/images/jquery/plugins/artDialog/iframeTools.js</@ofbizContentUrl>"></script>
    <style type="text/css">
        body {
            padding: 0;
            margin: 0;
            background: #F7F7F7;
            font-family: Verdana, Arial, Helvetica, sans-serif;
            font-size: 11px;
        }

        img {
            border: 0;
        }

        #container {
            padding: 0px 10px 7px 10px;
            height: 450px;
        }

        #menu {
            clear: both;
            height: 29px;
            margin-bottom: 3px;

        }

        #column_left {
            background: #FFF;
            border: 1px solid #CCC;
            float: left;
            width: 20%;
            height: 450px;
            overflow: auto;
        }

        #column_right {
            background: #FFF;
            border: 1px solid #CCC;
            float: right;
            width: 78%;
            height: 450px;
            overflow: auto;
            text-align: center;
        }

        #column_right div {
            text-align: left;
            padding: 5px;
        }

        #column_right a {
            display: inline-block;
            text-align: center;
            border: 1px solid #EEEEEE;
            cursor: pointer;
            margin: 5px;
            padding: 5px;
        }

        #column_right a.selected {
            border: 1px solid #7DA2CE;
            background: #EBF4FD;
        }

        #column_right input {
            display: none;
        }

        #dialog {
            display: none;
        }

        .button {
            display: block;
            float: left;
            padding: 8px 5px 8px 25px;
            margin-right: 5px;
            background-position: 5px 6px;
            background-repeat: no-repeat;
            cursor: pointer;
        }

        .button:hover {
            background-color: #EEEEEE;
        }

        .thumb {
            padding: 5px;
            width: 105px;
            height: 105px;
            background: #F7F7F7;
            border: 1px solid #CCCCCC;
            cursor: pointer;
            cursor: move;
            position: relative;
        }
    </style>
</head>
<body>

<div id="container">
    <div id="menu">
    <#--<a id="create" class="button"  style="background-image: url('view/image/filemanager/folder.png');">#language("filemanager_button_folder")</a>
    <a id="delete" class="button"  style="background-image: url('view/image/filemanager/edit-delete.png');">#language("filemanager_button_delete")</a>
    <a id="move" class="button"  style="background-image: url('view/image/filemanager/edit-cut.png');">#language("filemanager_button_move")</a>
    <a id="copy" class="button"  style="background-image: url('view/image/filemanager/edit-copy.png');">#language("filemanager_button_copy")</a>
    <a id="rename" class="button" style="background-image: url('view/image/filemanager/edit-rename.png');">#language("filemanager_button_rename")</a>-->
    <#assign butupload = Static["org.ofbiz.base.util.UtilProperties"].getMessage("ContentUiLabels", "filemanager_button_upload",  locale)/>
    <#assign butrefresh = Static["org.ofbiz.base.util.UtilProperties"].getMessage("ContentUiLabels", "filemanager_button_refresh",  locale)/>
        <a id="upload" class="button" style="background-image: url('/images/img/upload.png');">${butupload}</a>
        <a id="refresh" class="button" style="background-image: url('/images/img/refresh.png');">${butrefresh}</a>
    </div>
    <div id="column_left"></div>
    <div id="column_right"></div>
</div>
<script type="text/javascript"><!--
jQuery(document).ready(function () {
    jQuery('#column_left').tree({
        data: {
            type: 'json',
            async: true,
            opts: {
                method: 'POST',
                url: '/content/control/filemanage?t=init'
            }
        },
        selected: 'top',
        ui: {
            theme_name: 'classic',
            animation: 700
        },
        types: {
            'default': {
                clickable: true,
                creatable: false,
                renameable: false,
                deletable: false,
                draggable: false,
                max_children: -1,
                max_depth: -1,
                valid_children: 'all'
            }
        },
        callback: {
            beforedata: function (NODE, TREE_OBJ) {
                if (NODE == false) {
                    TREE_OBJ.settings.data.opts.static = [
                        {
                            data: '图片库',
                            attributes: {
                                'id': 'top',
                                'directory': ''
                            },
                            state: 'closed'
                        }
                    ];

                    return {'directory': ''}
                } else {
                    TREE_OBJ.settings.data.opts.static = false;
                    return {'directory': jQuery(NODE).attr('directory')}
                }
            },
            onselect: function (NODE, TREE_OBJ) {
                jQuery.ajax({
                    url: '/content/control/filemanage?t=dir',
                    type: 'POST',
                    data: 'directory=' + encodeURIComponent(jQuery(NODE).attr('directory')),
                    dataType: 'json',
                    success: function (json) {
                        html = '<div>';
                        if (json) {
                            for (i = 0; i < json.length; i++) {

                                name = '';

                                filename = json[i]['filename'];

                                for (j = 0; j < filename.length; j = j + 15) {
//                                    name += filename.substr(j, 15) + '<br />';
                                    name += json[i].name + "<br/>";
                                }
                                var width = json[i].width;
                                var height = json[i].height;
                                name += "(" + width + "*" + height + ")" + "<br/>";
                                name += json[i]['size'];

                                html += '<a file="' + json[i]['file'] + '"><img src="/images' + json[i]['thumb'] + '" title="' + json[i]['filename'] + '" width="100" /><br />' + name + '</a>';
                            }
                        }

                        html += '</div>';

                        jQuery('#column_right').html(html);
                    }
                });
            }
        }
    });

    jQuery('#column_right a').live('click', function () {
        if (jQuery(this).attr('class') == 'selected') {
            jQuery(this).removeAttr('class');
        } else {
            jQuery('#column_right a').removeAttr('class');

            jQuery(this).attr('class', 'selected');
        }
    });

    jQuery('#column_right a').live('dblclick', function () {
        api = art.dialog.open.api;	// 			art.dialog.open扩展方法
        if (!api) return;
        var win = art.dialog.open.origin;//来源页面
        win.window.saveChoooseImage('/images' + jQuery(this).attr('file'), api.config.id);
        art.dialog.close();
    });

    jQuery('#refresh').bind('click', function () {
        var tree = jQuery.tree.focused();
        tree.refresh(tree.selected);
    });
});


/*jQuery('#rename').bind('click', function () {
    jQuery('#dialog').remove();

    html = '<div id="dialog">';
    html += ' #language("filemanager_entry_rename") <input type="text" name="name" value="" /> <input type="button" value="Submit" />';
    html += '</div>';

    jQuery('#column_right').prepend(html);

    jQuery('#dialog').dialog({
        title: ' #language("filemanager_button_rename")',
        resizable: false
    });

    jQuery('#dialog input[type=\'button\']').bind('click', function () {
        path = jQuery('#column_right a.selected').attr('file');

        if (path) {
            jQuery.ajax({
                url: '/common/fileManager/rename?t=$token',
                type: 'POST',
                data: 'path=' + encodeURIComponent(path) + '&name=' + encodeURIComponent(jQuery('#dialog input[name=\'name\']').val()),
                dataType: 'json',
                success: function (json) {
                    if (json.JSONObject.success) {
                        jQuery('#dialog').remove();

                        var tree = jQuery.tree.focused();

                        tree.select_branch(tree.selected);

                        alert(json.JSONObject.success);
                    }

                    if (json.error) {
                        alert(json.JSONObject.error);
                    }
                }
            });
        } else {
            var tree = jQuery.tree.focused();

            jQuery.ajax({
                url: ‘/common/fileManager/rename?t=$token',
                type: 'POST',
                data: 'path=' + encodeURIComponent(jQuery(tree.selected).attr('directory')) + '&name=' + encodeURIComponent(jQuery('#dialog input[name=\'name\']').val()),
                dataType: 'json',
                success: function (json) {
                    if (json.JSONObject.success) {
                        jQuery('#dialog').remove();

                        tree.select_branch(tree.parent(tree.selected));

                        tree.refresh(tree.selected);

                        alert(json.JSONObject.success);
                    }

                    if (json.error) {
                        alert(json.JSONObject.error);
                    }
                }
            });
        }
    });
});*/

new AjaxUpload('#upload', {
    action: '/content/control/filemanage?t=upload',
    name: 'image',
    autoSubmit: false,
    responseType: 'json',
    onChange: function (file, extension) {
        var tree = jQuery.tree.focused();
        console.log(jQuery(tree.selected).attr('directory'));
        if (tree.selected) {
            this.setData({'directory': jQuery(tree.selected).attr('directory')});
        } else {
            this.setData({'directory': ''});
        }
        this.submit();
    },
    onSubmit: function (file, extension) {
        jQuery('#upload').append('<img src="/images/img/loading.gif" id="loading" style="padding-left: 5px;" />');
    },
    onComplete: function (file, json) {
        if (json.success) {
            var tree = jQuery.tree.focused();
            tree.select_branch(tree.selected);
            alert(json.success);
        }
        if (json.error) {
            alert(json.error);
        }

        jQuery('#loading').remove();
    }
});
//--></script>
</body>
</html>

/*
Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http=//ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example=
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';


    config.uiColor = '#AADC6E';
    config.filebrowserImageBrowseUrl= '/content/control/file',
    config.filebrowserBrowseUrl= '/content/control/file',
    config.filebrowserImageBrowseUrl= '/content/control/file',
    config.filebrowserFlashBrowseUrl= '/content/control/file',
    config.filebrowserUploadUrl= '/content/control/file',
    config.filebrowserImageUploadUrl= '/content/control/file',
    config.filebrowserFlashUploadUrl= '/content/control/file',
/*    config.filebrowserWindowWidth = '1024';
    config.filebrowserWindowHeight = '768';*/

    config.resize_enabled = true;

    config.toolbar = 'Custom';

    config.toolbar_Custom = [
        ['Save'],
        ['Source'],
        ['Maximize'],
        ['Bold', 'Italic', 'Underline', 'Strike', 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
        ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock','SpecialChar'],
        '/',
        ['Undo', 'Redo'],
        ['Font', 'FontSize'],
        ['TextColor', 'BGColor'],
        ['Link', 'Unlink', 'Anchor'],
        ['Image', 'Table', 'HorizontalRule']
    ];

    config.toolbar_Full =
        [
            ['Source', '-', 'Save', 'NewPage', 'Preview', '-', 'Templates'],
            ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Print', 'SpellChecker', 'Scayt'],
            ['Undo', 'Redo', '-', 'Find', 'Replace', '-', 'SelectAll', 'RemoveFormat'],
            ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
            '/',
            ['Bold', 'Italic', 'Underline', 'Strike', '-', 'Subscript', 'Superscript'],
            ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', 'Blockquote'],
            ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
            ['Link', 'Unlink', 'Anchor'],
            ['Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak'],
            '/',
            ['Styles', 'Format', 'Font', 'FontSize'],
            ['TextColor', 'BGColor'],
            ['Maximize', 'ShowBlocks', '-', 'About']
        ];

};

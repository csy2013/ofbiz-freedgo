/* ========================================================= 
 * bootstrap-gtreetable v2.2.1-alpha
 * https://github.com/gilek/bootstrap-gtreetable
 * ========================================================= 
 * Copyright 2014 Maciej Kłak
 * Licensed under MIT (https://github.com/gilek/bootstrap-gtreetable/blob/master/LICENSE)
 * ========================================================= */

// Chinese Translation by Thinking Song

(function( $ ) {
    $.fn.gtreetable.defaults.languages['zh-CN'] = {
        save: '保存',
        cancel: '取消',
        action: '操作',
        actions: {
            createBefore: '之前创建',
            createAfter: '之后创建',
            createFirstChild: '创建第一个分类',
            createLastChild: '创建最后一个分类',
            update: '修改当前',
            delete: '删除当前'
        },
        messages: {
            onDelete: '你确定删除该节点?',
            onNewRootNotAllowed: '不允许添加目录.',
            onMoveInDescendant: '目标节点不能是后裔节点T.',
            onMoveAsRoot: '目标节点不能是根节点.'
        }
    };
}( jQuery ));

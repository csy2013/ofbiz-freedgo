<#--<script language="javascript" type="text/javascript" src="<@ofbizContentUrl>/images/jquery/plugins/jsTree/jquery.jstree.js</@ofbizContentUrl>"></script>-->
<#--<script language="javascript" type="text/javascript" src="<@ofbizContentUrl>/images/jquery/plugins/cookie/jquery.cookie.js</@ofbizContentUrl>"></script>-->
<link rel="stylesheet" type="text/css" href="<@ofbizContentUrl>/images/bootstrap/plugins/bootstrap-gtreetable/dist/bootstrap-gtreetable.min.css</@ofbizContentUrl>"/>
<script type="text/javascript" src="<@ofbizContentUrl>/images/bootstrap/plugins/bootstrap-gtreetable/dist/bootstrap-gtreetable.js</@ofbizContentUrl>"></script>
<script type="text/javascript" src="<@ofbizContentUrl>/images/bootstrap/plugins/bootstrap-gtreetable/dist/languages/bootstrap-gtreetable.zh-CN.js</@ofbizContentUrl>"></script>

<script type="text/javascript">
    <#-- some labels are not unescaped in the JSON object so we have to do this manualy -->
    function unescapeHtmlText(text) {
        return jQuery('<div />').html(text).text()
    }

    //    "id": "node ID", "name": "node name", "level": "node level", "type": "node type"
    var gtreedata = [
    <#if (gTreeTable?has_content)>
        <@fillTree1 rootCat1 = gTreeTable/>
    </#if>

    <#macro fillTree1 rootCat1>
        <#if (rootCat1?has_content)>
            <#list rootCat1 as root>
                {
                    "name": unescapeHtmlText("<#if root.name?exists>${root.name?js_string} [${root.id}]<#else>${root.id?js_string}</#if>"),
                    "id": "${root.id}",
                    "level": "${root.level}",
                    "type": "${root.type}",
                    "parent": "${root.parent}",
                    "isCatalog": "${root.isCatalog}",
                    "isCategoryType": "${root.isCategoryType}",

                },
            </#list>
        </#if>
    </#macro>];


    $(function () {
        jQuery('#gtreetable').gtreetable({
            'source': function (id, type, level) {
                return {
                    type: 'POST',
                    url: 'getCatalogCateTree',
                    data: {'id': id, 'type': 'category', 'level': level,'scope':'category'},
                    dataType: 'json',
                    error: function (XMLHttpRequest) {
                        alert(XMLHttpRequest.status + ': ' + XMLHttpRequest.responseText);
                    }
                }

            },
            'manyroots': true,
            'cache': 1,
            'language': 'zh-CN',
            'nodeIndent': 40,
            'types': {
                catalog: 'glyphicon glyphicon-th-list',
                category: 'glyphicon glyphicon-folder-open',
                product: 'glyphicon glyphicon-grain'
            },
            'onSave': function (oNode) {
                var parentType = oNode.getParentType();
                var relatedType = oNode.getRelatedType();
                var type = oNode.type;
                var seq = oNode.getSeq();
                var level = oNode.level;
                return {
                    type: 'POST',
                    url: !oNode.isSaved() ? 'catalogNodeCreate' : 'catalogNodeUpdate',
                    data: {
                        id: oNode.id,
                        parent: oNode.getParent(),
                        name: oNode.getName(),
                        position: oNode.getInsertPosition(),
                        related: oNode.getRelatedNodeId(),
                        parentType: parentType,
                        relatedType: relatedType,
                        type: type,
                        level: level,
                        seq: seq
                    },
                    dataType: 'json',
                    error: function (XMLHttpRequest) {
                        alert(XMLHttpRequest.status + ': ' + XMLHttpRequest.responseText);
                    }
                };
            },
            'onDelete': function (oNode) {
                return {
                    type: 'POST',
                    url: 'catalogNodeDelete',
                    data: {
                        id: oNode.getId(),
                        type: oNode.type
                    },
                    dataType: 'json',
                    error: function (XMLHttpRequest) {
                        alert(XMLHttpRequest.status + ': ' + XMLHttpRequest.responseText);
                    }
                };
            },
            defaultActions: [
                {
                    name: '详细页',
                    event: function (oNode, oManager) {
                        var id = oNode.getId();
                        if (oNode.type === 'catalog') {
                            location.href = './EditProdCatalog?prodCatalogId=' + id.substring(0, id.indexOf('_catalog'));
                        } else if (oNode.type == 'category') {
                            location.href = './EditCategory?productCategoryId=' + id.substring(0, id.indexOf('_category'));
                        } else if (oNode.type == 'product') {
                            location.href = './EditProduct?productId=' + id.substring(0, id.indexOf('_product'));
                        }
                    }
                },

                {
                    name: '${'$'}{createBefore}',
                    event: function (oNode, oManager) {
                        if (oNode.type === 'catalog') {
                            oNode.add('before', 'catalog');
                        } else if (oNode.type == 'category') {
                            oNode.add('before', 'category');
                        }
                    }
                },
                {
                    name: '${'$'}{createAfter}',
                    event: function (oNode, oManager) {
                        if (oNode.type === 'catalog') {

                            oNode.add('after', 'catalog');
                        } else if (oNode.type == 'category') {
                            oNode.add('after', 'category');
                        }
                    }
                },
                {
                    name: '${'$'}{createFirstChild}',
                    event: function (oNode, oManager) {
                        console.log(oNode);
//                        console.log(oNode.related);
                        if (oNode.type === 'catalog') {
                            /*$('#addCatalogCategoryForm').find($("input[name='type']")).val(oNode.type);
                            $('#addCatalogCategoryForm').find($("input[name='parent']")).val(oNode.parent);
                            $('#addCatalogCategoryForm').find($("input[name='level']")).val(oNode.level);
                            $('#addCatalogCategoryForm').find($("input[name='id']")).val(oNode.id);
                            $('#addCatalogCategoryForm').find($("input[name='position']")).val(oNode.getInsertPosition());
                            $('#addCatalogCategoryForm').find($("input[name='related']")).val(oNode.getRelatedNodeId());
                            $('#addCatalogCategoryForm').find($("input[name='parentType']")).val(oNode.getParentType());
                            $('#addCatalogCategoryForm').find($("input[name='relatedType']")).val(oNode.getRelatedType());
                            $('#addCatalogCategoryForm').find($("input[name='seq']")).val(oNode.seq);*/
//                            $('#modal-dialog').modal();
                            oNode.add('firstChild', 'category');
                        } else if (oNode.type == 'category') {
//                            $('#modal-dialog').modal();
                            oNode.add('firstChild', 'category');
                        }
                    }
                },
                {
                    name: '${'$'}{createLastChild}',
                    event: function (oNode, oManager) {
                        if (oNode.type === 'catalog') {
                            oNode.add('lastChild', 'category');
                        } else if (oNode.type == 'category') {
                            oNode.add('lastChild', 'category');
                        }
                    }
                },
                {
                    divider: true
                },
                {
                    name: '${'$'}{update}',
                    event: function (oNode, oManager) {
                        oNode.makeEditable();
                    }
                },
                {
                    name: '${'$'}{delete}',
                    event: function (oNode, oManager) {
                        if (confirm(oManager.language.messages.onDelete)) {
                            oNode.remove();
                        }
                    }
                }

            ]
        });
    })


</script>

<div class="modal fade" id="modal-dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">创建分类</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" name="addCatalogCategoryForm" id="addCatalogCategoryForm">
                <#--当前点击的节点的类型-->
                    <input type="hidden" name="type"/>
                <#--当前节点的上级节点 当时目录时parent:0-->
                    <input type="hidden" name="parent"/>
                <#--当前点击的节点level-->
                    <input type="hidden" name="level"/>
                <#--当前点击的节点对应的id_(catalog,category)-->
                    <input type="hidden" name="id"/>
                    <input type="hidden" name="action"/>
                    <input type="hidden" name="position"/>
                <#--分类名称-->
                    <input type="hidden" name="related"/>
                    <input type="hidden" name="parentType"/>
                    <input type="hidden" name="relatedType"/>
                    <input type="hidden" name="seq"/>
                    <div class="form-group m-b-10 m-r-5">
                        <label class="control-label col-md-4">新建分类</label>
                        <div class="col-md-7"><input type="text" name="name" class="form-control" placeholder="输入分类名称"/></div>
                    </div>
                    <div class="form-group m-b-10 m-r-5">
                        <label class="control-label col-md-4">(或者)查找分类标识</label>
                        <div class="col-md-7">
                        <@htmlTemplate.lookupField value='${requestParameters.productCategoryId?if_exists}' formName="addCatalogCategoryForm" name="targetId" id="productCategoryId" fieldFormName="LookupProductCategory"/>
                        </div>
                    </div>
                    <div class="form-group m-b-10 m-r-5">
                        <label class="control-label col-md-4">分类类型</label>
                        <div class="col-md-7">
                            <select class="form-control">
                            <#list prodCatalogCategoryTypes as categoryType>
                                <#assign  cateTypeId = StringUtil.wrapString(categoryType.get("prodCatalogCategoryTypeId") )>
                                <#if cateTypeId == 'PCCT_BROWSE_ROOT' || cateTypeId == 'PCCT_PROMOTIONS' || cateTypeId == 'PCCT_WHATS_NEW'||cateTypeId == 'PCCT_BEST_SELL'>
                                    <option value="${categoryType.get("prodCatalogCategoryTypeId")}">${categoryType.get("description",locale)}</option>
                                </#if>
                            </#list>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="javascript:" class="btn btn-sm btn-primary" data-dismiss="modal">取消</a>
                <a href="javascript:" class="btn btn-sm btn-success" id="addCategoryBtn">确定</a>
            </div>
        </div>
    </div>
</div>
<#--<div id="tree"></div>-->
<table class="table gtreetable" id="gtreetable">
    <thead>
    <tr>
        <th>浏览分类</th>
    </tr>
    </thead>
</table>
<script type="text/javascript">

    $(document).ready(function () {
        $("#addCategoryBtn").click(function () {
            var categoryName = $('#addCatalogCategoryForm').find($("input[name='name']")).val();
            var productCategoryId = $('#addCatalogCategoryForm').find($("input[name='targetId']")).val();
            if (categoryName) {
                $('#addCatalogCategoryForm').find($("input[name='action']")).val("0");
            } else if (productCategoryId) {
                $('#addCatalogCategoryForm').find($("input[name='action']")).val("1");
            }

            $.ajax({
                url: "<@ofbizUrl>catalogNodeCreate</@ofbizUrl>",
                type: "POST",
                data: $('#addCatalogCategoryForm').serialize(),
                success: function (data) {
                    console.log(data);
                }
            });
        });
    });
</script>
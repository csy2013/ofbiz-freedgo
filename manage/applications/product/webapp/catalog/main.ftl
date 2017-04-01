<#if !sessionAttributes.userLogin?exists>
<div class='label'> ${uiLabelMap.ProductGeneralMessage}.</div>
</#if>
<br/>
<#if security.hasEntityPermission("CATALOG", "_VIEW", session)>


<div class="btn-group">
    <button class="btn btn-primary btn-sm" onclick="document.location.href='<@ofbizUrl>UpdateAllKeywords</@ofbizUrl>'">${uiLabelMap.ProductAutoCreateKeywordsForAllProducts}</button>
    <button class="btn btn-default btn-sm" onclick="document.location.href='<@ofbizUrl>FastLoadCache</@ofbizUrl>'">${uiLabelMap.ProductFastLoadCatalogIntoCache}</button>
</div>
<br/><br/>
    <@htmlScreenTemplate.renderScreenletBegin id="" title="目录管理" collapsible="" showMore=true collapsed=false/>
<div class="row">
    <div class="col-md-12">
        <@htmlScreenTemplate.renderModalPage id="addProdCatalog" name="addProdCatalog" modalTitle="${StringUtil.wrapString(uiLabelMap.ProductCreateNewCatalog)}" modalUrl="EditProdCatalog1" description="${uiLabelMap.ProductCreateNewCatalog}"/>
        <#--<a href="<@ofbizUrl>EditProdCatalog</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductCreateNewCatalog}</a>-->
    </div>
</div>
<br/>
<form method="post" action="<@ofbizUrl>EditProdCatalog</@ofbizUrl>" class="form-inline" name="EditProdCatalogForm" collapsed=false>
    <div class="row">
        <div class="col-md-12 form-group">
            <div class="input-group m-b-7 m-r-5">
                <span class="input-group-addon">
                    <span>目录名:</span>
                </span>
                <input class="form-control input-sm" type="text" size="20" maxlength="20" name="prodCatalogId" value=""/>
                        <span class="input-group-addon">
                            <a onclick="document.forms['EditProdCatalogForm'].submit()">${uiLabelMap.ProductEditCatalog}</a>
                     </span>
            </div>
        </div>
    </div>
</form>
    <@htmlScreenTemplate.renderScreenletEnd/>

    <@htmlScreenTemplate.renderScreenletBegin id="" title="分类管理" collapsible="" showMore=true collapsed=false/>

<div class="row">
    <div class="col-md-12"><a href="<@ofbizUrl>EditCategory</@ofbizUrl>" class="btn btn-primary btn-sm">${uiLabelMap.ProductCreateNewCategory}</a></div>
</div>
<br/>


<form method="post" action="<@ofbizUrl>EditCategory</@ofbizUrl>"class="form-inline"  name="EditCategoryForm">
    <div class="row">
        <div class="col-md-12 input-group m-b-7 m-r-5">
                <span class="input-group-addon">
                     <span>分类:</span>
                </span>
            <@htmlTemplate.lookupField name="productCategoryId" id="productCategoryId" formName="EditCategoryForm" fieldFormName="LookupProductCategory"/>
            <span class="input-group-addon">
                            <a onclick="document.forms['EditCategoryForm'].submit()">${uiLabelMap.ProductEditCategory}</a>
                     </span>
        </div>
    </div>

</form>
    <@htmlScreenTemplate.renderScreenletEnd/>

    <@htmlScreenTemplate.renderScreenletBegin id="" title="产品管理" collapsible="" showMore=true collapsed=false/>
<div class="btn-group">
    <button name="" class="btn btn-primary btn-sm" onclick="document.href.location='<@ofbizUrl>EditProduct</@ofbizUrl>'">${uiLabelMap.ProductCreateNewProduct}</button>
    <button name="" class="btn btn-primary btn-sm"
            onclick="document.href.location='<@ofbizUrl>CreateVirtualWithVariantsForm</@ofbizUrl>'">${uiLabelMap.ProductQuickCreateVirtualFromVariants}</button>

</div>
<br/><br/>

<form method="post" action="<@ofbizUrl>EditProduct</@ofbizUrl>" class="form-inline"  name="EditProductForm">
    <div class="row">
        <div class="col-md-12 input-group m-b-7 m-r-5">
                <span class="input-group-addon">
                     <span>产品:</span>
                </span>
            <@htmlTemplate.lookupField name="productId" id="productId" formName="EditProductForm" fieldFormName="LookupProduct"/>
            <span class="input-group-addon">
             <a onclick="document.forms['EditProduct'].submit()">编辑产品</a>
             </span>
        </div>
    </div>
</form>


</#if>

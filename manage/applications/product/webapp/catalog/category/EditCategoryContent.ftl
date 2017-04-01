<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductOverrideSimpleFields}"/>

        <form action="<@ofbizUrl>updateCategoryContent</@ofbizUrl>" method="post" class="form-horizontal" name="categoryForm">
            <input type="hidden" name="productCategoryId" value="${productCategoryId?if_exists}" />
                <div class="form-group">
                    <label class="control-label col-md-4">${uiLabelMap.ProductProductCategoryType}</label>
                     <div class="col-md-4 ">
                        <select name="productCategoryTypeId" class="form-control">
                        <option value="">&nbsp;</option>
                        <#list productCategoryTypes as productCategoryTypeData>
                            <option <#if productCategory?has_content><#if productCategory.productCategoryTypeId==productCategoryTypeData.productCategoryTypeId> selected="selected"</#if></#if> value="${productCategoryTypeData.productCategoryTypeId}">${productCategoryTypeData.get("description",locale)}</option>
                        </#list>
                        </select>
                     </div>
                    </div>
                
                <div class="form-group"> 
                    <label class="control-label col-md-4">${uiLabelMap.ProductName}</label>
                    <div class="col-md-4 ">
                        <input type="text" class="form-control" value="${(productCategory.categoryName)?if_exists}" name="categoryName" size="60" maxlength="60"/>
                    </div>
                </div> 
                <div class="form-group"> 
                    <label class="control-label col-md-4">${uiLabelMap.ProductCategoryDescription}</label>
                    <div class="col-md-4 ">
                        <textarea name="description" class="form-control" cols="60" rows="2">${(productCategory.description)?if_exists}</textarea>
                     </div>
                </div> 
                <div class="form-group">
                    <label class="control-label col-md-4">${uiLabelMap.ProductLongDescription}</label>
                    <div class="col-md-4 ">
                        <textarea name="longDescription" class="form-control" cols="60" rows="7">${(productCategory.longDescription)?if_exists}</textarea>
                    </div>
                </div> 
                <div class="form-group"> 
                    <label class="control-label col-md-4">${uiLabelMap.ProductDetailScreen}</label>
                    <div class="col-md-4 ">
                        <input type="text" <#if productCategory?has_content>value="${productCategory.detailScreen?if_exists}"</#if> name="detailScreen" size="60" maxlength="250" />
                        <br />
                        <span class="tooltip">${uiLabelMap.ProductDefaultsTo} &quot;categorydetail&quot;, ${uiLabelMap.ProductDetailScreenMessage}: &quot;component://ecommerce/widget/CatalogScreens.xml#categorydetail&quot;</span>
                    </div>
                </div> 
                <div class="form-group">
                    <div class="col-md-4">&nbsp;</div>
                    <div class="col-md-4 "><input type="submit" class="btn btn-primary btn-sm" name="Update" value="${uiLabelMap.CommonUpdate}" /></div>
                </div> 

        </form>
<@htmlScreenTemplate.renderScreenletEnd/>
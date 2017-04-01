
<form action="<@ofbizUrl>quickCreateVirtualWithVariants</@ofbizUrl>" method="post" name="quickCreateVirtualWithVariants" class="form-horizontal">

    <div class="form-group">
        <label class="control-label col-md-3">${uiLabelMap.ProductVariantProductIds}:</label>
        <div class="col-md-5 ">
            <textarea name="variantProductIdsBag" class="form-control" rows="6" cols="20"></textarea>
        </div>
    </div>

    <div class="form-group">
            <label class="control-label col-md-3">危险品:</label>
            <div class="col-md-5 ">
            <select name="productFeatureIdOne" class="form-control">
                <option value="">- ${uiLabelMap.CommonNone} -</option>
                <#list hazmatFeatures as hazmatFeature>
                    <option value="${hazmatFeature.productFeatureId}">${hazmatFeature.description}</option>
                </#list>
            </select>
            </div>
        </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5  pull-right">
            <input type="submit" value="${uiLabelMap.ProductCreateVirtualProduct}" class="btn btn-primary btn-sm"/>
        </div>
    </div>

</form>
<form method="post" action="<@ofbizUrl>CreateProductFeatureGroup</@ofbizUrl>" class="form-horizontal" data-parsley-validate="true">
    <div class="form-group">
    <label class="col-md-3 control-label">${uiLabelMap.CommonDescription}:</label>
      <div class="col-md-5">
          <input type="text" class="form-control" size='30' name='description' value='' data-parsley-required="true"/>
      </div>
    </div>
    <div class="form-group">
        <div class="col-md-3">&nbsp;</div>
        <div class="col-md-5">
            <input type="submit" value="${uiLabelMap.CommonCreate}" />
        </div>
    </div>
</form>
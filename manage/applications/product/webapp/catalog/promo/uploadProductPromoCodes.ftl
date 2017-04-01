<form method="post" action="<@ofbizUrl>createBulkProductPromoCode</@ofbizUrl>" class="form-horizontal" enctype="multipart/form-data">
    <input type="hidden" name="productPromoId" value="${productPromoId}"/>
    <input value="1" name="useLimitPerCode" type="hidden"/>
    <input value="1" name="useLimitPerCustomer" type="hidden"/>

    <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.ProductPromoUserEntered}:</label>

        <div class="col-md-7">
            <div class="input-group">
                <select class="form-control input-sm" name="userEntered">
                    <option value="Y">${uiLabelMap.CommonY}</option>
                    <option value="N">${uiLabelMap.CommonN}</option>

                </select>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.ProductPromotionReqEmailOrParty}:</label>

        <div class="col-md-7">
            <div class="input-group">
                <select class="form-control input-sm" name="requireEmailOrParty">
                    <option value="N">${uiLabelMap.CommonN}</option>
                    <option value="Y">${uiLabelMap.CommonY}</option>

                </select>
            </div>
        </div>
    </div>
    <div class="from-group">
        <label class="col-md-5 control-label">上传文件:</label>

        <div class="col-md-7">
            <div class="input-group">
                <input type="file" size="40" name="uploadedFile"/>
            </div>
        </div>
    </div>

    <div class="form-group">
        <label class="col-md-5 control-label"></label>

        <div class="col-md-7">
            <input type="submit" value="${uiLabelMap.CommonUpload}"/>
        </div>
    </div>


</form>



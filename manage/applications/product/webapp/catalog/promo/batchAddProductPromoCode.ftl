<form method="post" action="<@ofbizUrl>createProductPromoCodeSet</@ofbizUrl>" class="form-horizontal">
    <input type="hidden" name="productPromoId" value="${productPromoId}"/>
    <input value="1" name="useLimitPerCode" type="hidden"/>
    <input value="1" name="useLimitPerCustomer" type="hidden"/>

    <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.CommonQuantity}:</label>

        <div class="col-md-7">
            <div class="input-group">
                <input class="form-control input-sm" name="quantity" type="text" size="5"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.ProductPromoCodeLength}:</label>

        <div class="col-md-7">
            <div class="input-group">
                <input class="form-control input-sm" name="codeLength" type="text" size="4"/>
            </div>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 control-label">${uiLabelMap.ProductPromoCodeLayout}:</label>

        <div class="col-md-7">
            <div class="input-group">
                <select class="form-control input-sm" name="promoCodeLayout">
                    <option value="smart">${uiLabelMap.ProductPromoLayoutSmart}</option>
                    <option value="normal">${uiLabelMap.ProductPromoLayoutNormal}</option>
                    <option value="sequence">${uiLabelMap.ProductPromoLayoutSeqNum}</option>
                </select>
            </div>
        </div>
    </div>

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

    <div class="form-group">
        <label class="col-md-5 control-label"></label>

        <div class="col-md-7">
            <input type="submit" value="${uiLabelMap.CommonAdd}"/>
        </div>
    </div>


</form>

<#if !mechMap.productStoreContactMech?exists && mechMap.contactMech?exists>
<p><h3>${uiLabelMap.PartyContactInfoNotBelongToYou}.</h3></p>
&nbsp;<a href="<@ofbizUrl>authview/${donePage}?productStoreId=${productStoreId}</@ofbizUrl>" class="buttontext">${uiLabelMap.CommonGoBack}</a>
<#else>
    <#if !mechMap.contactMech?exists>
    <#-- When creating a new contact mech, first select the type, then actually create -->
        <#if !preContactMechTypeId?has_content>
        <#--<h1>${title}</h1>-->
        <form method="post" action='<@ofbizUrl>EditContactMech</@ofbizUrl>' name="createcontactmechform" class="form-horizontal">
            <input type='hidden' name='productStoreId' value='${productStoreId}'/>
            <input type='hidden' name='DONE_PAGE' value='${donePage?if_exists}'/>

            <div class="form-group">
                <label class="col-md-3">${uiLabelMap.PartySelectContactType}</div>
            <div class="col-md-5">
                <select name="preContactMechTypeId" class="form-control">
                    <#list mechMap.contactMechTypes as contactMechType>
                        <option value='${contactMechType.contactMechTypeId}'>${contactMechType.get("description",locale)}</option>
                    </#list>
                </select>&nbsp;<a href="javascript:document.createcontactmechform.submit()" class="buttontext">${uiLabelMap.CommonCreate}</a>
            </div>

        </form>
        </#if>
    </#if>

    <#if mechMap.contactMechTypeId?has_content>
        <#if !mechMap.contactMech?has_content>
        <#--<h1>${title}</h1>-->
        <#--  <#if contactMechPurposeType?exists>
          <div><span class="label">(${uiLabelMap.PartyMsgContactHavePurpose}</span>"${contactMechPurposeType.get("description",locale)?if_exists}")</div>
          </#if>-->

        <form method="post" action='<@ofbizUrl>${mechMap.requestName}</@ofbizUrl>' name="editcontactmechform" id="editcontactmechform" class="form-horizontal" data-parsley-validate="true">
            <input type='hidden' name='DONE_PAGE' value='${donePage}'/>
            <input type='hidden' name='contactMechTypeId' value='${mechMap.contactMechTypeId}'/>
            <input type='hidden' name='productStoreId' value='${productStoreId}'/>
            <#if preContactMechTypeId?exists><input type='hidden' name='preContactMechTypeId' value='${preContactMechTypeId}'/></#if>
            <#if contactMechPurposeTypeId?exists><input type='hidden' name='contactMechPurposeTypeId' value='${contactMechPurposeTypeId?if_exists}'/></#if>
            <#if paymentMethodId?exists><input type='hidden' name='paymentMethodId' value='${paymentMethodId}'/></#if>
        <#--<div class="form-group">
            <label class="col-md-3">${uiLabelMap.PartyContactPurposes}</label>
            <div class="col-md-5">
                <select name='contactMechPurposeTypeId' class="form-control">
                    <option></option>
                    <#list mechMap.purposeTypes as contactMechPurposeType>
                        <option value='${contactMechPurposeType.contactMechPurposeTypeId}' <#if contactMechPurposeType==contactMechPurposeType.contactMechPurposeTypeId>selected</#if>>${contactMechPurposeType.get("description",locale)}</option>
                    </#list>
                </select>
                *
            </div>
        </div>-->
        <#else>
        <#--<h1>${title}</h1>-->
            <#if mechMap.purposeTypes?has_content>
            <div class="form-group">
            <#--<label class="col-md-3">${uiLabelMap.PartyContactPurposes}</label>-->
            <div class="col-md-11">
            <#--<#if mechMap.productStoreContactMechPurposes?has_content>
                <#assign alt_row = false>
                <#list mechMap.productStoreContactMechPurposes as productStoreContactMechPurpose>
                        <#assign contactMechPurposeType = productStoreContactMechPurpose.getRelatedOneCache("ContactMechPurposeType")>
                        <div class="col-md-5">
                                <#if contactMechPurposeType?has_content>
                                    <b>${contactMechPurposeType.get("description",locale)}</b>
                                <#else>
                                    <b>${uiLabelMap.PartyMechPurposeTypeNotFound}: "${productStoreContactMechPurpose.contactMechPurposeTypeId}"</b>
                                </#if>
                                (${uiLabelMap.CommonSince}: ${productStoreContactMechPurpose.fromDate})
                                <#if productStoreContactMechPurpose.thruDate?has_content>(${uiLabelMap.CommonExpires}: ${productStoreContactMechPurpose.thruDate.toString()}</#if>
                                <a href="javascript:document.getElementById('deleteProductStoreContactMechPurpose_${productStoreContactMechPurpose_index}').submit();"
                                   class="buttontext">${uiLabelMap.CommonDelete}</a>
                        </div>
                    </div>
                </div>
                &lt;#&ndash; toggle the row color &ndash;&gt;
                    <#assign alt_row = !alt_row>
                    <form id="deleteProductStoreContactMechPurpose_${productStoreContactMechPurpose_index}" method="post"
                          action="<@ofbizUrl>deleteProductStoreContactMechPurpose</@ofbizUrl>" class="form-horizontal">
                        <input type="hidden" name="productStoreId" value="${productStoreId?if_exists}"/>
                        <input type="hidden" name="contactMechId" value="${contactMechId?if_exists}"/>
                        <input type="hidden" name="contactMechPurposeTypeId" value="${(productStoreContactMechPurpose.contactMechPurposeTypeId)?if_exists}"/>
                        <input type="hidden" name="fromDate" value="${(productStoreContactMechPurpose.fromDate)?if_exists}"/>
                        <input type="hidden" name="DONE_PAGE" value="${donePage?if_exists}"/>
                        <input type="hidden" name="useValues" value="true"/>
                    </form>
                </#list>
            </#if>-->
            <#-- <div class="form-group">
                 <div class="col-md-5">
                     <form method="post" action='<@ofbizUrl>createProductStoreContactMechPurpose?DONE_PAGE=${donePage}&amp;useValues=true</@ofbizUrl>' name='newpurposeform'>
                         <input type="hidden" name='productStoreId' value='${productStoreId}'/>
                         <input type="hidden" name='contactMechId' value='${contactMechId?if_exists}'/>
                         <select name='contactMechPurposeTypeId' class="form-control">
                             <option></option>
                             <#list mechMap.purposeTypes as contactMechPurposeType>
                                 <option value='${contactMechPurposeType.contactMechPurposeTypeId}'>${contactMechPurposeType.get("description",locale)}</option>
                             </#list>
                         </select>
                         &nbsp;<a href='javascript:document.newpurposeform.submit()' class='buttontext'>${uiLabelMap.PartyAddPurpose}</a>
                     </form>
                 </div>
             </div>-->

            </#if>
        <form method="post" action='<@ofbizUrl>${mechMap.requestName}</@ofbizUrl>' name="editcontactmechform" id="editcontactmechform" class="form-horizontal" data-parsley-validate="true" >
            <input type="hidden" name="contactMechId" value='${contactMechId}'/>
            <input type="hidden" name="contactMechTypeId" value='${mechMap.contactMechTypeId}'/>
            <input type="hidden" name='productStoreId' value='${productStoreId}'/>
        </#if>
        <#if "POSTAL_ADDRESS" = mechMap.contactMechTypeId?if_exists>
            <input type="hidden" name="countryGeoId" id="editcontactmechform_countryGeoId" value="CHN"/>
        <#--<div class="form-group">
            <label class="col-md-3">${uiLabelMap.CommonCountry}</label>
            <div class="col-md-5">
               &lt;#&ndash; <select name="countryGeoId" id="editcontactmechform_countryGeoId" class="form-control">
                ${screens.render("component://common/widget/CommonScreens.xml#countries")}
                    <#if (mechMap.postalAddress?exists) && (mechMap.postalAddress.countryGeoId?exists)>
                        <#assign defaultCountryGeoId = mechMap.postalAddress.countryGeoId>
                    <#else>
                        <#assign defaultCountryGeoId = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("general.properties", "country.geo.id.default")>
                    </#if>
                    <option selected="selected" value="${defaultCountryGeoId}">
                        <#assign countryGeo = delegator.findByPrimaryKey("Geo",Static["org.ofbiz.base.util.UtilMisc"].toMap("geoId",defaultCountryGeoId))>
                            ${countryGeo.get("geoName",locale)}
                    </option>
                </select>&ndash;&gt;
                <input type="hidden" name="countryGeoId" value="CHN"/>
            </div>
        </div>-->
            <div class="form-group">
                <label class="col-md-3">${uiLabelMap.PartyState}</label>

                <div class="col-md-5">
                    <select name="stateProvinceGeoId" id="editcontactmechform_stateProvinceGeoId" class="form-control" data-parsley-required="true">
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3">市</label>

                <div class="col-md-5">
                    <select name="city" id="editcontactmechform_city" class="form-control" data-parsley-required="true">
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3">区/县</label>

                <div class="col-md-5">
                    <select name="countyGeoId" id="editcontactmechform_countyGeoId" class="form-control" data-parsley-required="true">
                    </select>
                <#--<input type="text" class="form-control" size="30" maxlength="30" name="city" value="${(mechMap.postalAddress.city)?default(request.getParameter('city')?if_exists)}"
                       class="form-control"/>-->

                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3">${uiLabelMap.PartyToName}</label>

                <div class="col-md-5">
                    <input type="text" class="form-control" size="30" maxlength="60" name="toName"
                           value="${(mechMap.postalAddress.toName)?default(request.getParameter('toName')?if_exists)}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3">${uiLabelMap.PartyAttentionName}</label>

                <div class="col-md-5">
                    <input type="text" class="form-control" size="30" maxlength="60" name="attnName"
                           value="${(mechMap.postalAddress.attnName)?default(request.getParameter('attnName')?if_exists)}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3">${uiLabelMap.PartyAddressLine1}</label>

                <div class="col-md-5">
                    <input type="text" class="form-control" size="30" maxlength="30" name="address1"
                           value="${(mechMap.postalAddress.address1)?default(request.getParameter('address1')?if_exists)}" data-parsley-required="true"/>

                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3">${uiLabelMap.PartyAddressLine2}</label>

                <div class="col-md-5">
                    <input type="text" class="form-control" size="30" maxlength="30" name="address2"
                           value="${(mechMap.postalAddress.address2)?default(request.getParameter('address2')?if_exists)}"/>
                </div>
            </div>



            <div class="form-group">
                <label class="col-md-3">${uiLabelMap.PartyZipCode}</label>

                <div class="col-md-5">
                    <input type="text" class="form-control" size="12" maxlength="10" name="postalCode"
                           value="${(mechMap.postalAddress.postalCode)?default(request.getParameter('postalCode')?if_exists)}" data-parsley-required="true"/>

                </div>
            </div>

        <#elseif "TELECOM_NUMBER" = mechMap.contactMechTypeId?if_exists>
            <div class="alert alert-warning fade in m-b-15">填写说明：<br>1)固定电话填写区号<br>2)移动电话直接填写联系号码</div>
            <div class="form-group">
                <label class="col-md-3 control-label">地区代码</label>
                <div class="col-md-5">
                    <input type="hidden" class="form-control" size="4" maxlength="10" name="countryCode"
                           value="${(mechMap.telecomNumber.countryCode)?default(request.getParameter('countryCode')?default("68"))}"/>
                    <input type="text" class="form-control" size="4" maxlength="10" name="areaCode"
                           value="${(mechMap.telecomNumber.areaCode)?default(request.getParameter('areaCode')?if_exists)}"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label">${uiLabelMap.PartyContactNumber}</label>

                <div class="col-md-5">

                    <input type="text" class="form-control" size="11" maxlength="15" name="contactNumber" data-parsley-required="true"
                                  value="${(mechMap.telecomNumber.contactNumber)?default(request.getParameter('contactNumber')?if_exists)}" />
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-3 control-label">${uiLabelMap.PartyExtension}</label>

                <div class="col-md-5">

                    <input class="form-control" type="text" size="6" maxlength="10" name="extension"
                                         value="${(mechMap.productStoreContactMech.extension)?default(request.getParameter('extension')?if_exists)}"/>
                </div>
            </div>

        <#--<#elseif "EMAIL_ADDRESS" = mechMap.contactMechTypeId?if_exists>
            <div class="form-group">
                <label class="col-md-3">${uiLabelMap.PartyEmailAddress}</div>
                <div class="col-md-5">
                    <input type="text" class="required" size="60" maxlength="255" name="emailAddress"
                           value="${(mechMap.contactMech.infoString)?default(request.getParameter('emailAddress')?if_exists)}"/>
                    *
                </div>
            </div>-->
        <#else>
            <div class="form-group">
                <label class="col-md-3">${mechMap.contactMechType.get("description",locale)}</label>

                <div class="col-md-5">
                    <input type="text" class="form-control" size="60" maxlength="255" name="infoString" value="${(mechMap.contactMech.infoString)?if_exists}"/>
                    *
                </div>
            </div>
        </#if>
        <div class="form-group">
            <div class="col-md-3">&nbsp;</div>
            <div class="col-md-5 pull-right">
                <input type="submit" class="btn btn-primary btn-sm" value="保存">
            </div>
        </div>
    </form>

    </#if>
</#if>

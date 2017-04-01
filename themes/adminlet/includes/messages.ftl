<#escape x as x?html>
  <#if requestAttributes.errorMessageList?has_content><#assign errorMessageList=requestAttributes.errorMessageList></#if>
  <#if requestAttributes.eventMessageList?has_content><#assign eventMessageList=requestAttributes.eventMessageList></#if>
  <#if requestAttributes.serviceValidationException?exists><#assign serviceValidationException = requestAttributes.serviceValidationException></#if>
  <#if requestAttributes.uiLabelMap?has_content><#assign uiLabelMap = requestAttributes.uiLabelMap></#if>

  <#if !errorMessage?has_content>
    <#assign errorMessage = requestAttributes._ERROR_MESSAGE_?if_exists>
  </#if>
  <#if !errorMessageList?has_content>
    <#assign errorMessageList = requestAttributes._ERROR_MESSAGE_LIST_?if_exists>
  </#if>
  <#if !eventMessage?has_content>
    <#assign eventMessage = requestAttributes._EVENT_MESSAGE_?if_exists>
  </#if>
  <#if !eventMessageList?has_content>
    <#assign eventMessageList = requestAttributes._EVENT_MESSAGE_LIST_?if_exists>
  </#if>

<#-- display the error messages -->
  <#if (errorMessage?has_content || errorMessageList?has_content)>
  <div class="row">
      <div class="col-md-12">
          <div class="">
              <div class="box-body">
                  <div class="alert alert-danger alert-dismissable">
                      <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                      <h4><i class="icon fa fa-ban"></i> ${StringUtil.wrapString(uiLabelMap.CommonFollowingErrorsOccurred)}:</h4>
                    <#if errorMessage?has_content>
                    ${StringUtil.wrapString(errorMessage)}
                    </#if>
                    <#if errorMessageList?has_content>
                      <#list errorMessageList as errorMsg>
                      ${StringUtil.wrapString(errorMsg)}
                      </#list>
                    </#if>
                  </div>
              </div>
          </div>
      </div>
  </div>
  </#if>

<#-- display the event messages -->
  <#if (eventMessage?has_content || eventMessageList?has_content)>
  <div class="row">
      <div class="col-md-12">
          <div class="">
              <div class="box-body">
                  <div class="alert alert-info alert-dismissable">
                      <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                      <h4><i class="icon fa fa-info"></i> ${StringUtil.wrapString(uiLabelMap.CommonFollowingOccurred)}:</h4>
                    <#if eventMessage?has_content>
                    ${StringUtil.wrapString(eventMessage)}
                    </#if>
                    <#if eventMessageList?has_content>
                      <#list eventMessageList as eventMsg>
                      ${StringUtil.wrapString(eventMsg)}
                      </#list>
                    </#if>
                  </div>
              </div>
          </div>
      </div>
  </div>
  </#if>
</#escape>

<script src="/images/markjs/jquery.mark.min.js" type="text/javascript"></script>
<style>
    mark {
        background: orange;
        color: inherit;
        padding: 0;
    }

</style>
<#assign url='ServiceList'>
<#assign popupUrl='serviceEcaDetail'>

<#-- Selected Service is available -->
<#if selectedServiceMap?exists>
    <#if showWsdl?exists && showWsdl = true>
    <div class="screenlet">
        <div class="screenlet-title-bar">
            <h3>${uiLabelMap.WebtoolsServiceWSDL} - ${uiLabelMap.WebtoolsService} ${selectedServiceMap.serviceName}</h3>
        </div>
        <div class="screenlet-body" align="center">
            <form><textarea rows="20" cols="85" name="wsdloutput">${selectedServiceMap.wsdl}</textarea></form>
            <br/>
            <a href='<@ofbizUrl>${url}?sel_service_name=${selectedServiceMap.serviceName}</@ofbizUrl>' class='smallSubmit'>${uiLabelMap.CommonBack}</a>
        </div>
    </div>
    <#else>
        <@htmlScreenTemplate.renderScreenletBegin id="findOrders" title="${uiLabelMap.WebtoolsServiceName}: ${selectedServiceMap.serviceName}" collapsed=false/>


    <div class="pull-right">
        <a href='<@ofbizUrl>${url}</@ofbizUrl>'>${uiLabelMap.CommonListAll}</a></li>
        <a href='<@ofbizUrl>/scheduleJob?SERVICE_NAME=${selectedServiceMap.serviceName}</@ofbizUrl>'>${uiLabelMap.WebtoolsSchedule}</a></li>
        <a href='<@ofbizUrl>/setSyncServiceParameters?SERVICE_NAME=${selectedServiceMap.serviceName}&amp;POOL_NAME=pool&amp;_RUN_SYNC_=Y</@ofbizUrl>'>${uiLabelMap.PageTitleRunService}</a>
    </div>


    <table class="table">
        <tr>
            <th>${uiLabelMap.WebtoolsServiceName}:</th>
            <th>${selectedServiceMap.serviceName}</th>
            <th>${uiLabelMap.WebtoolsEngineName}:</th>
            <th><a href='<@ofbizUrl>${url}?constraint=engine_name@${selectedServiceMap.engineName}</@ofbizUrl>'>${selectedServiceMap.engineName}</a></th>
        </tr>
        <tr>
            <th>${uiLabelMap.CommonDescription}:</th>
            <td>${selectedServiceMap.description}</td>
            <th>${uiLabelMap.WebtoolsInvoke}:</th>
            <td>${selectedServiceMap.invoke}</td>
        </tr>
        <tr>
            <th>${uiLabelMap.WebtoolsExportable}:</th>
            <td>${selectedServiceMap.export}<#if selectedServiceMap.exportBool = "true">&nbsp;(<a
                    href='<@ofbizUrl>${url}?sel_service_name=${selectedServiceMap.serviceName}&amp;show_wsdl=true</@ofbizUrl>'>${uiLabelMap.WebtoolsShowShowWSDL}</a>)</#if>
            </td>
            <th>${uiLabelMap.WebtoolsLocation}:</th>
            <td><a href='<@ofbizUrl>${url}?constraint=location@${selectedServiceMap.location}</@ofbizUrl>'>${selectedServiceMap.location}</a></td>
        </tr>
        <tr>
            <th>${uiLabelMap.WebtoolsArtifactInfo}:</th>
            <td><a href='<@ofbizUrl>ArtifactInfo?name=${selectedServiceMap.serviceName}&amp;type=service</@ofbizUrl>'>${uiLabelMap.WebtoolsArtifactInfo}</a></td>
            <th>${uiLabelMap.WebtoolsDefaultEntityName}:</th>
            <td>
                <a href='<@ofbizUrl>${url}?constraint=default_entity_name@${selectedServiceMap.defaultEntityName}</@ofbizUrl>'>${selectedServiceMap.defaultEntityName}</a>
            </td>
        </tr>
        <tr>

            <th>${uiLabelMap.WebtoolsRequireNewTransaction}:</th>
            <td>${selectedServiceMap.requireNewTransaction}</td>
            <th>${uiLabelMap.WebtoolsUseTransaction}:</th>
            <td>${selectedServiceMap.useTrans}</td>
        </tr>
        <tr>

        <tr>

            <th>${uiLabelMap.WebtoolsMaxRetries}:</th>
            <td>${selectedServiceMap.maxRetry}</td>
            <td colspan="2">&nbsp;</td>
        </tr>
    </table>

    <div class="panel-heading-title">
        <h4>${uiLabelMap.SecurityGroups}:<#if selectedServiceMap.permissionGroups == 'NA'>NA</#if></h4>
    </div>
        <#if selectedServiceMap.permissionGroups != 'NA'>
        <table class="table ">
            <tr>
                <td>${uiLabelMap.WebtoolsNameOrRole}</td>
                <td>${uiLabelMap.WebtoolsPermissionType}</td>
                <td>${uiLabelMap.WebtoolsAction}</td>
            </tr>
            <#list selectedServiceMap.permissionGroups as permGrp>
                <tr>
                    <td>${permGrp.nameOrRole?default(uiLabelMap.CommonNA)}</td>
                    <td>${permGrp.permType?default(uiLabelMap.CommonNA)}</td>
                    <td>${permGrp.action?default(uiLabelMap.CommonNA)}</td>
                </tr>
            </#list>
        </table>
        </#if>
    <div class="panel-heading-title">
        <h4>${uiLabelMap.WebtoolsImplementedServices}:<#if selectedServiceMap.implServices == 'NA'><b>${selectedServiceMap.implServices}</b></#if></h4>
    </div>
        <#if selectedServiceMap.implServices == 'NA'>
        <#elseif selectedServiceMap.implServices?has_content>
            <#list selectedServiceMap.implServices as implSrv>
            <a href='<@ofbizUrl>${url}?sel_service_name=${implSrv.getService()}</@ofbizUrl>'>${implSrv.getService()}</a><br/>
            </#list>
        </#if>
    <#-- If service has ECA's -->
        <#if ecaMapList?exists && ecaMapList?has_content>
        <#-- add the javascript for modalpopup's -->
        <script language='javascript' type='text/javascript'>
            function detailsPopup(viewName) {
                var lookupWinSettings = 'top=50,left=50,width=600,height=300,scrollbars=auto,status=no,resizable=no,dependent=yes,alwaysRaised=yes';
                var params = '';
                var lookupWin = window.open(viewName, params, lookupWinSettings);
                if (lookupWin.opener == null) lookupWin.opener = self;
                lookupWin.focus();
            }
        </script>
        <div class="screenlet">
            <div class="screenlet-title-bar">
                <h3>${uiLabelMap.WebtoolsServiceECA}</h3>
            </div>
        <table class="basic-table" cellspacing='0'>
            <tr class="header-row">
                <td>${uiLabelMap.WebtoolsEventName}</td>
                <#if ecaMapList.runOnError?exists>
                    <td>${uiLabelMap.WebtoolsRunOnError}</td>
                </#if>
                <#if ecaMapList.runOnFailure?exists>
                    <td>${uiLabelMap.WebtoolsRunOnFailure}</td>
                </#if>
                <td>${uiLabelMap.WebtoolsActions}</td>
                <td>${uiLabelMap.WebtoolsConditions}</td>
                <td>${uiLabelMap.WebtoolsSet}</td>
            </tr>
            <#list ecaMapList as ecaMap>
            <tr>
                <td>${ecaMap.eventName?if_exists}</td>
                <#if ecaMap.runOnError?exists>
                    <td>${ecaMap.runOnError}</div></td>
                </#if>
                <#if ecaMap.runOnFailure?exists>
                <td>${ecaMap.runOnFailure}</div></td>
                </#if>
                <#if ecaMap.actions?has_content>
                <td>
                    <#list ecaMap.actions as action>
                        <table class="basic-table" cellspacing='0'>
                            <tr>
                                <td colspan="2"><a
                                        href='<@ofbizUrl>${url}?sel_service_name=${action.serviceName}</@ofbizUrl>'>${action.serviceName?default(uiLabelMap.CommonNA)}</a></td>
                            </tr>
                            <tr>
                                <td><b>${uiLabelMap.WebtoolsSecasIgnoreError}</b> ${action.ignoreError?default(uiLabelMap.CommonNA)}</td>
                                <td><b>${uiLabelMap.WebtoolsSecasIgnoreFailure}</b> ${action.ignoreFailure?default(uiLabelMap.CommonNA)}</td>
                            </tr>
                            <tr>
                                <td><b>${uiLabelMap.WebtoolsSecasPersist}</b> ${action.persist?default(uiLabelMap.CommonNA)}</td>
                                <td><b>${uiLabelMap.WebtoolsSecasResultMapName}</b> ${action.resultMapName?default(uiLabelMap.CommonNA)}</td>
                            </tr>
                            <tr>
                                <td><b>${uiLabelMap.WebtoolsSecasResultToContext}</b> ${action.resultToContext?default(uiLabelMap.CommonNA)}</td>
                                <td><b>${uiLabelMap.WebtoolsSecasResultToResult}</b> ${action.resultToResult?default(uiLabelMap.CommonNA)}</td>
                            </tr>
                            <tr>
                                <td><b>${uiLabelMap.WebtoolsSecasServiceMode}</b> ${action.serviceMode?default(uiLabelMap.CommonNA)}</td>
                                <td colspan="2">&nbsp;</td>
                            </tr>
                        </table>
                    </#list>
                </td>
                </#if>
                <#if ecaMap.conditions?has_content>
                <td>
                    <#list ecaMap.conditions as condition>
                        <table class='basic-table' cellspacing='0'>
                            <tr>
                                <td><b>${uiLabelMap.WebtoolsCompareType}</b> ${condition.compareType?default(uiLabelMap.CommonNA)}</td>
                                <td>
                                    <b>${uiLabelMap.WebtoolsConditionService}</b>
                                    <#if condition.conditionService?has_content>
                                        <a href='<@ofbizUrl>${url}?sel_service_name=${condition.conditionService}</@ofbizUrl>'>${condition.conditionService?default(uiLabelMap.CommonNA)}</a>
                                    <#else>
                                    ${condition.conditionService?default(uiLabelMap.CommonNA)}
                                    </#if>
                                </td>
                                <td><b>${uiLabelMap.WebtoolsFormat}</b> ${condition.format?default(uiLabelMap.CommonNA)}</td>
                            </tr>
                            <tr>
                                <td><b>${uiLabelMap.WebtoolsIsService}</b> ${condition.isService?default(uiLabelMap.CommonNA)}</td>
                                <td><b>${uiLabelMap.WebtoolsIsConstant}</b> ${condition.isConstant?default(uiLabelMap.CommonNA)}</td>
                                <td><b>${uiLabelMap.WebtoolsOperator}</b> ${condition.operator?default(uiLabelMap.CommonNA)}</td>
                            </tr>
                            <tr>
                                <td><b>${uiLabelMap.WebtoolsLHSMapName}</b> ${condition.lhsMapName?default(uiLabelMap.CommonNA)}</td>
                                <td><b>${uiLabelMap.WebtoolsLHSValueName}</b> ${condition.lhsValueName?default(uiLabelMap.CommonNA)}</td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <td><b>${uiLabelMap.WebtoolsRHSMapName}</b> ${condition.rhsMapName?default(uiLabelMap.CommonNA)}</td>
                                <td><b>${uiLabelMap.WebtoolsRHSValueName}</b> ${condition.rhsValueName?default(uiLabelMap.CommonNA)}</td>
                                <td>&nbsp;</td>
                            </tr>
                        </table>
                        <br/>
                    </#list>
                </td>
                </#if>
                <#if ecaMap.sets?has_content>
                <td>
                    <#list ecaMap.sets as set>
                        <table class='basic-table' cellspacing='0'>
                            <tr>
                                <td><b>${uiLabelMap.WebtoolsFieldName}</b> ${set.fieldName?default(uiLabelMap.CommonNA)}</td>
                                <td colspan="2">&nbsp;</td>
                            </tr>
                            <tr>
                                <#if set.envName?has_content>
                                    <td><b>${uiLabelMap.WebtoolsEnvName}</b> ${set.envName}</td>
                                    <td colspan="2">&nbsp;</td>
                                </#if>
                            </tr>
                            <tr>
                                <#if set.value?has_content>
                                    <td><b>${uiLabelMap.CommonValue}</b> ${set.value}</td>
                                    <td colspan="2">&nbsp;</td>
                                </#if>
                            </tr>
                            <tr>
                                <#if set.format?has_content>
                                    <td><b>${uiLabelMap.WebtoolsFormat}</b> ${set.format}</td>
                                    <td colspan="2">&nbsp;</td>
                                </#if>
                            </tr>
                        </table>
                        <br/>
                    </#list>
                </td>
                </#if>
            </tr>
            <tr>
                <td colspan='5'>
                    <hr/>
                </td>
            </tr>
            </#list>
        </table>
        </div>
        </#if>
    <#-- End if service has ECA's -->

        <#list selectedServiceMap.allParamsList?if_exists as paramList>
        <style type="text/css">
            .param-table tr td {
                width: 12.5%;
                vertical-align: top;
            }
        </style>
        <div class="panel-heading-title">
            <h4>${paramList.title}</h4>
        </div>
            <#if paramList.paramList?exists && paramList.paramList?has_content>
            <table class="table">
                <tr>
                    <td>${uiLabelMap.WebtoolsParameterName}</td>
                    <td>${uiLabelMap.CommonDescription}</td>
                    <td>${uiLabelMap.WebtoolsOptional}</td>
                    <td>${uiLabelMap.CommonType}</td>
                <#-- <td>Default Value</td> -->
                    <td>${uiLabelMap.WebtoolsMode}</td>
                    <td>${uiLabelMap.WebtoolsIsSetInternally}</td>
                    <td>${uiLabelMap.WebtoolsEntityName}</td>
                    <td>${uiLabelMap.WebtoolsFieldName}</td>
                </tr>
                <#list paramList.paramList as modelParam>
                    <tr>
                        <td>${modelParam.name?if_exists}</td>
                        <td>${modelParam.description?if_exists}</td>
                        <td>${modelParam.optional?if_exists}</td>
                        <td>${modelParam.type?if_exists}</td>
                    <#-- <td>[${modelParam.defaultValue?if_exists}]</td> -->
                        <td>${modelParam.mode?if_exists}</td>
                        <td>${modelParam.internal?if_exists}</td>
                        <td>
                            <#if modelParam.entityName?exists>
                                <a href='<@ofbizUrl>${url}?constraint=default_entity_name@${modelParam.entityName}</@ofbizUrl>'>${modelParam.entityName?if_exists}</a>
                            </#if>
                        </td>
                        <td>${modelParam.fieldName?if_exists}</td>
                    </tr>
                </#list>
            </table>
            <#else>
            ${uiLabelMap.WebtoolsNoParametersDefined}
            </#if>
        </#list>

    <#-- Show a little form for exportServiceEoModelBundle -->
    <div class="screenlet-body">
        <form name="exportServiceEoModelBundle" method="post" action="<@ofbizUrl>exportServiceEoModelBundle</@ofbizUrl>" class="basic-form">
            <input type="hidden" name="sel_service_name" value="${selectedServiceMap.serviceName}"/>
            <input type="hidden" name="serviceName" value="${selectedServiceMap.serviceName}"/>
            Save eomodeld to Local Path: <input type="text" name="eomodeldFullPath" value="${parameters.eomodeldFullPath?if_exists}" size="60"/>
            <input type="submit" name="submitButton" value="Export"/>
        </form>
    </div>
        <@htmlScreenTemplate.renderScreenletEnd/>
    </#if>
<#-- No Service selected , we list all-->
<#elseif servicesList?exists && servicesList?has_content>

<#-- Show alphabetical index -->
    <#if serviceNamesAlphaList?exists && serviceNamesAlphaList?has_content>

    <form id='dispForm' method='post' action='<@ofbizUrl>${url}</@ofbizUrl>' class="form-inline">
        <div class="form-group">
            <#assign isfirst=true>
            <#list serviceNamesAlphaList as alpha>
                <a href='<@ofbizUrl>${url}?constraint=alpha@${alpha}</@ofbizUrl>'>${alpha}</a>
                <#assign isfirst=false>
            </#list>

            <#if dispArrList?exists && dispArrList?has_content>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <script language='javascript' type='text/javascript'>
                    function submitDispForm() {
                        selObj = document.getElementById('sd');
                        var dispVar = selObj.options[selObj.selectedIndex].value;
                        if (dispVar != '') {
                            document.getElementById('dispForm').submit();
                        }
                    }
                </script>
                <select id='sd' name='selDisp' onchange='submitDispForm();' class="form-control">
                    <option value='' selected="selected">${uiLabelMap.WebtoolsSelectDispatcher}</option>
                    <option value=''></option>
                    <#list dispArrList as disp>
                        <option value='${disp}' <#if selDisp == disp>selected</#if>>${disp}</option>
                    </#list>
                </select>
            </#if>
            &nbsp;&nbsp;&nbsp;&nbsp;<a href="<@ofbizUrl>exportHtmlServiceList?selDisp=${selDisp}</@ofbizUrl>" target="_blank">导出Html</a>
            &nbsp;&nbsp;&nbsp;&nbsp;<a href="<@ofbizUrl>exportServiceList?selDisp=${selDisp}</@ofbizUrl>" target="_blank">导出PDF</a>
            &nbsp;&nbsp;&nbsp;&nbsp;关键字搜索:&nbsp;&nbsp;<input type="text" class="form-control" name="serviceKey"/>
        </div>
    </form>
     </#if>

<div class="panel panel-white">
    <div class="panel-heading">
        <div class="panel-heading-title">
            <h4>${uiLabelMap.WebtoolsServicesListFor} ${dispatcherName?default(uiLabelMap.CommonNA)} (${servicesFoundCount} ${uiLabelMap.CommonFound})</h4>
        </div>
    </div>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table" cellspacing='0'>
                <tr class="header-row">
                    <td>${uiLabelMap.WebtoolsServiceName}</td>
                    <td>${uiLabelMap.WebtoolsEngineName}</td>
                    <td>${uiLabelMap.WebtoolsDefaultEntityName}</td>
                    <td>${uiLabelMap.WebtoolsInvoke}</td>
                    <td>${uiLabelMap.WebtoolsLocation}</td>
                </tr>
                <#assign alt_row = false>
                <#list servicesList as service>
                    <tr<#if alt_row> class="alternate-row"</#if>>
                        <td><a href='<@ofbizUrl>${url}?sel_service_name=${service.serviceName}</@ofbizUrl>'>${service.serviceName}</a></td>
                        <td><a href='<@ofbizUrl>${url}?constraint=engine_name@${service.engineName?default(uiLabelMap.CommonNA)}</@ofbizUrl>'>${service.engineName}</a></td>
                        <td>
                            <a href='<@ofbizUrl>${url}?constraint=default_entity_name@${service.defaultEntityName?default(uiLabelMap.CommonNA)}</@ofbizUrl>'>${service.defaultEntityName}</a>
                        </td>
                        <td>${service.invoke}</td>
                        <td><a href='<@ofbizUrl>${url}?constraint=location@${service.location?default(uiLabelMap.CommonNA)}</@ofbizUrl>'>${service.location}</a></td>
                    </tr>
                    <#assign alt_row = !alt_row>
                </#list>
            </table>
        </div>
    </div>
</div>
<#else>
${uiLabelMap.WebtoolsNoServicesFound}.
<a href='<@ofbizUrl>${url}</@ofbizUrl>' class="smallSubmit">${uiLabelMap.CommonListAll}</a>
</#if>
<script type="text/javascript">
    $(function() {
        var $input = $("input[name='serviceKey']"),
        $context = $("table  tr");
        $input.on("input", function() {
            var term = $(this).val();
            $context.unmark().show();
            if (term) {
                $context.mark(term, {
                    done: function() {
                        $context.not(":has(mark)").hide();
                    }
                });
            }
        });
    });
</script>
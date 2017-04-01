<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->

<div class="am-cf am-padding">
    <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">${uiLabelMap.WebtoolsMainPage}</strong> /
        <small>${delegator.getDelegatorName()}</small>
    </div>
</div>



<#if !userLogin?has_content>
<div>${uiLabelMap.WebtoolsForSomethingInteresting}.</div>
<br/>
<div>${uiLabelMap.WebtoolsNoteAntRunInstall}</div>
<br/>
<div><a href="<@ofbizUrl>checkLogin</@ofbizUrl>">${uiLabelMap.CommonLogin}</a></div>
</#if>
<#if userLogin?has_content>
<div class="am-g">
    <div class="am-u-md-3">
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-1'}">${uiLabelMap.WebtoolsCacheDebugTools}<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-1">
                <ul class="am-list admin-content-task">
                    <li><a href="<@ofbizUrl>FindUtilCache</@ofbizUrl>">${uiLabelMap.WebtoolsCacheMaintenance}</a></li>
                    <li><a href="<@ofbizUrl>LogConfiguration</@ofbizUrl>">${uiLabelMap.WebtoolsAdjustDebuggingLevels}</a></li>
                    <li><a href="<@ofbizUrl>LogView</@ofbizUrl>">${uiLabelMap.WebtoolsViewLog}</a></li>
                    <li><a href="<@ofbizUrl>ViewComponents</@ofbizUrl>">${uiLabelMap.WebtoolsViewComponents}</a></li>

                </ul>
            </div>
        </div>

        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-2'}">${uiLabelMap.WebtoolsGeneralArtifactInfoTools}<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-2">
                <ul class="am-list admin-content-task">
                    <#if security.hasPermission("ARTIFACT_INFO_VIEW", session)>
                        <li><a href="<@ofbizUrl>ArtifactInfo</@ofbizUrl>" target="_blank">${uiLabelMap.WebtoolsArtifactInfo}</a></li>
                        <li><a href="<@ofbizUrl>entityref</@ofbizUrl>" target="_blank">${uiLabelMap.WebtoolsEntityReference} - ${uiLabelMap.WebtoolsEntityReferenceInteractiveVersion}</a></li>
                        <li><a href="<@ofbizUrl>ServiceList</@ofbizUrl>">${uiLabelMap.WebtoolsServiceReference}</a></li>
                    </#if>
                </ul>
            </div>
        </div>



        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-3'}">${uiLabelMap.WebtoolsLabelManager}<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-3">
                <ul class="am-list admin-content-task">
                    <#if security.hasPermission("LABEL_MANAGER_VIEW", session)>
                        <li><a href="<@ofbizUrl>SearchLabels</@ofbizUrl>">${uiLabelMap.uiLabelMapWebtoolsLabelManager}</a></li>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-12'}">${uiLabelMap.WebtoolsCertsX509}/Selenium<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-12">
                <ul class="am-list admin-content-task">
                    <li><a href="<@ofbizUrl>myCertificates</@ofbizUrl>">${uiLabelMap.WebtoolsMyCertificates}</a></li>
                    <li><a href="<@ofbizUrl>selenium</@ofbizUrl>">Selenium</a></li>
                </ul>
            </div>
        </div>




</div>

    <div class="am-u-md-3">
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-6'}">${uiLabelMap.WebtoolsServiceEngineTools}<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-6">
                <ul class="am-list admin-content-task">
                    <#if security.hasPermission("SERVICE_MAINT", session)>
                        <li><a href="<@ofbizUrl>ServiceList</@ofbizUrl>">${uiLabelMap.WebtoolsServiceReference}</a></li>
                        <li><a href="<@ofbizUrl>scheduleJob</@ofbizUrl>">${uiLabelMap.PageTitleScheduleJob}</a></li>
                        <li><a href="<@ofbizUrl>runService</@ofbizUrl>">${uiLabelMap.PageTitleRunService}</a></li>
                        <li><a href="<@ofbizUrl>FindJob</@ofbizUrl>">${uiLabelMap.PageTitleJobList}</a></li>
                        <li><a href="<@ofbizUrl>threadList</@ofbizUrl>">${uiLabelMap.PageTitleThreadList}</a></li>
                        <li><a href="<@ofbizUrl>ServiceLog</@ofbizUrl>">${uiLabelMap.WebtoolsServiceLog}</a></li>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-7'}">${uiLabelMap.WebtoolsDataFileTools}<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-7">
                <ul class="am-list admin-content-task">
                    <#if security.hasPermission("DATAFILE_MAINT", session)>

                        <li><a href="<@ofbizUrl>viewdatafile</@ofbizUrl>">${uiLabelMap.WebtoolsWorkWithDataFiles}</a></li>
                    </#if>

                </ul>
            </div>
        </div>

        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-11'}">${uiLabelMap.WebtoolsPerformanceTests}/${uiLabelMap.WebtoolsServerHitStatisticsTools}<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-11">
                <ul class="am-list admin-content-task">
                    <li><a href="<@ofbizUrl>EntityPerformanceTest</@ofbizUrl>">${uiLabelMap.WebtoolsEntityEngine}</a></li>
                    <#if security.hasPermission("SERVER_STATS_VIEW", session)>
                        <li><a href="<@ofbizUrl>StatsSinceStart</@ofbizUrl>">${uiLabelMap.WebtoolsStatsSinceServerStart}</a></li>
                    </#if>
                </ul>
            </div>
        </div>
    </div>

    <div class="am-u-md-3">
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-8'}">${uiLabelMap.WebtoolsMiscSetupTools}<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-8">
                <ul class="am-list admin-content-task">

                    <#if security.hasPermission("PORTALPAGE_ADMIN", session)>
                        <li><a href="<@ofbizUrl>FindPortalPage</@ofbizUrl>">${uiLabelMap.WebtoolsAdminPortalPage}</a></li>
                        <li><a href="<@ofbizUrl>FindGeo</@ofbizUrl>">${uiLabelMap.WebtoolsGeoManagement}</a></li>
                        <li><a href="<@ofbizUrl>WebtoolsLayoutDemo</@ofbizUrl>">${uiLabelMap.WebtoolsLayoutDemo}</a></li>
                    </#if>
                </ul>
            </div>
        </div>


        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-9'}">${uiLabelMap.WebtoolsCacheDebugTools}<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-9">
                <ul class="am-list admin-content-task">
                    <li><a href="<@ofbizUrl>FindUtilCache</@ofbizUrl>">${uiLabelMap.WebtoolsCacheMaintenance}</a></li>
                    <li><a href="<@ofbizUrl>LogConfiguration</@ofbizUrl>">${uiLabelMap.WebtoolsAdjustDebuggingLevels}</a></li>
                    <li><a href="<@ofbizUrl>LogView</@ofbizUrl>">${uiLabelMap.WebtoolsViewLog}</a></li>
                    <li><a href="<@ofbizUrl>ViewComponents</@ofbizUrl>">${uiLabelMap.WebtoolsViewComponents}</a></li>

                </ul>
            </div>
            </div>


        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-5'}">${uiLabelMap.WebtoolsEntityXMLTools}<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-5">
                <ul class="am-list admin-content-task">
                    <li><a href="<@ofbizUrl>xmldsdump</@ofbizUrl>">${uiLabelMap.PageTitleEntityExport}</a></li>
                    <li><a href="<@ofbizUrl>EntityExportAll</@ofbizUrl>">${uiLabelMap.PageTitleEntityExportAll}</a></li>
                    <li><a href="<@ofbizUrl>EntityImport</@ofbizUrl>">${uiLabelMap.PageTitleEntityImport}</a></li>
                    <li><a href="<@ofbizUrl>EntityImportDir</@ofbizUrl>">${uiLabelMap.PageTitleEntityImportDir}</a></li>
                    <li><a href="<@ofbizUrl>EntityImportReaders</@ofbizUrl>">${uiLabelMap.PageTitleEntityImportReaders}</a></li>
                </ul>
            </div>
        </div>





    </div>
    <div class="am-u-md-3">
        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-4'}">${uiLabelMap.WebtoolsEntityEngineTools}<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-4">
                <ul class="am-list admin-content-task">
                    <#if security.hasPermission("ENTITY_MAINT", session)>
                        <li><a href="<@ofbizUrl>entitymaint</@ofbizUrl>">${uiLabelMap.WebtoolsEntityDataMaintenance}</a></li>
                        <li><a href="<@ofbizUrl>entityref</@ofbizUrl>" target="_blank">${uiLabelMap.WebtoolsEntityReference} - ${uiLabelMap.WebtoolsEntityReferenceInteractiveVersion}</a></li>
                        <li><a href="<@ofbizUrl>entityref?forstatic=true</@ofbizUrl>" target="_blank">${uiLabelMap.WebtoolsEntityReference} - ${uiLabelMap.WebtoolsEntityReferenceStaticVersion}</a></li>
                        <li><a href="<@ofbizUrl>entityrefReport</@ofbizUrl>" target="_blank">${uiLabelMap.WebtoolsEntityReferencePdf}</a></li>
                        <li><a href="<@ofbizUrl>EntitySQLProcessor</@ofbizUrl>">${uiLabelMap.PageTitleEntitySQLProcessor}</a></li>
                        <li><a href="<@ofbizUrl>EntitySyncStatus</@ofbizUrl>">${uiLabelMap.WebtoolsEntitySyncStatus}</a></li>
                        <li><a href="<@ofbizUrl>view/ModelInduceFromDb</@ofbizUrl>" target="_blank">${uiLabelMap.WebtoolsInduceModelXMLFromDatabase}</a></li>
                        <li><a href="<@ofbizUrl>EntityEoModelBundle</@ofbizUrl>">${uiLabelMap.WebtoolsExportEntityEoModelBundle}</a></li>
                        <li><a href="<@ofbizUrl>view/checkdb</@ofbizUrl>">${uiLabelMap.WebtoolsCheckUpdateDatabase}</a></li>
                    <#-- not using Minerva by default any more <li><a href="<@ofbizUrl>minervainfo</@ofbizUrl>">Minerva Connection Info</a></li> -->
                    <#-- want to leave these out because they are only working so-so, and cause people more problems that they solve, IMHO
                      <li><a href="<@ofbizUrl>view/EditEntity</@ofbizUrl>"  target="_blank">Edit Entity Definitions</a></li>
                      <li><a href="<@ofbizUrl>ModelWriter</@ofbizUrl>" target="_blank">Generate Entity Model XML (all in one)</a></li>
                      <li><a href="<@ofbizUrl>ModelWriter?savetofile=true</@ofbizUrl>" target="_blank">Save Entity Model XML to Files</a></li>
                    -->
                    <#-- not working right now anyway
                      <li><a href="<@ofbizUrl>ModelGroupWriter</@ofbizUrl>" target="_blank">Generate Entity Group XML</a></li>
                      <li><a href="<@ofbizUrl>ModelGroupWriter?savetofile=true</@ofbizUrl>" target="_blank">Save Entity Group XML to File</a></li>
                    -->
                    <#--
                      <li><a href="<@ofbizUrl>view/tablesMySql</@ofbizUrl>">MySQL Table Creation SQL</a></li>
                      <li><a href="<@ofbizUrl>view/dataMySql</@ofbizUrl>">MySQL Auto Data SQL</a></li>
                    -->

                    </#if>
                </ul>
            </div>
        </div>


        <div class="am-panel am-panel-default">
            <div class="am-panel-hd am-cf" data-am-collapse="{target: '#collapse-panel-10'}">${uiLabelMap.WebtoolsGeneralArtifactInfoTools}<span class="am-icon-chevron-down am-fr"></span></div>
            <div class="am-panel-bd am-collapse am-in" id="collapse-panel-10">
                <ul class="am-list admin-content-task">
                    <#if security.hasPermission("ARTIFACT_INFO_VIEW", session)>
                        <li><a href="<@ofbizUrl>ArtifactInfo</@ofbizUrl>" target="_blank">${uiLabelMap.WebtoolsArtifactInfo}</a></li>
                        <li><a href="<@ofbizUrl>entityref</@ofbizUrl>" target="_blank">${uiLabelMap.WebtoolsEntityReference} - ${uiLabelMap.WebtoolsEntityReferenceInteractiveVersion}</a></li>
                        <li><a href="<@ofbizUrl>ServiceList</@ofbizUrl>">${uiLabelMap.WebtoolsServiceReference}</a></li>
                    </#if>
                </ul>
            </div>
        </div>


    </div>
</div>
</#if>

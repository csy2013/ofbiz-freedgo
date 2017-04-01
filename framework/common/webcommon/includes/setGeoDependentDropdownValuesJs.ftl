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
<script src="<@ofbizContentUrl>/images/getDependentDropdownValues.js</@ofbizContentUrl>" type="text/javascript"></script>
<script type="text/javascript">
    jQuery(document).ready(function () {
        if (jQuery('#${dependentForm}').length) {
            //      国家
            jQuery("#${dependentForm}_${mainId}").change(function (e, data) {
                getDependentDropdownValues('<@ofbizUrl>${requestName}</@ofbizUrl>', '${paramKey}', '${dependentForm}_${mainId}', '${dependentForm}_${dependentId}', '${responseName}', '${dependentKeyName}', '${descName}');
            });
            //    省
            jQuery("#${dependentForm}_${mainId1}").change(function (e, data) {
                getDependentDropdownValues('<@ofbizUrl>${requestName}</@ofbizUrl>', '${paramKey1}', '${dependentForm}_${mainId1}', '${dependentForm}_${dependentId1}', '${responseName}', '${dependentKeyName}', '${descName}');
            });
            /* 市*/
            jQuery("#${dependentForm}_${mainId2}").change(function (e, data) {
                getDependentDropdownValues('<@ofbizUrl>${requestName}</@ofbizUrl>', '${paramKey2}', '${dependentForm}_${mainId2}', '${dependentForm}_${dependentId2}', '${responseName}', '${dependentKeyName}', '${descName}');
            });
            getDependentDropdownValues('<@ofbizUrl>${requestName}</@ofbizUrl>', '${paramKey}', '${dependentForm}_${mainId}', '${dependentForm}_${dependentId}', '${responseName}', '${dependentKeyName}', '${descName}', '${selectedDependentOption}');
            getDependentDropdownValues('<@ofbizUrl>${requestName}</@ofbizUrl>', '${paramKey1}', '${dependentForm}_${mainId1}', '${dependentForm}_${dependentId1}', '${responseName}', '${dependentKeyName}', '${descName}', '${selectedDependentOption1}');
            getDependentDropdownValues('<@ofbizUrl>${requestName}</@ofbizUrl>', '${paramKey2}', '${dependentForm}_${mainId2}', '${dependentForm}_${dependentId2}', '${responseName}', '${dependentKeyName}', '${descName}', '${selectedDependentOption2}');
        }
    })
</script>
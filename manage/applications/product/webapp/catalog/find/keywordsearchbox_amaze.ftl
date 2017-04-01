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
<#if (requestAttributes.uiLabelMap)?exists><#assign uiLabelMap = requestAttributes.uiLabelMap></#if>

<script type="text/javascript">
//<![CDATA[
     function changeCategory() {
         document.forms["keywordsearchform"].elements["SEARCH_CATEGORY_ID"].value=document.forms["advancedsearchform"].elements["DUMMYCAT"].value;
         document.forms["advancedsearchform"].elements["SEARCH_CATEGORY_ID"].value=document.forms["advancedsearchform"].elements["DUMMYCAT"].value;
     }
     function submitProductJump(that) {
         jQuery('#productJumpForm input[name=productId]').val(jQuery('#productJumpForm input[name=productId]').val().replace(" ",""));
         jQuery('#productJumpForm').attr('action', jQuery('#dummyPage').val());
         jQuery('#productJumpForm').submit();
     }
//]]>
 </script>
<div class="am-g">
<form class="am-form am-form-horizontal" name="keywordsearchform" id="keywordSearchForm" method="post" action="<@ofbizUrl>keywordsearch?VIEW_SIZE=25&amp;PAGING=Y</@ofbizUrl>">
  <fieldset>
    <div class="am-input-group">
      <label class="" for="keywordSearchString">${uiLabelMap.ProductKeywords}:</label>
      <input class="am-input-field am-input-sm" type="text" name="SEARCH_STRING" id="keywordSearchString" size="25" maxlength="50" value="${requestParameters.SEARCH_STRING?if_exists}" />
    </div>
    <div class="am-input-group">
      <label for="keywordSearchCategoryId">${uiLabelMap.ProductCategoryId}:</label>
      <@amazeHtmlTemplate.lookupField value="${requestParameters.SEARCH_CATEGORY_ID?if_exists}" formName="keywordsearchform" name="SEARCH_CATEGORY_ID" id="keywordSearchCategoryId" fieldFormName="LookupProductCategory"/>
    </div>
    <div class="am-input-group">
      <label for="keywordSearchCointains">${uiLabelMap.CommonNoContains}</label>
      <input type="checkbox" name="SEARCH_CONTAINS" id="keywordSearchCointains" value="N" <#if requestParameters.SEARCH_CONTAINS?if_exists == "N">checked="checked"</#if> />
      <label for="keywordSearchOperatorOr">${uiLabelMap.CommonAny}</label>
      <input type="radio" name="SEARCH_OPERATOR" id="keywordSearchOperatorOr" value="OR" <#if requestParameters.SEARCH_OPERATOR?if_exists != "AND">checked="checked"</#if> />
      <label for="keywordSearchOperatorAnd">${uiLabelMap.CommonAll}</label>
      <input type="radio" name="SEARCH_OPERATOR" id="keywordSearchOperatorAnd" value="AND" <#if requestParameters.SEARCH_OPERATOR?if_exists == "AND">checked="checked"</#if> />
    </div>

    <div>
      <input type="submit" name="find" value="${uiLabelMap.CommonFind}" class="am-btn am-btn-primary am-btn-sm" />
    </div>
    </fieldset>
</form>
</div>


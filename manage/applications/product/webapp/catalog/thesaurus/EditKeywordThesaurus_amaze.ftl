<div class="am-cf am-padding-xs">
    <div class="am-panel am-panel-default">
        <div class="am-panel-hd am-cf">${uiLabelMap.ProductAlternateKeyWordThesaurus}</div>
        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-6">
                    <form class="am-form am-form-horizontal" method="post" action="<@ofbizUrl>createKeywordThesaurus</@ofbizUrl>" >
                        <div class="am-form-group am-g">
                            <label class="am-control-label  am-u-lg-2">
                                ${uiLabelMap.ProductKeyword}
                            </label>
                            <div class="am-u-lg-2">
                                <input type="text" class="am-form-field am-u-sm" name="enteredKeyword" size="10"/>
                            </div>
                            <label class="am-control-label  am-u-lg-1">
                                ${uiLabelMap.ProductAlternate}
                            </label>
                            <div class="am-u-lg-2">
                                <input type="text" class="am-form-field am-u-sm" name="alternateKeyword" size="10"/>
                            </div>
                            <label class="am-control-label  am-u-lg-1">
                                ${uiLabelMap.ProductRelationship}
                            </label>
                            <div class="am-u-lg-4">
                                <div class="am-u-lg-8 am-u-end">
                                    <select<#-- data-am-selected="{width:50}"-->  name="relationshipEnumId">
                                    <#list relationshipEnums as relationshipEnum>
                                        <option value="${relationshipEnum.enumId}">${relationshipEnum.get("description",locale)}</option>
                                    </#list>
                                    </select>
                                </div>
                                <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonAdd}"/>
                            </div>
                        </div>
                    </form>
                    <div>
                        <span style="margin-left: 50px">
                            <#list letterList as letter>
                                <#if letter == firstLetter><#assign highlight=true><#else><#assign highlight=false></#if>
                                <a href="<@ofbizUrl>editKeywordThesaurus?firstLetter=${letter}</@ofbizUrl>" class="buttontext"><#if highlight>[</#if>${letter}<#if highlight>]</#if></a>
                            </#list>
                        </span>
                    </div>
                </div>
            </div>
        </div>

        <div class="am-panel-bd am-collapse am-in">
            <div class="am-g">
                <div class="am-u-lg-12">
                <#assign lastkeyword = "">
                <#if keywordThesauruses?has_content>
                    <table class="am-table am-table-striped am-table-hover table-main">
                        <#assign rowClass = "2">
                        <#list keywordThesauruses as keyword>
                            <#assign relationship = keyword.getRelatedOneCache("RelationshipEnumeration")>
                            <#if keyword.enteredKeyword == lastkeyword><#assign sameRow=true><#else><#assign lastkeyword=keyword.enteredKeyword><#assign sameRow=false></#if>
                            <#if sameRow == false>
                                <#if (keyword_index > 0)>
                                    </td>
                                    </tr>
                                </#if>
                            <tr>
                                <td>
                                    <form method="post" class="am-form am-form-horizontal" action="<@ofbizUrl>createKeywordThesaurus</@ofbizUrl>">
                                        <div>
                                            ${keyword.enteredKeyword}&nbsp;&nbsp;&nbsp;
                                            <form method="post" action="<@ofbizUrl>deleteKeywordThesaurus</@ofbizUrl>">
                                                <input type="hidden" name="enteredKeyword" value="${keyword.enteredKeyword}" />
                                                <input type="hidden" name="alternateKeyword" value="${keyword.alternateKeyword}" />
                                                <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonDeleteAll}" />
                                            </form>
                                        </div>
                                        <div>
                                            <input type="hidden" name="enteredKeyword" value="${keyword.enteredKeyword}" />
                                            <span class="label"><b>${uiLabelMap.ProductAlternate}</b></span> &nbsp;&nbsp;<input type="text" name="alternateKeyword" size="20" />&nbsp;&nbsp;&nbsp;&nbsp;
                                            <span class="label"><b>${uiLabelMap.ProductRelationship}</b></span>&nbsp;&nbsp;
                                            <select class="am-selected-btn" name="relationshipEnumId"><#list relationshipEnums as relationshipEnum><option value="${relationshipEnum.enumId}">${relationshipEnum.get("description",locale)}</option></#list></select>
                                            <input type="submit" class="am-btn am-btn-primary am-btn-sm" value="${uiLabelMap.CommonAdd}" />
                                        </div>
                                    </form>
                                </td>
                            <td>
                            </#if>
                            <div>
                                <form method="post"  class="am-form am-form-horizontal" action="<@ofbizUrl>deleteKeywordThesaurus</@ofbizUrl>" name="deleteKeywordThesaurus">
                                    <input type="hidden" name="enteredKeyword" value="${keyword.enteredKeyword}" />
                                    <input type="hidden" name="alternateKeyword" value="${keyword.alternateKeyword}" />
                                    <input class="am-btn am-btn-primary am-btn-sm" type="submit" value="X" />
                                </form>
                            ${keyword.alternateKeyword}&nbsp;(${uiLabelMap.ProductRelationship}:${(relationship.get("description",locale))?default(keyword.relationshipEnumId?if_exists)})
                            </div>
                        <#-- toggle the row color -->
                            <#if rowClass == "2">
                                <#assign rowClass = "1">
                            <#else>
                                <#assign rowClass = "2">
                            </#if>
                        </#list>
                    </td>
                    </tr>
                    </table>
                </#if>
                </div>
            </div>
        </div>

    </div>
</div>
<@htmlScreenTemplate.renderScreenletBegin id="" title="${uiLabelMap.ProductAlternateKeyWordThesaurus}"/>

<form method="post" action="<@ofbizUrl>createKeywordThesaurus</@ofbizUrl>" class="form-inline">
    <div class="input-group">
        <div class="input-group-addon"><span>${uiLabelMap.ProductKeyword}</span></div>
        <input type="text" name="enteredKeyword" size="20" class="form-control"/>
    </div>
    <div class="input-group">
        <div class="input-group-addon"><span>${uiLabelMap.ProductAlternate}</span></div>
        <input type="text" name="alternateKeyword" size="20" class="form-control"/>
    </div>
    <div class="input-group">
        <div class="input-group-addon"><span>${uiLabelMap.ProductRelationship}</span></div>
        <select name="relationshipEnumId" class="form-control">
        <#list relationshipEnums as relationshipEnum>
            <option value="${relationshipEnum.enumId}">${relationshipEnum.get("description",locale)}</option>
        </#list>
        </select>
    </div>
    <input type="submit" value="${uiLabelMap.CommonAdd}" class="btn btn-primary btn-sm"/>

</form>
<div>
    <hr/>
    <div class="row">
        <div class="col-md-12">
        <#list letterList as letter>
            <#if letter == firstLetter><#assign highlight=true><#else><#assign highlight=false></#if>
            <a href="<@ofbizUrl>editKeywordThesaurus?firstLetter=${letter}</@ofbizUrl>" style="font-size:20px;"
               class="badge <#if highlight>badge-danger<#else>badge-success</#if> badge-square">
            ${letter}
            </a>
        </#list>
        </div>
    </div>
</div>
<br/>
<#assign lastkeyword = "">
<#if keywordThesauruses?has_content>
<div class="table-responsive">
    <table class="table table-striped table-bordered">
        <#assign rowClass = "2">
        <#list keywordThesauruses as keyword>
            <#assign relationship = keyword.getRelatedOneCache("RelationshipEnumeration")>
            <#if keyword.enteredKeyword == lastkeyword><#assign sameRow=true><#else><#assign lastkeyword=keyword.enteredKeyword><#assign sameRow=false></#if>
            <#if sameRow == false>
                <#if (keyword_index > 0)>
                    </td>
                    </tr>
                </#if>
            <tr valign="middle"<#if rowClass == "1"> class="alternate-row"</#if>>
                <td>

                        <form method="post" action="<@ofbizUrl>deleteKeywordThesaurus</@ofbizUrl>" name="deleteKeywordThesaurus" class="form-inline">
                            <input type="hidden" name="enteredKeyword" value="${keyword.enteredKeyword}"/>
                            <input type="hidden" name="alternateKeyword" value="${keyword.alternateKeyword}"/>
                            <div class="form-group">
                                <label class="col-md-3">${keyword.enteredKeyword}</label>
                            </div>
                            <input type="submit" value="${uiLabelMap.CommonDeleteAll}" class="btn btn-primary btn-sm"/>
                        </form>
                    <hr/>

                    <form method="post" action="<@ofbizUrl>createKeywordThesaurus</@ofbizUrl>" class="form-inline">
                        <input type="hidden" name="enteredKeyword" value="${keyword.enteredKeyword}"/>

                        <div class="form-group">
                            <div class="input-group">

                                <div class="input-group-addon"><span>${uiLabelMap.ProductAlternate}</span></div>
                                <input type="text" name="alternateKeyword" size="10" class="form-control"/>
                            </div>
                            <div class="input-group">
                                <div class="input-group-addon"><span>${uiLabelMap.ProductRelationship}</span></div>
                                <select name="relationshipEnumId" class="form-control"><#list relationshipEnums as relationshipEnum>
                                    <option value="${relationshipEnum.enumId}">${relationshipEnum.get("description",locale)}</option></#list></select>

                            </div>
                            <input type="submit" value="${uiLabelMap.CommonAdd}" class="btn btn-primary btn-sm"/>
                        </div>


                    </form>

                </td>
            <td>
            </#if>
            <div>
                <form method="post" action="<@ofbizUrl>deleteKeywordThesaurus</@ofbizUrl>" name="deleteKeywordThesaurus" class="form-inline">
                    <input type="hidden" name="enteredKeyword" value="${keyword.enteredKeyword}"/>
                    <input type="hidden" name="alternateKeyword" value="${keyword.alternateKeyword}"/>
                    <input type="submit" value="X" class="btn btn-primary btn-sm"/>
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
</div>
</#if>

<@htmlScreenTemplate.renderScreenletEnd/>
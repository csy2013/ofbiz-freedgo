
<#if orderTerms?has_content>
    <@htmlScreenTemplate.renderScreenletBegin id="OrderOrderTermsPanel" title="${uiLabelMap.OrderOrderTerms}"/>
<#--<div class="panel panel-default">
    <div class="panel-heading">
        <h4 class="panel-title">&nbsp;${uiLabelMap.OrderOrderTerms}</h4>
    </div>
    <div class="panel-body">-->
        <div class="table-responsive">
      <table class="table">
      <tr class="header-row">
        <td width="35%">${uiLabelMap.OrderOrderTermType}</td>
        <td width="15%" align="center">${uiLabelMap.OrderOrderTermValue}</td>
        <td width="15%" align="center">${uiLabelMap.OrderOrderTermDays}</td>
        <td width="35%" align="center">${uiLabelMap.CommonDescription}</td>
      </tr>
    <#list orderTerms as orderTerm>
      <tr>
        <td width="35%">${orderTerm.getRelatedOne("TermType").get("description", locale)}</td>
        <td width="15%" align="center">${orderTerm.termValue?default("")}</td>
        <td width="15%" align="center">${orderTerm.termDays?default("")}</td>
        <td width="35%" align="center">${orderTerm.textValue?default("")}</td>
      </tr>
    </#list>
      </table>
        </div>
     <@htmlScreenTemplate.renderScreenletEnd/>
</#if>
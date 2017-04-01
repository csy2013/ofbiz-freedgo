
<#if description?exists>
    <#if autocompleteOptions?exists>
        <#list autocompleteOptions as autocompleteOption>
            <#assign displayString = ""/>
            <#list displayFieldsSet as key>
                <#assign field = autocompleteOption.get(key)?if_exists>
                <#if field?has_content>
                    <#if (key != context.returnField)>
                        <#assign displayString = displayString + field + " ">
                    </#if>
                </#if>
            </#list>
            <#if (displayString?trim?has_content )>${displayString?trim}</#if>
        </#list>
    </#if>
<#else>
<script type="text/javascript">
var autocomp = [
    <#if autocompleteOptions?has_content>
        <#if !displayReturnField?exists>
            <#assign displayReturnField = Static["org.ofbiz.base.util.UtilProperties"].getPropertyValue("widget.properties", "widget.autocompleter.displayReturnField")>
        </#if>
        <#list autocompleteOptions as autocompleteOption>
            {
            <#assign displayString = ""/>
            <#assign returnField = ""/>
            <#list displayFieldsSet as key>
              <#assign field = autocompleteOption.get(key)?if_exists>
              <#if field?has_content>
                  <#if (key == context.returnField)>
                      <#assign returnField = field/>
                  <#else>
                      <#assign displayString = displayString + StringUtil.wrapString(field?string) + " ">
                  </#if>
              </#if>
            </#list>
            <#if ("Y" == displayReturnField)>
                <#assign displayString = displayString +  "[" + returnField + "]">
            </#if>
            "id": "${returnField}",
            "label": "<#if (displayString?trim?has_content )>${displayString?trim}<#else>${returnField}</#if>",
            "value": "${returnField}"
            }<#if autocompleteOption_has_next>,</#if>
        </#list>
    <#else>
      {
         "id": "",
         "label": "${uiLabelMap.CommonNoRecordFound}",
         "value": ""
      }
    </#if>
    ];
</script>
</#if>

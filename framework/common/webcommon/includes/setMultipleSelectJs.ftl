<script src="<@ofbizContentUrl>/images/jquery/plugins/asmselect/jquery.asmselect-1.0.4a-beta.js</@ofbizContentUrl>" type="text/javascript"></script>
<#if asm_multipleSelect?exists> <#-- we check only this var and suppose the others are also present -->
<script type="text/javascript">
jQuery(document).ready(function() {
    multiple = jQuery("#${asm_multipleSelect?if_exists}");

  <#if asm_title?exists>
    // set the dropdown "title" if exists
    multiple.attr('title', '${asm_title}');
  </#if>
  
    // use asmSelect in Widget Forms
    multiple.asmSelect({
      addItemTarget: 'top',
      sortable: ${asm_sortable!'false'},
      removeLabel: '${uiLabelMap.CommonRemove!'Remove'}'
      //, debugMode: true
    });
      
  <#if asm_relatedField?exists> <#-- can be used without related field -->
    // track possible relatedField changes
    // on initial focus (focus-field-name must be asm_relatedField) or if the field value changes, select related multi values. 
    typeValue = jQuery('#${asm_typeField}').val();
    jQuery("#${asm_relatedField}").one('focus', function() {
      selectMultipleRelatedValues('${asm_requestName}', '${asm_paramKey}', '${asm_relatedField}', '${asm_multipleSelect}', '${asm_type}', typeValue, '${asm_responseName}');
    });
    jQuery("#${asm_relatedField}").change(function() {
      selectMultipleRelatedValues('${asm_requestName}', '${asm_paramKey}', '${asm_relatedField}', '${asm_multipleSelect}', '${asm_type}', typeValue, '${asm_responseName}');
    });
    selectMultipleRelatedValues('${asm_requestName}', '${asm_paramKey}', '${asm_relatedField}', '${asm_multipleSelect}', '${asm_type}', typeValue, '${asm_responseName}');
  </#if>
  });  
</script>

<style type="text/css">
#${asm_multipleSelectForm} {
    width: ${asm_formSize!700}px; 
    position: relative;
}

.asmListItem {
  width: ${asm_asmListItemPercentOfForm!95}%; 
}
</style>
</#if>

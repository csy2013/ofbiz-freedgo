import org.ofbiz.base.util.StringUtil
import org.ofbiz.base.util.UtilMisc
import org.ofbiz.base.util.UtilValidate
import org.ofbiz.entity.util.EntityUtil

//菜系display显示
if(UtilValidate.isNotEmpty(inputFields)){
    List<String> currentValueList = null
    cuisineList = []
    if(inputFields.startsWith("{")&&inputFields.endsWith("}")) {
        currentValueList = StringUtil.toList(inputFields, "{", "}")
    }else
    // If currentValue is Array, it will start with [
    if (inputFields.startsWith("[")) {
        currentValueList = StringUtil.toList(inputFields)
    }

    else {
        currentValueList = UtilMisc.toList(inputFields)
    }
    if (UtilValidate.isNotEmpty(currentValueList)) {
        currentValueList.each {currentValue ->
            enumerationList = EntityUtil.getFirst(delegator.findByAnd("Enumeration",UtilMisc.toMap("enumTypeId","HOTEL_CUISINE","enumId",currentValue),UtilMisc.toList("sequenceId")))
            if(enumerationList) {
                cuisineList.add(enumerationList.description)
            }
        }
    }
    context.cuisine =StringUtil.join(cuisineList,",")
}
//宴会标签显示
if(UtilValidate.isNotEmpty(banquetTag)){
    List<String> checkboxValueList = null
    banquetTagList = []
    if(banquetTag.startsWith("{")&&banquetTag.endsWith("}")) {
        checkboxValueList = StringUtil.toList(banquetTag, "{", "}")
    }else
    // If banquetTag is Array, it will start with [
    if (banquetTag.startsWith("[")) {
        checkboxValueList = StringUtil.toList(banquetTag)
    }

    else {
        checkboxValueList = UtilMisc.toList(banquetTag)
    }

    if (UtilValidate.isNotEmpty(checkboxValueList)) {
        checkboxValueList.each {currentValue ->
            enumerationList = EntityUtil.getFirst(delegator.findByAnd("Enumeration",UtilMisc.toMap("enumTypeId","HOTEL_TAG","enumId",currentValue),UtilMisc.toList("sequenceId")))
            if(enumerationList) {
                banquetTagList.add(enumerationList.description)
            }
        }
    }
    context.banquetTag =StringUtil.join(banquetTagList,",")
}
if(UtilValidate.isNotEmpty(serviceTag)){
    List<String> checkboxValueList = null
    serviceTagList = []
    if(serviceTag.startsWith("{")&&serviceTag.endsWith("}")) {
        checkboxValueList = StringUtil.toList(serviceTag, "{", "}")
    }else
    // If serviceTag is Array, it will start with [
    if (serviceTag.startsWith("[")) {
        checkboxValueList = StringUtil.toList(serviceTag)
    }

    else {
        checkboxValueList = UtilMisc.toList(serviceTag)
    }

    if (UtilValidate.isNotEmpty(checkboxValueList)) {
        checkboxValueList.each {currentValue ->
            enumerationList = EntityUtil.getFirst(delegator.findByAnd("Enumeration",UtilMisc.toMap("enumTypeId","HOTEL_Service_TAG","enumId",currentValue),UtilMisc.toList("sequenceId")))
            if(enumerationList) {
                serviceTagList.add(enumerationList.description)
            }
        }
    }
    context.serviceTag =StringUtil.join(serviceTagList,",")
}



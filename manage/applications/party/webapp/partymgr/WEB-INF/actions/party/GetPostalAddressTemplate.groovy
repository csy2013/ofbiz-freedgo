postalAddressForTemplate = context.postalAddress
postalAddressTemplateSuffix = context.postalAddressTemplateSuffix

if (!postalAddressTemplateSuffix) {
  postalAddressTemplateSuffix = ".ftl"
}
context.postalAddressTemplate = "PostalAddress" + postalAddressTemplateSuffix
println "postalAddressForTemplate = $postalAddressForTemplate"
if (postalAddressForTemplate && postalAddressForTemplate.countryGeoId) {
    postalAddressTemplate = "PostalAddress_" + postalAddressForTemplate.countryGeoId + postalAddressTemplateSuffix
    file = new File(addressTemplatePath + postalAddressTemplate)
    println "postalAddressTemplate = $addressTemplatePath  $postalAddressTemplate"
    if (file.exists()) {

        context.postalAddressTemplate = postalAddressTemplate
    }
}

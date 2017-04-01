./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=mobile/daojia/data/DaoJiaData.xml
./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=mobile/token/data/AppTypeData.xml
./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=specialpurpose/weixin/data/weixinTypeData.xml
./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=services/productService/data/ProdPromoSecurityData.xml
./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=framework/service/data/ServiceSeedData.xml
./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=services/orderService/data/OrderScheduledServices.xml
./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=framework/service/data/ScheduledServices.xml
./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=services/partyService/data/ScheduledJobs.xml
./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=framework/entityext/data/EntityScheduledServices.xml
./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=services/accountService/data/AccountingScheduledServiceData.xml
./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=services/productService/data/ProductScheduledServices.xml
./ant load-file -Ddelegator=default#www_changsy_cn -Ddata-file=hot-deploy/newSite/entitydef/entitymodel.xml

task:


http://localhost:3000/client/cartV3_0_0.verifySettle?functionId=cartV3_0_0%2FverifySettle&body=%7B%22storeId%22%3A%2210060816%22%2C%22orgCode%22%3A%2275109%22%7D&appVersion=3.2.0&appName=paidaojia&platCode=H5&rand=1474005378544


http://localhost:3000/client/marketsettle.getCurrentAccount?functionId=marketsettle%2FgetCurrentAccount&body=%7B%22orderPayType%22%3A%22first%22%2C%22fromSource%22%3A2%2C%22jingBeansNum%22%3A0%2C%22source%22%3A2%2C%22channelType%22%3A%220%22%2C%22orgCode%22%3A%2275109%22%2C%22storeId%22%3A10060816%2C%22storeName%22%3A%22%E8%8B%8F%E5%AE%A2%E4%B8%AD%E5%BC%8F%E9%A4%90%E9%A5%AE-%E5%8D%8E%E4%BE%A8%E5%9F%8E%E5%BA%97%22%2C%22openJPIndustry%22%3A%2230%22%2C%22addressType%22%3Atrue%2C%22cityCode%22%3A904%2C%22longitude%22%3A118.7248%2C%22latitude%22%3A32.14155%7D&appVersion=3.2.0&appName=paidaojia&platCode=H5&rand=1474005378573


http://localhost:3000/client/voucher.getUsableVoucherCountFive?functionId=voucher%2FgetUsableVoucherCountFive&body=%7B%22unique%22%3A%22f4d5fb99-f51f-4268-a273-5194f98c7bab%22%7D&appVersion=3.2.0&appName=paidaojia&platCode=H5



http://www.changsy.cn:8080/mobile-daojia/dj/order.list?functionId=order%2Flist&body=%7B%22startIndex%22%3A0%2C%22dataSize%22%3A10%7D&appVersion=3.2.0&appName=paidaojia&platCode=H5&rand=1474005175835

http://www.changsy.cn:8080/mobile-daojia/dj/order.isExistsComment?functionId=order%2FisExistsComment&body=%7B%22startIndex%22%3A0%2C%22dataSize%22%3A10%7D&appVersion=3.2.0&appName=paidaojia&platCode=H5&rand=1474005175837

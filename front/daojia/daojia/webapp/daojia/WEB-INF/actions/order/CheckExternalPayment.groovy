import org.ofbiz.service.ModelService

result = com.yuaoq.yabiz.mobile.order.shoppingcart.CheckOutEvents.checkExternalPayment(request, response);
resultData = [:]
resultData.put('payment', result);
request.setAttribute("resultData", resultData);
request.setAttribute(ModelService.SUCCESS_MESSAGE, ModelService.RESPOND_SUCCESS);
return 'success'
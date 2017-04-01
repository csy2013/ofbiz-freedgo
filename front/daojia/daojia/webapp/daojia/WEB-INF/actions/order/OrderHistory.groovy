//orderItems
//OrderHeader
//确认收货
//查询出所有的订单，再根据状态分组
userLogin = request.getAttribute("userLogin")
resultData = dispatcher.runSync("queryOrderHistory",[loginId:userLogin.get("userLoginId"),userLogin:userLogin]);
request.setAttribute('resultData', resultData)
return 'success'
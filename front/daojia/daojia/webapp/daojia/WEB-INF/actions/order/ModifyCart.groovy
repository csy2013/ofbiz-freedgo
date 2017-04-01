import org.ofbiz.service.ModelService

/*
修改
removeSelected:false
update_0:8
update_1:8
update_2:8
shoppingListId:10011 // 貌似不用
删除
removeSelected:true
update_0:8
update_1:8
update_2:8
selectedItem:2
shoppingListId:10011 //貌似不用

删除全部
removeSelected:true
selectAll:0 // 貌似不用
update_0:5
selectedItem:0
update_1:5
selectedItem:1
shoppingListId:10011 //貌似不用
*/


result = com.yuaoq.yabiz.mobile.order.shoppingcart.ShoppingCartEvents.modifyCart(request,response);
if(result.equals("error")){
    request.setAttribute(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_ERROR);

    return "error"

}else{
    request.setAttribute(ModelService.RESPONSE_MESSAGE, ModelService.RESPOND_SUCCESS);

    return "success"
}

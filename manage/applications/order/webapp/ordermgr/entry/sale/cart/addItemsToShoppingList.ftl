<#if (shoppingLists?exists) && (shoppingCartSize > 0)>
    <@htmlScreenTemplate.renderScreenletBegin id="findOrders" title="${uiLabelMap.OrderAddOrderToShoppingList}" collapsed=false/>
    <#assign index = 0/>
    <#list shoppingCart.items() as cartLine>
        <#if (cartLine.getProductId()?exists) && !cartLine.getIsPromo()>
        <input type="hidden" name="selectedItem" value="${index}"/>
        </#if>
        <#assign index = index + 1/>
    </#list>

    <select name='shoppingListId' class="form-control">
        <#list shoppingLists as shoppingList>
            <option value='${shoppingList.shoppingListId}'>${shoppingList.getString("listName")}</option>
        </#list>
        <option value="">---</option>
        <option value="">${uiLabelMap.OrderNewShoppingList}</option>
    </select>
<input type="submit" class="btn btn-primary btn-sm" value="${uiLabelMap.OrderAddToShoppingList}"/>

</#if>

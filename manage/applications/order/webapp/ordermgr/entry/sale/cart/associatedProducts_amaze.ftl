<#--
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
-->
<#assign associatedProducts = Static["org.ofbiz.order.shoppingcart.product.ProductDisplayWorker"].getRandomCartProductAssoc(request, true)?if_exists>
<#if associatedProducts?has_content>
  <div class="am-panel am-panel-default">
    <div class="am-panel-hd am-cf">
      ${uiLabelMap.OrderHelpAlsoInterestedIn}
    </div>
    <div class="am-panel-bd am-collapse am-in">
        <div class="am-g">
        <#-- random complementary products -->
        <#list associatedProducts as assocProduct>


              ${setRequestAttribute("optProduct", assocProduct)}
              ${setRequestAttribute("listIndex", assocProduct_index)}
              ${screens.render(productsummaryScreen)}


          <#if assocProduct_has_next>
                <hr/>
          </#if>
        </#list>
      </div>
    </div>
  </div>
</#if>

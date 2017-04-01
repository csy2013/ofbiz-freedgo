/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import org.ofbiz.base.util.UtilDateTime
import org.ofbiz.party.contact.ContactMechWorker

context.nowStr = UtilDateTime.nowTimestamp()

productStoreId = parameters.productStoreId
productStore = delegator.findOne("ProductStore", [productStoreId : productStoreId], false)

context.productStore = productStore
context.productStore = productStoreId
showOld = "true".equals(request.getParameter("SHOW_OLD"))
context.showOld = new Boolean(showOld)

context.contactMeches = ContactMechWorker.getProductStoreContactMechValueMaps(delegator, productStoreId, showOld, null)

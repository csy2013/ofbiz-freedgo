/*******************************************************************************
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
 *******************************************************************************/
package org.ofbiz.product.facility;

import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericEntityException;
import org.ofbiz.entity.GenericValue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Product Information Related Events
 */
public class FacilityEvents {

    public static final String module = org.ofbiz.product.facility.FacilityEvents.class.getName();
    public static final String resource = "ProductErrorUiLabels";

    /**
     *获取实体店 的 信息和经纬度 Add by xdw at 2015.1.15
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public static final String findFacilityAndGeoPoint(HttpServletRequest request, HttpServletResponse response) {
      String longitude = request.getParameter("longitude");
      String latitude = request.getParameter("latitude");
      Delegator delegator = (Delegator) request.getAttribute("delegator");
      DecimalFormat df = new DecimalFormat("0.00");

      if (UtilValidate.isEmpty(longitude) || UtilValidate.isEmpty(latitude)) {
          return "error";
      }

      Map<String, Object> facilityMap = UtilMisc.toMap("facilityTypeId", "RETAIL_STORE");

      List<GenericValue> facilityAndGeoPointList = null;
      try {
        Double lon = Double.valueOf(longitude);
        Double lat = Double.valueOf(latitude);
        facilityAndGeoPointList = delegator.findByAnd("FacilityAndGeoPoint", facilityMap);
        if (facilityAndGeoPointList != null && facilityAndGeoPointList.size() > 0) {
          for(GenericValue item : facilityAndGeoPointList){
            if(UtilValidate.isEmpty(item.get("latitude")) || UtilValidate.isEmpty(item.get("longitude"))){
              item.remove();
            }else{
              double R = 6371; 
              double distance = 0.0;
              double lat1 = Double.valueOf((String) item.get("latitude"));
              double dLat = (Double.valueOf((String) item.get("latitude")) - lat) * Math.PI / 180; 
              double dLon = (Double.valueOf((String) item.get("longitude")) - lon) * Math.PI / 180; 
              double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) 
                      + Math.cos(lat1 * Math.PI / 180) 
                      * Math.cos(lat * Math.PI / 180) * Math.sin(dLon / 2) 
                      * Math.sin(dLon / 2); 
              distance = (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))) * R;
              if(distance > 10){
                facilityAndGeoPointList.remove(item);
              }else{
                item.put("elevation", BigDecimal.valueOf(Double.valueOf(df.format(distance))));
              }
            }
          }
          GenericValue temp;
          for (int i = 0; i < facilityAndGeoPointList.size(); i++) {
            Double num = Double.valueOf((String) facilityAndGeoPointList.get(i).get("latitude")) ;
            for (int j = i; j < facilityAndGeoPointList.size(); j++) {
              Double b = Double.valueOf((String) facilityAndGeoPointList.get(j).get("latitude"));
              if (num.compareTo(b)>0) {
                temp = facilityAndGeoPointList.get(j);
                facilityAndGeoPointList.set(j, facilityAndGeoPointList.get(i));
                facilityAndGeoPointList.set(i, temp);
              }
            }
          }
        }
        request.setAttribute("facilityAndGeoPointList", facilityAndGeoPointList);
        return "success";
      } catch (GenericEntityException e) {
        e.printStackTrace();
        return "error";
      }
  }
}
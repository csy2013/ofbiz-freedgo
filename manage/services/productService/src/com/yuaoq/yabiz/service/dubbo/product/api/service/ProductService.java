package com.yuaoq.yabiz.service.dubbo.product.api.service;

import org.ofbiz.service.GenericServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by tusm on 15/11/24.
 */
public interface ProductService {

    Map<String,Object> keywordSearch(HttpServletRequest request, HttpServletResponse response);

    Map<String,Object> productSummary(Map<String, ? extends Object> context) throws GenericServiceException;

    Map<String, Object> productReview(Map<String, ? extends Object> context) throws GenericServiceException;

    Map<String, Object> productContent(Map<String, ? extends Object> context) throws GenericServiceException;

    Map<String, Object> productCategoryList(Map<String, ? extends Object> context) throws GenericServiceException;
}

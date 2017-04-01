package com.yuaoq.yabiz.service.dubbo.party.api.service;

import java.util.Map;

/**
 * Created by tusm on 15/12/13.
 */
public interface PartyService {

    public Map<String, Object> customerBaseQuery(Map<String, ? extends Object> context);

    public Map<String, Object> customeAddressQuery(Map<String, ? extends Object> context);

}

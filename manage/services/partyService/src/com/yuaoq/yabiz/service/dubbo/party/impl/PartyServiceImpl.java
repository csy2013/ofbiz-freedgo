package com.yuaoq.yabiz.service.dubbo.party.impl;

import com.yuaoq.yabiz.service.dubbo.party.api.service.PartyService;

import java.util.Map;

/**
 * Created by changsy on 15/12/13.
 */
public class PartyServiceImpl implements PartyService {

    public static final String module = PartyServiceImpl.class.getName();
    public static final String resource = "partyServiceUiLabels";
    public static final String resourceError = "partyServiceUiErrorLabels";

    public Map<String, Object> customerBaseQuery(Map<String, ? extends Object> context) {

        return null;
    }

    @Override
    public Map<String, Object> customeAddressQuery(Map<String, ? extends Object> context) {
        return null;
    }
}

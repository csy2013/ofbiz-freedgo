package com.yuaoq.yabiz.dubbo.provider.service;

/**
 * Created by tusm on 15/11/30.
 */
public interface Service {

    public String getDelegatorName();
    public void setDelegatorName(String delegatorName);
    public String getDispatcherName();
    public void setDispatcherName(String dispatcherName);
}

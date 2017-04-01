package com.yuaoq.yabiz.dubbo.provider.service;

import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.DelegatorFactory;
import org.ofbiz.service.GenericDispatcher;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ModelService;

/**
 * Created by tusm on 15/11/30.
 */
public class ServiceImpl implements Service{

    protected LocalDispatcher dispatcher = null;
    private static boolean exportAll = false;
    private String delegatorName ;
    private String dispatcherName ;


    public void checkExportFlag(String serviceName) throws GenericServiceException {
        ModelService model = getDispatcher().getDispatchContext().getModelService(serviceName);
        if (!model.export && !exportAll) {
            // TODO: make this log on the server rather than the client
            //Debug.logWarning("Attempt to invoke a non-exported service: " + serviceName, module);
            throw new GenericServiceException("Cannot find requested service");
        }
    }

    public LocalDispatcher getDispatcher() {
        Delegator delegator = DelegatorFactory.getDelegator(getDelegatorName());
        LocalDispatcher dispatcher = GenericDispatcher.getLocalDispatcher(getDispatcherName(), delegator);
        return dispatcher;
    }



    public void setDispatcher(LocalDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }


    public String getDelegatorName() {
        return delegatorName;
    }

    public void setDelegatorName(String delegatorName) {
        this.delegatorName = delegatorName;
    }

    public String getDispatcherName() {
        return dispatcherName;
    }

    public void setDispatcherName(String dispatcherName) {
        this.dispatcherName = dispatcherName;
    }
}

package com.yuaoq.yabiz.service.engine;

import com.alibaba.dubbo.rpc.RpcContext;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.ModelService;
import org.ofbiz.service.ServiceDispatcher;
import org.ofbiz.service.engine.GenericAsyncEngine;
import org.ofbiz.service.engine.StandardJavaEngine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 提供调用dubbo服务的engine
 * Created by changsy on 2017/3/25.
 */
public class DubboServiceEngine extends GenericAsyncEngine {
    
    public static final String module = DubboServiceEngine.class.getName();
    
    public DubboServiceEngine(ServiceDispatcher dispatcher) {
        super(dispatcher);
    }
    
    /**
     * @see org.ofbiz.service.engine.GenericEngine#runSyncIgnore(java.lang.String, org.ofbiz.service.ModelService, java.util.Map)
     */
    @Override
    public void runSyncIgnore(String localName, ModelService modelService, Map<String, Object> context) throws GenericServiceException {
        runSync(localName, modelService, context);
    }
    
    /**
     * @see org.ofbiz.service.engine.GenericEngine#runSync(java.lang.String, org.ofbiz.service.ModelService, java.util.Map)
     */
    @Override
    public Map<String, Object> runSync(String localName, ModelService modelService, Map<String, Object> context) throws GenericServiceException {
        
        
        Object result = serviceInvoker(localName, modelService, context);
        
        if (result == null || !(result instanceof Map<?, ?>)) {
            throw new GenericServiceException("Service [" + modelService.name + "] did not return a Map object");
        }
        return UtilGenerics.checkMap(result);
    }
    
    // Invoke the static java method service.
    private Object serviceInvoker(String localName, ModelService modelService, Map<String, Object> context) throws GenericServiceException {
        // static java service methods should be: public Map<String, Object> methodName(DispatchContext dctx, Map<String, Object> context)
        DispatchContext dctx = dispatcher.getLocalContext(localName);
        
        if (modelService == null) {
            Debug.logError("ERROR: Null Model Service.", module);
        }
        if (dctx == null) {
            Debug.logError("ERROR: Null DispatchContext.", module);
        }
        if (context == null) {
            Debug.logError("ERROR: Null Service Context.", module);
        }
        
        Object result = null;
        
        // check the package and method names
        if (modelService.location == null || modelService.invoke == null) {
            throw new GenericServiceException("Service [" + modelService.name + "] is missing location and/or invoke values which are required for execution.");
        }
        
        // get the classloader to use
        ClassLoader cl = null;
        
        if (dctx == null) {
            cl = this.getClass().getClassLoader();
        } else {
            cl = dctx.getClassLoader();
        }
        //如何获取spring dubbo 对应的dubbo service？？？
        try {
            Class<?> c = cl.loadClass(this.getLocation(modelService));
            Method m = c.getMethod(modelService.invoke, DispatchContext.class, Map.class);
            if (Modifier.isStatic(m.getModifiers())) {
                result = m.invoke(null, dctx, context);
            } else {
                result = m.invoke(c.newInstance(), dctx, context);
            }
        } catch (ClassNotFoundException cnfe) {
            throw new GenericServiceException("Cannot find service [" + modelService.name + "] location class", cnfe);
        } catch (NoSuchMethodException nsme) {
            throw new GenericServiceException("Service [" + modelService.name + "] specified Java method (invoke attribute) does not exist", nsme);
        } catch (SecurityException se) {
            throw new GenericServiceException("Service [" + modelService.name + "] Access denied", se);
        } catch (IllegalAccessException iae) {
            throw new GenericServiceException("Service [" + modelService.name + "] Method not accessible", iae);
        } catch (IllegalArgumentException iarge) {
            throw new GenericServiceException("Service [" + modelService.name + "] Invalid parameter match", iarge);
        } catch (InvocationTargetException ite) {
            throw new GenericServiceException("Service [" + modelService.name + "] target threw an unexpected exception", ite.getTargetException());
        } catch (NullPointerException npe) {
            throw new GenericServiceException("Service [" + modelService.name + "] ran into an unexpected null object", npe);
        } catch (ExceptionInInitializerError eie) {
            throw new GenericServiceException("Service [" + modelService.name + "] Initialization failed", eie);
        } catch (Throwable th) {
            throw new GenericServiceException("Service [" + modelService.name + "] Error or unknown exception", th);
        }
        
        return result;
    }
    
  
    
    
    
}

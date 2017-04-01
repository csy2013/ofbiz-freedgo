package com.yuaoq.yabiz.container;


import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Container;
import org.ofbiz.base.container.ContainerException;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.DelegatorFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by tusm on 15/11/28.
 */
public class DubboContainer implements org.ofbiz.base.container.Container {

    public static final String module = DubboContainer.class.getName();

    protected String[] configFileLocation = null;

    public static final String CONTAINER_KEY = "dubbo.container";

    public static final String SHUTDOWN_HOOK_KEY = "dubbo.shutdown.hook";

    private static final ExtensionLoader<Container> loader = ExtensionLoader.getExtensionLoader(Container.class);

    private static volatile boolean running = true;

    protected Delegator delegator = null;

    final List<Container> containers = new ArrayList<Container>();

    @Override
    public void init(String[] args, String configFile) throws ContainerException{
        if (args == null || args.length == 0) {
            String config = ConfigUtils.getProperty(CONTAINER_KEY, loader.getDefaultExtensionName());
            args = Constants.COMMA_SPLIT_PATTERN.split(config);
            configFileLocation = args;
        }
    }

    @Override
    public boolean start() throws ContainerException {
        try {
            for (int i = 0; i < configFileLocation.length; i++) {
                containers.add(loader.getExtension(configFileLocation[i]));
            }
            Debug.logInfo("Use container type(" + Arrays.toString(configFileLocation) + ") to run dubbo serivce.",module);

            if ("true".equals(System.getProperty(SHUTDOWN_HOOK_KEY))) {
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        for (Container container : containers) {
                            try {
                                container.stop();
                                Debug.logInfo("Dubbo " + container.getClass().getSimpleName() + " stopped!",module);
                            } catch (Throwable t) {
                                Debug.logError(t.getMessage(), module);
                            }
                            synchronized (DubboContainer.class) {
                                running = false;
                                DubboContainer.class.notify();
                            }
                        }
                    }
                });
            }

            for (Container container : containers) {
                container.start();
                Debug.logInfo("Dubbo " + container.getClass().getSimpleName() + " started!",module);
            }
            System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " Dubbo service server started!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            Debug.logError(e.getMessage(), module);
            System.exit(1);
        }

        this.delegator = DelegatorFactory.getDelegator("default");

        synchronized (DubboContainer.class) {
            while (running) {
                try {
                    DubboContainer.class.wait();
                } catch (Throwable e) {
                }
            }
        }
        return true;
    }



    @Override
    public void stop() throws ContainerException {
        for (Container container : containers) {
            try {
                container.stop();
                Debug.logInfo("Dubbo " + container.getClass().getSimpleName() + " stopped!",module);
            } catch (Throwable t) {
                Debug.logError(t.getMessage(), module);
            }
            synchronized (DubboContainer.class) {
                running = false;
                DubboContainer.class.notify();
            }
        }
    }
}

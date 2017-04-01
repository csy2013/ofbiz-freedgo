package org.ofbiz.amaze.widget;

import org.ofbiz.amaze.widget.screen.AmazeIterateSectionWidget;
import org.ofbiz.amaze.widget.screen.AmazeModelScreenWidget;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.widget.WidgetFactory;
import org.ofbiz.widget.WidgetLoader;
import org.ofbiz.widget.screen.ModelScreenWidget;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by changsy on 2015/1/8.
 * desc: 实现amaze针对widget组件的css定义 目前只考虑AMAZE
 */
public class AmazeWidgetLoaderImpl implements WidgetLoader {
    public static final String module = AmazeWidgetLoaderImpl.class.getName();

    /**
     * 实现类必须要有一个无参的构造方法，ServiceLoader支持懒加载方式，只有当需要的时候，才去加载服务的实现类
     */
    public AmazeWidgetLoaderImpl() {
    }

    @Override
    public void loadWidgets() {
        loadCustomAmazeWidgets();
    }
    /**
     * Loads the custom OFBiz screen widgets.
     * 另：当用户选择theme后触发
     */
    public static void loadCustomAmazeWidgets() {
//        获取本身扩展的class
        for (Class<?> clz: AmazeModelScreenWidget.class.getDeclaredClasses()) {
            try {
                // Subclass of ModelScreenWidget and non-abstract
                if (ModelScreenWidget.class.isAssignableFrom(clz) && (clz.getModifiers() & Modifier.ABSTRACT) == 0) {
                    try {
                        Field field = clz.getField("TAG_NAME");
                        Object fieldObject = field.get(null);
                        if (fieldObject != null) {
                            Class<? extends ModelScreenWidget> widgetClass = UtilGenerics.cast(clz);
                            WidgetFactory.registerScreenWidget("amaze."+fieldObject.toString(), widgetClass);
                        }
                    } catch (Exception e) {}
                }
            } catch (Exception e) {
                Debug.logError(e, module);
            }
        }

        try {
            Class<? extends ModelScreenWidget> widgetClass = UtilGenerics.cast(AmazeIterateSectionWidget.class);
            WidgetFactory.registerScreenWidget("amaze.iterate-section",widgetClass );
        } catch (Exception e) {}
    }

}

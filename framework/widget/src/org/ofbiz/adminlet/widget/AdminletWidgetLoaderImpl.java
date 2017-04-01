package org.ofbiz.adminlet.widget;

import org.ofbiz.adminlet.widget.screen.AdminletIterateSectionWidget;
import org.ofbiz.adminlet.widget.screen.AdminletModelScreenWidget;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.widget.WidgetFactory;
import org.ofbiz.widget.WidgetLoader;
import org.ofbiz.widget.screen.ModelScreenWidget;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by tusm on 15/8/8.
 */
public class AdminletWidgetLoaderImpl implements WidgetLoader {
    public static final String module = AdminletWidgetLoaderImpl.class.getName();

    /**
     * 实现类必须要有一个无参的构造方法，ServiceLoader支持懒加载方式，只有当需要的时候，才去加载服务的实现类
     */
    public AdminletWidgetLoaderImpl() {
    }

    @Override
    public void loadWidgets() {
        loadCustomAdminletWidgets();
    }
    /**
     * Loads the custom OFBiz screen widgets.
     * 另：当用户选择theme后触发
     */
    public static void loadCustomAdminletWidgets() {
//        获取本身扩展的class
        for (Class<?> clz: AdminletModelScreenWidget.class.getDeclaredClasses()) {
            try {
                // Subclass of ModelScreenWidget and non-abstract
                if (ModelScreenWidget.class.isAssignableFrom(clz) && (clz.getModifiers() & Modifier.ABSTRACT) == 0) {
                    try {
                        Field field = clz.getField("TAG_NAME");
                        Object fieldObject = field.get(null);
                        if (fieldObject != null) {
                            Class<? extends ModelScreenWidget> widgetClass = UtilGenerics.cast(clz);
                            WidgetFactory.registerScreenWidget("adminlet." + fieldObject.toString(), widgetClass);
                        }
                    } catch (Exception e) {}
                }
            } catch (Exception e) {
                Debug.logError(e, module);
            }
        }

        try {
            Class<? extends ModelScreenWidget> widgetClass = UtilGenerics.cast(AdminletIterateSectionWidget.class);
            WidgetFactory.registerScreenWidget("adminlet.iterate-section",widgetClass );
        } catch (Exception e) {}
    }

}
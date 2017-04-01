package org.ofbiz.coloradmin.widget;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilGenerics;
import org.ofbiz.coloradmin.widget.screen.ColoradminIterateSectionWidget;
import org.ofbiz.coloradmin.widget.screen.ColoradminModelScreenWidget;
import org.ofbiz.widget.WidgetFactory;
import org.ofbiz.widget.WidgetLoader;
import org.ofbiz.widget.screen.ModelScreenWidget;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by tusm on 15/8/8.
 */
public class ColoradminWidgetLoaderImpl implements WidgetLoader {
    public static final String module = ColoradminWidgetLoaderImpl.class.getName();

    /**
     * 实现类必须要有一个无参的构造方法，ServiceLoader支持懒加载方式，只有当需要的时候，才去加载服务的实现类
     */
    public ColoradminWidgetLoaderImpl() {
    }

    @Override
    public void loadWidgets() {
        loadCustomBootsWidgets();
    }
    /**
     * Loads the custom OFBiz screen widgets.
     * 另：当用户选择theme后触发
     */
    public static void loadCustomBootsWidgets() {
//        获取本身扩展的class
        for (Class<?> clz: ColoradminModelScreenWidget.class.getDeclaredClasses()) {
            try {
                // Subclass of ModelScreenWidget and non-abstract
                if (ModelScreenWidget.class.isAssignableFrom(clz) && (clz.getModifiers() & Modifier.ABSTRACT) == 0) {
                    try {
                        Field field = clz.getField("TAG_NAME");
                        Object fieldObject = field.get(null);
                        if (fieldObject != null) {
                            Class<? extends ModelScreenWidget> widgetClass = UtilGenerics.cast(clz);
                            WidgetFactory.registerScreenWidget("coloradmin." + fieldObject.toString(), widgetClass);
                        }
                    } catch (Exception e) {}
                }
            } catch (Exception e) {
                Debug.logError(e, module);
            }
        }

        try {
            Class<? extends ModelScreenWidget> widgetClass = UtilGenerics.cast(ColoradminIterateSectionWidget.class);
            WidgetFactory.registerScreenWidget("coloradmin.iterate-section",widgetClass );
        } catch (Exception e) {}
    }

}
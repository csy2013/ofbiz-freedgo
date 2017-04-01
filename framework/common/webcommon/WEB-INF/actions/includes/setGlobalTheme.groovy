import org.ofbiz.amaze.widget.AmazeWidgetLoaderImpl;

/**
 * Created by changsy on 2015/1/11.
 * 设置themeId 供widget加载使用
 */

if( AmazeWidgetLoaderImpl.visualThemeId != context.visualThemeId){
    AmazeWidgetLoaderImpl.visualThemeId = context.visualThemeId;
    if(!context.visualThemeId.equals("")){
        out.println("context.visualThemeId="+context.visualThemeId);
        if(context.visualThemeId.equals("amaze")){
            AmazeWidgetLoaderImpl.loadCustomAmazeWidgets();
        }
    }

}


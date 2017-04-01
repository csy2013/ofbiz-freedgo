package com.yuaoq.yabiz.admin.page;

/**
 * Created by tusm on 15/11/8.
 */
public class WebInfo {

    public WebInfo(String id, String location, String name, String uiLabel, String iconStyle, String contextRoot, String basePermission, String title) {
        this.id = id;
        this.location = location;
        this.name = name;
        this.uiLabel = uiLabel;
        this.iconStyle = iconStyle;
        this.contextRoot = contextRoot;
        this.basePermission = basePermission;
        this.title = title;
    }

    private String id ;
    private  String location;
    private String name;
    private String uiLabel;
    private String iconStyle;
    private String contextRoot;
    private String basePermission;
    private String title;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUiLabel() {
        return uiLabel;
    }

    public void setUiLabel(String uiLabel) {
        this.uiLabel = uiLabel;
    }

    public String getIconStyle() {
        return iconStyle;
    }

    public void setIconStyle(String iconStyle) {
        this.iconStyle = iconStyle;
    }
}

package com.example.appmanager;

import android.graphics.drawable.Drawable;

public class AppModel {
    String name;
    String packageName;
    Drawable icon;
    boolean isSystem;

    public AppModel(String name, String packageName, Drawable icon, boolean isSystem) {
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
        this.isSystem = isSystem;
    }
}
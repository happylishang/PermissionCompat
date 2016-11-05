package com.snail.labaffinity.permissioin;

import com.annotation.OnGrantedListener;
import com.snail.labaffinity.activity.BaseActivity;

/**
 * Author: hzlishang
 * Data: 16/11/4 下午7:15
 * Des:
 * version:
 */
public class PermissionCompat {
    public static void requestPermission(BaseActivity activity, String[] strings, OnGrantedListener listener) {
        activity.requestPermissions(strings, listener);
    }

    public static void requestPermission(BasePermissionCompatFragment activity, String[] strings, OnGrantedListener listener) {
        activity.requestPermissions(strings, listener);
    }
}

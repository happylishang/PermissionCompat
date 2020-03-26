package com.snail.labaffinity.activity;

import android.app.Activity;
import android.content.Context;

import com.snail.permissioncompat.SimpleOnGrantedListener;

import java.util.Map;

/**
 * Author: snail
 * Data: 2018/12/14.
 * Des:
 * version:
 */
public class AbstractBasePermissionUtil {


    protected void showGuildDialog(Activity activity,
                                   final SimpleOnGrantedListener listener,
                                   final int requestCode,
                                   final Map<String, Integer> result,
                                   final String tips) {
        showPermissionDialog(activity, listener, requestCode, result, tips);
    }

    protected void showNeverAskGuildDialog(Activity activity,
                                           final SimpleOnGrantedListener listener,
                                           final int requestCode,
                                           final Map<String, Integer> result,
                                           final String tips) {
        showPermissionDialog(activity, listener, requestCode, result, tips);
    }

    private static void showPermissionDialog(final Context context,
                                             final SimpleOnGrantedListener listener,
                                             final int requestCode,
                                             final Map<String, Integer> result, String tips) {

    }
}

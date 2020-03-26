package com.snail.permissioncompat;


import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.v4.app.FragmentActivity;

import java.lang.reflect.Method;

/**
 * Author: snail
 * Data: 2018/3/28.
 * Des: 原则，权限 尽量不要同时申请太多，尽量不要不相干的组放在一块
 * version: 6.0以下的，去获取权限的时候，通过动态权限检查，肯定是全部授权，不需要处理，如果定制rom启用了 6.0以下的逻辑，
 * 则自动触发系统弹窗即可，不要APP自己申请权限，而且是个同步阻塞的过程，不需要考虑
 */
public class PermissionCompatUtil {

    public static void requestPermission(FragmentActivity activity, String[] permission, int requestCode, OnGrantedListener listener) {
        if (activity == null) {
            return;
        }
        if (checkPermissionsMarshmallow(activity, permission)) {
            listener.onGranted(requestCode, permission);
            return;
        }
        HelpDialogFragment fragment = HelpDialogFragment.newInstance();
        fragment.requestAllPermissions(activity, permission, requestCode, listener);
    }

    /**
     * 对于sdk 在23以下，不需要考虑权限问题， 因为就算是检测到了，你也没有主动的触发手段，因为弹窗是在使用服务的时候才会弹出来的
     */
    public static boolean checkPermissionsMarshmallow(Context context, String... permissions) {
        return Build.VERSION.SDK_INT < 23 || snail.permissioncompat.PermissionUtils.hasSelfPermissions(context, permissions);
    }


    /**
     * 6.0之后的权限检查
     */
    public static boolean checkPermissionMarshmallow(Context context, String permission) {
        return Build.VERSION.SDK_INT < 23 || snail.permissioncompat.PermissionUtils.hasSelfPermissions(context, permission);
    }

    /**
     * 6.0之前权限检查太乱，直接用String的权限来查询，兼容一般  ,不推荐使用，6.0之前的权限管理太随机，有原理，但是无规律。检测方式基本处于不可见状态
     * 尤其注意，不要用这个方法检测targetSdkVersion>=23M时候，M之后的权限，会有问题，似乎是revoke的漏洞，
     *
     * 唯一可以能保证的是对通知权限的管理 OP_POST_NOTIFICATION （可能还有个悬浮窗权限）
     */
    @Deprecated
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean checkPermissionKitkat(Context context, int op) {
        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        try {
            Class sCheckClass = Class.forName(AppOpsManager.class.getName());
            Method sCheckMethod = sCheckClass.getDeclaredMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
            sCheckMethod.setAccessible(true);
            return ((Integer) sCheckMethod.invoke(mAppOps, op, appInfo.uid, context.getApplicationContext().getPackageName()) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}

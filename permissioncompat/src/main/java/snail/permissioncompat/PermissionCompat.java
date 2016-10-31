package snail.permissioncompat;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.annotation.annotion.OnGrantedListener;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: hzlishang
 * Data: 16/10/28 下午5:39
 * Des:
 * version:
 */
public class PermissionCompat {


    static void checkPermmisons(BasePermissionCompatActivity target) {
        if (PermissionUtils.hasSelfPermissions(target, null)) {
        } else {
            if (PermissionUtils.shouldShowRequestPermissionRationale(target, null)) {
//                target
            } else {
//                ActivityCompat.startRequest(target, {"",""},1);
            }
        }
    }

    private static int sNextRequestCode;
    static final Map<Class<?>, OnGrantedListener<? extends BasePermissionCompatActivity>> BINDERS = new LinkedHashMap<>();

    // 分批次请求权限
    static void requestPermission(BasePermissionCompatActivity target, String[] permissions) {
        Class<?> targetClass = target.getClass();
        try {
            int requestCode = getNextRequestCode();
            OnGrantedListener<? extends BasePermissionCompatActivity> listener = findOnGrantedListenerForClass(targetClass);
            startRequest(target, listener, permissions);
        } catch (Exception e) {
            throw new RuntimeException("Unable to bind views for " + targetClass.getName(), e);
        }
    }

    private static OnGrantedListener<? extends BasePermissionCompatActivity> findOnGrantedListenerForClass(Class<?> cls)
            throws IllegalAccessException, InstantiationException {
        OnGrantedListener<? extends BasePermissionCompatActivity> viewBinder = BINDERS.get(cls);
        if (viewBinder != null) {
            return viewBinder;
        }
        String clsName = cls.getName();
        try {
            Class<?> listenerClass = Class.forName(clsName + "$$OnGrantedListener");
            viewBinder = (OnGrantedListener<? extends BasePermissionCompatActivity>) listenerClass.newInstance();
        } catch (ClassNotFoundException e) {
            viewBinder = findOnGrantedListenerForClass(cls.getSuperclass());
        }
        BINDERS.put(cls, viewBinder);
        return viewBinder;
    }

    private static void startRequest(BasePermissionCompatActivity target, OnGrantedListener listener, final @NonNull String[] permissions) {
        int requestCode = getNextRequestCode();
        target.addOnGrantedListener(requestCode, listener);
        ActivityCompat.requestPermissions(target, permissions, getNextRequestCode());
    }

    private static int getNextRequestCode() {
        return sNextRequestCode++;
    }
}

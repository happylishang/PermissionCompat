package com.snail.labaffinity.activity;

import android.Manifest;
import android.support.v4.app.FragmentActivity;


import com.snail.labaffinity.utils.ToastUtil;
import com.snail.permissioncompat.PermissionCompatUtil;
import com.snail.permissioncompat.SimpleOnGrantedListener;

import java.util.Map;

/**
 * Author: snail
 * Data: 2018/11/20.
 * Des: 不给权限统一提示好了
 * version:
 */
public class IMEIPermissionUtil extends AbstractBasePermissionUtil {

    private static class SingleTonHolder {
        private static final IMEIPermissionUtil INSTANCE = new IMEIPermissionUtil();
    }

    public static IMEIPermissionUtil getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private IMEIPermissionUtil() {

    }

    private static final String[] PERMISSION = {Manifest.permission.READ_PHONE_STATE};

    public void requestPermission(final FragmentActivity activity, final SimpleOnGrantedListener listener) {

        PermissionCompatUtil.requestPermission(activity, PERMISSION, 0, new SimpleOnGrantedListener() {
            @Override
            public void onGranted(int requestCode, String[] permissions) {

                ToastUtil.show("授权成功");
            }

            @Override
            public void onDenied(int requestCode, Map<String, Integer> result) {

                ToastUtil.show("授权失败");
            }

            @Override
            public void onNeverAsk(int requestCode, Map<String, Integer> result) {
                ToastUtil.show("永不询问");
            }

            @Override
            public void onGotoSetting() {

            }
        });
    }


}

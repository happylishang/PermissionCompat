package com.snail.labaffinity.activity;

import android.Manifest;
import android.os.Bundle;

import com.annotation.ActivityPermission;
import com.annotation.OnDenied;
import com.annotation.OnGranted;
import com.annotation.OnNeverAsk;
import com.annotation.OnShowRationale;
import com.snail.labaffinity.R;
import com.snail.labaffinity.utils.ToastUtil;

import butterknife.OnClick;
import cn.campusapp.router.annotation.RouterMap;
import snail.permissioncompat.PermissionCompat;

/**
 * Author: hzlishang
 * Data: 16/10/12 上午9:56
 * Des:
 * version:
 */
@ActivityPermission
@RouterMap({"activity://second"})
public class SecondActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
    }

    @OnGranted(value = {Manifest.permission.CAMERA})
    void grantedC() {
        ToastUtil.show("CAMERA granted");
    }

    @OnDenied(value = {Manifest.permission.CAMERA})
    void onDeniedC() {
        ToastUtil.show(" CAMERA onDenied");
    }

    @OnNeverAsk(value = {Manifest.permission.CAMERA})
    void OnNeverAskC() {
        ToastUtil.show(" CAMERA OnNeverAsk");
        starSettingActivityForPermission(1000);
    }

    @OnShowRationale(value = {Manifest.permission.CAMERA})
    void OnShowRationaleC() {
        ToastUtil.show(" CAMERA OnShowRationale");
    }

    @OnClick(R.id.camera)
    void camera() {
        PermissionCompat.requestPermission(this, new String[]{Manifest.permission.CAMERA});
    }

    @OnGranted(value = {Manifest.permission.CALL_PHONE})
    void grantedP() {
        ToastUtil.show("Phone granted");
    }

    @OnDenied(value = {Manifest.permission.CALL_PHONE})
    void onDeniedP() {
        ToastUtil.show("Phone onDenied");
    }

    @OnNeverAsk(value = {Manifest.permission.CALL_PHONE})
    void OnNeverAskP() {
        ToastUtil.show("Phone OnNeverAsk");
        starSettingActivityForPermission(1000);
    }

    @OnShowRationale(value = {Manifest.permission.CALL_PHONE})
    void OnShowRationaleP() {
        ToastUtil.show("Phone OnShowRationale");
    }


    @OnClick(R.id.phone)
    void phone() {
        PermissionCompat.requestPermission(this, new String[]{Manifest.permission.CALL_PHONE});
    }


    @OnGranted(value = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA})
    void grantedPC() {
        ToastUtil.show("Phone CAMERA  granted");
    }

    @OnDenied(value = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA})
    void onDeniedPC() {
        ToastUtil.show("Phone CAMERA onDenied");
    }

    @OnNeverAsk(value = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA})
    void OnNeverAskPC() {
        ToastUtil.show("Phone CAMERA  OnNeverAsk");
        starSettingActivityForPermission(1000);
    }

    @OnShowRationale(value = {Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA})
    void OnShowRationalePC() {
        ToastUtil.show("Phone CAMERA OnShowRationale");
    }


    @OnClick(R.id.phone_c)
    void phoneCamera() {
        PermissionCompat.requestPermission(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA});
    }

    @OnClick(R.id.no_match)
    void no_match() {
        PermissionCompat.requestPermission(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA});
    }
}

package com.snail.labaffinity.activity;

import android.Manifest;
import android.os.Bundle;

import com.annotation.ActivityPermission;
import com.annotation.OnDenied;
import com.annotation.OnGranted;
import com.annotation.OnNeverAsk;
import com.annotation.OnShowRationale;
import com.snail.labaffinity.R;
import com.snail.labaffinity.utils.LogUtils;

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
    void granted() {
        LogUtils.v("granted");
    }

    @OnDenied(value = {Manifest.permission.CAMERA})
    void onDenied() {
        LogUtils.v("onDenied");
    }

    @OnNeverAsk(value = {Manifest.permission.CAMERA})
    void OnNeverAsk() {
        LogUtils.v("OnNeverAsk");
        starSettingActivityForPermission(1000);
    }

    @OnShowRationale(value = {Manifest.permission.CAMERA})
    void OnShowRationale() {
        LogUtils.v("OnShowRationale");
    }

    @OnClick(R.id.get)
    void get() {
        PermissionCompat.requestPermission(this, new String[]{Manifest.permission.CAMERA});
    }
}

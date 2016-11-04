package com.snail.labaffinity.activity;

import android.Manifest;
import android.os.Bundle;

import com.annotation.ActivityPermission;
import com.annotation.OnGrantedListener;
import com.snail.labaffinity.R;
import com.snail.labaffinity.utils.ToastUtil;

import java.util.Arrays;

import butterknife.OnClick;
import cn.campusapp.router.annotation.RouterMap;

/**
 * Author: hzlishang
 * Data: 16/10/12 上午9:56
 * Des:
 * version:
 */
@ActivityPermission
@RouterMap({"activity://second"})
public class SecondActivity extends BaseActivity implements OnGrantedListener<SecondActivity> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
    }


    @OnClick(R.id.camera)
    void camera() {
        PermissionCompat.requestPermission(this, new String[]{Manifest.permission.CAMERA}, this);
    }

    @OnClick(R.id.phone)
    void phone() {
        PermissionCompat.requestPermission(this, new String[]{Manifest.permission.CALL_PHONE}, this);
    }

    // 根据permissions自行处理，可合并，可分开
    @Override
    public void onGranted(SecondActivity target, String[] permissions) {

        ToastUtil.show(Arrays.toString(permissions) + " onGranted");
    }

    @Override
    public void onDenied(SecondActivity target, String[] permissions) {

        ToastUtil.show(Arrays.toString(permissions) + " onDenied");
    }

    @Override
    public void onNeverAsk(SecondActivity target, String[] permissions) {

        ToastUtil.show(Arrays.toString(permissions) + " onNeverAsk");
    }

    @Override
    public void onShowRationale(SecondActivity target, String[] permissions) {

        ToastUtil.show(Arrays.toString(permissions) + " onShowRationale");
    }
}

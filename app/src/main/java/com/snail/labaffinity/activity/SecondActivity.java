package com.snail.labaffinity.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import com.annotation.ActivityPermission;
import com.annotation.OnGrantedListener;
import com.snail.labaffinity.R;
import com.snail.labaffinity.permissioin.PermissionCompat;
import com.snail.labaffinity.utils.LogUtils;
import com.snail.labaffinity.utils.ToastUtil;

import java.util.Arrays;

import butterknife.OnClick;
import cn.campusapp.router.annotation.RouterMap;

/**
 * Author: hzlishang
 * Data: 16/10/12 上午9:56
 * Des:基于回调比较方便同MVP结合，监听实现放在persenter里面，
 * 编码阶段硬性规定回调完整性
 * version:
 */
@ActivityPermission
@RouterMap({"activity://second"})
public class SecondActivity extends BaseActivity implements OnGrantedListener<SecondActivity> {

    private static final String[] P_CALL = new String[]{Manifest.permission.CALL_PHONE};
    private static final String[] P_CAMERA = new String[]{Manifest.permission.CAMERA};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
    }

    @OnClick(R.id.fragment)
    void fragment() {
        Intent intent = new Intent(this, PFragmentActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.camera)
    void camera() {
        PermissionCompat.requestPermission(this, P_CALL, this);
    }

    @OnClick(R.id.phone)
    void phone() {
        PermissionCompat.requestPermission(this, P_CAMERA, this);
    }

    // 根据permissions自行处理，可合并，可分开
    @Override
    public void onGranted(SecondActivity target, String[] permissions) {
        if (Arrays.equals(permissions, P_CALL)) {
            LogUtils.v("P_CALL");
        } else if (Arrays.equals(permissions, P_CAMERA)) {
            LogUtils.v("P_CAMERA");
        } else {
            LogUtils.v("else");
        }
        ToastUtil.show(Arrays.toString(permissions) + " onGranted");
    }

    @Override
    public void onDenied(SecondActivity target, String[] permissions) {
        if (Arrays.equals(permissions, P_CALL)) {
            LogUtils.v("P_CALL");
        } else if (Arrays.equals(permissions, P_CAMERA)) {
            LogUtils.v("P_CAMERA");
        } else {
            LogUtils.v("else");
        }
        ToastUtil.show(Arrays.toString(permissions) + " onDenied");
    }

    @Override
    public void onNeverAsk(SecondActivity target, String[] permissions) {
        if (Arrays.equals(permissions, P_CALL)) {
            LogUtils.v("P_CALL");
        } else if (Arrays.equals(permissions, P_CAMERA)) {
            LogUtils.v("P_CAMERA");
        } else {
            LogUtils.v("else");
        }
        ToastUtil.show(Arrays.toString(permissions) + " onNeverAsk");
    }

    @Override
    public void onShowRationale(SecondActivity target, String[] permissions) {
        if (Arrays.equals(permissions, P_CALL)) {
            LogUtils.v("P_CALL");
        } else if (Arrays.equals(permissions, P_CAMERA)) {
            LogUtils.v("P_CAMERA");
        } else {
            LogUtils.v("else");
        }
        ToastUtil.show(Arrays.toString(permissions) + " onShowRationale");
    }
}

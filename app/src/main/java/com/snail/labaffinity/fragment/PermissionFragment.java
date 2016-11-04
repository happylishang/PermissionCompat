package com.snail.labaffinity.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annotation.FragmentPermission;
import com.annotation.OnDenied;
import com.annotation.OnGranted;
import com.annotation.OnNeverAsk;
import com.annotation.OnShowRationale;
import com.snail.labaffinity.R;
import com.snail.labaffinity.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import snail.permissioncompat.BasePermissionCompatFragment;
import snail.permissioncompat.PermissionCompat;

/**
 * Author: hzlishang
 * Data: 16/11/4 下午3:17
 * Des:
 * version:
 */
@FragmentPermission
public class PermissionFragment extends BasePermissionCompatFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
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

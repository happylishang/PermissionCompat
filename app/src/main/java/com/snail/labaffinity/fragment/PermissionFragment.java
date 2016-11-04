package com.snail.labaffinity.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annotation.FragmentPermission;
import com.annotation.OnGrantedListener;
import com.snail.labaffinity.R;
import com.snail.labaffinity.activity.BasePermissionCompatFragment;
import com.snail.labaffinity.activity.PermissionCompat;
import com.snail.labaffinity.utils.ToastUtil;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: hzlishang
 * Data: 16/11/4 下午3:17
 * Des:
 * version:
 */
@FragmentPermission
public class PermissionFragment extends BasePermissionCompatFragment implements OnGrantedListener<PermissionFragment> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
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
    public void onGranted(PermissionFragment target, String[] permissions) {

        ToastUtil.show(Arrays.toString(permissions) + " onGranted");
    }

    @Override
    public void onDenied(PermissionFragment target, String[] permissions) {

        ToastUtil.show(Arrays.toString(permissions) + " onDenied");
    }

    @Override
    public void onNeverAsk(PermissionFragment target, String[] permissions) {

        ToastUtil.show(Arrays.toString(permissions) + " onNeverAsk");
    }

    @Override
    public void onShowRationale(PermissionFragment target, String[] permissions) {

        ToastUtil.show(Arrays.toString(permissions) + " onShowRationale");
    }
}

package com.snail.permissioncompat;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: snail
 * Data: 2018/3/28.
 * Des: 不入侵工程Activity，用个独立的不可见Framgent实现回调
 * version:
 */
public class HelpDialogFragment extends DialogFragment {

    private String[] mPermissions;
    private int mRequestCode;
    private OnGrantedListener mOnGrantedListener;


    public static HelpDialogFragment newInstance() {
        return new HelpDialogFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (mOnGrantedListener != null) {
            if (snail.permissioncompat.PermissionUtils.getTargetSdkVersion(this.getActivity()) < 23 && !snail.permissioncompat.PermissionUtils.hasSelfPermissions(getActivity(), permissions)) {
                mOnGrantedListener.onGranted(requestCode, permissions);
                return;
            }
            if (snail.permissioncompat.PermissionUtils.verifyPermissions(grantResults)) {
                mOnGrantedListener.onGranted(requestCode, permissions);
            } else {
                Map<String, Integer> hashMap = new HashMap<>();
                for (int i = 0; i < permissions.length; i++) {
                    hashMap.put(permissions[i], grantResults[i]);
                }
                //暂不区分多组权限申请部分选择不再提醒
                if (!snail.permissioncompat.PermissionUtils.shouldShowRequestPermissionRationale(getActivity(), permissions)) {
                    mOnGrantedListener.onNeverAsk(requestCode, hashMap);
                } else {
                    mOnGrantedListener.onDenied(requestCode, hashMap);
                }
            }
        }
        /**移除Fragment*/
        dismissAllowingStateLoss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(mPermissions, mRequestCode);
    }

    public void requestAllPermissions(FragmentActivity activity, String[] permissions, int requestCode, OnGrantedListener listener) {
        /**弄一个看不见的Fragment，来处理回调*/
        show(activity.getSupportFragmentManager(), "");
        mOnGrantedListener = listener;
        mPermissions = permissions;
        mRequestCode = requestCode;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setDimAmount(0);
        }
    }
}

package com.snail.labaffinity.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

import com.annotation.OnGrantedListener;

public class BasePermissionCompatFragment extends Fragment {


    private SparseArray<OnGrantedListener<BasePermissionCompatFragment>> mOnGrantedListeners = new SparseArray<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OnGrantedListener<BasePermissionCompatFragment> listener = mOnGrantedListeners.get(requestCode);
        if (listener == null)
            return;
        if (PermissionUtils.verifyPermissions(grantResults)) {
            listener.onGranted(this, permissions);
        } else {
            if (PermissionUtils.shouldShowRequestPermissionRationale(this.getActivity(), permissions)) {
                listener.onDenied(this, permissions);
            } else {
                listener.onNeverAsk(this, permissions);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mOnGrantedListeners.clear();
        mOnGrantedListeners = null;
    }

    public void requestPermissions(final @NonNull String[] permissions, OnGrantedListener<BasePermissionCompatFragment> onGrantedListener) {
        int requestCode = getNextRequestCode();
        ActivityCompat.requestPermissions(this.getActivity(), permissions, requestCode);
        mOnGrantedListeners.put(requestCode, onGrantedListener);
    }

    private static int sNextCode;

    private static int getNextRequestCode() {
        return sNextCode++;
    }
}

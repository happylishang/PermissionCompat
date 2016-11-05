package com.snail.labaffinity.permissioin;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.annotation.OnGrantedListener;

public class BasePermissionCompatActivity extends AppCompatActivity {


    private SparseArray<OnGrantedListener<BasePermissionCompatActivity>> mOnGrantedListeners = new SparseArray<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OnGrantedListener<BasePermissionCompatActivity> listener = mOnGrantedListeners.get(requestCode);
        if (listener == null)
            return;
        if (PermissionUtils.verifyPermissions(grantResults)) {
            listener.onGranted(this, permissions);
        } else {
            if (PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
                listener.onDenied(this, permissions);
            } else {
                listener.onNeverAsk(this, permissions);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOnGrantedListeners.clear();
        mOnGrantedListeners = null;
    }

    public void requestPermissions(final @NonNull String[] permissions, OnGrantedListener<BasePermissionCompatActivity> onGrantedListener) {
        int requestCode = getNextRequestCode();
        ActivityCompat.requestPermissions(this, permissions, requestCode);
        mOnGrantedListeners.put(requestCode, onGrantedListener);
    }

    private static int sNextCode;

    private static int getNextRequestCode() {
        return sNextCode++;
    }
}

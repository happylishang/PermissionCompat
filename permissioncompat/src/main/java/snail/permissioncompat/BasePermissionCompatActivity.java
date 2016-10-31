package snail.permissioncompat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.annotation.annotation.OnGrantedListener;

/**
 * Author: hzlishang
 * Data: 16/10/12 上午11:37
 * Des:
 * version:
 */
public class BasePermissionCompatActivity extends AppCompatActivity {


    private OnGrantedListener<BasePermissionCompatActivity> mOnGrantedListener;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mOnGrantedListener != null) {
            mOnGrantedListener.onDenied(this, permissions);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOnGrantedListener = null;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }


    public void setOnGrantedListener(OnGrantedListener onGrantedListener) {
        mOnGrantedListener = onGrantedListener;
    }
}

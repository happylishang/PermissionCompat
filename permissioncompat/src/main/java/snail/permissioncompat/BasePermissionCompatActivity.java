package snail.permissioncompat;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.annotation.annotion.OnGrantedListener;

/**
 * Author: hzlishang
 * Data: 16/10/12 上午11:37
 * Des:
 * version:
 */
public class BasePermissionCompatActivity extends AppCompatActivity {


    private SparseArray<OnGrantedListener> mOnGrantedListeners = new SparseArray<>();

    private void addOnGrantedListener(int requestCode, OnGrantedListener onGrantedListener) {
        mOnGrantedListeners.put(requestCode, onGrantedListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OnGrantedListener listener = mOnGrantedListeners.get(requestCode);
        if (listener == null)
            return;

        for (String item : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, item)) {

            } else {

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOnGrantedListeners.clear();
        mOnGrantedListeners = null;
    }

    public void requestPermissions(final @NonNull String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, getNextRequestCode());
    }

    public void requestPermissions(final @NonNull String permission) {
        String[] permissions = new String[1];
        permissions[0] = permission;
        ActivityCompat.requestPermissions(this, permissions, getNextRequestCode());
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    private static int getNextRequestCode() {
        return 1;
    }
}

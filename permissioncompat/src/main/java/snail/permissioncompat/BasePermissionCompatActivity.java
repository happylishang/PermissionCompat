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
            if (PermissionUtils.getTargetSdkVersion(this) < 23 && !PermissionUtils.hasSelfPermissions(this, permissions)) {
                mOnGrantedListener.onGranted(this, permissions);
                return;
            }
            if (PermissionUtils.verifyPermissions(grantResults)) {
                mOnGrantedListener.onGranted(this, permissions);
            } else {
                if (!PermissionUtils.shouldShowRequestPermissionRationale(this, permissions)) {
                    mOnGrantedListener.onNeverAsk(this, permissions);
                } else {
                    mOnGrantedListener.onDenied(this, permissions);
                }
            }
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

    public void jumpToSettingForPermissions() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 1000);
    }
}

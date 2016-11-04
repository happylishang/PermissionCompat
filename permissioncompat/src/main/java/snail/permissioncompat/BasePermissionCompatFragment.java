package snail.permissioncompat;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.annotation.OnGrantedListener;

/**
 * Author: hzlishang
 * Data: 16/10/12 上午11:37
 * Des:
 * version:
 */
public abstract class BasePermissionCompatFragment extends Fragment {


    private OnGrantedListener<BasePermissionCompatFragment> mOnGrantedListener;

    public void setOnGrantedListener(OnGrantedListener<BasePermissionCompatFragment> onGrantedListener) {
        mOnGrantedListener = onGrantedListener;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (mOnGrantedListener != null) {
            if (PermissionUtils.getTargetSdkVersion(this.getActivity()) < 23 && !PermissionUtils.hasSelfPermissions(getActivity(), permissions)) {
                mOnGrantedListener.onGranted(this, permissions);
                return;
            }
            if (PermissionUtils.verifyPermissions(grantResults)) {
                mOnGrantedListener.onGranted(this, permissions);
            } else {
                if (!PermissionUtils.shouldShowRequestPermissionRationale(getActivity(), permissions)) {
                    mOnGrantedListener.onNeverAsk(this, permissions);
                } else {
                    mOnGrantedListener.onDenied(this, permissions);
                }
            }
        }
    }

    /**
     * 去设置页面请求权限，回来后Activity要自己控制后续流程
     */
    public void starSettingActivityForPermission(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, requestCode);
    }

}

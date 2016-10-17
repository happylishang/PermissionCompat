package snail.permissioncompat;

/**
 * Author: hzlishang
 * Data: 16/10/17 下午7:35
 * Des:分组、单个
 * version:
 */
public interface OnGrantedListener {

    void onGranted(String permission);

    void onDenied();
}

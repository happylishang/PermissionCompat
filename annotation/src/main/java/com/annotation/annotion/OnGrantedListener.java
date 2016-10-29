package com.annotation.annotion;

/**
 * Author: hzlishang
 * Data: 16/10/17 下午7:35
 * Des:分组、单个
 * version:
 */
public interface OnGrantedListener {

    void onGranted();

    void onDenied();

    void onNeverAsk();

    void OnShowRationale();
}

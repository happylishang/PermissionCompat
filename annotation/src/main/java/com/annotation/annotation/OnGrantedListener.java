package com.annotation.annotation;

/**
 * Author: hzlishang
 * Data: 16/10/17 下午7:35
 * Des:分组、单个
 * version:
 */
public interface OnGrantedListener<T> {

    void onGranted(T target);

    void onDenied(T target);

    void onNeverAsk(T target);

    void onShowRationale(T target);
}

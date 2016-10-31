package com.annotation.annotation;

/**
 * Author: hzlishang
 * Data: 16/10/17 下午7:35
 * Des:分组、单个
 * version:
 */
public interface OnGrantedListener<T> {

    void onGranted(T target,String[] permissions);

    void onDenied(T target,String[] permissions);

    void onNeverAsk(T target,String[] permissions);

    void onShowRationale(T target,String[] permissions);
}

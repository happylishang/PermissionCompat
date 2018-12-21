package com.snail.permissioncompat;

import java.util.Map;

/**
 * Author: hzlishang
 * Data: 16/10/17 下午7:35
 * Des:分组、单个
 * version:
 */
public interface OnGrantedListener {

    void onGranted(int requestCode, String[] permissions);

    void onDenied(int requestCode, Map<String, Integer> result);

    void onNeverAsk(int requestCode, Map<String, Integer> result);

    void onGotoSetting();
}

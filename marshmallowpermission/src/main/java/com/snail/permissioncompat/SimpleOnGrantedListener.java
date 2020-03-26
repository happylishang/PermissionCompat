package com.snail.permissioncompat;

import java.util.Map;

/**
 * Author: snail
 * Data: 2018/11/8.
 * Des:
 * version:
 */
public class SimpleOnGrantedListener implements OnGrantedListener {
    @Override
    public void onGranted(int requestCode, String[] permissions) {

    }

    @Override
    public void onDenied(int requestCode, Map<String, Integer> result) {

    }

    @Override
    public void onNeverAsk(int requestCode, Map<String, Integer> result) {

    }

    @Override
    public void onGotoSetting() {

    }
}

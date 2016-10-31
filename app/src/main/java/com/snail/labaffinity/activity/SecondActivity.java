package com.snail.labaffinity.activity;

import android.os.Bundle;

import com.annotation.annotation.ActivityPermission;
import com.annotation.annotation.OnDenied;
import com.annotation.annotation.OnGranted;
import com.annotation.annotation.OnNeverAsk;
import com.annotation.annotation.OnShowRationale;
import com.snail.labaffinity.R;

import cn.campusapp.router.annotation.RouterMap;

/**
 * Author: hzlishang
 * Data: 16/10/12 上午9:56
 * Des:
 * version:
 */
@ActivityPermission
@RouterMap({"activity://second"})
public class SecondActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @OnGranted(value = {"first", "second"})
    void granted() {

    }

    @OnDenied(value = {"first", "second"})
    void onDenied() {

    }

    @OnNeverAsk(value = {"first", "second"})
    void OnNeverAsk() {

    }
    @OnShowRationale(value = {"first", "second"})
    void OnShowRationale() {

    }

}

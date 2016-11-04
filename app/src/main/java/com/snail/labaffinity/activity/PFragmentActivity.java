package com.snail.labaffinity.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.snail.labaffinity.R;
import com.snail.labaffinity.fragment.PermissionFragment;

/**
 * Author: hzlishang
 * Data: 16/11/4 下午3:29
 * Des:
 * version:
 */
public class PFragmentActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank);
        Fragment fragment = new PermissionFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }
}

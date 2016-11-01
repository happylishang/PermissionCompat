package com.snail.labaffinity.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.snail.labaffinity.R;

import butterknife.ButterKnife;
import snail.permissioncompat.BasePermissionCompatActivity;

/**
 * Author: hzlishang
 * Data: 16/10/12 上午9:57
 * Des:
 * version:
 */
public class BaseActivity extends BasePermissionCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
    }
}

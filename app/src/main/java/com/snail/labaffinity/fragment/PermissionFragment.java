package com.snail.labaffinity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annotation.FragmentPermission;
import com.snail.labaffinity.R;

import butterknife.ButterKnife;

/**
 * Author: hzlishang
 * Data: 16/11/4 下午3:17
 * Des:
 * version:
 */
@FragmentPermission
public class PermissionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

}

package com.snail.labaffinity.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.snail.labaffinity.R;
import com.snail.labaffinity.service.BackGroundService;
import com.snail.labaffinity.utils.AppProfile;
import com.snail.labaffinity.utils.ToastUtil;
import com.snail.permissioncompat.PermissionCompatUtil;
import com.snail.permissioncompat.SimpleOnGrantedListener;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.campusapp.router.Router;
import cn.campusapp.router.route.ActivityRoute;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(MainActivity.this, BackGroundService.class);
        startService(intent);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://second");
                activityRoute.open();
            }
        });
    }

    @BindView(R.id.first)
    View mView;
    private static final String[] PERMISSION = {Manifest.permission.CAMERA};

    @OnClick(R.id.first)
    void first() {
        PermissionCompatUtil.requestPermission(this, PERMISSION,
                0, new SimpleOnGrantedListener() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onGranted(int requestCode, String[] permissions) {

                        ToastUtil.show("授权成功");
                    }

                    @Override
                    public void onDenied(int requestCode, Map<String, Integer> result) {

                        ToastUtil.show("授权失败");
                    }

                    @Override
                    public void onNeverAsk(int requestCode, Map<String, Integer> result) {
                        ToastUtil.show("永不询问");
                    }
                }
        );
    }
}

package com.snail.labaffinity.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ImageView;

import com.snail.labaffinity.R;
import com.snail.labaffinity.service.BackGroundService;
import com.snail.labaffinity.utils.ToastUtil;

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
        ButterKnife.bind(this);
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
    @OnClick(R.id.first)
    void first() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                loadImg();
            }
        }).subscribe();
    }

    private void test() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //在call方法中执行异步任务
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                count++;
                subscriber.onNext("成功执行异步任务" + count + "次");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        ToastUtil.show("onCompleted:");

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        ToastUtil.show("number:" + s);

                    }
                });

    }

    private void loadImg() {
        Observable.just(R.mipmap.ic_launcher) // 输入类型 String
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer filePath) { // 参数类型 String
                        return BitmapFactory.decodeResource(getResources(), filePath);
                    }
                }).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {
                ImageView image= (ImageView) findViewById(R.id.img);
                image.setImageBitmap(bitmap);
            }
        });
    }
}

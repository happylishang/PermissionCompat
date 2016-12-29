![权限申请](http://upload-images.jianshu.io/upload_images/1460468-fada7ed2171bd16d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

# 使用说明

#build.gradle添加依赖

在根build.gradle中添加  classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
	
    buildscript {

    dependencies {
      ...
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
	
在app的build.gradle中添加如下代码，默认开启了jcenter库依赖


	apply plugin: 'com.neenbedankt.android-apt'

	dependencies {
	
	    apt 'com.snail:compiler:0.0.3'
	    compile 'com.snail:permissioncompat:0.0.3'
	
	}
	
#Activity使用

继承BasePermissionCompatActivity，里面做了对回调的封装

	
	@ActivityPermission
	public class SecondActivity extends BasePermissionCompatActivity {
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.second);
	    }
	
	    @OnGranted(value = {Manifest.permission.CAMERA})
	    void grantedC() {
	        ToastUtil.show("CAMERA granted");
	    }
	
	    @OnDenied(value = {Manifest.permission.CAMERA})
	    void onDeniedC() {
	        ToastUtil.show(" CAMERA onDenied");
	    }
	
	    @OnNeverAsk(value = {Manifest.permission.CAMERA})
	    void OnNeverAskC() {
	        ToastUtil.show(" CAMERA OnNeverAsk");
	        starSettingActivityForPermission(1000);
	    }
	
	    @OnShowRationale(value = {Manifest.permission.CAMERA})
	    void OnShowRationaleC() {
	        ToastUtil.show(" CAMERA OnShowRationale");
	    }
	
	    @OnClick(R.id.camera)
	    void camera() {
	        PermissionCompat.requestPermission(this, new String[]{Manifest.permission.CAMERA});
	    }
	}
#fragment使用

继承BasePermissionCompatFragment，里面做了对回调的封装

	@FragmentPermission
	public class PermissionFragment extends BasePermissionCompatFragment {
	
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
	
	    @OnGranted(value = {Manifest.permission.CAMERA})
	    void grantedC() {
	        ToastUtil.show("CAMERA granted");
	    }
	
	    @OnDenied(value = {Manifest.permission.CAMERA})
	    void onDeniedC() {
	        ToastUtil.show(" CAMERA onDenied");
	    }
	
	    @OnNeverAsk(value = {Manifest.permission.CAMERA})
	    void OnNeverAskC() {
	        ToastUtil.show(" CAMERA OnNeverAsk");
	        starSettingActivityForPermission(1000);
	    }
	
	    @OnShowRationale(value = {Manifest.permission.CAMERA})
	    void OnShowRationaleC() {
	        ToastUtil.show(" CAMERA OnShowRationale");
	    }
	
	    @OnClick(R.id.camera)
	    void camera() {
	        PermissionCompat.requestPermission(this, new String[]{Manifest.permission.CAMERA});
	    }
	}	

### 用法

1、引入依赖

     implementation 'com.snail:marshmallowpermission:2.1'

2，项目使用：

Android 6.0权限适配，采用不可见FragmentDialog方式，减少代码入侵 


    PermissionCompatUtil.requestPermission(final FragmentActivity activity, final SimpleOnGrantedListener listener) {

        PermissionCompatUtil.requestPermission(activity, PERMISSION, 0, new SimpleOnGrantedListener() {
            @Override
            public void onGranted(int requestCode, String[] permissions) {
                listener.onGranted(requestCode, permissions);
            }

            @Override
            public void onDenied(int requestCode, Map<String, Integer> result) {
                listener.onDenied(requestCode, result);
            }

            @Override
            public void onNeverAsk(int requestCode, Map<String, Integer> result) {
    
            }
        });
    }

### 用法

1、引入依赖

     implementation 'com.snail:marshmallowpermission:2.0'

2，项目中国使用：

Android 6.0权限适配，采用不可见FragmentDialog方式，减少代码入侵 


    public void requestPermission(final FragmentActivity activity, final SimpleOnGrantedListener listener) {

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
                showNeverAskGuildDialog(activity, listener, requestCode, result, ResourcesUtil.getString(R.string.pia_open_camera_permission_alert ));
            }
        });
    }

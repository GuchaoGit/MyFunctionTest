package com.guc.mylibrary.base;

import android.app.Application;

import com.mabeijianxi.smallvideorecord2.DeviceUtils;
import com.mabeijianxi.smallvideorecord2.JianXiCamera;

import java.io.File;

/**
 * Created by guc on 2019/8/5.
 * 描述：
 */
public class MyApplication extends Application {
    public static String mFileSaveVideoPath;
    public static String mFileSaveImagePath;

    public static void initSmallVideo() {
        // 设置拍摄视频缓存路径
//        File dcim = Environment
//                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File dcim = new File(mFileSaveVideoPath);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                JianXiCamera.setVideoCachePath(dcim + "/");
            } else {
                JianXiCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/mabeijianxi/");
            }
        } else {
            JianXiCamera.setVideoCachePath(dcim + "/");
        }
        // 初始化拍摄，遇到问题可选择开启此标记，以方便生成日志
        JianXiCamera.initialize(true, null);
    }

    private void init() {
        mFileSaveVideoPath = getExternalCacheDir().getAbsolutePath() + "/video/";//视频
        mFileSaveImagePath = getExternalCacheDir().getAbsolutePath() + "/images/";//图片
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        initSmallVideo();
    }
}

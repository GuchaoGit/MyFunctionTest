package com.guc.mylibrary.base;

import android.app.Application;

/**
 * Created by guc on 2019/8/5.
 * 描述：
 */
public class MyApplication extends Application {
    public static String mFileSaveVideoPath;
    public static String mFileSaveImagePath;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        mFileSaveVideoPath = getExternalCacheDir().getAbsolutePath() + "/video/";//视频
        mFileSaveImagePath = getExternalCacheDir().getAbsolutePath() + "/images/";//图片
    }
}

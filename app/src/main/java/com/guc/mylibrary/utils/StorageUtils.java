package com.guc.mylibrary.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.guc.mylibrary.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author:guc
 * Time:2018/5/20
 * Description:存储工具
 */
public class StorageUtils {
    /**
     * 目录配置
     */
    public static final String ROOT_DIR = "My";    // 根目录
    private static final String CACHE_DIR = "cache";        // 缓存目录
    private static final String CAMERA_DIR = "camera";      // 照相目录
    private static final String DOWNLOAD_DIR = "download";  // 下载目录
    private static final String IMAGE_DIR = "image";        // 上传目录
    private static DisplayImageOptions optionNormal = null;

    /**
     * @Name creatDir
     * @Description 判断目录是否存在，不存在则创建
     **/
    private static void creatDir(String path) {

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
    }

    /**
     * @Name getRootDir
     * @Description 获取根目录
     */
    public static String getRootDir() {
        StringBuffer sb = new StringBuffer();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        String path = sb.toString();
        sb = null;
        // 判断是否存在文件夹，不存在建立
        creatDir(path);
        return path;
    }

    /**
     * @Name getImageCacheDir
     * @Description 获取缓存目录
     **/
    public static String getImageCacheDir() {
        StringBuffer sb = new StringBuffer();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        sb.append(CACHE_DIR);
        String path = sb.toString();
        sb = null;
        // 判断是否存在文件夹，不存在建立
        creatDir(path);
        return path;
    }

    /**
     * @Name getNormalImageOption
     * @Description uil配置
     **/
    public static DisplayImageOptions getNormalImageOption() {

        if (optionNormal == null) {
            optionNormal = new DisplayImageOptions.Builder()//
                    .showImageOnLoading(R.drawable.uil_loading)//
                    .showImageForEmptyUri(R.drawable.uil_loading)//
                    .showImageOnFail(R.drawable.uil_loading)//
                    .cacheInMemory(true)//
                    .cacheOnDisk(true)//
                    .considerExifParams(false)//
                    .bitmapConfig(Bitmap.Config.RGB_565)//
                    .build();
        }
        return optionNormal;
    }

    public static DisplayImageOptions getOriginalImageOption(Drawable pic) {

        DisplayImageOptions optionOriginal = new DisplayImageOptions.Builder()//
                .showImageOnLoading(pic)//
                .showImageForEmptyUri(pic)//
                .showImageOnFail(pic)//
                .cacheInMemory(true)//
                .cacheOnDisk(true)//
                .considerExifParams(false)//
                .bitmapConfig(Bitmap.Config.RGB_565)//
                .build();
        return optionOriginal;
    }

    /**
     * @Name downloadImage
     * @Description 下载图片并显示
     **/

    public static void downloadImage(final String imageUrl, final ImageView mImageView) {

        mImageView.setImageResource(R.drawable.uil_loading);

        final Handler handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {

                Bitmap mBitmap = msg.getData().getParcelable("data");
                mImageView.setImageBitmap(mBitmap);
                return false;
            }
        });

        new Thread(new Runnable() {

            @Override
            public void run() {

                try {
                    URL url = new URL(imageUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream inputStream = conn.getInputStream();
                    Bitmap mBitmap = BitmapFactory.decodeStream(inputStream);

                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data", mBitmap);
                    message.setData(bundle);// bundle传值，耗时，效率低
                    message.what = 1;// 标志是哪个线程传数据
                    handler.sendMessage(message);// 发送message信息
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @Name getDownloadDir
     * @Description 获取下载路径
     **/
    public static String getDownloadDir() {

        StringBuffer sb = new StringBuffer();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        sb.append(DOWNLOAD_DIR);
        String path = sb.toString();
        sb = null;
        // 判断是否存在文件夹，不存在建立
        creatDir(path);
        return path;
    }

    /**
     * @Name getUploadDir
     * @Description 获取照相目录
     **/
    public static String getCameraDir() {

        StringBuffer sb = new StringBuffer();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        sb.append(CAMERA_DIR);
        String path = sb.toString();
        sb = null;
        // 判断是否存在文件夹，不存在建立
        creatDir(path);
        return path;
    }

    /**
     * @Name getUploadDir
     * @Description 获取上传目录
     **/
    public static String getUploadDir() {

        StringBuffer sb = new StringBuffer();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        sb.append(IMAGE_DIR);
        String path = sb.toString();
        sb = null;
        // 判断是否存在文件夹，不存在建立
        creatDir(path);
        return path;
    }
}

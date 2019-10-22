package com.guc.mylibrary.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by guc on 2019/8/20.
 * 文件下载工具类（单例模式）
 */


public class DownloadUtil {

    private static DownloadUtil downloadUtil;

    private final OkHttpClient okHttpClient;


    private DownloadUtil() {

        okHttpClient = new OkHttpClient();

    }

    public static DownloadUtil get() {

        if (downloadUtil == null) {

            downloadUtil = new DownloadUtil();

        }

        return downloadUtil;

    }

    /**
     * @param url          下载连接
     * @param destFileDir  下载的文件储存目录
     * @param destFileName 下载文件名称
     * @param listener     下载监听
     */


    public void download(final String url, final String destFileDir, final String destFileName, final OnDownloadListener listener) {

        final DownloadResponse rsp = new DownloadResponse();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Observable.create((ObservableEmitter<DownloadResponse> emitter) -> {
                    //异步请求
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            // 下载失败监听回调
                            rsp.code = -1;
                            rsp.exception = e;
                            emitter.onNext(rsp);
                            emitter.onComplete();
                        }


                        @Override

                        public void onResponse(Call call, Response response) throws IOException {
                            InputStream is = null;

                            byte[] buf = new byte[2048];

                            int len = 0;

                            FileOutputStream fos = null;


                            //储存下载文件的目录

                            File dir = new File(destFileDir);

                            if (!dir.exists()) {

                                dir.mkdirs();

                            }

                            File file = new File(dir, destFileName);


                            try {


                                is = response.body().byteStream();

                                long total = response.body().contentLength();

                                fos = new FileOutputStream(file);

                                long sum = 0;

                                while ((len = is.read(buf)) != -1) {

                                    fos.write(buf, 0, len);

                                    sum += len;

                                    int progress = (int) (sum * 1.0f / total * 100);

                                    //下载中更新进度条
                                    rsp.code = 0;
                                    rsp.progress = progress;
                                    emitter.onNext(rsp);
                                }

                                fos.flush();

                                //下载完成
                                rsp.code = 1;
                                rsp.progress = 100;
                                rsp.file = file.getAbsolutePath();
                                emitter.onNext(rsp);
                                emitter.onComplete();
                            } catch (Exception e) {
                                //出现异常
                                rsp.code = -1;
                                rsp.exception = e;
                                emitter.onNext(rsp);
                                emitter.onComplete();
                            } finally {
                                try {
                                    if (is != null) {
                                        is.close();
                                    }
                                    if (fos != null) {
                                        fos.close();
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DownloadResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DownloadResponse response) {
                        listener.listener(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //出现异常
                        rsp.code = -1;
                        listener.listener(rsp);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    public interface OnDownloadListener {
        void listener(DownloadResponse response);
    }

    /**
     * 下载回调
     */
    public static class DownloadResponse {
        public int code;//-1 异常 0:下载中 1：下载完成
        public int progress;//下载进度 0-100
        public String file;//下载成功的file
        public Exception exception;//
    }
}

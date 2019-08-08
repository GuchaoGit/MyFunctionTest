package com.guc.mylibrary.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.guc.mylibrary.R;
import com.guc.mylibrary.base.BaseActivity;
import com.guc.mylibrary.base.MyApplication;
import com.guc.mylibrary.utils.DataCleanManager;
import com.guc.mylibrary.utils.ImageUtils;
import com.guc.mylibrary.utils.Utils;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.VideoPlayerActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.Manifest.permission.RECORD_AUDIO;

public class ActivityVideoRecord extends BaseActivity {
    @BindView(R.id.tv_size)
    TextView mTvSize;
    @BindView(R.id.btn_record)
    Button mBtnRecord;
    @BindView(R.id.iv_preview)
    ImageView mIvPreview;
    @BindView(R.id.iv_preview2)
    ImageView mIvPreview2;
    private int VIDEO_CAPTURE = 222;
    private String filePath;
    private String videoPic;

    private String videoPath;
    private File file;

    public static void jump(Context context) {
        Intent intent = new Intent(context, ActivityVideoRecord.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_record);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        requestPermissions();
    }

    private void requestPermissions() {
        requestRuntimePermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, RECORD_AUDIO}, new PermissionListener() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
                Toast.makeText(mContext, "没有权限", Toast.LENGTH_SHORT).show();
                ActivityVideoRecord.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == VIDEO_CAPTURE) {
            Bitmap bitmap = Utils.getVideoThumbnail(file.getAbsolutePath());
            if (bitmap == null) return;
            calculateFileSize(file);
            videoPic = ImageUtils.saveBitmap(mContext, bitmap, MyApplication.mFileSaveImagePath + UUID.randomUUID());
            Glide.with(mContext).load(videoPic).into(mIvPreview);
        } else if (resultCode == RESULT_OK && requestCode == 100) {
            if (data != null) {
                videoPath = data.getStringExtra(MediaRecorderActivity.VIDEO_URI);
                String videoScreenShotPath = data.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);
                Glide.with(mContext).load(videoScreenShotPath).into(mIvPreview2);
            }
        }
    }

    @OnClick({R.id.btn_record, R.id.btn_face_identify, R.id.btn_record2, R.id.iv_preview2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_record:
                filePath = MyApplication.mFileSaveVideoPath + System.currentTimeMillis() + ".mp4";// ;
                file = new File(filePath);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                    }
                }
                Uri uri = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(ActivityVideoRecord.this,
                            getApplicationContext().getPackageName() + ".android7.fileprovider", file);
                } else {
                    uri = Uri.fromFile(file);
                }
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

//        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.8F);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //好使
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                startActivityForResult(intent, VIDEO_CAPTURE);
                break;

            case R.id.btn_face_identify:
                ActivityCameraIdentify.jump(this, 333);
                break;
            case R.id.btn_record2:
                goRecorder();
                break;
            case R.id.iv_preview2:
                if (TextUtils.isEmpty(videoPath)) return;
                VideoPlayerActivity.jump(mContext, videoPath);
                break;
        }

    }

    private void calculateFileSize(File file) {
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                long size = DataCleanManager.getFolderSize(file);
                emitter.onNext(DataCleanManager.getFormatSize(size));
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String o) {
                mTvSize.setText(o);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void goRecorder() {
        MediaRecorderConfig config = new MediaRecorderConfig.Buidler()

                .fullScreen(false)
                .smallVideoWidth(360)
                .smallVideoHeight(480)
                .recordTimeMax(6000)
                .recordTimeMin(1500)
                .maxFrameRate(20)
                .videoBitrate(600000)
                .captureThumbnailsTime(1)
                .build();
        MediaRecorderActivity.goSmallVideoRecorder(this, ActivityVideoRecord.class.getName(), config);
    }
}

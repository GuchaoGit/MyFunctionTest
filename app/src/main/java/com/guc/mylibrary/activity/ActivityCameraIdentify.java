package com.guc.mylibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.guc.mylibrary.R;
import com.guc.mylibrary.base.BaseActivity;
import com.guc.mylibrary.widgets.CameraSurfaceView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guc on 2018/8/26.
 * 描述：相机识别---人脸识别和身份证识别
 */
public class ActivityCameraIdentify extends BaseActivity {


    @BindView(R.id.surface_view)
    CameraSurfaceView mSurfaceView;
    @BindView(R.id.ll_portrait)
    LinearLayout mLlPortrait;
    @BindView(R.id.ll_identity_card)
    LinearLayout mLlIdentityCard;
    @BindView(R.id.view_background)
    ImageView mViewBackground;

    /**
     * 1:人像识别   2：身份证识别
     */
    private int mType = 1;

    public static void jump(Activity aty, int requestCode) {
        Intent intent = new Intent(aty, ActivityCameraIdentify.class);
        aty.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_indenty);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        setStatusBar();
        initView();
    }

    @OnClick({R.id.ll_portrait, R.id.ll_identity_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_portrait:
                if (mType == 1) return;
                mType = 1;
                initView();
                break;
            case R.id.ll_identity_card:
                if (mType == 2) return;
                mType = 2;
                initView();
                break;
        }
    }

    private void initView() {
        mLlPortrait.setSelected(mType == 1);
        mLlIdentityCard.setSelected(mType == 2);
        mViewBackground.setImageResource(mType == 1 ? R.drawable.bg_camera_portrait : R.drawable.bg_camera_card);
    }

}

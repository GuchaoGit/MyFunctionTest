package com.guc.mylibrary.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.guc.mylibrary.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guc on 2018/5/14.
 * 描述：自定义dialog
 */
public class DialogCustom extends Dialog {

    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_cancle)
    TextView mTvCancle;
    @BindView(R.id.tv_sure)
    TextView mTvSure;
    private OnclickListener mListner;


    public DialogCustom(@NonNull Context context) {
        this(context, R.style.MyDialog);
    }

    public DialogCustom(@NonNull Context context, int themeResId) {
        this(context, false, null);
    }

    public DialogCustom(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public void setOnClickListener(OnclickListener mListner) {
        this.mListner = mListner;
    }

    public DialogCustom setTipMsg(String msg) {
        mTvContent.setText(TextUtils.isEmpty(msg) ? "no message!" : msg);
        return this;
    }

    private void initView() {
        setContentView(R.layout.layout_dialog);
        ButterKnife.bind(this);
        getWindow().setBackgroundDrawable(new ColorDrawable());
    }

    @OnClick({R.id.tv_cancle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                if (mListner != null) {
                    mListner.onNoClick();
                } else {
                    dismiss();
                }
                break;
            case R.id.tv_sure:
                if (mListner != null) {
                    mListner.onYesClick();
                } else {
                    dismiss();
                }
                break;
        }
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface OnclickListener {
        void onYesClick();

        void onNoClick();
    }
}

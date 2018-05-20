package com.guc.mylibrary.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guc.mylibrary.R;

/**
 * Author:guc
 * Time:2018/5/20
 * Description:
 */
public class TipDialog extends Dialog {

    public static final int THEME_ONE_BUT = 1;
    public static final int THEME_TWO_BUT = 2;

    private TextView txtvTip;
    private Button btnLeft, btnRight;

    private Context mContext;
    private TipDialogOnClickListener mListener;

    public TipDialog(@NonNull Context context) {
        super(context, R.style.TransDialog);
        mContext = context;
        init();
    }

    private void init() {
        setContentView(R.layout.view_tip_dialog);
        //
        txtvTip = (TextView) findViewById(R.id.txtv_tip_dialog_tips);
        btnLeft = (Button) findViewById(R.id.btn_tip_dialog_left);
        btnRight = (Button) findViewById(R.id.btn_tip_dialog_right);
        //
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消显示
                dismiss();
                if (mListener != null) {
                    mListener.onLeftClick();
                }
            }
        });
        //
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消显示
                dismiss();
                if (mListener != null) {
                    mListener.onRightClick();
                }
            }
        });
    }

    public TipDialog setTipsGravity(int gravity) {
        txtvTip.setGravity(gravity);
        return this;
    }

    public TipDialog setTips(int resId) {
        String mTip = mContext.getResources().getString(resId);
        setTips(mTip);
        return this;
    }

    public TipDialog setTips(String tips) {
        txtvTip.setText(tips);
        return this;
    }

    public TipDialog setLeftText(String tip) {
        btnLeft.setText(tip);
        return this;
    }

    public TipDialog setRightText(String tip) {
        btnRight.setText(tip);
        return this;
    }

    public TipDialog setTheme(int theme) {
        switch (theme) {
            case THEME_ONE_BUT:
                ((LinearLayout.LayoutParams) btnLeft.getLayoutParams()).weight = 5;
                btnRight.setVisibility(View.GONE);
                break;

            case THEME_TWO_BUT:
                ((LinearLayout.LayoutParams) btnLeft.getLayoutParams()).weight = 3;
                btnRight.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        return this;
    }

    public TipDialog setOnTipDialogClickListener(TipDialogOnClickListener listener) {
        mListener = listener;
        return this;
    }

    public interface TipDialogOnClickListener {
        void onLeftClick();

        void onRightClick();
    }
}



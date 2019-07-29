package com.guc.mylibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.guc.mylibrary.R;
import com.guc.mylibrary.dialogs.TipDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 窗口化Activity
 */
public class ActivityDialog extends Activity {
    private static final String TAG_CAN_CANCEL = "can_cancel";
    private boolean mCanCancelTouchOutside = true;

    public static void jump(Context context, boolean canCancelTouchOutside) {
        Intent intent = new Intent(context, ActivityDialog.class);
        intent.putExtra(TAG_CAN_CANCEL, canCancelTouchOutside);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.dialog_in, R.anim.dialog_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
//        getWindow().setGravity(Gravity.BOTTOM);
        ButterKnife.bind(this);
        mCanCancelTouchOutside = getIntent().getBooleanExtra(TAG_CAN_CANCEL, true);
        setFinishOnTouchOutside(mCanCancelTouchOutside);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.dialog_out, R.anim.dialog_out);
    }

    @OnClick(R.id.btn_show_dialog)
    public void onViewClicked() {
        TipDialog dialog = new TipDialog(this)
                .setTips("这是一个提示框")
                .setOnTipDialogClickListener(new TipDialog.TipDialogOnClickListener() {
                    @Override
                    public void onLeftClick() {

                    }

                    @Override
                    public void onRightClick() {

                    }
                });
        dialog.show();
    }
}

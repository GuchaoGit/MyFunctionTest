package com.guc.mylibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.guc.mylibrary.R;

/**
 * 窗口化Activity
 */
public class ActivityDialog extends Activity {
    private boolean mCanCancelTouchOutside = true;

    public static void jump(Context context, boolean canCancelTouchOutside) {
        Intent intent = new Intent(context, ActivityDialog.class);
        intent.putExtra("can_cancel", canCancelTouchOutside);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.dialog_in, R.anim.dialog_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        mCanCancelTouchOutside = getIntent().getBooleanExtra("can_cancel", true);
        setFinishOnTouchOutside(mCanCancelTouchOutside);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.dialog_out, R.anim.dialog_out);
    }
}

package com.guc.mylibrary.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.guc.mylibrary.R;
import com.guc.mylibrary.adapter.AdapterSingleSelect;
import com.guc.mylibrary.utils.commonadapter.AdaptiveWidthListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guc on 2019/7/30.
 * 描述：单选提示框
 */
public class SingleSelectDialog extends Dialog {
    private final Context mContext;
    private AdaptiveWidthListView mListView;
    private AdapterSingleSelect<String> mAdapter;
    private List<String> mDatas;
    private View mParentView;

    public SingleSelectDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyleReverse);
        mContext = context;
        init();
    }
    public SingleSelectDialog showDropDownAs(View view){
        mParentView = view;
        initView();
        return this;
    }

    private void initView() {
        Window window = this.getWindow();
        //设置窗口的位置
        //window.setGravity(Gravity.LEFT | Gravity.TOP);
        //设置窗口的属性，以便设设置
        WindowManager.LayoutParams layoutParams = window.getAttributes();

        layoutParams.x =-mParentView.getWidth()/4;//x 位置设置
        layoutParams.y=mParentView.getHeight();//y 位置设置
        window.setAttributes(layoutParams);
    }

    private void init() {
        setContentView(R.layout.view_select_dialog);
        setCanceledOnTouchOutside(true);
        mListView = findViewById(R.id.lv_content);
        mDatas = new ArrayList<>();
        mDatas.add("选项一");
        mDatas.add("这是选项二");
        mDatas.add("选项三");
        mDatas.add("选项四");
        mAdapter=new AdapterSingleSelect<>(this.getContext(),mDatas);
        mListView.setAdapter(mAdapter);
    }
}

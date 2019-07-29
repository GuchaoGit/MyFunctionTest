package com.guc.mylibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.guc.mylibrary.R;
import com.guc.mylibrary.dialogchooseinfo.BeanChooseOption;
import com.guc.mylibrary.dialogchooseinfo.DialogChooseInfoMultiple;
import com.guc.mylibrary.dialogchooseinfo.DialogChooseInfoUtils;
import com.guc.mylibrary.widgets.ViewRichText;
import com.guc.mylibrary.widgets.YearSelectDialogUtil;
import com.guc.mylibrary.widgets.pickerview.OptionsPickerView;
import com.guc.mylibrary.widgets.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by guc on 2019/6/20.
 * 描述：测试条件选择器
 */
public class ActivityPickerViewTest extends Activity {

    @BindView(R.id.tv_sel_option)
    ViewRichText mTvSelOption;
    @BindView(R.id.tv_sel_option2)
    ViewRichText mTvSelOption2;
    @BindView(R.id.tv_sel_year)
    ViewRichText mTvSelYear;
    @BindView(R.id.tv_sel_time)
    ViewRichText mTvSelTime;

    private OptionsPickerView<String> mOptionPicker;
    private TimePickerView mTimePicker;
    private List<String> mOptions;
    private List<List<String>> mOptions2Opt;
    private List<BeanChooseOption> mOptions2;
    private List<BeanChooseOption> mOpt2Sel;

    public static void jump(Context context) {
        Intent intent = new Intent(context, ActivityPickerViewTest.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_pickerview_test);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mOptions = new ArrayList<String>() {
            {
                add("Option 1");
                add("Option 2");
                add("Option 3");
                add("Option 4");
                add("Option 5");
                add("Option 6");
                add("Option 7");
                add("Option 8");
                add("Option 9");
            }
        };
        mOptions2Opt = new ArrayList<>();
        mOptions2Opt.add(mOptions);
        mOptions2Opt.add(mOptions);
        mOptions2Opt.add(mOptions);
        mOptions2Opt.add(mOptions);
        mOptions2Opt.add(mOptions);
        mOptions2Opt.add(mOptions);
        mOptions2Opt.add(mOptions);
        mOptions2Opt.add(mOptions);
        mOptions2Opt.add(new ArrayList<>());
        mOptions2 = new ArrayList<BeanChooseOption>(){
            {
                add(new BeanChooseOption("选择1","1"));
                add(new BeanChooseOption("选择2","2"));
                add(new BeanChooseOption("选择3","3"));
                add(new BeanChooseOption("选择4","4"));
                add(new BeanChooseOption("选择5","5"));
                add(new BeanChooseOption("选择6","6"));
            }
        };
    }

    @OnClick({R.id.btn_sel_option, R.id.btn_sel_year, R.id.btn_sel_time,R.id.btn_sel_option2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sel_option:
                mOptionPicker = new OptionsPickerView.Builder<String>(this, (int options1, int options2, int options3, View v) -> {
                    mTvSelOption.setContent(mOptions.get(options1));
                }).build();
                mOptionPicker.setPicker(mOptions, mOptions2Opt);
                mOptionPicker.show();
                break;
            case R.id.btn_sel_year:
                YearSelectDialogUtil.showSelectYearDialog(this, 2019, 10, new YearSelectDialogUtil.OnYearSelected() {
                    @Override
                    public void onYearSelected(int year) {
                        mTvSelYear.setContent(String.valueOf(year));
                    }
                });
                break;
            case R.id.btn_sel_time:
                mTimePicker = new TimePickerView.Builder(this, (Date date, View v) ->
                        mTvSelTime.setContent(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date))
                ).setType(TimePickerView.Type.ALL).setContentSize(14).build();
                mTimePicker.show();
                break;
            case R.id.btn_sel_option2:
                DialogChooseInfoUtils.showDialogChooseInfoMultiple("请选择（多选）", mOptions2,mOpt2Sel, getFragmentManager(), new DialogChooseInfoMultiple.OnChooseInfoClickListener() {
                    @Override
                    public void onChoose(boolean hasSelected, List<BeanChooseOption> beanChooseInfos) {
                        mOpt2Sel = beanChooseInfos;
                        if(hasSelected){
                            mTvSelOption2.setContent("已选择：" + beanChooseInfos.size()+"个条件");
                        }else {
                            mTvSelOption2.setContent("什么都没选");
                        }
                    }
                });
                break;
        }
    }
}

package com.guc.mylibrary.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.guc.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:guc
 * Time:2018/5/20
 * Description:
 */
public class MyTimePickerDialog  implements TimePicker.OnTimeChangedListener {
    private Context mContext;
    private AlertDialog.Builder b;
    private TimePicker tp;
    private static boolean is24Hour = true;
    private OnTimeChangeListener listener;
    private String hour = "00";
    private String minute = "00";
    private int hourI = 0;
    private int minuteI = 0;
    private static String[] displayvalue = new String[] { "00", "30" };

    public MyTimePickerDialog(Context context) {
        this(context, is24Hour, 0, 0);
    }

    public MyTimePickerDialog(Context context, boolean is24Hour, int hour,
                              int minute) {
        this.is24Hour = is24Hour;
        this.mContext = context;
        this.hour = String.valueOf(hour);
        this.minute = String.valueOf(minute);
        this.hourI = hour;
        this.minuteI = minute;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.common_time,
                null);
        tp = (TimePicker) view.findViewById(R.id.timepicker);
        tp.setIs24HourView(is24Hour);
        tp.setCurrentHour(hourI);
        tp.setCurrentMinute(minuteI);
        NumberPicker mMinuteSpinner = findNumberPicker(tp).get(
                findNumberPicker(tp).size() - 1);
        mMinuteSpinner.setMinValue(0);
        mMinuteSpinner.setMaxValue(1);
        mMinuteSpinner.setDisplayedValues(displayvalue);
        mMinuteSpinner.setOnLongPressUpdateInterval(100);
        tp.setOnTimeChangedListener(this);
        b = new AlertDialog.Builder(mContext);
        b.setView(view);
        b.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onTimeChange(hour, minute);
                }

            }
        });
        b.setNegativeButton("取消", null);
        b.create().show();

    }

    /**
     * 得到viewGroup里面的numberpicker组件
     *
     * @param viewGroup
     * @return
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> npList = new ArrayList<NumberPicker>();
        View child = null;
        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        this.minute = displayvalue[minute];
    }

    public void setOnTimeChangeListener(OnTimeChangeListener listener) {
        this.listener = listener;
    }

    public interface OnTimeChangeListener {
        void onTimeChange(String hour, String minute);
    }
}



package com.guc.mylibrary.widgets;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.guc.mylibrary.R;
import com.guc.mylibrary.utils.commonadapter.CommonRecycleAdapter;
import com.guc.mylibrary.utils.commonadapter.CommonViewHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by guc on 2019/6/11.
 * 描述：选择年份 弹出窗工具
 */
public class YearSelectDialogUtil {

    public static void showSelectYearDialog(final Context context, final int selectYear, int maxRange, final OnYearSelected onYearSelected) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        View rootView = View.inflate(context, R.layout.dialog_select_year, null);
        final AlertDialog dialog = new AlertDialog.Builder(context, R.style.ActionSheetDialogStyle).setView(rootView).create();
        dialog.show();
        rootView.findViewById(R.id.view_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        final RecyclerView lvContent = rootView.findViewById(R.id.rcv_content);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, OrientationHelper.VERTICAL, false);
        lvContent.setLayoutManager(layoutManager);

        final List<YearSel> datas = new ArrayList<>();
        YearSel yearSel;
        int initialPosition = 0;
        if (maxRange < 3) maxRange = 3;
        for (int i = 0; i < maxRange; i++) {
            yearSel = new YearSel(currentYear - i, selectYear == (currentYear - i));
            if (selectYear == (currentYear - i)) {
                initialPosition = i;
            }
            datas.add(yearSel);
        }
        CommonRecycleAdapter<YearSel> adapter = new CommonRecycleAdapter<YearSel>(context, datas, R.layout.item_year_sel) {
            @Override
            public void bindData(CommonViewHolder holder, YearSel data, int position) {
                holder.setText(R.id.tv_content, String.valueOf(data.year))
                        .setViewVisibility(R.id.v_divider_bottom, data.selected ? View.VISIBLE : View.INVISIBLE)
                        .setViewVisibility(R.id.v_divider_top, data.selected ? View.VISIBLE : View.INVISIBLE);
                TextView textView = holder.getView(R.id.tv_content);
                textView.setTextColor(data.selected ? context.getResources().getColor(R.color.text_black) : context.getResources().getColor(R.color.text_grey));
                holder.setCommonClickListener(new CommonViewHolder.onItemCommonClickListener() {
                    @Override
                    public void onItemClickListener(int position) {
                        for (int i = 0; i < datas.size(); i++) {
                            datas.get(i).selected = i == position;
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onItemLongClickListener(int position) {

                    }
                });
            }

        };
        lvContent.setAdapter(adapter);
        rootView.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onYearSelected != null) {
                    int selYear;
                    for (YearSel yearSel : datas) {
                        if (yearSel.selected) {
                            selYear = yearSel.year;
                            onYearSelected.onYearSelected(selYear);
                            dialog.dismiss();
                            return;
                        }
                    }

                }
                dialog.dismiss();
            }
        });
        lvContent.scrollToPosition(initialPosition);
        Window window = dialog.getWindow();
        if (window == null) return;
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.horizontalMargin = 0;
        window.setAttributes(lp);
        window.getDecorView().setMinimumWidth(context.getResources().getDisplayMetrics().widthPixels);

    }

    public interface OnYearSelected {
        void onYearSelected(int year);
    }

    static class YearSel {
        int year;
        boolean selected;

        YearSel(int year, boolean selected) {
            this.year = year;
            this.selected = selected;
        }
    }
}


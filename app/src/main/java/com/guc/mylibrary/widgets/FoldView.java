package com.guc.mylibrary.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.guc.mylibrary.R;


/**
 * Author: Mr.Gu
 * Date: 2019/5/15
 * Description:
 */
public class FoldView extends FrameLayout implements View.OnClickListener {
    TextView mTvContent;
    ImageView mIvArrow;
    private View mView;
    private int mTextColor;
    private float mTextSize;
    private boolean isTextVisiable = true;
    private Drawable mResArrowUp ;
    private Drawable mResArrowDown;

    private boolean foldType = false;//默认展开状态

    public FoldView(@NonNull Context context) {
        this(context, null);
    }

    public FoldView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FoldView, defStyleAttr, 0);

        mTextColor = attributes.getColor(R.styleable.FoldView_desc_text_color, Color.BLUE);
        mTextSize = attributes.getDimensionPixelSize(R.styleable.FoldView_desc_text_size,12);
        isTextVisiable = attributes.getBoolean(R.styleable.FoldView_is_text_visiable,true);
        mResArrowUp = attributes.getDrawable(R.styleable.FoldView_arrow_up_drawable);
        mResArrowDown = attributes.getDrawable(R.styleable.FoldView_arrow_down_drawable);
        if (mResArrowUp == null)
        mResArrowUp =  ContextCompat.getDrawable(context, R.drawable.arrowup);
        if (mResArrowDown == null)
        mResArrowDown =  ContextCompat.getDrawable(context, R.drawable.arrowdown);
        attributes.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_fold, null);
        mTvContent = view.findViewById(R.id.tv_content);
        mIvArrow = view.findViewById(R.id.iv_arrow);
        mTvContent.setVisibility(isTextVisiable?VISIBLE:INVISIBLE);
        mTvContent.setTextColor(mTextColor);
        mTvContent.setTextSize(px2sp(mTextSize));
        mTvContent.setOnClickListener(this);
        mIvArrow.setOnClickListener(this);
        initFold();
        addView(view);
    }

    /**
     * 设置需要改变的view
     *
     * @param view
     */
    public void setControlView(View view) {
        this.mView = view;
        changeStatus();
    }

    /**
     * 设置默认状态
     *
     * @param foldType
     */
    public void setFoldType(boolean foldType) {
        this.foldType = foldType;
        initFold();
    }

    private void initFold() {
        if (foldType) {
            mTvContent.setText("点击收起");
            mIvArrow.setImageDrawable(mResArrowUp);
        } else {
            mTvContent.setText("查看更多");
            mIvArrow.setImageDrawable(mResArrowDown);
        }
    }

    @Override
    public void onClick(View view) {
                changeStatus();
    }

    private void changeStatus() {
        if (foldType) {
            mTvContent.setText("查看更多");
            mIvArrow.setImageDrawable(mResArrowDown);
        } else {
            mTvContent.setText("点击收起");
            mIvArrow.setImageDrawable(mResArrowUp);
        }
        changeView();
        foldType = !foldType;
    }

    private void changeView() {
        if (mView != null) {
            if (foldType) {//打开view
                mView.setVisibility(View.GONE);
            } else {//关闭view
                mView.setVisibility(View.VISIBLE);
            }
        }
    }
    private int sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return (int)(sp * scale + 0.5);
    }
    private int px2sp(float px) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return (int)(px/scale + 0.5);
    }
}

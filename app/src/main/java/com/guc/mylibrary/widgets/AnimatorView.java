package com.guc.mylibrary.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Author:guc
 * Time:2018/5/20
 * Description:
 */
public class AnimatorView extends View {

    private Paint mPaint;
    private int width;//组件宽度
    private int height;//组件高度
    private int rect_width;//每个矩形的宽度
    private int rect_distance;//每个矩形的距离
    private static final int TOTAL_PAINT_TIMES = 100; //控制绘制速度,分100次完成绘制
    private int rect_num = RECT_ARRAY.length;
    private int mPaintTimes = 0;

    public AnimatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        initialize();
    }

    public AnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initialize();
    }

    public AnimatorView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initialize();
    }
    protected void initialize() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
//		canvas.drawColor(Color.WHITE);
        mPaintTimes++;
        canvas.drawRect(70, 80, 130, 140, mPaint);;
        for (int i = 0; i < RECT_ARRAY.length; i++) {
            mPaint.setColor(RECT_ARRAY[i][1]);

            int paintXPos = i*(rect_width+rect_distance) + rect_distance/2;
            int paintYPos = RECT_ARRAY[i][0]/TOTAL_PAINT_TIMES*mPaintTimes;
            int height =  getHeight();
            canvas.drawRect(paintXPos , (height - 20-paintYPos)>0?(height - 20-paintYPos):0, paintXPos + rect_width, height -20, mPaint);
        }
        if (mPaintTimes < TOTAL_PAINT_TIMES) {
            invalidate(); //实现动画的关键点
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        rect_width = width/rect_num*2/3;
        rect_distance = width/rect_num*1/3;
    }

    //待绘制的矩形块矩阵，left为高度，right为颜色
    private static final int[][] RECT_ARRAY = {
            {380,Color.GRAY},
            {600,Color.YELLOW},
            {200,Color.GREEN},
            {450,Color.RED},
            {300,Color.BLUE}
    };
}



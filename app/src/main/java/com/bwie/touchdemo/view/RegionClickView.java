package com.bwie.touchdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import com.bwie.touchdemo.R;
import com.gcssloop.view.CustomView;

/**
 * 1. 点击区域
 * 2. @author：liqingyi
 * 3. @date：2017/2/10 20:48
 */

public class RegionClickView extends CustomView {

    public static final float RIDIUS = 100F;

    Region circleRegion;
    Path circlePath;

    float radius = 100;
    int color= Color.GREEN;

    public RegionClickView(Context context) {
        super(context);
        initView(context, null);
    }

    public RegionClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mDeafultPaint.setColor(0xFF4E5268);
        circlePath = new Path();
        circleRegion = new Region();

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RegionClickView);
            radius = ta.getFloat(R.styleable.RegionClickView_liqyRegionRadius, RIDIUS);
            color=ta.getColor(R.styleable.RegionClickView_liqyRegionColor,Color.GREEN);
            mDeafultPaint.setColor(color);
            ta.recycle();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // ▼在屏幕中间添加一个圆
        circlePath.addCircle(w / 2, h / 2, radius, Path.Direction.CW);
        circlePath.addRect(20,20,100,100,Path.Direction.CW);

        // ▼将剪裁边界设置为视图大小
        Region globalRegion = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        circleRegion.setPath(circlePath, globalRegion);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                // ▼点击区域判断
                if (circleRegion.contains(x, y)) {
                    Toast.makeText(this.getContext(), "圆被点击", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // ▼注意此处将全局变量转化为局部变量，方便 GC 回收 canvas
        Path circle = circlePath;
        // 绘制圆
        canvas.drawPath(circle, mDeafultPaint);
    }
}

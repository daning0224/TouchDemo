package com.bwie.touchdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 作    者：云凯文
 * 时    间：2017/2/11
 * 描    述：
 * 修改时间：
 */

public class RegionRectClickView extends View {

    Region region;
    Region circleRegion;
    Path rectPath;
    Path circlePath;

    Paint mDeafultPaint = new Paint();

    public RegionRectClickView(Context context) {
        super(context);
        initView(context, null);
    }

    public RegionRectClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mDeafultPaint.setColor(Color.YELLOW);
        region = new Region();
        circleRegion = new Region();
        rectPath = new Path();
        circlePath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDeafultPaint.setColor(Color.BLUE);
        circlePath.addCircle(60, 60, 60, Path.Direction.CW);

        mDeafultPaint.setColor(Color.YELLOW);
        rectPath.addRect(20, 20, 100, 100, Path.Direction.CW);

        // ▼将剪裁边界设置为视图大小
        Region globalRegion = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        region.setPath(rectPath, globalRegion);
        circleRegion.setPath(circlePath, globalRegion);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (region.contains(x, y)) {
                    Toast.makeText(this.getContext(), "矩形被点击", Toast.LENGTH_SHORT).show();
                } else if (circleRegion.contains(x, y)) {
                    Toast.makeText(this.getContext(), "圆被点击", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this.getContext(), "空白被点击", Toast.LENGTH_SHORT).show();
                }

                break;
        }
        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path rect = rectPath;
        Path circle = circlePath;

        canvas.drawPath(circle, mDeafultPaint);
        canvas.drawPath(rect, new Paint());

    }
}

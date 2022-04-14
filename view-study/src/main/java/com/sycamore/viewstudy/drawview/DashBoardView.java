package com.sycamore.viewstudy.drawview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sycamore.viewstudy.utils.ViewUtils;


/**
 * @author dingyx
 * @description: 仪表盘
 * @date: 2022/4/6
 */
public class DashBoardView extends View {


    // 仪表盘下方不画的角度
    private static final int ANGLE = 120;
    // 弧形半径
    private static final float RADIUS = ViewUtils.dp2px(120);

    // 指针长度
    private static final float LENGTH = ViewUtils.dp2px(100);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Path dashPath = new Path();
    PathDashPathEffect pathDashPathEffect;

    public DashBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ViewUtils.dp2px(2));
        dashPath.addRect(0, 0, ViewUtils.dp2px(2), ViewUtils.dp2px(10), Path.Direction.CW);

        Path arcPath = new Path();
        arcPath.addArc((getWidth() >> 1) - RADIUS, (getHeight() >> 1) - RADIUS,
                (getWidth() >> 1) + RADIUS, (getHeight() >> 1) + RADIUS,
                90 + (ANGLE >> 1),
                360 - ANGLE);
        PathMeasure pathMeasure = new PathMeasure(arcPath, false);

        // 弧形间距
        pathDashPathEffect = new PathDashPathEffect(dashPath, (pathMeasure.getLength() - ViewUtils.dp2px(2)) / 20, 0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画弧形 弧形位置、开始角度、扫过角度、是否封口
        canvas.drawArc((getWidth() >> 1) - RADIUS,
                (getHeight() >> 1) - RADIUS,
                (getWidth() >> 1) + RADIUS,
                (getHeight() >> 1) + RADIUS,
                90 + (ANGLE >> 1),
                360 - ANGLE,
                false,
                paint);

        // 画刻度
        paint.setPathEffect(pathDashPathEffect);
        canvas.drawArc((getWidth() >> 1) - RADIUS,
                (getHeight() >> 1) - RADIUS,
                (getWidth() >> 1) + RADIUS,
                (getHeight() >> 1) + RADIUS,
                90 + (ANGLE >> 1),
                360 - ANGLE,
                false, paint);

        paint.setPathEffect(null);

        // 画指针
        canvas.drawLine(getWidth() >> 1,
                getHeight() >> 1,
                (float) Math.cos(Math.toRadians(getAngleFromDegree(5))) * LENGTH + (getWidth() >> 1),
                (float) Math.sin(Math.toRadians(getAngleFromDegree(5))) * LENGTH + (getHeight() >> 1),
                paint);

    }

    /**
     * 根据刻度对应的角度
     *
     * @param degree 刻度
     * @return angle
     */
    private float getAngleFromDegree(float degree) {
        return 90 + (ANGLE >> 1) + (360 - ANGLE) / 20 * degree;
    }

}

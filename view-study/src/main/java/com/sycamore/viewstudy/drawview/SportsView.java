package com.sycamore.viewstudy.drawview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sycamore.viewstudy.utils.ViewUtils;


/**
 * @author dingyx
 * @description: 环形进度
 * @date: 2022/4/12
 */
public class SportsView extends View {

    private static final int CIRCLE_COLOR = Color.parseColor("#2979FF");

    private static final int HIGHLIGHT_COLOR = Color.parseColor("#00FF00");

    private static final float RING_WIDTH = ViewUtils.dp2px(20);

    private static final float RADIUS = ViewUtils.dp2px(160);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Rect rect = new Rect();

    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        paint.setTextSize(ViewUtils.dp2px(100));
        paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), "AR-PL-SungtiL-GB.ttf"));

        // 设置此参数 TextView 横坐标 为水平中心位置
        paint.getFontMetrics(fontMetrics);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制环
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(CIRCLE_COLOR);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, RADIUS, paint);

        // 绘制进度条
        paint.setColor(HIGHLIGHT_COLOR);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc((getWidth() >> 1) - RADIUS,
                (getHeight() >> 1) - RADIUS,
                (getWidth() >> 1) + RADIUS,
                (getHeight() >> 1) + RADIUS, -90, 120, false, paint);

        // 绘制文字
        paint.setTextSize(ViewUtils.dp2px(100));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);

        // paint.getTextBounds("abab", 0, "abab".length(), rect);
        // float offset = (rect.top + rect.bottom) >> 1;
        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText("aaa", getWidth() >> 1, (getHeight() >> 1) - offset, paint);

        paint.setTextSize(ViewUtils.dp2px(150));
        paint.setTextAlign(Paint.Align.LEFT);
        paint.getTextBounds("aacg",0,"aacg".length(),rect);
        canvas.drawText("aacg", -rect.left, -(offset * 2), paint);


        paint.setTextSize(ViewUtils.dp2px(15));
        canvas.drawText("abcfg", 0, -(offset * 2)+100, paint);

    }
}

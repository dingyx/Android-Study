package com.sycamore.viewstudy.transformview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sycamore.viewstudy.utils.ViewUtils;

/**
 * @author dingyx
 * @description: Canvas 截取区域绘制
 * @date: 2022/4/14
 */
public class CanvasClipView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Path path = new Path();

    Bitmap bitmap;

    public CanvasClipView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = ViewUtils.getAvatar(getResources(), (int) ViewUtils.dp2px(200));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        path.addCircle(bitmap.getWidth() >> 1, bitmap.getHeight() >> 1, ViewUtils.dp2px(100), Path.Direction.CW);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // canvas 截取选中位置绘制
        // canvas.clipRect(ViewUtils.dp2px(40), ViewUtils.dp2px(40), ViewUtils.dp2px(160), ViewUtils.dp2px(160));


        // canvas 截取 选中位置以外的所有区域
        // canvas.clipOutRect(ViewUtils.dp2px(40), ViewUtils.dp2px(40), ViewUtils.dp2px(160), ViewUtils.dp2px(160));

        // canvas 截取 Path 区域进行绘制
        // 没有抗锯齿效果
        canvas.clipPath(path);

        canvas.drawBitmap(bitmap, 0, 0, paint);
    }
}

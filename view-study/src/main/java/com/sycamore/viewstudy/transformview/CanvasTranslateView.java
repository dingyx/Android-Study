package com.sycamore.viewstudy.transformview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sycamore.viewstudy.utils.ViewUtils;

/**
 * @author dingyx
 * @description: Canvas 几何变换
 * 理解多次变换后 canvas 坐标
 * @date: 2022/4/14
 */
public class CanvasTranslateView extends View {

    Paint paint = new Paint();

    public CanvasTranslateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 移动 canvas 后，绘制 bitmap
        // canvas.translate(ViewUtils.dp2px(50), ViewUtils.dp2px(100));

        canvas.rotate(45, ViewUtils.dp2px(200) / 2, ViewUtils.dp2px(200) / 2);

        canvas.drawBitmap(ViewUtils.getAvatar(getResources(), (int) ViewUtils.dp2px(200)), 0, 0, paint);


    }

}

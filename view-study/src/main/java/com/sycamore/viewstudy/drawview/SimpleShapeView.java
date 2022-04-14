package com.sycamore.viewstudy.drawview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author dingyx
 * @description: 简单图形绘制示例
 * @date: 2022/4/6
 */
public class SimpleShapeView extends View {

    // 抗锯齿
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Path path = new Path();

    PathMeasure pathMeasure;

    public SimpleShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * layout 结束，实际尺寸变化了
     * onSizeChanged 才会调用
     * <p>
     * 每次图像变化，size重置
     * 不会过多调用
     * <p>
     * OnMeasure 会多次调用
     * onSizeChanged - 如果尺寸变化了，才会触发
     *
     * @param w    width
     * @param h    height
     * @param oldw oldWith
     * @param oldh oldHeight
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        path.reset();

        // Path.Direction.CW    - 顺时针 clockwise
        // Path.Direction.CCW   - 逆时针 counter-clockwise

        path.addRect((getWidth() >> 1) - 150, (getHeight() >> 1) - 300, (getWidth() >> 1) + 150, getHeight() >> 1, Path.Direction.CCW);

        path.addCircle(getWidth() >> 1, getHeight() >> 1, 150, Path.Direction.CW);

        path.addCircle(getWidth() >> 1, getHeight() >> 1, 360, Path.Direction.CCW);

        // forceClose 强制封闭 最后默认封闭
        pathMeasure = new PathMeasure(path, false);
        // 获取整个路径长度
        pathMeasure.getLength();


        float[] point = new float[2];
        float[] tan = new float[2];
        // 获取距离起点某个距离的点的位置、角度
        pathMeasure.getPosTan(100, point, tan);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // px 绘制
        // canvas.drawLine(100, 100, 200, 200, paint);

        // 使用 dp 绘制
        // canvas.drawCircle(getWidth() / 2, getHeight() / 2, ViewUtils.dp2px(100), paint);

        // Path.FillType.WINDING
        // Path.FillType.INVERSE_WINDING - 内部外部取反

        // Path.FillType.EVEN_ODD 某个点向外 交叉奇数个点-内部、偶数个点-外部 （作用：用来做镂空）
        // Path.FillType.INVERSE_EVEN_ODD - 内部外部取反

        // 不做镂空的话-所有点全部一个方向即可

        path.setFillType(Path.FillType.EVEN_ODD);
        canvas.drawPath(path, paint);
    }
}

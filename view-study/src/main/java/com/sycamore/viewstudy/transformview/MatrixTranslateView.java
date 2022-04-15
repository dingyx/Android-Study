package com.sycamore.viewstudy.transformview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author dingyx
 * @description: matrix 几何变换
 * @date: 2022/4/14
 *
 * preTranslate(x,y) / postTranslate(x,y)
 * preRotate(degree) / postRotate(degree)
 * preScale(x,y)    /  postScale(x,y)
 * preSkew(x,y)     /  postSkew(x,y)
 */
public class MatrixTranslateView extends View {



    public MatrixTranslateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


}

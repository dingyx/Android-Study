package com.sycamore.viewstudy.transformview;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sycamore.viewstudy.utils.ViewUtils;

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

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Matrix matrix = new Matrix();


    public MatrixTranslateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        matrix.reset();
        matrix.postTranslate(100,200);
        matrix.postRotate(90,100,200);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.concat(matrix);
        canvas.drawBitmap(ViewUtils.getAvatar(getResources(), 120), 0, 0, paint);
        canvas.restore();

    }


}

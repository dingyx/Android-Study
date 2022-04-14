package com.sycamore.viewstudy.drawview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sycamore.viewstudy.utils.ViewUtils;


/**
 * @author dingyx
 * @description: 圆形头像绘制
 * @date: 2022/4/8
 */
public class AvatarView extends View {

    private static final float WIDTH = ViewUtils.dp2px(300);

    private static final float PADDING = ViewUtils.dp2px(50);

    private static final float EDGE_WIDTH = ViewUtils.dp2px(10);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    // xfermode  transfermode
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    RectF savedArea = new RectF();

    Bitmap bitmap;

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = ViewUtils.getAvatar(getResources(), (int) WIDTH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        savedArea.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);


        canvas.drawOval(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, paint);

        int saved = canvas.saveLayer(savedArea, paint);

        canvas.drawOval(PADDING + EDGE_WIDTH, PADDING + EDGE_WIDTH, PADDING + WIDTH - EDGE_WIDTH, PADDING + WIDTH - EDGE_WIDTH, paint);

        paint.setXfermode(xfermode);

        canvas.drawBitmap(bitmap, PADDING, PADDING, paint);

        paint.setXfermode(null);

        canvas.restoreToCount(saved);
    }

}

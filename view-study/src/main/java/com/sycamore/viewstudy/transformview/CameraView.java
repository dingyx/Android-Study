package com.sycamore.viewstudy.transformview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.camera2.params.InputConfiguration;
import android.net.wifi.WifiConfiguration;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sycamore.viewstudy.utils.ViewUtils;

/**
 * @author dingyx
 * @description: 使用 Camera 做简单三维旋转
 * @date: 2022/4/15
 */
public class CameraView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Camera camera = new Camera();

    Bitmap bitmap;

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = ViewUtils.getAvatar(getResources(), (int) ViewUtils.dp2px(200));
        camera.rotateX(30);
        // z 轴 单位 -inch 英寸 1英寸==72px
        // 默认 -8inch - - - -8*72px
        camera.setLocation(0, 0, ViewUtils.getZForCamera());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // 绘制上半部分
        canvas.save();
        canvas.rotate(-30);
        canvas.clipRect(-bitmap.getWidth(), -bitmap.getHeight(), ViewUtils.dp2px(50) + bitmap.getWidth(), ViewUtils.dp2px(50) + (bitmap.getHeight()));
        canvas.rotate(30);
        canvas.drawBitmap(bitmap, ViewUtils.dp2px(50), ViewUtils.dp2px(50), paint);
        canvas.restore();

        // 绘制下半部分

        // 先将 Canvas 移动到图片正中心点
        canvas.translate(ViewUtils.dp2px(50) + (bitmap.getWidth() >> 1), ViewUtils.dp2px(50) + (bitmap.getWidth() >> 1));

        canvas.rotate(-30);

        // Canvas 根据 camera 设置的角度转换 (修改投影的点位置,默认在坐标系原点 0，0，z )
        camera.applyToCanvas(canvas);

        canvas.rotate(30);

        canvas.clipRect(-(bitmap.getWidth()), 0, bitmap.getWidth(), bitmap.getHeight());

        // 将 Canvas 移动到原位置进行绘制
        canvas.translate(-(ViewUtils.dp2px(50) + (bitmap.getWidth() >> 1)), -(ViewUtils.dp2px(50) + (bitmap.getWidth() >> 1)));
        canvas.drawBitmap(bitmap, ViewUtils.dp2px(50), ViewUtils.dp2px(50), paint);

    }
}

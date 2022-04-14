package com.sycamore.viewstudy.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import com.sycamore.view_study.R;

/**
 * @author dingyx
 * @description: View 工具
 * @date: 2022/4/6
 */
public class ViewUtils {

    /**
     * dp 转 px
     *
     * @param dp dp
     * @return px
     */
    public static float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }


    /**
     * 获取头像 bitmap
     *
     * @param res   resource
     * @param width 宽度
     * @return Bitmap
     */
    public static Bitmap getAvatar(Resources res, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 调用 decodeResource 消耗资源 只能获取到宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, R.drawable.img, options);
        options.inJustDecodeBounds = false;
        // 获取图片的宽高比
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(res, R.drawable.img, options);
    }

}

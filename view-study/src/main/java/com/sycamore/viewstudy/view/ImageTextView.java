package com.sycamore.viewstudy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.sycamore.view_study.R;
import com.sycamore.viewstudy.utils.ViewUtils;


/**
 * @author dingyx
 * @description: 图片+文字
 * @date: 2022/4/12
 */
public class ImageTextView extends View {


    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Bitmap bitmap;

    float[] cutWidth = new float[1];

    TextPaint textPaint = new TextPaint();

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {
        bitmap = getAvatar((int) ViewUtils.dp2px(100));
        textPaint.setTextSize(ViewUtils.dp2px(12));
        paint.setTextSize(ViewUtils.dp2px(12));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

/*        StaticLayout staticLayout = new StaticLayout("长大是不知不觉不摇滚的过程 却也是此生 独一无二 的成分 " +
                "就算 回忆长满皱纹 现实稀释灵魂 岁月依然无声 可在夜深独自一人 别让枕边的心事 轻易就得逞 拥抱 最温柔的时分 " +
                "路的尽头会有 对与错的结论 是吧 一天循环成一生 或换上自己口吻 讲述未完旅程或许这一生 你在找一个人 我也等那个人 才得以完整  " +
                "长大是不知不觉不摇滚的过程  却也是此生 相伴相衬的见证 或许这一生 你在找一个人我也等那个人 才得以完整 " +
                "长大是不知不觉不摇滚的过程 却让我活成 独一无二 回头看人生 穷尽每个可能 历经每次沸腾 用平凡天分 " +
                "你我的青春 遗憾 精彩 共生共存 让虚幻此生 不知不觉里成真", textPaint, getWidth(), Layout.Alignment.ALIGN_NORMAL,
                1, 0, false);
        staticLayout.draw(canvas);*/

        canvas.drawBitmap(bitmap, getWidth() - ViewUtils.dp2px(100), paint.getFontSpacing() * 2, paint);

        String text = "长大是不知不觉不摇滚的过程 却也是此生 独一无二 的成分 " +
                "就算 回忆长满皱纹 现实稀释灵魂 岁月依然无声 可在夜深独自一人 别让枕边的心事 轻易就得逞 拥抱 最温柔的时分 " +
                "路的尽头会有 对与错的结论 是吧 一天循环成一生 或换上自己口吻 讲述未完旅程或许这一生 你在找一个人 我也等那个人 才得以完整 " +
                "长大是不知不觉不摇滚的过程 却也是此生 相伴相衬的见证 或许这一生 你在找一个人我也等那个人 才得以完整 " +
                "长大是不知不觉不摇滚的过程 却让我活成 独一无二 回头看人生 穷尽每个可能 历经每次沸腾 用平凡天分 " +
                "你我的青春 遗憾 精彩 共生共存 让虚幻此生 不知不觉里成真";

        // 第一行
        int index = paint.breakText(text, true, getWidth(), cutWidth);
        canvas.drawText(text, 0, index, 0, 50, paint);

        // 第二行
        int oldIndex = index;
        index = paint.breakText(text, index, text.length(), true, getWidth(), cutWidth);
        canvas.drawText(text, oldIndex, index + oldIndex, 0, 50 + paint.getFontSpacing(), paint);

        // 第三行
        oldIndex = index + oldIndex;
        index = paint.breakText(text, index, text.length(), true, getWidth() - ViewUtils.dp2px(100), cutWidth);
        canvas.drawText(text, oldIndex, index + oldIndex, 0, 50 + paint.getFontSpacing() * 2, paint);

    }


    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 调用 decodeResource 消耗资源 只能获取到宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.img, options);
        options.inJustDecodeBounds = false;
        // 获取图片的宽高比
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.img, options);
    }

}

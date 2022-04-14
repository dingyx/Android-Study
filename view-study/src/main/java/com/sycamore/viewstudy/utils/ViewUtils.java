package com.sycamore.viewstudy.utils;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @author dingyx
 * @description: View 工具
 * @date: 2022/4/6
 */
public class ViewUtils {

    /**
     * dp 转 px
     * @param dp dp
     * @return px
     */
    public static float dp2px(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

}

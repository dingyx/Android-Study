package com.sycamore.viewstudy.animatorview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sycamore.view_study.R;
import com.sycamore.viewstudy.utils.ViewUtils;

/**
 * @author dingyx
 * @description: 属性动画 ViewPropertyAnimator
 * @date: 2022/4/18
 */
public class ViewPropertyAnimatorActivity extends AppCompatActivity {

    View view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_property_animator);

        view = findViewById(R.id.view);

        // 平移、旋转、缩放、透明度 动画   ViewPropertyAnimator
        view.animate()
                .translationX(ViewUtils.dp2px(100))
                .translationY(ViewUtils.dp2px(100))
                .rotation(180)
                .alpha(0.5f)
                .setStartDelay(1000)
                .start();
        // view.setTranslationX();... 不断调用该方法，实现动画效果

    }








}

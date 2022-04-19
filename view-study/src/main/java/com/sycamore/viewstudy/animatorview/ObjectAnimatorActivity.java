package com.sycamore.viewstudy.animatorview;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sycamore.view_study.R;
import com.sycamore.viewstudy.utils.ViewUtils;

/**
 * @author dingyx
 * @description: 属性动画 ObjectAnimator
 * @date: 2022/4/18
 */
public class ObjectAnimatorActivity extends AppCompatActivity {

    CircleView view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_animator);
        view = findViewById(R.id.view);

        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "radius", ViewUtils.dp2px(120));
        animator.setStartDelay(1000);
        animator.start();
    }


}

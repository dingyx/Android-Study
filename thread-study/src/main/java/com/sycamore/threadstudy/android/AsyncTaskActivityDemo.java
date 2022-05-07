package com.sycamore.threadstudy.android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sycamore.threadstudy.R;

/**
 * @author dingyx
 * @description: AsyncTask
 * @date: 2022/5/7
 */
public class AsyncTaskActivityDemo extends Activity {

    // GC Root
    // 运行中的线程、静态对象、本地方法栈中JNI(Native 方法)引用的对象

    // 原理：通过对枚举 GC root 对象做引用可达性分析，即从GC root对象开始，向下搜索，形成的路径称之为 引用链。
    // 如果一个对象到 GC roots 对象没有任何引用，没有形成引用链，那么该对象等待GC回收。

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MyAsyncTask().execute();
    }

    class MyAsyncTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            // onResume();
            return null;
        }
    }
}

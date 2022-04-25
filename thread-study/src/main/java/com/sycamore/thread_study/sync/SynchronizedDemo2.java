package com.sycamore.thread_study.sync;

/**
 * @author dingyx
 * @description: SynchronizedDemo2
 * @date: 2022/4/25
 */
public class SynchronizedDemo2 implements TestDemo {

    private int x = 0;

    // 方法上加 synchronized 指定的 monitor 对象是 当前对象(SynchronizedDemo2)
    // 同一个类 多个方法前加 synchronized ，那么不能同时
    private synchronized void count() {
//        int temp = x + 1;
//        x = temp;
        // x++ 不是原子操作，会被拆成两步
        x++;
    }


    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                System.out.println("final x from 1:" + x);
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    count();
                }
                System.out.println("final x from 2:" + x);
            }
        }.start();

    }
}

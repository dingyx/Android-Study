package com.sycamore.thread_study.sync;

/**
 * @author dingyx
 * @description: SynchronizedDemo1
 * @date: 2022/4/25
 */
public class SynchronizedDemo1 implements TestDemo {

    private int x = 0;
    private int y = 0;

    // synchronized 当有线程访问这个方法，别的线程无法访问
    // 方法内的代码 合成了一个原子操作
    private synchronized void count(int newValue) {
        x = newValue;
        y = newValue;
        if (x != y) {
            System.out.println("x:" + x + "  y:" + y);
        }
    }


    @Override
    public void runTest() {

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
            }
        }.start();

    }
}

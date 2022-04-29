package com.sycamore.threadstudy.sync;

/**
 * @author dingyx
 * @description: SynchronizedDemo3
 * synchronized 代码块
 * @date: 2022/4/25
 */
public class SynchronizedDemo3 implements TestDemo {

    private int x = 0;
    private int y = 0;
    private String name;

    // synchronized
    private final Object monitor1 = new Object();
    private final Object monitor2 = new Object();


    private void count(int newValue) {
        synchronized (monitor1) {
            x = newValue;
            y = newValue;
        }
    }

    private synchronized void minus(int delta) {
        synchronized (monitor1) {
            x -= delta;
            y -= delta;
        }
    }


    private void setName(String newName) {
        synchronized (monitor2) {
            name = newName;
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

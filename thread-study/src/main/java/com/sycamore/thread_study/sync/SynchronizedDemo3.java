package com.sycamore.thread_study.sync;

/**
 * @author dingyx
 * @description: SynchronizedDemo3
 * @date: 2022/4/25
 */
public class SynchronizedDemo3 implements TestDemo {

    private int x = 0;
    private int y = 0;
    private String name;


    private void count(int newValue) {
        synchronized (this) {
            x = newValue;
            y = newValue;
        }
    }



    private void setName(String newName) {
        synchronized (this){
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

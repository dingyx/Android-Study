package com.sycamore.threadstudy.wait;

/**
 * @author dingyx
 * @description: wait 、notify demo
 * wait、notify 是 object native 类方法， 关联该 monitor 的线程
 * wait、notify 需要放在 synchronized 方法或代码块里面，否则没有意义。(wait 、notify 要知道对应 monitor)
 * @date: 2022/5/7
 */
public class WaitDemo {

    private String sharedString;

    private synchronized void initString() {
        sharedString = "dyx";
        notifyAll();
        // interrupt 也会唤醒 wait
    }

    private synchronized void printString() {
        while (sharedString == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("String:" + sharedString);
    }

    public void startThread3() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printString();
            }
        };
        thread1.start();

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initString();
            }
        };
        thread2.start();
    }

}

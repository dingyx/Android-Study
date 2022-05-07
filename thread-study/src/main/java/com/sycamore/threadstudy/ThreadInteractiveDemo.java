package com.sycamore.threadstudy;

import android.os.SystemClock;

import com.sycamore.threadstudy.wait.WaitDemo;

/**
 * @author dingyx
 * @description: 线程交互 Demo
 * 启动线程、终结线程
 * @date: 2022/5/7
 */
public class ThreadInteractiveDemo {

    public static void main(String[] args) {

//        startThread1();

//        startThread2();

//        WaitDemo waitDemo = new WaitDemo();
//        waitDemo.startThread3();

//        startThread4();

//        startThread5();

        new CustomizableThreadDemo().startThread6();

    }


    /**
     * 中断线程
     */
    public static void startThread1() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    // Thread.interrupt() 中断状态 会重置状态 （false- - ->true）
                    // isInterrupted() 不会重置
                    if (isInterrupted()) {
                        // ... 结束工作
                        return;
                    }
                    System.out.println("number:" + i);
                }

            }
        };
        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 方法被弃用(结果不可预期)
        //thread.stop();
        // 中断标记 (通知)
        thread.interrupt();
    }


    /**
     * InterruptedException 说明
     * sleep 过程中，外部调用 interrupt 中断线程 抛出异常
     */
    public static void startThread2() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    if (isInterrupted()) {
                        return;
                    }
                    try {
                        // sleep 过程中，外部调用 interrupt 抛出异常
                        // 抛异常 会重置 interrupt 状态
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {

                        e.printStackTrace();
                        // 如果要支持外部关闭 此处需要调用关闭线程
                        return;
                    }
                    // 等同  Thread.sleep  但是不抛异常 (不需要外部关闭时，可以使用该方法)
                    // SystemClock.sleep(300);
                    System.out.println("number:" + i);
                }

            }
        };
        thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 方法被弃用(结果不可预期)
        //thread.stop();
        // 中断标记 (通知)
        thread.interrupt();
    }


    /**
     * join
     *
     * 相当于进入 不需要 synchronize 的 wait 状态，并自动 notify
     */
    public static void startThread4() {

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

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    // 等待 thread2 执行完毕，再继续执行(相当于进入 不需要 synchronize 的 wait 状态，并自动 notify)
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                printString();
            }
        };
        thread1.start();
    }

    private static String sharedString;

    private static void initString() {
        sharedString = "dyx";
    }

    private static void printString() {
        System.out.println("String:" + sharedString);
    }



    public static void startThread5() {

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.yield();
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

package com.sycamore.thread_study;

import java.util.concurrent.ThreadFactory;

/**
 * @author dingyx
 * @description: Java 实现多线程 Demo
 * @date: 2022/4/24
 */
public class JavaThreadDemo {

    public static void main(String[] args) {
        thread();
        runnable();
    }


    /**
     * 使用 Thread 类来定义工作
     */
    static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                //  super.run 传了 runnable 会运行 runnable
                // super.run();
                System.out.println("Thread started !");
            }
        };
        thread.start();
    }

    /**
     * 使用 Runnable 类来定义工作
     */
    static void runnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with runnable started !");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    static void threadFactory(){
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return null;
            }
        };

    }

}

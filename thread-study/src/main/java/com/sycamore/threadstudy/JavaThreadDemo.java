package com.sycamore.threadstudy;

import com.sycamore.threadstudy.sync.SynchronizedDemo1;
import com.sycamore.threadstudy.sync.SynchronizedDemo2;
import com.sycamore.threadstudy.sync.SynchronizedDemo3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dingyx
 * @description: Java 实现多线程 Demo
 * @date: 2022/4/24
 */
public class JavaThreadDemo {

    public static void main(String[] args) {
//        thread();
//        runnable();
//        threadFactory();
//        executor();
//        callable();
//        runSynchronizedDemo1();
        runSynchronizedDemo2();
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

    /**
     * ThreadFactory 创建线程
     * <p>
     * 工厂方法 统一规则的初始化
     */
    static void threadFactory() {
        ThreadFactory factory = new ThreadFactory() {
            AtomicInteger count = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                // 可能两个线程一个名字
                return new Thread(r, "Thread-" + count.incrementAndGet());
            }
        };

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getName() + "started !");
        };

        Thread thread = factory.newThread(runnable);
        thread.start();
        Thread thread1 = factory.newThread(runnable);
        thread1.start();
    }

    /**
     * Executor 创建线程
     */
    static void executor() {
        Runnable runnable = () -> {
            System.out.println("Thread with runnable started !");
        };
        // Executors.newSingleThreadExecutor();
        // Executors.newFixedThreadPool(8);
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);

        // BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(10000);
        // Executor executorThreadPool = new ThreadPoolExecutor(10, 100, 5, TimeUnit.SECONDS, queue);
    }


    /**
     * callable
     * 有返回值 runnable
     */
    static void callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Done!";
            }
        };
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(callable);

        try {
            // future.isDone(); 判断结果是否返回
            // future.get 为阻塞方法,会等call方法返回结果
            String result = future.get();
            System.out.println("result:" + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }


    static void runSynchronizedDemo1() {
        SynchronizedDemo1 synchronizedDemo1 = new SynchronizedDemo1();
        synchronizedDemo1.runTest();
    }

    static void runSynchronizedDemo2() {
        SynchronizedDemo2 synchronizedDemo2 = new SynchronizedDemo2();
        synchronizedDemo2.runTest();
    }


    static void runSynchronizedDemo3() {
        SynchronizedDemo3 synchronizedDemo3 = new SynchronizedDemo3();
        synchronizedDemo3.runTest();
    }

}

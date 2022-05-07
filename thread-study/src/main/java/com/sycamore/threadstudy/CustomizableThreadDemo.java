package com.sycamore.threadstudy;

/**
 * @author dingyx
 * @description: 模拟 android handler 实现
 * @date: 2022/5/7
 */
public class CustomizableThreadDemo {

    private CustomizableThread thread = new CustomizableThread();

    ThreadLocal<String> name;

    public void startThread6() {
        thread.start();

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.looper.setTask(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        });

        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.looper.quit();
    }

    class CustomizableThread extends Thread {
        Looper looper = new Looper();
    }

    class AnotherCustomizableThread extends Thread {
        Looper looper = new Looper();
    }

    class Looper {
        private Runnable task;
        private boolean quit = false;

        synchronized void setTask(Runnable task) {
            this.task = task;
        }

        synchronized void quit() {
            quit = true;
        }

        public void loop() {
            while (!quit) {
                synchronized (this) {
                    if (task != null) {
                        task.run();
                        task = null;
                    }
                }
            }
        }

    }

}

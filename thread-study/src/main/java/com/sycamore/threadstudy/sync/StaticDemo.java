package com.sycamore.threadstudy.sync;

/**
 * @author dingyx
 * @description: 静态方法加锁
 * @date: 2022/4/28
 */
public class StaticDemo {

    private static StaticDemo staticDemo;

    private StaticDemo() {
    }

    static StaticDemo newInstance() {
        if (staticDemo == null) {
            synchronized (StaticDemo.class) {
                if (staticDemo == null) {
                    staticDemo = new StaticDemo();
                }
            }
        }
        return staticDemo;
    }

}

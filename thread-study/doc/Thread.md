# Thread Study

## 线程和进程

* 操作系统中运行多个软件
* 一个运行中的软件可能包含多个进程
* 一个运行中的进程可能包含多个线程

### CPU 线程

* 多核 CPU 的每个核各自独立运行，因此每个核一个线程
* **四核八线程**：CPU 在硬件方，在硬件级别对 CPU 进行了一核多线程的支持(本质上依然是每个核一个线程)

### 操作系统线程

* 操作系统利用时间分片的方式，把 CPU 的运行拆分给多条运行逻辑，即为操作系统线程
* 单核 CPU 也可以运行多线程操作系统

### 线程是什么

* 按代码顺序执行下来，执行完毕就结束的一条线。

  > UI 线程为什么不会结束？因为在初始化完毕后会执行死循环，循环内容是刷新界面

## Java 多线程的使用

### Thread 和 Runnable

* Thread

  ```java
  Thread thread = new Thread() {
      @Override
      public void run() {
          //  super.run 传了 runnable 会运行 runnable
          // super.run();
          System.out.println("Thread started !");
      }
  };
  thread.start();
  ```

* Runnable

  ```java
  Runnable runnable = new Runnable() {
      @Override
      public void run() {
          System.out.println("Thread with runnable started !");
      }
  };
  Thread thread = new Thread(runnable);
  thread.start();
  ```

### ThreadFactory

```java
ThreadFactory factory = new ThreadFactory() {
    int count = 0;

    @Override
    public Thread newThread(Runnable r) {
        // 这样使用 可能两个线程一个名字
        count++;
        return new Thread(r, "Thread-" + count);
    }
};

Runnable runnable = () -> {
    System.out.println(Thread.currentThread().getName() + "started !");
};

Thread thread = factory.newThread(runnable);
thread.start();
Thread thread1 = factory.newThread(runnable);
thread1.start();
```

### Executor 和线程池

* 常用：**newCachedThreadPool()**

  ```java
  Runnable runnable = () -> {
      System.out.println("Thread with runnable started !");
  };
  // Executors.newSingleThreadExecutor();
  // Executors.newFixedThreadPool(8);
  Executor executor = Executors.newCachedThreadPool();
  executor.execute(runnable);
  executor.execute(runnable);
  executor.execute(runnable);
  ```

* 短时批量处理：**newFixedThreadPool()**

  ```java
  ExecutorService executor = Executors.newFixedThreadPool(20);
  for (Bitmap bitmap : bitmaps) {
   executor.execute(bitmapProcessor(bitmap));
  }
  executor.shutdown();
  ```

### Callable 和 Future

```java
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
```

## 线程同步与线程安全


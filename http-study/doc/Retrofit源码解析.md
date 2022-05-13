# Retrofit

* 官方文档

  [https://square.github.io/retrofit/](https://square.github.io/retrofit/)

  [https://github.com/square/retrofit](https://github.com/square/retrofit)

## Retrofit 使用方法

1. 创建一个 interface 作为 Web Service 请求集合，在里面用注解（Annotation）写入需要配置的请求方法

   ```java
   public interface GitHubService {
     @GET("users/{user}/repos")
     Call<List<Repo>> listRepos(@Path("user") String user);
   }
   ```

   > 声明所有 API 调用

2. 在正式代码里用 Retrofit 创建 interface 实例

   ```java
   Retrofit retrofit = new Retrofit.Builder()
       .baseUrl("https://api.github.com/")
       .build();
   
   GitHubService service = retrofit.create(GitHubService.class);
   ```

3. 调用创建出的 Service 实例的对应方法，创建出相应的可以用来发起网络请求的 Call 对象

   ```java
   Call<List<Repo>> repos = service.listRepos("dingyx");
   ```

4. 使用 ```Call.execute()``` 或 ```Call.enqueue``` 来发起请求

   ```java
   repos.enqueue(callback);
   ```

## 源码总结

* 通过 Retrofit.create(Class)方法创建出 Service interface 的实例，从而使得 Service 中 配置的方法变得可用，这是Retrofit代码结构的核心；

* Retrofit.create() 方法内部，使用的是 Proxy.newProxyInstance() 方法来创建 Service实例。这个方法会为参数中的多个interface (具体到Retrofit来说，是固定传入一个 interface)创建一个对象，这个对象实现了所有interface的每个方法，并且每个方法的实现都 是雷同的：调用对象实例内部的一个InvocationHandler成员变量的invoke()方法，并 把自己的方法信息传递进去。这样就在实质上实现了代理逻辑：interface中的方法全部由一个 另外设定的InvocationHandler对象来进行代理操作。并且，这些方法的具体实现是在运行 时生成interface实例时才确定的，而不是在编译时(虽然在编译时就已经可以通过代码逻辑推 断出来)。这就是网上所说的「动态代理机制」的具体含义。

* 因此，invoke()方法中的逻辑，就是Retrofit创建Service实例的关键。这个方法内有三行 关键代码，共同组成了具体逻辑：

  1. ```ServiceMethod``` 的创建

     ```java
     ServiceMethod<Object, Object> serviceMethod =
         (ServiceMethod<Object, Object>) loadServiceMethod(method);
     ```

     这行代码负责读取interface中原方法的信息(包括返回值类型、方法注解、参数类型、参数注 解)，并将这些信息做初步分析。

  2. ```OkHttpCall ```的创建：

     ```java
     OkHttpCall<Object> okHttpCall = new OkHttpCall<>(serviceMethod, args);
     ```

     OkHttpCall 是 retrofit2.Call 的子类。这行代码负责将ServiceMethod封装进一个 retrofit2.Call 对象；而这个对象可以在需要的时候(例如它的 enqueue() 方法被调用的 时候，利用ServiceMethod中包含的信息来创建一个okhttp3.Call 对象，并调用这个 okhttp3.Call 对象来进行网络请求的发起，然后对结果进行预处理(如类型转换)。

  3. ```adapt()``` 方法：

     ```
     return serviceMethod.adapt(okHttpCall);
     ```

     这个方法会使用 ServiceMethod 中的 callAdapter 对象来把 okHttpCall 对象进行转换，生成一 个新的 retrofit2.Call 对象，在这个新的 Call 对象中，后台线程发起的请求，会在相应返回后，从主线程中调用回调方法，实现线程的自动切换。

     另外，这个方法不止可以生成新的 retrofit2.Call 对象，也可以生成别的类型对象，例如 RxJava 的 Obervable ，来让 Retrofit 可以和 RxJava 结合使用。

* 更细的代码逻辑（ServiceMethod 如果做方法解析、CallAdapter如果做 adapt 等查看源码）


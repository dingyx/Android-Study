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

   ```
   repos.enqueue(callback);
   ```

## 源码

* 




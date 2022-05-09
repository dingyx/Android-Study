# HTTP 原理和工作机制

## HTTP 定义

Hypertext Transfer Protocol，超⽂本传输协议。(和 HTML (Hypertext Markup Language 超⽂本标 记语⾔) ⼀起诞⽣，⽤于在⽹络上请求和传输 HTML 内容)

## HTTP 工作方式

* 浏览器

  ⽤户输⼊地址后回⻋或点击链接 -> 浏览器拼装 HTTP 报⽂并发送请求给服务器 -> 服务器处理请求后 发送响应报⽂给浏览器 -> 浏览器解析响应报⽂并使⽤渲染引擎显示到界⾯

* 手机

  ⽤户点击或界⾯⾃动触发联⽹需求 -> Android 代码调⽤拼装 HTTP 报⽂并发送请求到服务器 -> 服务 器处理请求后发送响应报⽂给⼿机 -> Android 代码处理响应报⽂并作出相应处理（如储存数据、加⼯ 数据、显示数据到界⾯）

## URL 和 HTTP 报文

### URL 格式

*  三部分：协议类型、服务器地址(和端⼝号)、路径(Path) 

  > 协议类型://服务器地址[:端⼝号]路径

  ```
  http://dingyx.tech/users?name=dyx
  ```

### 报文格式

* 请求报文

  ![请求报文](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/请求报文.png)

* 响应报文

  ![响应报文](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/响应报文.png)

## Request Method 请求方法

### GET

* 获取资源
* 对服务器数据不进行修改
* 没有 Body

```
GET /users/1 HTTP/1.1
Host: api.github.com
```

对应 Retrofit 代码：

```java
@GET("/users/{id}")
Call<User> getUser(@Path("id") String id, @Query("gender") String gender);
```

###  POST

* 用于增加或修改资源
* 发送给服务器的内容写在 Body 里面

```
POST /users HTTP/1.1
Host: api.github.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 13

name=dyx&gender=male
```

对应 Retrofit 代码：

```java
@FormUrlEncoded
@POST("/users")
Call<User> addUser(@Field("name") String name, @Field("gender") String
gender);
```

### PUT

* 用于修改资源
* 发送给服务器的内容写在 Body 里面

```
PUT /users/1 HTTP/1.1
Host: api.github.com
Content-Type: application/x-www-form-urlencoded
Content-Length: 13

gender=female
```

对应 Retrofit 代码：

```java
@FormUrlEncoded
@PUT("/users/{id}")
Call<User> updateGender(@Path("id") String id, @Field("gender") String
gender);
```

### DELETE

* 用于删除资源
* 不发送 Body

```
DELETE /users/1 HTTP/1.1
Host: api.github.com
```

对应 Retrofit 代码：

```java
@DELETE("/users/{id}")
Call<User> getUser(@Path("id") String id, @Query("gender") String gender);
```

### HEAD

* 和 GET 使⽤⽅法完全相同 
* 和 GET 唯⼀区别在于，返回的响应中没有 Body

## Status Code 状态码

三位数字，用于对响应结果做出类型化描述（如「获取成功」「内容未找到」）。

### 1xx

* 临时性消息。eg：100 （继续发送） 、101（正在切换协议）

### 2xx

* 成功。eg：200（OK）、201（创建成功）

### 3xx

* 重定向。eg：301（永久移动）、302（暂时移动）、304（内容未改变）

### 4xx

* 客户端错误。eg：400（客户端请求错误）、401（认证失败）、403（被禁⽌）、404（找不到内容）

### 5xx

* 服务器错误。eg：500（服务器内部错误）

## Header

作用：HTTP 消息的元数据（matadata）

### Host

* 目标主机。注意：不是在网络上用于寻址，而是在目标服务器上用于定位子服务器的。

  ```
  GET /users/1 HTTP/1.1
  Host: api.github.com
  ```

  > api.github.com ：是一个服务器地址，但不是用来寻址
  >
  > 浏览器把报文拼好以后，请求之前就已经（通过 DNS）进行寻址了。

### Content-Type

> 指定 Body 的类型

* text/html
  * 请求 Web 页面，返回响应的类型，Body 中返回 html 文本

    ```
    HTTP/1.1 200 OK
    Content-Type: text/html; charset=utf-8
    Content-Length: 853
    
    <!DOCTYPE html>
    <html>
    <head>
     <meta charset="utf-8">
    .....
    ```

* application/x-www-form-urlencoded

  * 普通表单，encoded URL 格式 （URL编码形式进行表单提交，纯文字表单）

    ```
    POST /users HTTP/1.1
    Host: api.github.com
    Content-Type: application/x-www-form-urlencoded
    Content-Length: 27
    
    name=dyx&gender=male
    ```

    对应 Retrofit 的代码：
    
    ```java
    @FormUrlEncoded
    @Post("/users")
    Call<User> addUser(@Field("name") String name, @Field("gender") String gender);
    ```

* multipart/form-data
  * 多部分形式，一般用于传输包含二进制内容的多项内容

    ```
    POST /users HTTP/1.1
    Host: hencoder.com
    Content-Type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW
    Content-Length: 2382
    
    ------WebKitFormBoundary7MA4YWxkTrZu0gW
    Content-Disposition: form-data; name="name"
    
    dingyx
    ------WebKitFormBoundary7MA4YWxkTrZu0gW
    Content-Disposition: form-data; name="avatar"; filename="avatar.jpg"
    Content-Type: image/jpeg
    
    JFIFHHvOwX9jximQrWa.....
    ------WebKitFormBoundary7MA4YWxkTrZu0gW--
    ```

    对应 Retrofit 代码：

    ```java
    @Multipart
    @Post("/users")
    Call<User> addUser(@Part("name") RequestBody name, @Part("avatar") RequestBody avatar);
    
    ...
    
    // 调用
    RequestBody namePart = RequestBody.create(MediaType.parse("text/plain"),
    nameStr);
    RequestBody avatarPart = RequestBody.create(MediaType.parse("image/jpeg"),
    avatarFile);
    api.addUser(namePart, avatarPart);
    ```

* application/json , image/jpeg , application/zip ..
  > 单项内容（⽂本或⾮⽂本都可以），⽤于 Web Api 的响应或者 POST / PUT 的请求

  * 请求中提交 JSON

    ```
    POST /users HTTP/1.1
    Host: hencoder.com
    Content-Type: application/json; charset=utf-8
    Content-Length: 38
    
    {"name":"dingyx","gender":"male"}
    ```

    对应 Retrofit 代码：

    ```java
    @POST("/users")
    Call<User> addUser(@Body("user") User user);
    ...
    // 需要使⽤ JSON 相关的 Converter
    api.addUser(user);
    ```

  * 响应中返回 JSON

    ```
    HTTP/1.1 200 OK
    content-type: application/json; charset=utf-8
    content-length: 234
    
    [{"login":"mojombo","id":1,"node_id":"MDQ6VXNl
    cjE=","avatar_url":"https://avatars0.githubuse
    rcontent.com/u/1?v=4","gravat......
    ```

  * 请求中提交二进制内容

    ```
    POST /user/1/avatar HTTP/1.1
    Host: hencoder.com
    Content-Type: image/jpeg
    Content-Length: 1575
    
    JFIFHH9......
    ```

    对应 Retrofit 的代码：

    ```java
    @POST("users/{id}/avatar")
    Call<User> updateAvatar(@Path("id") String id, @Body RequestBody avatar);
    ...
    RequestBody avatarBody = RequestBody.create(MediaType.parse("image/jpeg"),
    avatarFile);
    api.updateAvatar(id, avatarBody)
    ```

  * 响应中返回二进制内容

    ```
    HTTP/1.1 200 OK
    content-type: image/jpeg
    content-length: 1575
    
    JFIFHH9......
    ```

### Content-Length

* Body 的长度（字节）

### Transfer​-Encoding

Transfer-Encoding: chunked

> 分块传输编码 Chunked Transfer Encoding

* ⽤于当响应发起时，内容⻓度还没能确定的情况下。和 Content-Length 不同时使⽤。⽤途是尽早给 出响应，减少⽤户等待

  ```
  HTTP/1.1 200 OK
  Content-Type: text/html
  Transfer-Encoding: chunked
  
  4
  Chun
  9
  ked Trans
  12
  fer Encoding
  0
  
  ```

  > 最后传输 0 表示内容结束（0+换行）

### Location

* 指定重定向的目标 URL

### User-Agent

* 用户代理，即是谁实际发送请求、接受响应的，例如⼿机浏览器、某款⼿机 App

### Range / Accept-Range

* 指定 Body 内容范围

  ```
  Accept-Range: bytes
  
  响应报⽂中出现，表示服务器⽀持按字节来取范围数据
  ```

  ```
  Range: bytes=<start>-<end> 
  
  请求报⽂中出现，表示要取哪段数据
  ```

  ```
  Content-Range:<start>-<end>/total 
  
  响应报⽂中出现，表示发送的是哪段数据
  ```

  > 作⽤：断点续传、多线程下载

### Cookie / Set-Cookie

* 发送 Cookie / 设置 Cookie

### Authorization

* 授权信息

### Cache

* 作⽤：在客户端或中间⽹络节点缓存数据，降低从服务器取数据的频率，以提⾼⽹络性能

  > 注意与Buffer区别：Buffer 针对工作流（在线播放视频流提前缓冲）
  >
  > Cache-用过的东西，后面可能会用。

  * Cache-Control：no-cache、no-store、max-age

  * Last-Modified

    * If-Modified-Since

  * Etag

    * If-None-Match

  * Cache-Control：private / public

    > 中间节点-是否缓存（private-定制化的个性信息）

### 其他 Headers

* Accept: 客户端能接受的数据类型。如 text/html 

* Accept-Charset: 客户端接受的字符集。如 utf-8 
* Accept-Encoding: 客户端接受的压缩编码类型。如 gzip 
* Content-Encoding：压缩类型。如 gzip

## Rest

* Client-server architecture 客户端服务器架构（C/S架构）
* Statelessness 无状态：两个请求间不存在状态
* Cacheability 可缓存
* Layered System 分层性
* Code on demand 可执行代码
* Uniform interface 统一接口

### Restful HTTP

> REST HTTP 即正确使⽤ HTTP

* 使用资源的格式来定义 URL
* 规范地使用 method 来定义网络请求操作
* 规范地使⽤ status code 来表示响应状态
* 其他符合 HTTP 规范的设计准则


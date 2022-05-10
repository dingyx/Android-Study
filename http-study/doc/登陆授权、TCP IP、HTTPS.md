# 登陆授权、TCP / IP、HTTPS

## 登录和授权

### 登陆和授权区别

* 登陆：身份认证，即确认**你是你**的过程
* 授权：由身份或持有的令牌确认享有某些权限（例如获取⽤户信息）。登录过程实质上的⽬的也 是为了确认权限。 

因此，在实际的应⽤中，多数场景下的「登录」和「授权」界限是模糊的。

### HTTP 中确认授权（登陆）的两种方式

* Cookie
* Authorization Header

### Cookie

* Cookie起源：「购物⻋」功能的需求，由 Netscape 浏览器开发团队打造。

* 工作机制

  1. 服务器需要客户端保存的内容，放在  ```set-Cookie``` header 里返回，客户端会自动保存。
  2. 客户端保存的 Cookies，会在之后的所有请求⾥都携带进 ```Cookie``` header ⾥发回给服务器。 
  3. 客户端保存 Cookie 是按照服务器域名来分类的，例如 shop.com 发回的 Cookie 保存下来 以后，在之后向 games.com 的请求中并不会携带。 
  4. 客户端保存的 Cookie 在超时后会被删除、没有设置超时时间的 Cookie （称作 Session Cookie）在浏览器关闭后就会⾃动删除；另外，服务器也可以主动删除还未过期的客户端 Cookies。

  ![Cookie工作机制](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/Cookie工作机制.png)
  
* Cookie 作用

  * 会话管理：登陆状态、购物车
  * 个性化：用户偏好、主题
  * Tracking：分析用户行为
  * XSS (Cross-site scripting)
  * XSRF (Cross-site request forgery)

### Authorization Header


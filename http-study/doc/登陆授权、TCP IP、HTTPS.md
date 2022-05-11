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

    使用 Cookie 管理登陆状态

    ![使用Cookie管理登陆状态](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/使用Cookie管理登陆状态.png)

  * 个性化：用户偏好、主题

    ![使用Cookie管理用户偏好](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/使用Cookie管理用户偏好.png)

  * Tracking：分析用户行为

    ![使用Cookie追踪用户行为](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/使用Cookie追踪用户行为.png)

* ### XSS (Cross-site scripting)

  > 跨站脚本攻击。即使⽤ JavaScript 拿到浏览器的 Cookie 之后，发送到⾃⼰的⽹站，以这种⽅式来盗取⽤户 Cookie。应对⽅式：Server 在发送 Cookie 时，敏感的 Cookie 加上 HttpOnly。

  * 应对⽅式：HttpOnly——这个 Cookie 只能⽤于 HTTP 请求，不能被 JavaScript 调⽤。它可 以防⽌本地代码滥⽤ Cookie。eg：```Set-Cookie:sessionid=123;HttpOnly```

* ### XSRF (Cross-site request forgery)

  > 跨站请求伪造。即在⽤户不知情的情况下访问已经保存了 Cookie 的⽹站，以此来越权操作⽤户账户（例如盗取⽤户资⾦）。应对⽅式主要是 从服务器安全⻆度考虑。

  * 应对⽅式：Referer 校验。

### Authorization

#### Basic

* 格式

  ```
  Authorization：Basic <username:password(Base64ed)>
  
  Authorization：Basic ZHl4OjEyMzQ1Ng==
  ```

#### Bearer

* 格式

  ```
  Authorization：Bearer <bearer token>
  ```

* bearer token 的获取⽅式：通过 OAuth2 的授权流程

  > OAuth2 的流程：

  1.  第三⽅⽹站向授权⽅⽹站申请第三⽅授权合作，拿到 client id 和 client secret 

  2. ⽤户在使⽤第三⽅⽹站时，点击「通过 XX (如 GitHub) 授权」按钮，第三⽅⽹站将⻚⾯跳 转到授权⽅⽹站，并传⼊ client id 作为⾃⼰的身份标识

     ![OAuth2第三方网站传入clientid](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/OAuth2第三方网站传入clientid.png)

  3. 授权⽅⽹站根据 client id ，将第三⽅⽹站的信息和第三⽅⽹站需要的⽤户权限展示给⽤ 户，并询问⽤户是否同意授权

     ![OAuth2授权方网站询问用户同意授权](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/OAuth2授权方网站询问用户同意授权.png)

  4. ⽤户点击「同意授权」按钮后，授权⽅⽹站将⻚⾯跳转回第三⽅⽹站，并传⼊ Authorization code 作为⽤户认可的凭证。

     ![OAuth2授权方返回AuthorizationCode](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/OAuth2授权方返回AuthorizationCode.png)

  5. 第三⽅⽹站将 Authorization code 发送回⾃⼰的服务器

     ![OAuth2第三方网站将AuthorizationCode发送到自己服务器](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/OAuth2第三方网站将AuthorizationCode发送到自己服务器.png)

  6. 服务器将 Authorization code 和⾃⼰的 client secret ⼀并发送给授权⽅的服务器，授权⽅服务器在验证通过后，返回 access token。OAuth 流程结束。

     ![OAuth2第三方获取access_token](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/OAuth2第三方获取access_token.png)

  7. 在上⾯的过程结束之后，第三⽅⽹站的服务器（或者有时客户端也会）就可以使⽤ access token 作为⽤户授权的令牌，向授权⽅⽹站发送请求来获取⽤户信息或操作⽤户账户。但这 已经在 OAuth 流程之外。

     > OAuth2第三方网站向授权方获取用户信息

     ```
     get /user http/1.1
     Host: github.com
     Authorization: Bearer abcdefg...
     ```

![OAuth2第三方网站向授权方获取用户信息](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/OAuth2第三方网站向授权方获取用户信息.png)

* OAuth 引⼊ Authorization code 意义

  为什么 OAuth 要引⼊ Authorization code，并需要申请授权的第三⽅将 Authorization code 发送回⾃⼰的服务器，再从服务器来获取 access token，⽽不是直接返回 access token ？这样复杂的流程意义何在？ 为了安全。OAuth 不强制授权流程必须使⽤ HTTPS，因此需要保证当通信 路径中存在窃听者时，依然具有⾜够⾼的安全性。

  > 以下示例不安全（失去 OAuth2 安全性， access token 可能被窃取）

  * 服务器返回 access token 给客户端

    ![OAuth2服务器返回access_token给客户端](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/OAuth2服务器返回access_token给客户端.png)

  * 客户端使用 access token 发送请求

    ![OAuth2客户端直接使用access_token](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/OAuth2客户端直接使用access_token.png)

* 第三方 App 通过微信登陆流程

  > 是一个 完整OAuth2 流程

  1. 第三⽅ App 向腾讯申请第三⽅授权合作，拿到 client id 和 client secret 
  2.  ⽤户在使⽤第三⽅ App 时，点击「通过微信登录」，第三⽅ App 将使⽤微信 SDK 跳转到 微信，并传⼊⾃⼰的 client id 作为⾃⼰的身份标识 
  3. 微信通过和服务器交互，拿到第三⽅ App 的信息，并限制在界⾯中，然后询问⽤户是否同 意授权该 App 使⽤微信来登录  
  4. ⽤户点击「使⽤微信登录」后，微信和服务器交互将授权信息提交，然后跳转回第三⽅ App，并传⼊ Authorization code 作为⽤户认可的凭证 
  5. 第三⽅ App 调⽤⾃⼰服务器的「微信登录」API，并传⼊ Authorization code，然后等待 服务器的响应 
  6. 服务器在收到登录请求后，拿收到的 Authorization code 去向微信的第三⽅授权接⼝发送 请求，将 Authorization code 和⾃⼰的 client secret ⼀起作为参数发送，微信在验证通过后，返回 access token 
  7. 服务器在收到 access token 后，⽴即拿着 access token 去向微信的⽤户信息接⼝发送请 求，微信验证通过后，返回⽤户信息 
  8. 服务器在收到⽤户信息后，在⾃⼰的数据库中为⽤户创建⼀个账户，并使⽤从微信服务器 拿来的⽤户信息填⼊⾃⼰的数据库，以及将⽤户的 ID 和⽤户的微信 ID 做关联 
  9. ⽤户创建完成后，服务器向客户端的请求发送响应，传送回刚创建好的⽤户信息 
  10. 客户端收到服务器响应，⽤户登录成功

* 在自家 App 使用 Bear token

  有的 App 会在 API 的设计中，将登录和授权设计成类似 OAuth2 的过程，但简化掉 Authorization code 概念。即：登录接⼝请求成功时，会返回 access token，然后客户端在之后的请求中，就可以使⽤这个 access token 来当做 bearer token 进⾏⽤户操作了。

  ```
  api.dingyx.tech/login?username=dyx&password=123
  
  access_token=qqqq5656aaaa
  
  Authorization：Bearer qqqq5656aaaa
  ```

  * Refresh token

    ```
    {
     "token_type": "Bearer",
     "access_token": "xxxxx",
     "refresh_token": "xxxxx",
     "expires_time": "xxxxx"
    } 
    ```

    > access token 有失效时间，在它失效后，调⽤ refresh token 接⼝，传⼊ refresh_token 来获取新的 access token。

  * 目的：安全。当 access_token 失窃，由于它有有效时间，因此坏⼈只有较短的时间来「做坏事」；同时，由于（在标准的 OAuth2 流程中）refresh token 永远只存在与第三⽅服务的服务器中，因此 refresh token ⼏乎没有失窃的⻛险。




## TCP / IP 协议族

* 概念

  ⼀系列协议所组成的⼀个⽹络分层模型

* 为什么要分层

  因为⽹络的不稳定性

  ![TCP&IP协议族为什么要分层](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/TCP&IP协议族为什么要分层.png)

  > 分块传输（传输内容很多时，避免每次失败都要全部重新传）

### 具体分层

* Application Layer 应⽤层：HTTP、FTP、DNS

* Transport Layer 传输层：TCP、UDP

  > 拆包

* Internet Layer ⽹络层：IP

  > 寻址（查找路由）

* Link Layer 数据链路层：以太⽹、Wi-Fi

  > 传输数据

#### TCP 连接

* 什么叫做连接？

  通信双⽅建⽴确认「可以通信」，不会将对⽅的消息丢弃，即为「建⽴连接」

* TCP 连接的建立与关闭

  ![TCP连接的建立](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/TCP连接的建立.png)

  ![TCP连接的关闭](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/TCP连接的关闭.png)

#### 长连接

* 为什么需要长连接？

  因为移动⽹络并不在 Internet 中，⽽是在运营商的内⽹，并不具有真正的公⽹ IP，因此当某个 TCP 连接在⼀段时间不通信之后，⽹关会出于⽹络性能考虑⽽关闭这条 TCP 连接和公⽹的连接通道，导致 这个 TCP 端⼝不再能收到外部通信消息，即 TCP 连接被动关闭。

* ⻓连接的实现⽅式

  ⼼跳。即在⼀定间隔时间内，使⽤ TCP 连接发送超短⽆意义消息来让⽹关不能将⾃⼰定义为「空闲连 接」，从⽽防⽌⽹关将⾃⼰的连接关闭。

## HTTPS

* 定义：HTTP over SSL 的简称，即⼯作在 SSL （或 TLS）上的 HTTP。说⽩了就是加密通信的 HTTP。

  > 在 HTTP 之下增加的一个安全层，用于保障 HTTP 的加密传输。

* SSL

  Secure Sockets Layer 安全套接字协议 --> Transport Layer Security TLS

  > 为网络通信提供安全及数据完整性的一种安全协议

### 工作原理

在客户端和服务器之间协商出一套对称密钥，每次发送信息之前将内容加密，收到之后解密，达到内容的加密传输

* 为什么不直接使用非对称加密

  ⾮对称加密由于使⽤了复杂了数学原理，因此计算相当复杂，如果完全使⽤⾮对称加密来加密通信内 容，会严重影响⽹络通信的性能

### HTTPS 连接建立的过程

1. 客户端请求建立 TLS 连接
2. 服务器返回证书
3. 客户端验证服务器证书
4. 客户端信任服务器后，和服务器协商（非对称加密）对称密钥
5. 使用对称密钥开始通信




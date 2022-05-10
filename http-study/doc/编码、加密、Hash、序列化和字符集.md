# 编码、加密、Hash

## 密码学

### 古典密码学

> 起源：古代战争--古典密码学

* 移位式加密

  ![移位加密](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/移位加密.png)

  ```
  china->FKLQD
  
  c->F
  h->K
  i->L
  n->Q
  a->D
  ```

  * 加密算法：位移
  * 密钥：位移数

* 替换式加密

  ![替换加密](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/替换加密.png)

  ```
  china->HTJNW
  
  c->H
  h->T
  i->J
  n->N
  a->W
  ```

  * 加密算法：替换文字
  * 密钥：码表

### 现代密码学

* 不止可以用于文字内容，还可以用于各种二进制数据

#### 对称加密

* 通信双⽅使⽤**同⼀个密钥**，使⽤**加密算法**配合上密钥来加密，解密时使⽤加密过程的完全**逆过程**配合 密钥来进⾏解密。

  > 简化模型即上⾯的古典密码学中替换式加密的模型：对⽂字进⾏规则化替换来加密，对密⽂进⾏逆向的规则化替换来解密。

  ![对称加密](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/对称加密.png)

* 经典算法

  * DES

    > 64位密钥，密钥太短⽽逐渐被弃⽤。

  * AES

    > 128 位、192 位、256 位密钥，现在最流⾏

* 对称加密（如 AES）的破解

  > 破解思路

  * 拿到⼀组或多组原⽂-密⽂对
  * 设法找到⼀个密钥，这个密钥可以将这些原⽂-密⽂对中的原⽂加密为密⽂，以及将密⽂解密为 原⽂的组合，即为成功破解

  > 反破解

  * ⼀种优秀的对称加密算法的标准是，让破解者找不到⽐穷举法（暴⼒破解法）更有效的破解⼿段，并 且穷举法的破解时间⾜够⻓（例如数千年）。

* 对称加密缺点

  > 密钥泄露：**不能在不安全⽹络上传输密钥**，⼀旦密钥泄露则加密通信失败。

#### 非对称加密

* 使⽤公钥对数据进⾏加密得到密⽂；使⽤私钥对数据进⾏解密得到原数据。 

  > ⾮对称加密使⽤的是复杂的数学技巧，在古典密码学中没有对应的原型。

  ![非对称加密](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/非对称加密.png)

* 使⽤⾮对称加密通信，可以在不可信⽹络上将双⽅的公钥传给对⽅，然后在发消息前分别对消息使⽤ 对⽅的公钥来加密和使⽤⾃⼰的私钥来签名，做到不可信⽹络上的可靠密钥传播及加密通信。

  ![非对称加密公钥发送](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/非对称加密公钥发送.png)

* 由于私钥和公钥<font color=green>互相可解</font>，因此⾮对称加密还可以应⽤于数字签名技术。

  ![非对称加密签名](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/非对称加密签名.png)

  * 通常会对原数据 hash 以后对 hash 签名，然后附加在原数据的后⾯作为签名。这是为了让数据更⼩。

    > **加密 + 签名**

    ![非对称加密加密和签名](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/非对称加密加密和签名.png)

    ![非对称加密签名与验证](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/非对称加密签名与验证.png)

* 经典算法

  * RSA

    > 加密\解密、签名

  * DSA

    > 签名（速度优势）

* 非对称加密优缺点

  * 优点：可以在不安全⽹络上传输密钥
  * 缺点：计算复杂，因此性能相⽐对称加密差很多

* ⾮对称加密（如 RSA、ECDSA）的破解

  > 破解思路

  * 和对称加密不同之处在于，⾮对称加密的公钥很容易获得，因此制造原⽂-密⽂对是没有困难的事 
  * 所以，⾮对称加密的关键只在于，如何找到⼀个正确的私钥，可以解密所有经过公钥加密过的密 ⽂。找到这样的私钥即为成功破解 
  * 由于⾮对称加密的⾃身特性，怎样通过公钥来推断出私钥通常是⼀种思路（例如 RSA），但往往 最佳⼿段依然是穷举法，只是和对称加密破解的区别在于，对称加密破解是不断尝试⾃⼰的新密 钥是否可以将⾃⼰拿到的原⽂-密⽂对进⾏加密和解密，⽽⾮对称加密时不断尝试⾃⼰的新私钥 是否和公钥互相可解。

  > 反破解

  * 和对称加密⼀样，⾮对称加密算法优秀的标准同样在于，让破解者找不到⽐穷举法更有效的破解⼿ 段，并且穷举法的破解时间⾜够⻓。

### 密码学密钥和登陆密码

> 仅需要理解

* 密钥 （key）
  * 场景：用于加密、解密
  * 目的：保证数据被盗时不会被人读懂
  * 焦点：数据
* 登陆密码（password）
  * 场景：用户进入网站或游戏前身份验证
  * 目的：数据提供方或应用服务方对账户拥有者的数据保护，保证***你是你***时才提供权限
  * 焦点：身份

## Base64

将二进制数据转换成由 64 个字符组成的字符串的编码算法

* 什么是二进制数据
  * 广义：所有计算机数据都是⼆进制数据
  * 狭义：⾮⽂本数据即⼆进制数据

### 算法

将原数据每 6 位对应成 Base 64 索引表中的⼀个字符编排成⼀个字符串（每个字符 8 位）。

* Base64 索引表：

  > A-Z、a-z、0-9、+、/

  ![Base64索引表](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/Base64索引表.png)

* 编码示例：把「Man」进⾏ Base64 编码

  ![Base64_Man](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/Base64_Man.png)

* 编码示例：Base64 的末尾补⾜

  ![Base64末尾补足](https://dingyx.oss-cn-shenzhen.aliyuncs.com/picgo/Base64末尾补足.png)

### Base64 用途

* 将⼆进制数据扩充了储存和传输途径（例如可以把数据保存到⽂本⽂件、可以通过聊天对话框或 短信形式发送⼆进制数据、可以在 URL 中加⼊简单的⼆进制数据）
* 普通的字符串在经过 Base64 编码后的结果会变得⾁眼不可读，因此可以适⽤于⼀定条件下的防偷窥（较少⽤）

### Base64 的缺点

因为⾃身的原理（6 位变 8 位），因此每次 Base64 编码之后，数据都会增⼤约 1/3，所以会影响存 储和传输性能。

### Base64 加密图⽚传输更安全和⾼效 ？？？

不。⾸先，Base64 并不是加密；另外，Base64 会导致数据增⼤ 1/3，降低⽹络性能，增⼤⽤户流量 开销，是画蛇添⾜的⼿段。

Base64 对图⽚进⾏编码的⽤于在于，有时需要使⽤⽂本形式来传输图⽚。除此之外，完全没必要使 ⽤ Base64 对图⽚进⾏额外处理。

### 变种：Base58

⽐特币使⽤的编码⽅式，去掉了 Base64 中的数字 "0"，字⺟⼤写 "O"，字⺟⼤写 "I"，和字⺟⼩写 "l"，以及 "+" 和 "/" 符号，⽤于⽐特币地址的表示。

 Base58 对于 Base64 的改动，主要⽬的在于⽤户的便捷性。由于去掉了难以区分的字符，使得 Base58 对于「⼈⼯抄写」更加⽅便。另外，去掉了 "+" "/" 号后也让⼤多数的软件可以⽅便双击选取。 

### URL encoding

> 类似 Base64 的变种

* 在 URL 的字符串中，对⼀些不⽤于特殊⽤途的保留字符，使⽤百分号 "%" 为前缀进⾏单独编码，以避免出现解析错误

* 目的：消除歧义，避免解析错误

  > 要在 http://github.com/users 后⾯添加查询字符串，查询 name 为「李&王」的⽤ 户，如果直接写成 http://github.com/user/?name=李&王 ，"&" 符号就会被解析为分隔符号，因此需要对它进⾏转码，转码后的 URL 为 http://github.com/user/?name=李%26王 。 

## 压缩与解压缩

### 压缩

* 将数据换一种方式来存储，以减小存储空间

### 解压缩

* 将压缩数据解码还原成原来的形式，以方便使用

### 粗暴算法举例

* 将下面文本内容压缩：

  ```
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
  aaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb
  bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb
  ```

* 使用某种算法压缩后数据：

  ```
  compress:a:1062;b:105
  ```

### 压缩是编码吗？

是。所谓编码，即把数据**从一种形式**转换为**另一种形式**。压缩过程属于**编码**过程，解压缩过程属于**解码**过程。

### 常见压缩算法

* DEFLATE

  > zip 打包归档--使用的压缩算法为 DEFLATE

* JPEG

  > 对 JPG 图片压缩

* MP 3

### 图片与音频、视频编解码

> 将图像、音频、视频数据通过编码来转换成存档形式（编码），以及从存档形式转换回来。

* 目的

  存储和压缩媒体数据（大多数媒体编码算法会压缩数据，但不是全部）。

* 图片压缩粗暴算法举例

  一张纯白（⽩⾊的 16 进制数值为 0xfffff）的 64 x 64 不透明像素图⽚，原数据格式⼤致为：

  ```
  width:64;height:64;ffffffffffffffffffffffffffffffffffffffffffffffffffffffff
  fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
  fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
  fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
  fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
  fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
  fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
  fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
  fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
  fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
  fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff.......f
  fffff
  ```

  使用某种算法压缩后数据为：

  ```
  width:64;height:64;ffffff:[0,0]-[63,63]
  ```

  > 具体的压缩场景很多，因此压缩算法也会复杂很多，上⾯只是⼀个原型算法。

  音频与视频的编码和图片编码同理。

## 序列化

将数据对象（一般是内存中的，例如 JVM 中的对象）转换成字节序列化的过程。对象在程序内存里的存放形式是散乱的（存放在不同的内存区域、并且由引用进行连接），通过序列化可以把内存中的对象转换层一个字节序列，从而使用 byte[] 等形式进行本地存储或网络传输，在需要的时候重新组装（反序列化）来使用。

* 目的

  让内存中的对象可以被存储和传输

* 和编码的区别

  > 序列化不是编码

  编码是把数据由⼀种数据格式转换成另⼀种数据格式；⽽序列化是把数据由**内存中的对象**（⽽不是某 种具体的格式）转换成**字节序列**。

## Hash

把任意数据转换成指定大小范围（通常很小，例如 256 字节以内）的数据。

* 作用

  相当于从数据中提出摘要信息，因此最主要的用途是数字指纹。

### Hash的实际用途

#### 数据完整性验证

从⽹络上下载⽂件后，通过⽐对⽂件的 Hash 值（例如 MD5、SHA1），可以确认下载的⽂件是否有损坏。如果下载的⽂件 Hash 值和⽂件提供⽅给出的 Hash 值⼀致，则证明下载的⽂件是完好⽆损的。

#### 快速查找

hashCode() 和 HashMap

#### 隐私保护

当重要数据必须暴露的时候，有时可以选择暴露它的 Hash 值（例如 MD5），以保障原数据的安全。 例如⽹站登录时，可以只**保存⽤户密码的 Hash 值**，在每次登录验证时只需要将**输⼊的密码的 Hash 值**和**数据库中保存的 Hash 值**作⽐对就好，⽹站⽆需知道⽤户的密码。这样，当⽹站数据失窃时，⽤户不会因为⾃⼰的密码被盗导致其他⽹站的安全也受到威胁。

> 注意：这不属于加密。

> 彩虹表对策：加盐

#### 唯一性验证

例如 Java 中 hashCode() 方法

### Hash 是编码吗？

不是。Hash 是单向过程，往往是不可逆的，⽆法进⾏逆向恢复操作，因此 Hash 不属于编码。

### Hash 是加密吗？

不是。Hash 是单向过程，⽆法进⾏逆向回复操作，因此 Hash 不属于加密。（记住，MD5 不是加密！）

## 字符集

⼀个由整数向现实世界中的⽂字符号映射的 Map

 * ASCII：128 个字符，1 字节 
 * ISO-8859-1：对 ASCII 进⾏扩充，1 字节 
 * Unicode：13 万个字符，多字节
    *  UTF-8：Unicode 的编码分⽀ 
    * UTF-16：Unicode 的编码分⽀ 
 * GBK / GB2312 / GB18030：中国⾃研标准，多字节，字符集 + 编码

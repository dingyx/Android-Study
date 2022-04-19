#### HTTP 

* HyperText Transfer Protocol 

  > 超文本传输协议

  * 超文本：在电脑中显示的、含有可以指向其他文本的链接的文本

    > HTML

* URL ---> HTTP报文

  ```
  http://dingyx.tech/users?name=dyx
  
  协议类型-----http
  服务器地址---dingyx.tech
  路径--------users?name=dyx
  
  
  GET /users?name=dyx  HTTP/1.1
  Host: dingyx.tech
  ```

  * 报文格式：Request

    ```
    请求行 --- GET   /users HTTP/1.1
    		  Host: api.dingyx.tech
    		  
    method:GET
    path:/users
    http version:HTTP/1.1
    
    
    Headers
    	Host: api.dingyx.tech
    	Content-Type:text/plain
    	Content-Length：
    ```

    


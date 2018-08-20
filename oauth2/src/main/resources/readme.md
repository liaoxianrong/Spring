## 正常请求
http://localhost:8080/product/123
可以访问
## 授权资源请求
http://localhost:8080/order/123
拒绝请求：
```
<oauth>
<error_description>
Full authentication is required to access this resource
</error_description>
<error>unauthorized</error>
</oauth>
```
## 请求授权
1. password 模式
http://localhost:8080/oauth/token?username=superuser&password=123456&grant_type=password&scope=read+write&client_id=client_password&client_secret=123456
```
{"access_token":"abb56b7d-fe88-415f-b6bb-543aac82f305","token_type":"bearer","refresh_token":"b499da2e-1d1d-404d-92d5-18a89265ea35","expires_in":42914,"scope":"select"}
```
2. client模式
http://localhost:8080/oauth/token?grant_type=client_credentials&scope=read+write&client_id=client_client&client_secret=123456
```
{"access_token":"8195507a-f6ae-43a5-85b3-8251a127a79e","token_type":"bearer","expires_in":43199,"scope":"select"}
```
3.code模式
http://localhost:8080/oauth/authorize?client_id=client_code&response_type=code&redirect_uri=http://www.baidu.com&state=liaoxr
http://localhost:8080/oauth/token?grant_type=authorization_code&client_id=client_code&client_secret=123456&redirect_uri=http://www.baidu.com&code=D94gH4


## 再次请求授权资源（带token）
http://localhost:8080/order/123?access_token=8195507a-f6ae-43a5-85b3-8251a127a79e


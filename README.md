#   模块介绍
### zhushan-shopp-parent

    项目的父模块
	
### zhushan-shopp-eurekaserver

    eureka server 注册中心
	
	启动端口：8761
	
### zhushan-shopp-configserver

    配置服务器，被多个其他模块使用，用于读取activitemq通道，读取邮件名称和邮件标题等，读取文件为当前路径下的application.yml
	
	启动端口：8888
	
### zhushan-shopp-api

    zhushan-shopp-api-member 和 zhushan-shopp-member 两个模块的父模块
	
### zhushan-shopp-common

    zhushan-shopp-member 模块的一部分，主要功能BaseResponse的封装和redis操作的封装
	
	启动端口：8762
	
	redis端口：6379
	
### zhushan-shopp-message 

    activitemq 邮件队列,该模块对邮件发送进行了实现，当用户注册后会通过activitemq接受邮箱号码并且通过读取配置服务器的配置进行邮件的发送
	
	启动端口：8763
	
	activitemq默认端口：8161
	
### zhushan-shopp-member

	会员模块,增加了tokenfilter，使用令牌去调用发布的服务，令牌登录后存储在redis中，登录后会返回一个token，访问其他服务需携带token访问
	
# 主要功能介绍

### zhushan-shopp-web

	会员调用模块，通过feign来调用会员模块发布的服务，新增降级策略和线程隔离
	
	启动端口：80

	
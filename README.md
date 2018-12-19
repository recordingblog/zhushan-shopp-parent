#   模块介绍
### zhushan-shopp-parent

    项目的父模块
	
### zhushan-shopp-eurekaserver

    eureka server 注册中心，用于服务注册于服务发现
	
	启动端口：8761
	
### zhushan-shopp-configserver

    配置服务器，被多个其他模块使用，用于读取activitemq通道名称，读取邮件名称和邮件标题等，读取文件为当前路径下的application.yml
	
	启动端口：8888
	
### zhushan-shopp-api

    zhushan-shopp-api-member 和 zhushan-shopp-member 两个模块的父模块
	
### zhushan-shopp-common

    主要功能BaseResponse的封装和redis操作的封装以及接口类型的抽取
	
	redis端口：6379
	
### zhushan-shopp-message 

    邮件队列,提供了统一接口，只对邮件发送进行了实现，当用户注册后会通过activitemq接收邮箱号码并且通过读取配置服务器的配置进行邮件的发送
	可以通过修改interfaceType的类型调用第三方接口使用短信或者其他方式
	
	启动端口：8763
	
	activitemq默认端口：8161
	
### zhushan-shopp-member

	会员模块,增加了tokenfilter，使用令牌去调用当前的服务，令牌登录后存储在redis中，登录后会返回一个token，访问其他服务需携带token访问
	令牌的有效时间为一分钟
	
	启动端口：8762
	
# 主要功能介绍

### zhushan-shopp-web

	会员调用模块，通过feign来调用会员模块发布的服务，新增降级策略和线程隔离
	
	启动端口：80
	
### zhushan-shopp-gateway

    网关模块,路由和token的校验
	
### zhushan-shopp-dashboard
    
	接口服务监控
	
	颜色说明：
	
	绿色：执行成功

	蓝色：短路

	青色：不良请求

	橘黄色：超时请求

	紫色：被拒绝

	红色：失败
	
	访问方式:127.0.0.1:port/hystrix.stream 需要看哪个接口的状态，就选哪个接口

### zhushan-shopp-admin
    
	服务告警模块，把配置服务器，网关，服务发布者，服务调度者注册进注册中心以后，当服务接口发生异常的
	时候会弹出服务告警并发送邮件通知
	
	注意事项：需要开启redis和activitemq状态下进行服务注册，只要在客户端启用客户端发现功能，并注册进eureka
	就会自己进行监控
	
	不开启redis,mysql等情况下会出现连接被拒的强制中断的情况，服务状态在启动状态下在springboot admin ui页面内的状态一直是down
	可以通过修改admin ui内的服务状态，在流量高峰期进行边缘业务下线操作
	
	
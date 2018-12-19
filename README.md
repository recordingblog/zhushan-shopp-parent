#   模块介绍
### zhushan-shopp-parent

    项目的父模块
	
### zhushan-shopo-eurekaserver

    eureka server 注册中心，用于服务的注册于发现
	
	启动端口：8761
	
### zhushan-shopp-configserver

    config server 服务器，用于读取队列通道名称，读取邮件名称和邮件标题等
	
	启动端口：8888
	
### zhushan-shopp-api

    zhushan-shopp-api-member 和 zhushan-shopp-member 两个模块的父模块
	
### zhushan-shopp-common

    zhushan-shopp-member 模块的一部分，主要功能为自定义响应内容的包装和redis的set，get功能
	
	启动端口：8762
	
	redis端口：6379
	
### zhushan-shopp-message 

    activitemq 邮件队列,该模块对邮件发送进行了实现
	
	启动端口：8763
	
	activitemq默认端口：8161
	
### zhushan-shopp-member

	会员模块接口
	
# 主要功能介绍

  
###	一、注册功能

		密码的加密存储
	
	    注册以后获取注册信息中的邮件名称，然后通过队列发送到zhushan-shopp-message模块进行注册短信的发送
		
###	二、登录功能
		
	    通过账号密码查询出用户id，通过用户id去redis中查找用户对应的令牌，有就返回使用
		
	    没有会重新生成一个令牌，访问接口需要通过令牌续访问，在filter中加入了token非空和有效性的校验
	
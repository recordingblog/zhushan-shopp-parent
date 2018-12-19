package com.zhushan.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer // 启动配置服务器读取远程配置
@EnableEurekaClient
public class ConfigServerApp {
	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApp.class, args);
	}
}

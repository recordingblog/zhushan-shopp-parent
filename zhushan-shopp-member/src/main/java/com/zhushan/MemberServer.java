package com.zhushan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@ServletComponentScan("com.zhushan.filter")
public class MemberServer {
	public static void main(String[] args) {
		SpringApplication.run(MemberServer.class, args);
	}

}

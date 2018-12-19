package com.zhushan.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.zhushan.base.ResponseBase;
import com.zhushan.domain.UserEntry;


@RestController
@RequestMapping(produces="application/json")
@DefaultProperties(
		commandProperties= {
		// 超时时间,超过超时时间触发降级
		@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "1000"),
		// 请求失败错误率,拿到指定的百分比打开断路器
		@HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="50"),
		// 10秒内的最大请求数量,超过阀值触发降级
		@HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
		// 断路器的休眠时间
		@HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="5000"),
		// 超时发生时是否被中断
		@HystrixProperty(name="execution.isolation.thread.interruptOnTimeout",value="true"),
		// 断路器是否生效
		@HystrixProperty(name="circuitBreaker.enabled",value="true"),
		// 使用线程隔离
		@HystrixProperty(name="execution.isolation.strategy",value="THREAD")
	  // 线程池配置
	},threadPoolProperties={
		// 核心线程池大小
		@HystrixProperty(name="coreSize",value="20"),
		// 当前线程池的最大值
		@HystrixProperty(name="maximumSize",value="10")
		})
public class MemberClientController extends MemberClientFallback{
	
	@Autowired
	private MemberClient memberClient;
	
	@RequestMapping("selectById/{id}")
	@HystrixCommand(fallbackMethod="selectByIdTimeOut")
	public ResponseBase selectById(@PathVariable Integer id){
		return memberClient.findById(id);
	}
	
	@RequestMapping("login")
	@HystrixCommand(fallbackMethod="loginTimeOut")
	public ResponseBase login(UserEntry user){
		return memberClient.login(user);
	}
	
	@RequestMapping("register")
	public ResponseBase register(UserEntry user){
		return memberClient.register(user);
		
	}
}

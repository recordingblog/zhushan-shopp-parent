package com.zhushan.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhushan.base.ResponseBase;
import com.zhushan.domain.UserEntry;

@FeignClient("zhushan-member")
@Component
public interface MemberClient {
	@RequestMapping("/user/findById/{id}")
	public ResponseBase findById(@PathVariable("id") Integer id);
	
	@RequestMapping("/user/login")
	public ResponseBase login(UserEntry user);
	
	@RequestMapping("/user/register")
	public ResponseBase register(UserEntry user);
}

package com.zhushan.feign;


import org.springframework.web.bind.annotation.PathVariable;

import com.zhushan.base.BaseApiService;
import com.zhushan.base.ResponseBase;
import com.zhushan.domain.UserEntry;

public class MemberClientFallback extends BaseApiService{

	public ResponseBase selectByIdTimeOut(@PathVariable Integer id){
		return setResultError("select by id is time out fallback");
	}
	
	public ResponseBase loginTimeOut(UserEntry user){
		return setResultError("login time out fallback");
	}
}

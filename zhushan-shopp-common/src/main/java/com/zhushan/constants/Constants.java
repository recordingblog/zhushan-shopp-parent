package com.zhushan.constants;

// 必须是接口才能被调用
public interface Constants {
	// 响应请求成功
	String HTTP_RES_CODE_200_VALUE = "success";
	// 系统错误
	String HTTP_RES_CODE_500_VALUE = "fial";
	// 响应请求成功code
	Integer HTTP_RES_CODE_200 = 200;
	// 系统错误
	Integer HTTP_RES_CODE_500 = 500;
	// 邮件
	String  MSG_EMAIL = "EMAIL";
	// 短信
	String  MSG_PHONE = "PHONE";
	// 用户令牌标识
	String TOKEN_MEMBER = "TOKEN_MEBER";
	// 令牌有效期
	Long TOKEN_MEMBER_TIME = (long) (60*1) ;
}

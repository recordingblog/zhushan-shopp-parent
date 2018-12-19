package com.zhushan.utils;

import java.util.UUID;

import com.zhushan.constants.Constants;

public class TokenUtils {
	public static String getMemberToken(){
		return Constants.TOKEN_MEMBER+"-"+UUID.randomUUID();
	}
}

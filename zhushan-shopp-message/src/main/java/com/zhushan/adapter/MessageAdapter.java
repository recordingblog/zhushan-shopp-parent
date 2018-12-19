package com.zhushan.adapter;

import com.alibaba.fastjson.JSONObject;

// 统一发送消息接口 匹配类型
public interface MessageAdapter {
	public void sendMsg(JSONObject body);
}

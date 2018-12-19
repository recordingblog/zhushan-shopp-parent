package com.zhushan.mq;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.zhushan.adapter.MessageAdapter;
import com.zhushan.constants.Constants;
import com.zhushan.email.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ConsumerDistribute {
	
	private MessageAdapter messageAdapter;
	
	@Autowired
	private EmailService emailService;
	
	private  final Logger log = LoggerFactory.getLogger(ConsumerDistribute.class);
	// 监听消息
	@JmsListener(destination = "messages_queue") // 监听指定队列
	public void distribute(String json) {
		log.info("#####消息服务平台接受消息内容:{}#####",json);
		if (StringUtils.isEmpty(json)) {
			return; // json = null
		}
		@SuppressWarnings("static-access") // mq规范
		JSONObject rootJSON = new JSONObject().parseObject(json);
		JSONObject header = rootJSON.getJSONObject("header"); // 获取header
		String interfaceType = header.getString("interfaceType"); // 从header获取接口类型

		if (StringUtils.isEmpty(interfaceType)) {
			return; // 接口类型 = null
		}
		// 判断接口类型是否为发邮件
		if (interfaceType.equals(Constants.MSG_EMAIL)) {
			messageAdapter = emailService; // 拿到匹配类型
		}
	
		if (messageAdapter == null) { // 没找到匹配类型
			return;
		}
		JSONObject contentJson = rootJSON.getJSONObject("content");
		messageAdapter.sendMsg(contentJson);// 调用邮件发送接口
	}
}

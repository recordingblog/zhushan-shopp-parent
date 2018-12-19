package com.zhushan.email.service;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.zhushan.adapter.MessageAdapter;
import com.zhushan.mq.ConsumerDistribute;

// 处理第三方发送邮件
@Service
public class EmailService implements MessageAdapter {

	@Value("${com.zhushan.msg.subject}")
	private String subject;
	@Value("${com.zhushan.msg.test}")
	private String text;
	@Autowired
    private  JavaMailSender  javaMailSender;
	
	private  final Logger log = LoggerFactory.getLogger(ConsumerDistribute.class);
	
	public void sendMsg(JSONObject body) {
		// 处理发送邮件
		String email = body.getString("email");
		if (StringUtils.isEmpty(email)) {
			return;
		}
		log.info("消息服务平台发送邮件:{}开始", email);
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		// 来自账号
		simpleMailMessage.setFrom("784753574@qq.com");
		// 发送账号
		simpleMailMessage.setTo(email);
		// 标题
		simpleMailMessage.setSubject(subject);
		// 内容
		simpleMailMessage.setText(text.replace("{}", email));
		// 发送邮件
		javaMailSender.send(simpleMailMessage);
		log.info("消息服务平台发送邮件:{}完成", email);
		
	}
}

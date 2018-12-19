package com.zhushan.controller;

import java.util.Date;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.zhushan.api.test.MemberService;
import com.zhushan.base.BaseApiService;
import com.zhushan.base.LogAspectServiceApi;
import com.zhushan.base.ResponseBase;
import com.zhushan.constants.Constants;
import com.zhushan.domain.UserEntry;
import com.zhushan.mq.RegisterMailboxProducer;
import com.zhushan.utils.MD5Util;
import com.zhushan.utils.TokenUtils;

@RestController
@RequestMapping(value = "user",produces="application/json")
public class MemberController extends BaseApiService {
	
	@Value("${com.zhushan.user.queue.name}") // 读取队列
	private String MESSAGESQUEUE;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired // 用于发送队列消息
	private RegisterMailboxProducer registerMailboxProducer;
	
	private  final Logger log = LoggerFactory.getLogger(LogAspectServiceApi.class);
	
	@RequestMapping(value ="findById/{id}")
	public ResponseBase findById(@PathVariable Integer id){
		UserEntry user = memberService.findById(id);
		if(user==null){
			return setResultError("未查到用户信息");
		}
		return setResultSuccess(user);
	}
	
	@RequestMapping("register") // 注册功能
	public ResponseBase register(UserEntry user){
		// 参数验证
		if (StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())) {
			return setResultError("账号或密码不能为空.");
		}
		if(memberService.findByName(user.getUsername())!=null){
			return setResultError("用户名已经被注册");
		}
		// 密码加密
		user.setPassword(MD5Util.MD5(user.getPassword()));
		user.setCreated(new Date());
		user.setUpdated(new Date());
		
		Integer result = memberService.register(user);
		if (result <= 0) {
			return setResultError("注册用户信息失败.");
		}
		// 采用异步方式发送消息
		String email = user.getEmail();
		String json = emailJson(email);
		
		log.info("####会员服务推送消息到消息服务平台####json:{}", json);
		sendMsg(json); // 把邮箱号码推送到消息平台
		return setResultSuccess("用户注册成功.");
	}
	
	@RequestMapping("login") // 登录功能
	public ResponseBase login(UserEntry user){
		if (StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())) {
			return setResultError("账号或密码不能为空.");
		}
		user.setPassword(MD5Util.MD5(user.getPassword()));
		UserEntry userEntry = memberService.login(user);
		if(userEntry==null){
			return setResultError("账号或密码不正确");
		}
		String redisToken = (String) baseRedisService.getString(userEntry.getId()+"");
		if (StringUtils.isEmpty(redisToken)) {
			String memberToken = TokenUtils.getMemberToken();
			baseRedisService.setString(memberToken, userEntry.getId()+"",Constants.TOKEN_MEMBER_TIME);
			baseRedisService.setString(userEntry.getId()+"",memberToken,Constants.TOKEN_MEMBER_TIME);
			log.info("####用户信息token已经存放在redis中... key为:{},value", memberToken, userEntry.getId());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("memberToken", memberToken);
			return setResultSuccess(jsonObject);
		}
		return setResultSuccess(redisToken);
	}
	
	@RequestMapping("filter/findUserByid")
	public ResponseBase findUserByid(Integer id,String token){
		UserEntry user = memberService.findById(id);
		return setResultSuccess(user);
	}
	
	@RequestMapping("filter/list")
	public ResponseBase selectList(String token){
		return setResultSuccess(memberService.list());
	}
	
	@RequestMapping("findUserByToken")
	public ResponseBase findUserByToken(String token){
		if(StringUtils.isEmpty(token)){
			return setResultError("token不能为空");
		}
		String userId = (String) baseRedisService.getString(token);
		Integer id = new Integer(userId);
		if(StringUtils.isEmpty(userId)){
			return setResultError("token不存在或者已经失效");
		}
		UserEntry user = memberService.findById(id);
		return setResultSuccess(user);
	}
	
	private void sendMsg(String json) {
		// 获取队列
		ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGESQUEUE);
		// 发送邮件消息
		registerMailboxProducer.sendMsg(activeMQQueue, json);
	}
	
	private String emailJson(String email) {
		JSONObject rootJson = new JSONObject();
		JSONObject header = new JSONObject();
		header.put("interfaceType", Constants.MSG_EMAIL);
		JSONObject content = new JSONObject();
		content.put("email", email);
		rootJson.put("header", header);
		rootJson.put("content", content);
		return rootJson.toJSONString();
	}

}

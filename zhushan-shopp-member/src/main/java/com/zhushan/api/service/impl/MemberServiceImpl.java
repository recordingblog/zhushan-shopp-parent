package com.zhushan.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhushan.api.test.MemberService;
import com.zhushan.dao.MemberDao;
import com.zhushan.domain.UserEntry;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao memberDao;
	
	@Override
	public UserEntry findById(Integer id) {
		return memberDao.findById(id);
	}

	@Override
	public Integer register(UserEntry userEntry) {
		return memberDao.register(userEntry);
	}

	@Override
	public UserEntry findByName(String username) {
		return memberDao.findByName(username);
	}

	@Override
	public UserEntry login(UserEntry user) {
		return memberDao.login(user);
	}

	@Override
	public void refreshToken(UserEntry userEntry) {
		memberDao.refreshToken(userEntry);
	}

	@Override
	public List<UserEntry> list() {
		return memberDao.list();
	}

}

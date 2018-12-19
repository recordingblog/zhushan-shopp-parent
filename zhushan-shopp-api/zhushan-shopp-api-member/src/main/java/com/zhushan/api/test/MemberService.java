package com.zhushan.api.test;


import java.util.List;

import com.zhushan.domain.UserEntry;

public interface MemberService {
	UserEntry findById(Integer id);
	Integer register(UserEntry userEntry);
	UserEntry findByName(String username);
	UserEntry login(UserEntry user);
	void refreshToken(UserEntry userEntry);
	List<UserEntry> list();
}

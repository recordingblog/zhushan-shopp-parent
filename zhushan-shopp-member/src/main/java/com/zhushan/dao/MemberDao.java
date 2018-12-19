package com.zhushan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.zhushan.domain.UserEntry;

@Mapper
public interface MemberDao {	
	
	@Select("select * from mb_user where id = #{id}")
	UserEntry findById(Integer id);
	
	@Insert("insert into mb_user (username,password,phone,email,created,updated) values (#{username},#{password},#{phone},#{email},#{created},#{updated})")
	Integer register(UserEntry userEntry);
	
	@Select("select * from mb_user where username = #{username}")
	UserEntry findByName(String username);
	
	@Select("select * from mb_user where username = #{username} and password = #{password}")
	UserEntry login(UserEntry user);
	
	@Update("update mb_user set token = #{token}")
	void refreshToken(UserEntry userEntry);
	
	@Select("select * from mb_user")
	List<UserEntry> list();
}

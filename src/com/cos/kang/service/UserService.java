package com.cos.kang.service;

import java.util.List;

import com.cos.kang.domain.user.User;
import com.cos.kang.domain.user.UserDao;
import com.cos.kang.domain.user.dto.JoinReqDto;
import com.cos.kang.domain.user.dto.LoginReqDto;
import com.cos.kang.domain.user.dto.UpdateReqDto;

public class UserService {
	
	private UserDao userDao;
	
	public UserService() {
		userDao = new UserDao();		
	}
	
	public int 회원삭제(int id) {
						
		return userDao.deleteById(id);
	}
	
	public int 회원가입(JoinReqDto dto) {
		
		int result = userDao.save(dto);
		return result;
	}
	
	public User 로그인(LoginReqDto dto) {
		
		return userDao.findByUsernameAndPassword(dto);
	}
	
	public int 회원수정(UpdateReqDto dto) {
		
		return userDao.update(dto);
	}

	public int 유저네임중복체크(String username) {
		int result = userDao.findByUsername(username);
		return result;
	}
	
	public int 유저개수() {		
		return userDao.count();
	}
	
	public List<User> 유저목록보기(int page){
		
		return userDao.findAll(page);
	}
	
	
}

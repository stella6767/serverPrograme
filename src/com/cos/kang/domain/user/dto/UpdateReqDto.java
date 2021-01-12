package com.cos.kang.domain.user.dto;

import lombok.Data;

@Data
public class UpdateReqDto {
	private int id;
	private String username;
	private String password;
	private String email;
}

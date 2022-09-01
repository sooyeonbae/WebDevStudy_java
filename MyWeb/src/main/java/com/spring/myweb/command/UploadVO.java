package com.spring.myweb.command;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadVO {

	private String name;	//파일명
	private MultipartFile file;
	
}

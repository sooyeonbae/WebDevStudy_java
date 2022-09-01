package com.spring.myweb.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageVO {
	
	//사용자가 선택한 페이지 정보 담을 변수
	private int pageNum;
	private int cpp;
	private int offset; //이거 왜 ㅜㅜ
	
	
	
	//검색에 필요한 데이터를 변수로 선언
	private String keyword;
	private String condition;

	public PageVO() {
		this.pageNum = 1;
		this.cpp = 20;
		this.offset = (pageNum-1)*cpp;
	}
}

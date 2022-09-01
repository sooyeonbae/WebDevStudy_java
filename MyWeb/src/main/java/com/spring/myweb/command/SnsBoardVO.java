package com.spring.myweb.command;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
  CREATE TABLE `snsboard` (
  `bno` int NOT NULL AUTO_INCREMENT,
  `writer` varchar(50) NOT NULL,
  `uploadpath` varchar(100) NOT NULL,
  `fileloca` varchar(100) NOT NULL,
  `filename` varchar(50) NOT NULL,
  `filerealname` varchar(50) NOT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `regdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`bno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
*/

@Getter
@Setter
@ToString
@NoArgsConstructor	//아무것도없는생성자
@AllArgsConstructor	//모든걸받는생성자
public class SnsBoardVO {
	
	private int bno;
	private String writer;
	private String uploadpath;	//공통경로
	private String fileloca;	//상세경로(날짜별로만든폴더)
	private String filename;	//랜덤으로만든파일명
	private String filerealname;	//원본파일명
	private String content;
	private Timestamp regdate;
	
	//좋아요 개수가 몇 개인지 알려주는 변수
	private int likeCnt;
	
}

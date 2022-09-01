package com.spring.myweb.command;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
CREATE TABLE `freeboard` (
  `bno` int NOT NULL,
  `title` varchar(300) NOT NULL,
  `writer` varchar(50) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `regdate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedate` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`bno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
*/

@Getter
@Setter
@ToString
public class FreeBoardVO {
	
	private int bno;
	private String title;
	private String writer;
	private String content;
	private Timestamp regDate;
	private Timestamp updateDate;
	private boolean newMark;

}

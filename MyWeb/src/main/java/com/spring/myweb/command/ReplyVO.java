package com.spring.myweb.command;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
  CREATE TABLE `freereply` (
  `rno` int NOT NULL AUTO_INCREMENT,
  `bno` int DEFAULT NULL,
  `reply` varchar(1000) DEFAULT NULL,
  `reply_id` varchar(50) DEFAULT NULL,
  `reply_pw` varchar(50) DEFAULT NULL,
  `reply_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`rno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
 */

@Getter
@Setter
@ToString
public class ReplyVO {

	private int rno;
	private int bno;
	
	private String reply;
	private String replyId;
	private String replyPw;
	private Timestamp replyDate;
	private Timestamp updateDate;
	
}

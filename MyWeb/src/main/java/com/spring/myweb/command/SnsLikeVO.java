package com.spring.myweb.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 CREATE TABLE `spring`.`snslike` (
  `bno` INT NOT NULL,
  `userId` VARCHAR(50) NOT NULL,
  `lno` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`lno`));
*/
@Getter
@Setter
@ToString
public class SnsLikeVO {
	
	private int bno;
	private String userId;
	private int lno;
	
	//컨트롤러는 따로 없이 SnsBoardController에서 쓸거다.

}

package com.spring.myweb.command;

import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 CREATE TABLE `users` (
  `userId` varchar(50) NOT NULL,
  `userPw` varchar(50) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `userPhone1` varchar(50) DEFAULT NULL,
  `userPhone2` varchar(50) DEFAULT NULL,
  `userEmail1` varchar(50) DEFAULT NULL,
  `userEmail2` varchar(50) DEFAULT NULL,
  `addrBasic` varchar(300) DEFAULT NULL,
  `addrDetail` varchar(300) DEFAULT NULL,
  `addrZipNum` varchar(50) DEFAULT NULL,
  `regDate` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
 */


@Getter
@Setter
@ToString
public class UserVO {

	private String userId;
	private String userPw;
	private String userName;
	private String userPhone1;
	private String userPhone2;
	private String userEmail1;
	private String userEmail2;
	private String addrBasic;
	private String addrDetail;
	private String addrZipNum;
	private Timestamp regDate;
	
	//join해서 freeboard거도 가져와야해서 추가
	/*
	 한 명의 회원은 글을 여러 개 작성할 수 있습니다.
	 마이페이지에서는 특정회원이 작성한 글 목록을 나타내야 합니다.
	 회원 정보와 글 목록은 서로 다른 테이블로 이루어져있고, 마이페이지에서는 해당 정보를
	 한 번의 DB 연동으로 가져올 수 있도록 하기 위해서
	 JOIN 문법으로 테이블을 합친 뒤 원하는 컬럼을 선택해서 가져올 예정입니다.
	 */
	
		// 1(회원) : n(게시물) 이라서  List<FreeboardVO>로 선언해야한다.
	//1이 UserVO이기 때문에 UserVO 안에 N의 값을 뜻하는 FreeBoardVO 객체의 모음을 저장할 수 있는 List를 선언.
	//1:N관계의 테이블을 list형태로 선언.
	private List<FreeBoardVO> userBoardList;

	
	
}

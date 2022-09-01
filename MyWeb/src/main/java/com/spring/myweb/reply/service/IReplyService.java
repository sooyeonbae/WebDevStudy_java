package com.spring.myweb.reply.service;

import java.util.List;
import java.util.Map;

import com.spring.myweb.command.ReplyVO;
import com.spring.myweb.util.PageVO;

public interface IReplyService {
	
	//댓글 등록
	void replyRegist(ReplyVO vo);
	
	//댓글 목록 요청
	List<ReplyVO> getList(PageVO vo, int bno); //매개변수 바꿈
	
	//댓글 개수 (댓글 페이징방법 : 더보기)
	int getTotal(int bno);
	
	//비밀번호 확인
	int pwCheck(ReplyVO vo);
	
	//댓글 수정
	void update(ReplyVO vo);
	
	//댓글 삭제
	void delete(int rno);

}

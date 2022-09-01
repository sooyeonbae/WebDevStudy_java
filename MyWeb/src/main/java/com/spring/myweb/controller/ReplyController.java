package com.spring.myweb.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.myweb.command.ReplyVO;
import com.spring.myweb.reply.service.IReplyService;
import com.spring.myweb.util.PageVO;

@RestController	//댓글 비동기방식으로 할거라
@RequestMapping("/reply")
public class ReplyController {
	
	@Autowired
	private IReplyService service;
	
	//댓글 등록
	@PostMapping("/replyRegist")
	public String replyRegist(@RequestBody ReplyVO vo) {	//requestbody:json을 자바객체로변환
		System.out.println("댓글등록요청들어옴");		
		service.replyRegist(vo);
		return "regSuccess";
	}

	//페이징 있는 댓글 목록
	@GetMapping("/getList/{bno}/{pageNum}")
	//리턴할게 List<ReplyVO>랑 PageVO 두개라 Map에 담아 반환할거다. 타입이 다양하니까 Object로
	public Map<String, Object> getList(@PathVariable int bno,
								@PathVariable int pageNum) {
		//1. getList메서드가 (글번호, 페이지번호)를 매개값으로 받습니다.
		//2. Mapper 인터페이스에 각각 다른 값을 전달하기 위해 Map을 쓰던지, @Param 아노테이션을 쓰던지. (우리는 맵으로쓸거다)
		//3. ReplyMapper.xml에 sql문을 페이징쿼리로 작성합니다.
		//4. 레스트방식은 화면에 필요한 값을 여러 개 보낼 때, 리턴에 Map이나 VO 형식으로 필요한데이터를 한번에 담아서 처리해야한다.(모델 못씀)
		//댓글목록리스트와, 전체댓글개수를 함께 전달할거다.
		
		PageVO vo = new PageVO();
		vo.setPageNum(pageNum);	//화면에서전달된 pageNum으로 셋팅
		vo.setCpp(5);			//한화면에 댓글 5개씩 배치하겠다.
		
		List<ReplyVO> list = service.getList(vo, bno);
		int total = service.getTotal(bno); //댓글 총개수
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("total", total);
		return map;
	}
	
	
	@PostMapping("/update")
	public String replyUpdate (@RequestBody ReplyVO vo) {
		System.out.println("댓글 수정 요청 들어왔다 . : POST");
		
		int result = service.pwCheck(vo);
		if (result == 1) {	//비밀번호 맞음
			service.update(vo);
			return "pwSuccess";
		} else {
			return "pwFail";
		}
	}
	
	
	@PostMapping("/delete")
	public String replyDelete (@RequestBody ReplyVO vo) {
		
		int result = service.pwCheck(vo);
		if (result == 1) {
			service.delete(vo.getRno());
			return "delSuccess";
		} else {
			return "delFail";
		}
		
	}
	
	
	
	
}

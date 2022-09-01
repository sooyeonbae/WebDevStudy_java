package com.spring.myweb.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.myweb.command.FreeBoardVO;
import com.spring.myweb.freeboard.service.IFreeBoardService;
import com.spring.myweb.util.PageCreator;
import com.spring.myweb.util.PageVO;

@Controller
@RequestMapping("/freeboard")
public class FreeBoardController {

	@Autowired
	private IFreeBoardService service;
	
	//목록화면 불러오기 + 페이징
	@GetMapping("/freeList")
	public void freeList(PageVO vo, Model model) {
		System.out.println("/freeboard/freeList : GET");
		System.out.println(vo);
		
		PageCreator pc = new PageCreator();
		pc.setPaging(vo);
		pc.setArticleTotalCount(service.getTotal(vo));
		
		System.out.println(pc);
		
		//모델에 담기
		model.addAttribute("boardList", service.getList(vo));
		model.addAttribute("pc", pc);
		
		// return "freeBoard/freeList"; //같아서 그냥 void 처리하면 된다.
		
	}
	
	//글쓰기 화면 처리
	@GetMapping("/freeRegist")
	public void freeRegist() {
		
	}
	
	//글등록처리
	@PostMapping("/registForm")
	public String registForm(FreeBoardVO vo, RedirectAttributes ra) {
		service.regist(vo);
		ra.addFlashAttribute("msg", "정상 등록처리 되었습니다.");
		return "redirect:/freeboard/freeList";
	}
	
	//글상세보기처리
	@GetMapping("/freeDetail/{bno}")
	public String getContent(@PathVariable int bno,
							@ModelAttribute("p") PageVO vo,
							Model model) {
		model.addAttribute("article", service.getContent(bno));
		return "freeboard/freeDetail";
		
	}
	
	//글수정 화면으로
	@GetMapping("/freeModify")
	public void modify(int bno,
						Model model){
		model.addAttribute("article", service.getContent(bno));
	}
	
	//글수정처리
	@PostMapping("/freeUpdate")
	public String freeUpdate(FreeBoardVO vo,
							RedirectAttributes ra) {
		service.update(vo);
		ra.addFlashAttribute("msg", "updateSuccess");
		return "redirect:/freeboard/freeDetail/" + vo.getBno();
	}
	
	//글삭제처리
	@PostMapping("/freeDelete")
	public String freeDelete(int bno, RedirectAttributes ra) {
		service.delete(bno);
		ra.addFlashAttribute("msg", "게시글이 정상적으로 삭제되었습니다.");
		return "redirect:/freeboard/freeList";
		
	}

	
	
}

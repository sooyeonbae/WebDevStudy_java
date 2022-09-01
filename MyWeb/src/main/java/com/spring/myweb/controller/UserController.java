package com.spring.myweb.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.myweb.command.UserVO;
import com.spring.myweb.user.service.IUserService;
import com.spring.myweb.util.MailSendService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//도로명 주소 승인 키 : devU01TX0FVVEgyMDIyMDYyMjE2MTc1NTExMjcxNzM=

	@Autowired
	private IUserService service;
	
	@Autowired
	private MailSendService mailService;
	
	//회원가입페이지 띄우기
	@GetMapping("/userJoin")
	public void userJoin() {
		
	}
	
	//아이디 중복체크(비동기방식)
	@ResponseBody //Rest Controller가 아닌 경우에는 responsebody 아노테이션을붙여야 값이 전송된다.
	@PostMapping("/idCheck")
	public String idCheck(@RequestBody String userId) {
		System.out.println("/user/idCheck : POST");
		
		int result = service.idCheck(userId);
		System.out.println("id : " + userId);
		System.out.println("result : " + result);
		
		if (result == 0) {
			return "available";
		} else {
			return "duplicated";
		}
	}
	
	
	//이메일 인증
	@GetMapping("/mailCheck")
	@ResponseBody
	public String mailCheck(String email) {	//변수명이 같아서 requestParam생략
		System.out.println("이메일 인증 요청 들어옴 : GET");
		System.out.println("인증 이메일 : " + email);
		return mailService.joinEmail(email);
	}
	
	//회원가입처리
	@PostMapping("/join")
	public String join(UserVO vo, RedirectAttributes ra) {
		System.out.println(vo);
		service.join(vo);
		ra.addFlashAttribute("msg", "joinSuccess");
		return  "redirect:/user/userLogin";
		//내 실수 : 값이 전달안된이유 : 
		//jsp에서 input에 name이 다 있어야하는데 없었다.파라미터변수명이랑같게해서 다써줘야한다.
		
	}
	
	//로그인페이지로 이동 요청
	@GetMapping("/userLogin")
	public void userLogin() {
		
	}
	
	//로그인요청
	@PostMapping("/login")
	public String login(String userId,
						String userPw,
						Model model) {
		
		model.addAttribute("user", service.login(userId, userPw));	//null인지, vo 제대로 오는지는 interceptor에서 판단
		
		return "/user/userLogin";
		
		//mapper의 login 메서드 리턴 타입이 UserVO죠?
	      //그거 모델에 담으세요.
	      //리턴은 /user/userLogin으로 세팅을 하세요.
	      //util 패키지 안에 interceptor 패키지를 생성해서
	      //UserLoginSuccessHandler 클래스를 하나 생성해 주세요.
	      //UserLoginSuccessHandler는 로그인 처리 이후에 실행되는 핸들러를 
	      //오버라이딩 해서 -> 모델을 꺼내오세요.
	      //모덜 내의 데이터가 null인지 아닌지를 확인하셔서 
	      //null이라면 로그인 실패입니다. msg라는 이름으로 loginFail을 담아서
	      //userLogin.jsp 파일로 응답하도록 viewName을 세팅하시고,
	      //null이 아니라면 세션 만드셔서 홈 화면으로 이동시켜 주세요.
		
	}
	
	//마이페이지로 이동
	@GetMapping("/userMypage")
	public void userMypage(HttpSession session,
							Model model) {
		
		
		//session에서 id만 뽑아내기 (sql 돌리려고)
		String id = ((UserVO)session.getAttribute("login")).getUserId();	//괄호 주의!! 먼저씌워놔야한다.
		
		UserVO vo = service.getInfo(id); //마이바티스에서 join 사용하기(테이블 users, freeboard)
										//합쳐서가져온거를 UserVO에 포장할수없음... -> userVO 좀 고쳐주기.
		System.out.println("JOIN의 결과 : " + vo);
		model.addAttribute("userInfo", vo);
		
	}
	
	//수정
	@PostMapping("/update")
	public String userUpdate(UserVO vo,
						RedirectAttributes ra) {
		System.out.println("param : " + vo);
		service.updateUser(vo);
		ra.addFlashAttribute("msg", "updateSuccess");
		return  "redirect:/user/userMypage";
	}
	
}

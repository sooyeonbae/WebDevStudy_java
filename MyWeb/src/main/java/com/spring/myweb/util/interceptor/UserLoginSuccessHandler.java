package com.spring.myweb.util.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.myweb.command.UserVO;



public class UserLoginSuccessHandler implements HandlerInterceptor{
	
	//컨트롤러가 처리를 '마친 후' 로직을 가로챌거다. -> postHandle
	//컨트롤러 동작 이후에 실행되는 핸들러(postHandle) 오버라이딩.
	//	/login 요청으로 들어올 때 실행되도록 xml 파일에 빈으로 등록 후 매핑. 
	//- (spring / interceptor-config (spring bean definition file))
	//		-> namespaces에서 mvc 추가
	
	// (sevlet-context.xml에 만들어도 되지만
	//여기서는 인터셉터를 여러개 만들수도 있으니까 따로 파일을 만들어서 선언했다.)
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("로그인 인터셉터 동작");
		
		//modelandview에서 model 꺼내기
		//방법1
		//modelAndView.getModel().get("key");
	
		//방법2
		ModelMap mv = modelAndView.getModelMap();
		UserVO vo = (UserVO) mv.get("user");
		
		System.out.println("interceptor에서 얻어낸 VO : " + vo);
		
		
		if( vo != null) { //vo가 null이 아니라면 : controller에서 로그인을 성공한 사람
			System.out.println("로그인 성공 로직 동작시키기");
			//로그인 성공한 회원에게 세션 데이터를 생성해서 로그인 유지를 하게 해줌.
			HttpSession session = request.getSession();
			session.setAttribute("login", vo);
			response.sendRedirect(request.getContextPath()); //메인페이지로 보내주기."/myweb" 대신
		} else {		//vo가 null이라면 : 로그인실패한사람
			modelAndView.addObject("msg","loginFail");
			modelAndView.setViewName("/user/userLogin");
			
		}
		
	}



}

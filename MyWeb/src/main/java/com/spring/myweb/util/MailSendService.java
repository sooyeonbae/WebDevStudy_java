package com.spring.myweb.util;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailSendService {
	
	@Autowired
	private JavaMailSender mailSender;
	private int authNum;	//인증번호. 다른메서드에서도 사용하려고 따로선언.
	
	//난수 발생 메서드 (여러분들 맘대로 설정하세요)
	public void makeRandomNumber() {
		//난수 범위 설정 : 111111 ~ 999999 (랜덤 6자리)
		Random r = new Random();
		int checkNum = r.nextInt(888888) + 111111;
		System.out.println("인증번호 : " + checkNum);
		authNum = checkNum;
	}

	//회원가입시 사용할 이메일 양식
	public String joinEmail(String email) {
		
		makeRandomNumber();
		
		
		String setFrom = "daisybaesy3131@gmail.com";// 보내는사람 : email-config에 설정한 자신의 이메일 주소 입력
		String toMail = email;	//수신받을 이메일
		String title = "회원가입인증 이메일입니다."; //이메일 제목
		String content = "홈페이지를 방문해주셔서 감사합니다."
						+ "<br><br>"
						+ "인증번호는 "+authNum+ "입니다."
						+ "<br>"
						+ "해당 인증번호를 인증번호 확인란에 기입하여 주세요."; //이메일 내용 (html태그 , 이미지로 넣어도된다.)

		mailSend(setFrom, toMail, title, content);
		
		return Integer.toString(authNum);	//return값 : 인증번호 (확인하기위해) (int를 문자열로변경해서 리턴)
	}
	
	
	//이메일 전송 메서드 (비번찾기 등에도 활용할것)
	public void mailSend (String setFrom, String toMail, String title, String content) {
		MimeMessage message = mailSender.createMimeMessage();	//new로 생성하는거 아니고 이런식으로한다.
	//SimpleMailMessage : 문자만 전송 	/ MimeMessage : 이미지도 전송가능
		
		//기타 설정들을 담당할 MimeMessageHelber 객체를 생성.
		//생성자의 매개값으로 (MimeMessage 객체, boolean논리값, 문자인코딩 설정)
		//true를 매개값으로 전달하면 MultiPart형식의 메세지 전달이 가능.
		//					전달하지 않으면 단순 텍스트만 사용.
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true); //true : html형식으로 전송. true없으면 단순 텍스트.
			
			mailSender.send(message);
			
		} catch (MessagingException e) {

			e.printStackTrace();
		}
	}
	
	

}

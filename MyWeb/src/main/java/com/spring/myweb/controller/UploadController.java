package com.spring.myweb.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.spring.myweb.command.MultiUploadVO;
import com.spring.myweb.command.UploadVO;

@Controller
@RequestMapping("/fileupload")
public class UploadController {

	@GetMapping("/upload")
	public void form() {
		
	}
	
	@PostMapping("upload_ok")
	public String upload(@RequestParam("file") MultipartFile file) {	//multipartFile 객체타입으로 받아올거다.
	
		String fileRealName = file.getOriginalFilename();	//파일원본명
		long size = file.getSize();		//파일 크기 (바이트 단위)
		
		System.out.println("파일명 : " + fileRealName);
		System.out.println("파일사이즈 : " + size);
		
		//DB에는 파일경로를 저장하고, 실제 파일은 서버컴퓨터의 로컬경로에 저장하는 방식.
		//경로는 자바로 잡아줄거다. 
		//String uploadFolder = "C:\\Users\\user\\Desktop\\내폴더";	//폴더경로. 정슬래시(/)로 해도됨. 백슬래시 쓸거면 두개씩써야한다.
		String uploadFolder = "/Users/baesy/WebDevStudy/uploadTest";
		String fileExtension = fileRealName.substring(fileRealName.lastIndexOf(".", fileRealName.length())); 
														//파일 확장자 (점부터(점 포함) 끝까지)
														//파일명의 중복을 방지하기 위해(파일명 우리가 바꿔줄거다)
		/*
        파일 업로드 시 파일명이 동일한 파일이 이미 존재할 수도 있고,
        사용자가 업로드하는 파일명이 영어 이외의 언어로 되어있을 수 있습니다.
        타 언어를 지원하지 않는 환경에서는 정상 동작이 되지 않습니다. (리눅스)
        고유한 랜덤 문자를 통해 DB와 서버에 저장할 파일명을 새롭게 만들어 줍니다. -> UUID
        */
		
		UUID uuid = UUID.randomUUID();
		System.out.println("uuid : " +uuid.toString());
		//UUID중간중간 하이픈 없애기
		String[] uuids = uuid.toString().split("-"); //-기준으로 자르기
		System.out.println("생성된 고유 문자열 : " + uuids[0]); //맨앞에 한덩어리만 쓸거다.
		System.out.println("확장자명 : " + fileExtension);
		
		
		//로컬경로에 파일 저장하기 (위에 적어둔 경로가 존재하지않는다면 만들어라)
		File folder = new File(uploadFolder);
		if(!folder.exists()) {
			folder.mkdir();//폴더가 존재하지 않는다면 생성해라.(앞에경로 완성안돼있으면 오류)
							//mkdirs() : 앞에경로가 완성 안돼있으면 걔네까지 만들어준다.
		}
		
		try {
			folder = new File(uploadFolder + "/" + uuids[0] + fileExtension);
			//실제 파일 저장 메서드 (fileWriter 작업을 손쉽게 한방에 처리해줍니다.)
			
			file.transferTo(folder);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return "fileupload/upload_ok";
	}
	
	
	
	// 여러개 업로드
	@PostMapping("/upload_ok2")
	public String upload2(MultipartHttpServletRequest files) { //여러개 받을거니까 MultipartHttpServletRequest
		//파라미터변수명 똑같이맞춰줘서 @requestParam생략
		//서버에서 저장할 파일 경로
		String uploadFolder = "/Users/baesy/WebDevStudy/uploadTest";

		files.getFiles("files");	//List로 리턴해준다.
		List<MultipartFile> list = files.getFiles("files");

//		//반복문으로 돌려서 하나씩 넣어주기 (일반for문)
//		for (int i=0; i<list.size(); i++) {
//			//유효아이디 생략하겟다..
//			try {
//				String fileRealName = list.get(i).getOriginalFilename();
//				long size = list.get(i).getSize();
//				System.out.println("파일명 : " + fileRealName);
//				System.out.println("사이즈 : " + size);
//
//				File saveFile = new File(uploadFolder + "/" + fileRealName);
//				list.get(i).transferTo(saveFile);
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		
		//향상for문 버전
		for (MultipartFile m : list) {

			try {
				String fileRealName = m.getOriginalFilename();
				long size = m.getSize();
				System.out.println("파일명 : " + fileRealName);
				System.out.println("사이즈 : " + size);

				File saveFile = new File(uploadFolder + "/" + fileRealName);
				m.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "fileupload/upload_ok";
	}
	
	
	
	//여러개받기 input태그여러개해서
	//태그 다 못채우면 에러난다.프론트단에서 다 채우라고 경고하던가 / 여기서 리스트 비어잇으면 반복문 멈추던가
	@PostMapping("/upload_ok3")
	public String upload3(@RequestParam("file") List<MultipartFile> list) {
		
		String uploadFolder = "/Users/baesy/WebDevStudy/uploadTest";
		
		for (MultipartFile m : list) {

			try {
				String fileRealName = m.getOriginalFilename();
				long size = m.getSize();
				System.out.println("파일명 : " + fileRealName);
				System.out.println("사이즈 : " + size);

				File saveFile = new File(uploadFolder + "/" + fileRealName);
				m.transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "fileupload/upload_ok";
	}
	
	
	//파일명, 파일 VO로해서 받기 (여러개면 i)List로받거나 , ii)여러개 받는 VO(MultiUploadVO)로 받기)
	@PostMapping("/upload_ok4")
	public String upload4 (MultiUploadVO vo) {	
						//MultiUploadVO로 받을때에는 jsp에서 파라미터변수를 list[i].name 이런식으로 바꿔줘야한다.
		
		String uploadFolder = "/Users/baesy/WebDevStudy/uploadTest";
		
		List<UploadVO> list = vo.getList();
		for (UploadVO m : list) {

			try {
				String fileRealName = m.getFile().getOriginalFilename();
				String fileExtension = fileRealName.substring(fileRealName.lastIndexOf(".", fileRealName.length()));

				File saveFile = new File(uploadFolder + "/" + m.getName() + fileExtension);
				m.getFile().transferTo(saveFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "fileupload/upload_ok";
	}
	

}

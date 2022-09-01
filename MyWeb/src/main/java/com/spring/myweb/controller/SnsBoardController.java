package com.spring.myweb.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.myweb.command.SnsBoardVO;
import com.spring.myweb.command.SnsLikeVO;
import com.spring.myweb.command.UserVO;
import com.spring.myweb.snsboard.service.ISnsBoardService;
import com.spring.myweb.util.PageVO;

@Controller
@RequestMapping("/snsBoard")
public class SnsBoardController {
	
	//출력 sysout말고 logger로 찍기. 변수먼저 선언
	public static final Logger logger = LoggerFactory.getLogger(SnsBoardController.class);
	
	@Autowired
	private ISnsBoardService service;
	
	@GetMapping("/snsList")
	public void snsList() {
		
	}
	
	@PostMapping("/upload")	//파일전송은 무조건 POST방식이다.
	@ResponseBody
	public String upload(MultipartFile file,
						String content,
						HttpSession session) {	//session : 아이디받는용
		
		String writer = ((UserVO)session.getAttribute("login")).getUserId();
		String fileRealName = file.getOriginalFilename();	//원본파일명
		
		//날짜별로 폴더 생성해서 파일 관리
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String fileloca = sdf.format(date);	//날짜정보가 sdf형태에 맞춰진다. (문자형태로)
		
		//저장할 폴더 경로
		String uploadpath = "/Users/baesy/WebDevStudy/upload/" + fileloca;
		
		File folder = new File(uploadpath);
		if (!folder.exists()) {
			folder.mkdirs();		//경로 폴더 없으면 생성해라
		}
		
		//파일명을 고유한 랜덤문자로 생성
		UUID uuid = UUID.randomUUID();
		String uuids = uuid.toString().replaceAll("-", "");		// '-' 를 없애라 ("-"를 ""로 대체해라)
		
		//확장자를 추출합니다.
		String fileExtension = fileRealName.substring(fileRealName.indexOf("."), fileRealName.length());
		
		System.out.println("저장할폴더경로 : " + uploadpath);
		System.out.println("실제 파일명 : " + fileRealName);
		System.out.println("폴더명 : " + fileloca);
		System.out.println("확장자 : " + fileExtension);
		System.out.println("고유랜덤문자 : " + uuids);
		
		String fileName = uuids + fileExtension;
		System.out.println("변경해서 저장할 파일명 : " + fileName);
		
		//업로드한 파일을 서버 컴퓨터 내의 지정한 경로에 실제로 저장
		File saveFile = new File(uploadpath + "/"+ fileName);
		try {
			file.transferTo(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//DB에 인서트
		SnsBoardVO snsVO = new SnsBoardVO(0, writer, uploadpath, fileloca, fileName, fileRealName, content, null, 0);
		service.insert(snsVO);
		
		return "success";
	}
	
	
	//비동기 통신 후 가져올 글 목록
	@GetMapping("/getList")
	@ResponseBody
	public List<SnsBoardVO> getList (PageVO paging) {
		
		logger.info("/snsBoard/getList : GET");
		
		paging.setCpp(3);		//몇개씩 불러올지.
		//return service.getList(paging);	//좋아요 리스트 가져오기 전
		
		//좋아요 개수 가져오기 (서비스에 써도 된다)
		List<SnsBoardVO> list = service.getList(paging);
		for (SnsBoardVO svo : list) {
			svo.setLikeCnt(service.likeCnt(svo.getBno()));
		}
		return list;
	
	}
	
	//게시글의 이미지 파일 전송 요청
	//jsp의 img태그에 의해 들어온 요청
	//snsList.jsp 페이지가 로딩되면서 글 목록을 가져오고있고, JS를 이용해서 화면을 꾸밀 때
	//img태그의 src에 작성된 요청url을 통해 자동으로 요청이 들어옵니다.
	//요청을 받아서 경로에 지정된 파일을 보낼 예정입니다.
	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(String fileLoca,		//그냥byte배열(byte[]) 로 리턴해도되지만
							String fileName) {					//권장은 responseEntity<byte[]> (여러응답에대한정보까지)
		System.out.println("fileName : " + fileName);
		System.out.println("fileLoca : " + fileLoca);
		
		File file = new File("/Users/baesy/WebDevStudy/upload/"+fileLoca+"/"+fileName);
		System.out.println(file);
	
		ResponseEntity<byte[]> result = null;
		
		//값넣어주기
		//응답헤더파일에 여러가지 정보를 담아서 전송할 수 있다.
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Type", Files.probeContentType(file.toPath())); 
										//파라미터로 전달받은 File의 타입의 타입을 문자열로 변환해주는 메서드 : probeContentType()
										//사용자에게 보여주고자 하는 데이터가 어떤 파일인지를 검사해서 응답상태 코드를 다르게 리턴할 수도 있다.
			headers.add("hello", "hi-hi"); //이런식으로 화면단에서 확인할수있다구한다.
			
			//ResponseEntity<>(응답객체에 담을 내용, 헤더에 담을 내용, 상태메시지)
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);	
										//FileCopyUtils : 파일 및 스트림 데이터 복사를 위한 간단한 유틸리티 메서드의 집합체.
										//file 객체 안에 있는 내용을 복사해서 byte 배열로 변환해서 바디에 담아 화면에 전달.
			
										//FileCopyUtils.copyToByteArray() : byte로 변환해서 전달해준다.
										//HttpStatus.OK : 잘 전달됐을떄
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return result;
		//result를 responseEntity로 안하고 그냥 FileCotpUtils.copyToByteArray(file)로 바로 보내도된다.
	}
	
	
	
	//상세보기 처리
	@GetMapping("/getDetail/{bno}")
	@ResponseBody
	public SnsBoardVO getDetail(@PathVariable int bno) {
		
		SnsBoardVO vo = service.getDetail(bno);
		
		return vo;
	}
	
	//삭제처리
	@PostMapping("/delete")
	@ResponseBody
	public String delete(@RequestBody int bno,
						HttpSession session) {
		SnsBoardVO vo = service.getDetail(bno);
		
		UserVO user = (UserVO)session.getAttribute("login");
		
		if (user == null || !user.getUserId().equals(vo.getWriter())) {
			return "noAuth";
		} 
		
		service.delete(bno);
		
		//글이 삭제되었다면 더이상 이미지도 존재할 필요가 없기때문에 로컬경로의 이미지도 삭제.
		File file = new File(vo.getUploadpath() + "/" + vo.getFilename());
		System.out.println("파일 삭제 완료");
		
		return file.delete() ? "Success" : "Fail";	//.delete() : 파일삭제메서드
		
	}
	
	/* (DB)

	//게시글 지워지면 그 글에 좋아요한 DB도 지우기
	 ALTER TABLE snslike ADD FOREIGN KEY (bno)
	REFERENCES snsboard(bno)
	ON DELETE CASCADE;
	
	//회원정보 삭제되면 그 회원이 좋아요한 DB지우기
	ALTER TABLE snslike ADD FOREIGN KEY (userId)
	REFERENCES users(userid)
	ON DELETE CASCADE;
	
	ON DELETE CASCADE : 외래키(FK)를 참조할 때, 참조하는 데이터가 삭제되는 경우
	참조하고 있는 데이터도 함께 삭제를 진행하겠다.

*/
	
	
	
	
	
	
	//다운로드
	@GetMapping("/download")
	@ResponseBody
	public ResponseEntity<byte[]> download(String fileLoca,
											String fileName) {
		System.out.println("fileName:"+fileName);
		System.out.println("fileLoca:"+fileLoca);
		
		File file = new File("/Users/baesy/WebDevStudy/upload/"+fileLoca+"/"+fileName);
		
		ResponseEntity<byte[]> result = null;
		
		/*브라우저별 설정
		응답하는 본문을 브라우저가 어떻게 표시해야 할 지 알려주는 헤더 정보를 추가합니다.
        inline인 경우 웹 페이지 화면에 표시되고, attachment인 경우 다운로드를 제공합니다.
        
        request객체의 getHeader("User-Agent") -> 단어를 뽑아서 확인
        ie: Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko  
        chrome: Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.101 Safari/537.36
        
        파일명한글처리(Chrome browser) 크롬
        header.add("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") );
        파일명한글처리(Edge) 엣지 
        header.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        파일명한글처리(Trident) IE
        Header.add("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " "));
		*/
		
		HttpHeaders header = new HttpHeaders();
		//응답헤더파일에 Content-Disposition을 attachment로 준다면
		//브라우저 내에서 다운로드 처리합니다.
		//filename=파일명.확장자	로 전송합니다.
		header.add("Content-Disposition", "attachment; filename=" + fileName);  // 다운로드
		
		try {
			result = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return result;
	}
	
	
	//좋아요 버튼 클릭처리
	@PostMapping("/like")
	@ResponseBody
	public String likeConfirm(@RequestBody SnsLikeVO vo) {
		System.out.println(vo.getBno());
		System.out.println(vo.getUserId());

		//좋아요를 누르는건지, 좋아요 취소를 하는건지 구분하는 법 : DB에서 조회
		int result = service.searchLike(vo);
		if (result == 0 ) { //좋아요 누르지 않은 사람. 버튼눌렀으니까 좋아요 작동
			service.createLike(vo);
			return "like";
		} else {	//이미 좋아요 누른사람. 버튼눌렀으니까 좋아요취소 작동
			service.deleteLike(vo);
			return "delete";
		}

	}

	
	//회원이 글목록 진입시 좋아요 게시물 수 체크
	@PostMapping("/listLike")
	@ResponseBody
	public List<Integer> listLike(@RequestBody String userId) {
		System.out.println("listLike ID : " + userId);
		return service.listLike(userId);
		
	}
	
	
	
	
	
	
}

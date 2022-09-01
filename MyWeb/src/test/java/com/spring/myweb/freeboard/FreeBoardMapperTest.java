package com.spring.myweb.freeboard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.myweb.command.FreeBoardVO;
import com.spring.myweb.freeboard.mapper.IFreeBoardMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class FreeBoardMapperTest {

	@Autowired
	private IFreeBoardMapper mapper;
	
	@Test
	public void registTest() {
		for(int i=1; i<=30; i++) {
			FreeBoardVO vo = new FreeBoardVO();
			vo.setTitle("마이페이지 테스트" + i);
			vo.setWriter("kim1234");
			vo.setContent("테스트 "+i + "글쓰기내용");
			mapper.regist(vo);
		}
	}
	
	
}

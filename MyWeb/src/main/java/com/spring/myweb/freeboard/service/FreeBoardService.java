package com.spring.myweb.freeboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.myweb.command.FreeBoardVO;
import com.spring.myweb.freeboard.mapper.IFreeBoardMapper;
import com.spring.myweb.util.PageVO;

@Service
public class FreeBoardService implements IFreeBoardService {

	@Autowired
	private IFreeBoardMapper mapper;
	
	
	
	@Override
	public void regist(FreeBoardVO vo) {
		mapper.regist(vo);

	}

	
	
	
	@Override
	public List<FreeBoardVO> getList(PageVO vo) {
		
		List<FreeBoardVO> list = mapper.getList(vo);
		
		//new 마크 처리
		for (FreeBoardVO article : list) {
			long now = System.currentTimeMillis();
			long regTime = article.getRegDate().getTime();	//두개 다 밀리초로해서
			
			if (now - regTime < 60 * 60 * 24 * 1000) {	//기준 하루
				article.setNewMark(true);
			}
		}
		return list;
	}

	@Override
	public int getTotal(PageVO vo) {
		return mapper.getTotal(vo);
	}

	
	
	
	
	@Override
	public FreeBoardVO getContent(int bno) {
		return mapper.getContent(bno);
	}

	@Override
	public void update(FreeBoardVO vo) {
		mapper.update(vo);
	}

	@Override
	public void delete(int bno) {
		mapper.delete(bno);

	}

}

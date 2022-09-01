package com.spring.myweb.snsboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.myweb.command.SnsBoardVO;
import com.spring.myweb.command.SnsLikeVO;
import com.spring.myweb.snsboard.mapper.ISnsBoardMapper;
import com.spring.myweb.util.PageVO;

@Service
public class SnsBoardService implements ISnsBoardService {
	
	@Autowired
	private ISnsBoardMapper mapper;

	@Override
	public void insert(SnsBoardVO vo) {
		mapper.insert(vo);

	}

	@Override
	public List<SnsBoardVO> getList(PageVO paging) {
		return mapper.getList(paging);
	}

	@Override
	public SnsBoardVO getDetail(int bno) {
		return mapper.getDetail(bno);
	}

	@Override
	public void delete(int bno) {
		mapper.delete(bno);

	}

	@Override
	public int searchLike(SnsLikeVO vo) {
		return mapper.searchLike(vo);
	}

	@Override
	public void createLike(SnsLikeVO vo) {
		mapper.createLike(vo);
	}

	@Override
	public void deleteLike(SnsLikeVO vo) {
		mapper.deleteLike(vo);
	}

	@Override
	public int likeCnt(int bno) {
		return mapper.likeCnt(bno);
	}

	@Override
	public List<Integer> listLike(String userId) {
		return mapper.listLike(userId);
	}

}

package com.spring.myweb.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.spring.myweb.command.UserVO;

public interface IUserMapper {
	
	//아이디 중복확인
	int idCheck(String id);
	
	//회원가입
	void join(UserVO vo);
	
	//로그인
	UserVO login(@Param("id") String id,	//null오면 로그인실패
				@Param("pw") String pw);
	
	//회원 정보 얻어오기
	//리턴타입을 UserVO로 해두면 결과가 하나일거라 예상해서 TooManyResultsException 뜬다.
	//-> resultMap에 모든 컬럼들을 다 써줘야한다...(이름 같더라도). collection은 걔 하나만 적용하는거라는걸 알려주기위해.
	UserVO getInfo(String id);
	
	//회원 정보 수정
	void updateUser(UserVO vo);
	
	//회원 정보 삭제
	void deleteUser(@Param("id") String id,
					@Param("pw") String pw);
	
}

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="../include/header.jsp"%>

    <section>
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-7 col-xs-10 login-form">
                    <div class="titlebox">
                        로그인
                    </div>
                    <form action="<c:url value='/user/login'/>" method="post" name="login" id="loginForm">
                        <div class="form-group"><!--사용자클래스선언-->
                            <label for="id">아이디</label>
                            <input type="text" class="form-control" id="id" name="userId" placeholder="아이디">
                         </div>
                         <div class="form-group"><!--사용자클래스선언-->
                            <label for="id">비밀번호</label>
                            <input type="password" class="form-control" id="pw" name="userPw" placeholder="비밀번호">
                         </div>
                         <div class="form-group">
                            <button type="button" class="btn btn-info btn-block" id="loginBtn">로그인</button>
                            <button type="button" class="btn btn-primary btn-block" onclick="location.href=<c:url value='/user/join'/>">>회원가입</button>
                         </div>
                    </form>                
                </div>
            </div>
        </div>
    </section>
<%@ include file="../include/footer.jsp"%>

<script>
	const msg = '${msg}';
	if (msg === 'joinSuccess') {
		alert('회원가입 정상처리되었습니다.');
	} else if (msg === 'loginFail') {
		alert('로그인 실패! 아이디와 비밀번호를 확인하세요.');
	}
	
	//입력란이 공백인지 확인후, 공백이 아니라면 submit()진행.
	//mapper에 작성한 login 메서드의 리턴타입은 UserVO
	//리턴타입에 맞게 sql문을 작성해 주시면 됩니다.
	
	$(function() {
		$('#loginBtn').click(function() {
			
			/* 내코드
			if ($('#id').val()!=='' && $('#pw').val()!=='') {
				document.login.submit();
			} */
			
			
			//선생님코드
			if($('#id').val() === '') {
				alert('아이디를 입력해주세요.');
				return;
			} else if ($('#pw').val() === '') {
				alert('비밀번호를 입력하세요.');
				return;
			} else {
				$('#loginForm').submit();
			}
		});
		
		
		
	});//end jQuery
		

	
	
	
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


 <%@ include file="../include/header.jsp" %>
 
 
    <section>
        <div class="container">
            <div class="row">
                <div class="col-xs-12 col-md-9 write-wrap">
                        <div class="titlebox">
                            <p>상세보기</p>
                        </div>
                        
                        <form>
                            <div>
                                <label>DATE</label>
                                
                                <c:if test="${article.updateDate == null }">
                                	<p>${article.regDate }</p>
                                </c:if>
                                <c:if test="${article.updateDate != null }">
                                	<p>${article.updateDate }</p>
                                </c:if>
                            </div>   
                            <div class="form-group">
                                <label>번호</label>
                                <input class="form-control" name='##' value=${article.bno } readonly>
                            </div>
                            <div class="form-group">
                                <label>작성자</label>
                                <input class="form-control" name='##' value=${article.writer } readonly>
                            </div>    
                            <div class="form-group">
                                <label>제목</label>
                                <input class="form-control" name='##' value=${article.title } readonly>
                            </div>

                            <div class="form-group">
                                <label>내용</label>
                                <textarea class="form-control" rows="10" name='##' readonly>${article.content }</textarea>
                            </div>

							<button type="button" class="btn btn-primary" onclick="location.href='<c:url value="/freeboard/freeModify?bno=${article.bno}&writer=${article.writer }" />'">변경</button>
                            <button type="button" class="btn btn-dark" onclick="location.href='${pageContext.request.contextPath}/freeboard/freeList?pageNum=${p.pageNum}&cpp=${p.cpp}&condition=${p.condition}&keyword=${p.keyword}'">목록</button>

                           <%--  <button type="button" class="btn btn-primary" onclick="location.href='<c:url value='/freeboard/freeModify?bno=${article.bno}' /> ' ">변경</button>
                            <button type="button" class="btn btn-dark" onclick="location.href='<c:url value='/freeboard/freeList?pageNum=${p.pageNum }&cpp=${p.cpp }&condition=${p.condition }&keyword=${p.keyword }'/>"'>목록</button> --%>
                    </form>
                </div>
            </div>
        </div>
        </section>
        
        <section style="margin-top: 80px;">
            <div class="container">
                <div class="row">
                    <div class="col-xs-12 col-md-9 write-wrap">
                        <form class="reply-wrap">
                            <div class="reply-image">
                                <img src= "<c:url value='/img/profile.png'/>">
                            </div>
                            <!--form-control은 부트스트랩의 클래스입니다-->
	                    <div class="reply-content">
	                        <textarea class="form-control" rows="3" id="reply"></textarea>
	                        <div class="reply-group">
	                              <div class="reply-input">
		                              <input type="text" class="form-control" placeholder="이름" id="replyId">
		                              <input type="password" class="form-control" placeholder="비밀번호" id="replyPw">
	                              </div>
	                              
	                              <button type="button" class="right btn btn-info" id="replyRegist">등록하기</button>
	                        </div>
	
	                    </div>
                        </form>

                        <!--여기에접근 반복 (댓글)-->
                        <div id="replyList">
                        
                        
                       <!-- 자바스크립트단에서 반복문을 이용해서 댓글의 개수만큼 반복해서 표현하겠다.
                        <div class='reply-wrap'>
                            <div class='reply-image'>
                                <img src='../resources/img/profile.png'>
                            </div>
                            <div class='reply-content'>
                                <div class='reply-group'>
                                    <strong class='left'>honggildong</strong> 
                                    <small class='left'>2019/12/10</small>
                                    <a href='#' class='right'><span class='glyphicon glyphicon-pencil'></span>수정</a>
                                    <a href='#' class='right'><span class='glyphicon glyphicon-remove'></span>삭제</a>
                                </div>
                                <p class='clearfix'>여기는 댓글영역</p>
                            </div>
                        </div> -->
                        
                        
                        
                        </div>
                        <!-- 댓글 더보기 (페이징) -->
                        <button class="form-control" id="moreList">더보기</button>
                    </div>
                </div>
            </div>
        </section>
        
	<!-- 모달 -->
	<div class="modal fade" id="replyModal" role="dialog">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="btn btn-default pull-right" data-dismiss="modal">닫기</button>
					<h4 class="modal-title">댓글수정</h4>
				</div>
				<div class="modal-body">
					<!-- 수정폼 id값을 확인하세요-->
					<div class="reply-content">
					<textarea class="form-control" rows="4" id="modalReply" placeholder="내용입력"></textarea>
					<div class="reply-group">
						<div class="reply-input">
						    <input type="hidden" id="modalRno">
							<input type="password" class="form-control" placeholder="비밀번호" id="modalPw">
						</div>
						<button class="right btn btn-info" id="modalModBtn">수정하기</button>
						<button class="right btn btn-info" id="modalDelBtn">삭제하기</button>
					</div>
					</div>
					<!-- 수정폼끝 -->
				</div>
			</div>
		</div>
	</div>

	
	<%@ include file="../include/footer.jsp" %>
	
	<script>
		const msg = '${msg}';
		if (msg !== '') {
			alert(msg);
		}
	
		$(document).ready(function() {
			//비동기방식(ajax이용해서)
			$('#replyRegist').click(function() {	//등록버튼클릭했을때
				/*
		         댓글을 등록하려면 게시글 번호도 보내 주셔야 합니다.
		         댓글 내용, 작성자, 댓글 비밀번호, 게시글 번호를 
		         json 표기 방법으로 하나로 모아서 전달해 주시면 됩니다.
		         비동기 통신으로 댓글 삽입을 처리해 주시고,
		         console.log를 통해 '댓글 등록 완료!'를 확인하시고
		         실제 DB에 댓글이 추가되는지도 확인해 주세요.
		         전송방식: POST, url: /reply/replyRegist
		         */
		        
		         const bno = ${article.bno}; //컨트롤러에서 넘어온 게시글 번호
		         const replyId = $('#replyId').val();	//댓글아이디
		         const replyPw = $('#replyPw').val();	//댓글비번
		         const reply = $('#reply').val();		//댓글내용 
		         
		         if (reply === '' || replyId === '' || replyPw === '' ) {
		        	 alert('이름/ 비밀번호/ 내용을 입력하세요.');
		        	 return;
		         }
		         
		         const r = {
		        		 'bno' : bno,
		        		 'replyId' : replyId,
		        		 'replyPw' : replyPw,
		        		 'reply' : reply
		         };
		         
		         $.ajax({
		        	type : 'POST',
		        	url : '<c:url value= "/reply/replyRegist"/>',
		        	headers : {
		        		'Content-Type' : 'application/json'
		        	},
		        	dataType : 'text', //서버로부터 어떤 형식으로 받을지 (text는 디폴트라 생략해도 가능.)
		        	data : JSON.stringify(r),
		        	
		        	//객체(r)로 안묶어놨을때 지금 자리에서 묶어 보내도 된다.
		        	/* data : JSON.stringify( {
		        		"bno" : bno,
		        		"reply" : reply,
		        		"replyId" : replyId,
		        		"replyPw" : replyPw
		        	}) */
		        	
		        	success : function (data) {
		        		
		        		console.log('통신 성공 ! : ' + data);
		        		$('#reply').val('');
		        		$('#replyId').val('');
		        		$('#replyPw').val(''); 	//비워두기
		        		//등록완료후 댓글목록 함수를 호출해서 비동기식으로 목록 표현 (목록새롭게불러오기)
		        		getList(1,true);
		        		
		        	},
		        	error : function() {
		        		alert ('등록에 실패했습니다. 관리자에게 문의하세요');	
		        		
		        	}
		         }); //댓글등록 비동기통신 끝
				
			});//댓글등록이벤트 끝
			
			//더보기버튼 클릭처리(클릭시 전역변수page에 +1한 값을 전달)
			$('#moreList').click(function() {
				getList(++page, false);	//더보기니까 누적해야해서 false. 1페이지내용 + 2페이지 라서.
										//전위연산해야해서
			});
			
			
			
			//댓글목록요청 + 페이징
			let page = 1;	//페이지번호
			let strAdd = ''; //화면에 그려넣을 태그를 문자열의 형태로 추가할 변수
			
			getList(1, true); 	//freeDetail 화면에 처음 진입시 댓글리스트 불러오기		
			
			//함수
			//getList의 매개값으로 요청된페이지번호(pageNum)와, 
			//화면을 리셋할것인지의여부를 boolean 타입의 변수 'reset'으로 받겠습니다.
			//(페이지가 그대로 머물면서 댓글이 밑으로 계속 쌓이기 때문에 상황에 따라 페이지를 리셋해서 새롭게 가져올 것인지 
					//누적해서 계속 쌓을것인지의 여부를 확인)
			function getList(pageNum, reset) {
				
				const bno = '${article.bno}';	//게시글 번호
				
				//getJSON 함수를 통해 JSON 형식의파일을 읽어올 수 있다.
				//get방식의 요청을 통해 서버로부터 받은 JSON 데이터를 로드한다. (ajax도 쓸수있긴함 )
				//$.getJSON(url, [DATA], [통신성공여부])
				$.getJSON(
					"<c:url value='/reply/getList/' />" + bno + '/' + pageNum,	//파라미터 방식 아닌 'pathVariable방식'
					function(data) {
						console.log(data);
						
						let total = data.total; //총 댓글 수
						let replyList = data.list; //댓글 리스트
						
						//insert, update, delete 작업 후에는 댓글을 누적하고있는 strAdd변수를 초기화해서
						//화면이 리셋된것처럼 보여줘야합니다.
						if(reset === true) {	//초기화. 이거안하면 strAdd 누적된다
							strAdd='';
							page=1;
						}
						
						//페이지 * 데이터 수 보다 전체댓글개수가 작으면 더보기 버튼을 없애자.
						console.log('현재 페이지 : ' +page);
						if (total <= page * 5) {
							$('#moreList').css('display', 'none');
						} else {
							$('#moreList').css('display', 'block');
						}
						
						//응답데이터의 길이가 0과 같거나 더 작으면 함수를 종료하자.
						if (replyList.length<=0) {
							return;	//함수 종료
						}
						
						
						
						for (let i=0; i<replyList.length; i++) {
							
							strAdd +=		//strAdd에 이어붙여넣겠다. (뒤에내용 백틱으로 감쌈. 넣어줘야될내용 빼고)
								
							`	<div class='reply-wrap'>
                            <div class='reply-image'>
                                <img src='${pageContext.request.contextPath}/img/profile.png'>
                            </div>
                            <div class='reply-content'>
                                <div class='reply-group'>
                                    <strong class='left'> ` + replyList[i].replyId + ` </strong> 
                                    <small class='left'> ` + timeStamp(replyList[i].replyDate) + ` </small>
                                    <a href='  ` + replyList[i].rno +  ` ' class='right replyDelete'><span class='glyphicon glyphicon-remove'></span>삭제</a>
                                    <a href='  ` + replyList[i].rno +  ` ' class='right replyModify'><span class='glyphicon glyphicon-pencil'></span>수정</a>
                                </div>
                                <p class='clearfix'> ` + replyList[i].reply + `</p>
                            </div>
                        </div>	`;
						}
						//replyList라는 div 영역에 문자열 형식으로 모든 댓글을 추가.
						$('#replyList').html(strAdd);
						
						
						
					}
				);//end getJSON
				
			}//end getList()
			
			//댓글 수정, 삭제 (모달 띄워서)
			/* 이렇게 쓰면 안된다. 이벤트 안먹고 a 태그로 먹힌다.
			이유 : ajax함수의 실행이 더 늦게 완료가 되기 때문에, 실제 이벤트선언이 먼저 실행되게 됩니다.
			이런상황에서는 화면에 댓글 관련 창은 아무것도 등록되어있지 않은 형태이므로,
			일반 클릭이벤트가 동작하지 않습니다.
			이 때는 반드시! 
			이미 존재하는 #replyList에 이벤트를 등록하고, 이벤트를 자식에게 전파시켜 사용하는
			이벤트 위임함수를 사용해야합니다.
			
			$('.replyModify').click(function(e) {
				e.preventDefault();
				console.log('수정 이벤트 발생');
			});
			*/
			$('#replyList').on('click', 'a', function(e) {
				e.preventDefault();		//태그의 고유 기능 중지
				console.log('이벤트 함수 동작');
				
				//1. a 태그가 두개(수정, 삭제)이므로 버튼부터 구분. + 수정,삭제가 발생하는 댓글 번호가 몇 번인지도 확인.
				const rno = $(this).attr('href');
				$('#modalRno').val(rno); //모달 내부에 hidden input 태그에 댓글 번호를 담아서 전송.
				
				//2. 모달창 하나를 이용해서 상황에 따라 수정/삭제 모달을 구분하기위해 조건문 작성
				//(모달 하나로 수정,삭제 같이 처리. 모달창 디자인만 살짝 수정)
				if ($(this).hasClass('replyModify')) {	
						//hasClass() : 클래스의 존재유무 확인 (클래스이름에 매개값이 포함되어있다면 true,없으면 false)
						//true : 수정, false : 삭제
						
						//수정모달  형식
						$('.modal-title').html('댓글 수정');
						$('#modalReply').css ('display','inline');
						$('#modalModBtn').css ('display','inline');
						$('#modalDelBtn').css ('display','none');
						
						//jQuery를 이용한 모달열기 - .modal('show')
										//닫기 - .modal('hide')
										
										//cf)BootStrap에서는 trigger를 통해서 모달을 열고 닫았지만,
										//지금은 그런게 없기 때문에 제이쿼리를 이용해 직접 모달을 열고 닫습니다.
						$('#replyModal').modal('show');
						
						
				} else {
					//삭제모달 형식
					$('.modal-title').html('댓글 삭제');
					$('#modalReply').css ('display','none');
					$('#modalModBtn').css ('display','none');
					$('#modalDelBtn').css ('display','inline');
					
					$('#replyModal').modal('show');
					
				}
			});	//수정or삭제버튼 클릭 이벤트 처리 끝
			
			//수정처리함수 (수정 모달을 열어서 수정 내용을 작성 후 수정버튼을 클릭했을 때)
			$('#modalModBtn').click(function() {
				
				/*
		         1. 모달창에 rno값, 수정한 댓글 내용(reply), replyPw값을 얻습니다.
		         2. ajax함수를 이용해서 post방식으로 reply/update 요청,
		         필요한 값은 JSON형식으로 처리해서 요청.
		         3. 서버에서는 요청받을 메서드 선언해서 비밀번호 확인하고, 비밀번호가 맞다면
		          수정을 진행하세요. 만약 비밀번호가 틀렸다면 "pwFail"을 반환해서
		          '비밀번호가 틀렸습니다.' 경고창을 띄우세요.
		         4. 업데이트가 진행된 다음에는 modal창의 모든 값을 ''로 처리해서 초기화 시키시고
		          modal창을 닫으세요.
		          수정된 댓글 내용이 반영될 수 있도록 댓글 목록을 다시 불러 오세요.
		         */
		         
				const rno = $('#modalRno').val();
				const reply = $('#modalReply').val();
				const replyPw = $('#modalPw').val();
				
				if (reply === '' || replyPw === '') {
					alert('내용, 비밀번호를 확인하세요. ');
					return;
				}
				
				$.ajax({
					type : 'POST',
					url : '<c:url value= "/reply/update" />',
					headers : {
		        		'Content-Type' : 'application/json'
		        	},		//이거 그냥headers 없이 contentType : 'application/json' 해도된다.
					dataType : 'text',
					data : JSON.stringify( {
						'rno' : rno,
						'reply' : reply,
						'replyPw' : replyPw
					}),
					success : function (result) {
						
						if (result === 'pwSuccess') {
					
							console.log('댓글 수정 통신 성공' + result );
							alert('정상 수정되었습니다.');
							$('#modalReply').val('');
							$('#modalPw').val('');
							$('#replyModal').modal('hide');
							getList(1, true);
						
						} else if (result === 'pwFail') {
							console.log('댓글 수정 통신 성공' + result);
							alert('비밀번호가 틀렸습니다.');
							$('#modalPw').val('');
							$('#modalPw').focus();
						}
					},
					error : function() {
						alert('댓글 수정 실패');
						}
					
				});	//end ajax(수정)

			});	//수정 처리 이벤트 끝
			
			
			
			//댓글 삭제 처리 함수
			$('#modalDelBtn').click(function () {
				/*
		         1. 모달창에 rno값, replyPw값을 얻습니다.
		         2. ajax함수를 이용해서 POST방식으로 /reply/delete 요청
		          필요한 값은 JSON 형식으로 처리해서 요청
		         3. 서버에서는 요청을 받아서 비밀번호를 확인하고, 비밀번호가 맞으면
		          삭제를 진행하시면 됩니다.
		         4. 만약 비밀번호가 틀렸다면, 문자열을 반환해서 
		          '비밀번호가 틀렸습니다.' 경고창을 띄우세요.
		         */
		         
				const rno = $('#modalRno').val();
				const replyPw = $('#modalPw').val();
				
				if (replyPw == '') {
					alert('비밀번호를 확인하세요');
					return;
				}
				
				$.ajax({
					type : 'POST',
					url : '<c:url value= "/reply/delete" />',
					contentType : 'application/json',
					dataType : 'text',
					data : JSON.stringify({
						'rno' : rno,
						'replyPw' : replyPw
					}),
					success : function (result) {
						if (result == 'delSuccess') {
							console.log('댓글 삭제 통신 성공' + result );
							$('#modalPw').val('');
							$('#replyModal').modal('hide');
							alert('정상 삭제되었습니다.');
							getList(1, true);
						} else if (result == 'delFail') {
							console.log('댓글 삭제 통신 성공' + result);
							alert('비밀번호가 틀렸습니다.');
							$('#modalPw').val('');
							$('#modalPw').focus();
						}
					},
					error : function() {
						alert ('댓글 삭제 실패');
					}
					
				});
		         
		         
			});
			
			
			
			
			
			//날짜처리함수
			function timeStamp(millis) {
				const date = new Date(); //현재날짜얻어오기
				//현재날짜를 밀리초로 변환해서 등록일밀리초를 빼주면 -> 시간차
				const gap = date.getTime() - millis;
				
				let time;	//리턴할 시간
				if (gap < 60 * 60 * 24 * 1000) { //1일 미만일 경우
					if (gap < 60 * 60 * 1000) { //1시간 미만일 경우
						time = '방금 전';
					} else {
						time = parseInt(gap/(1000*60*60)) + '시간 전'; 
					}
				} else {	//gap이 하루 이상일 경우
					const today = new Date(millis);
					const year = today.getFullYear(); //년
					const month = today.getMonth() +1; //월 (0부터시작이라)
					const day = todaty.getDate(); //일
					
					time = year +'년 ' +month + '월 '+ day + '일 ';
				}
				return time;
			}
			
			
			
		}); //end jQuery
		
		
		
		
	</script>
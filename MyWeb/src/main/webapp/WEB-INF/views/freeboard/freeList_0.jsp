<%-- 기존방법

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
    
    <%@ include file="../include/header.jsp" %>
    
    
    <section>
        <div class="container-fluid">
            <div class="row">
                <!--lg에서 9그리드, xs에서 전체그리드-->   
                <div class="col-lg-9 col-xs-12 board-table">
                    <div class="titlebox">
                        <p>자유게시판</p>
                    </div>
                    <hr>
                    
                    <!--form select를 가져온다 -->
            <form>
		    		<div class="search-wrap">
                       <button type="button" class="btn btn-info search-btn">검색</button>
                       <input type="text" name="keyword" class="form-control search-input" value="${pc.paging.keyword }">
                       <select class="form-control search-select" name="condition">
                            <option value="title" ${pc.paging.condition == 'title' ? 'selected' : '' }>제목</option>
                            <option value="content" ${pc.paging.condition == 'content' ? 'selected' : '' }>내용</option>
                            <option value="writer" ${pc.paging.condition == 'writer' ? 'selected' : '' }>작성자</option>
                            <option value="titleContent" ${pc.paging.condition == 'titleContent' ? 'selected' : '' }>제목+내용</option>
                       </select>
                    </div>
		    </form>
                   
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>번호</th>
                                <th class="board-title">제목</th>
                                <th>작성자</th>
                                <th>등록일</th>
                                <th>수정일</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:forEach var="vo" items="${boardList}">
	                            <tr>
	                                <td>${vo.bno}</td>
	                                <td><a href="<c:url value='/freeBoard/freeDetail?bno=' />">${vo.title}</a></td>
	                                <td>${vo.writer}</td>
	                                <td><fmt:formatDate value="${vo.regDate}" pattern="yyyy-MM-dd HH:mm" /></td>
	                                <td><fmt:formatDate value="${vo.updateDate}" pattern="yyyy-MM-dd HH:mm" /></td>
	                            </tr>
                            </c:forEach>
                        </tbody>
                        
                    </table>


                    <!--페이지 네이션을 가져옴-->
		    <form>
                    <div class="text-center">
                    <hr>
                    <ul class="pagination pagination-sm">
                    	<c:if test="${pc.prev }">
                        	makeURI사용 전
                        	 <li><a href="/freeboard/freeList?pagenum=${pc.paging.pageNum }&cpp=${pc.paging.cpp }&condition=${pc.paging.condition }&keyword=${pc.paging.keyword } ">이전</a></li>
                        	<li><a href="<c:url value='/freeboard/freeList${pc.makeURI(pc.beginPage-1 )}' />"> 이전 </a></li>
                        
                        </c:if>
                        
                        <c:forEach var="num" begin="${pc.beginPage }" end="${pc.endPage}">
                        	<li  class="${pc.paging.pageNum == num ? 'active' : '' }"><a href="<c:url vlaue='freeboard/freeBoard${pc.makeURI}'">${num }</a></li>
                       </c:forEach>
                       
                        <c:if test="${pc.next }">
                        	<li><a href="<c:url value='/freeboard/freeList${pc.makeURI(pc.endPage+1 )}' /> "> 다음 </a> </li>
                        </c:if>
                        
                    </ul>
                    <button type="button" class="btn btn-info" onclick="location.href='<c:url value="/freeBoard/freeRegist" />'">글쓰기</button>
                    </div>
		    </form>

                </div>
            </div>
        </div>
	</section>
	
	<%@ include file="../include/footer.jsp" %>






	<script>
		$(function() {
			$('.search-btn').click(function() {
				const keyword = $('.search-input').val();
				const condition = $('.search-select').val();
				
				console.log(keyword);
				console.log(condition);
			
									//c:url 대신에 씀 
				location.href='${pageContext.request.contextPath}/freeboard/freeList?keyword='+keyword+'&condition='+condition;
			});
			
			
		});	//end jQuery
	</script>

 --%>
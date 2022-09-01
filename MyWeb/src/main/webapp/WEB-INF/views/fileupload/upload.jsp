<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


	<!-- 파일업로드에서는 enctype(인코딩타입)을 반드시 "multipart/form-data"로 지정해야 컨트롤러에서 받을수있다. -->
	<form action="upload_ok" method="post" enctype="multipart/form-data">
		파일선택 : <input type="file" name="file"> <br> 
		<input type="submit" value="전송">
	</form>


	<hr>

	<!-- multiple : 여러개 선택 가능 (묶어서보내기)-->
	<form action="upload_ok2" method="post" enctype="multipart/form-data">
		파일선택 : <input type="file" multiple="multiple" name="files"> <br>
		<input type="submit" value="전송">
	</form>

	<hr>

	<!-- 여러개 다른방법 (input을 여러개) -->
	<form action="upload_ok3" method="post" enctype="multipart/form-data">
		파일선택 : <input type="file" name="file"> <br> 
		파일선택 : <input type="file" name="file"> <br> 
		파일선택 : <input type="file" name="file"> <br> 
		<input type="submit" value="전송">
	</form>
	
	<hr>
	
	<!-- 저장할 파일명 지정하는 방식 -->
	<form action="upload_ok4" method="post" enctype="multipart/form-data">
		파일명 뭘로할건지 정하세요 : <input type="text" name="list[0].name">
		파일선택 : <input type="file" name="list[0].file"> <br> 
		파일명 뭘로할건지 정하세요 : <input type="text" name="list[1].name">
		파일선택 : <input type="file" name="list[1].file"> <br> 
		파일명 뭘로할건지 정하세요 : <input type="text" name="list[2].name">
		파일선택 : <input type="file" name="list[2].file"> <br> 
		<input type="submit" value="전송">
	</form>
	
	
	
</body>
</html>
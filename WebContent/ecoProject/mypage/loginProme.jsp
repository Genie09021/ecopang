<%@page import="ecopang.model.UsersDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	request.setCharacterEncoding("utf-8");
	
	//loginForm 에서 넘어온 파라미터 담기
	String userID = request.getParameter("userID");
	String pw = request.getParameter("pw");

	
	//db에 와 비교해야 하니까 dao 만들어서 커넥션 해서 
		UsersDAO dao = new UsersDAO();
		boolean res = dao.idPwCheck(userID, pw);
	
%>
<body>
<%
	if(res){
		session.setAttribute("memId", userID );//세션에 속성 추가!! == 로그인 처리한 것
		response.sendRedirect("mypage.jsp");//mypage으로 이동하도록 !
	}
	 %>
</body>
</html>
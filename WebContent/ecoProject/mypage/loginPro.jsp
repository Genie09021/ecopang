<%@page import="ecopang.model.UsersDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>loginPro</title>
</head>
<% request.setCharacterEncoding("UTF-8");
	String userID = request.getParameter("userID");
	String pw = request.getParameter("pw");
	String auto = request.getParameter("auto");
	
	UsersDAO dao = new UsersDAO(); 
	boolean res = dao.idPwCheck(userID, pw); 
	if(res){// id, pw 일치 -> 로그인 처리
		if(auto != null){ // 자동로그인 체크, 로그인 시도
			// 쿠키 생성
			Cookie c1 = new Cookie("autoId", userID);
			Cookie c2 = new Cookie("autoPw", pw);
			Cookie c3 = new Cookie("autoCh", auto);
			c1.setMaxAge(60*60*24); // 24시간 // 쿠키기간 갱신
			c2.setMaxAge(60*60*24); // 24시간
			c3.setMaxAge(60*60*24); // 24시간
			response.addCookie(c1);
			response.addCookie(c2);
			response.addCookie(c3);
		}
		session.setAttribute("memId", userID); // 세션에 속성추가! -> 로그인 처리함
		response.sendRedirect("mypage.jsp"); // mypage으로 이동//수정
	}else{ %>
		<script>
			alert("id 또는 pw가 일치하지 않습니다. 다시 시도해주세요.");
			history.go(-1);
		</script>	
<%	}
%>
	
%>
<body>

</body>
</html>
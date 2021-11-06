<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="ecopang.model.ActDAO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="activity" class="ecopang.model.ActDTO"/>
<jsp:setProperty property="*" name="activity"/>
<%
	ActDAO dao = new ActDAO(); 
	dao.updateActivity(activity);

%>
	

<body>

</body>
</html>
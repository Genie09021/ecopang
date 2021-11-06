<%@page import="ecopang.model.ActDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
//	int act_num=Integer.parseInt(request.getParameter("act_num"));
//	String pw = request.getParameter("userId");
%>

<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="activity" class="ecopang.model.ActDTO"/>
<jsp:setProperty property="*" name="activity"/>
<%
	ActDAO dao = new ActDAO(); 
	dao.deleteActivity(activity);
%>

<body>
</body>
</html>
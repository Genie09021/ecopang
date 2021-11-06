<%@page import="ecopang.model.UsersDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ID 중복확인</title>
<%
	String userID = request.getParameter("userID");
	UsersDAO dao = new UsersDAO();
	boolean result = dao.confirmUserID(userID); 
%>

<body>
	<br />
	<% if(result){  // id 존재 %>
		<table>
			<tr>
				<td><%=userID %>, 이미 사용중인 아이디 입니다. </td>
			</tr>
		</table>
		<br />
		<form action="confirmUserID.jsp" method="post">
			<table>
				<tr>
					<td> 다른 아이디를 선택하세요. <br />
						<input type="text" name="userID" />
						<input type="submit" value="ID중복확인" /> 
					</td>
				</tr>
			</table>
		</form>
	<%}else{ // id 존재x %>
		<table>
			<tr>
				<td>입력하신 <%=userID %>는 사용가능한 아이디 입니다. <br />
					<input type="button" value="사용하기" onclick="setUserID()"/>
				</td>
			</tr>
		</table>
	<%} %>
	
	<script>
		function setUserID(){
			opener.document.inputForm.userID.value = "<%=userID%>";  
			self.close(); // 팝업창 닫기
		}
	</script>
	
</body>
</html>
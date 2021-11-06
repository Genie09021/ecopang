<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>loginPage</title>
</head>
<body>
	<div>
		<div>헤더</div>
		<!-- 헤더에 로그인 글씨?? -->
		
		<div>
			<form action="loginPro.jsp" method="post">
				<table>
					<tr>
						<td>아이디</td>
						<td><input type="text" name="userID"/></td>
					</tr>
					<tr>
						<td>비밀번호</td>
						<td><input type="password" name="pw"/></td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="checkbox" name="auto" value="1" /> 자동 로그인
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="로그인" />
							<input type="button" value="회원가입" onclick="window.location='signupForm.jsp'" />
						</td>
					</tr>
					
				</table>
			</form>
		</div>
		
		<div>푸터</div>
	</div>
</body>
</html>
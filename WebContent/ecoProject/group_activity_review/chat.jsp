<%@page import="ecopang.model.ActDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <title>채팅</title>
   <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link href="style.css" rel="stylesheet" type="text/css" >
</head>
<%
	request.setCharacterEncoding("UTF-8");
	String fromID = (String)session.getAttribute("memId");
	fromID = "test3@nate.com"; // 테스트용
	int num = 0; // 테스트용
	if(request.getParameter("act_num") != null)
			num = Integer.parseInt(request.getParameter("act_num"));
	num = 2; // 테스트용
	//ActDAO adao = new ActDAO();
	
%>
<script>
	
	var path = "${pageContext.request.contextPath}";
	let time;
	function submitFunction() {
		var fromID = '<%=fromID %>';
		var act_num = <%= num %>;
		var chat_content = $('#chat_content').val();
		
		$.ajax({
				type: "POST",
				url: path + "/chatSubmitServlet",
				data: {
					fromID: encodeURIComponent(fromID),
					act_num: act_num,
					chat_content: encodeURIComponent(chat_content),
				},
				success: function() {
				},
				error: function() {
					alert("실패");
				}
		});
		$('#chat_content').val('');
	}
	var last;
	function chatListFunction(date) {
		var fromID = '<%=fromID %>';
		var act_num = <%= num %>;
		last = date;
		$.ajax({
				type: "POST",
				url: path +  "/chatListServlet",
				data: {
					fromID: encodeURIComponent(fromID),
					act_num: act_num,
					last: last,
				},
				success: function(data) {
					if(data == "") return;
					var parsed = JSON.parse(data);
					var result = parsed.result;
					last = parsed.last;
					for(var i = 0; i < result.length; i++) {
						addChat(result[i][1].value, result[i][2].value, result[i][3].value);
					}
				}
		});
	}
	function addChat(chatName, chatContent, chatTime) {
		$('#chatList').append(
				'<dir class="row">' +
				chatName + "&nbsp;&nbsp;&nbsp;&nbsp;" + chatTime + '<br/>' +
				chatContent +
				'</dir>'
				);
		$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
	}
	function getInfiniteChat() {
		time = setInterval(function() {
			chatListFunction(last);
		},1000);
	}
</script>
<body>
  	<div class="chat_wrapper">
	  	<form method="post">
	  		<div>
	  			<% //adao.getActTitle(num) %>
	  			테스트 활동 제목 
	  		</div>
	  		<div id="chatList" class="chat_box">
	  			
	  		</div>
	  		<div>
	  			<input type="text" id="chat_content"/>
	  			<button onclick="submitFunction();">입력</button>
	  		</div>
  		</form>
  	</div>
  	<script>
  		$(document).ready(function() {
  			chatListFunction("1990-01-01 00:00:00");
  			getInfiniteChat();
  		})
  	</script>
</body>
  
</html>
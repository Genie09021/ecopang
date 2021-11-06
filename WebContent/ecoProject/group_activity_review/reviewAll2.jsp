<%@page import="ecopang.model.ReviewDTO"%> 
<%@page import="ecopang.model.UsersDTO"%> 
<%@page import="java.util.List"%>
<%@page import="ecopang.model.ReviewDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>review - 12page 소모임 상세 페이지에서 후기 더보기 버튼 누르면 이 페이지(17page)로 옴</title>
	<style>
	#button{
		border-radius:50%;
		width:100px;
		height:100px;
		background:#1e90ff;
		font-size:2.5em
	}
	#followplus { 
	position:absolute; 
	top:650px; right:50%; margin-right:-650px;
	}
	
	</style>
	<script src="//code.jquery.com/jquery-3.6.0.js"></script>
</head>
<%
	String userID = (String)session.getAttribute("memId");
	if(userID==null){%>
	<script>
		alert("로그인 이 필요한 페이지 입니다");
		history.back();
	</script>
<%	}
%>
	<script>
	$(window).scroll(function(){
		var scrollTop = $(document).scrollTop();
		$("#followplus").stop();
		$("#followplus").animate( {"top": scrollTop +650},250);
		});
	

	$(function(){
		  $("input[name=onlyPho]").click(function(){
			  alert("사진 후기만 보기");
			  $(location).attr('href',"reviewPhoto.jsp");
		  });
		});
	
	</script>
	
	<%-- --%>
	<script>
		function report(inputForm) {
			var url = "/ecopang/ecoProject/group_activity_review/reviewAll.jsp?category="+inputForm.category.value+"&num="+inputForm.num.value+"&userID="+inputForm.userID.value;
			open(
					url,
					"reviewAll",
					"toolbar=no, location=no, status=no, menubar=no, scrollbars=no resizeable=no, width=500, height=400");
		}
	</script>
	

<%	
	//12페이지에서 group_num 받아오기	
	//+++ 임시
	String group_Num = request.getParameter("group_num");
	if(group_Num == null){
		group_Num = "1";
	}
	int group_num = Integer.parseInt(group_Num);

	
	String sort = request.getParameter("sort");
	if(sort == null){
		sort="1";
	}
	//후기 전체 글을 담아올것
	int revCount = 0;//후기글 전체 개수
	List revList = null;//후기글 담아올 List 변수 생성
	
	ReviewDAO dao = new ReviewDAO();//사용할 java dao 객체 생성
	
	//페이지 정보** 
	// 한페이지에 보여줄 게시글의 수(변수로 지정할것이다.나중에 다시 바꿀수도 있으니) 
	int pageSize = 4; 

	// 현재 페이지 번호  
	String pageNum = request.getParameter("pageNum"); 
	if(pageNum == null){ // 
		pageNum = "1";
	}
	
	// 현재 페이지에 보여줄 게시글 시작과 끝 등등 정보 세팅 
	int currentPage = Integer.parseInt(pageNum); // 계산을 위해 현재페이지 숫자로 변환하여 저장 
	int startRow = (currentPage - 1) * pageSize + 1; // 페이지 시작글 번호 
	int endRow = currentPage * pageSize; // 페이지 마지막 글번호
	//DB에서 정렬했을때 가져오는 번호범위이다 첫번째부터 열개까지 가져와라 ~같은 
	
	//---------------------------------------
	
	// ++ 서치 한다면 밑에서 검색어 작성해서 list요청했다면 , 아래 reSel/reSearch 변수에 파라미터가 들어갈 것 임.
	String revSel = request.getParameter("revSel");
	String revSearch = request.getParameter("revSearch");
	
	//++
	if(revSel != null && revSearch != null){//검색을한 경우
		revCount = dao.getSearchRevCount(revSel,revSearch);//검색된 글의 총 개수 가져오는것
		//검색한 글이 하나라도 있으면 검색한 글 가져오기
		
		if(revCount >0){
			revList = dao.getSearchArticles(startRow, endRow, revSel, revSearch);
		}
		
	}else{//검색안함. 전체 게시판 요청
	
		//전체 글의 개수  
		revCount = dao.getRevCount(group_num);//DB에 저장되어있는 일반 전체 글의 개수를 가져와 담기

		if(revCount >0){
			if(sort.equals("1")){
				revList = dao.getRevArticles(startRow, endRow, group_num);
			}else{
				revList = dao.getBestRev(startRow, endRow);	
			}
		}
	}
		System.out.println("revList 확인 :"+revList);
	//--------------------------------------------------------------
	
%>
<body>
	<div class="mainDiv">
		<div class="header">
			
		</div>
		<div class="body" align="center"><h1>16Page 후기 더보기 페이지</h1><br/><br/>
			<%--작성자 또는 내용으로 검색하기 --%>
			<form action="reviewAll.jsp">
				<table >
					<tr>
						<td><select name="revSel">
							<option value="userID" >작성자</option>
							<option value="rev_content" >내용</option>
							</select>&#9;<input type="text" name="revSearch" size="50"/>
							&emsp;<input type="submit" value="검색"/></td>
					</tr>
				</table>
			</form>
			<span style="float:left;width:10%;" ><a href="reviewAll.jsp?sort=2">인기순</a>&nbsp;<a href="reviewAll.jsp">최신순</a></a></span> 
			<span style="float:right;width:49%;">사진 후기만 보기 <input type="checkBox" name="onlyPho" value="null"></span>
		</div><br/>
		
		<div class="main_content">
			
			<form name="noname">
				<ul style="list-style: none;">
					<%if(revList != null){
					
						for(int i =0; i<revList.size();i++){
							ReviewDTO revdto = (ReviewDTO)revList.get(i);
							UsersDTO udto = dao.getUserInfo(revdto.getRev_num());
							
							System.out.println("Test " + revdto);
							System.out.println("Test2 " + revdto.getRev_num());
							
							System.out.println("Test3 " + udto);
							
						if(revdto.getRev_img() != null){
					%>
			 				<li><img src="/ecopang/ecoProject/imgs/<%=revdto.getRev_img()%>" width="900"height="300"></li>
					<%		} %>
					<li></li>
					<li><%if(udto.getUser_img() == null){%>
			 			<img src="/ecopang/ecoProject/imgs/default.png" width="30"/>
						
					<%
						}else{
					%>
			 			<img src="/ecopang/ecoProject/imgs/<%=udto.getUser_img()%>" width="30"/><%}%><%=revdto.getUserID()%></li>
					<%-----------------------------------------------좋아요/신고하기 넣기---------------------------------------------- --%>	
					
					<form name="inputForm">
						<table>
						<tr>
							<td class="revReportBtn">
								<button type="button" onclick="report(this.form)">신고하기</button>
								<input type="hidden" name="category" value="후기"/> 
								<input type="hidden" name="num" value="<%= revdto.getRev_num()%>"/> 
								<input type="hidden" name="userID" value="<%= revdto.getUserID()%>"/> 
							</td>
						</tr>
						</table>
					</form>		
					
					
					
					<li><%=revdto.getRev_content() %></li>
					<li>----------------------------------------------------------------------------------------------------------------</li>
					<br/><br/><br/>
					<%	}
					}else{%>
						<li><pre>작성된 후기가 없습니다! 첫 후기를 작성해 보세요 !</pre></li>
					<%} %>
				</ul>
			</form>
		</div><br /> <br /> 

		<%-- 페이지 번호 --%>
		<div align="center">
		<% if(revCount > 0) {
			// 페이지 번호를 몇개까지 보여줄것인지 지정
			int pageBlock = 3; 
			// 총 몇페이지가 나오는지 계산 
			int pageCount = revCount / pageSize + (revCount % pageSize == 0 ? 0 : 1);
			
			// 현재 페이지에서 보여줄 첫번째 페이지번호
			int startPage = (int)((currentPage-1)/pageBlock) * pageBlock + 1; 
			// 현재 페이지에서 보여줄 마지막 페이지번호 
			int endPage = startPage + pageBlock - 1; 
			// 마지막에 보여줄 페이지번호는, 전체 페이지 수에 따라 
			if(endPage > pageCount) endPage = pageCount; 
			
			//++검색시, 페이지 번호 처리
			if(revSel != null &&revSearch !=null){
			
				// 왼쪽 화살표 
				if(startPage > pageBlock) { %>
					<a href="reviewAll.jsp?pageNum=<%=startPage-pageBlock %>&revSel=<%=revSel%>&revSearch=<%=revSearch%>" class="pageNums"> &lt; &nbsp;</a>
			<%	}
				
				// 페이지 번호 뿌리기 
				for(int i = startPage; i <= endPage; i++){ %>
					<a href="reviewAll.jsp?pageNum=<%=i%>&revSel=<%=revSel%>&revSearch=<%=revSearch%>" class="pageNums"> &nbsp; <%= i %> &nbsp; </a>
			<%	}
				
				// 오른쪽 화살표 : 전체 페이지 개수(pageCount)가 endPage(현재보는페이지에서의 마지막번호) 보다 크면 
				if(endPage < pageCount) { %>
					&nbsp; <a href="reviewAll.jsp?pageNum=<%=startPage+pageBlock%>&revSel=<%=revSel%>&revSearch=<%=revSearch%>" class="pageNums"> &gt; </a>
			<%	}
			
			}else{//if검색 안 했을때
					if(startPage > pageBlock) { %>
						<a href="reviewAll.jsp?pageNum=<%=startPage-pageBlock %>" class="pageNums"> &lt; &nbsp;</a>
				<%	}
					for(int i = startPage; i <= endPage; i++){ %>
						<a href="reviewAll.jsp?pageNum=<%=i%>" class="pageNums"> &nbsp; <%= i %> &nbsp; </a>
				<%	}	
					if(endPage < pageCount) { %>
						&nbsp; <a href="reviewAll.jsp?pageNum=<%=startPage+pageBlock%>" class="pageNums"> &gt; </a>
				<%	}
			}//else 검색 안했을때 
		}//if(count > 0)%>			
		<div id="followplus">
			<input type="button" value="+" id="button" onclick="window.location='reviewWriteForm.jsp'">
		</div>

		</div>
	</div>
</body>
</html>
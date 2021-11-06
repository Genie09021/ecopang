<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="ko">

    <head>

        <meta charset="utf-8">

        <script src="/js/jquery.js"></script>

        <script>


function getReadList() { $('#loading').html('데이터 로딩중입니다.'); //ajax 
$.post("data.html?action=getLastList&lastID=" + $(".list:last").attr("id"), function(data){ if (data != "") { $(".list:last").after(data); } $('#loading').empty(); }); }; //무한 스크롤 
$(window).scroll(function() { if($(window).scrollTop() == $(document).height() - $(window).height()){ getReadList(); } });

</script>

</head>

<body>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>    

    <div class="list">content</div>                     

    <div class="list" id="9">content</div>  

    <div id="loading"></div>

</body>

</html>






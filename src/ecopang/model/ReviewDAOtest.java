package ecopang.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class ReviewDAOtest {

	public Connection getConnection() throws Exception{
		Context ctx = new InitialContext();
		Context env = (Context)ctx.lookup("java:comp/env");
		DataSource ds = (DataSource)env.lookup("jdbc/orcl");
			return ds.getConnection();
	}

	//reviewWritePro
	//리뷰 작성글 insert
	public void insertReview(ReviewDTO reviews) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count =0;
		
		try {
			conn=getConnection();
			String sql="insert into reviews values(reviews_seq.nextval, ?, ?, ?, ?, ?)";
			//"insert into reviews values(reviews_seq.nextVal,?,?,?,?,?,?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, reviews.getGroup_num());
			pstmt.setString(2, "test3@nate.com");//reviews.getUserID()
			pstmt.setString(3, reviews.getRev_content());
			pstmt.setString(4, reviews.getRev_img());//
			pstmt.setTimestamp(5, reviews.getRev_reg());
			count = pstmt.executeUpdate();
			
			System.out.println("33번 리뷰 count 확인용 :" + count);//count 확인용
			System.out.println("34번 이미지 이름 확인용 :" + reviews.getRev_img());
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null) try{rs.close();}catch(Exception e){e.printStackTrace();}
			if(pstmt != null) try{pstmt.close();}catch(Exception e){e.printStackTrace();}
			if(conn != null) try{conn.close();}catch(Exception e){e.printStackTrace();}
		}
		
	}
	
	
	//reviewAll
	//리뷰 개수 가져오기
	public int getRevCount(int group_num) {
		Connection conn = null;
		PreparedStatement pstmt =null;
		int revCount =0;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql="select count(*) from reviews where group_num =?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, group_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				revCount = rs.getInt(1);//
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
			if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
			if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
		}
		return revCount;
	}
	
	
	/*1-1.페이지 모두 가져오기
	public List getGroupRev(int group_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List revList = null;//담아서 리턴할것
		
		try {
			conn = getConnection();
			String sql ="select * from reviews where group_num =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, group_num);//매개변수groupnum
			rs = pstmt.executeQuery();
			
			if(rs.next()) {//쿼리문 실행 값있으면
				revList = new ArrayList();//객체생성 -
				do {
					ReviewDTO revdto = new ReviewDTO();//반복할 때 마다 ReviewDTO에 다시 담는것.한바퀴돌면 추가하고 (add) 전 데이터는 추가됐고..다시 반복하면서 
					//do 밖에 생성하면 안됨...덮어쓰기됨
					revdto.setGroup_num(rs.getInt("group_num"));
					revdto.setRev_num(rs.getInt("rev_num"));
					revdto.setUserID(rs.getString("userId"));
					revdto.setRev_content(rs.getString("rev_content"));
					revdto.setRev_img(rs.getString("rev_img"));
					revdto.setRev_reg(rs.getTimestamp("rev_reg"));
					
					revList.add(revdto);
				}while(rs.next());
			}
			
		}catch(Exception e ) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
			if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
			if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}	
		}
		return revList;
	}
	
	*/
	//1-2.페이지 범위만큼 가져오기
	
	public List getRevArticles(int start, int end, int group_num) {//1~3
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List revList = null;
		
		try {
			conn = getConnection();
			String sql ="select A.*, r from (select reviews.*, rownum r from reviews where group_num = ?)A where r >= ? and r <= ?";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, group_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
		
			rs = pstmt.executeQuery();
			if(rs.next()) {
				revList = new ArrayList();//결과가 있으면 list 객체 생성해서 준비
				ReviewDTO revdto = null;
				do {
					revdto = new ReviewDTO();//반복할 때 마다 ReviewDTO에 다시 담는것.한바퀴돌면 추가하고 (add) 전 데이터는 추가됐고..다시 반복하면서 
					//do 밖에 생성하면 안됨...덮어쓰기됨
					revdto.setGroup_num(rs.getInt("group_num"));
					revdto.setRev_num(rs.getInt("rev_num"));
					revdto.setUserID(rs.getString("userId"));
					revdto.setRev_content(rs.getString("rev_content"));
					revdto.setRev_img(rs.getString("rev_img"));
					revdto.setRev_reg(rs.getTimestamp("rev_reg"));
					
					revList.add(revdto);
				}while(rs.next());	
			}
			
		}catch(Exception e ) {
			e.printStackTrace();
		}finally{
			if(rs != null)try {rs.close();}catch(Exception e ) {e.printStackTrace();}
			if(pstmt != null)try {pstmt.close();}catch(Exception e ) {e.printStackTrace();}
			if(conn != null)try {conn.close();}catch(Exception e ) {e.printStackTrace();}
		}
		return revList;
	}
	
	
	
	
	
	
	
	
	
	//작성자 회원 정보 담기
	//	->후기글에서 ,그글 작성자 
	//		-> users 테이블 
	//			->review 테이블
	
	public String getUserInfo(int rev_num) {
		Connection conn = null;
		PreparedStatement pstmt= null;
		ResultSet rs = null;
		UsersDTO usersdto = null;
		
		try{
			conn = getConnection();
			String sql="select B.* from reviews A, users B where (A.userID = B.userID) and A.rev_num=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, rev_num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				usersdto = new UsersDTO();//18ro
				usersdto.setUserID(rs.getString("userID"));
				usersdto.setPw(rs.getString("pw"));
				usersdto.setName(rs.getString("name"));
				usersdto.setNickname(rs.getString("nickname"));
				usersdto.setUser_img(rs.getString("user_img"));//5
				usersdto.setBirth(rs.getString("birth"));
				usersdto.setUser_city1(rs.getString("user_city1"));
				usersdto.setUser_district1(rs.getString("user_district1"));
				usersdto.setUser_city2(rs.getString("user_city2"));
				usersdto.setUser_district2(rs.getString("user_district2"));//10
				usersdto.setUser_city3(rs.getString("user_city3"));
				usersdto.setUser_district3(rs.getString("user_district3"));
				usersdto.setUser_level(rs.getInt("user_level"));
				usersdto.setReportCount(rs.getInt("reportCount"));
				usersdto.setWarning(rs.getInt("warning"));//15
				usersdto.setDel_reason(rs.getString("del_reason"));
				usersdto.setComplaints(rs.getString("complaints"));
				usersdto.setUser_state(rs.getString("user_state"));
				
				System.out.println("40번 확인용 출력 닉네임 : "+ rs.getString("nickname"));//확인용 출력
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)try {rs.close();}catch(Exception e) {e.printStackTrace();}
			if(pstmt!=null)try {pstmt.close();}catch(Exception e) {e.printStackTrace();}
			if(conn!=null)try {conn.close();}catch(Exception e) {e.printStackTrace();}
		}
		return usersdto.getUser_img();
	}
	
	//revewAll
	//작성자 , 내용 검색 가져오기
	//1-1list 검색한 글 갯수 가져오기
	public int getSearchRevCount(String revSel, String revSearch) {
		int revCount = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn= getConnection();
			String sql = "select count(*) from reviews where "+revSel +" like '%" + revSearch + "%'";
			//select count (*) from board where userID like '%abc%';
			pstmt= conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			System.out.println("39번 확인용 출력 reSel + revSearch :"+revSel + revSearch);
			
			if(rs.next()) {
				revCount = rs.getInt(1);//count(*) 은 결과를 숫자로 가져오며,컬럼명 대신에 컬럼번호1로 꺼내기 	
			}
			
		}catch(Exception e ) {
			e.printStackTrace();
		}finally {
			if(rs != null)try {rs.close();}catch(Exception e ) {e.printStackTrace();}
			if(pstmt != null)try {pstmt.close();}catch(Exception e ) {e.printStackTrace();}
			if(conn != null)try {conn.close();}catch(Exception e ) {e.printStackTrace();}
		}
		return revCount;
	}
	
	//1-2 총 글 가져오기
	//list 검색한 글들 가져오기 메서드
	public List getSearchArticles(int startRow, int endRow, String revSel, String revSearch) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List revList = null;
		
		try {
			conn = getConnection();
			String sql = "select * , r from (select reviews.*, rownum r from reviews where "+revSel+" like '%" + revSearch + "%') where r>=? and r<=?";
			sql="select A.* , r from (select reviews.*, rownum r from reviews where "+revSel+" like '%"+revSearch+"%')A where r>=? and r<=?";
			//select * from reviews where userID like '%test%';
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			revList = new ArrayList();//결과가 있으면 list 객체 생성해서 준비
			ReviewDTO searchArticle = null;
			if(rs.next()) {
				do {//if문에서 rs.next() 실행되어서 커서가 내려가 버렸으니 do-while로 먼저 데이터 꺼내게 하기
					searchArticle = new ReviewDTO();
					searchArticle.setGroup_num(rs.getInt("group_num"));
					searchArticle.setRev_num(rs.getInt("rev_num"));
					searchArticle.setUserID(rs.getString("userId"));
					searchArticle.setRev_content(rs.getString("rev_content"));
					searchArticle.setRev_img(rs.getString("rev_img"));
					searchArticle.setRev_reg(rs.getTimestamp("rev_reg"));
					revList.add(searchArticle);//리스트에 추가
					
				}while(rs.next());	
			}
			
		}catch(Exception e ) {
			e.printStackTrace();
		}finally{
			if(rs != null)try {rs.close();}catch(Exception e ) {e.printStackTrace();}
			if(pstmt != null)try {pstmt.close();}catch(Exception e ) {e.printStackTrace();}
			if(conn != null)try {conn.close();}catch(Exception e ) {e.printStackTrace();}
		}
		return revList;
	}
	
	
	//사진 있는 후기는 총 몇개일까?
	public int getRevPhoto(int group_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int revPhoto =0;
		
		try {
			conn = getConnection();
			String sql="select count(*) from reviews where rev_img is not null";
			pstmt=conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
			
			if(rs.next()) {
				revPhoto = rs.getInt(1);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(rs != null)try {rs.close();}catch(Exception e ) {e.printStackTrace();}
			if(pstmt != null)try {pstmt.close();}catch(Exception e ) {e.printStackTrace();}
			if(conn != null)try {conn.close();}catch(Exception e ) {e.printStackTrace();}
		}
		return revPhoto;
	}
	
	//
	//사진후기를 페이지 범위만큼 가져오기
	
	public List getRevPho(int start, int end, int group_num) {//1~3
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List revList = null;
		
		try {
			conn = getConnection();
			String sql ="select A.*, r from (select reviews.*, rownum r from reviews where group_num = ?)A where r >= ? and r <= ? and rev_img is not null";
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, group_num);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
		
			rs = pstmt.executeQuery();
			if(rs.next()) {
				revList = new ArrayList();//결과가 있으면 list 객체 생성해서 준비
				ReviewDTO revdto = null;
				do {
					revdto = new ReviewDTO();
					//
					revdto.setGroup_num(rs.getInt("group_num"));
					revdto.setRev_num(rs.getInt("rev_num"));
					revdto.setUserID(rs.getString("userId"));
					revdto.setRev_content(rs.getString("rev_content"));
					revdto.setRev_img(rs.getString("rev_img"));
					revdto.setRev_reg(rs.getTimestamp("rev_reg"));
					
					revList.add(revdto);
				}while(rs.next());	
			}
			
		}catch(Exception e ) {
			e.printStackTrace();
		}finally{
			if(rs != null)try {rs.close();}catch(Exception e ) {e.printStackTrace();}
			if(pstmt != null)try {pstmt.close();}catch(Exception e ) {e.printStackTrace();}
			if(conn != null)try {conn.close();}catch(Exception e ) {e.printStackTrace();}
		}
		return revList;
	}
	
	
	
	//reviewPhoto
	/*사진 후기만 보기
	public List getRevPho() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List phoList  = null;
		
		try {
			conn = getConnection();
			String sql="select * from reviews where rev_img is not null ";
			pstmt = conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
			
				phoList = new ArrayList();
				ReviewDTO phodto = null;
			if(rs.next()) {
				do {
					phodto = new ReviewDTO();
					phodto.setRev_num(rs.getInt("rev_num"));;
					phodto.setGroup_num(rs.getInt("group_num"));
					phodto.setUserID(rs.getString("userID"));
					phodto.setRev_content(rs.getString("rev_content"));
					phodto.setRev_img(rs.getString("rev_img"));
					phodto.setRev_reg(rs.getTimestamp("rev_reg"));
					phoList.add(phodto);
					
				}while(rs.next());
			}
			
		}catch(Exception e){
				e.printStackTrace();
		}finally {
				if(rs != null)try {rs.close();}catch(Exception e ) {e.printStackTrace();}
				if(pstmt != null)try {pstmt.close();}catch(Exception e ) {e.printStackTrace();}
				if(conn != null)try {conn.close();}catch(Exception e ) {e.printStackTrace();}			
		}
		return phoList;
	}
	*/
	//2-1 사진글에서만 서치 개수
	//작성자 , 내용 검색 가져오기
	//검색한 글 갯수 가져오기
	public int getSearchPRevCount(String phoRevSel, String phoRevSearch) {
		int phoRevCount = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn= getConnection();
			String sql = "select count(*) from reviews where "+ phoRevSel +" like '%" + phoRevSearch + "%' and rev_img is not null";
			//select count (*) from board where userID like '%test3@nate.com%';
			pstmt= conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			System.out.println("49번 확인용 출력 phoRevSel + phoRevSearch :"+phoRevSel+ phoRevSearch);
			
			if(rs.next()) {
				phoRevCount = rs.getInt(1);//count(*) 은 결과를 숫자로 가져오며,컬럼명 대신에 컬럼번호1로 꺼내기 
				System.out.println(phoRevCount);
			}
		}catch(Exception e ) {
			e.printStackTrace();
		}finally {
			if(rs != null)try {rs.close();}catch(Exception e ) {e.printStackTrace();}
			if(pstmt != null)try {pstmt.close();}catch(Exception e ) {e.printStackTrace();}
			if(conn != null)try {conn.close();}catch(Exception e ) {e.printStackTrace();}
		}
		return phoRevCount;
	}
	
	//2-2 검색한 사진글 가져오기
	//list 검색한 글들 가져오기 메서드
	public List getSearchPArticles(int startRow, int endRow, String phoRevSel, String phoRevSearch) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List revList = null;
		
		try {
			conn = getConnection();
			String sql ="select A.*, r from (select reviews.*, rownum r from reviews where "+phoRevSel+" like '%"+phoRevSearch+"%')A where r>=? and r<=? and rev_img is not null";
			//select * from board where userID like '%test%';
			pstmt= conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			
			revList = new ArrayList();//list 객체 생성해서 준비
			ReviewDTO searchArticle = null;
			if(rs.next()) {
				do {//
					searchArticle = new ReviewDTO();
					searchArticle.setGroup_num(rs.getInt("group_num"));
					searchArticle.setRev_num(rs.getInt("rev_num"));
					searchArticle.setUserID(rs.getString("userId"));
					searchArticle.setRev_content(rs.getString("rev_content"));
					searchArticle.setRev_img(rs.getString("rev_img"));
					searchArticle.setRev_reg(rs.getTimestamp("rev_reg"));
					revList.add(searchArticle);//리스트에 추가
					
				}while(rs.next());	
			}
		}catch(Exception e ) {
			e.printStackTrace();
		}finally{
			if(rs != null)try {rs.close();}catch(Exception e ) {e.printStackTrace();}
			if(pstmt != null)try {pstmt.close();}catch(Exception e ) {e.printStackTrace();}
			if(conn != null)try {conn.close();}catch(Exception e ) {e.printStackTrace();}
		}
		return revList;
	}
	//테스트용 테테테테테테스트용 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
	public List getTestList(){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List phoList  = null;
		
		try {
		conn = getConnection();
		String sql="select * from reviews where rev_img is not null ";
		pstmt = conn.prepareStatement(sql);
		rs =pstmt.executeQuery();
		
		if(rs.next()) {
			phoList = new ArrayList();
			do {
				ReviewDTO phodto = new ReviewDTO();
				phodto.setRev_num(rs.getInt("rev_num"));;
				phodto.setGroup_num(rs.getInt("group_num"));
				phodto.setUserID(rs.getString("userID"));
				phodto.setRev_content(rs.getString("rev_content"));
				phodto.setRev_img(rs.getString("rev_img"));
				phodto.setRev_reg(rs.getTimestamp("rev_reg"));
				phoList.add(phodto);
				
			}while(rs.next());
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(rs != null)try {rs.close();}catch(Exception e ) {e.printStackTrace();}
			if(pstmt != null)try {pstmt.close();}catch(Exception e ) {e.printStackTrace();}
			if(conn != null)try {conn.close();}catch(Exception e ) {e.printStackTrace();}			
		}
		return phoList;
	}
	
	//최신순으로 정렬 해서 가져오기 시켜 
	public List getNewRev(int group_num) {

			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List laList  = null;
			
			try {
			conn = getConnection();
			String sql="select * from reviews order by rev_reg desc";
			pstmt = conn.prepareStatement(sql);
			rs =pstmt.executeQuery();
			
			laList = new ArrayList();
			ReviewDTO ladto = null;
			if(rs.next()) {
				do {
					ladto = new ReviewDTO();
					ladto.setRev_num(rs.getInt("rev_num"));;
					ladto.setGroup_num(rs.getInt("group_num"));
					ladto.setUserID(rs.getString("userID"));
					ladto.setRev_content(rs.getString("rev_content"));
					ladto.setRev_img(rs.getString("rev_img"));
					ladto.setRev_reg(rs.getTimestamp("rev_reg"));
					laList.add(ladto);
					
				}while(rs.next());
			}
			
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				if(rs != null)try {rs.close();}catch(Exception e ) {e.printStackTrace();}
				if(pstmt != null)try {pstmt.close();}catch(Exception e ) {e.printStackTrace();}
				if(conn != null)try {conn.close();}catch(Exception e ) {e.printStackTrace();}			
			}
			return laList;
	}
	
	/*
	//인기순으로 정렬 해서 가져오기  
	public List getBest(int group_num) {

			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List bestList  = null;
			
			try {
			conn = getConnection();
			String sql="select B.rev_num from (select reviewlikes.rev_num from reviewlikes , reviews where reviews.rev_num = reviewlikes.rev_num and reviews.group_num=?)B group by B.rev_num order by B.rev_num desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, group_num);
			rs =pstmt.executeQuery();

			bestList = new ArrayList();
			if(rs.next()) {
				do {
					bestList.add(rs.getInt(1));
				}while(rs.next());
			}
			//bestList에는 인기순으로 rev_num 번호가 들어있음.
			//sql="select * from reviews where rev_num=?";
			//pstmt=conn.prepareStatement(sql);
			//pstmt.setInt(1, bestList);
			
			//ReviewDTO bestdto = null;
			//for(int i =0; i<bestList.size();i++) {
			//	bestdto=new ReviewDTO();
		
			//}
			
			
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				if(rs != null)try {rs.close();}catch(Exception e ) {e.printStackTrace();}
				if(pstmt != null)try {pstmt.close();}catch(Exception e ) {e.printStackTrace();}
				if(conn != null)try {conn.close();}catch(Exception e ) {e.printStackTrace();}			
			}
	}
	*/
	
	
}

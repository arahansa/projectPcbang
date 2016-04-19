package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import assets.BCrypt;
import assets.DBConnectionMgr;

public class DB_query {
	DBConnectionMgr pool;

	public DB_query() {
		try {
			pool = DBConnectionMgr.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		//System.out.println(checkId("admin"));
		post1000();
	}
	//더미인간 메소드~
	public static void post1000(){
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		DBConnectionMgr pool = null;

		try {
			pool = DBConnectionMgr.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String[] ids={"spider", "pain", "lightuser", "heavyuser", "muktee", "gosu","chickenman"};
		String pass=BCrypt.hashpw("1234", BCrypt.gensalt(12));
		String tel="010-1234-1234";
		String id=null;
		int age=0;
		try {
			con = pool.getConnection();
				
			for(int i=0;i<500;i++){
				// num, id, password, tel, mileage, age
				
				int a= (int)(Math.random()*7);
				age=(int)(Math.random()*100);
				id=ids[a]+i;
				
					
				sql = "insert member(id, password, tel, mileage, age )"
						+ "values(?,?,?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, pass);
				pstmt.setString(3, tel);
				pstmt.setInt(4, 0);
				pstmt.setInt(5, age);
				pstmt.executeUpdate();
				if(i%20==0)
				System.out.println("20명째 등록완료");
			}
			
			System.out.println("500명 등록완료");
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			pool.freeConnection(con, pstmt);
		}
	
	}
	// 아이디 비교 메소드 시작
	public static boolean checkId(String id) {
		DBConnectionMgr pool = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;

		try {
			pool = DBConnectionMgr.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			con = pool.getConnection();
			sql = "select id from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			flag = pstmt.executeQuery().next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
	}

	// 아이디 비교 메소드 종료
	// 로그인 메소드
	public static boolean loginMember(String id, String pass) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String hashPass = "hash_pass 초기값";
		boolean flag = false;
		DBConnectionMgr pool = null;

		try {
			pool = DBConnectionMgr.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			con = pool.getConnection();
			sql = "select password from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// System.out.println("id 값은 " + id);
			rs = pstmt.executeQuery();
			rs.next();
			hashPass = rs.getString("password");
			// System.out.println(hashPass);

			if (BCrypt.checkpw(pass, hashPass))
				flag = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
	}
		
}//DB_query클래스 종료

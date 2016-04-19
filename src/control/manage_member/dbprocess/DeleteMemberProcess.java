package control.manage_member.dbprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;


import assets.DBConnectionMgr;

public class DeleteMemberProcess {

	// 회원 DB에 있는 회원정보를 삭제하는 메소드 시작
	public static void delMember(String id) {

		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		DBConnectionMgr pool = null;

		try {
			pool = DBConnectionMgr.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			con = pool.getConnection();
			sql = "delete from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			pool.freeConnection(con, pstmt);
		}

	}
	// 회원 DB에 있는 회원정보를 삭제하는 메소드 종료

}

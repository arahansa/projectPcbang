package _client.dbprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import _client.assets.DBConnectionMgr;

public class ItemNumCompareProcess {

	// 아이디 비교 메소드 시작
	public static boolean compNum(String item, int num) {
		DBConnectionMgr pool = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		boolean flag = false;
		int comp = 0;

		try {
			pool = DBConnectionMgr.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			con = pool.getConnection();
			sql = "select stock from stock where item=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, item);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				comp = rs.getInt("stock");
			}

			if (num > comp) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;
	}
	// 아이디 비교 메소드 종료

}

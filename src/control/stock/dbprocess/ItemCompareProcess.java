package control.stock.dbprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import assets.DBConnectionMgr;

public class ItemCompareProcess {

	// 재고 조회메소드 시작
	public static boolean compare(String item) {

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		DBConnectionMgr pool = null;
		boolean flag = false;

		try {
			pool = DBConnectionMgr.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			// id, tel, mileage, age
			con = pool.getConnection();
			sql = "select * from stock where item=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, item);
			rs = pstmt.executeQuery();
			flag = rs.next();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			pool.freeConnection(con, pstmt);
		}
		return flag;

	}

}

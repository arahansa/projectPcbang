package control.stock.dbprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;


import assets.DBConnectionMgr;

public class StockChangeProcess {

	// 재고 조회메소드 시작
	public static void StockChange(String item,int val, int index) {

		
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		DBConnectionMgr pool = null;
		String field = null;

		if (index == 0) {
			field = "price";
		} else {
			field = "stock";
		}

		try {
			pool = DBConnectionMgr.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			con = pool.getConnection();
			sql = "update stock set "+field+"=? where item=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, val);
			pstmt.setString(2, item);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			pool.freeConnection(con, pstmt);
		}

	}
	// 재고 조회메소드 종료

}

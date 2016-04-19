package _client.dbprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import _client.assets.DBConnectionMgr;

public class GetPriceProcess {

	// 가격 가져오는 메소드 시작
	public static int getPrice(String item) {
		int price=0;
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
			sql = "select price from stock where item=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, item);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				price = rs.getInt("price");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}

		return price;
	}
	// 가격 가져오는 메소드 종료

}

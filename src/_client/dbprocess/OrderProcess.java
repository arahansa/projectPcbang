package _client.dbprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import _client.assets.BCrypt;
import _client.assets.DBConnectionMgr;

public class OrderProcess {
	
	public static void order(String item, int num) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		DBConnectionMgr pool = null;

		try {
			pool = DBConnectionMgr.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			con = pool.getConnection();
			sql = "update stock set stock=stock-"+num+" where item=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,item);
			pstmt.executeUpdate();
						

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		
	}

}

package control.stock.dbprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;

import assets.DBConnectionMgr;

public class InsertStockProcess {
	
	public static void insertStock(String itemtype, String item, int price, int stock) {
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
			sql = "insert into stock(itemtype,item,price,stock) values (?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, itemtype);
			pstmt.setString(2, item);
			pstmt.setInt(3, price);
			pstmt.setInt(4, stock);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			pool.freeConnection(con, pstmt);
		}
		
	}
}

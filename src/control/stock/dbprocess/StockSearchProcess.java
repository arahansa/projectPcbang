package control.stock.dbprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.table.DefaultTableModel;

import assets.DBConnectionMgr;

public class StockSearchProcess {

	// 재고 조회메소드 시작
	public static void readStock(DefaultTableModel dtm) {

		ResultSet rs = null;
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

			// id, tel, mileage, age
			con = pool.getConnection();
			sql = "select * from stock order by itemtype asc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String itemtype = rs.getString("itemtype");
				String item = rs.getString("item");
				String price = new Integer(rs.getInt("price")).toString();
				String stock = new Integer(rs.getInt("stock")).toString();
				String[] arr = { itemtype, item, price, stock };
				dtm.addRow(arr);

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			pool.freeConnection(con, pstmt);
		}

	}
	// 재고 조회메소드 종료
}

package control.manage_member.dbprocess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.manage_member.MemberInfo;
import assets.DBConnectionMgr;

public class ReadMemberProcess {

	// 회원 DB로 부터 값을 읽어온다
	public static void readMember(ArrayList<MemberInfo> list) {

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

			int num = 0;
			// id, tel, mileage, age
			con = pool.getConnection();
			sql = "select id,tel,mileage,age from member where id<>'admin'";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String tel = rs.getString("tel");
				String mileage = new Integer(rs.getInt("mileage")).toString();
				String age = new Integer(rs.getInt("age")).toString();
				list.add(new MemberInfo(id, tel, mileage, age));
				num++;

			}
			if (num == 0) {
				JOptionPane.showMessageDialog(null, "조회할 데이터가 존재 하지 않습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			pool.freeConnection(con, pstmt);
		}

	}
	// 회원DB에서 값읽어 오는 메소드 종료
	
	
	//특정 회원 정보 불러온다.
	public static MemberInfo readPerson(String id) {

		ResultSet rs = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = null;
		DBConnectionMgr pool = null;
		MemberInfo memberInfo=null;
		
		try {
			pool = DBConnectionMgr.getInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {

			int num = 0;
			// id, tel, mileage, age
			con = pool.getConnection();
			sql = "select id,tel,mileage,age from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String id2 = rs.getString("id");
				String tel = rs.getString("tel");
				String mileage = new Integer(rs.getInt("mileage")).toString();
				String age = new Integer(rs.getInt("age")).toString();
				memberInfo = new MemberInfo(id2, tel, mileage, age);
				num++;
			}
			if (num == 0) {
				JOptionPane.showMessageDialog(null, "조회할 데이터가 존재 하지 않습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt);
		}
		return memberInfo;

	}
	// 회원DB에서 값읽어 오는 메소드 종료
	

}

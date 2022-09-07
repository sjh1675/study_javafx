package hospital.dao.reserv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import hospital.dao.util.DBHelper;
import hospital.vo.ReservVO;

public class ReservDAOImpl implements ReservDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	@Override
	public int reservInsert(ReservVO reserv) {
		int result = 0;
		String sql = "INSERT INTO reserv_tbl VALUES(?, ?, ?)";

		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, reserv.getId());
			pstmt.setString(2, reserv.getName());
			pstmt.setTimestamp(3, reserv.getTime());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("reservInsert 오류");
		} finally {
			DBHelper.close(pstmt);
		}
		return result;
	}

	@Override
	public ReservVO reservSelectId(String id) {
		ReservVO rUser = null; 
		String sql = "SELECT * FROM reserv_tbl WHERE rId = ?";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();			
			if (rs.next()) {
				rUser = new ReservVO(rs.getString(1), rs.getString(2), rs.getTimestamp(3));				
			}
		} catch (SQLException e) {
			System.out.println("reservSelectId 오류");
		} finally {
			DBHelper.close(rs, pstmt);
		}		
		return rUser;
	}

	@Override
	public ArrayList<ReservVO> reservSelectDay(Timestamp time) {
		ArrayList<ReservVO> rUsers = new ArrayList<>();
	    String day =  time.toString().split(" ")[0] + "%";
		String sql = "SELECT * FROM reserv_tbl WHERE rTime LIKE ?";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, day);
			rs = pstmt.executeQuery();			
			while (rs.next()) {
				ReservVO rUser = new ReservVO(rs.getString(1), rs.getString(2), rs.getTimestamp(3));
				rUsers.add(rUser);
			}
		} catch (SQLException e) {
			System.out.println("reservSelectDay 오류");
		} finally {
			DBHelper.close(rs, pstmt);
		}		
		return rUsers;
	}
	
	@Override
	public ArrayList<ReservVO> reservSelectAll() {
		ArrayList<ReservVO> rUsers = new ArrayList<>();
		String sql = "SELECT * FROM reserv_tbl";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();			
			while (rs.next()) {
				ReservVO rUser = new ReservVO(rs.getString(1), rs.getString(2), rs.getTimestamp(3));
				rUsers.add(rUser);
			}
		} catch (SQLException e) {
			System.out.println("reservSelectAll 오류");
		} finally {
			DBHelper.close(rs, pstmt);
		}		
		return rUsers;
	}
}

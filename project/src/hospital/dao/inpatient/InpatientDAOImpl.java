package hospital.dao.inpatient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import hospital.dao.util.DBHelper;
import hospital.vo.InpatientVO;

public class InpatientDAOImpl implements InpatientDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	@Override
	public ArrayList<InpatientVO> inpSelectAll() {
		ArrayList<InpatientVO> pUsers = new ArrayList<>();
		String sql = "SELECT * FROM inpatient_tbl";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				InpatientVO pUser = new InpatientVO(rs.getString(1), rs.getString(2), rs.getTimestamp(3),
						rs.getString(4));
				pUsers.add(pUser);
			}
		} catch (SQLException e) {
			System.out.println("inpSelectAll 오류");
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return pUsers;
	}

	@Override
	public int inpUser(InpatientVO inpUser) {
		int result = 0;
		String sql = "INSERT INTO inpatient_tbl VALUES(?, ?, ?, ?)";

		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, inpUser.getId());
			pstmt.setString(2, inpUser.getName());
			pstmt.setTimestamp(3, inpUser.getTime());
			pstmt.setString(4, inpUser.getRoom());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("inpUser 오류");
		} finally {
			DBHelper.close(pstmt);
		}
		return result;
	}

	@Override
	public ArrayList<InpatientVO> inpSelectDay(Timestamp time) {
		ArrayList<InpatientVO> pUsers = new ArrayList<>();
		String day = time.toString().split(" ")[0] + "%";
		String sql = "SELECT * FROM inpatient_tbl WHERE pTime LIKE ?";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, day);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				InpatientVO pUser = new InpatientVO(rs.getString(1), rs.getString(2), rs.getTimestamp(3),
						rs.getString(4));
				pUsers.add(pUser);
			}
		} catch (SQLException e) {
			System.out.println("inpSelectDay 오류");
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return pUsers;
	}

	@Override
	public InpatientVO inpSelectId(String id) {
		InpatientVO pUser = null;
		String sql = "SELECT * FROM inpatient_tbl WHERE pId = ?";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pUser = new InpatientVO(rs.getString(1), rs.getString(2), rs.getTimestamp(3), rs.getString(4));
			}
		} catch (SQLException e) {
			System.out.println("inpSelectId 오류");
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return pUser;
	}

	@Override
	public int outpUser(String id) {
		int result = 0;
		String sql = "DELETE FROM inpatient_tbl WHERE pId = ?";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("outpUser 오류");
		} finally {
			DBHelper.close(pstmt);
		}
		return result;
	}
}

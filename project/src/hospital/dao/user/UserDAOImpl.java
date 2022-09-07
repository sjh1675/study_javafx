package hospital.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import hospital.dao.util.DBHelper;
import hospital.vo.UserVO;

public class UserDAOImpl implements UserDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	@Override
	public ArrayList<UserVO> selectAll() {
		ArrayList<UserVO> users = new ArrayList<>();
		String sql = "SELECT * FROM user_tbl";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();			
			while (rs.next()) {
				UserVO user = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				users.add(user);
			}
		} catch (SQLException e) {
			System.out.println("selectAll 오류");
		} finally {
			DBHelper.close(rs, pstmt);
		}		
		return users;
	}
		
	@Override
	public UserVO userSelect(String uId) {
		UserVO user = null;
		String sql = "SELECT * FROM user_tbl WHERE uId = ?";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}			
		} catch (SQLException e) {
			System.out.println("userSelect 오류");
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return user;
	}
	
	@Override
	public int userJoin(UserVO user) {
		int result = 0;
		String sql = "INSERT INTO user_tbl VALUES(?, ?, ?, ?, ?)";

		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getRegNum());
			pstmt.setString(5, user.getPhoneNum());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("userJoin 오류");
		} finally {
			DBHelper.close(pstmt);
		}
		return result;
	}

	@Override
	public int userUpdate(UserVO user) {
		int result = 0;
		String sql = "UPDATE user_tbl SET uPassword = ?, uPhoneNum = ? WHERE uId = ?"; 
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getPhoneNum());
			pstmt.setString(3, user.getId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("userUpdate 오류");
		} finally {
			DBHelper.close(pstmt);
		}
		return result;
	}

	@Override
	public int userDelete(String uId) {
		int result = 0;
		String sql = "DELETE FROM user_tbl WHERE uId = ?";
		
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uId);
			result = pstmt.executeUpdate();
			} catch (SQLException e) {
			System.out.println("userDelete 오류");
		} finally {
			DBHelper.close(rs, pstmt);			
		}
		return result;
	}
	
	
	
	
	
	@Override
	public UserVO userLogin(String uId, String uPw) {
		UserVO user = null;
		String sql = "SELECT * FROM user_tbl WHERE uId = ? AND uPassword = ?";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uId);
			pstmt.setString(2, uPw);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}
		} catch (SQLException e) {
			System.out.println("userLogin(id, pw) 오류");
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return user;
	}
	
	@Override
	public UserVO userLogin(UserVO userVO) {
		UserVO user = null;
		String sql = "SELECT * FROM user_tbl WHERE uId = ? AND uPassword = ?";
		try {
			conn = DBHelper.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userVO.getId());
			pstmt.setString(2, userVO.getPassword());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				user = new UserVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
			}			
		} catch (SQLException e) {
			System.out.println("userLogin(userVO) 오류");
		} finally {
			DBHelper.close(rs, pstmt);
		}
		return user;
	}
}
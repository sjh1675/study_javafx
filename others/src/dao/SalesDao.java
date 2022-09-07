package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;

import application.Main;
import dto.Sales;

public class SalesDao {
	private Connection con; // DB연동시 사용되는 클래스 : DB연동클래스
	private PreparedStatement ps; // 연결된 DB내 SQL 조작 할때 사용되는 인터페이스 : DB조작인터페이스
	private ResultSet rs; // 결과물을 조작하는 인터페이스 
	
	public static SalesDao salesDao = new SalesDao(); // DB 연동 객체;
	
	public SalesDao() {
		try {
			// DB연동 
			Class.forName("com.mysql.cj.jdbc.Driver"); // 1. DB 드라이버 가져오기
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx?serverTimezone=UTC",
					"root","1234"); // 2. DB 주소 연결 
		}
		catch(Exception e ) { System.out.println( "[DB 연동 오류]"+e  ); }
	}
	//메소드
	public void logadd(Sales sales) {
		try {
			Timestamp time = Timestamp.valueOf(sales.getSdate().toLocalDate().atStartOfDay()); // db date 타임 맞게 시간뽑기
			String sql = "insert into sales(sdate,cnum,sprice)values(?,?,?)";
			ps = con.prepareStatement(sql);
			ps.setTimestamp(1, time);
			ps.setInt(2, sales.getCnum());
			ps.setInt(3, sales.getSprice());
			ps.executeLargeUpdate();
		} catch (Exception e) {
			System.out.println("[sql 오류]" + e);
		}
	}
}
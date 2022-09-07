package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import controller.Main;
import dto.Category;
import dto.Machine;
import dto.temptable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CategoryDao {
	private Connection con; // DB연동시 사용되는 클래스 : DB연동클래스
	private PreparedStatement ps; // 연결된 DB내 SQL 조작 할때 사용되는 인터페이스 : DB조작인터페이스
	private ResultSet rs; // 결과물을 조작하는 인터페이스 
	
	public static CategoryDao categoryDao = new CategoryDao(); // DB 연동 객체;
	
	public CategoryDao() {
		try {
			// DB연동 
			Class.forName("com.mysql.cj.jdbc.Driver"); // 1. DB 드라이버 가져오기
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx?serverTimezone=UTC",
					"root","1234"); // 2. DB 주소 연결 
		}
		catch(Exception e ) { System.out.println( "[DB 연동 오류]"+e  ); }
	}
	// Category불러오기
	public Category load(int mnum) {
		try {
			String sql = "select * from category where mnum=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, mnum);
			rs = ps.executeQuery();
			Category category = null;
			if(rs.next()) {
				Category temp = new Category(	
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getString(4),
						rs.getInt(5)	
						);
			category = temp;
			}
			return category;
		} catch (Exception e) {
			System.out.println("[sql 에러]" + e);
		}
		return null;
	}
	
	public void ch(int mnum) {
		
		
		try {
			String sql="update category set cname= ? where mnum=?";
			System.out.println(mnum);
			String ss=Main.main.temptable.getCname()+"이용중";
			System.out.println(ss);
			ps = con.prepareStatement(sql);
			ps.setString(1, ss);
			ps.setInt(2, mnum);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void ch2(int mnum) {
		
		
		try {
			String sql="update category set cname= ? where mnum=?";
			System.out.println(mnum);
			boolean asd11=Main.main.temptable.getCname().equals("중형세탁기이용중");
			boolean asd12=Main.main.temptable.getCname().equals("중형건조기이용중");
			boolean asd13=Main.main.temptable.getCname().equals("대형세탁기이용중");
			boolean asd14=Main.main.temptable.getCname().equals("대형건조기이용중");
			System.out.println(asd11);
			System.out.println(asd12);
			System.out.println(asd13);
			System.out.println(asd14);
			ps = con.prepareStatement(sql);
			String ss="";
			if(asd11) {
				ss="중형세탁기";
			}
			if(asd12) {
				ss="중형건조기";
			}
			if(asd13) {
				ss="대형세탁기";
			}
			if(asd14) {
				ss="대형건조기";
			}
			ps.setString(1, ss);
			ps.setInt(2, mnum);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void ch3(int mnum) {
		
		
		try {
			String sql="update machine set mphone= ? where mnum=?";
			ps = con.prepareStatement(sql);
			String ss="0000-0000";
			ps.setString(1, ss);
			ps.setInt(2, mnum);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public int load2(int mnum) {
		int qwer=0;
		try {
			String sql = "select * from category where mnum=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, mnum);
			rs = ps.executeQuery();
			Category category = null;
			if(rs.next()) {
				Category temp = new Category(	
						rs.getInt(1),
						rs.getInt(2),
						rs.getInt(3),
						rs.getString(4),
						rs.getInt(5)	
						);
			category = temp;
			}
			qwer=category.getCprice();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return qwer;
		
	}
/////////////////////////////////////////////////////////	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ObservableList<temptable> list(String a) {
			ObservableList<temptable> tempList100 = FXCollections.observableArrayList();
			ObservableList<temptable> tempList99 = FXCollections.observableArrayList();
			ObservableList<temptable> tempList98 = FXCollections.observableArrayList();
			
			/////////////////////////////////////////////////////////////////////
			try {

				
				
				
				
				////////////////////////////////////////


				String	sql = "select * from machine where mphone=\""+a+"\" order by mnum";
						
						ps = con.prepareStatement(sql);
					
						rs = ps.executeQuery();
					
					
				
				
				System.out.println(sql);
				while(rs.next()) {
					temptable temp1 = new temptable(rs.getInt(1));
					System.out.println(rs.getInt(1));
					tempList98.add(temp1);

				}
		///////////////////////////////////////////////////////////////////////////////////		
				 
				
				 for(int g=0; g<tempList98.size();g++) {
					 
					 sql = "select * from category where mnum ="+tempList98.get(g).getMnum()+" order by mnum";
					 System.out.println(tempList98.get(g).getMnum());
						ps = con.prepareStatement(sql);
					
						rs = ps.executeQuery();

					
				
				
				
					while(rs.next()) {
						temptable temp2 = new temptable(rs.getString(4));
						
						tempList99.add(temp2);
						System.out.println(rs.getString(4));
					}
				 }
				
				
				/////////////////////////////////////////////////
				System.out.println(tempList99.size());
				for(int h=0; h<tempList99.size();h++) {
				temptable temp = new temptable(tempList98.get(h).getMnum(),tempList99.get(h).getCname());
				if(tempList99.get(h).getCname().equals("중형세탁기")) {break;}
				if(tempList99.get(h).getCname().equals("중형건조기")) {break;}
				if(tempList99.get(h).getCname().equals("대형세탁기")) {break;}
				if(tempList99.get(h).getCname().equals("대형건조기")) {break;}
				tempList100.add(temp);
				System.out.println(temp);
				}
				////////////////////////////////////////
			
				return tempList100;
			}catch (Exception e) {

			}
		
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////
}
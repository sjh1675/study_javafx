package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dto.Machine;
import dto.temptable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MachineDao {
	private Connection con; // DB연동시 사용되는 클래스 : DB연동클래스
	private PreparedStatement ps; // 연결된 DB내 SQL 조작 할때 사용되는 인터페이스 : DB조작인터페이스
	private ResultSet rs; // 결과물을 조작하는 인터페이스 
	
	
public static MachineDao machinedao = new MachineDao(); // DB 연동 객체;
	
	public MachineDao() {
		try {
			// DB연동 
			Class.forName("com.mysql.cj.jdbc.Driver"); // 1. DB 드라이버 가져오기
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx?serverTimezone=UTC",
					"root","1234"); // 2. DB 주소 연결 
		}
		catch(Exception e ) { System.out.println( "[DB 연동 오류]"+e  ); }
	}
	//머신 저장
	public boolean update(temptable tb) {
        LocalDateTime dateTime =  LocalDateTime.now().plusHours(9);
        Timestamp timestamp = Timestamp.valueOf(dateTime);
		try {
			String sql = "update machine set mphone=?,mtemperature=?,mdegree=?,mtime=? where mnum=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, tb.getMphone());
			ps.setString(2, tb.getMtemperature());
			ps.setString(3, tb.getMdegree());
			ps.setTimestamp(4,timestamp);
			ps.setInt(5, tb.getMnum());
			ps.executeUpdate();
			return true;
		} catch (Exception e) {
			System.out.println("[sql 에러]" + e);
		}
		return false;
	}
	
	//머신 불러오기
	public Machine load(int mnum) {
		try {
			String sql = "select * from machine where mnum=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, mnum);
			rs = ps.executeQuery();
			Machine machine = null;
			if(rs.next()) {
				LocalDateTime localDate1 = rs.getTimestamp(6).toLocalDateTime().minusHours(9);// LocalDateTime <=> Timestamp   
			Machine temp = new Machine(	
						rs.getInt(1),
						rs.getInt(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						localDate1,
						0
						);
			machine = temp;
			}
			return machine;
		} catch (Exception e) {
			System.out.println("[sql 에러]" + e);
		}
		return null;
	}
	
	public ObservableList<temptable> list(String a) {
		if(a.equals("중형세탁기")||a.equals("대형세탁기")) {
			ObservableList<temptable> tempList = FXCollections.observableArrayList();
			ObservableList<temptable> tempList1 = FXCollections.observableArrayList();
			ObservableList<temptable> tempList2 = FXCollections.observableArrayList();
			/////////////////////////////////////////////////////////////////////
			try {

				
				
				
				
				////////////////////////////////////////


				String	sql = "select * from category where cname=\""+a+"\" order by cnum";
						
						ps = con.prepareStatement(sql);
					
						rs = ps.executeQuery();
					
					
				
				
				
				while(rs.next()) {
					temptable temp2 = new temptable(rs.getInt(2),rs.getString(4));
					
					tempList2.add(temp2);

				}
		///////////////////////////////////////////////////////////////////////////////////		
				 
				
				 for(int g=0; g<tempList2.size();g++) {
					 
					 sql = "select * from machine where mamount is not null and mnum ="+tempList2.get(g).getMnum()+" order by mnum";

						ps = con.prepareStatement(sql);
					
						rs = ps.executeQuery();

					
				
				
				
				while(rs.next()) {
					temptable temp1 = new temptable(rs.getInt(1)
							,rs.getInt(2));
					
					tempList1.add(temp1);
				
				}
				 }
				
				
				/////////////////////////////////////////////////
				
				for(int h=0; h<tempList1.size();h++) {
				temptable temp = new temptable(tempList1.get(h).getMnum(),tempList2.get(h).getCname(),tempList1.get(h).getMamount());
				
				tempList.add(temp);
				}
				////////////////////////////////////////
			
				return tempList;
			}catch (Exception e) {

			}
		}
		if(a.equals("중형건조기")||a.equals("대형건조기")) {
			ObservableList<temptable> tempList4 = FXCollections.observableArrayList();
			ObservableList<temptable> tempList5 = FXCollections.observableArrayList();
			ObservableList<temptable> tempList6 = FXCollections.observableArrayList();
			
				try {

				
				
				
				
				////////////////////////////////////////


				String	sql = "select * from category where cname=\""+a+"\" order by cnum";
						
						ps = con.prepareStatement(sql);
					
						rs = ps.executeQuery();
					
					
				
				
				
				while(rs.next()) {
					temptable temp2 = new temptable(rs.getInt(2),rs.getString(4));
					
					tempList6.add(temp2);

				}
		///////////////////////////////////////////////////////////////////////////////////		
				 
				
				 for(int g=0; g<tempList6.size();g++) {
					 
					 sql = "select * from machine where mamount is null and mnum ="+tempList6.get(g).getMnum()+" order by mnum";

						ps = con.prepareStatement(sql);
					
						rs = ps.executeQuery();

					
				
				
				
				while(rs.next()) {
					temptable temp1 = new temptable(rs.getInt(1)
							,rs.getInt(2));
					
					tempList5.add(temp1);
				
				}
				 }
				
				
				/////////////////////////////////////////////////
				
				for(int h=0; h<tempList5.size();h++) {
				temptable temp = new temptable(tempList5.get(h).getMnum(),tempList6.get(h).getCname(),tempList5.get(h).getMamount());
				
				tempList4.add(temp);
				}
				////////////////////////////////////////
			
				return tempList4;
			}catch (Exception e) {

			}
		}
		
		
		
		return null;
	}
}
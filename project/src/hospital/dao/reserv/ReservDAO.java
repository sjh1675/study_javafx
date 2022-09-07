package hospital.dao.reserv;

import java.sql.Timestamp;
import java.util.ArrayList;

import hospital.vo.ReservVO;

public interface ReservDAO {

	// 사용자가 예약 정보를 입력(insert)
	int reservInsert(ReservVO reserv);
	
	// 관리자가 사용자의 예약 정보가 있는지 아이디로 확인(select)
	ReservVO reservSelectId(String id);
	
	// 관리자가 특정 날을 기준으로 예약 정보를 확인(select)
	// !!주의 : 반드시 가공된 시간을 사용할 것 
	ArrayList<ReservVO> reservSelectDay(Timestamp time);
	
	// 관리자/사용자/프로그램 자체가 모든 예약 정보를 알고 싶을 때 사용(select)
	ArrayList<ReservVO> reservSelectAll();
	
	
}
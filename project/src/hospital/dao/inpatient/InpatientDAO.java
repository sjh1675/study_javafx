package hospital.dao.inpatient;

import java.sql.Timestamp;
import java.util.ArrayList;

import hospital.vo.InpatientVO;


public interface InpatientDAO {
	
	
	// 입원 회원 목록 (관리자 + 사용자 / select)
	ArrayList<InpatientVO> inpSelectAll();
	
	// 환자가 입원(insert)
	int inpUser(InpatientVO inpUser);

	// 회원이 입원했는지 검색(관리자 / select)
	InpatientVO inpSelectId(String id);
	
	// 특정한 날을 기준으로 입원한 환자 목록 (관리자 / select)
	ArrayList<InpatientVO> inpSelectDay(Timestamp time);
	
	// 퇴원 !
	int outpUser(String id);
	
}
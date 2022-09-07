package hospital.dao.user;

import java.util.ArrayList;

import hospital.vo.UserVO;

public interface UserDAO { // 유저가 쓰는 DB의 의미가 아니라, 유저 VO를 다루는 곳
	
		// 회원 가입(insert)
		int userJoin(UserVO user);
		
		// 회원 한명 보기 / 아이디 중복 확인(select)
		UserVO userSelect(String id);
		
		// 회원 수정(update)
		int userUpdate(UserVO userVO);
				
		// 회원 삭제(delete)
		int userDelete(String id);
		
		//// 로그인 (아이디와 비밀번호로 DB 참조 / select)
		
		UserVO userLogin(String id, String pw);
		UserVO userLogin(UserVO user); // 위의 메소드와 기능이 동일하고 매개변수만 VO로 받음 아무거나 선택하여 사용
				
		// 회원 정보 보기(관리자 / select)
		ArrayList<UserVO> selectAll();
		
}
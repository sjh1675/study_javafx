package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import dao.CategoryDao;
import dao.MachineDao;
import dao.SalesDao;
import dto.Category;
import dto.Machine;
import dto.Sales;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Payment implements Initializable{
	@FXML
	private Label temperature;

	@FXML
	private Label mdegree;
	
	@FXML
	private Label catagroy;
	 
	@FXML
	private Label mamount;

	@FXML
	private Button btnhome;

	@FXML
	private Button btnaddmoney;

	@FXML
	private Button btnpaymentend;

    @FXML
    private Button back99;

    @FXML
    void accback99(ActionEvent event) {
    	
    	Main.main.loadpage("/view/user/5번페이지(세탁옵션 선택) 상단.fxml");
		Main.main.loadpage2("/view/user/5번페이지(세탁옵션선택)하단.fxml");
    }
	boolean pass = false; // 금액 충족 판단
	@FXML
	void addmoney(ActionEvent event) {
		
	    if(Main.main.temptable.getWholeprice() > Main.main.temptable.getInputprice()) {//투입금액 충족시
	    	Main.main.temptable.setInputprice(Main.main.temptable.getInputprice() + 500); //500원추가
	    	Main.main.loadpage2("/view/user/6번페이지(결제창)하단.fxml"); // 버튼눌르때마다 새로고침
	    	System.out.println(Main.main.temptable.getInputprice());
	    }else { // 투입금액 충족시
	    	pass = true;
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setHeaderText("투입금액에 충족했습니다. 결제하시길바립니다.");
	    	alert.show();
	    }
	}

	@FXML
	void home(ActionEvent event) {
		Main.main.loadpage2("/view/user/1번페이지(메인).fxml");	// 잘려서 아래 추가 load 사용
		Main.main.temptable.setInputprice(0); // 금액초기화
	}

	@FXML
	void paymentend(ActionEvent event) {
		if(pass) { // 투입금액 충족여부 가 true이면
			LocalDateTime date = LocalDateTime.now();
			//db에 집어넣기
			boolean pass = MachineDao.machinedao.update(Main.main.temptable);
			if(pass) {
				//매출 기록


		    	Category category = CategoryDao.categoryDao.load(Main.main.temptable.getMnum());
		    	
		    	Sales sales = new Sales(0, date, category.getCnum(), category.getCprice());
				SalesDao.salesDao.logadd(sales);
				CategoryDao.categoryDao.ch(Main.main.temptable.getMnum());
		    	//로드
		    	Main.main.loadpage2("/view/user/7페이지(영수증).fxml");
			}else {
				Alert alert = new Alert(AlertType.INFORMATION);
		    	alert.setHeaderText("오류 관리자 호출바람[010-123-1234]");
		    	alert.show();
		    	Main.main.loadpage2("/view/user/1번페이지(메인).fxml");
			}		
		}else {
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setHeaderText("금액에 맞게 충분히 돈을 넣어주세요");
	    	alert.show();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		temperature.setText(Main.main.temptable.getMtemperature()); //온도설정
		mamount.setText(Integer.toString(Main.main.temptable.getWholeprice())); // 총합가격
		mdegree.setText(Main.main.temptable.getMdegree()); //세기설정
		catagroy.setText(Main.main.temptable.getCname()); // catagroy1 넣기	
	}
}
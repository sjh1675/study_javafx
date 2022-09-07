package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import dao.CategoryDao;
import dao.MachineDao;
import dto.Category;
import dto.Machine;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Receipt implements Initializable{
	 	@FXML
	    private Button btnhome;

	    @FXML
	    private Button btnhome2;

	    @FXML
	    private Label phone;

	    @FXML
	    private Label category1;

	    @FXML
	    private Label temperature;

	    @FXML
	    private Label drgree;

	    @FXML
	    private Label starttime;

	    @FXML
	    private Label endtime;

	    @FXML
	    private Label amoment;

	    @FXML
	    void home(ActionEvent event) {
	    	Main.main.loadpage2("/view/user/1번페이지(메인).fxml");
	    	Main.main.temptable.setInputprice(0); // 금액 초기화
	    }
	    @FXML
	    void home2(ActionEvent event) {
	    	Main.main.loadpage2("/view/user/1번페이지(메인).fxml");
	    	Main.main.temptable.setInputprice(0); // 금액 초기화
	    }
	    @Override
	    public void initialize(URL arg0, ResourceBundle arg1) {
	    	//db로 꺼내오기
	    		//머신
	    	Machine machine = MachineDao.machinedao.load(Main.main.temptable.getMnum());
	    		//카테고리
	    	Category category = CategoryDao.categoryDao.load(Main.main.temptable.getMnum());
	    	//예외처리
	    	if (machine != null) {
	    		//레이블 수정
		    	phone.setText(machine.getMphone());
		    	//category.setText();
		    	temperature.setText(machine.getMtemperature());
		    	drgree.setText(machine.getMdegree());
		    	//시간 변환
		    	String stime = machine.getMtime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		    	starttime.setText(stime);
		    	//종료시간 [세탁 40분 건조기 30분]
		    		//종료시간 계산
		    	category1.setText(Main.main.temptable.getCname());
		    	if(category.getCname().contains("대형")) {
		    		LocalDateTime etime = machine.getMtime().plusMinutes(40); // 40분 더하기 
		    		String etimes = etime.format(DateTimeFormatter.ofPattern("HH:mm"));
		    		endtime.setText(etimes);
		    	}else if(category.getCname().contains("중형")) {
		    		LocalDateTime etime = machine.getMtime().plusMinutes(30);// 30분 더하기
		    		String etimes = etime.format(DateTimeFormatter.ofPattern("HH:mm"));
		    		endtime.setText(etimes);
		    	}
		    	amoment.setText(Integer.toString(category.getCprice()));
	    	}else {
	    		Alert alert = new Alert(AlertType.INFORMATION);
	    		alert.setHeaderText("하하하하하하 넌 빨래에 실패했다");
	    		alert.show();
	    	}
	    	
	    }
}
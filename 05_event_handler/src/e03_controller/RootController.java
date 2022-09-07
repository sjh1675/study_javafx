package e03_controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class RootController implements Initializable {

	@FXML private Button btn1;
	@FXML private Button btn2;
	@FXML private Button btn3;
	
	public RootController() {
		System.out.println("RootController 생성자 호출");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("initialize 호출");
		System.out.println("FXML 위치 정보 : " + location);
		
		btn1.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				System.out.println("버튼 1 클릭");
			}
		});
		btn2.setOnAction(event -> System.out.println("람다식으로 구현한 버튼 2 클릭!!"));
	}
	
	
	
}
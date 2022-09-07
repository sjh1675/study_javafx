package controller.machine;

import java.net.URL;
import java.util.ResourceBundle;

import controller.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class Choose implements Initializable{

    @FXML
    private Button btn2;

    @FXML
    private Button btn1;

    @FXML
    private Button btnhome;
    @FXML
    private Button back99;
    @FXML
    void acc1(ActionEvent event) {
    	Main.main.temptable.setCname("세탁기");
    	Main.main.loadpage2("/view/user/4번페이지(세탁기선택)미완성.fxml");
    }
    @FXML
    void accback99(ActionEvent event) {
    	Main.main.temptable.setMphone("0000-0000");
    	 Main.main.loadpage("/view/user/2페이지상단(전화번호).fxml");
		 Main.main.loadpage2("/view/wash/빨래전화번호하단.fxml");

    }
    @FXML
    void acc2(ActionEvent event) {
    	Main.main.temptable.setCname("건조기");
    	Main.main.loadpage2("/view/user/4번페이지(세탁기선택)미완성.fxml");
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
    @FXML
    void acchome(ActionEvent event) {
    	Main.main.loadpage2("/view/user/1번페이지(메인).fxml");
    }
}

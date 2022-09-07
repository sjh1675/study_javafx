package hospital.controller.user.data;

import static hospital.view.user.UserLoaderFactory.USER_EDIT;
import static hospital.view.user.UserLoaderFactory.USER_HOME;

import java.net.URL;
import java.util.ResourceBundle;

import hospital.controller.user.User_Home;
import hospital.controller.util.Changable;
import hospital.controller.util.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.Pane;

public class User_PwChk implements Initializable, Changable {

	@FXML private PasswordField pwChk_pw;
	@FXML private Button pwChk_enter;
	
	private User_Home homeCont = USER_HOME.getController();
	private Pane edit;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pwChk_pw.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				pwChk_enter.fire();
			}
		});
		
		pwChk_enter.setOnAction(e -> {
			String pwChk = pwChk_pw.getText();
			if (homeCont.getLoginUser().getPassword().equals(pwChk)) { // 일치
				
				Controllers.switchView(edit, homeCont);
			} else {
				Controllers.alt("비밀번호 불일치", "비밀번호과 일치하지 않습니다. 다시 확인해주세요.", AlertType.INFORMATION);
			}
			
			User_Edit cont = USER_EDIT.getController();
			cont.initData();
		});
	}
	
	@Override
	public void loadView() {
		edit = homeCont.getEdit();
	}
}

package hospital.controller.user.data;

import static hospital.view.user.UserLoaderFactory.USER_HOME;
import static hospital.view.user.UserLoaderFactory.USER_JOIN;

import java.net.URL;
import java.util.ResourceBundle;

import hospital.controller.user.User_Home;
import hospital.controller.util.Changable;
import hospital.controller.util.Controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class User_Login implements Initializable, Changable {
	@FXML
	private Button login_enter;
	@FXML
	private Button login_toJoin;
	@FXML
	private TextField login_id;
	@FXML
	private PasswordField login_pw;

	private final User_Home homeCont = USER_HOME.getController();
	private Pane join, main;
	private boolean loginCheck;
	private boolean duplCheck;

	public void setLoginCheck(boolean loginCheck) {
		this.loginCheck = loginCheck;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		login_toJoin.setOnAction(e -> {
			User_Join cont = USER_JOIN.getController();
			cont.getJoin_pw_warning().setVisible(false);
			homeCont.switchView(join);
		});

		login_id.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				login_pw.requestFocus();
			}
		});

		login_pw.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				login_enter.fire();
			}
		});

		login_enter.setOnAction(e -> {
			String id = login_id.getText();
			String pw = login_pw.getText();
			if (id.contains("$") || id.contains(",") || pw.contains("$") || pw.contains(",")) {
				Controllers.alt("경고!", "사용할 수 없는 문자가 포함되어 있습니다!", AlertType.WARNING);
				return;
			}

			if (!id.equals("") && !pw.equals("")) { // 변경
				homeCont.helper.send("$로그인," + id + "," + pw);
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if (loginCheck && duplCheck) {
				loginCheck = false;
				Controllers.alt("로그인 완료", "환영합니다 ^^ 최고의 서비스를 자랑하는 영남병원입니다!", AlertType.INFORMATION);
				homeCont.switchView(main);
				homeCont.helper.userSet();
			} else if (!loginCheck){
				Controllers.alt("로그인 실패", "아이디 혹은 비밀번호를 잘못 입력하셨습니다.\n다시 확인해주세요.", AlertType.WARNING);
				login_id.requestFocus();
				return;
			} else if (!duplCheck) {
				Controllers.alt("중복 로그인", "이미 로그인 중인 아이디입니다.\n다시 확인해주세요.", AlertType.WARNING);
				login_id.requestFocus();
				return;
			}
		});
	}

	@Override
	public void loadView() {
		join = homeCont.getJoin();
		main = homeCont.getMain();
	}

	public void setDuplCheck(boolean duplCheck) {
		this.duplCheck = duplCheck;
	}

}
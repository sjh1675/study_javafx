package hospital.controller.user.chat;

import static hospital.view.user.UserLoaderFactory.USER_HOME;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import hospital.controller.user.User_Home;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class User_Chat implements Initializable {

	@FXML
	public TextArea chat_text_room;
	@FXML
	TextField chat_text_in;
	@FXML
	Button chat_enter;
	private User_Home homeCont = USER_HOME.getController();
	private String tempName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			tempName = InetAddress.getLocalHost().getHostName();
		} catch (Exception e1) {
			tempName = "비회원";
		}

		chat_enter.setOnAction(e -> {
			String message = chat_text_in.getText().trim();
			if (message.equals("")) {
				return;
			} else if (message.contains("$중복체크") || message.contains("$회원가입") || message.contains("$회원가입")
					|| message.contains("$로그인") || message.contains("$회원정보수정") || message.contains("$회원탈퇴")
					|| message.contains("$날짜확인") || message.contains("$진료예약")) {
				chat_text_in.clear();
				Platform.runLater(() -> chat_text_room.appendText("!사용할 수 없는 메세지가 포함되어 있습니다!\n"));
				return;
			}

			if (homeCont.getLoginUser() == null) {
				homeCont.helper.send(tempName + " : " + message);
			} else {
				homeCont.helper.send(homeCont.getLoginUser().getName() + " : " + message);
			}
			chat_text_in.clear();
		});

		chat_text_in.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				chat_enter.fire();
			}
		});

	}

}

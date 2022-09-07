package hospital.controller.admin.chat;

import static hospital.view.admin.AdminLoaderFactory.ADMIN_HOME;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import hospital.controller.admin.Admin_Home;
import hospital.network.admin.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class Admin_Chat implements Initializable {

	@FXML
	public TextArea chat_text_room;
	@FXML
	TextField chat_text_in;
	@FXML
	Button chat_enter;
	Admin_Home homeCont = ADMIN_HOME.getController();
	int index; // 어떤 회원에게 메세지를 보낼지 선택
	@FXML
	ComboBox<String> chat_list;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		update();
		chat_list.getSelectionModel().selectedIndexProperty().addListener((t, o, n) -> index = n.intValue());

		chat_enter.setOnAction(e -> {
			String message = chat_text_in.getText().trim();
			if (message.equals("")) {
				return;
			} else if (message.contains("$중복아님") || message.contains("$중복이다") || message.contains("$회원가입성공")
					|| message.contains("$회원가입실패") || message.contains("$로그인성공") || message.contains("$로그인실패")
					|| message.contains("$회원정보수정완료") || message.contains("$회원정보수정실패") || message.contains("$날짜확인")
					|| message.contains("$예약성공") || message.contains("$예약실패")) {
				chat_text_in.clear();
				Platform.runLater(() -> chat_text_room.appendText("!사용할 수 없는 메세지가 포함되어 있습니다!\n"));
				return;
			}

			if (index <= 0) {
				Platform.runLater(() -> chat_text_room.appendText("[전체알림] " + message + "\n"));
				Vector<Client> list = homeCont.helper.clients;
				for (Client c : list) {
					c.send("[전체알림] " + message);
				}
			} else {
				Platform.runLater(
						() -> chat_text_room.appendText("[" + chat_list.getValue() + "님에게]" + message + "\n"));
				homeCont.helper.clients.get(index - 1).send("[관리자] " + message);
			}
			chat_text_in.clear();
		});

		chat_text_in.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				chat_enter.fire();
			}
		});
	}

	public void update() {
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("전체 회원");
		Vector<Client> clientList = homeCont.helper.clients;
		for (Client c : clientList) {
			if (c.user == null) {
				list.add("비회원");
			} else {
				list.add(c.user.getName());
			}
		}
		Platform.runLater(() -> chat_list.setItems(list));
	}

}

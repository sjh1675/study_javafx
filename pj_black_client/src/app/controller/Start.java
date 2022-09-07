package app.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;

public class Start implements Initializable {

	@FXML
	private Button move;
	
	String nickName;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		move.setOnAction(e -> {
			Root.getRoot().toGame();
			handleAlert();
		});
	}
	
	private void handleAlert() {
		TextInputDialog nick = new TextInputDialog();
		nick.setContentText("닉네임을 입력해 주세요.");
		Optional<String> res = nick.showAndWait();
		// 확인 버튼을 눌러서 종료 했고, 닉네임에 공백이 없으면.
		if(res.isPresent() && !res.get().trim().equals("")) {
			res.ifPresent(new Consumer<String>() {
				@Override
				public void accept(String n) {
					nickName = n;
					System.out.println("닉네임은 : "+n);
					Client.send(0,nickName);
					Client.nickName = nickName;
				}
			});
		} // if end
	}
}

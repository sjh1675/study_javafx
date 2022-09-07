package app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class End implements Initializable {

	@FXML
	private Button move, exit;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		move.setOnAction(e -> {
			Root.getRoot().clientConn.startClient();
			Root.getRoot().toStart();
		});
		
		exit.setOnAction(e -> {
			Platform.exit();
		});
	}
}

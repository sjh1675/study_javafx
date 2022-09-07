package app.controller;

import java.io.IOException;

import client.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class Root extends Pane {
	public static Root root = new Root();
	public Client clientConn;
	private Pane screen;

	private Root() {
		FXMLLoader loader = new FXMLLoader(getClass().
				getResource("../page/root.fxml"));
		try {
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
			isClient();
			
			this.getChildren().add(screen);
		} catch (IOException e) {
			clientConn.stopClient();
		}
	}// Root end

	// Root 현재 싱글턴 getRoot로 가져와서 사용
	public static Root getRoot() {
		return root;
	}

	// client 실행
	private void isClient() throws IOException {
		screen = FXMLLoader.load(getClass().
				getResource("../page/start.fxml"));
		clientConn = new Client();
		clientConn.startClient();
	}

	// 페이지 전환
	public void toStart() {
		try {
			screen = FXMLLoader.load(getClass().getResource("../page/start.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getChildren().remove(0);
		this.getChildren().add(screen);
	}

	public void toGame() {
		try {
			screen = FXMLLoader.load(getClass().
					getResource("../page/game.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getChildren().remove(0);
		this.getChildren().add(screen);
	}

	public void toEnd() {
		try {
			screen = FXMLLoader.load(getClass().getResource("../page/end.fxml"));
			clientConn.stopClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.getChildren().remove(0);
		this.getChildren().add(screen);
	}

	

}

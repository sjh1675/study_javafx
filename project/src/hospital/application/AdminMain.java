package hospital.application;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		Scene scene = isServer() ? new Scene(new Admin_Root(primaryStage)):
									new Scene(new User_Root(primaryStage));
		primaryStage.setScene(scene);
		primaryStage.setTitle("영남병원");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	private boolean isServer() {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return Objects.equals(localHost, "192.168.1.47");
	}

	public static void main(String[] args) {
		launch(args);
	}
}
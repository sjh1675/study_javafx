package e03_controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		System.out.println("start 호출");
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Root.fxml"));
			System.out.println("FXML load 완료");
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("start 종료");
	}

	public static void main(String[] args) {
		launch(args);
	}
}

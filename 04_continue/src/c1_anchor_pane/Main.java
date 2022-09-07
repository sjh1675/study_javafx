package c1_anchor_pane;
	
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			URL url = getClass().getResource("Root.fxml");
			System.out.println(url.getPath());
			url = Main.class.getResource("Root.fxml"); // 위의 getClass()와 Main.class가 같음
			
			AnchorPane root = FXMLLoader.load(url);
			
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("AnchorPane");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

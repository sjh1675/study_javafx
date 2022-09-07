package app.application;

import app.controller.Root;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AppMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Root root = Root.getRoot();
			Scene scene = new Scene(root);
			primaryStage.getIcons().add(new Image("/app/image/blackjack.png"));
			primaryStage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("btn.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("Rule.css").toExternalForm());
			System.setProperty("prism.lcdtext", "false");
			Font.loadFont(getClass().getResource("/app/page/BMJUA_ttf.ttf").toString(),30);
			Font.loadFont(getClass().getResource("/app/page/yanolja.ttf").toString(),16);
			primaryStage.setTitle("Black Jack");
			primaryStage.setResizable(false);
			primaryStage.setOnCloseRequest((e) -> {
				root.clientConn.stopClient();
			});

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	public static void main(String[] args) {
		launch(args);
	}

}

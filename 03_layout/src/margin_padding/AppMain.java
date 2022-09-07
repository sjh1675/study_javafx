package margin_padding;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		HBox hBox = new HBox();
		
		Insets insets = new Insets(50, 10, 10, 50); // 상 우 하 좌
		hBox.setPadding(insets);
		
		Button btn = new Button();
		btn.setPrefSize(100, 100);
		btn.setText("확인");
		
		hBox.setMargin(btn, new Insets(30));
		hBox.getChildren().add(btn);
		
		primaryStage.setScene(new Scene(hBox));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

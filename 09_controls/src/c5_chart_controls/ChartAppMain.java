package c5_chart_controls;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChartAppMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Chart.fxml"))));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

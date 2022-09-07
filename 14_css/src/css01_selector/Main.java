package css01_selector;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("Root.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().addAll(getClass().getResource("application.css").toExternalForm(), getClass().getResource("state.css").toString());
//			scene.getStylesheets().add(getClass().getResource("state.css").toString());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

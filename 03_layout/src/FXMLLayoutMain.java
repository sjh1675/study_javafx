
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FXMLLayoutMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		HBox root = FXMLLoader.load(getClass().getResource("Sub.fxml"));	// FXML 파일을 읽어 와서 객체(자바코드)로 변환
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

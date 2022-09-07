package chat_client;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("Client.fxml")
				);
				BorderPane root = loader.load();
				ClientController controller = loader.getController();
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.setTitle("CHAT CLIENT");
				primaryStage.setResizable(false);
				primaryStage.setOnCloseRequest(e -> {
					controller.stopClient();
				});
				primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

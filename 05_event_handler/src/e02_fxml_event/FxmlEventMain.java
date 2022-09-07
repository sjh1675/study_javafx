package e02_fxml_event;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FxmlEventMain extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			HBox root = FXMLLoader.load(getClass().getResource("Root.fxml"));
			
			ObservableList<Node> list = root.getChildren();
			System.out.println(list.get(0));
			Node btn1 = list.get(0);
			((Button)btn1).setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("버튼 1 클릭!!");
				}
			});
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}

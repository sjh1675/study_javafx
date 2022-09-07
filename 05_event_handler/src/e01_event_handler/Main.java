package e01_event_handler;
	
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			HBox root = new HBox();
			root.setAlignment(Pos.BOTTOM_CENTER);
			root.setSpacing(10.0);
			root.setPadding(new Insets(15));
			
			Button btn1 = new Button("버튼1");			
			btn1.setId("btn1");
			btn1.setPrefSize(200, 100);
			
			ButtonActionEventHandler handler = new ButtonActionEventHandler();
			btn1.setOnAction(handler);
			
			
			Button btn2 = new Button("버튼2");
//			btn2.setId("btn2");
//			btn2.setPrefSize(200, 100);
			EventHandler<ActionEvent> handler2 = new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("버튼2 클릭!!");
				}
			};
			btn2.setOnAction(handler2);
			
			Button btn3 = new Button("버튼3");
//			btn3.setId("btn3");
//			btn3.setPrefSize(200, 100);

			btn3.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("버튼3 클릭!!");
				}
			});
			
			root.getChildren().add(btn1);
			root.getChildren().add(btn2);
			root.getChildren().add(btn3);
//			list.addAll(btn1, btn2, btn3); // 위 세개를 한번에
			
			
			ObservableList<Node> list = root.getChildren();
			
			
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class l01_layout_java extends Application {

	@Override
	public void start(Stage primaryStage) {
		// Vertical : 세로(수직)로 내부의 컨트롤을 포함하는 컨테이너
		VBox root = new VBox();
		root.prefHeight(150);	// VBox 높이 설정
		root.prefWidth(350);	// VBox 폭 설정
		
		Label label = new Label();	// Label Control 생성
		label.setText("Hello JavaFX");
		label.setFont(new Font(50));
		
		// container에 포함 된 자식 요소 목록 list
		root.getChildren().add(label);
		
		Button btn = new Button();
		btn.setText("확인");
		root.getChildren().add(btn);	// VBox의 자식 요소에 버튼 추가
		
		// root(VBox)를 컨테이너로 해서 장면 생성
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);	// root로 설정한 창을 띄움
		
		primaryStage.setTitle("AppMain");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

package application;
	
import java.util.Optional;
import java.util.function.Consumer;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		VBox vBox = new VBox();
		vBox.setPrefWidth(200);
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);
		vBox.setPadding(new Insets(10));
		
		ObservableList<Node>
				child = vBox.getChildren();
		child.add(new Button("알림"));
		child.add(new Button("경고"));
		child.add(new Button("오류"));
		child.add(new Button("확인"));
		child.add(new Button("입력"));
		child.add(new Button("Custom"));
		
		for (Node n : child) {
			Button b = (Button)n;
			b.setMaxWidth(Double.MAX_VALUE);
			b.setOnAction(e ->{
				handleAlert(b.getText());
			});
		}
		
		primaryStage.setScene(new Scene(vBox));
		primaryStage.show();
	}
	
	public void handleAlert(String text) {
		System.out.println(text);
//		final Stage stage = new Stage(StageStyle.UNDECORATED);
		switch(text) {
		case "알림":
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(text);
			alert.setHeaderText("알림창입니다. \n헤더정보입니다.");
			alert.setContentText("이곳은 컨텐츠 정보입니다. \n내용을 확인하세요.");
			
			alert.show();
			break;
		case "경고":
			alert = new Alert(AlertType.WARNING);
			alert.setTitle(text);
			alert.setHeaderText("경고창입니다. \n헤더정보입니다.");
			alert.setContentText("이곳은 컨텐츠 정보입니다. \n내용을 확인하세요.");	
			alert.show();
			break;
		case "오류":
			alert = new Alert(AlertType.ERROR);
			alert.setTitle(text);
			alert.setHeaderText(null);
			alert.setContentText("이곳은 컨텐츠 정보입니다. \n내용을 확인하세요.");
			alert.show();
			break;
		case "확인":
			alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("결제 확인");
			alert.setHeaderText("결제 정보입니다.");
			alert.setContentText("확인 시 5,000,000원이 결제됩니다.");
			Optional<ButtonType> result = alert.showAndWait();
			System.out.println(result.get());
			if (result.get() == ButtonType.OK) {
				System.out.println("OK 버튼 선택");
			} else if (result.get() == ButtonType.CANCEL){
				// 취소 or 닫기
				System.out.println("취소 버튼 선택");
			}
			break;
		case "입력":
			TextInputDialog dialog = new TextInputDialog();	// 생성자에 문자열로 값을 주면 입력란에 문자열값이 들어가 있음
			dialog.setTitle(text);
			dialog.setHeaderText("Header Text");
			dialog.setContentText("이름을 입력해주세요");
			Optional<String> res = dialog.showAndWait();
			
			if (res.isPresent() && !res.get().trim().equals("")) {
				System.out.println(res);
				res.ifPresent(new Consumer<String>() {
					@Override
					public void accept(String t) {
						System.out.println("작성하신 이름은 : " + t + "입니다.");
					}
				});
			}
			break;
		case "Custom":
			alert = new Alert(
					AlertType.INFORMATION,
					"이곳은  content 정보입니다.\n내용을 확인하세요",
					ButtonType.OK,
					ButtonType.CANCEL,
					ButtonType.NEXT
					);
			alert.setTitle("알림!");
			alert.setHeaderText(null);
			Optional<ButtonType> result1 
				= alert.showAndWait();
			if(result1.get() == ButtonType.NEXT) {
				System.out.println("다음");
			}else if(result1.get() == ButtonType.OK) {
				System.out.println("확인");
			}else {
				System.out.println("취소");
			}
			break;
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

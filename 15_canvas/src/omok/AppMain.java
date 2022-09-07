package omok;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppMain extends Application {

	Canvas canvas;

	GraphicsContext gc;

	// 돌 위치 저장
	int[][] map = null; // 0은 빈자리

	int doll_state = 1; // 흑 1, 백 2

	Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Omok.fxml"));
			canvas = (Canvas) root.lookup("#canvas");
			gc = canvas.getGraphicsContext2D();

			this.primaryStage = primaryStage; // 스테이지 정보 저장
			initCanvas(); // 바둑판 초기화

			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
		}

	}

	private void initCanvas() {

		doll_state = 1;
		map = new int[19][19];

		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for (int i = 30; i < canvas.getWidth(); i += 30) { // 바둑판이 600이라 30으로 나누어 줄을 얻음
			gc.strokeLine(30, i, canvas.getWidth() - 30, i);
		}
		for (int i = 30; i < canvas.getWidth(); i += 30) { // 바둑판이 600이라 30으로 나누어 줄을 얻음
			gc.strokeLine(i, 30, i, canvas.getHeight() - 30);
		}

		// 선 색깔
		gc.setStroke(new Color(0, 0, 0, 1));
		canvas.setOnMouseClicked(e -> {
			double x = e.getX();
			double y = e.getY();
			int offsetX = (int) (x + 15) / 30;
			int offsetY = (int) (y + 15) / 30;

			System.out.println(x + ":" + y);
			System.out.println(offsetX + ":" + offsetY);

			draw(offsetX, offsetY);
		});

	}

	// 돌 그리기
	private void draw(int x, int y) {

		int drawX = (x * 30) - 15;
		int drawY = (y * 30) - 15;

		// 0 : 빈자리, 1 : 흑돌, 2 : 백돌
		if (map[--y][--x] != 0) {
			System.out.println("이미 돌이 있습니다.");
			return;
		}

		if (doll_state == 1) {
			System.out.println("흑돌");
			gc.setFill(Color.BLACK);
		} else {
			System.out.println("백돌");
			gc.setFill(Color.WHITE);
		}
		gc.strokeOval(drawX, drawY, 28, 28);
		gc.fillOval(drawX, drawY, 28, 28);

		map[y][x] = doll_state;
		victory(x, y);
		doll_state = (doll_state == 1) ? 2 : 1;

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				System.out.printf("[%d]", map[i][j]);
			}
			System.out.println();
		}
	}

	// 승리 확인
	private void victory(int x, int y) {
		int cnt1 = 1, cnt2 = 1, cnt3 = 1, cnt4 = 1;
		// 배열에서 가로 오른쪽 방향 동일한 색의 돌 개수 추가
		for (int i = 1; i < 5; i++) {
			if (x + i < 19 && map[y][x + i] == doll_state) {
				cnt1++;
			} else {
				break;
			}
		}

		// 배열에서 가로 왼쪽 방향 동일한 색의 돌 개수 추가
		for (int i = 1; i < 5; i++) {
			if (x - i >= 0 && map[y][x - i] == doll_state) {
				cnt1++;
			} else {
				break;
			}
		}

		// 배열에서 세로 아래 방향 동일한 색의 돌 개수 추가
		for (int i = 1; i < 5; i++) {
			if (y + i < 19 && map[y + i][x] == doll_state) {
				cnt2++;
			} else {
				break;
			}
		}

		// 배열에서 세로 위 방향 동일한 색의 돌 개수 추가
		for (int i = 1; i < 5; i++) {
			if (y - i >= 0 && map[y - i][x] == doll_state) {
				cnt2++;
			} else {
				break;
			}
		}

		// 배열에서 오른쪽 아래 방향 동일한 색의 돌 개수 추가
		for (int i = 1; i < 5; i++) {
			if (x + i < 19 && y + i < 19 && map[y + i][x + i] == doll_state) {
				cnt3++;
			} else {
				break;
			}
		}

		// 배열에서 왼쪽 위 동일한 색의 돌 개수 추가
		for (int i = 1; i < 5; i++) {
			if (x - i >= 0 && y - i >= 0 && map[y - i][x - i] == doll_state) {
				cnt3++;
			} else {
				break;
			}
		}

		// 배열에서 오른쪽 위 방향 동일한 색의 돌 개수 추가
		for (int i = 1; i < 5; i++) {
			if (x + i < 19 && y - i >= 0 && map[y - i][x + i] == doll_state) {
				cnt4++;
			} else {
				break;
			}
		}

		// 배열에서 왼쪽 아래 동일한 색의 돌 개수 추가
		for (int i = 1; i < 5; i++) {
			if (x - i >= 0 && y + i < 19 && map[y + i][x - i] == doll_state) {
				cnt4++;
			} else {
				break;
			}
		}

		if (cnt1 >= 5 || cnt2 >= 5 || cnt3 >= 5 || cnt4 >= 5) {
			String text = doll_state == 1 ? "흑" : "백";
			System.out.println(text + " 승리!!");
			showDialog(text);
		}
	}

	// 알림창
	private void showDialog(String text) {
		Stage stage = new Stage(StageStyle.UNDECORATED);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(primaryStage);
		AnchorPane a = new AnchorPane();
		a.setPrefSize(200, 100);
		Label label = new Label(text + " 승리!!");
		label.setStyle("-fx-font-size:35;");
		label.setLayoutX(10);
		label.setLayoutY(25);

		Button btn = new Button("확인");
		btn.setLayoutX(140);
		btn.setLayoutY(40);

		btn.setOnAction(e -> {
			stage.close();
			initCanvas();
		});

		a.getChildren().addAll(label, btn);
		Scene scene = new Scene(a);
		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}

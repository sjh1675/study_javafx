package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	
	// 캔버스
	private Canvas canvas;
	
	// 그리는 도구
	private GraphicsContext gc;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Root.fxml"));
			Scene scene = new Scene(root);
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			
			canvas = (Canvas)root.lookup("#canvas");
			gc = canvas.getGraphicsContext2D();	// canvas에 그릴 도구
			
			draw();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void draw() {
		gc.setFill(Color.RED);		// 채우기 색상 지정
		gc.fillRect(100, 100, 100, 100); // 가로좌표 세로좌표 너비 높이 // 좌표는 기본 0,0 기준 제일 왼쪽 위
		
		gc.setStroke(Color.BLUE);	// 선 색깔 지정
		gc.strokeRect(150, 150, 100, 100);	// 채우지 않은 사각형
		
		gc.strokeOval(100, 100, 50, 50);	// 호를 그리는 도구
		
		// 선 그리기 도구
		// 시작x, 시작y, 끝x, 끝y
		gc.strokeLine(200, 200, 200, 250);	// 두 점의 좌표로 선을 그음(200, 200)  (200, 250)
		
		// 여러 선을 이어서 그려주는 도구
		gc.beginPath();		// 선 그리기 시작
		gc.setStroke(new Color(0.5, 0.5, 0.5, 1));	// 선 색깔 지정
		gc.lineTo(10, 10);
		gc.lineTo(20, 30);
		gc.lineTo(50, 40);
		gc.lineTo(10, 10);
		gc.stroke();
		
		Image img = new Image(getClass().getResource("/image/cat3.jpg").toString());
		// gc.drawImage(img, 300, 300);
		gc.drawImage(img, 300, 300, 100, 100);
		gc.clearRect(0, 0, 100, 100);
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		Thread t = new Thread(() -> {
			for (int i = 0; i < canvas.getWidth(); i++) {
				gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
				gc.drawImage(img, i, i, 100, 100);
				try {
					Thread.sleep(700/(i+1));
				} catch (InterruptedException e) {}
			}
		});
		t.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

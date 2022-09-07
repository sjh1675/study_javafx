package w1_runlater;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class RootController implements Initializable {

	@FXML
	private Label lblTime;
	@FXML
	private Button btnStart, btnStop;

	private SimpleDateFormat sdf;
	private boolean isRun = true;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sdf = new SimpleDateFormat("HH:mm:ss");
		
		btnStart.setOnAction(e ->{
			Thread t = new Thread(() -> {
				isRun = true;
				while (isRun) {
					String date = sdf.format(new Date());
					Platform.runLater(() -> {
						lblTime.setText(date);
					});	// 매개변수 : UI 변경 작업(Runnable)이 들어감
					
//					lblTime.setText(date);	// 외부 스레드에서 UI를 직접적으로 변경하는걸 막아놔서 처리가 필요함
											// 외부 스레드에서 UI를 직접적으로 변경하지 말고 FX 메인스레드에서 처리를 하도록 명령 (Platform.runLater() 사용)
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {}
				}
			});
			t.start();
		});
		
		btnStop.setOnAction(e ->{
			isRun = false;
		});
	}
	
	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}
}

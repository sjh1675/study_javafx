package second;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SecondController implements Initializable {

	@FXML Button btn1, btn2;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn1.setOnAction(e -> {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../application/root.fxml"));
			try {
				Parent root = loader.load();
				Scene scene = new Scene(root);
				Stage stage = new Stage();
				stage.setScene(scene);
				
				MainController cont = loader.getController();
				cont.setPrimaryStage(stage);
				stage.show();
				
				Stage secondStage = (Stage)btn1.getScene().getWindow();		// getScene으로 root 컨테이너를 들고 오고 getWindow로 그 무대 정보를 가져옴
				// 이니셜라이즈에서는 할 수 없음(이니셜라이즈가 완전히 실행 되어야 버튼 등의 무대값이 온전하게 있어서)
				// 그래서 이니셜라이즈가 지난 뒤, 이벤트로써 버튼을 누를때는 모든 정보(무대 정보)가 로드되어 있어서 가능함
				secondStage.close();
			} catch (IOException e1) {}			
		});
		
		
		
	}
}
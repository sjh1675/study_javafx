package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.stage.Stage;

public class MainController implements Initializable {
	
	@FXML Button btn1, btn2, btn3;
	
	private Stage primaryStage;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn1.setMnemonicParsing(true);
		btn1.setText("_A 버튼");		// 언더바와 함께 버튼을 지정해서(현재 A 지정) alt 한번 눌렀다가 뗀 이후 누르면 버튼이 눌러짐
		btn1.setOnAction(e -> {
			System.out.println("1번 선택");
		});
		btn2.setText("_S 버튼");
		btn2.setOnAction(e -> {
			System.out.println("2번 선택");
		});
		btn3.setText("ss_Oreo");	// 단축키가 O 한글자임
		btn3.setOnAction(e -> {
			System.out.println("3번 선택");
			Stage stage = new Stage();
			Parent root = null;
			try {
				root = FXMLLoader.load(getClass().getResource("../second/Second.fxml"));
			} catch (IOException e1) {}
			stage.setScene(new Scene(root));
			stage.show();
			this.primaryStage.close();
		});
		
	}


	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		KeyCombination kc = new KeyCharacterCombination("P", KeyCombination.ALT_DOWN, KeyCombination.SHIFT_DOWN);
		Mnemonic mn = new Mnemonic(btn1, kc);
		primaryStage.getScene().addMnemonic(mn);
	}
}

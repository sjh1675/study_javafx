package c2_input;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class InputController implements Initializable {
	
	@FXML private TextField txtTitle;
	@FXML private PasswordField txtPass;
	@FXML private ComboBox<String> comboPublic;
	@FXML private ColorPicker colorPicker;
	@FXML private DatePicker datePicker;
	@FXML private TextArea txtContent;
	@FXML private Button btnReg, btnCancel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnReg.setOnAction(e -> {
			String titleText = txtTitle.getText();
			System.out.println("제목 : " + titleText);
			String pass = txtPass.getText();
			System.out.println("비밀번호 : " + pass);
			
			String comboData = comboPublic.getValue();
			System.out.println("combobox : " + comboData);
			
			// Color : 색상 정보를 저장하는 객체
			// RGBA : RGB + Alpha(brightness)
			Color color = colorPicker.getValue();
			System.out.println("color : " + color);
			System.out.println("R : " + color.getRed());
			System.out.println("G : " + color.getGreen());
			System.out.println("B : " + color.getBlue());
			System.out.println("A : " + color.getBrightness());
									
			// LocalDate : 날짜 정보를 yyyy-MM-dd 형태로 표현
			LocalDate date = datePicker.getValue();
			System.out.println(date);
			if(date != null) {
				System.out.println("연도 : " + date.getYear());
				System.out.println("월 : " + date.getMonth());
				System.out.println("일 : " + date.getDayOfMonth());
			}
			
			String content = txtContent.getText();
			System.out.println("content : " + content);
			
		});
		
		btnCancel.setOnAction(e -> {
			txtTitle.setText(null);
			txtPass.setText("");
			txtContent.clear();
			
			ObservableList<String> oldList = comboPublic.getItems();
			ObservableList<String> newList = FXCollections.observableArrayList();
			oldList.add("박주신 제외");
			oldList.remove("공개");
			comboPublic.setItems(newList);
			
			colorPicker.setValue(new Color(1,1,1,1));
			datePicker.setValue(null);
			
		});
		
		txtTitle.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.out.println(event.getCode());
				if(event.getCode() == KeyCode.ENTER) {
					txtPass.requestFocus();		// 비밀번호 입력란에 포커스
				}
			}								
		});
		
		txtPass.textProperty().addListener((target, oldValue, newValue) -> {
			System.out.println(newValue);
			txtContent.appendText(newValue + "\n");
		});
	}
	
}
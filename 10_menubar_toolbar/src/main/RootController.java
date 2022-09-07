package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class RootController implements Initializable {
	
	@FXML private TextArea textArea;
	@FXML private ComboBox<String> comboBox;
	
	private Stage primary;
	
	
	public void setStage(Stage stage) {		
		this.primary = stage;
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// selected'Index'Property : 인덱스 번호를 가져옴
		// selected'Item'Property : 스트링 값을 가져올 수 있음
		comboBox.getSelectionModel().selectedIndexProperty().addListener((target, oldValue, newValue) -> {
			int index = newValue.intValue();
			System.out.println("select comboBox : " + index);
			switch(index) {
			case 0:	// directory chooser : 폴더 선택 상자
				DirectoryChooser chooser = new DirectoryChooser();
				File selectedDir = chooser.showDialog(primary);
				if(selectedDir != null) {
					for (File f : selectedDir.listFiles()) {
						textArea.appendText(f.getName()+"\n");
					}
				}
				break;
			case 1:	// popup window
				handlePopup();
				break;
			case 2:	// custom window
				handleCustom();
				break;
			case 3:	// custom dialog
				
				break;
			}
		});
	}
	
	private void handleCustom() {
		// 새 무대 생성
		final Stage stage = new Stage(StageStyle.DECORATED);	// 제목줄에 다 있음
		// final을 사용하는 이유는 이벤트로 인해 스테이지가 변경될 수 있음을 막기 위해 사용
		
//		stage = new Stage(StageStyle.UNDECORATED);	// 제목줄 X
//		stage = new Stage(StageStyle.UTILITY);		// 제목줄에 타이틀과 종료버튼만 존재
//		stage = new Stage(StageStyle.TRANSPARENT);	// 투명배경 제목줄 x
//		stage = new Stage(StageStyle.TRANSPARENT);	// 투명배경 제목줄 x
//		stage = new Stage();	// DECORATED 와 동일
		
//		stage.initModality(Modality.WINDOW_MODAL);	// WINDOW_MODAL(기본값) : 창의 소유권자가 있을 때, 소유권자로 포커스가 이동되지 않음
//		stage.initOwner(primary);	// 창의 소유권자를 지정하여, 이 창이 꺼지기 전 까지 소유권자에 포커스가 이동되지 않음
		
		stage.initModality(Modality.APPLICATION_MODAL);	// APPLICATION_MODAL : 소유권자가 있던 없던 포커스를 이동시키지 않음
		
		
		stage.setTitle("입력 창");
		Parent parent = null;
		try {
			parent = FXMLLoader.load(getClass().getResource("Custom.fxml"));
		} catch (IOException e) {}
		
		
		TextField textField = (TextField)parent.lookup("#textTitle");
		TextArea textArea = (TextArea)parent.lookup("#textContent");
		Button btnOK = (Button)parent.lookup("#btnOK");
		Button btnCancel = (Button)parent.lookup("#btnCancel");
		
		btnOK.setOnAction(e -> {
			String title = textField.getText();
			String content = textArea.getText();
			stage.close();
			RootController.this.textArea.appendText(title + "\n");
			RootController.this.textArea.appendText(content + "\n");
			comboBox.getSelectionModel().clearSelection();
		});
		
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setResizable(false);		// 창 크기 변경
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {			
			@Override
			public void handle(WindowEvent event) {
				System.out.println("close 이벤트");
				comboBox.getSelectionModel().clearSelection();
//				event.consume();	// 닫기 이벤트 무시 (종료 버튼 눌러도 안꺼짐)
			}
		});	// 닫기를 눌렀을 때 이벤트 처리
		
		btnCancel.setOnAction(e -> {
			stage.close();
		});
		
		stage.show();
		
		
		
	}


	private void handlePopup() {
		System.out.println("POPUP");
		Popup popUp = new Popup();
		Parent parent = null;
		try {
			parent = FXMLLoader.load(
				getClass().getResource("Popup.fxml")
			);
		} catch (IOException e) {}
		ImageView imageView 
			= (ImageView)parent.lookup("#imgMessage");
		String imagePath = "../images/dialog-info.png";
		URL url = getClass().getResource(imagePath); 
		Image image = new Image(url.toString());
		imageView.setImage(image);
				
		imageView.setOnMouseClicked(event->{
			System.out.println("image mouse click");
			popUp.hide();
		});
		
		Label lblMessage
			= (Label)parent.lookup(".lblMessage");
		lblMessage.setText("메세지 알림");
		
		popUp.getContent().add(parent);
		popUp.setAutoHide(true);
		popUp.show(primary);
		
	}

	public void handleNew(ActionEvent e) throws IOException {
		System.out.println("new");
		textArea.appendText("New\n");
		Runtime rt = Runtime.getRuntime();
		rt.exec("notepad");
	}
	public void handleOpen(ActionEvent e) throws IOException {
		System.out.println("Open");
		textArea.appendText("Open\n");
		// FileChooser - 파일 선택 창
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
			new ExtensionFilter("Text files","*.txt")
		);
		File selectedFile = fileChooser.showOpenDialog(primary);
		if(selectedFile != null) {
			textArea.appendText(selectedFile.getPath()+"\n");
			FileReader reader = new FileReader(selectedFile);
			BufferedReader bReader = new BufferedReader(reader);
			
			String message = null;
			while((message = bReader.readLine()) != null) {
				textArea.appendText(message+"\n");
			}
			bReader.close();
			reader.close();
		}
		
	}
	public void handleSave(ActionEvent e) throws Exception{
		System.out.println("Save");
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(
			new ExtensionFilter("text files", "*.txt")
		);
		File selectedFile = fileChooser.showSaveDialog(primary);
		System.out.println(selectedFile.getPath());
		if(selectedFile != null) {
			FileWriter writer = new FileWriter(selectedFile);
			BufferedWriter bWriter = new BufferedWriter(writer);
			String message = textArea.getText();
			bWriter.write(message);
			bWriter.flush();
			bWriter.close();
			writer.close();
		}
	}
	public void handleClose(ActionEvent e) {
		System.out.println("Close");
		// 무대 종료
		primary.close();
	}
}

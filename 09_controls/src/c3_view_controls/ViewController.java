package c3_view_controls;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewController implements Initializable {

	@FXML private ListView<String> listView;
	@FXML private TableView<PhoneVO> tableView;
	@FXML private ImageView imageView;
	@FXML private TextField txtName;
	@FXML private Button btnUpdate;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> list
			= FXCollections.observableArrayList(
				"갤럭시S1","갤럭시S2","갤럭시S3","갤럭시S4",
				"갤럭시S5","갤럭시S6","갤럭시S7"
			);
		listView.setItems(list);
		
		// 이 아래에 기능 : listView 아이템이 선택 됬을때
		// 동일한 tableView 항목도 선택되도록 만들 예정
		
		listView.getSelectionModel().selectedIndexProperty().addListener((target, oldValue, newValue) -> {
			if (newValue != null) {
				System.out.println(newValue);
				int idx = newValue.intValue();
				System.out.println(list.get(idx));
				tableView.getSelectionModel().select(idx);
				tableView.scrollTo(idx);
			}
		});
		
		
		
		// tableView
		ObservableList<PhoneVO> phoneList 
			= FXCollections.observableArrayList();
		for(int i=1; i<=7; i++) {
			phoneList.add(new PhoneVO("갤럭시S"+i,"phone0"+i+".png"));
		}
		System.out.println(phoneList);
		TableColumn<PhoneVO,?> tColumnName
				= tableView.getColumns().get(0);
		tColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		TableColumn<PhoneVO, ?> tColumnPath = tableView.getColumns().get(1);
		tColumnPath.setCellValueFactory(new PropertyValueFactory<>("path"));
		
		tableView.setItems(phoneList);
		
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PhoneVO>() {
			@Override
			public void changed(ObservableValue<? extends PhoneVO> observable, PhoneVO oldValue, PhoneVO newValue) {
				if(newValue != null) {
					System.out.println(newValue);
					String path = "../images/" + newValue.getPath();
					URL url = getClass().getResource(path);
					// 텍스트 정보 수정을 위해 TextField의 값을 선택된 행 정보로 변경
					txtName.setText(newValue.getName());
					
					imageView.setImage(new Image(url.toString()));
				}
			}
		});
		
		tableView.getSelectionModel().selectedIndexProperty().addListener((target, oldValue, newValue) -> {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					int index = newValue.intValue();
					listView.getSelectionModel().select(index);
					listView.scrollTo(index);
				}
			});
		});
		
		btnUpdate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String name = txtName.getText();
				PhoneVO phone = tableView.getSelectionModel().getSelectedItem();
				
				System.out.println(phone);
				int index = tableView.getSelectionModel().getSelectedIndex();
				list.set(index, name);
				System.out.println(list);
				
				if (phone != null) phone.setName(name);
				System.out.println(phoneList);
				
				tableView.refresh(); // 값이 변경 된 후 테이블의 값을 갱신시켜준다
				listView.refresh();
			}
		});
	}

}












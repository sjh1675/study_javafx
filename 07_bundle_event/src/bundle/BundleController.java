package bundle;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class BundleController implements Initializable {

	@FXML
	private Button btnAccept, btnReload, btnCancel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnAccept.setOnAction(event -> {
			Button btn = (Button)event.getTarget();
			System.out.println(btn.getText());
			System.out.println(event.getEventType());
		});
				
		btnReload.setOnAction(event -> {
			handleAction(event);
		});
		
		btnCancel.setOnAction(event -> {

		});
		
	}
	
	public void handleAction(ActionEvent e) {
		Button btn = (Button)e.getTarget();
		System.out.println(btn.getText());
		System.out.println(e.getEventType()); 
	}
}

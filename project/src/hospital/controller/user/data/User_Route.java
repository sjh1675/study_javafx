package hospital.controller.user.data;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class User_Route implements Initializable {

	@FXML Label route_address;
	@FXML Label route_vehicle;
	@FXML Button btnWeb;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnWeb.setOnAction(e->{
			try {
				Desktop.getDesktop().browse(new URI("https://map.naver.com/v5/directions/-/-/-/transit?c=14367966.8354420,4192077.8517844,17,0,0,0,dh"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
		});
	}

}
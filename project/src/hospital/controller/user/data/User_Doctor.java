package hospital.controller.user.data;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class User_Doctor implements Initializable {

	@FXML private ImageView doctor_img;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Image doctor = new Image(getClass().getResource("../../../img/doctor.png").toString());
		doctor_img.setImage(doctor);
	}
}

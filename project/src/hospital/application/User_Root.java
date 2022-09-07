package hospital.application;

import static hospital.view.user.UserLoaderFactory.USER_HOME;

import java.io.IOException;

import hospital.controller.user.User_Home;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class User_Root extends Pane {

	@FXML
	private Pane root;
	private final Pane screen = USER_HOME.getScreen();
	private final User_Home homeCont = USER_HOME.getController();

	User_Root(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("user_root.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		root.getChildren().add(screen);
		stage.setOnCloseRequest(e -> homeCont.helper.stopClient());
		
		
	}
	
}

package hospital.application;

import static hospital.view.admin.AdminLoaderFactory.ADMIN_HOME;

import java.io.IOException;

import hospital.controller.admin.Admin_Home;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Admin_Root extends Pane {

	@FXML
	private Pane root;
	private final Pane screen = ADMIN_HOME.getScreen();
	private final Admin_Home homeCont = ADMIN_HOME.getController();

	Admin_Root(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("admin_root.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		root.getChildren().add(screen);
		stage.setOnCloseRequest(e -> homeCont.helper.stopServer());
	}

}

package hospital.view.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hospital.controller.util.Changable;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public enum UserLoaderFactory {
	USER_HOME("user_Home.fxml"),
	USER_CHAT("user_Chat.fxml"),
	USER_DOCTOR("user_Doctor.fxml"),
	USER_EDIT("user_Edit.fxml"),
	USER_JOIN("user_Join.fxml"),
	USER_LOGIN("user_Login.fxml"),
	USER_MAIN("user_Main.fxml"),
	USER_PWCHK("user_PwChk.fxml"),
	USER_RESERV("user_Reserv.fxml"),
	USER_ROUTE("user_Route.fxml");
	
	public final FXMLLoader loader;
	
	UserLoaderFactory(String text) {
		this.loader = new FXMLLoader(getClass().getResource(text));
	};
	
	public <T extends Pane> T getScreen() {
		T result = null;
		try {
			result = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public <T> T getController() {
		return loader.getController();
	}
	
	public <T extends Changable> T get(){
		return loader.getController();
	}
	
}

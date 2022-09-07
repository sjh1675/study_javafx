package hospital.view.admin;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public enum AdminLoaderFactory {
	
	ADMIN_CHAT("admin_Chat.fxml"), 
	ADMIN_INPATIENT_INFO("admin_Inpatient_Info.fxml"), 
	ADMIN_MAIN("admin_Main.fxml"),
	ADMIN_RESERV_INFO("admin_Reserv_Info.fxml"), 
	ADMIN_USER_INFO("admin_User_Info.fxml"),
	ADMIN_HOME("admin_Home.fxml");
	
	public final FXMLLoader loader;
	
	AdminLoaderFactory(String text) {
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
	
}

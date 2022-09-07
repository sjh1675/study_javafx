package hospital.controller.admin;

import static hospital.view.admin.AdminLoaderFactory.ADMIN_CHAT;
import static hospital.view.admin.AdminLoaderFactory.ADMIN_INPATIENT_INFO;
import static hospital.view.admin.AdminLoaderFactory.ADMIN_MAIN;
import static hospital.view.admin.AdminLoaderFactory.ADMIN_RESERV_INFO;
import static hospital.view.admin.AdminLoaderFactory.ADMIN_USER_INFO;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import hospital.controller.util.Controllers;
import hospital.controller.util.Home;
import hospital.network.admin.ServerHelper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Admin_Home implements Initializable, Home {

	
	@FXML private Button home_toMain,
					  	 home_toUserInfo, 
						 home_toReservInfo, 
						 home_toInpatientInfo, 
						 home_toAdminChat,
						 home_logout,
						 home_toUser,
						 home_exit;
	
	@FXML private Pane viewWrap;
	
	private Pane userInfo, reservInfo, inpatientInfo, chat, main;
	public ServerHelper helper;
	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//서버 소켓 생성
		createServerSocket();		
		
		//모든 page 초기화
		pageInit();
		
		//이벤트 핸들러 등록
		setHandler();
		
	}
	
	@Override
	public Pane getViewWrap() {
		return viewWrap;
	}
	
	public void switchView(Pane pane) {
		Controllers.switchView(pane, this);
	}
	
	private void createServerSocket() {
		Thread t = new Thread(()->{
			helper = new ServerHelper();
			
			try {
				helper.startServer(InetAddress.getLocalHost().getHostAddress(), 9126);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
		});
		t.start();
	}

	
	private void pageInit() {
		reservInfo = ADMIN_RESERV_INFO.getScreen();
		inpatientInfo = ADMIN_INPATIENT_INFO.getScreen();
		chat = ADMIN_CHAT.getScreen();
		main = ADMIN_MAIN.getScreen();
		userInfo = ADMIN_USER_INFO.getScreen();
		viewWrap.getChildren().add(main);
	}
	
	private void setHandler() {
		home_toMain.setOnAction(e -> switchView(main));
		home_toUserInfo.setOnAction(e -> switchView(userInfo));
		home_toReservInfo.setOnAction(e -> switchView(reservInfo));
		home_toInpatientInfo.setOnAction(e -> switchView(inpatientInfo));
		home_toAdminChat.setOnAction(e -> switchView(chat));
		home_exit.setOnAction(e -> {
			helper.stopServer();
			Platform.exit();
			});
	}

}

package hospital.controller.user;

import static hospital.view.user.UserLoaderFactory.USER_CHAT;
import static hospital.view.user.UserLoaderFactory.USER_DOCTOR;
import static hospital.view.user.UserLoaderFactory.USER_EDIT;
import static hospital.view.user.UserLoaderFactory.USER_JOIN;
import static hospital.view.user.UserLoaderFactory.USER_LOGIN;
import static hospital.view.user.UserLoaderFactory.USER_MAIN;
import static hospital.view.user.UserLoaderFactory.USER_PWCHK;
import static hospital.view.user.UserLoaderFactory.USER_RESERV;
import static hospital.view.user.UserLoaderFactory.USER_ROUTE;

import java.net.URL;
import java.util.ResourceBundle;

import hospital.controller.util.Controllers;
import hospital.controller.util.Home;
import hospital.network.user.UserHelper;
import hospital.vo.UserVO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class User_Home implements Initializable, Home {

	@FXML private Button home_toMain,
						 home_toDoctor, 
						 home_toResv, 
						 home_toChat, 
						 home_toRoute,
						 home_toEdit,
						 home_login_switch,
						 home_exit;
	
	@FXML private Pane viewWrap;
	
	private Pane chat, doctor, edit, join, login, main, pwChk, reserv, route;
	private UserVO loginUser;

	public UserHelper helper;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//소켓 생성
		createSocket();		
				
		//모든 page 초기화
		pageInit();
		
		//내부 화면에 초기화된 page 전달
		Controllers.setView();
		
		//이벤트 핸들러 등록
		setHandler();
		
	}
	
	
	public Pane getEdit() {
		return edit;
	}

	public Pane getJoin() {
		return join;
	}

	public Pane getLogin() {
		return login;
	}

	public Pane getMain() {
		return main;
	}
	
	public UserVO getLoginUser() {
		return loginUser;
	}
	
	public void setLoginUser(UserVO loginUser) {
		this.loginUser = loginUser;
		switchLoginText();
		switchView(main);
	}
	
	public void userLogout() {
		loginUser = null;
	}
	
	public void switchView(Pane pane) {
		Controllers.switchView(pane, this);
	}
	
	@Override
	public Pane getViewWrap() {
		return viewWrap;
	}
	
	private void createSocket() {
		Thread t = new Thread(()->{
			helper = new UserHelper();
			helper.startClient("192.168.1.47", 9126);
		});
		t.start();
	}
		
	private void setHandler() {
		home_toMain.setOnAction(e -> switchView(main));
		home_toDoctor.setOnAction(e -> switchView(doctor));
		home_toRoute.setOnAction(e -> switchView(route));
		home_login_switch.setOnMouseClicked(e -> loginSwitchHandler());
		home_toEdit.setOnAction(e -> { if(loginUser != null) switchView(pwChk); });
		home_exit.setOnAction(e -> Platform.exit());
		home_toResv.setOnAction(e -> { if(loginUser == null) { switchView(login);} else { switchView(reserv);} } );
		home_toChat.setOnAction(e -> { if(loginUser == null) { switchView(login);} else { switchView(chat);} } );
	}
	
	private void switchLoginText() {
		String text;
		if(loginUser == null) {
			home_login_switch.setText("로그인");
			text = "";
			home_toEdit.setVisible(false);
		} else {
			home_login_switch.setText("로그아웃");
			text = loginUser.getName() + "님 환영합니다!";
			home_toEdit.setVisible(true);
		}
		home_toEdit.setText(text);
	}
	
	private void loginSwitchHandler() {
		if(loginUser == null) {
			switchView(login);
		} else {
			userLogout();
			switchView(main);
			switchLoginText();
		}
	}
	private void pageInit() {
		chat = USER_CHAT.getScreen();
		doctor = USER_DOCTOR.getScreen();
		edit = USER_EDIT.getScreen();
		join = USER_JOIN.getScreen();
		login = USER_LOGIN.getScreen();
		main = USER_MAIN.getScreen();
		pwChk = USER_PWCHK.getScreen();
		reserv = USER_RESERV.getScreen();
		route = USER_ROUTE.getScreen();
		viewWrap.getChildren().add(main);
	}
	
}

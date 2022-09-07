package hospital.controller.util;

import static hospital.view.user.UserLoaderFactory.USER_EDIT;
import static hospital.view.user.UserLoaderFactory.USER_JOIN;
import static hospital.view.user.UserLoaderFactory.USER_LOGIN;
import static hospital.view.user.UserLoaderFactory.USER_PWCHK;

import java.time.LocalDate;
import java.util.Optional;

import hospital.view.user.UserLoaderFactory;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Controllers {

	private Controllers(){}
	
	/**
	 * 내부 화면 간의 전환을 위해 User_Home 컨트롤러에 load된 내부 화면의 참조를 
	 * 다른 컨트롤러들의 필드에 저장 시킴
	 */
	public static void setView() {
		UserLoaderFactory[] list = { USER_EDIT, USER_JOIN, USER_LOGIN, USER_PWCHK };
		for(UserLoaderFactory loader : list) {
			loader.get().loadView();
		}
	}
	
	
	/**
	 * 화면 전환 시 이전 화면에 저장된 데이터를 모두 제거.
	 * 
	 * @param previous switchView 메소드 내부에서 전달받은 전환 전 화면
	 */
	public static void sweeper(Pane previous) {
		for (Node target : previous.getChildren()) {
			if (target instanceof Button) {
				continue;
			} else if (target instanceof TextField) {
				((TextField) target).clear();
			} else if (target instanceof DatePicker) {
				((DatePicker) target).setValue(LocalDate.now());
			} else if (target instanceof PasswordField) {
				((PasswordField) target).clear();
			} else if (target instanceof ListView) {
				((ListView) target).getSelectionModel().select(0);
			} else if (target instanceof ChoiceBox) {
				((ChoiceBox) target).getSelectionModel().select(0);
			} else if (target instanceof CheckBox){
				((CheckBox) target).setSelected(false);
			} else if (target instanceof HBox) {
				sweeper((Pane) target);
			} else if (target instanceof VBox) {
				sweeper((Pane) target);
			} else if (target instanceof GridPane) {
				sweeper((Pane) target);
			}
		}
	}
	
	/**
	 * 화면을 전환.
	 * 
	 * @param T Home 인터페이스의 구현 클래스인 Admin_Home, User_Home Type
	 * @param pane 전환하고자하는 화면
	 * @param cont Admin_Home, User_Home 컨트롤러
	 */
	public static <T extends Home> void switchView(Pane pane, T cont) {
		ObservableList<Node> node = cont.getViewWrap().getChildren();
		if(node.get(0) != null) {
			Pane previous = (Pane)node.get(0);
			sweeper(previous);
			node.remove(0);
			node.add(pane);
		}
	}
	
	/**
	 * Alert 팝업을 생성.
	 */
	public static void alt(String title, String content, AlertType alt) {
		Alert alert = new Alert(alt);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.getDialogPane().setGraphic(null);
		alert.setContentText(content);
		alert.show();
	}
	
	/**
	 * OK, CANCEL 버튼이 존재하는 Alert 팝업을 생성. 
	 */
	public static String altConfirm(String title, String content, String confirmBtn, String cancelBtn) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.getDialogPane().setGraphic(null);
		alert.setContentText(content);

		((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(confirmBtn);
		((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText(cancelBtn);

		Optional<ButtonType> result = alert.showAndWait();

		return result.get().getText();
	}
	
}
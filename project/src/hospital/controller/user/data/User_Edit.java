package hospital.controller.user.data;

import static hospital.view.user.UserLoaderFactory.USER_HOME;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import hospital.controller.user.User_Home;
import hospital.controller.util.Changable;
import hospital.controller.util.Controllers;
import hospital.vo.UserVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class User_Edit implements Initializable, Changable {

	@FXML
	private Label edit_pw_warning;

	@FXML
	private TextField edit_id;
	@FXML
	private TextField edit_pw;
	@FXML
	private TextField edit_pw_check;
	@FXML
	private TextField edit_name;
	@FXML
	private TextField edit_reg_first;
	@FXML
	private TextField edit_phone_middle;
	@FXML
	private TextField edit_phone_last;
	@FXML
	private ChoiceBox<String> edit_phone_first;
	@FXML
	private Button edit_enter;
	@FXML
	private Button edit_cancel;
	@FXML
	private Button edit_withdraw;
	private boolean updateCheck;
	private boolean deleteCheck;

	private final User_Home homeCont = USER_HOME.getController();
	private Pane main, login;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initPhone();
		
		edit_phone_middle.setOnKeyReleased(e->{
			if(edit_phone_middle.getText().length() > 3) {
				String str = edit_phone_middle.getText().substring(0,4);
				edit_phone_middle.setText(str);
				edit_phone_last.requestFocus();
			}
		});
		
		edit_phone_last.setOnKeyReleased(e -> {
			if(edit_phone_last.getText().length() > 3) {
				String str = edit_phone_last.getText().substring(0,4);
				edit_phone_last.setText(str);
				edit_enter.requestFocus();
			}
		});

		edit_enter.setOnAction(e -> {

			String pw = edit_pw.getText();
			String pwCheck = edit_pw_check.getText();
			String phone = edit_phone_first.selectionModelProperty().getValue().getSelectedItem()
					+ edit_phone_middle.getText() + edit_phone_last.getText();

			if (pw.equals("")) {
				Controllers.alt("비밀번호 미입력", "비밀번호를 입력해주세요.", AlertType.WARNING);
				return;
			}

			if (pwCheck.equals("")) {
				Controllers.alt("비밀번호 미입력", "비밀번호를 확인란을 입력해주세요.", AlertType.WARNING);
				return;
			}

			if (!pw.equals(pwCheck)) {
				Controllers.alt("비밀번호 불일치", "비밀번호 확인이 일치하지 않습니다.", AlertType.WARNING);
				return;
			}

			if (phone.length() < 10) {
				phone = null;
			}

			// 데이터 1차 통과
			// 정규식으로 검증

			// 8자 이상 영어(대소문자 아무거나 1개 이상) + 숫자(1개 이상) + 특수문자 1개 이상
			String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$";
						
			String phonePattern = "^[0-9]{10,11}$";			// 숫자 10~11자리
			
			
			boolean passwordResult = Pattern.matches(passwordPattern, pw);
			
			if (!passwordResult) {
				Controllers.alt("위험한 비밀번호", "8~20자 영문자, 숫자, 특수문자를 각각 1회 이상 사용하세요.", AlertType.WARNING);
				edit_pw.requestFocus();
				return;
			}
			
			if (phone != null) {
				boolean phoneResult = Pattern.matches(phonePattern, phone);
				if (!phoneResult) {
					Controllers.alt("전화번호 양식", "10~11자리의 번호를 입력하세요.", AlertType.WARNING);
					return;
				}
			} else {
				boolean phoneConfirm = (Controllers.altConfirm("전화번호 양식", "전화를 걸수 없는 번호로 입력하셨습니다. 미입력으로 하겠습니까?", "확인", "취소").equals("OK"));
				if (!phoneConfirm) {
					edit_phone_middle.requestFocus();
					return;
				}
			}

			homeCont.helper.send("$회원정보수정," + pw + "," + phone);

			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

			if (updateCheck) {
				Controllers.alt("수정 완료", homeCont.getLoginUser().getId() + "님의 정보 수정이 완료되었습니다. 다시 로그인 해주세요.",
						AlertType.INFORMATION);
				updateCheck = false;

				edit_pw_warning.setText("비밀번호가 일치하지 않습니다.");
				edit_pw_warning.setVisible(false);

				homeCont.setLoginUser(null);
				homeCont.switchView(login);
			} else {
				Controllers.alt("수정 실패", "정보 수정에 실패했습니다.\n잠시 후에 다시 시도해주세요.", AlertType.WARNING);
			}
		});

		edit_cancel.setOnAction(e -> {
			edit_pw_warning.setText("비밀번호가 일치하지 않습니다.");
			edit_pw_warning.setVisible(false);
			homeCont.switchView(main);
		});

		edit_withdraw.setOnAction(e -> {
			String confirm = Controllers.altConfirm("회원 탈퇴", "정말 탈퇴하시겠습니까?", "확인", "취소"); // 확인 : OK_DONE, 취소 :
																							// CANCEL_CLOSE 값을 가져온다
			System.out.println(confirm);
			if (confirm.equals("OK")) {
				homeCont.helper.send("$회원탈퇴");
				deleteCheck = true;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

			if (deleteCheck) {
				deleteCheck = false;
				Controllers.alt("탈퇴 완료", "회원 탈퇴가 완료되었습니다.", AlertType.INFORMATION);
				edit_pw_warning.setText("비밀번호가 일치하지 않습니다.");
				edit_pw_warning.setVisible(false);
				homeCont.setLoginUser(null);
				homeCont.switchView(main);

			} else {
				Controllers.alt("탈퇴 취소", "탈퇴가 취소되었습니다.", AlertType.INFORMATION);
			}
		});

		edit_pw_check.setOnKeyReleased(e -> {

			String pw = edit_pw.getText();
			String pwCheck = edit_pw_check.getText();

			if (!pw.equals(pwCheck)) {
				edit_pw_warning.setText("비밀번호가 일치하지 않습니다.");
				edit_pw_warning.setVisible(true);
			} else {
				edit_pw_warning.setText("비밀번호가 일치합니다.");
			}

		});
	}

	@Override
	public void loadView() {
		main = homeCont.getMain();
		login = homeCont.getLogin();
	}

	public void setUpdateCheck(boolean updateCheck) {
		this.updateCheck = updateCheck;
	}

	public void initPhone() {
		ObservableList<String> phoneFirst = FXCollections.observableArrayList();
		phoneFirst.add("010");
		phoneFirst.add("011");
		phoneFirst.add("016");
		phoneFirst.add("017");
		edit_phone_first.setItems(phoneFirst);
		edit_phone_first.getSelectionModel().selectFirst();
	}

	public void initData() {

		UserVO user = homeCont.getLoginUser();
		edit_pw_warning.setVisible(false);
		if (user == null) {
			return;
		}

		String firstReg = user.getRegNum().substring(0, 6);

		edit_id.setText(user.getId());
		edit_name.setText(user.getName());
		edit_reg_first.setText(firstReg);
	}

	public void setDeleteCheck(boolean deleteCheck) {
		this.deleteCheck = deleteCheck;
	}

}
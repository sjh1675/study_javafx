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
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

public class User_Join implements Initializable, Changable {

	@FXML
	private Label join_pw_warning;

	public Label getJoin_pw_warning() {
		return join_pw_warning;
	}

	@FXML
	private Button join_id_check;
	@FXML
	private Button join_enter;
	@FXML
	private Button join_cancel;

	@FXML
	private ChoiceBox<String> join_phone_first;

	@FXML
	private TextField join_phone_middle;
	@FXML
	private TextField join_phone_last;
	@FXML
	private TextField join_id;
	@FXML
	private TextField join_name;
	@FXML
	private TextField join_reg_first;

	@FXML
	private PasswordField join_reg_last;
	@FXML
	private PasswordField join_pw;
	@FXML
	private PasswordField join_pw_check;

	private final User_Home homeCont = USER_HOME.getController();
	private Pane login, main;

	private boolean isJoin;
	private boolean isID;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initPhone(); // 전화번호 앞자리 추가
		
		join_reg_first.setOnKeyReleased(e->{
			if(join_reg_first.getText().length() > 5) {
				String str = join_reg_first.getText().substring(0,6);
				join_reg_first.setText(str);
				join_reg_last.requestFocus();
			}
		});
		
		join_phone_middle.setOnKeyReleased(e->{
			if(join_phone_middle.getText().length() > 3) {
				String str = join_phone_middle.getText().substring(0,4);
				join_phone_middle.setText(str);
				join_phone_last.requestFocus();
			}
		});
		
		join_phone_last.setOnKeyReleased(e -> {
			if(join_phone_last.getText().length() > 3) {
				String str = join_phone_last.getText().substring(0,4);
				join_phone_last.setText(str);
				join_enter.requestFocus();
			}
		});
		
		enterPressed(join_id, join_pw);
		enterPressed(join_pw, join_pw_check);
		enterPressed(join_pw_check, join_name);
		enterPressed(join_name, join_reg_first);
		enterPressed(join_reg_first, join_reg_last);
		enterPressed(join_reg_last, join_phone_first);
		enterPressed(join_phone_first, join_phone_middle);
		enterPressed(join_phone_middle, join_phone_last);
		join_phone_last.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				join_enter.fire();
			}
		});

		join_enter.setOnAction(e -> {
			String id = join_id.getText();
			String pw = join_pw.getText();
			String pwCheck = join_pw_check.getText();
			String name = join_name.getText();
			String regNum = join_reg_first.getText() + join_reg_last.getText();
			String phone = join_phone_first.selectionModelProperty().getValue().getSelectedItem()
					+ join_phone_middle.getText() + join_phone_last.getText();


			
			if (id.contains("$") || id.contains(",") || pw.contains("$") || pw.contains(",") || pwCheck.contains("$")
					|| pwCheck.contains(",") || name.contains("$") || name.contains(",") || regNum.contains("$")
					|| regNum.contains(",") || phone.contains("$") || phone.contains(",")) {
				Controllers.alt("경고!", "사용할 수 없는 문자가 포함되어 있습니다!", AlertType.WARNING);
				System.out.println("이거 호출됨");
				return;
			}

			UserVO user = null;

			if (!id.equals("")) {

				homeCont.helper.send("$중복체크" + id);

				try {
					Thread.sleep(100); // 데이터 송수신 대기
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if (!isID) {
					Controllers.alt("아이디 중복", "이미 사용 중인 아이디입니다.", AlertType.WARNING);
					return;
				}

			} else {
				Controllers.alt("아이디 미입력", "아이디를 입력해주세요.", AlertType.WARNING);
				return;
			}

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

			if (name.equals("")) {
				Controllers.alt("이름 미입력", "이름을 입력해주세요.", AlertType.WARNING);
				return;
			}

			if (regNum.equals("")) {
				System.out.println("주민등록번호를 입력해주세요");
				Controllers.alt("주민번호 미입력", "주민등록번호를 입력해주세요.", AlertType.WARNING);
				return;
			}

			if (phone.length() < 10) {
				phone = null;
			}

			String idPattern = "^[a-z|A-Z|0-9]{4,12}$"; // 영어 숫자 4~12자리

			// 8자 이상 영어(대소문자 아무거나 1개 이상) + 숫자(1개 이상) + 특수문자 1개 이상
			String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,20}$";

			String namePattern = "^[가-힣|a-z|A-Z|]+$"; // 한글 영어

			String phonePattern = "^[0-9]{10,11}$"; // 숫자 10~11자리
			
			String regNumPattertn = "^\\d{2}([0]\\d|[1][0-2])([0][1-9]|[1-2]\\d|[3][0-1])*[1-4]$";

			
			
			boolean idResult = Pattern.matches(idPattern, id);
			boolean passwordResult = Pattern.matches(passwordPattern, pw);
			boolean nameResult = Pattern.matches(namePattern, name);
			boolean regNumResult = Pattern.matches(regNumPattertn, regNum);			

			if (!idResult) {
				Controllers.alt("아이디 양식", "4~12자 사이의 영문자, 숫자를 사용해주세요", AlertType.WARNING);
				join_id.requestFocus();
				return;
			}
			
			if (!passwordResult) {
				Controllers.alt("비밀번호 양식", "8~20자 영문자, 숫자, 특수문자를 각각 1회 이상 사용하세요.", AlertType.WARNING);
				join_pw.requestFocus();
				return;
			}

			if (!nameResult) {
				Controllers.alt("이름 양식", "한글, 영문자를 사용하세요", AlertType.WARNING);
				join_name.requestFocus();
				return;
			}
			
			if (!regNumResult) {
				Controllers.alt("주민번호 양식", "올바른 양식의 주민번호를 입력하세요.", AlertType.WARNING);
				join_reg_first.requestFocus();
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
					join_phone_middle.requestFocus();
					return;
				}
			}

			homeCont.helper.send("$회원가입," + id + "," + pw + "," + name + "," + regNum + "," + phone);

			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
			}

			if (isJoin) {
				Controllers.alt("가입 완료", name + "님 가입을 축하합니다.", AlertType.INFORMATION);
				isID = false;
				isJoin = false;
				homeCont.switchView(main);
			} else {
				Controllers.alt("가입 실패", "가입 중에 오류가 발생하였습니다.", AlertType.WARNING);
			}

		});

		join_id_check.setOnAction(e -> {

			String id = join_id.getText();

			if (id == null || id.equals("")) { // 아이디란이 공백이면
				Controllers.alt("아이디 미입력", "아이디를 입력해주세요.", AlertType.WARNING);
			} else {

				homeCont.helper.send("$중복체크" + id);

				try {
					Thread.sleep(100); // 데이터 송수신 대기
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if (isID) {
					Controllers.alt("사용 가능", "사용 가능한 아이디입니다.", AlertType.INFORMATION);
				} else {
					Controllers.alt("아이디 중복", "이미 사용 중인 아이디입니다.", AlertType.WARNING);
				}

			}
		});

		join_cancel.setOnAction(e -> {
			homeCont.switchView(main);
		});

		join_pw_check.setOnKeyReleased(e -> {

			String pw = join_pw.getText();
			String pwCheck = join_pw_check.getText();

			if (!pw.equals(pwCheck)) {
				join_pw_warning.setText("비밀번호가 일치하지 않습니다.");
				join_pw_warning.setVisible(true);
			} else {
				join_pw_warning.setText("비밀번호가 일치합니다.");
			}

		});
	}

	public void setJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}

	public void setID(boolean isID) {
		this.isID = isID;
	}

	public void initPhone() {
		ObservableList<String> phoneFirst = FXCollections.observableArrayList();
		phoneFirst.add("010");
		phoneFirst.add("011");
		phoneFirst.add("016");
		phoneFirst.add("017");
		join_phone_first.setItems(phoneFirst);
		join_phone_first.getSelectionModel().selectFirst();
	}

	@Override
	public void loadView() {
		login = homeCont.getLogin();
		main = homeCont.getMain();
	}


	/**
	 * 엔터키를 눌렀을 때 포커스 이동
	 * 
	 * @param clicked 현재 포커스
	 * @param next    다음 포커스
	 */
	private void enterPressed(Node clicked, Node next) {
		clicked.setOnKeyPressed(e -> {
			if (e.getCode().equals(KeyCode.ENTER)) {
				next.requestFocus();
			}
		});
	}

}

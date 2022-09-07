package hospital.controller.user.data;

import static hospital.view.user.UserLoaderFactory.USER_HOME;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import hospital.controller.user.User_Home;
import hospital.controller.util.Controllers;
import hospital.vo.UserVO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

public class User_Reserv implements Initializable {

	@FXML
	private DatePicker reserv_date;
	@FXML
	private ListView<String> reserv_time;
	@FXML
	private Button reserv_enter;
	@FXML
	private Button reserv_cancel;

	String id;
	String name;
	String date;
	private boolean reservCheck;
	User_Home homeCont = USER_HOME.getController();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reserv_time.setDisable(true);
		reserv_date.setValue(LocalDate.now());

		reserv_date.valueProperty().addListener((t, o, n) -> {
			if (n.toEpochDay() < LocalDate.now().toEpochDay()) {
				Controllers.alt("알림", "진료 가능한 날짜가 아닙니다.", AlertType.WARNING);
				reserv_time.setDisable(true);
				return;
			}
			homeCont.helper.send("$날짜확인," + n);
			reserv_time.setDisable(false);
		});

		reserv_enter.setOnAction(e -> {
			UserVO user = homeCont.getLoginUser();
			if (user == null) {
				Controllers.alt("회원전용", "먼저 로그인을 해주세요.", AlertType.WARNING);
				return;
			} else {
				id = user.getId();
				name = user.getName();
				date = reserv_date.getValue() + " " + reserv_time.selectionModelProperty().get().getSelectedItem() + ":00";
				if(date.contains("null")) {
					Controllers.alt("경고", "날짜와 시간을 선택해주세요.", AlertType.WARNING);
					return;
				}
				homeCont.helper.send("$진료예약," + id + "," + name + "," + date);

				try {
					Thread.sleep(150);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				if (reservCheck) {
					setReservCheck(false);
					Controllers.alt("예약 완료", name + "님 " + date + "에 예약이 완료되었습니다.", AlertType.INFORMATION);
					homeCont.switchView(homeCont.getMain());
				} else {
					Controllers.alt("예약 실패", "예약에 실패했습니다. \n잠시 후에 다시 시도해주세요.", AlertType.WARNING);
					homeCont.switchView(homeCont.getMain());
				}

			}
		});

	}

	public void listUpdate(String[] strs) {
		ObservableList<String> timeList = FXCollections.observableArrayList();
		ArrayList<String> str = new ArrayList<>();
		str.add("09:00");
		str.add("10:00");
		str.add("11:00");
		str.add("14:00");
		str.add("15:00");
		str.add("16:00");
		for (String s : strs) {
			s = s + ":00";
			str.remove(s);
		}
		if (str.isEmpty()) {
			Controllers.alt("알림", "진료 가능한 시간이 없습니다.", AlertType.WARNING);
		}
		timeList.addAll(str);
		Platform.runLater(() -> reserv_time.setItems(timeList));
	}

	public void setReservCheck(boolean reservCheck) {
		this.reservCheck = reservCheck;
	}

}
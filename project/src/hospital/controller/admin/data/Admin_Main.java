package hospital.controller.admin.data;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import hospital.controller.admin.Admin_Home;
import hospital.view.admin.AdminLoaderFactory;
import hospital.vo.ReservVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Admin_Main implements Initializable {

	@FXML
	private Label main_date;
	@FXML
	private Label main_people;
	@FXML
	private Label main_today_date;

	@FXML
	private TableView<ReservVO> main_today_reserv_tbl;
	@FXML
	private TableColumn<ReservVO, ?> main_today_name;
	@FXML
	private TableColumn<ReservVO, ?> main_today_time;
	@FXML
	private Button main_refresh;

	private Admin_Home home;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setToday();
		home = AdminLoaderFactory.ADMIN_HOME.getController();
		update();

		main_refresh.setOnAction(e -> {
			update();
		});
	}

	private void update() {
		ObservableList<ReservVO> list = FXCollections.observableArrayList();
		
		List<ReservVO> reservList = home.helper.reservDAO.reservSelectAll();
		
		reservList = reservList.stream()
				.filter(vo -> vo.getTime().toLocalDateTime().toLocalDate().isEqual(LocalDate.now()))
				.collect(Collectors.toList());

		list.addAll(reservList);

		columnInit();
		main_today_reserv_tbl.setItems(list);
	}

	private void columnInit() {
		main_today_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		main_today_time.setCellValueFactory(new PropertyValueFactory<>("time"));
	}

	private void setToday() {
		main_today_date.setText(LocalDate.now().toString());
	}

}
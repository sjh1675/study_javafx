package hospital.controller.admin.data;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import hospital.controller.admin.Admin_Home;
import hospital.view.admin.AdminLoaderFactory;
import hospital.vo.ReservVO;
import hospital.vo.UserVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Admin_Reserv_Info implements Initializable {

	@FXML
	private TableView<ReservVO> reserv_tbl;
	@FXML
	private TableColumn<ReservVO, ?> reserv_id, reserv_name, reserv_time;
	@FXML
	private Button reserv_refresh;

	private Admin_Home home;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		home = AdminLoaderFactory.ADMIN_HOME.getController();
		update();

		reserv_refresh.setOnAction(e -> {
			update();
		});
	}

	private void update() {
		ObservableList<ReservVO> list = FXCollections.observableArrayList();
		ArrayList<ReservVO> reservList = home.helper.reservDAO.reservSelectAll();
		for (ReservVO user : reservList) {
			list.add(user);
		}
		columnInit();
		reserv_tbl.setItems(list);
	}

	private void columnInit() {
		reserv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		reserv_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		reserv_time.setCellValueFactory(new PropertyValueFactory<>("time"));
	}

}
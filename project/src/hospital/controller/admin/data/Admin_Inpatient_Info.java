package hospital.controller.admin.data;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import hospital.controller.admin.Admin_Home;
import hospital.network.admin.ServerHelper;
import hospital.view.admin.AdminLoaderFactory;
import hospital.vo.InpatientVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;

public class Admin_Inpatient_Info implements Initializable {

	@FXML
	private TableView<InpatientVO> inpatient_tbl;
	@FXML
	private TableColumn<InpatientVO, ?> inpatient_id, inpatient_name, inpatient_time, inpatient_room;
	private Admin_Home home;
	@FXML Button inpatient_refresh;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		home = AdminLoaderFactory.ADMIN_HOME.getController();
		update();
		
		inpatient_refresh.setOnAction(e->{
			update();
		});
	}

	private void update() {
		ObservableList<InpatientVO> list = FXCollections.observableArrayList();
		ArrayList<InpatientVO> inpatientList = home.helper.inpatientDAO.inpSelectAll();
		for(InpatientVO i : inpatientList) {
			list.add(i);
		}
		columnInit();
		inpatient_tbl.setItems(list);
	}

	private void columnInit() {
		inpatient_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		inpatient_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		inpatient_time.setCellValueFactory(new PropertyValueFactory<>("time"));
		inpatient_room.setCellValueFactory(new PropertyValueFactory<>("room"));
	}

}

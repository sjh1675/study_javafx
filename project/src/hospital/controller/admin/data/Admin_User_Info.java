package hospital.controller.admin.data;

import static hospital.view.admin.AdminLoaderFactory.ADMIN_HOME;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import hospital.controller.admin.Admin_Home;
import hospital.vo.InpatientVO;
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
import javafx.scene.layout.AnchorPane;

public class Admin_User_Info implements Initializable {

	@FXML
	private AnchorPane user_info;
	@FXML
	private TableView<UserVO> user_tbl;
	@FXML
	private TableColumn<UserVO, ?> user_chk, user_id, user_name, user_regNum, user_phoneNum;
	@FXML
	private Button user_inpatient_in, user_inpatient_out, user_refresh;
	
	private Admin_Home homeCont = ADMIN_HOME.getController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		update();

		user_inpatient_in.setOnAction(e -> {
			List<UserVO> inList = user_tbl.getItems().stream().filter(vo -> vo.getChk().isSelected()).collect(Collectors.toList());
			for(UserVO u : inList) {
				int a = new Random().nextInt(5) + 5; // 층 (5~9층)
				int b = new Random().nextInt(9) +1; // 호 (1~9호)
				homeCont.helper.inpatientDAO.inpUser(new InpatientVO(u.getId(), u.getName(), Timestamp.valueOf(LocalDateTime.now()),a+"0"+b+"호"));
			}
		});
		
		user_inpatient_out.setOnAction(e->{;
			List<UserVO> inList = user_tbl.getItems().stream().filter(vo -> vo.getChk().isSelected()).collect(Collectors.toList());
			for(UserVO u : inList) {
				homeCont.helper.inpatientDAO.outpUser(u.getId());
			}
		});
		
		user_refresh.setOnAction(e->{
			update();
		});
		
		
	}

	private void update() {
		ObservableList<UserVO> list = FXCollections.observableArrayList();
		ArrayList<UserVO> userList = homeCont.helper.userDAO.selectAll();
		for (UserVO user : userList) {
			list.add(user);
		}
		columnInit();
		user_tbl.setItems(list);
	}

	private void columnInit() {
		user_chk.setCellValueFactory(new PropertyValueFactory<>("chk"));
		user_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		user_name.setCellValueFactory(new PropertyValueFactory<>("name"));
		user_regNum.setCellValueFactory(new PropertyValueFactory<>("regNum"));
		user_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
	}
	
	private void setHandler() {
		
	}

}
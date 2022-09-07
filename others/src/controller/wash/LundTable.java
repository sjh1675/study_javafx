package controller.wash;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import controller.Main;
import dao.CategoryDao;
import dao.MachineDao;
import dto.Category;
import dto.Machine;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LundTable implements Initializable{

    @FXML
    private TableView<dto.temptable> temptable2;

    @FXML
    private Button btnhome;

    @FXML
    private Label phonelbl;

    @FXML
    void acchome(ActionEvent event) {
        Main.main.loadpage2("/view/user/1번페이지(메인).fxml");     
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		phonelbl.setText(Main.main.temptable.getMphone());
		System.out.println(Main.main.temptable.getMphone());
		ObservableList<dto.temptable> templist = CategoryDao.categoryDao.list(Main.main.temptable.getMphone());
    	
		TableColumn tc = temptable2.getColumns().get(0);	
    	tc.setCellValueFactory( new PropertyValueFactory<>("mnum")); // 
    	
    	tc = temptable2.getColumns().get(1);	
    	tc.setCellValueFactory( new PropertyValueFactory<>("cname"));
    	
    	temptable2.setItems(templist);
    	System.out.println(templist);
    	temptable2.setOnMouseClicked(e->{
   
    		String b= temptable2.getSelectionModel().getSelectedItem().getCname();
    		int c= temptable2.getSelectionModel().getSelectedItem().getMnum();

    		Main.main.temptable.setCname(b);
    		Main.main.temptable.setMnum(c);
    		System.out.println(b);
    		System.out.println(c);
    		Alert alert = new Alert( AlertType.CONFIRMATION );
			alert.setHeaderText(c+"번 "+b+"에 있는 빨래를 회수하시겠습니까?");
			Optional<ButtonType> optional = alert.showAndWait();
			if( optional.get() == ButtonType.OK ) {
				Machine machine = MachineDao.machinedao.load(Main.main.temptable.getMnum());
				Category category = CategoryDao.categoryDao.load(Main.main.temptable.getMnum());
				
				
				if(category.getCname().contains("대형")) {
					LocalDateTime startDateTime = LocalDateTime.now();
					LocalDateTime etime = machine.getMtime();
					
					Duration duration = Duration.between(startDateTime, etime);
					long aft = (duration.toMinutes())*-1;
					System.out.println(aft);
					if(aft>40) {
						CategoryDao.categoryDao.ch2(Main.main.temptable.getMnum());
						CategoryDao.categoryDao.ch3(Main.main.temptable.getMnum());
						Main.main.loadpage2("/view/wash/빨래찾기테이블.fxml");
					}
					else{Alert alert2 = new Alert( AlertType.INFORMATION );
					alert2.setHeaderText("시간이 "+(40-aft)+"분 남았습니다.");
					Optional<ButtonType> optional2 = alert2.showAndWait();}
					
		    		
		    		
		    	}else if(category.getCname().contains("중형")) {
		    		LocalDateTime startDateTime = LocalDateTime.now();
		    		LocalDateTime etime = machine.getMtime();
					
					Duration duration = Duration.between(startDateTime, etime);
					long aft = (duration.toMinutes())*-1;
					System.out.println(aft);
					if(aft>30) {
						CategoryDao.categoryDao.ch2(Main.main.temptable.getMnum());
						CategoryDao.categoryDao.ch3(Main.main.temptable.getMnum());
						Main.main.loadpage2("/view/wash/빨래찾기테이블.fxml");
					}
					else{Alert alert2 = new Alert( AlertType.INFORMATION );
					alert2.setHeaderText("시간이 "+(30-aft)+"분 남았습니다.");
					Optional<ButtonType> optional2 = alert2.showAndWait();}
		    	}
				
			}
    	});
		
	}

}

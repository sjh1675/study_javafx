package controller.machine;

import java.net.URL;
import java.util.ResourceBundle;

import controller.Main;
import dao.CategoryDao;
import dao.MachineDao;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class KindChoose implements Initializable{
	
    @FXML
    private Button btnhome;

    @FXML
    private Button btnmedium;

    @FXML
    private Button btnbig;
    
    @FXML
    private TableView<dto.temptable> temptable1;

    public static dto.Machine machine;
   
    @FXML
    private Button back99;

    @FXML
    void accback99(ActionEvent event) {
    	Main.main.loadpage2("/view/user/3번페이지(세탁건조선택).fxml");
    }
    @FXML
    void accbig(ActionEvent event) {
    	String rtemp = Main.main.temptable.getCname();
    	if(rtemp.equals("세탁기")) {
    			System.out.println("세탁기연결");
    			Main.main.temptable.setCname("대형세탁기");

    	//세탁기 대형 출력
    		//테이블출력
    		Main.main.loadpage2("/view/user/4번페이지(세탁기선택)미완성.fxml");
    	}
    	if(rtemp.equals("건조기")) {
    		System.out.println("건조기연결");
    		Main.main.temptable.setCname("대형건조기");
    	
    	//건조기 대형 출력
    		//테이블출력
       		Main.main.loadpage2("/view/user/4번페이지(세탁기선택)미완성.fxml");
    	}
	 
    }

    @FXML		//홈가기
    void acchome(ActionEvent event) {
    	Main.main.loadpage2("/view/user/1번페이지(메인).fxml");
    }
    
    @FXML
    void accmedium(ActionEvent event) {
    	String rtemp = Main.main.temptable.getCname();
    	if(rtemp.equals("세탁기")) {
			System.out.println("중형세탁기연결");
    		Main.main.temptable.setCname("중형세탁기");
    	
    	//세탁기 중형 출력
    		//테이블출력
    		Main.main.loadpage2("/view/user/4번페이지(세탁기선택)미완성.fxml");
    	}
    	if(rtemp.equals("건조기")) {
    		System.out.println("중형건조기연결");
    		Main.main.temptable.setCname("중형건조기");
    	
    	//건조기 중형 출력
    		 //테이블출력
       		Main.main.loadpage2("/view/user/4번페이지(세탁기선택)미완성.fxml");
    	}
    }  
    	


	///이니셜라이즈시작///
	///////////////////////////////////////////
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {

			Main.main.temptable.setMtemperature("미설정");
			
			Main.main.temptable.setMdegree("미설정");
			System.out.println("대형중형연결성공");
	    	String rtemp = Main.main.temptable.getCname();
	    	
	    	//1. 앞에서 세탁기 선택시
	    	//1_1. 앞에서 중형세탁기 선택시
	    	if(rtemp.equals("중형세탁기")||rtemp.equals("세탁기")) {
	    		Main.main.temptable.setCname("세탁기");
	    		ObservableList<dto.temptable> templist = MachineDao.machinedao.list("중형세탁기");
	        	
	    		TableColumn tc = temptable1.getColumns().get(0);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("mnum")); // 
	        	
	        	tc = temptable1.getColumns().get(1);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("cname"));
	        	
	        	tc = temptable1.getColumns().get(2);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("mamount"));
	        	
	        	temptable1.setItems(templist);
	        	
	        	temptable1.setOnMouseClicked(e->{
	        		//세탑옵션선택페이지로 선택한 중형 대형 넘겨주기
	        		String b= temptable1.getSelectionModel().getSelectedItem().getCname();
	        		int c= temptable1.getSelectionModel().getSelectedItem().getMnum();

	        		Main.main.temptable.setCname(b);
	        		Main.main.temptable.setMnum(c);
	        		
	        		Main.main.temptable.setWholeprice(CategoryDao.categoryDao.load2(c));
	        		
	        		Main.main.loadpage("/view/user/5번페이지(세탁옵션 선택) 상단.fxml");
	        		Main.main.loadpage2("/view/user/5번페이지(세탁옵션선택)하단.fxml");
	        			
	        	});
	    	}
	    	//2. 앞에서 건조기 선택시
	    	//2_1. 앞에서 중형건조기 선택시
	    	else if(rtemp.equals("중형건조기")||rtemp.equals("건조기")) {
	    		Main.main.temptable.setCname("건조기");
	    		ObservableList<dto.temptable> templist = MachineDao.machinedao.list("중형건조기");
	        	
	    		TableColumn tc = temptable1.getColumns().get(0);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("mnum")); // 
	        	
	        	tc = temptable1.getColumns().get(1);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("cname"));
	        	
	        	tc = temptable1.getColumns().get(2);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("mamount"));
	        	
	        	temptable1.setItems(templist);
	        	
	        	temptable1.setOnMouseClicked(e->{
	        		//세탑옵션선택페이지로 선택한 중형 대형 넘겨주기
	        		String b= temptable1.getSelectionModel().getSelectedItem().getCname();

	        		int c= temptable1.getSelectionModel().getSelectedItem().getMnum();

	        		Main.main.temptable.setCname(b);
	        		Main.main.temptable.setMnum(c);
	        		Main.main.temptable.setWholeprice(CategoryDao.categoryDao.load2(c));
	        		Main.main.loadpage("/view/user/5번페이지(세탁옵션 선택) 상단.fxml");
	        		Main.main.loadpage2("/view/user/5번페이지(세탁옵션선택)하단.fxml");
	        	});
	    	}
	    	//1_2. 앞에서 대형세탁기 선택시
	    	else if(rtemp.equals("대형세탁기")) {
	    		Main.main.temptable.setCname("세탁기");
	    		ObservableList<dto.temptable> templist = MachineDao.machinedao.list("대형세탁기");
	        	
	    		TableColumn tc = temptable1.getColumns().get(0);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("mnum")); // 
	        	
	        	tc = temptable1.getColumns().get(1);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("cname"));
	        	
	        	tc = temptable1.getColumns().get(2);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("mamount"));
	        	
	        	temptable1.setItems(templist);
	        	
	        	temptable1.setOnMouseClicked(e->{
	        		//세탑옵션선택페이지로 선택한 중형 대형 넘겨주기
	        		String b= temptable1.getSelectionModel().getSelectedItem().getCname();

	        		int c= temptable1.getSelectionModel().getSelectedItem().getMnum();

	        		Main.main.temptable.setCname(b);
	        		Main.main.temptable.setMnum(c);
	        		Main.main.temptable.setWholeprice(CategoryDao.categoryDao.load2(c));
	        		Main.main.loadpage("/view/user/5번페이지(세탁옵션 선택) 상단.fxml");
	        		Main.main.loadpage2("/view/user/5번페이지(세탁옵션선택)하단.fxml");
	        	});
	    	}
	    	//2_2. 앞에서 대형건조기 선택시
	    	else if(rtemp.equals("대형건조기")) {
	    		Main.main.temptable.setCname("건조기");
	    		ObservableList<dto.temptable> templist = MachineDao.machinedao.list("대형건조기");
	        	
	    		TableColumn tc = temptable1.getColumns().get(0);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("mnum")); // 
	        	
	        	tc = temptable1.getColumns().get(1);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("cname"));
	        	
	        	tc = temptable1.getColumns().get(2);	
	        	tc.setCellValueFactory( new PropertyValueFactory<>("mamount"));
	        	
	        	temptable1.setItems(templist);
	        	
	        	temptable1.setOnMouseClicked(e->{
	        		//세탑옵션선택페이지로 선택한 중형 대형 넘겨주기
	        		String b= temptable1.getSelectionModel().getSelectedItem().getCname();

	        		int c= temptable1.getSelectionModel().getSelectedItem().getMnum();

	        		Main.main.temptable.setCname(b);
	        		Main.main.temptable.setMnum(c);
	        		Main.main.temptable.setWholeprice(CategoryDao.categoryDao.load2(c));
	        		Main.main.loadpage("/view/user/5번페이지(세탁옵션 선택) 상단.fxml");
	        		Main.main.loadpage2("/view/user/5번페이지(세탁옵션선택)하단.fxml");
	        	});
	        	
	    	}
	    	
		}
		
		
		///이니셜라이즈마무리///
///////////////////////////////////////////
    	
    	
		
		
		
	
	
}
package controller.phone_find;

import java.net.URL;
import java.util.ResourceBundle;

import controller.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

public class DialInput implements Initializable{
    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

    @FXML
    private Button btn7;

    @FXML
    private Button btn8;

    @FXML
    private Button btn9;

    @FXML
    private Button btnback;

    @FXML
    private Button btn0;

    @FXML
    private Button btncheck;

    @FXML
    private Button btnhome;

    @FXML
    void a0(ActionEvent event) {
       input("0");
    }

    @FXML
    void a1(ActionEvent event) {
       input("1");
    }

    @FXML
    void a2(ActionEvent event) {
       input("2");
    }

    @FXML
    void a3(ActionEvent event) {
       input("3");
    }

    @FXML
    void a4(ActionEvent event) {
       input("4");
    }

    @FXML
    void a5(ActionEvent event) {
       input("5");
    }

    @FXML
    void a6(ActionEvent event) {
       input("6");
    }

    @FXML
    void a7(ActionEvent event) {
       input("7");
    }

    @FXML
    void a8(ActionEvent event) {
       input("8");
    }

    @FXML
    void a9(ActionEvent event) {
       input("9");
    }

    @FXML
    void back(ActionEvent event) { // 번호 1개 씩 지우기
       if(Main.main.temptable.getMphone().substring(Main.main.temptable.getMphone().length() - 1).equals("-")) {
          Main.main.temptable.setMphone(Main.main.temptable.getMphone().substring(0,
                 Main.main.temptable.getMphone().length()-1));
       }
       Main.main.temptable.setMphone(Main.main.temptable.getMphone().substring(0,
             Main.main.temptable.getMphone().length()-1));
       Main.main.loadpage2("/view/wash/빨래전화번호하단.fxml");
    }

    @FXML
    void check(ActionEvent event) {
       if(Main.main.temptable.getMphone().length() == 9) {
    	   Main.main.loadpage2("/view/wash/빨래찾기테이블.fxml");
       }else {
          Alert alert = new Alert(AlertType.INFORMATION);
          alert.setHeaderText("올바른 번호가 아닙니다");   
          System.out.println("오류");
       }
    }

    @FXML
    void home(ActionEvent event) {
       Main.main.loadpage2("/view/user/1번페이지(메인).fxml");     
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       Main.main.temptable.setMphone("");
       
    }
    
    void input(String s) {
       if(Main.main.temptable.getMphone().length() >= 9) {
          
       }else {
          if(Main.main.temptable.getMphone().length() == 4) {
             
             Main.main.temptable.setMphone(Main.main.temptable.getMphone() + "-");
          }
          Main.main.temptable.setMphone(Main.main.temptable.getMphone() + s);
          System.out.println(Main.main.temptable.getMphone());
          Main.main.loadpage2("/view/wash/빨래전화번호하단.fxml");
       }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
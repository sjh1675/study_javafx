package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class PaymentDetail implements Initializable{

    @FXML
    private Label inputamount;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	inputamount.setText(Integer.toString(Main.main.temptable.getInputprice())); // 투입금액
    }
}
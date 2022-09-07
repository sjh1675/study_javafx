package controller.phone_find;

import java.net.URL;
import java.util.ResourceBundle;

import controller.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class DialOutput implements Initializable{
	
    @FXML
    private Label phonelbl;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
    	phonelbl.setText(Main.main.temptable.getMphone());
    }
}
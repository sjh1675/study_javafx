package controller.machine;

import java.net.URL;
import java.util.ResourceBundle;

import controller.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class main_1 implements Initializable{
	 @FXML
	 private Button bunstart;
	 
	 @FXML
	 private MediaView mediaview;
	 
	 @FXML
	 private Button bunstart2;

	 @FXML
	 void start2(ActionEvent event) {
		 Main.main.loadpage("/view/wash/빨래전화번호상단.fxml");
		 Main.main.loadpage2("/view/wash/빨래전화번호하단.fxml");

		 mediaplayer.stop();
	 }
	 
	 
	 @FXML
	 void start(ActionEvent event) {
		 Main.main.loadpage("/view/user/2페이지상단(전화번호).fxml");
		 Main.main.loadpage2("/view/wash/빨래전화번호하단.fxml");
		 mediaplayer.stop();
	 }
	
	 Media media = new Media(getClass().getResource("/img/광고1.mp4").toString());
		
	MediaPlayer mediaplayer = new MediaPlayer(media);
		
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
    	
		mediaview.setMediaPlayer(mediaplayer);
		
		mediaplayer.play();
		
		mediaplayer.setAutoPlay(true);
		mediaplayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaview = new MediaView(mediaplayer);
    }
 
   
}
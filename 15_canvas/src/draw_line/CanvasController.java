package draw_line;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CanvasController implements Initializable {

	@FXML private Canvas canvas;
	@FXML private TextArea txtArea;
	@FXML private ColorPicker pick;
	@FXML private Slider slider;
	@FXML private Button btnClear;
	
	GraphicsContext gc;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);		
		gc.setLineWidth(1);
		
		slider.setMin(1);
		slider.setMax(100);
		txtArea.setEditable(false); // readOnly
		
		canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				double x = event.getSceneX();
				double y = event.getSceneY();
				txtArea.appendText("x : " + x + ", y : " + y);
			}
		});
		
		canvas.setOnMousePressed(e -> {
			gc.beginPath();
			gc.lineTo(e.getX(), e.getY());
		});
		
		canvas.setOnMouseDragged(e ->{
			this.canvas.setOnKeyPressed(e2 -> {
				double x = e.getX();
				double y = 0;
				if(e2.getCode() == KeyCode.SHIFT) {
					y = e.getY();
				} else {
					y = e.getY();
				}
							
				txtArea.appendText("x : " + x + ", y : " + y);
				gc.lineTo(x, y);
				gc.stroke();
			});
		});
		
		btnClear.setOnAction(e ->{
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			slider.setValue(1);
			gc.setLineWidth(1);
			gc.setStroke(Color.BLACK);
		});
		
		pick.valueProperty().addListener((t, o, n) ->{
			gc.setStroke(n);
		});
		
		slider.valueProperty().addListener((t, o, n) ->{
			int value = n.intValue();
			double result = value/10;
			gc.setLineWidth(result);
		});
		
		
	}

}

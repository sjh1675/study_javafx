package bundle;
	
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		// Locale : 각 나라 지역의 [언어][나라] 등의 공통 정보를 담고 있는 class
		// Map 형태로 구현된 데이터의 묶음을 bundle이라 한다
		for (Locale locale : Locale.getAvailableLocales()) {
			System.out.printf("Display Language : %s, ", locale.getDisplayLanguage());
			System.out.printf("Language code : %s, ", locale.getLanguage());
			System.out.printf("Display Country : %s", locale.getDisplayCountry());
			System.out.printf("Country code : %s, code %s \n", locale.getCountry(), locale.toString());
		}
		
		System.out.println("========================================");
		Locale locale = Locale.getDefault();
		System.out.println(locale);
		
		locale = new Locale("ja", "JP");
		ResourceBundle bundle = ResourceBundle.getBundle("prop.s", locale); // prop 패키지 안에 있는 접두어 s로 시작하는 것 >> "prop.s" + "_" + "ko_KR"
		
		
		
		
		System.out.println(bundle.keySet());
		try {
			HBox root = (HBox)FXMLLoader.load(getClass().getResource("Bundle.fxml"), bundle); // load의 매개변수로 두번째 값에 번들 추가
			Scene scene = new Scene(root,400,400);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

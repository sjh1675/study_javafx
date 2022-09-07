package chat_server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ServerController implements Initializable {

	@FXML
	private TextArea displayText;
	@FXML
	private TextField txtPort;
	@FXML
	private Button btnStartStop;

	// Client receive thread 를 관리할 스레드 풀
	ExecutorService serverPool;
	// 운영체제에서 사용할 수 있는 PORT를 할당 받아
	// client socket 연결 관리를 할 객체
	ServerSocket server;

	// Client 사용자 정보를 저장할 map
	// <사용자닉네임, Client Socket 출력 스트림>
	Hashtable<String, PrintWriter> ht;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnStartStop.setOnAction(e -> {
			if (btnStartStop.getText().equals("START")) {
				startServer();
			} else {
				stopServer();
			}
		});
	}

	// 서버 시작
	public void startServer() {
		serverPool = Executors.newFixedThreadPool(20);
		ht = new Hashtable<>();

		try {
			String port = txtPort.getText();

			if (!checkNumber(port)) {
				printText("사용할 수 없는 포트번호입니다.");
				return;
			}
			int portNumber = Integer.parseInt(port);
			if (portNumber > 65535) {
				printText("65535 이하의 포트만 사용 가능");
				return;
			}

			server = new ServerSocket(portNumber);
			printText("[서버 시작]");
		} catch (IOException e) {
			printText("서버 연결 오류 : " + e.getMessage());
			stopServer();
			return;
		}
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Platform.runLater(() -> {
					btnStartStop.setText("STOP");
				});

				while (true) {
					try {
						printText("** CLIENT 연결 대기중 **");
						Socket client = server.accept();
						String address = client.getRemoteSocketAddress().toString();
						String message = "[연결 수락 : " + address + "]";
						ServerTask sc = new ServerTask(client, ServerController.this);
						serverPool.submit(sc);
						printText(message);
					} catch (IOException e) {
						if (server != null && !server.isClosed()) {
							stopServer();
						}
						break;
					}
				}
			}
		};
		serverPool.submit(runnable);

	}

	public void printText(String text) {
		Platform.runLater(() -> {
			displayText.appendText(text + "\n");
		});
	}

	// 서버 중지
	public void stopServer() {
		try {
			if (ht != null) {
				for (PrintWriter pw : ht.values()) {
					if (pw != null) {
						pw.close();
					}
				}
			}

			if (server != null && !server.isClosed()) {
				server.close();
			}

			if (serverPool != null && !serverPool.isShutdown()) {
				serverPool.shutdownNow();
			}
			printText("[ 서버 중지 ]");
			Platform.runLater(() -> {
				btnStartStop.setText("START");
			});
		} catch (IOException e) {
		}
	}

	public boolean checkNumber(String text) {
		if (text.trim().length() == 0) {
			return false;
		}
		for (char c : text.toCharArray()) {
			if (c < 48 || c > 57) {
				return false;
			}
		}
		return true;
	}

}

package chat_client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientController implements Initializable {

	@FXML
	private TextArea txtDisplay;
	@FXML
	private TextField txtIP, txtNick, txtInput;
	@FXML
	private ListView<String> userList;
	@FXML
	private Button btnConn, btnSend;

	// 연결된 서버의 소켓 정보
	Socket server;
	// 연결 요청을 보낼 server ip 주소
	InetAddress ip;
	// 사용자 닉네임
	String nickName;

	// 서버로 데이터 출력
	PrintWriter pw;
	// 서버에서 데이터를 읽음
	BufferedReader br;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			txtIP.requestFocus();
		});

		btnConn.setOnAction(event -> {
			try {
				String textIP = txtIP.getText().trim();
				// 문자열 ipv4 주소 값을 받아서 address로 반환
				// 정상적인 ip주소나 Domain이 아니면 예외 발생
				ip = InetAddress.getByName(textIP);
				String textNick = txtNick.getText().trim();
				if (textNick.equals("")) {
					displayText("닉네임을 작성해 주세요.");
					txtNick.requestFocus();
					return;
				}
				nickName = textNick;
				startClient();
			} catch (UnknownHostException e) {
				displayText("사용 할 수 없는 주소입니다. 주소를 확인해주세요.");
				txtIP.requestFocus();
			}
		});

		btnSend.setOnAction(e -> {
			String text = txtInput.getText().trim();
			if (text.equals("")) {
				displayText("메세지를 작성해주세요.");
				txtInput.requestFocus();
				return;
			}
			send(1, text);
		});

	} // initilize END

	// client 실행
	public void startClient() {
		try {
			// Socket이 생성될때
			// 해당 아이피와 PORT 번호로
			// 연결 요청을 전달 하고
			// 연결 수락이 완료되면 Socket 정보를 저장
			server = new Socket(ip, 5001);
			displayText("[연결 완료 : ]" + server.getRemoteSocketAddress());

			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(server.getOutputStream())), true);

			br = new BufferedReader(new InputStreamReader(server.getInputStream()));

			btnSend.setDisable(false);
			send(0, nickName);
		} catch (IOException e) {
			displayText("[서버와 연결 안됨]");
			stopClient();
			return;
		}
		receive();
	}

	// client 자원 해제
	public void stopClient() {
		try {
			displayText("[Server 연결 종료]");
			if (server != null && !server.isClosed()) {
				server.close();
			}
		} catch (IOException e) {
		}
	}

	// 서버에 데이터 전달
	public void send(int order, String text) {
		// 0|닉네임
		// 1|메세지
		pw.println(order + "|" + text);
		txtInput.clear();
		txtInput.requestFocus();
	}

	// 서버에서 데이터를 읽어 들임
	public void receive() {
		Thread t = new Thread(() -> {
			while (true) {
				try {
					String receiveData = br.readLine();
					if (receiveData == null)
						break;
					// 1|message
					// 0|사용자목록
					String[] data = receiveData.split("\\|");
					String order = data[0];
					String text = data[1];
					if (order.equals("0")) {
						// 사용자 목록 갱신
						// text : [최기근,박주신,이주명...]
						Platform.runLater(() -> {
							String[] nameList = text.split("\\,");
							userList.setItems(FXCollections.observableArrayList(nameList));
						});

					} else if (order.equals("1")) {
						displayText(text);
					}
				} catch (IOException e) {
					stopClient();
					break;
				}

			}
		});
		t.start();
	}

	// txtDisplay textarea에 text 추가
	public void displayText(String text) {
		Platform.runLater(() -> {
			txtDisplay.appendText(text + "\n");
		});
	}

}

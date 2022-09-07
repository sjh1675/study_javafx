package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import app.controller.Game;
import app.controller.Root;

public class Client {

	// 연결된 server의 socket 정보
	Socket server;
	// 사용자 닉네임
	public static String nickName;

	// server로 데이터 출력
	// send 메소드를 Start에서 불러오기 위해서 static으로 고정시킴
	static PrintWriter pw;
	// server에서 데이터 읽음
	BufferedReader br;
	
	Game game;

	// client 실행
	public void startClient() {
		try {
			// Socket 생성 될 때 ip와 port 번호로 연결 요청을 전달 하고,
			// 연결 수락이 완료 되면 연결된 server의 socket 정보로 저장
			server = new Socket("192.168.1.104", 5001);
			System.out.println(server);
			System.out.println("[ 연결 완료 : ]" + server.getRemoteSocketAddress());

			// client가 server로 메세지 출력스트림
			pw = new PrintWriter(
					new BufferedWriter(
						new OutputStreamWriter(
							server.getOutputStream())
						), true
					);
			// server에서 출력된 내용을 client가 읽어들일 스르림
			br = new BufferedReader(
					new InputStreamReader(
							server.getInputStream())
					);

		} catch (IOException e) {
			System.out.println("[ 서버와 연결 안됨 ]");
			stopClient();
			return;
		}
		// 서버에서 전달된 내용을 읽을 수 있도록 receive 호출
		receive();
	}

	// client 자원 해제
	public void stopClient() {
		try {
			System.out.println("[ Sever 연결 종료 ]");
			if (server != null && !server.isClosed()) {
				server.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// server에 데이터 전달
	public static void send(int order, String text) {
		pw.println(order + "|" + text);
	}

	// server에서 전달되는 데이터 받기
	public void receive() {
		Thread t = new Thread(()->{
			while(true){
				try {
					String receiveData = br.readLine();
					String[] strs = receiveData.split("\\|");
					System.out.println(receiveData);
					String order = strs[0];
					String text = strs[1]; // == receiveData
					// 0 닉네임
					// 1 게임시작 알림
					// 2 채팅
					switch(order) {
						case "0" :
							Root.getRoot().toEnd();
							break;
						case "1" :
							// 딜러
							game.appendText(text);
							break;
						case "2" :
							// 유저
							game.appendText(text);
							break;
					}
				} catch (IOException e) {
					stopClient();
					break;
				}
			}
		});
		// 메인 스레드가 종료되면 daemon 스레드도 같이 죽게 만듬.
		t.setDaemon(true);
		t.start();
	}

	public void setGame(Game game) {
		this.game = game;
	}
}

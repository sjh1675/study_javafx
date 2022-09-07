package chat_server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

// 연결된 client와 통신을 진행할 작업 Task
public class ServerTask implements Runnable {
	// 현재 연결된 client Socket 정보
	Socket client;
	// display 정보가 있는 controller
	ServerController sc;

	PrintWriter pw; // 연결된 client로 출력
	BufferedReader br; // 연결된 client에서 데이터 받기

	String nickName; // 사용자 닉네임

	boolean isRun = true; // receive flag

	public ServerTask(Socket client, ServerController sc) {
		this.client = client;
		this.sc = sc;

		try {
			OutputStream os = client.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			pw = new PrintWriter(bw, true); // autoflush

			InputStream is = client.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

		} catch (IOException e) {
			sc.printText("Client 연결 오류 :" + e.getMessage());
		}

	}

	// receive
	@Override
	public void run() {
		while (isRun) {
			try {
				String receiveData = br.readLine();
				System.out.println(receiveData);
				if (receiveData == null) {
					break;
				}
				// 0|닉네임
				// 1|message
				String[] data = receiveData.split("\\|");
				System.out.println(data);
				String order = data[0];
				String text = data[1];
				switch (order) {
				case "0":
					// 닉네임을 이용해서 hashtable에 client 정보 등록
					// 이미 존재하는 닉네임은 재요청 할 수 있도록 연결 종료
					if (sc.ht.containsKey(text)) {
						// 이미 존재하는 닉네임으로 연결 요청
						this.pw.println("1|사용할 수 없는 아이디입니다. 다시 요청해 주세요.");
						sc.printText(text + "- 사용 할 수 없는 닉네임으로 요청");
						isRun = false;
						this.client.close();
						break;
					}

					this.nickName = text;
					sc.ht.put(text, this.pw);

					// 사용자 목록 갱신
					String sendData = "";
					for (String s : sc.ht.keySet()) {
						sendData += s + ",";
					}
					// 0|최기근,남은시간,하옥형지각...
					broadCast(0, sendData);
					broadCast(1, text + "님이 입장 하셨습니다 - 방인원은 : " + sc.ht.size());
					break;
				case "1":
					// 메세지 전달
					broadCast(1, this.nickName + " : " + text);
					break;
				}
			} catch (IOException e) {
				System.out.println("Client 연결 오류 : " + e.getMessage());
				isRun = false;
			}
		} // end while

		if (client != null && !client.isClosed()) {
			try {
				client.close();
			} catch (IOException e) {
			}
		}

		sc.ht.remove(this.nickName);
		String list = "";
		for (String s : sc.ht.keySet()) {
			list += s + ",";
		}
		broadCast(0, list);
		broadCast(1, nickName + "님이 나가셨습니다. 방인원 : " + sc.ht.size());

	}

	// 연결된 모든 사용자 Client에 메세지 전달
	public void broadCast(int order, String msg) {
		for (PrintWriter p : sc.ht.values()) {
			p.println(order + "|" + msg);
		}
	}

}

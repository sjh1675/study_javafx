package hospital.network.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import hospital.controller.user.User_Home;
import hospital.controller.user.chat.User_Chat;
import hospital.controller.user.data.User_Edit;
import hospital.controller.user.data.User_Join;
import hospital.controller.user.data.User_Login;
import hospital.controller.user.data.User_Reserv;
import hospital.vo.UserVO;

import static hospital.view.user.UserLoaderFactory.*;
import javafx.application.Platform;

public class UserHelper {
	Socket socket;

	UserVO user;

	public void startClient(String IP, int port) {
		Thread thread = new Thread(() -> {
			try {
				socket = new Socket(IP, port);
				System.out.println("[서버 접속]");
				receive();
			} catch (Exception e) {
				if (!socket.isClosed()) {
					stopClient();
					System.out.println("서버 접속 실패");
				}
			}
		});
		thread.start();
	}

	public void stopClient() {
		try {
			if (socket != null && !socket.isClosed()) {
				System.out.println("[프로그램 종료]");
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void receive() {
		while (true) {
			try {
				InputStream is = socket.getInputStream();
				byte[] buffer = new byte[512];
				int length = is.read(buffer);
				if (length == -1) {
					throw new IOException();
				}
				System.out.println("[메세지 수신]");
				String message = new String(buffer, 0, length, "UTF-8");

				// 명령 분기
				if (message.equals("$중복아님")) {
					User_Join joinCont = USER_JOIN.getController();
					joinCont.setID(true);
				} else if (message.equals("$중복이다")) {
					User_Join joinCont = USER_JOIN.getController();
					joinCont.setID(false);
				} else if (message.equals("$회원가입성공")) {
					User_Join joinCont = USER_JOIN.getController();
					joinCont.setJoin(true);
				} else if (message.equals("$회원가입실패")) {
					User_Join joinCont = USER_JOIN.getController();
					joinCont.setJoin(false);
				} else if (message.contains("$로그인성공")) {
					User_Login loginCont = USER_LOGIN.getController();
					loginCont.setLoginCheck(true);
					loginCont.setDuplCheck(true);
					String[] values = message.split(",");
					if(values.length < 6) {
						user = new UserVO(values[1], values[2], values[3], values[4], null);
					} else {
						user = new UserVO(values[1], values[2], values[3], values[4], values[5]);
					}
				} else if (message.equals("$로그인실패")) {
					User_Login loginCont = USER_LOGIN.getController();
					loginCont.setDuplCheck(true);
					loginCont.setLoginCheck(false);
				} else if (message.equals("$회원정보수정완료")) {
					User_Edit editCont = USER_EDIT.getController();
					editCont.setUpdateCheck(true);
				} else if (message.equals("$$회원정보수정실패")) {
					User_Edit editCont = USER_EDIT.getController();
					editCont.setUpdateCheck(false);
				} else if (message.contains("$날짜확인")) {
					String[] values = message.split(",");
					User_Reserv reservCont = USER_RESERV.getController();
					reservCont.listUpdate(values);
				} else if (message.contains("$예약성공")) {
					User_Reserv reservCont = USER_RESERV.getController();
					reservCont.setReservCheck(true);
				} else if (message.contains("$예약실패")) {
					User_Reserv reservCont = USER_RESERV.getController();
					reservCont.setReservCheck(false);
				} else if (message.contains("$중복로그인")) {
					User_Login loginCont = USER_LOGIN.getController();
					loginCont.setDuplCheck(false);
					loginCont.setLoginCheck(true);
				}else { // 채팅
					User_Chat chat = USER_CHAT.getController();
					Platform.runLater(() -> {
						chat.chat_text_room.appendText(message + "\n");
					});
				}
			} catch (Exception e) {
				System.out.println("[수신 오류] :" + e.getMessage());
				e.printStackTrace();
				stopClient();
				break;
			}
		}
	}

	public void send(String message) {
		Thread thread = new Thread(() -> {
			try {
				OutputStream os = socket.getOutputStream();
				byte[] buffer = message.getBytes("UTF-8");
				os.write(buffer);
				os.flush();
				System.out.println("[메세지 전송]");
			} catch (Exception e) {
				System.out.println("[전송 오류]");
				stopClient();
			}
		});
		thread.start();
	}

	public void userSet() {
		User_Home homeCont = USER_HOME.getController();
		homeCont.setLoginUser(this.user);
	}

}
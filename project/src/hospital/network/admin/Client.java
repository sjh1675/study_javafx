package hospital.network.admin;

import static hospital.view.admin.AdminLoaderFactory.ADMIN_CHAT;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;

import hospital.controller.admin.chat.Admin_Chat;
import hospital.vo.ReservVO;
import hospital.vo.UserVO;
import javafx.application.Platform;

public class Client {

	Socket socket;

	public UserVO user;

	public Client(Socket socket) {
		this.socket = socket;
		receive();
		chatUpdate();
	}

	public void chatUpdate() {
		Runnable thread = () -> {
			Admin_Chat chatCont = ADMIN_CHAT.getController();
			chatCont.update();
		};
		ServerHelper.threadPool.submit(thread);
	}

	public void receive() {
		Runnable thread = () -> {
			try {
				while (true) {
					InputStream is = socket.getInputStream();
					byte[] buffer = new byte[512];
					int length = is.read(buffer);
					if (length == -1)
						throw new IOException();
					String message = new String(buffer, 0, length, "UTF-8");
					System.out.println("메세지 수신 성공" + message + socket.getRemoteSocketAddress() + ":"
							+ Thread.currentThread().getName());

					// 명령 분기
					if (message.contains("$중복체크")) { // 회원가입 아이디 중복체크
						UserVO user = ServerHelper.userDAO.userSelect(message.substring(5));
						if (user == null) {
							this.send("$중복아님");
						} else {
							this.send("$중복이다");
						}

					} else if (message.contains("$회원가입")) { // 회원가입
						String[] values = message.split(",");
						UserVO user = new UserVO(values[1], values[2], values[3], values[4], values[5]);
						int result = ServerHelper.userDAO.userJoin(user);

						if (result == 1) {
							this.send("$회원가입성공");
						} else {
							this.send("$회원가입실패");
						}

					} else if (message.contains("$로그인")) { // 로그인
						String[] values = message.split(",");
						UserVO user = ServerHelper.userDAO.userLogin(values[1], values[2]);

						if (user != null) {
							for (Client c : ServerHelper.clients) {
								UserVO u = c.user;
								if (user.equals(u)) {
									this.send("$중복로그인");
									return;
								}
							}

							this.send("$로그인성공," + user.getId() + "," + user.getPassword() + "," + user.getName() + ","
									+ user.getRegNum() + "," + user.getPhoneNum());

							this.user = user;
							chatUpdate();
						} else {
							this.send("$로그인실패");
						}
					} else if (message.contains("$회원정보수정")) { // 회원정보수정
						String[] values = message.split(",");
						UserVO user = new UserVO(this.user.getId(), values[1], this.user.getName(),
								this.user.getRegNum(), values[2]);
						System.out.println(user);
						int result = ServerHelper.userDAO.userUpdate(user);
						if (result == 1) {
							this.send("$회원정보수정완료");
							this.user = null;
							chatUpdate();
						} else {
							this.send("$회원정보수정실패");
						}
					} else if (message.contains("$회원탈퇴")) {
						int result = ServerHelper.userDAO.userDelete(this.user.getId());
						if (result == 1) {
							this.send("$회원탈퇴완료");
							this.user = null;
							chatUpdate();
						} else {
							this.send("$회원탈퇴실패");
						}

					} else if (message.contains("$날짜확인")) {
						String[] values = message.split(",");
						String date = values[1] + " 00:12:34";
						Timestamp timeStamp = Timestamp.valueOf(date);
						ArrayList<ReservVO> list = ServerHelper.reservDAO.reservSelectDay(timeStamp);
						StringBuilder sb = new StringBuilder();
						for (ReservVO r : list) {
							String str = r.getTime().toString();
							String time = str.substring(11, 13);
							sb.append(",").append(time);
						}
						this.send("$날짜확인" + sb);
					} else if (message.contains("$진료예약")) {
						String[] values = message.split(",");
						ReservVO reserv = new ReservVO(values[1], values[2], Timestamp.valueOf(values[3]));
						int result = ServerHelper.reservDAO.reservInsert(reserv);
						if (result == 1) {
							this.send("$예약성공");
						} else {
							this.send("$예약실패");
						}
					} else { // 채팅
//						for (Client client : ServerHelper.clients) { // 전체 회원에게 출력
//							client.send(message);
//						}

						this.send(message); // 개인 회원에게 전달

						Platform.runLater(() -> { // 관리자 채팅창에 메세지 출력
							Admin_Chat chat = ADMIN_CHAT.getController();
							chat.chat_text_room.appendText(message + "\n");
						});

					}
				}

			} catch (IOException e) {
				try {
					System.out.println(
							"메세지 수신 오류" + socket.getRemoteSocketAddress() + ": " + Thread.currentThread().getName());
					ServerHelper.clients.remove(Client.this);
					socket.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		ServerHelper.threadPool.submit(thread);
	}

	public void send(String message) {
		Runnable thread = () -> {
			try {
				OutputStream os = socket.getOutputStream();
				byte[] buffer = message.getBytes("UTF-8");
				os.write(buffer);
				os.flush();
				System.out.println("메세지 전송 --" + message + socket.getRemoteSocketAddress() + ": "
						+ Thread.currentThread().getName());
			} catch (Exception e) {
				try {
					System.out.println(
							"메세지 송신 오류" + socket.getRemoteSocketAddress() + ": " + Thread.currentThread().getName());
					ServerHelper.clients.remove(Client.this);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		};
		ServerHelper.threadPool.submit(thread);
	}

}
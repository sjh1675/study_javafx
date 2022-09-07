package hospital.network.admin;

import static hospital.view.admin.AdminLoaderFactory.ADMIN_CHAT;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hospital.controller.admin.chat.Admin_Chat;
import hospital.dao.inpatient.InpatientDAO;
import hospital.dao.inpatient.InpatientDAOImpl;
import hospital.dao.reserv.ReservDAO;
import hospital.dao.reserv.ReservDAOImpl;
import hospital.dao.user.UserDAO;
import hospital.dao.user.UserDAOImpl;
import hospital.vo.UserVO;

/**
 * 데이터 통신을 도와주는 클래스
 * 
 * @author 제승후
 *
 */
public class ServerHelper {

	public static ExecutorService threadPool;
	public static Vector<Client> clients = new Vector<Client>();
//	public static ArrayList<UserVO> list = new ArrayList<>();
	public static UserDAO userDAO;
	public static ReservDAO reservDAO;
	public static InpatientDAO inpatientDAO;
	ServerSocket serverSocket;

	/**
	 * 서버 오픈 및 클라이언트 접속 대기
	 * 
	 * @param IP   -- 서버 IP
	 * @param port -- 서버 PORT
	 */
	public void startServer(String IP, int port) {
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(IP, port));
			userDAO = new UserDAOImpl();
			reservDAO = new ReservDAOImpl();
			inpatientDAO = new InpatientDAOImpl();
			System.out.println("[서버 오픈]");
		} catch (Exception e) {
			e.printStackTrace();
			if (!serverSocket.isClosed()) {
				stopServer();
			}
			return;
		}

		// 클라이언트 접속 대기
		Runnable thread = () -> {
			while (true) {
				System.out.println("[클라이언트 접속 대기]");
				try {
					Socket socket = serverSocket.accept();
					clients.add(new Client(socket));
					System.out.println(
							"[클라이언트 접속]" + socket.getRemoteSocketAddress() + ": " + Thread.currentThread().getName());
				} catch (Exception e) {
					if (!serverSocket.isClosed()) {
						stopServer();
					}
					break;
				}
			}
		};
		threadPool = Executors.newCachedThreadPool();
		threadPool.submit(thread);
	}

	/**
	 * 서버 종료
	 */
	public void stopServer() {
		try { // 현재 작동 중인 모든 소켓 종료
			Iterator<Client> iterator = clients.iterator();
			while (iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();
				iterator.remove();
			}

			if (serverSocket != null && !serverSocket.isClosed()) {
				System.out.println("[서버 종료]");
				serverSocket.close();
			}
			if (threadPool != null && !threadPool.isShutdown()) {
				threadPool.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 캐싱 리스트 업데이트
	 */
	// 여유가 되면 도전해볼 것
//	public void listUpdate() {
//		Runnable thread = () -> {
//			ArrayList<UserVO> temp = userDAO.selectAll();
//			list = temp;
//		};
//		threadPool.submit(thread);
//	}

}

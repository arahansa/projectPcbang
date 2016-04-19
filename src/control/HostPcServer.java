package control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class HostPcServer extends Thread {
	Vcontrol vc = Vcontrol.getInstance("호스트서버"); // 싱글톤불러오기;
	ServerReceiver receiver = null;

	// 서버스타트
	public void startFromFrame(int i) {
		if (i == 1)
			vc.selectFrame(1);
		else
			vc.selectFrame(2);
	}

	public void run() {

		ServerSocket serverSocket = null;
		Socket socket = null;

		try {
			serverSocket = new ServerSocket(7777);
			System.out.println("호스트 피시 : " + "PC방 호스트가 시작됩니다");

			// 접속을 계속 받아내는 쓰레드~
			while (true) {
				socket = serverSocket.accept();
				System.out.println("호스트 피시 : " + "[" + socket.getInetAddress()
						+ "]에서 접속하였다!");

				receiver = new ServerReceiver(socket);
				receiver.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 내부 리시버 클래스 - 이 클래스는 연결이 생길때마다 계속 생긴다.
	class ServerReceiver extends Thread {
		Socket socket;
		DataInputStream in;
		DataOutputStream out;

		// 생성자에서는 서버소켓을 받아서 인풋아웃풋 스트림을 하나 만들고 연결한다~
		ServerReceiver(Socket socket) {
			this.socket = socket;

			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				System.out.println("호스트 피시 : 인아웃 생성완료!");
			} catch (IOException e) {
				System.out.println("호스트 피시 : " + "서버 리시버 소켓 입출력 에러");
			}
		}// 생성자 끝

		// 리시버 스타트~
		public void run() {
			int num = 0;
			String name = "";

			try {
				num = in.readInt();
				name = in.readUTF();
				System.out.println("호스트 피시 : " + "자리 번호는 " + num);
				System.out.println("호스트 피시 : " + "이 사람의 이름은 " + name + "입니다.");

				// 컴퓨터 켜진 상태로 만든다.
				vc.turnOn(num);
				// 네트워크 연결중 요청 메시지 계속 받기 처리
				while (in != null) {
					String s = in.readUTF();
					switch (s) {
					case "로그인":
						vc.newSeat(num, name, socket);
						vc.login(num, name);
						break;
					case "로그아웃":
						vc.logout(num);
						break; // 화면변환 메소드
					case "컴퓨터끔":
						break;
					case "메시지":
						String message = in.readUTF();
						vc.messageFromPC(num, message);
						break; // 메시지처리 메소드
					case "주문":
						int seatNum = in.readInt();
						String getItem = in.readUTF();
						int getNum = in.readInt();
						int getPrice = in.readInt();
						System.out.println(seatNum + "석에서 주문받았다~");
						JOptionPane.showMessageDialog(null, "주문 :" + getItem
								+ "을/를 " + getNum
								+ "개 주문 받았습니다. \n 가격은 알아서 올렸습니다." + seatNum
								+ "석으로 배달해 알바야~");
						System.out.println(vc.pcseat[seatNum] + "이 현재 돈입니다.");
						vc.pcseat[seatNum].setMoney(getPrice
								+ vc.pcseat[seatNum].getMoney());

						break;
					}
				}
			} catch (IOException e) {
				System.out.println("호스트피시: " + "클라이언트와의 접속중 에러 : 나가거나..");
			} finally {
				// 컴퓨터 꺼지다
				vc.turnOff(num);
				System.out.println("호스트피시: " + "컴퓨터가 꺼짐~");

			}
		}// 런끝
	}
}

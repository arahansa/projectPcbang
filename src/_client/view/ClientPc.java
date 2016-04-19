package _client.view;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.net.*;
import java.io.*;

//현재 사용중인 피시방 이용자의 이용시간과 이용요금창을 보여준다
public class ClientPc {// 클라이언트 클래스 시작
	
	private String id; // 현재 사용중인 아이디 저장
	private String pc; // 현재 사용중인 피시 번호 저장
	private JFrame clFrame;
	private JLabel userId;
	private JLabel userPc;
	private JLabel userTime;
	private JLabel userPrice;
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	private ClientChat chat;
	private Menu menu;
	protected static boolean doClient=true;

	ClientPc(String id, String pc) {// 클라이언트 생성자시작

		this.id = id;
		this.pc = pc;
		clFrame = new JFrame("이용중");

		// 현재 스크린사이즈를 받아온다
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;

		// 표시라벨
		userId = new JLabel(id);
		userPc = new JLabel(pc);
		userTime = new JLabel("");
		userPrice = new JLabel("");

		JLabel pc_label = new JLabel("피씨번호:");
		JLabel id_label = new JLabel("고객아이디:");
		JLabel time_label = new JLabel("이용시간:");
		JLabel price_label = new JLabel("이용요금:");

		// 버튼
		JButton chatBtn = new JButton("채팅");
		JButton menuBtn = new JButton("메뉴표");

		// 컴포넌트가 붙을 패널 생성
		JPanel panel = new JPanel();

		// 컴포넌트 배치부
		pc_label.setBounds(30, 30, 95, 30);
		id_label.setBounds(30, 5, 95, 30);
		time_label.setBounds(30, 55, 95, 30);
		price_label.setBounds(30, 80, 95, 30);
		userId.setBounds(130, 5, 95, 30);
		userPc.setBounds(130, 30, 95, 30);
		userTime.setBounds(130, 55, 95, 30);
		userPrice.setBounds(130, 80, 95, 30);
		chatBtn.setBounds(30, 120, 95, 30);
		menuBtn.setBounds(150, 120, 95, 30);

		// 컴포넌트 결합부
		panel.add(pc_label);
		panel.add(id_label);
		panel.add(time_label);
		panel.add(userId);
		panel.add(userTime);
		panel.add(userPc);
		panel.add(userPrice);
		panel.add(chatBtn);
		panel.add(menuBtn);
		panel.add(price_label);
		panel.setLayout(null);
		clFrame.add(panel);

		// 버튼 이벤트 처리부
		chatBtn.addActionListener(new ChatEvent());
		menuBtn.addActionListener(new MenuEvent());

		// 현재 프레임 위치 및 크기
		clFrame.setBounds(width - 300, height / 5 - 100, 270, 200);
		clFrame.setResizable(false);

		// 유저가 창을 강제 종료시키면 안되므로
		clFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		clFrame.setVisible(true);
		// 소켓 쓰레드시작
		new Thread(new ClientConnector()).start();

	}// 클라이언트 생성자종료

	// 챗이벤트클래스 시작(채팅창을 불러온다)
	private class ChatEvent implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			if (chat == null) {
				chat = new ClientChat(out, pc);
				return;
			}
			chat.chatFrameVisible();

		}

	}
	// 챗이벤트 클래스 종료
	// 메뉴표를 불러오기 위한 이벤트 클래스 시작
	private class MenuEvent implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			Menu menu = new Menu(out,Integer.parseInt(pc));

		}

	}

	// 메뉴표를 불러오기 위한 이벤트 클래스 종료
	// 서버와 연결을 위한 클라이언트 커넥터
	private class ClientConnector implements Runnable {

		@Override
		public void run() {
			try {
				String serverIp = "127.0.0.1";// "172.168.0.80";
				socket = new Socket(InetAddress.getByName(serverIp), 7777);
				System.out.println("연결성공");
				in = new DataInputStream(new BufferedInputStream(
						socket.getInputStream()));
				out = new DataOutputStream(new BufferedOutputStream(
						socket.getOutputStream()));

				int pcNum = Integer.parseInt(pc);
				out.writeInt(pcNum);
				out.writeUTF(id);
				out.writeUTF("로그인");
				out.flush();

				while (true) {
					String str = in.readUTF();
					// 이용요금 처리부
					if (str.equals("요금정보")) {
						Integer money = in.readInt();
						userPrice.setText(money.toString());
						userTime.setText(in.readUTF());
					}
					// 채팅메시지 처리부
					if (str.equals("메시지")) {
						String msg = in.readUTF();
						System.out.println(msg);
						if (chat == null) {
							chat = new ClientChat(out, pc);
						}
						chat.chatFrameVisible();
						chat.addChat(msg);

					}
					// 로그아웃 처리부
					if (str.equals("로그아웃")) {
						
						socket.close();
					}
				}

			} catch (IOException e) {// 서버와 연결이 끊어질시 창이 변함

				if (chat != null) {
					chat.closeFrame();
				}
				doClient=false;
				clFrame.dispose();
				ClientLogin cl = new ClientLogin();

			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}// 클라이언트 커넥터종료

}// 클라이언트 클래스 종료

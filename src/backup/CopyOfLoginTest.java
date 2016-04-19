package backup;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.rmi.ConnectException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CopyOfLoginTest {

	static String nick = null;
	static JTextArea ta;
	static JLabel label, label3, label2;
	static JTextField tf,tf2;
	static DataOutputStream out;
	// gui 클래스
	@SuppressWarnings("serial")
	static class FrClient extends JFrame implements ActionListener {

		public FrClient(String nick) {
			setSize(600, 400);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setTitle(nick + "의 채팅방 클라이언트!");
			setLayout(new BorderLayout());

			label = new JLabel("사용시간 : \n");
			Font f = new Font("Serif", 1, 20);
			label.setFont(f);
			label3 = new JLabel("요금");
			label3.setFont(f);
			label2 = new JLabel("채팅제외 커맨드입력");
			JPanel uppanel=new JPanel();
			uppanel.setLayout(new GridLayout(1,3));
			ta = new JTextArea(25, 40);
			uppanel.add(label);
			uppanel.add(label3);
			uppanel.add(ta);
			

			JPanel panel = new JPanel();
			panel.setLayout(new FlowLayout());
			panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			JLabel label_name = new JLabel(nick + "님 ");
			tf = new JTextField(15);
			tf2 = new JTextField(15);
			tf.addActionListener(this);
			tf2.addActionListener(this);
			
			panel.add(label_name);
			panel.add(tf);
			panel.add(label2);
			panel.add(tf2);
			

			
			add(uppanel, BorderLayout.CENTER);
			add(panel, BorderLayout.SOUTH);
			
			setVisible(true);

		}

		// - 메시지 전송
		@Override
		public void actionPerformed(ActionEvent e) {
			//채팅 메시지 입력
			if (e.getSource() == tf) {
				// 이거는 그냥 gui 에 나오게 하기 위한 것이다~
				// ta.append(nick + " : "+tf.getText()+"\n");
				try {
					out.writeUTF("메시지");
					out.writeUTF(tf.getText());
					tf.setText("");
				} catch (IOException e1) {
					System.out.println("gui  상에서 메시지 보내는게 에러");
				}
			//커맨드 라인 입력
			}else if(e.getSource()==tf2){
				try {
					out.writeUTF(tf2.getText());
					tf2.setText("");
				} catch (IOException e1) {
					System.out.println("gui  상에서 메시지 보내는게 에러");
				}
			}
		}
	}//gui클래스끝 
	public static void main(String args[]) {
		
			
		//nick = JOptionPane.showInputDialog("당신의 대화명은?");
		//int num=Integer.parseInt(JOptionPane.showInputDialog("당신의 자리번호는?"));
		nick = "실험용닉";
		int num =5; //자리
		
		@SuppressWarnings("unused")
		FrClient f = new FrClient(nick);
		Socket socket;
		
		try {
			String serverIp = "172.168.0.80";
			//String serverIp = "127.0.0.1";
			
			// 소켓을 생성하여 연결을 요청
			socket = new Socket(serverIp, 7777);
			System.out.println("서버에 연결되었습니다");
			
			Thread receiver = new ClientReceiver(socket);
			receiver.start();
			Thread sender = new ClientSender(socket, nick, num);
			sender.start();

		} catch (ConnectException e) {
			e.printStackTrace();
			System.out.println("접속에러");
		} catch (Exception e) {
			System.out.println(" 에러는 에러");
		}

	}// main

	static class ClientSender extends Thread {
		Socket socket;
		String name;
		int num;
		ClientSender(Socket socket, String name, int num) {
			this.socket = socket;
			try {
				out = new DataOutputStream(socket.getOutputStream());
				this.name = name;
				this.num = num;
			} catch (Exception e) {
				System.out.println("클라이언트 생성자 중 에러");
			}
		}

		// - 콘솔 메세지받기문
		public void run() {
			Scanner scanner = new Scanner(System.in);
			try {
				// 이것은 접속하면서 대화명 날리는 if문이다.
				
				//좌석번호를 출력
				if (out != null) {
					out.writeInt(num);
				}
				if (out != null) {
					out.writeUTF(name);
				}
				
				
				while (out != null) {
					out.writeUTF("[" + name + "]" + scanner.nextLine());

				}
			} catch (IOException e) {
				System.out.println(" 클라이언트 센더 실행중 io 에러");
			}
		}// run
	}// clientSender

	// 리시버 시작
	static class ClientReceiver extends Thread {
		Socket socket;
		DataInputStream in;

		ClientReceiver(Socket socket) {
			this.socket = socket;

			try {
				in = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				System.out.println("클라이언트 : 리시버 실행중 에러 : 아마도 서버 종료");
			}
		}

		// - 메세지 받기 In
		public void run() {
				try {
					while (in != null) {
					String s = in.readUTF();
					if(s.equals("요금정보")){
						int money = in.readInt();
						String gametime=in.readUTF();
						label.setText(gametime+"\n 사용요금 : "+money);
					}else if(s.equals("메시지")){
						String message= in.readUTF();
						ta.append(message+"\n");
					}
					System.out.println(s);
					ta.append(s + "\n");
					}
				} catch (IOException e) {
					System.out.println("강용로그인테스트 : 리시버 실행중 에러 : 서버나간듯.");
				}
			
		}

	}// 클라이언트 리시버 끝


}// class
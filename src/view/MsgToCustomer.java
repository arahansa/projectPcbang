package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.Vcontrol;

@SuppressWarnings("serial")
public class MsgToCustomer extends JFrame implements ActionListener {
	Vcontrol vc = Vcontrol.getInstance("메시지를손님께");

	public JTextArea ta = new JTextArea(25, 25);
	JTextField tf = new JTextField(15);
	public boolean flag;

	int num;
	Socket socket;
	DataInputStream in;
	DataOutputStream out;

	public MsgToCustomer(int num) {
		this.num = num;
		flag = true;
		setSize(300, 300);

		// 프레임 화면 중앙 배열
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) ,
				(screenSize.height - frameSize.height) );

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(num + "손님과의 메시지보내기창");
		setLayout(new BorderLayout());

		JPanel downPanel = new JPanel();
		JLabel label = new JLabel("관리자 메시지 : ");
		downPanel.add(label);
		downPanel.add(tf);

		tf.addActionListener(this);
		add(ta, BorderLayout.CENTER);
		add(downPanel, BorderLayout.SOUTH);
		setVisible(true);

		// 소켓을 받아온다.

		try {
			System.out.println(num + "자리.클라이언트에서 소켓얻어오기!");
			/** 중요 여기서 삽질좀함.. vc에서 받아올때는 vc.pcseat */
			socket = vc.clients.get(vc.pcseat[num]);
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("메세지를손님께 : 아웃 스트림 받아오기 실패");
		}

		Thread receiver = new ChatReceiver();
		receiver.start();

	}

	public static void main(String[] args) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tf) {
			// 이거는 그냥 gui 에 나오게 하기 위한 것이다~
			// ta.append(nick + " : "+tf.getText()+"\n");
			try {

				out.writeUTF("메시지");
				out.writeUTF("PC방주인 : " + tf.getText());
				ta.append("PC방주인 : " + tf.getText() + "\n");
				tf.setText("");

			} catch (IOException e1) {
				System.out.println("gui  상에서 메시지 보내는게 에러");
			}
		}

	}// 액션끝

	// 리시버 시작
	class ChatReceiver extends Thread {
		public void run() {
			while (in != null) {
				try {
					String s = in.readUTF();
					System.out.println(s);
					ta.append(s + "\n");
				} catch (IOException e) {
					System.out.println("채팅 리시버 메소드 실행중 입출력 에러");
				}
			}
		}
	}// 클라이언트 리시버 끝

}

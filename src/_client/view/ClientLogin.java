package _client.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import _client.dbprocess.LoginMemberProcess;

public class ClientLogin {// clientlogin 클래스 시작

	private JTextField id;
	private JTextField pc;
	private JPasswordField pass;
	private JFrame login;

	ClientLogin()// clientlogin 생성자 시작부
	{
		// 프레임 생성
		login = new JFrame("로그인");

		// 라벨 생성
		JLabel id_label = new JLabel("아이디");
		JLabel pass_label = new JLabel("비밀번호");
		JLabel pc_label = new JLabel("피씨번호");
		JPanel panel = new JPanel();

		// 버튼 생성
		JButton join_btn = new JButton("회원가입");
		JButton log_btn = new JButton("로그인");
		JButton exit_btn = new JButton("종료");

		// id,pass,pc객체 생성
		id = new JTextField();
		pass = new JPasswordField();
		pc = new JTextField();

		// 아이디 필드와 패스워드 필드 입력글자수 제한
		id.setDocument(new JTextFieldLimit(10));
		pass.setDocument(new JTextFieldLimit(10));
		pc.setDocument(new JTextFieldLimit(2));

		// 현재 스크린사이즈를 받아온다
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;

		// //컴포넌트 배치부
		id_label.setBounds(30, 5, 95, 30);
		pass_label.setBounds(30, 30, 95, 30);
		pc_label.setBounds(30, 55, 95, 30);
		id.setBounds(100, 10, 95, 20);
		pass.setBounds(100, 35, 95, 20);
		pc.setBounds(100, 60, 65, 20);
		join_btn.setBounds(5, 90, 90, 30);
		log_btn.setBounds(100, 90, 80, 30);
		exit_btn.setBounds(185, 90, 60, 30);

		// 컴포턴트 결합부
		panel.add(id);
		panel.add(pass);
		panel.add(id_label);
		panel.add(pass_label);
		panel.add(join_btn);
		panel.add(log_btn);
		panel.add(exit_btn);
		panel.add(pc_label);
		panel.add(pc);
		panel.setLayout(null); // 앱솔루트 레이아웃
		login.add(panel);
		// 버튼에 이벤트 리스너 결합부
		exit_btn.addActionListener(new ExitProcess());
		join_btn.addActionListener(new JoinProcess());
		log_btn.addActionListener(new LoginProcess());

		login.setBounds(width - 300, height / 5 - 100, 270, 150);
		login.setResizable(false);
		login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		login.setVisible(true);

	}// client_login 생성자 종료부

	// JTextFieldLimit클래스 시작(아이디 비번 입력 글자수 제한)

	private class JTextFieldLimit extends PlainDocument {
		private int limit; // 제한할 길이

		private JTextFieldLimit(int limit) // 생성자 : 제한할 길이를 인자로 받음
		{
			super();
			this.limit = limit;
		}

		// 텍스트 필드를 채우는 메써드 : 오버라이드
		public void insertString(int offset, String str, AttributeSet attr)
				throws BadLocationException {
			if (str == null)
				return;
			if (getLength() + str.length() <= limit)
				super.insertString(offset, str, attr);
		}
	}// JTextFieldLimit클래스 종료

	// ExitProcess 클래스 시작 (종료이벤트 구현)
	private class ExitProcess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);

		}

	}

	// ExitProcess 클래스 종료(종료이벤트 구현)

	// JoinProcess 클래스 시작 (회원가입 창을 띄운다)
	private class JoinProcess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JoinMember join = new JoinMember();
		}

	}

	// JoinProcess 클래스 종료

	// LoginProcess 클래스 시작 (login을 처리한다)
	private class LoginProcess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			try {
				Integer.parseInt(pc.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "피씨번호를 입력하세요.");
				return;
			}
			if (id.getText().equals("비회원")) {
				JOptionPane.showMessageDialog(null,
						"아이디 \'비회원\' 으로 로그인 할 수 없습니다.");
			} else if (id.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "아이디 \'비회원\' 으로 로그인 합니다.");
				login.dispose();
				ClientPc cl = new ClientPc("비회원", pc.getText());

			} else {

				boolean logIn = LoginMemberProcess.loginMember(id.getText(),
						pass.getText());
				if (logIn == false) {
					JOptionPane.showMessageDialog(null,
							"아이디가 존재하지 않거나 잘 못 입력하셨습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "로그인 되었습니다.");
					login.dispose();
					ClientPc.doClient=true;
					ClientPc cl = new ClientPc(id.getText(), pc.getText());

				}

			}
		}

	}
	// LoginProcess 클래스 종료
}// client_login 클래스 종료부

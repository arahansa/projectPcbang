package view;

/**
 * 기본 로그인 페이지.. 아이디창은 tf 비밀번호창은 tf2 버튼은 bt
 * Login.java에서 호출되어서 버튼이 눌릴때 ManageFrame을 호출한다.
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.text.*;

import control.*;


public class Login_Fr extends JFrame implements ActionListener {
	Vcontrol vc = Vcontrol.getInstance();
	// 이미지와 버튼은 전역변수설정
	BufferedImage img = null;
	JButton bt;
	JTextField tf;
	JPasswordField tf2;
	Manage_Fr mf;
	
	public static void main(String[] args) {
		new Login_Fr();
	}

	public Login_Fr() {
		
		
		// 프레임 기본 설정
		setTitle("Load Image test");
		setSize(1024, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.black);
		setLayout(null);
		setLocation(200,200);
		
		// 이미지 받아오기
		try {
			img = ImageIO.read(new File("img/login3.jpg"));

		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		// 가장 큰 JLayer패널= 레이어를 순서대로 올려줌
		JLayeredPane lpane = new JLayeredPane();
		lpane.setBounds(0, 0, 1024, 768);
		lpane.setLayout(null);
		
		// 첫번째 panel = 텍스트필드 tf, tf2 들어감.
		MyPanel panel = new MyPanel();
		panel.setBounds(0, 0, 1024, 768);
		panel.setLayout(null);

		tf = new JTextField(20);
		tf.setBounds(350, 333, 280, 30);
		tf.setBackground(Color.black);
		tf.setForeground(Color.green);
		tf.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(tf);

		tf2 = new JPasswordField(20);
		tf2.setBounds(350, 395, 280, 30);
		tf2.setBackground(Color.black);
		tf2.setForeground(Color.green);
		tf2.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		tf.setDocument(new JTextFieldLimit(10));
		tf2.setDocument(new JTextFieldLimit(10));
		panel.add(tf2);

		// 두번째 패널 panel2 = 버튼들어감
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBounds(587, 458, 106, 50);
		panel2.setBackground(Color.black);
		panel2.setOpaque(false);

		bt = new JButton(new ImageIcon("img/btLogin.png")); // 587 458 에 위치해야한다.
		bt.setBorderPainted(false);
		bt.setFocusPainted(false);
		bt.setContentAreaFilled(false);
		/** 이게 레알이네: 버튼에 이미지만 보여주는 기능 */
		bt.setBounds(0, 0, 106, 50);
		bt.addActionListener(this);
		panel2.add(bt);

		// 패널들 프레임에 삽입
		lpane.add(panel, new Integer(0), 0);
		lpane.add(panel2, new Integer(1), 0);
		add(lpane);
		setVisible(true);
	}

	private class JTextFieldLimit extends PlainDocument // 텍스트 필드 글자수 제한을 위한 이너
														// 클래스 시작
	{
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
	}// 텍스트 필드 글자수 제한을 위한 이너 클래스 종료

	// 그림그리기 패널 (panel1)들어감
	class MyPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}

	// 액션
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) { // 로그인 처리 부

		//종태씨.. 아이디랑 비밀번호 안적혀있을때 매번 로그인 쿼리날리면 시간걸리니까 
		//else 에 로그인 쿼리 넣어둘게요~

		if (tf.getText().equals("") || tf2.getText().equals(""))// 아이디 비번
																// 입력했는
																// 지 검사
		{
			if (tf.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "아이디를 입력하세요", "아이디 입력",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (tf2.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요", "비번 입력",
						JOptionPane.INFORMATION_MESSAGE);
			}

		} else {
			//로그인 쿼리
			boolean existId = DB_query.loginMember(tf.getText(), tf2.getText());
			
			if (existId == true) // 로그인 가능 판별

			{
				
				JOptionPane.showMessageDialog(null, "로그인에 성공하였습니다.", "로그인 성공",
						JOptionPane.INFORMATION_MESSAGE);
				//프레임 안보이게하고 해제시킨다.
				vc.mainFrame();
				
				
				
			} else {
				JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다.", "로그인 실패",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}// 액션 끝
}

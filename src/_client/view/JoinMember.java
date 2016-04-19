package _client.view;

import java.awt.*;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import _client.dbprocess.IdCheckProcess;
import _client.dbprocess.JoinMemberProcess;
import assets.BCrypt;

import java.awt.event.*;

//회원 가입창 생성을 위한 테이블
public class JoinMember {// JoinMember클래스 시작

	private boolean dupl_id = true; // 아이디 중복인지 검사

	private JFrame join;
	private JTextField id;
	private JTextField tel;
	private JTextField age;
	private JPasswordField pass; // 비번입력
	private JPasswordField comp_pass; // 입력된 비번확인 필드
	private String reConfirmId;

	JoinMember()// JoinMember 생성자 시작
	{
		// 회원가입 창 프레임 생성
		join = new JFrame("회원가입");

		// 텍스트필드
		id = new JTextField();
		pass = new JPasswordField();
		tel = new JTextField();
		age = new JTextField();
		// 텍스트필드 제한설정
		id.setDocument(new JTextFieldLimit(10));
		pass.setDocument(new JTextFieldLimit(10));
		tel.setDocument(new JTextFieldLimit(14));
		age.setDocument(new JTextFieldLimit(3));
		// 라벨 생성
		JLabel id_label = new JLabel("아이디");
		JLabel pass_label = new JLabel("비밀번호");
		JLabel tel_label = new JLabel("핸드폰 번호");
		JLabel age_label = new JLabel("나이");

		// 버튼 생성
		JButton id_btn = new JButton("중복확인");
		JButton join_btn = new JButton("회원가입");
		JButton close_btn = new JButton("닫기");

		// 컴포넌트 사이즈 및 배치설정
		id.setBounds(100, 10, 95, 20);
		pass.setBounds(100, 35, 95, 20);
		tel.setBounds(100, 60, 110, 20);
		age.setBounds(100, 85, 30, 20);
		id_label.setBounds(30, 5, 95, 30);
		pass_label.setBounds(30, 30, 95, 30);
		tel_label.setBounds(20, 55, 95, 30);
		age_label.setBounds(40, 80, 95, 30);
		id_btn.setBounds(200, 5, 90, 25);
		join_btn.setBounds(30, 140, 90, 25);
		close_btn.setBounds(130, 140, 90, 25);
		JPanel panel = new JPanel();

		// 버튼 이벤트 결합부
		id_btn.addActionListener(new IdConfirm());
		join_btn.addActionListener(new JoinProcess());
		close_btn.addActionListener(new CloseProcess());

		// 현재 스크린사이즈를 받아온다
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;

		panel.setLayout(null);
		panel.add(id_btn);
		panel.add(id_label);
		panel.add(pass_label);
		panel.add(tel_label);
		panel.add(tel);
		panel.add(id);
		panel.add(pass);
		panel.add(join_btn);
		panel.add(close_btn);
		panel.add(age_label);
		panel.add(age);

		join.add(panel);
		join.setBounds(width / 3, height / 4, 300, 200);
		join.setResizable(false);
		join.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		join.setVisible(true);
	}// JoinMember 생성자 종료

	// JTextFieldLimit클래스 시작(텍스트필드 제한)
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

	// IdConfirm클래스 아이디 중복확인 이벤트 처리를 한다
	private class IdConfirm implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (id.getText().equals("비회원"))// 아이디 검사
			{
				JOptionPane.showMessageDialog(null, "아이디로 \'비회원\'을 쓸수 없습니다.");
			} else if (id.getText().equals("")) {

				JOptionPane.showMessageDialog(null, "아이디를 입력해 주세요");
			} else {
				dupl_id = IdCheckProcess.checkId(id.getText());
				if (dupl_id == false) {
					JOptionPane.showMessageDialog(null, "사용 가능 한 아이디 입니다.");
					reConfirmId = id.getText();
				} else {
					JOptionPane.showMessageDialog(null, "다른 아이디를 입력해주세요.");
				}

			}

		}

	}

	// IdConfirm클래스 종료

	// JoinProcess 클래스 시작
	private class JoinProcess implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			int input_age;
			try {
				input_age = Integer.parseInt(age.getText());
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "나이에 숫자를 입력해주세요");
				return;
			}

			if (dupl_id == false)// 아이디 중복검사 통과했고 아이디 중복검사 체크를 실행했으면
			{
				dupl_id = IdCheckProcess.checkId(id.getText());

				if (id.getText().equals("비회원")) {
					JOptionPane.showMessageDialog(null,
							"아이디로 \'비회원\'을 쓸수 없습니다.");
					dupl_id = true;
				} else if (id.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "아이디를 입력해주세요");
					dupl_id = true;

				} else if (dupl_id == true) {
					JOptionPane.showMessageDialog(null, "아이디 중복검사를 해주세요");
					// 혹시라도 유저가 회원가입시 중복검사 버튼 누르고 가입시는 다른아이디로 가입할 때 검사
				} else if (!reConfirmId.equals(id.getText())) {

					JOptionPane.showMessageDialog(null, "아이디 중복검사를 해주세요");

				} else {
					String encrypt_Pass = BCrypt.hashpw(pass.getText(),
							BCrypt.gensalt(12));
					JoinMemberProcess.insertMember(id.getText(), encrypt_Pass,
							tel.getText(), input_age);
					JOptionPane.showMessageDialog(null, "회원가입에 성공하셨습니다.");
					join.dispose();
				}

			} else {
				JOptionPane.showMessageDialog(null, "아이디 중복 검사을 다시 해주세요");

			}

		}

	}

	// JoinProcess 클래스 종료

	// CloseProcess 클래스 시작
	private class CloseProcess implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			join.dispose();

		}

	}
	// CloseProcess 클래스 종료
}// JoinMember클래스 종료

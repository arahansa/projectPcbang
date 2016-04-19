package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.manage_member.MemberInfo;
import control.manage_member.dbprocess.ReadMemberProcess;

@SuppressWarnings("serial")
public class MemInfo_Small extends JFrame implements ActionListener {
	String id, age, mileage, tel;
	JLabel label[] = new JLabel[8];

	public MemInfo_Small(String id) {
		MemberInfo memberInfo = ReadMemberProcess.readPerson(id);
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(memberInfo.getId() + "님의 회원정보");
		setUndecorated(true);
		// 프레임 화면 중앙 배열
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

		setLayout(new GridLayout(5, 1));

		JPanel panel[] = new JPanel[5];

		label[0] = new JLabel("아이디:");
		label[1] = new JLabel(memberInfo.getId());
		label[2] = new JLabel("전화번호:");
		label[3] = new JLabel(memberInfo.getTel());
		label[4] = new JLabel("나이:");
		label[5] = new JLabel(memberInfo.getAge());
		label[6] = new JLabel("마일리지:");
		label[7] = new JLabel(memberInfo.getMileage());

		for (int i = 0; i < 5; i++) {
			panel[i] = new JPanel();
		}
		for (int i = 0, j = 0; i < 8; i++) {

			panel[j].setBackground(Color.black);
			label[i].setBackground(Color.black);
			label[i].setForeground(Color.green);
			System.out.println(" i: " + i + " j: " + j);

			if (i % 2 == 0 && i > 1)
				j++;
			panel[j].add(label[i]);
		}
		JButton btn = new JButton("확인");
		btn.addActionListener(this);
		panel[4].setBackground(Color.black);
		panel[4].add(btn);
		for (int i = 0; i < 5; i++) {
			panel[i].setLayout(new FlowLayout());
			add(panel[i]);
		}
		setVisible(true);
	}

	public static void main(String[] args) {
		new MemInfo_Small("arahansa");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}

}

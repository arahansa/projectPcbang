package view;

/**
 * 관리자 화면의 메인 패널이 될 듯한 화면..
 * 좌석패널은 panel_seat
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import view.manage_member.ManageMember;
import control.HostPcServer;
import control.Vcontrol;


public class Manage_Fr extends Manage implements ActionListener{
	private static final long serialVersionUID = 1L;
	JPanel panel;
	public JPanel panel_seat;
	public JLabel label_seat;
	public JTextArea ta = new JTextArea(20, 40);
	JButton bt1, bt2, bt3, bt4, bt5, bt7, bt6, bt8;
	Vcontrol vcm = Vcontrol.getInstance("매니지프레임");
	public Manage_Fr() {
		// 프레임 초기 설정
		setSize(1600, 900);
		setTitle("난파선 피씨방에 오신 것을 환영합니다");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);

		panel = new JPanel();
		panel.setBackground(Color.black);
		panel.setBounds(0, 0, 1600, 900);
		panel.setLayout(null);

		// 네비게이션 패널프로토타입
		JPanel navi = new JPanel();
		navi.setLayout(new GridLayout(2, 4));
		navi.setBounds(200, 56, 948, 77);
		bt1 = new JButton("현재 화면");
		bt2 = new JButton("회원 관리");
		bt3 = new JButton("재고 관리");
		bt4 = new JButton("매상 관리");
		bt5 = new JButton("선택자종료");
		bt6 = new JButton("선택단체계산");
		bt7 = new JButton("미정");
		bt8 = new JButton("미정");
		bt1.addActionListener(this);
		bt2.addActionListener(this);
		bt3.addActionListener(this);
		bt4.addActionListener(this);
		bt5.addActionListener(this);
		bt6.addActionListener(this);
		bt7.addActionListener(this);
		bt8.addActionListener(this);
		
		
		navi.add(bt1);
		navi.add(bt2);
		navi.add(bt3);
		navi.add(bt4);
		navi.add(bt5);
		navi.add(bt6);
		navi.add(bt7);
		navi.add(bt8);
		
		ta.append("일단은 여기가 메시지 받는 창입니다\n");
		ta.setBounds(200 + 950, 56, 250, 77);
		panel.add(navi);
		panel.add(ta);
		// 좌석 패널
		JPanel seat = new JPanel();
		seat.setBackground(Color.gray);
		seat.setBounds(223, 183, 1440 - 223, 813 - 183); // 1440 813
		seat.setLayout(new GridLayout(5, 10));

		pan = new Seat_pan[50];
		for (int a = 0; a < 50; a++) {
			pan[a] = new Seat_pan(a);
			seat.add(pan[a]);
		}

		panel.add(seat);
		// 패널들 모두 추가하고 비지블!
		add(panel);
		setVisible(true);

	}
	public static void main(String args[]) {
		HostPcServer host = new HostPcServer();
		host.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==bt6){
			vcm.groupPayOff(2, 0);
		}else if(e.getSource()==bt1){
			
		}else if(e.getSource()==bt2){
			//회원관리
			new ManageMember();
		}else if(e.getSource()==bt3){
			//재고관리
			new MenuSet();
		}else if(e.getSource()==bt4){
			new SalesToday();
		}else if(e.getSource()==bt5){
			
		}else if(e.getSource()==bt7){
			
		}else if(e.getSource()==bt8){
			
		}
	}
}

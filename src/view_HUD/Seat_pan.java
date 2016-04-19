package view_HUD;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import view.MemInfo_Small;
import view.Seat_panAb;
import control.Vcontrol;

public class Seat_pan extends Seat_panAb implements ActionListener,
		MouseListener {
	private static final long serialVersionUID = 1L;
	Vcontrol vcm = Vcontrol.getInstance("시트팬");
	BufferedImage img = null;
	JLayeredPane lpane;
	JPanel panel3;

	public Seat_pan(int i) {
		num = i;
		isChecked = false;
		img("gameOff");
		setLayout(null);

		// 제이레이어드 패널
		lpane = new JLayeredPane();
		lpane.setBounds(0, 0, 1600, 900);
		lpane.setLayout(null);
		lpane.setOpaque(false);
		// 이미지 패널
		JPanel panel = new InnerPanel();
		panel.setBounds(0, 0, 99, 99);
		panel.setOpaque(false);
		// 안에 들어갈 내용물들
		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBounds(0, 0, 99, 99);

		int y = 15;
		for (int a = 0; a < 4; a++) {
			if (a == 0)
				label[a] = new JLabel((i + 1) + ". 빈자리");
			else
				label[a] = new JLabel("");

			label[a].setBounds(20, y, 80, 15);
			y += 16;
			label[a].setForeground(new Color(36, 205, 198));
			label[a].setFont(new Font("배달의민족 한나", 1, 12));
			panel2.add(label[a]);
		}
		panel2.setOpaque(false);

		// 체크패널
		panel3 = new CheckPanel();
		panel3.setLayout(null);
		panel3.setBounds(0, 0, 99, 99);
		panel3.setOpaque(false);
		// 마지막 붙이기
		lpane.add(panel, new Integer(0), 0);
		lpane.add(panel2, new Integer(1), 0);

		add(lpane);
		setVisible(true);

		setOpaque(false);
		setFocusable(true);
		addMouseListener(this);
		/** 여기서부터는 오른쪽 버튼 구현~ */
		pMenu = new JPopupMenu();
		miEnd = new JMenuItem("정산");
		miEnd.addActionListener(this);
		miInfo = new JMenuItem("회원정보");
		miInfo.addActionListener(this);
		miChat = new JMenuItem("메세지보내기");
		miChat.addActionListener(this);
		pMenu.add(miEnd);
		pMenu.add(miInfo);
		pMenu.add(miChat);
		// 패널에 마우스 리스너를 붙인다. JPopupMenu는 이런식으로 구현을 해야 한다..
		addMouseListener(new MousePopupListener());
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setTitle("시트 패널");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(99, 144);

		JPanel panel = new Seat_pan(1);
		f.add(panel);

		f.setVisible(true);
	}

	public void img(String s) {
		// 이미지 받아오기 - turnOn, turnOff, gameOn, gameOff
		try {
			// System.out.println("이미지 불러옵니다~");
			img = ImageIO.read(new File("img/" + s + ".png"));
		} catch (IOException e) {
			System.out.println("이미지 불러오기 실패!");
			System.exit(0);
		}
		repaint();
	}

	/** 이부분이 상태 체크 */
	public void turnOn() {
		img("gameOn");
		isTurned = true;
	}

	public void turnOff() {
		img("gameOff");
		isTurned = false;
	}

	public void checkOn() {
		lpane.add(panel3, new Integer(2), 0);
		this.isChecked = true;
		repaint();
	}

	@Override
	public void checkOff() {
		lpane.remove(panel3);
		this.isChecked = false;
		repaint();
	}

	// 이미지불러오는패널
	class InnerPanel extends JPanel {
		private static final long serialVersionUID = 1547128190348749556L;

		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(img, 0, 0, null);
		}
	}

	// 체크패널
	@SuppressWarnings("serial")
	class CheckPanel extends JPanel {
		BufferedImage img_c;

		CheckPanel() {
			try {
				img_c = ImageIO.read(new File("img/check.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(img_c, 0, 0, null);
		}
	}

	/** 여기서부터 액션처리 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (this.isChecked == false) {
			checkOn();

		} else if (this.isChecked == true) {
			checkOff();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@SuppressWarnings("static-access")
	@Override
	public void mousePressed(MouseEvent me) {
		if (me.getModifiers() == me.BUTTON3_MASK)
			pMenu.show(this, me.getX(), me.getY());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	@Override
	// 팝업 : 로그아웃 정산메소드와 메세지를 손님께 메소드
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == miEnd) {
			vcm.groupPayOff(1, num);
		} else if (e.getSource() == miChat) {
			vcm.messageFromPC(num, "채팅을 시작합니다\n");

		} else if (e.getSource() == miInfo) {
			new MemInfo_Small(nickname);
		}
	}

	class MousePopupListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			checkPopup(e);
		}

		public void mouseClicked(MouseEvent e) {
			checkPopup(e);
		}

		public void mouseReleased(MouseEvent e) {
			checkPopup(e);
		}

		private void checkPopup(MouseEvent e) {
			if (e.isPopupTrigger()) {
				pMenu.show(Seat_pan.this, e.getX(), e.getY());
			}
		}
	}

}

package view;


import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import control.Vcontrol;

public class Seat_pan extends Seat_panAb implements ActionListener, MouseListener{
	private static final long serialVersionUID = 1L;
	Vcontrol vcm = Vcontrol.getInstance("시트팬");
	
	public Seat_pan(int i) {
		num=i;
		isChecked = false;
		setLayout(new GridLayout(5, 0));
		
		
		label[0] = new JLabel("좌석" + (i + 1));
		label[0].setBackground(Color.blue);
		label[0].setForeground(Color.red);
		
		setBackground(Color.gray);
		label[1] = new JLabel("컴퓨터꺼짐");
		label[2] = new JLabel();
		label[3] = new JLabel();
		
		add(label[0]);
		add(label[1]);
		add(label[2]);
		add(label[3]);
		
		
		setFocusable(true);
		addMouseListener(this);
		
		
		/** 여기서부터는 오른쪽 버튼 구현~ */
		pMenu = new JPopupMenu();
		miEnd= new JMenuItem("정산");
		miEnd.addActionListener(this);
		miInfo = new JMenuItem("회원정보");
		miChat = new JMenuItem("메세지보내기");
		miChat.addActionListener(this);
		pMenu.add(miEnd);
		pMenu.add(miInfo);
		pMenu.add(miChat);
		//패널에 마우스 리스너를 붙인다. JPopupMenu는 이런식으로 구현을 해야 한다..
	   addMouseListener(new MousePopupListener());
	   
	    
	}
	
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setTitle("시트 패널");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1024, 768);
		f.setLayout(new GridLayout(5, 10));

		f.setVisible(true);
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(this.isChecked==false){
			checkOn();
			
		}else if(this.isChecked==true){
			checkOff();
		}	
	}
	
	public void turnOn(){
		
	}
	@Override
	public void turnOff() {
		
		
	}


	@Override
	public void checkOn() {
		tmp= this.getBackground();
		setBackground(Color.red);
		this.isChecked = true;
		System.out.println(num+"번째 자리가 눌렸습니다");
	}


	@Override
	public void checkOff() {
		setBackground(tmp);
		this.isChecked = false;
		System.out.println(num+"번째 자리가 체크해제되었다!");
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
		if(me.getModifiers()==me.BUTTON3_MASK)
			pMenu.show(this, me.getX(), me.getY());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	
	@Override
	//팝업 : 로그아웃 정산메소드와 메세지를 손님께 메소드
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==miEnd){
			vcm.groupPayOff(1, num);
		}else if(e.getSource()==miChat){
			vcm.messageFromPC(num, "채팅을 시작합니다\n");
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

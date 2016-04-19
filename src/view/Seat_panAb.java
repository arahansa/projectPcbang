package view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public abstract class Seat_panAb extends JPanel{
	public JLabel[] label = new JLabel[4];
	public boolean isChecked;
	public boolean isLogined;
	public boolean isTurned;
	public int num;//좌석번호를 혼자 가지고 있기 위한 넘버
	public Color tmp; //잠시 컬러를 가지고 있다가 다시 돌아갈때 준다. 
	public JPopupMenu pMenu;
	public JMenuItem miEnd, miInfo, miChat;
	public abstract void turnOn();
	public abstract void turnOff();
	public abstract void checkOn();
	public abstract void checkOff();
	public int x,y;
	public String nickname;

}

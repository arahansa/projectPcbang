package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import model.People;
import model.PeoplesModel;
import control.SaveSales;
import control.Vcontrol;

@SuppressWarnings("serial")
public class CalculatorFrame extends JFrame implements ActionListener, KeyListener {
	Vcontrol vc = Vcontrol.getInstance("계산프레임");

	int num;
	JButton button1, button2;
	JLabel label = new JLabel("요금 계산메뉴(나가지마..)");
	ArrayList<People> peoples;
	
	public CalculatorFrame(ArrayList<People> people) {
		this.peoples = people;
		
		setSize(500, 500);
		setTitle("정산메뉴");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(4, 4));
		setUndecorated(true);
		
		
		
		// 프레임 화면 중앙 배열
		Dimension frameSize = this.getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

		// 센터들어가는 중간패널
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		// panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		Border blackBorder = BorderFactory.createLineBorder(Color.BLACK);
		panel.setBorder(blackBorder);

		// 테이블
		PeoplesModel model = new PeoplesModel(peoples);
		JTable table = new JTable(model);
		panel.add(new JScrollPane(table));
		
		
		
		// 맨아래들어가는 아래패널
		int sum=0;
		for(int i=0;i<peoples.size();i++){
			sum+=peoples.get(i).getMoney();
		}
		JPanel 아래패널 = new JPanel();
		JLabel money= new JLabel("총 요금 : "+sum);
		button1 = new JButton("계산!");
		button1.addActionListener(this);
		button2 = new JButton("취소");
		button2.addActionListener(this);
		
		아래패널.add(money);
		아래패널.add(button1);
		아래패널.add(button2);
		
		add(label, BorderLayout.NORTH);
		add(panel, BorderLayout.CENTER);
		add(아래패널, BorderLayout.SOUTH);
		setVisible(true);
	}

	public static void main(String[] args) {
		ArrayList<People> peoples = new ArrayList<People>();
		peoples.add(new People(1, "nick", "연습시간", 300));
		new CalculatorFrame(peoples);
	}
	
	@Override
	// 정산버튼 구현함
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {
			for (int i = 0; i < peoples.size(); i++) {
				vc.logout(peoples.get(i).getNum());
				//파일을 저장한다. 매상을 위해서~
			}
			SaveSales.Save(peoples);
			dispose();
		} else if (e.getSource() == button2) {
			dispose();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==10){
		
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	//추상테이블모델
	
}

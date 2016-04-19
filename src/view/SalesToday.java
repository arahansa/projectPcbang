package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import control.ViewSales;
import model.People;
import model.PeoplesModel;

@SuppressWarnings("serial")
public class SalesToday extends JFrame {
	ArrayList<People> peoples;
	public SalesToday() {
		
		
		setSize(600, 600);
		setTitle("오늘의 매상");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(4, 4));
		
		Date today = new Date();
		SimpleDateFormat sdf1= new SimpleDateFormat("yyyy-MM-dd");
		String fileName = sdf1.format(today)+".dat"; //2014-05-09.dat 식으로 파일명이 생긴다.
		this.peoples = ViewSales.load(fileName);
		
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
		JLabel money= new JLabel(sdf1.format(today)+" 총 매상 : "+sum+"원");
		money.setFont(new Font("Serif", Font.BOLD, 30));
		money.setForeground(Color.red);
		아래패널.add(money);
		
		
		
		add(panel, BorderLayout.CENTER);
		add(아래패널, BorderLayout.SOUTH);
		setVisible(true);
	}
	public static void main(String[] args) {
		new SalesToday();
	}

}

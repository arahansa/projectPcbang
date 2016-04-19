package model;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class Stock_1st extends JFrame {

	private JPanel content_panel,panel_a,panel_b,panel_a_a;
	private JRadioButton radiobtn1,radiobtn2;
	private JTextField txtfld;
	private JLabel label;
	public Stock_1st(){
		
		content_panel=new JPanel();
		panel_b= new JPanel();
		panel_a=new JPanel();
		panel_a_a=new JPanel();
		txtfld=new JTextField(20);
		label = new JLabel("Item :");
		setSize(1300,700);
		setTitle("stock");
		setBackground(Color.WHITE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		content_panel.setLayout(null);
		panel_a.setLayout(new GridLayout(2,2));
		//content_panel.setBackground(Color.WHITE);
		
		panel_b.setBorder(new TitledBorder(null,"[FOOD]",TitledBorder.LEADING,TitledBorder.TOP,null,null));
		panel_b.setBounds(5,355, 600, 270);
		
		content_panel.add(panel_b);
		
		content_panel.add(txtfld);
		txtfld.setBounds(5, 630, 550, 20);
		
		panel_a.setBorder(new TitledBorder(null,"[ODER]",TitledBorder.LEADING,TitledBorder.TOP,null,null));
		panel_a.setBounds(5, 5, 600, 300);
		content_panel.add(panel_a);
		
		radiobtn1 = new JRadioButton("회원");
		panel_a.add(radiobtn1);
		radiobtn2 = new JRadioButton("비회원");
		panel_a.add(radiobtn2);
		
		
		panel_a.add(label);
		label.setOpaque(true);
		label.setBackground(Color.BLUE);
		label.setSize(50,50);
		
		
		
		panel_a_a.setBorder(new TitledBorder(null,"[STOCK]",TitledBorder.LEADING,TitledBorder.TOP,null,null));
		panel_a_a.setBounds(650, 5, 600, 650);
		content_panel.add(panel_a_a);
		
		
		add(content_panel);
		setVisible(true);
	}
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Stock_1st ap = new Stock_1st();

	}

}

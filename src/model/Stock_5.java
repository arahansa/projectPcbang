package model;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class Stock_5 extends JFrame{

	JPanel panel = new JPanel();
	
	String []arr = {"번호","상품이름","상품가격"};
	
	Object str[][]={{1,"맛동산","100"},{2,"아폴로","200"}};
	
	JTable table = new JTable(str,arr);
	JScrollPane pane = new JScrollPane(table);
	
	public Stock_5(){
		
		
		panel.add(pane);
		add(panel);
		setSize(400,300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Stock_5 ap = new Stock_5();
	}
}

package model;



import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;



@SuppressWarnings("serial")
public class test01 extends JFrame{

	 
	    private JRootPane jrp;

	    private Container con;

	    private DefaultTableModel dtm = new DefaultTableModel(5,4);

	    private JTable table = new JTable(dtm);  //  JTable(TableModel) 생성자 이용

	    private JScrollPane jsp = new JScrollPane(table);

	 

	    public test01() {

	        setTitle("Title Swing");

	        setSize(400, 300);

	        initLocation();

	        init();

	        start();

	 

	        this.setVisible(true);

	    }

	 

	    private void init() {

	        jrp = this.getRootPane();

	        con = jrp.getContentPane();

	        con.setLayout(new BorderLayout(5,5));

	 

	        con.add("North", new JLabel("Stock", JLabel.CENTER));

	        con.add("Center", jsp);

	        JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	        jp.add(new JButton("OK"));

	        jp.add(new JButton("Cancel"));

	        con.add("South", jp);

	 

	    }

	 

	    private void start() {

	            dtm.setValueAt("", 0, 0);  // 테이블에 데이타를 추가

	            dtm.setValueAt("", 1, 0);

	            dtm.setValueAt("", 3, 2);

	 

	            dtm.addColumn("NewCol");   // 새로운 컬럼을 추가

	            dtm.addRow(new Object[]{"first","second","third"});


	    }

	    private void initLocation() {

	        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        Toolkit tk = Toolkit.getDefaultToolkit();

	        Dimension dm = tk.getScreenSize();

	        Dimension fm = this.getSize();

	        this.setLocation((int) (dm.getWidth() / 2 - fm.getWidth() / 2),

	                (int) (dm.getHeight() / 2 - fm.getHeight() / 2));

	    }
@SuppressWarnings("unused")
public static void main(String[] args) {
	test01 ap =new test01();
}
	}


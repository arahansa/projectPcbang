package _client.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import _client.dbprocess.GetPriceProcess;
import _client.dbprocess.ItemNumCompareProcess;
import _client.dbprocess.OrderProcess;
import _client.dbprocess.ShowMenuProcess;

public class Menu {

	private final String[] COLUNM = { "품목", "품명", "가격", "재고" };
	private final String[] ITEMTYPE = { "라면", "버거", "음료", "과자" };
	private DefaultTableModel dtm;
	private JTable jt;
	private JTextField numField;
	private JComboBox box;
	private JComboBox item;
	private JFrame frm;
	private DataOutputStream out;
	private int pc;

	// Menu생성자 종료
	protected Menu(DataOutputStream out,int pc) {
		this.pc=pc;
		this.out = out;
		frm = new JFrame("주문가능한 메뉴 목록");
		dtm = new DefaultTableModel(COLUNM, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		jt = new JTable(dtm);
		JScrollPane center = new JScrollPane(jt);
		JButton orderBtn = new JButton("주문");
		JPanel south = new JPanel();
		JLabel label = new JLabel("정렬기준");
		JLabel orderLabel = new JLabel("주문");
		JLabel numLabel = new JLabel("개수");
		numField = new JTextField(3);
		box = new JComboBox(ITEMTYPE);
		item = new JComboBox();

		int xPos = Toolkit.getDefaultToolkit().getScreenSize().width - 530;
		int yPos = Toolkit.getDefaultToolkit().getScreenSize().height / 5 + 250;

		// 컴포넌트 결합부
		south.add(label);
		south.add(box);
		south.add(orderLabel);
		south.add(item);
		south.add(numLabel);
		south.add(numField);
		south.add(orderBtn);
		frm.add(south, "South");
		frm.add(center, "Center");

		// 이벤트 리스너 결합부
		box.addActionListener(new BoxEvent());
		orderBtn.addActionListener(new OrderEvent());
		frm.setBounds(xPos, yPos, 500, 200);
		frm.setResizable(false);
		frm.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		ShowMenuProcess.showMenu(dtm, "라면");

		for (int i = 0; i < dtm.getRowCount(); i++) {
			String inputItem = (String) (((Vector) dtm.getDataVector()
					.elementAt(i)).elementAt(1));
			item.addItem(inputItem);
		}

		frm.setVisible(true);
		new Thread(new Check()).start();

	}

	// Menu생성자 종료

	// 주문을 하기 위한 이벤트 클래스 시작
	private class OrderEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			int price=0;
			try {
				Integer.parseInt(numField.getText());
			} catch (NumberFormatException g) {
				JOptionPane.showMessageDialog(null, "숫자를 입력하세요");
				return;
			}

			String orderItem = (String) item.getSelectedItem();
			int num = Integer.parseInt(numField.getText());
			boolean doOrder = ItemNumCompareProcess.compNum(orderItem, num);
			if (doOrder == false) {
				JOptionPane.showMessageDialog(null, "\'" + orderItem
						+ "\'의 제품의 수량을 확인해주세요");
				return;
			}
			OrderProcess.order(orderItem, num);
			price=GetPriceProcess.getPrice(orderItem);
			price=price*num;
			////////////////////////////////////////////////////////////주문내역 전송부
			try {
				out.writeUTF("주문");
				out.writeInt(pc);
				out.writeUTF(orderItem);
				out.writeInt(num);
				out.writeInt(price);
				out.flush();
								
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			////////////////////////////////////////////////////////////주문내역 전송부
			String select = (String) box.getSelectedItem();
			resetRow();
			ShowMenuProcess.showMenu(dtm, select);

		}
	}

	// 주문을 하기 위한 이벤트 클래스 종료

	// 콤보박스 이벤트 처리 클래스 시작
	private class BoxEvent implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			String select = (String) box.getSelectedItem();
			resetRow();
			ShowMenuProcess.showMenu(dtm, select);
			item.removeAllItems();
			for (int i = 0; i < dtm.getRowCount(); i++) {
				String inputItem = (String) (((Vector) dtm.getDataVector()
						.elementAt(i)).elementAt(1));
				item.addItem(inputItem);
			}
		}
	}

	// 콤보박스 이벤트 처리 클래스 종료
	// 테이블의 모든 데이터를 삭제하는 메소드 시작
	private void resetRow() {
		if (dtm.getRowCount() > 0) {
			for (int i = dtm.getRowCount() - 1; i > -1; i--) {
				dtm.removeRow(i);
			}
		}
	}

	// 테이블의 모든 데이터를 삭제하는 메소드 종료

	private class Check implements Runnable {

		@Override
		public void run() {
			while (ClientPc.doClient == true) {

			}
			frm.dispose();
		}

	}

}

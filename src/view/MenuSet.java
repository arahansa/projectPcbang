package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import model.stock.ChangeStockFrame;
import model.stock.InsertStockFrame;
import control.stock.dbprocess.DeleteStockProcess;
import control.stock.dbprocess.StockSearchProcess;

@SuppressWarnings("serial")
public class MenuSet extends JFrame {
	private JFrame frame = new JFrame("재고관리");
	private JTextField searchField = new JTextField(10);
	private JTable table = new JTable();
	private JScrollPane scroll = new JScrollPane();
	private JPanel centerpanel = new JPanel();
	private JPanel southpanel = new JPanel();
	private JPanel otherpanel = new JPanel();
	private DefaultTableModel dtm;
	private JLabel searchLabel = new JLabel("품목검색");
	private JButton btn_a = new JButton("조회");
	private JButton btn_b = new JButton("품목추가");
	private JButton btn_c = new JButton("재고변경");
	private JButton btn_d = new JButton("품목삭제");
	private JButton searchBtn = new JButton("검색");
	private Dimension dimen_a;
	private Dimension dimen_b;
	private int xpos, ypos;
	public static boolean doSearch = false; // 조회버튼을 눌렀는지 판별하기 위해 사용
	static boolean doItemSearch = true;

	public MenuSet() {// 생성부
		init();

		frame.setSize(600, 600);
		dimen_a = Toolkit.getDefaultToolkit().getScreenSize();
		dimen_b = Toolkit.getDefaultToolkit().getScreenSize();
		xpos = dimen_a.width / 5;
		ypos = dimen_b.height / 5;

		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocation(xpos, ypos);
		frame.setResizable(false);
		frame.setVisible(true);

	}

	public void init() {// 화면 구성 보일부
		String[] str = { "품목종류", "품목", "가격", "재고" };
		dtm = new DefaultTableModel(str, 0) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		southpanel.setBackground(Color.blue);

		// 이벤트 처리부
		btn_a.addActionListener(new SearchEvent());
		btn_b.addActionListener(new InsertEvent());
		btn_c.addActionListener(new ChangeEvent());
		btn_d.addActionListener(new DeleteEvent());
		searchBtn.addActionListener(new ItemSearchEvent());
		// 결합부
		table = new JTable(dtm);
		scroll = new JScrollPane(table);
		otherpanel.add(scroll);
		centerpanel.add(scroll);
		southpanel.add(btn_a);
		southpanel.add(btn_b);
		southpanel.add(btn_c);
		southpanel.add(btn_d);
		southpanel.add(searchLabel);
		southpanel.add(searchField);
		southpanel.add(searchBtn);

		frame.add(scroll, "Center");
		frame.add(southpanel, "South");

	}

	// 조회 버튼 처리하기 위한 이벤트 클래스 시작
	private class SearchEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			resetRow();
			doSearch = true;
			doItemSearch = false;
			StockSearchProcess.readStock(dtm);
		}
	}

	// 품목 추가 창을 띄우기 위한 이벤트 클래스시작
	private class InsertEvent implements ActionListener {

		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent arg0) {

			InsertStockFrame isf = new InsertStockFrame(dtm, table);

		}
	}

	// 품목 추가 창을 띄우기 위한 이벤트 클래스종료
	// 재고 변경 창을 띄우기 위한 이벤트 클래스 시작
	private class ChangeEvent implements ActionListener {

		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent arg0) {

			ChangeStockFrame csf = new ChangeStockFrame(dtm, table);
		}
	}

	// 재고 변경 창을 띄우기 위한 이벤트 클래스 종료

	// 삭제 처리를 위한 창을 띄우기 위한 클래스 시작
	private class DeleteEvent implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (doSearch == true) {
				if (table.getSelectedRow() != -1) {

					String item = (String) dtm.getValueAt(table.getSelectedRow(), 1);
					DeleteStockProcess.delStock(item);
					dtm.removeRow(table.getSelectedRow());
					JOptionPane.showMessageDialog(null, "품목 \'" + item + "\'이 삭제되었습니다");

				} else {
					JOptionPane.showMessageDialog(null, "삭제할 품목을 먼저 선택해 주세요");
				}
			} else {
				JOptionPane.showMessageDialog(null, "삭제를 위해 조회를 먼저 해주세요");
			}

		}
	}

	// 삭제 처리를 위한 창을 띄우기 위한 클래스 종료
	// 품목검색 처리를 위한 클래스 시작
	private class ItemSearchEvent implements ActionListener {

		@SuppressWarnings("rawtypes")
		public void actionPerformed(ActionEvent arg0) {
			String item;
			if (doSearch == true) {
				if (searchField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "검색할 품목명을 입력 해주세요");
					return;
				}

				if (doItemSearch == true) {
					JOptionPane.showMessageDialog(null, "조회를 다시 해주세요");
					return;
				}

				if (dtm.getRowCount() > 0) {
					item = searchField.getText();
					for (int i = 0; i < dtm.getRowCount(); i++) {
						if (item.equals((String) ((Vector) dtm.getDataVector().elementAt(i)).elementAt(1))) {
							doItemSearch = true;
							String itemType = (String) ((Vector) dtm.getDataVector().elementAt(i)).elementAt(0);
							item = (String) ((Vector) dtm.getDataVector().elementAt(i)).elementAt(1);
							String price = (String) ((Vector) dtm.getDataVector().elementAt(i)).elementAt(2);
							String stock = (String) ((Vector) dtm.getDataVector().elementAt(i)).elementAt(3);
							resetRow();
							String[] add = { itemType, item, price, stock };
							dtm.addRow(add);
							JOptionPane.showMessageDialog(null, "품목\'" + item+ "\'이 검색되었습니다.");
							return;
						}
					}
					JOptionPane.showMessageDialog(null, "검색할 품목이 존재 하지 않습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "검색할 품목이 존재 하지 않습니다.");
				}

			} else {
				JOptionPane.showMessageDialog(null, "조회를 먼저 해주세요");
			}
		}

	}

	// 품목검색 처리를 위한 클래스 종료
	// 테이블의 모든 데이터를 삭제하는 메소드 시작
	private void resetRow() {
		if (dtm.getRowCount() > 0) {
			for (int i = dtm.getRowCount() - 1; i > -1; i--) {
				dtm.removeRow(i);
			}
		}
	}
	// 테이블의 모든 데이터를 삭제하는 메소드 종료

}

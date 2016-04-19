package model.stock;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import control.stock.dbprocess.InsertStockProcess;
import control.stock.dbprocess.ItemCompareProcess;
import control.stock.dbprocess.StockSearchProcess;

@SuppressWarnings("serial")
public class InsertStockFrame extends JFrame {

	// 컴포넌트 선언부
	private final String[] ITEM_TYPE_MENU = { "라면", "과자", "음료", "버거" };
	private DefaultTableModel dtm;
	@SuppressWarnings("unused")
	private JTable table;
	private JTextField itemField = new JTextField(5);
	private JTextField priceField = new JTextField(5);
	private JTextField stockField = new JTextField(5);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox itemTypeBox = new JComboBox(ITEM_TYPE_MENU);

	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();

	private JLabel label_1 = new JLabel("종류");
	private JLabel label_2 = new JLabel("품목명");
	private JLabel label_3 = new JLabel("가격");
	private JLabel label_4 = new JLabel("재고");

	private JButton insertBtn = new JButton("추가");
	private int xpos, ypos;

	// 생성자시작
	public InsertStockFrame(DefaultTableModel dtm, JTable table) {
		super("재고추가");
		this.dtm = dtm;
		this.table = table;

		xpos = Toolkit.getDefaultToolkit().getScreenSize().width / 5;
		ypos = Toolkit.getDefaultToolkit().getScreenSize().height / 3;

		centerPanel.add(label_1);
		centerPanel.add(itemTypeBox);
		centerPanel.add(label_2);
		centerPanel.add(itemField);
		centerPanel.add(label_3);
		centerPanel.add(priceField);
		centerPanel.add(label_4);
		centerPanel.add(stockField);
		southPanel.add(insertBtn);
		add(southPanel, BorderLayout.SOUTH);
		add(centerPanel, BorderLayout.CENTER);

		insertBtn.addActionListener(new InsertProcess());
		setBounds(xpos, ypos, 500, 100);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

	}

	// 생성자종료

	// 추가이벤트 클래스 시작
	public class InsertProcess implements ActionListener {
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent e) {

			// 같은 상품이 데이터로 들어가면 안되므로 비교하기 위한 값을 가져옴

			try {
				Integer.parseInt(priceField.getText());
			} catch (NumberFormatException g) {
				JOptionPane.showMessageDialog(null, "가격을 입력하세요");
				return;
			}

			try {
				Integer.parseInt(stockField.getText());
			} catch (NumberFormatException g) {
				JOptionPane.showMessageDialog(null, "재고수를 입력하세요");
				return;
			}

			if (itemField.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "품목명을 입력하세요");
				return;
			}

			// //////////품목중복 방지
			// 검사////////////////////////////////////////////////////////

			if (ItemCompareProcess.compare(itemField.getText())) {
				JOptionPane.showMessageDialog(null,
						"이미\'" + itemField.getText() + "\'이 존재합니다");
				return;
			}

			// //////////////////////////////////////////////////////////////////////////////

			String itemType = (String) (itemTypeBox.getSelectedItem());
			String item = itemField.getText();
			int priceInput = Integer.parseInt(priceField.getText());
			int stockInput = Integer.parseInt(stockField.getText());

			InsertStockProcess.insertStock(itemType, item, priceInput,
					stockInput);
			Integer price = new Integer(priceInput);
			Integer stock = new Integer(stockInput);
			String[] add = { itemType, item, price.toString(), stock.toString() };
			resetRow();
			StockSearchProcess.readStock(dtm);
			itemField.setText("");
			priceField.setText("");
			stockField.setText("");

			JOptionPane.showMessageDialog(null, "\'" + item
					+ "\' 물품이 추가 되었습니다.");

		}
	}

	// 추가 이벤트 클래스 종료
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

package model.stock;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;

import javax.swing.table.*;

import view.MenuSet;
import control.stock.dbprocess.ItemCompareProcess;
import control.stock.dbprocess.StockChangeProcess;
import control.stock.dbprocess.StockSearchProcess;

public class ChangeStockFrame {

	private DefaultTableModel dtm;
	@SuppressWarnings("unused")
	private JTable jt;
	private JTextField itemField;
	private JTextField valField;
	@SuppressWarnings("rawtypes")
	private JComboBox box;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ChangeStockFrame(DefaultTableModel dtm, JTable jt) {
		this.dtm = dtm;
		this.jt = jt;

		String[] menu = { "가격", "개수" };
		int xPos = Toolkit.getDefaultToolkit().getScreenSize().width / 5;
		int yPos = Toolkit.getDefaultToolkit().getScreenSize().height / 3;

		// 컴포넌트 선언
		JFrame cgFrame = new JFrame("재고 변경");
		box = new JComboBox(menu);
		JLabel itemLabel = new JLabel("품명");
		itemField = new JTextField(10);
		valField = new JTextField(10);
		JPanel center = new JPanel();
		JButton chgBtn = new JButton("변경");
		// 컴포넌트 위치 및 크기선언

		// 컴포넌트 결합부
		center.add(itemLabel);
		center.add(itemField);
		center.add(box);
		center.add(valField);
		center.add(chgBtn);
		cgFrame.add(center, "Center");

		// 이벤트 처리부
		chgBtn.addActionListener(new ChangeStockEvent());

		cgFrame.setBounds(xPos, yPos, 500, 70);
		cgFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		cgFrame.setVisible(true);

	}

	private class ChangeStockEvent implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {

			int index = 0;
			try {
				Integer.parseInt(valField.getText());
			} catch (NumberFormatException g) {
				JOptionPane.showMessageDialog(null, "변경 값 입력은 숫자만 가능합니다");
				return;
			}

			if (itemField.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "품목명을 입력해 주세요");
				return;
			}

			if (!ItemCompareProcess.compare(itemField.getText())) {
				JOptionPane.showMessageDialog(null, "품목이 존재 하지 않습니다. 확인해 주세요");
				return;
			}

			String select = (String) (box.getSelectedItem());
			if (select.equals("가격")) {
				index = 0;
			} else {
				index = 1;
			}

			StockChangeProcess.StockChange(itemField.getText(),
					Integer.parseInt(valField.getText()), index);

			JOptionPane.showMessageDialog(null, "\'" + itemField.getText()
					+ "\'의\'" + select + "가 성공적으로 변경되었습니다");
			MenuSet.doSearch=true;
			resetRow();
			StockSearchProcess.readStock(dtm);
			
			itemField.setText("");
			valField.setText("");

		}

	}

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

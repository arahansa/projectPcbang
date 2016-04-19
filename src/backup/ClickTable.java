package backup;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.AbstractTableModel;

import model.People;

public class ClickTable {

	public static void main(String[] args) {
		new ClickTable();
	}

	public ClickTable() {

		ArrayList<People> peoples = new ArrayList<People>();
		peoples.add(new People(1, "nick", "연습시간", 300));
		peoples.add(new People(2, "nick2", "연습시간2", 3004));
		
		PeoplesModel model = new PeoplesModel(peoples);
		JTable table = new JTable(model);

		JFrame frame = new JFrame("Testing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JScrollPane(table));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}

class Click {

	private int x;
	private int y;
	private int z;

	public Click(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

}

class PeoplesModel extends AbstractTableModel {

	private ArrayList<People> peoples;

	public PeoplesModel(ArrayList<People> peoples) {
		this.peoples = new ArrayList<People>(peoples);
	}

	@Override
	public int getRowCount() {
		return peoples.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public String getColumnName(int column) {
		String name = "??";
		switch (column) {
		case 0:
			name = "자리번호";
			break;
		case 1:
			name = "닉네임";
			break;
		case 2:
			name = "시간";
			break;
		
		case 3:
			name = "요금";
			break;
		}
		return name;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		Class type = String.class;
		switch (columnIndex) {
		case 0:type = Integer.class;
		break;
		case 1:type = String.class; break;
		case 2: type = String.class; break;
		case 3: type = Integer.class;
		break;
		}
		return type;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		People people = peoples.get(rowIndex);
		Object value = null;
		switch (columnIndex) {
		case 0:
			value = people.getNum();
			break;
		case 1:
			value = people.getNick();
			break;
		case 2:
			value = people.getHour();
			break;
		case 3:
			value = people.getMoney();
			break;
		}
		return value;
	}
}

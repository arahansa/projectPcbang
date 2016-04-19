package model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class PeoplesModel extends AbstractTableModel {

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
		@SuppressWarnings("rawtypes")
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
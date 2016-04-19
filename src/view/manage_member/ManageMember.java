package view.manage_member;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import model.manage_member.MemberInfo;
import control.manage_member.dbprocess.DeleteMemberProcess;
import control.manage_member.dbprocess.ReadMemberProcess;

import java.util.*;
import java.awt.event.*;

public class ManageMember {

	private int currentIndex = 0; // 현재인덱스
	private int endIndex = 0; // 앞페이지 맨 뒷페이지를 구현하기 위한 인덱스
	private final int MAX_ROW = 20; // 테이블에서 데이터를 표시하기 위한 최대 줄 수

	// 조회버튼을 눌렀는지 확인
	private boolean doSearch = false;

	// 디비에서 선택된 검색된 멤버를 저장
	private ArrayList<MemberInfo> list = new ArrayList<MemberInfo>();
	private DefaultTableModel dtm;
	private JTable jt;
	private JTextField idField;
	public static void main(String[] args) {
		new ManageMember();
	}
	// ManageMember생성자 시작
	@SuppressWarnings("serial")
	public ManageMember() {
		// 컬럼네임 지정
		String[] culumnName = { "아이디", "전화번호", "마일리지", "나이" };

		// 컴포넌트 선언
		JFrame memberFrame = new JFrame("가입자현황");
		dtm = new DefaultTableModel(culumnName, 0) {
			// 셀편집을 못하게 하는 필드
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		jt = new JTable(dtm);
		JScrollPane panel = new JScrollPane(jt);
		JPanel center = new JPanel();
		JPanel south = new JPanel();
		JLabel idLabel = new JLabel("아이디");
		JButton leftBtn = new JButton("<<");
		JButton rightBtn = new JButton(">>");
		JButton searchBtn = new JButton("조회");
		JButton deleteBtn = new JButton("삭제");
		JButton tableSearchBtn = new JButton("검색");
		idField = new JTextField(10);

		// 컴포넌트 결합부
		center.add(panel);
		south.add(leftBtn);
		south.add(rightBtn);
		south.add(searchBtn);
		south.add(idLabel);
		south.add(idField);
		south.add(tableSearchBtn);
		south.add(deleteBtn);

		memberFrame.add(south, "South");
		memberFrame.add(panel, "Center");

		// 버튼 이벤트 세팅
		tableSearchBtn.addActionListener(new IdSearchEvent());
		searchBtn.addActionListener(new SearchEvent());
		leftBtn.addActionListener(new LeftEvent());
		rightBtn.addActionListener(new RightEvent());
		deleteBtn.addActionListener(new DeleteEvent());

		// 프레임 위치 세팅
		int width = Toolkit.getDefaultToolkit().getScreenSize().width / 3;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height / 7;

		// 프레임의 기본적인 위치와 사이즈 세팅
		memberFrame.setResizable(false);
		memberFrame.setBounds(width, height, 500, 400);
		memberFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		memberFrame.setVisible(true);

	}

	// 조회버튼을 누를시에 member테이블에서 DB를읽어와서 테이블에 보여주는 이벤트처리 클래스
	private class SearchEvent implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			doSearch = true;
			if (list.size() > 0) {
				list.clear();
			}
			ReadMemberProcess.readMember(list);
			// 데이터삭제

			currentIndex = 0;
			resetRow();

			for (int i = 0; i < MAX_ROW; i++) {

				String id = list.get(i).getId();
				String tel = list.get(i).getTel();
				String mileage = list.get(i).getMileage();
				String age = list.get(i).getAge();
				String[] str = { id, tel, mileage, age };
				dtm.addRow(str);
			}
		}
	}
	// 조회버튼을 누를시에 member테이블에서 DB를읽어와서 테이블에 보여주는 이벤트처리 클래스종료

	// 왼쪽버튼으로 페이지 넘길때 이벤트 처리 클래스
	private class LeftEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int j = 0;
			if (doSearch == true) {
				if (currentIndex != 0) {
					// 데이터삭제
					resetRow();
					j = ((--currentIndex) * 20);
					for (int i = 0; i < MAX_ROW; i++) {

						String id = list.get(j).getId();
						String tel = list.get(j).getTel();
						String mileage = list.get(j).getMileage();
						String age = list.get(j).getAge();
						String[] str = { id, tel, mileage, age };
						dtm.addRow(str);
						j++;
					}

				} else {
					JOptionPane.showMessageDialog(null, "첫 번째 입니다.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "조회를 먼저하세요");
			}
		}
	}

	// 왼쪽버튼으로 페이지 넘길때 이벤트 처리 클래스 종료
	// 오른쪽 버튼으로 넘길때 이벤트 클래스 시작
	private class RightEvent implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			int j = 0;
			endIndex = setEndIndex(list.size());
			if (doSearch == true) {
				if (currentIndex != endIndex) {
					// 데이터삭제
					resetRow();
					j = ((++currentIndex) * 20);

					Outer: for (int i = 0; i < MAX_ROW; i++) {

						if (j > list.size() - 1) {
							break Outer;
						}
						String id = list.get(j).getId();
						String tel = list.get(j).getTel();
						String mileage = list.get(j).getMileage();
						String age = list.get(j).getAge();
						String[] str = { id, tel, mileage, age };
						dtm.addRow(str);
						j++;
					}

				} else {
					JOptionPane.showMessageDialog(null, "마지막 입니다.");
				}
			} else {
				JOptionPane.showMessageDialog(null, "조회를 먼저하세요");
			}

		}
	}

	// 오른쪽 버튼으로 넘길때 이벤트 클래스 종료
	// 멤버삭제 삭제를 위한 이벤트 클래스 시작
	private class DeleteEvent implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int index = jt.getSelectedRow();
			if (index != -1) {
				String id = (String) dtm.getValueAt(jt.getSelectedRow(), 0);
				DeleteMemberProcess.delMember(id);
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getId().equals(id)) {
						list.remove(i);
					}
				}
				dtm.removeRow(jt.getSelectedRow());
				JOptionPane.showMessageDialog(null, "회원 " + id + "를 삭제하였습니다.");

			} else {
				JOptionPane.showMessageDialog(null, "테이블에서 삭제할 값을 먼저 선택 하세요");
			}

		}
	}

	// 멤버삭제 삭제를 위한 이벤트 클래스 종료
	// 수월하게 삭제하기 위해 아이디를 검색하고 삭제하기 위한 이벤트 처리 클래스 시작
	private class IdSearchEvent implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			String searchId = idField.getText();
			if (doSearch == true) {
				if (searchId.equals("")) {
					JOptionPane.showMessageDialog(null, "검색할 아이디를 입력하세요");
				} else {
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getId().equals(searchId)) {
							resetRow();
							doSearch=false;
							String id = list.get(i).getId();
							String tel = list.get(i).getTel();
							String mileage = list.get(i).getMileage();
							String age = list.get(i).getAge();
							String[] str = { id, tel, mileage, age };
							dtm.addRow(str);
							return;
						}
					}
					JOptionPane.showMessageDialog(null, "아이디가 존재 하지 않습니다.");

				}
			} else {
				JOptionPane.showMessageDialog(null, "조회 먼저 해주세요.");
			}

		}
	}

	// 수월하게 삭제하기 위해 아이디를 검색하고 삭제하기 위한 이벤트 처리 클래스 종료
	// 창넘기기를 실행시 마지막 인덱스를 설정하는 메소드 시작
	private int setEndIndex(int i) {
		int end = 0;
		end= i / 20;
		return end;
	}

	// 창넘기기를 실행시 마지막 인덱스를 설정하는 메소드 종료

	// 테이블의 모든 데이터를 삭제하는 메소드 시작
	private void resetRow() {
		if (dtm.getRowCount() > 0) {
			for (int i = dtm.getRowCount() - 1; i > -1; i--) {
				dtm.removeRow(i);
			}
		}
	}
	// /테이블의 모든 데이터를 삭제하는 메소드 종료

}

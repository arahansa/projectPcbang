package model;


	import java.awt.Dimension;
import java.awt.GridLayout;

	import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


	@SuppressWarnings("serial")
	public class test02 extends JPanel {

		public test02() {
			super(new GridLayout(1,0));
			
			JTable table = new JTable(3,3);
			table.setPreferredScrollableViewportSize(new Dimension(500,70));
			table.setFillsViewportHeight(true);
			
			// Create the scroll pane and add the table to it.
			JScrollPane scrollPane = new JScrollPane(table);
			
			// Add the scroll pane to this panel.
			add(scrollPane);
		}
		private static void createAndShowGUI() {
			// Create and set up the window.
			JFrame frame = new JFrame("stock");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setBounds(50,50,400,400);
			
			// Ceate and set up the content pane.
			test02 newContentPane = new test02();
			newContentPane.setOpaque(true);		// content panes must be opaque
			frame.setContentPane(newContentPane);
			
			// Display the window
			//frame.pack();
			frame.setVisible(true);
		}
		
		public static void main(String[] args) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					createAndShowGUI();
				}
			});
		}
	}


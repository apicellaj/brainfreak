package main.java.brainfreak;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MemoryDump extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6911289527909024022L;
	private static final int TABLE_HEIGHT = 471;
	private static final int TABLE_WIDTH = 300;

	public MemoryDump(byte[] memoryCells) {
		final String[] columnNames = {"Cell", "Memory Value", "Character"};
		final Object[][] data = getData(memoryCells);
		final NonEditableModel tableModel = new NonEditableModel(data, columnNames);
		final JTable memoryTable = new JTable(tableModel);
		final DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
			
		});
		
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		// Center JTable cells
		memoryTable.setDefaultRenderer(Object.class, centerRenderer);
		// Center JTable headers
		((JLabel) memoryTable.getTableHeader().getDefaultRenderer())
		.setHorizontalAlignment(SwingConstants.CENTER);
		
		memoryTable.setFocusable(false);
		memoryTable.setRowSelectionAllowed(true);
		memoryTable.setGridColor(Color.BLACK);
		
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		add(new JScrollPane(memoryTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
		setVisible(true);
	}
	
	private Object[][] getData(byte[] memoryCells) {
		ArrayList<Object[]> memoryValues = new ArrayList<Object[]>();
		for (int i = 0; i < memoryCells.length; i++) {
			if (memoryCells[i] != 0) {
				memoryValues.add(new Object[] {i, memoryCells[i], (char) memoryCells[i]});
			}
		}
		Object[][] data = new Object[memoryValues.size()][];
		for (int i = 0; i < memoryValues.size(); i++) {
			data[i] = memoryValues.get(i);
		}
		return data;
	}
	
	private class NonEditableModel extends DefaultTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 8212917546343681550L;
		NonEditableModel(Object[][] data, String[] columnNames) {
	        super(data, columnNames);
	    }
		@Override
	    public boolean isCellEditable(int row, int column) {
	        return false;
	    }
	}
	
}

package brainfreak;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AsciiCheatSheet extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3408873757217381749L;
	private static final int TABLE_HEIGHT = 471;
	private static final int TABLE_WIDTH = 150;
	private static AsciiCheatSheet cheatSheet = new AsciiCheatSheet();
	
	public static AsciiCheatSheet getInstance() {
		if (cheatSheet != null) {
			cheatSheet.setSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
			cheatSheet.setVisible(true);
		}
		return cheatSheet;
	}
	
	private AsciiCheatSheet() {
		final String[] columnNames = {"Decimal", "Character"};
		final Object[][] data = getData();
		final NonEditableModel tableModel = new NonEditableModel(data, columnNames);
		final JTable cheatSheetTable = new JTable(tableModel);
		cheatSheetTable.setFocusable(false);
		cheatSheetTable.setRowSelectionAllowed(true);
		cheatSheetTable.setGridColor(Color.BLACK);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		add(new JScrollPane(cheatSheetTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
		setVisible(true);
	}
	
	private Object[][] getData() {
		final Object[][] data = 
			{
			{32, "space"},
			{33, '!'},
			{34, '"'},
			{35, '#'},
			{36, '$'},
			{37, '%'},
			{38, '&'},
			{39, '\''},
			{40, '('},
			{41, ')'},
			{42, '*'},
			{43, '+'},
			{44, ','},
			{45, '-'},
			{46, '.'},
			{47, '/'},
			{48, '0'},
			{49, '1'},
			{50, '2'},
			{51, '3'},
			{52, '4'},
			{53, '5'},
			{54, '6'},
			{55, '7'},
			{56, '8'},
			{57, '9'},
			{58, ':'},
			{59, ';'},
			{50, '<'},
			{61, '='},
			{62, '>'},
			{63, '?'},
			{64, '@'},
			{65, 'A'},
			{66, 'B'},
			{67, 'C'},
			{68, 'D'},
			{69, 'E'},
			{70, 'F'},
			{71, 'G'},
			{72, 'H'},
			{73, 'I'},
			{74, 'J'},
			{75, 'K'},
			{76, 'L'},
			{77, 'M'},
			{78, 'N'},
			{79, 'O'},
			{80, 'P'},
			{81, 'Q'},
			{82, 'R'},
			{83, 'S'},
			{84, 'T'},
			{85, 'U'},
			{86, 'V'},
			{87, 'W'},
			{88, 'X'},
			{89, 'Y'},
			{90, 'Z'},
			{91, '['},
			{92, '\\'},
			{93, ']'},
			{94, '^'},
			{95, '_'},
			{96, '`'},
			{97, 'a'},
			{98, 'b'},
			{99, 'c'},
			{100, 'd'},
			{101, 'e'},
			{102, 'f'},
			{103, 'g'},
			{104, 'h'},
			{105, 'i'},
			{106, 'j'},
			{107, 'k'},
			{108, 'l'},
			{109, 'm'},
			{110, 'n'},
			{111, 'o'},
			{112, 'p'},
			{113, 'q'},
			{114, 'r'},
			{115, 's'},
			{116, 't'},
			{117, 'u'},
			{118, 'v'},
			{119, 'w'},
			{120, 'x'},
			{121, 'y'},
			{122, 'z'},
			{123, '{'},
			{124, '|'},
			{125, '}'},
			{126, '~'},
			{127, "DEL"}
			};
		return data;
	}
	
	private class NonEditableModel extends DefaultTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5925088082322379600L;
		NonEditableModel(Object[][] data, String[] columnNames) {
	        super(data, columnNames);
	    }
		@Override
	    public boolean isCellEditable(int row, int column) {
	        return false;
	    }
	}
	
}

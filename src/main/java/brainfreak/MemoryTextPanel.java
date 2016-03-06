package main.java.brainfreak;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

public class MemoryTextPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7872573652549775500L;
	private static final String DEFAULT_MEMORY_SIZE = "30000";
	
	private JTextField memoryTextField;
	
	public MemoryTextPanel() {
		add(memoryTextField = new JTextField(10));
		setPreferredSize(new Dimension(100,25));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		PlainDocument document = (PlainDocument) memoryTextField.getDocument();
		document.setDocumentFilter(new DocumentLengthFilter(9));
		memoryTextField.setText(DEFAULT_MEMORY_SIZE);
	}
	
	public void setMemoryFieldText(String text) {
		String memorySize;
		if (text.length() == 0) {
			memorySize = "0";
		} else if (text.length() > 9) {
			memorySize = text.substring(0,10);
		} else {
			memorySize = text;
		}
		memoryTextField.setText(memorySize);
	}
	
	public void resetMemoryFieldText() {
		memoryTextField.setText(DEFAULT_MEMORY_SIZE);
	}
	
	public String getMemoryFieldText() {
		if (memoryTextField.getText().length() == 0) {
			setMemoryFieldText("0");
		}
		return memoryTextField.getText();
	}
	
	private class DocumentLengthFilter extends DocumentFilter {

		private int maxTextLength;
		
		public DocumentLengthFilter(int maxTextLength) {
			this.maxTextLength = maxTextLength;
		}
		
		@Override
		public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
			PlainDocument document = (PlainDocument) fb.getDocument();
			String oldText = document.getText(0, document.getLength());
			StringBuilder sb = new StringBuilder(oldText);
			sb.replace(offset, offset + length, "");
			
			if (checkLength(sb.toString())) {
				super.remove(fb, offset, length);
			}
			
		}
		
		@Override
		public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
				throws BadLocationException {
			String validatedString = string.replaceAll("[^0-9]", "");
			PlainDocument document = (PlainDocument) fb.getDocument();
			String oldText = document.getText(0, document.getLength());
			StringBuilder sb = new StringBuilder(oldText);
			sb.insert(offset, validatedString);

			if (checkLength(sb.toString())) {
				super.insertString(fb, offset, validatedString, attr);
			}
        }
		
		@Override
		public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
				throws BadLocationException {
			String validatedString = text.replaceAll("[^0-9]", "");
			PlainDocument document = (PlainDocument) fb.getDocument();
			String oldText = document.getText(0, document.getLength());
			StringBuilder sb = new StringBuilder(oldText);

			sb.replace(offset, offset + length, validatedString);
			if (checkLength(sb.toString())) {
				super.replace(fb, offset, length, validatedString, attrs);
			}
		}
		
		private boolean checkLength(String text) {
			return text.length() <= maxTextLength;
		}
		
	}

}

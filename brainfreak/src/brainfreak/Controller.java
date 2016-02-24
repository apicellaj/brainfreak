package brainfreak;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller {
	
	final private GUI gui;
	final private Interpreter interpreter;
	
	public Controller(final GUI gui, final Interpreter interpreter) {
		this.gui = gui;
		this.interpreter = interpreter;
		
		gui.addRunButtonListener(new runButonActionListener());
		gui.addReturnKeyListener(new ReturnKeyKeyListener());
		gui.addCheatSheetButtonActionListener(new CheatSheetActionListener());
		gui.addMemoryWrapItemListener(new MemoryWrapItemListener());
	}
	
	public String getInputAreaText() {
		return gui.getInputAreaText();
	}
	
	public String getCodeAreaText() {
		return gui.getCodeText();
	}
	
	public void setCodeAreaText(String text) {
		gui.setCodeText(text);
	}
	
	public void setResultAreaText(String text) {
		gui.setResultAreaText(text);
	}
	
	private void launchInterpreter() {
		final String regexPattern;
		if (gui.hasExtendedSupport()) {
			regexPattern = "[^\\>\\<\\+\\-\\.\\,\\:\\;\\[\\]]";
		} else {
			regexPattern = "[^\\>\\<\\+\\-\\.\\,\\[\\]]";
		}
		final String bfCode = getCodeAreaText().replaceAll(regexPattern, "").trim();
		final String stdIn =  getInputAreaText().trim();
		interpreter.setMemoryWrap(gui.hasMemoryWrap());
		interpreter.run(bfCode, stdIn);
		gui.setResultAreaText(interpreter.getResult());
		if (gui.isInDebugMode()) {
			final String debugInformation = interpreter.getDebugInfo();
			gui.setDebugDisplayLabel(debugInformation);
		} else {
			gui.setDebugDisplayLabel(" ");
		}
	}
	
	class runButonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			launchInterpreter();
		}
	}
	
	class ReturnKeyKeyListener implements KeyListener {
		private boolean shiftIsPressed = false;
		
	    @Override
	    public void keyTyped(KeyEvent e) {}

	    @Override
	    public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			    shiftIsPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER && shiftIsPressed) {
			    gui.clickRunButton();
			}
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			    shiftIsPressed = false;
			}
	    }
	}

	class CheatSheetActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			AsciiCheatSheet cheat = AsciiCheatSheet.getInstance();
			cheat.addCheatSheetKeyListener(new CheatSheetKeyListener(cheat));
		}
	}
	
	class MemoryWrapItemListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			final boolean hasMemoryWrap = gui.hasMemoryWrap();
			interpreter.setMemoryWrap(hasMemoryWrap);
		}
	}
	
	class CheatSheetKeyListener implements KeyListener {
		AsciiCheatSheet cheat;
		
		public CheatSheetKeyListener(AsciiCheatSheet cheat) {
			this.cheat = cheat;
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				cheat.dispose();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}
		
	}
	
}

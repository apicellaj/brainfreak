package main.java.brainfreak;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller {

	final private GUI gui;

	public Controller(final GUI gui) {
		this.gui = gui;

		gui.addRunButtonListener(new RunButonActionListener());
		gui.addReturnKeyListener(new ReturnKeyKeyListener());
		gui.addResetButtonListener(new ResetButtonActionListener());
		gui.addCheatSheetButtonActionListener(new CheatSheetActionListener());
		gui.addSmallSizeRadioButtonListener(new SmallSizeButtonActionListener());
		gui.addLargeSizeRadioButtonListener(new LargeSizeButtonActionListener());
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

	public void setDebugDisplayLabel(String debugInformation) {
		gui.setDebugDisplayLabel(debugInformation);
	}

	public boolean isInDebugMode() {
		return gui.isInDebugMode();
	}

	public void setMemoryFieldText(String text) {
		gui.setMemoryFieldText(text);
	}

	public void resetMemoryFieldText() {
		gui.resetMemoryFieldText();
	}

	private void launchInterpreter() {
		final String bfCode = getCodeAreaText();
		final String stdIn = getInputAreaText().replaceAll("[^0-9\\s]", "");
		updateInputAreaText(stdIn);
		final boolean hasMemoryWrap = gui.hasMemoryWrap();
		final boolean hasExtendedMode = gui.hasExtendedSupport();
		final boolean hasMemoryDump = gui.hasMemoryDump();
		final int memorySize = gui.getMemoryFieldValue();
		Interpreter interpreter = new Interpreter(this);
		interpreter.setCode(bfCode);
		interpreter.setInput(stdIn);
		interpreter.setMemoryWrap(hasMemoryWrap);
		interpreter.setExtendedMode(hasExtendedMode);
		interpreter.setMemoryDump(hasMemoryDump);
		interpreter.setMemorySize(memorySize);
		gui.addStopButtonListener(new StopButtonActionListener(interpreter));
		interpreter.execute();
	}

	private void updateInputAreaText(String text) {
		gui.setInputAreaText(text);
	}

	class RunButonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			launchInterpreter();
		}
	}

	class StopButtonActionListener implements ActionListener {
		private Interpreter interpreter;

		public StopButtonActionListener(Interpreter interpreter) {
			this.interpreter = interpreter;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			interpreter.cancel(true);
			interpreter.exitProgram();
		}
	}

	class ResetButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gui.setCodeText("");
			gui.setInputAreaText("");
			gui.setResultAreaText("");
			gui.resetComboBox();
			gui.resetMemoryFieldText();
		}
	}

	class SmallSizeButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gui.setSizeSmall();
		}
	}

	class LargeSizeButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			gui.setSizeLarge();
		}
	}

	class ReturnKeyKeyListener implements KeyListener {
		private boolean shiftIsPressed = false;

		@Override
		public void keyTyped(KeyEvent e) {
		}

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

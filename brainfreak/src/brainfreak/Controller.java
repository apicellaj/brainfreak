package brainfreak;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingWorker;

public class Controller {
	
	final private GUI gui;
	//TODO: create a way to write intermediate results to JTextArea
	
	private SwingWorker<Void, String> worker;
	
	public Controller(final GUI gui) {
		this.gui = gui;
		
		gui.addRunButtonListener(new RunButonActionListener());
		gui.addReturnKeyListener(new ReturnKeyKeyListener());
		gui.addCheatSheetButtonActionListener(new CheatSheetActionListener());
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
		final String regexPattern = (gui.hasExtendedSupport()) ? 
				"[^\\>\\<\\+\\-\\.\\,\\:\\;\\[\\]]" : 
				"[^\\>\\<\\+\\-\\.\\,\\[\\]]";
		final String bfCode = getCodeAreaText().replaceAll(regexPattern, "");
		final String stdIn =  getInputAreaText().replaceAll("[^0-9\\s]", "");
		Interpreter interpreter = new Interpreter();
		gui.addStopButtonListener(new StopButtonActionListener(interpreter));
		
		this.worker = new SwingWorker<Void, String>() {
			@Override
			protected Void doInBackground() throws Exception {
				interpreter.setMemoryWrap(gui.hasMemoryWrap());
				interpreter.run(bfCode, stdIn);
				return null;
			}
			
			protected void done() {
				gui.setResultAreaText(interpreter.getResult());
			}
		};
		
		worker.execute();
		
		final String debugInformation = gui.isInDebugMode() ? interpreter.getDebugInfo() : "";
		gui.setDebugDisplayLabel(debugInformation);
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
			//worker.cancel(true);
			interpreter.exitProgram();
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

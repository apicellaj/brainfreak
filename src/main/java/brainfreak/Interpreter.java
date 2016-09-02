package main.java.brainfreak;

import java.text.NumberFormat;
import java.util.List;
import java.util.Stack;

import javax.swing.SwingWorker;

public class Interpreter extends SwingWorker<Void, String> {

	final static private char memoryDumpChar = '#';

	private Controller controller;
	private MemoryTape memoryTape;
	private long startTime;
	private int[] inputArray;

	private String code = "";
	private String standardInput = "";
	private int codePosition = 0;
	private int inputPosition = 0;
	private long numberOfCalculations = 0;
	private boolean stopProgram = false;
	private boolean hasExtendedMode = false;
	private boolean hasMemoryDump = false;
	private byte[] memoryDumpSnapshot = null;

	private StringBuilder result = new StringBuilder();
	private StringBuilder warnings = new StringBuilder();

	public Interpreter(Controller controller) {
		this.controller = controller;
		memoryTape = new MemoryTape();
	}

	@Override
	protected Void doInBackground() throws Exception {
		initialize();
		if (controller != null) {
			controller.setDebugDisplayLabel("");
		}
		startTime = System.nanoTime();
		decode(codePosition);
		return null;
	}

	@Override
	protected void done() {
		if (controller != null) {
			if (hasMemoryDump && memoryDumpSnapshot != null) {
				new MemoryDump(memoryDumpSnapshot);
			}
			final String debugInformation = controller.isInDebugMode() ? getDebugInfo() : "";
			controller.setResultAreaText(getResult());
			controller.setDebugDisplayLabel(debugInformation);
		}
	}

	@Override
	protected void process(List<String> chunks) {
		if (controller != null) {
			controller.setResultAreaText(chunks.get(chunks.size() - 1));
		}
	}

	private void initialize() {
		replaceWithCode();
		this.inputArray = createInputArray(standardInput);
		checkForErrors();
	}

	private void replaceWithCode() {
		final String regexPattern = (hasExtendedMode) ? "[^\\>\\<\\+\\-\\.\\,\\:\\;\\[\\]\\#]"
				: "[^\\>\\<\\+\\-\\.\\,\\[\\]\\#]";
		this.code = code.replaceAll(regexPattern, "");
	}

	private int[] createInputArray(String stdIn) {
		if (stdIn.length() == 0) {
			return null;
		}
		String[] stdInArray = stdIn.split(" ");
		int[] intArray = new int[stdInArray.length];
		for (int i = 0; i < stdInArray.length; i++) {
			intArray[i] = Integer.parseInt(stdInArray[i]);
		}
		return intArray;
	}

	private void checkForErrors() {
		if (!hasValidBrackets()) {
			triggerError("Loop brackets paired incorrectly.");
		}
		if (!hasEnoughInputData()) {
			triggerError("Insufficient input data.");
		}
		if (!hasSingleMemoryDumpChar() && hasMemoryDump) {
			warnings.append("WARNING: Extra memory dump character.\n");
		}
	}

	private void decode(int start) {
		if (!stopProgram) {
			for (codePosition = start; codePosition < code.length()
					&& code.charAt(codePosition) != ']'; codePosition++) {
				interpret(code.charAt(codePosition));
				numberOfCalculations++;
				if (numberOfCalculations % 1000000 == 0) {
					publish(getResult());
				}
			}
		}
	}

	public String getResult() {
		final String output = result.toString() + appendWarnings();
		return output;
	}

	private void interpret(char c) {
		final char value;
		switch (c) {
		case ';':
			memoryTape.setValue((byte) inputArray[inputPosition++]);
			break;
		case ':':
			result.append(memoryTape.getValue()).append(' ');
			break;
		case ',':
			value = (char) (inputArray[inputPosition++]);
			memoryTape.setValue((byte) value);
			break;
		case '.':
			if (memoryTape.getValue() >= 0) {
				value = (char) memoryTape.getValue();
				result.append(value);
			}
			break;
		case '>':
			memoryTape.incrementPosition();
			checkMemory();
			break;
		case '<':
			memoryTape.decrementPosition();
			checkMemory();
			break;
		case '+':
			memoryTape.incrementMemory();
			break;
		case '-':
			memoryTape.decrementMemory();
			break;
		case '[':
			enterLoop(codePosition + 1);
			break;
		case memoryDumpChar:
			if (memoryDumpSnapshot == null && hasMemoryDump) {
				memoryDumpSnapshot = memoryTape.getMemoryTape();
			}
		}
	}

	private void checkMemory() {
		if (memoryTape.getMemoryPosition() < 0) {
			triggerError("Memory Underflow at character " + codePosition);
		} else if (memoryTape.getMemoryPosition() >= memoryTape.getMemorySize()) {
			triggerError("Memory Overflow at character " + codePosition);
		}
	}

	private void enterLoop(int startOfLoop) {
		if (codePosition < code.length() - 2 && code.substring(codePosition, codePosition + 3).equals("[-]")) {
			memoryTape.setValue((byte) 0);
			codePosition += 2;
			return;
		}
		int loopExit = loopExit(startOfLoop);
		while (memoryTape.getValue() != 0) {
			decode(startOfLoop);
		}
		codePosition = loopExit;
	}

	private int loopExit(int startOfLoop) {
		Stack<Character> s = new Stack<>();
		for (int j = startOfLoop; j < code.length(); j++) {
			if (code.charAt(j) == '[') {
				s.add(code.charAt(j));
			}
			if (code.charAt(j) == ']' && !s.isEmpty()) {
				s.pop();
			} else if (code.charAt(j) == ']' && s.isEmpty()) {
				return j;
			}
		}
		triggerError("Looping problem at: " + codePosition);
		return code.length();
	}

	private boolean hasValidBrackets() {
		final String bracketText = code.replaceAll("[^\\[\\]]", "");
		Stack<Character> s = new Stack<>();
		for (int pos = 0; pos < bracketText.length(); pos++) {
			if (bracketText.charAt(pos) == '[') {
				s.add('[');
			}
			if (bracketText.charAt(pos) == ']' && !s.isEmpty()) {
				s.pop();
			} else if (bracketText.charAt(pos) == ']' && s.isEmpty()) {
				return false;
			}
		}
		return s.isEmpty();
	}

	private boolean hasEnoughInputData() {
		final int inputCharacterLength = code.replaceAll("[^\\,\\;]", "").length();
		final int inputArraySize = inputArray == null ? 0 : inputArray.length;
		if (inputCharacterLength < inputArraySize) {
			warnings.append("WARNING: Unused input data.\n");
		}
		return inputCharacterLength <= inputArraySize ? true : false;
	}

	private boolean hasSingleMemoryDumpChar() {
		return code.indexOf(memoryDumpChar) == code.lastIndexOf(memoryDumpChar);
	}

	private void triggerError(String error) {
		result = new StringBuilder("ERROR: " + error + "\n");
		exitProgram();
	}

	private String appendWarnings() {
		final boolean emptyResult = result.toString().length() == 0;
		final boolean emptyWarnings = warnings.toString().length() == 0;
		String optionalLineBreak = emptyResult || emptyWarnings ? "" : "\n";
		return optionalLineBreak + warnings.toString();
	}

	public void exitProgram() {
		stopProgram = true;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setInput(String input) {
		this.standardInput = input;
	}

	public void setMemoryWrap(boolean hasMemoryWrap) {
		memoryTape.setMemoryWrap(hasMemoryWrap);
	}

	public void setExtendedMode(boolean hasExtendedMode) {
		this.hasExtendedMode = hasExtendedMode;
	}

	public void setMemoryDump(boolean hasMemoryDump) {
		this.hasMemoryDump = hasMemoryDump;
	}

	public void setMemorySize(int memorySize) {
		memoryTape.setMemorySize(memorySize);
	}

	public byte[] getMemoryDumpSnapshot() {
		return memoryDumpSnapshot;
	}

	public String getDebugInfo() {
		final long endTime = System.nanoTime();
		final long totalTime = endTime - startTime;
		return "Executed " + NumberFormat.getInstance().format(numberOfCalculations) + " commands in "
				+ NumberFormat.getInstance().format(totalTime) + " ms.";
	}

}
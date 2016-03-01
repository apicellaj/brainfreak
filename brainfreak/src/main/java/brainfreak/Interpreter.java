package main.java.brainfreak;

import java.text.NumberFormat;
import java.util.List;
import java.util.Stack;

import javax.swing.SwingWorker;

public class Interpreter extends SwingWorker<Void, String>{
    
    //TODO: maybe create an enum for the different array bit states
    //TODO: maybe convert BF string with run length encoding (RLE)* for optimization

    private String code;
    private Controller controller;
    private long startTime;
    private int[] inputArray;
    
    private int memoryPosition = 0;
    private int codePosition = 0;
    private int inputPosition = 0;
    private boolean hasMemoryWrapEnabled = false;
    private boolean stopProgram = false;
    private StringBuilder result = new StringBuilder();
    private StringBuilder warnings = new StringBuilder();
    private long numberOfCalculations = 0;
    private static final long MAX_CALCULATIONS_ALLOWED = Long.MAX_VALUE;
    private static final int MEMORY_SIZE = 30000;
    private int[] memoryArray = new int[MEMORY_SIZE];
    
    public Interpreter(String simplifiedCode, String standardInput, boolean hasMemoryWrap, Controller controller) {
    	code = simplifiedCode;
    	inputArray = createInputArray(standardInput);
    	hasMemoryWrapEnabled = hasMemoryWrap;
    	this.controller = controller;
    }
    
    @Override
	protected Void doInBackground() throws Exception {
    	checkForErrors();
    	if (controller != null) {
    		controller.setDebugDisplayLabel("");
    	}
		startTime = System.currentTimeMillis();
		decode(codePosition);
		return null;
	}
    
    @Override
    protected void done() {
		appendWarnings();
		if (controller != null) {
			final String debugInformation = controller.isInDebugMode() ? getDebugInfo() : "";
			controller.setResultAreaText(getResult());
			controller.setDebugDisplayLabel(debugInformation);
		}
    	
    }
    
    @Override
    protected void process(List<String> chunks) {
    	if (controller != null) {
    		controller.setResultAreaText(chunks.get(chunks.size()-1));
    	}
    }
    
    private void checkForErrors() {
    	if(!hasValidBrackets()) {
		    triggerError("Loop brackets paired incorrectly.");
		}
		if (!hasEnoughInputData()) {
			triggerError("Insufficient input data.");
		}
    }
    
    public String getResult() {
    	return result.toString();
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
    
    private void decode(int start) {
    	if (!stopProgram) {
    		for (codePosition = start; codePosition < code.length() && code.charAt(codePosition) != ']'; codePosition++) {
                interpret(code.charAt(codePosition));
                numberOfCalculations++;
                if (numberOfCalculations == MAX_CALCULATIONS_ALLOWED) {
                	triggerError("Exceeded maximum number of calculations: " + 
                		NumberFormat.getInstance().format(MAX_CALCULATIONS_ALLOWED));
                }
            }
    	}
    }
    
    private void interpret(char c) {
    	if (numberOfCalculations % 1000000 == 0) {
    		publish(getResult());
    	}
    	final char value;
    	switch (c) {
	    	case ';' : 	memoryArray[memoryPosition] = inputArray[inputPosition++];
	        			break;
	    	case ':' :	result.append(memoryArray[memoryPosition]).append(' ');
	    				break;
	    	case ',' :	value = (char) (inputArray[inputPosition++]+48);
				        memoryArray[memoryPosition] = (int) value;
				        break;
	    	case '.' : 	if (memoryArray[memoryPosition] >= 0) {
				    		value = (char) memoryArray[memoryPosition];
				            result.append(value);
		            	}
	    				break;
	    	case '>' : 	memoryPosition++;
	    				break;
	    	case '<' : 	memoryPosition--;
	    				break;
	    	case '+' :	memoryArray[memoryPosition]++;
	    				break;
	    	case '-' :	memoryArray[memoryPosition]--;
	    				break;
	    	case '[' :	enterLoop(codePosition+1);
	    				break;
    	}
    	if (hasMemoryWrapEnabled) {
    		memoryWrap();
    	} else {
    		if (memoryPosition < 0) {
    			triggerError("Memory Underflow at character " + codePosition);
    		} else if (memoryPosition > MEMORY_SIZE) {
    			triggerError("Memory Overflow at character " + codePosition);
    		}
    	}
    }
    
    private void memoryWrap() {
    	if (memoryPosition >= MEMORY_SIZE) {
    		memoryPosition %= MEMORY_SIZE;
        } else if (memoryPosition < 0) {
        	memoryPosition += MEMORY_SIZE;
        }
    }
    
    private void enterLoop(int startOfLoop) {
    	if (codePosition < code.length()-2 && code.substring(codePosition, codePosition+3).equals("[-]")) {
			memoryArray[memoryPosition] = 0;
			codePosition += 2;
			return;
		}
        int loopExit = loopExit(startOfLoop);
        while(memoryArray[memoryPosition] != 0) {
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
		return inputCharacterLength <= inputArraySize ? true: false;
    }
    
    private void triggerError(String error) {
    	result = new StringBuilder("ERROR: " + error + "\n");
    	exitProgram();
    }
    
    private void appendWarnings() {
    	result.append(warnings);
    }
    
    public void exitProgram() {
    	stopProgram = true;
    }
    
    public String getDebugInfo() {
    	final long endTime = System.currentTimeMillis();
    	final long totalTime = endTime - startTime;
    	return "Executed " + NumberFormat.getInstance().format(numberOfCalculations) + 
    			" commands in " + NumberFormat.getInstance().format(totalTime) + " ms.";
    }
    
}
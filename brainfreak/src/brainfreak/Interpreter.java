package brainfreak;

import java.text.NumberFormat;
import java.util.Stack;

class Interpreter {
    
    //TODO: maybe create an enum for the different array bit states
    //TODO: maybe convert BF string with run length encoding (RLE)* for optimization
    //TODO: find a more efficient way to write to resultArea
    //TODO: allow operation to be cancelled with a button (halt button)
    
    private int memoryPosition;
    private int codePosition;
    private int inputPosition;
    private boolean memoryWrap = false;
    private boolean stopProgram = false;
    private String code;
    private StringBuilder result;
    private StringBuilder warnings;
    private long numberOfCalculations;
    private long startTime;
    private long endTime;
    private static final long MAX_CALCULATIONS_ALLOWED = Integer.MAX_VALUE;
    private static final int MEMORY_SIZE = 30000;
    private int[] inputArray;
    private int[] memoryArray;
    
    public Interpreter() {
    }
    
    public void run(String simplifiedCode, String standardInput) {
    	variableInitialization();
    	//dump();
    	code = simplifiedCode;
    	inputArray = createInputArray(standardInput);
		checkForErrors();
		decode(codePosition);
		appendWarnings();
		endTime   = System.currentTimeMillis();
		codePosition = 0;
    }
    
    public void dump() {
    	System.out.println("memPos " + memoryPosition);
    	System.out.println("codePos " + codePosition);
    	System.out.println("inputPos " + inputPosition);
    	System.out.println("noOfCalc " + numberOfCalculations);
    	System.out.println("stopProgram " + stopProgram);
    	System.out.println("result " + result);
    }
    
    private void variableInitialization() {
    	memoryPosition = 0;
    	codePosition = 0;
    	inputPosition = 0;
    	numberOfCalculations = 0;
    	stopProgram = false;
    	memoryArray = new int[MEMORY_SIZE];
    	result = new StringBuilder();
    	warnings = new StringBuilder();
    	startTime = System.currentTimeMillis();
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
    	//if (numberOfCalculations % 100000000 == 0) System.out.println(numberOfCalculations);
    	
    	final char value;
    	switch (c) {
	    	case ';' : 	memoryArray[memoryPosition] = inputArray[inputPosition++];
	        			break;
	    	case ':' :	result.append(memoryArray[memoryPosition]).append(' ');
	    				break;
	    	case ',' :	value = (char) (inputArray[inputPosition++]+48);
				        memoryArray[memoryPosition] = (int) value;
				        break;
	    	case '.' : 	if (memoryArray[memoryPosition] >= 10) {
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
    	if (memoryWrap) {
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
    	codePosition = 0;
    }
    
    public String getDebugInfo() {
    	final long totalTime = endTime - startTime;
    	return "Executed " + NumberFormat.getInstance().format(numberOfCalculations) + " commands in " + totalTime + " ms.";
    }
    
    public void setMemoryWrap(boolean enableMemoryWrap) {
    	memoryWrap = enableMemoryWrap;
    }
}
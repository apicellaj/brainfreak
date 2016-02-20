package brainfreak;

import java.util.Stack;

class Interpreter {
    
    //TODO: maybe create an enum for the different array bit states
    //TODO: maybe convert BF string with run length encoding (RLE)* for optimization
    //TODO: reduce scope as much as possible, use final modifier where possible
    //TODO: find a more efficient way to write to resultArea
    //TODO: allow operation to be cancelled with a button (halt button)
    
    private int memoryPosition = 0;
    private int codePosition = 0;
    private int inputPosition = 0;
    private final String code;
    private StringBuilder result = new StringBuilder();
    private long numberOfCalculations = 0L;
    private static final long MAX_CALCULATIONS_ALLOWED = Integer.MAX_VALUE;
    private static final int MEMORY_SIZE = 30000;
    private int[] inputArray;
    private int[] memoryArray = new int[MEMORY_SIZE];
    
    Interpreter(String simplifiedCode, String standardInput) {
		code = simplifiedCode;
		inputArray = createInputArray(standardInput);
		run();
    }
    
    private void run() {
		if(!hasValidBrackets()) {
		    result.append("ERROR: Loop brackets paired incorrectly.");
		    return;
		}
		if (!hasEnoughInputData()) {
			result.append("ERROR: Insufficient input data.");
			return;
		}
		long startTime = System.currentTimeMillis();
		decode(codePosition);
		long endTime   = System.currentTimeMillis();
    	long totalTime = endTime - startTime;
    	System.out.println(totalTime);
		result.append("\n\n" + "Total running time: " + totalTime + " ms");
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
        for (codePosition = start; codePosition < code.length() && code.charAt(codePosition) != ']'; codePosition++) {
            interpret(code.charAt(codePosition));
            numberOfCalculations++;
            if (numberOfCalculations == MAX_CALCULATIONS_ALLOWED) {
            	result.append("\n\nERROR: Exceeded maximum number of calculations: " + MAX_CALCULATIONS_ALLOWED);
                return;
            }
        }
    }
    
    private void interpret(char c) {
        if (c == ';') {
        	memoryArray[memoryPosition] = inputArray[inputPosition];
            inputPosition++;
        }
        if (c == ':') result.append(memoryArray[memoryPosition]).append(' ');
        if (c == ',') {
            char value = (char) (inputArray[inputPosition]+48);
            memoryArray[memoryPosition] = (int) value;
            inputPosition++;
        }
        if (c == '.') {
            if (memoryArray[memoryPosition] < 10) {
              return;
            }
            char value = (char) memoryArray[memoryPosition];
            result.append(value);
        }
        if (c == '>') {
            memoryPosition++;
            if (memoryPosition >= MEMORY_SIZE) {
        	memoryPosition %= MEMORY_SIZE;
            }
        }
        if (c == '<') {
            memoryPosition--;
            if (memoryPosition < 0) {
        	memoryPosition += MEMORY_SIZE;
            }
        }
        if (c == '+') memoryArray[memoryPosition]++;
        if (c == '-') memoryArray[memoryPosition]--;
        if (c == '[') enterLoop(codePosition+1);
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
        result.append("\n\nERROR: Looping problem.");
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
    		result.append("WARNING: Unused input data.\n");
    	}
		return inputCharacterLength <= inputArraySize ? true: false;
    }
}
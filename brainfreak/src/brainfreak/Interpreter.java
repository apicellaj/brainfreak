package brainfreak;

import java.util.Stack;

class Interpreter {
    
    //TODO: maybe create an enum for the different array bit states
    
    //TODO: maybe convert BF string with run length encoding (RLE)* for optimization
    
    //TODO: reduce scope as much as possible, use final modifier where possible
    
    //TODO: allow string to be sent out after each write command, use StringBuilder?
    
    //TODO: allow operation to be cancelled with a button (halt button)
    
    int posInArray = 0;
    int i = 0;
    private String code;
    private String result = "";
    int inputPos = 0;
    long numberOfCalculations = 0L;
    int[] inputArray;
    int[] brainfuckDataArray = new int[30000];
    
    Interpreter(String simplifiedCode, String standardInput) {
	code = simplifiedCode;
	inputArray = createInputArray(standardInput);
	result = run();
    }
    
    private String run() {
	//TODO: check for valid amounts of input before running
	//TODO: possibly change bracket check to a void function
	if(!bracketCheck()) {
	    System.out.println("\n\nERROR: Loop brackets paired incorrectly.");
	    System.exit(-1);
	}
	decode(i);
	return result;
    }
    
    public String getResult() {
	return result;
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
	
        for (i = start; i < code.length() && code.charAt(i) != ']'; i++) {
            interpret(code.charAt(i));
            numberOfCalculations++;
            if (numberOfCalculations > Long.MAX_VALUE) {
                System.out.print("\n\nERROR: WAY Over 100,000 calculations. ");
                System.exit(-1);
            }
        }
    }
    
    private void interpret(char c) {
        if (c == ';') {
            if (inputPos >= inputArray.length) {
                System.out.println("\n\nERROR: Insufficient input data provided.");
                System.exit(-1);
            } else {
                brainfuckDataArray[posInArray] = inputArray[inputPos];
                inputPos++;
            }
        }
        if (c == ':') result = result + brainfuckDataArray[posInArray] + " ";
        if (c == ',') {
            char value = (char) (inputArray[inputPos]+48);
            brainfuckDataArray[posInArray] = (int) value;
            inputPos++;
        }
        if (c == '.') {
            if (brainfuckDataArray[posInArray] < 10) {
              return;
            }
            char value = (char) brainfuckDataArray[posInArray];
            result = result + value;
        }
        if (c == '>') {
            posInArray++;
            if (posInArray >= 30000) {
        	posInArray %= 30000;
            }
        }
        if (c == '<') {
            posInArray--;
            if (posInArray < 0) {
        	posInArray += 30000;
            }
        }
        if (c == '+') brainfuckDataArray[posInArray]++;
        if (c == '-') brainfuckDataArray[posInArray]--;
        if (c == '[') enterLoop(i+1);
    }
    
    private void enterLoop(int startOfLoop) {
        int loopExit = loopExit(startOfLoop);
        while(brainfuckDataArray[posInArray] != 0) decode(startOfLoop);
        i = loopExit;
    }
    
    private int loopExit(int startOfLoop) {
        Stack<Character> s = new Stack<>();
        for (int j = startOfLoop; j < code.length(); j++) {
            if (code.charAt(j) == '[') s.add(code.charAt(j));
            if (code.charAt(j) == ']' && !s.isEmpty()) s.pop();
            else if (code.charAt(j) == ']' && s.isEmpty()) return j;
        }
        System.out.println("\n\nERROR: Looping problem.");
        return code.length();
    }
    
    private boolean bracketCheck() {
        Stack<Character> s = new Stack<>();
        for (int pos = 0; pos < code.length(); pos++) {
            if (code.charAt(pos) == '[') s.add(code.charAt(pos));
            if (code.charAt(pos) == ']' && !s.isEmpty()) s.pop();
            else if (code.charAt(pos) == ']' && s.isEmpty()) {
               return false; 
            }
        }
        return s.isEmpty();
    }
}
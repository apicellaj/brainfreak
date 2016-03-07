package test.java.brainfreak;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.brainfreak.Interpreter;

public class InterpreterTest {

	private Interpreter interpreter;

	private String badBracketsRight;
	private String badBracketsLeft;
	private String unusedInput;
	private String memoryWrapUnderflowTest;
	private String memoryWrapOverflowTest;
	private String helloWorld;
	private String extendedModeDivision;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		badBracketsRight = "][]";
		badBracketsLeft = "[]]";
		unusedInput = "1 2 3 4";
		memoryWrapUnderflowTest = "<<++++[-<++++++++>]<+.";
		memoryWrapOverflowTest = ">>>++++[-<++++++++>]<+.";
		helloWorld = ">++++++++[-<+++++++++>]<.>>+>-[+]++>++>+++[>[->+++<<+++>]<<]>-----.>->"
					+ "+++..+++.>-.<<+[>[+>+]>>]<--------------.>>.+++.------.--------.>+.>+";
		extendedModeDivision = ";[>+<--[>>+>+<<<-]>>[-[>[<<<+>>>-]<[-]]>[->+<]<]<<]>:>>>:";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBrackets() {
		interpreter = new Interpreter(null);
		interpreter.setCode(badBracketsRight);
		interpreter.run();
		assertEquals("ERROR: Loop brackets paired incorrectly.\n", interpreter.getResult());
		
		interpreter = new Interpreter(null);
		interpreter.setCode(badBracketsLeft);
		interpreter.run();
		assertEquals("ERROR: Loop brackets paired incorrectly.\n", interpreter.getResult());
	}

	@Test
	public void testUnusedInput() {
		interpreter = new Interpreter(null);
		interpreter.setInput(unusedInput);
		interpreter.run();
		assertEquals("WARNING: Unused input data.\n", interpreter.getResult());
		
		interpreter = new Interpreter(null);
		interpreter.setCode(helloWorld);
		interpreter.setInput(unusedInput);
		interpreter.run();
		assertEquals("Hello World!\nWARNING: Unused input data.\n", interpreter.getResult());
	}
	
	@Test
	public void testInsufficientInput() {
		interpreter = new Interpreter(null);
		interpreter.setCode(",");
		interpreter.run();
		assertEquals("ERROR: Insufficient input data.\n", interpreter.getResult());
	}

	@Test
	public void testMemoryWrapOff() {
		interpreter = new Interpreter(null);
		interpreter.setCode(memoryWrapUnderflowTest);
		interpreter.run();
		assertEquals("ERROR: Memory Underflow at character 1\n", interpreter.getResult());
		
		interpreter = new Interpreter(null);
		interpreter.setCode(memoryWrapOverflowTest);
		interpreter.setMemorySize(2);
		interpreter.run();
		assertEquals("ERROR: Memory Overflow at character 2\n", interpreter.getResult());
	}

	@Test
	public void testMemoryWrapOn() {
		interpreter = new Interpreter(null);
		interpreter.setCode(memoryWrapUnderflowTest);
		interpreter.setMemoryWrap(true);
		interpreter.run();
		assertEquals("!", interpreter.getResult());
		
		interpreter = new Interpreter(null);
		interpreter.setCode(memoryWrapOverflowTest);
		interpreter.setMemoryWrap(true);
		interpreter.setMemorySize(2);
		interpreter.run();
		assertEquals("!", interpreter.getResult());
	}

	@Test
	public void testHelloWorld() {
		interpreter = new Interpreter(null);
		interpreter.setCode(helloWorld);
		interpreter.run();
		assertEquals("Hello World!", interpreter.getResult());
	}

	@Test
	public void testExtendedMode() {
		interpreter = new Interpreter(null);
		interpreter.setCode(extendedModeDivision);
		interpreter.setInput("19");
		interpreter.setExtendedMode(true);
		interpreter.run();
		assertEquals("9 1 ", interpreter.getResult());
		
		interpreter = new Interpreter(null);
		interpreter.setCode(extendedModeDivision);
		interpreter.setInput("19 1");
		interpreter.setExtendedMode(true);
		interpreter.run();
		assertEquals("9 1 \nWARNING: Unused input data.\n", interpreter.getResult());
	}

}

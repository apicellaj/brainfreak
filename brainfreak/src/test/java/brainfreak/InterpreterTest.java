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
	
	private final static boolean MEMORY_WRAP_OFF = false;
	private final static boolean MEMORY_WRAP_ON = true;
	private final static String NO_STD_IN = "";

	private String badBrackets;
	private String unusedInput;
	private String memoryWrap;
	private String helloWorld;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		badBrackets = "][]";
		unusedInput = "1 2 3 4";
		memoryWrap = "<++++[-<++++++++>]<+.";
		helloWorld = ">++++++++[-<+++++++++>]<.>>+>-[+]++>++>+++[>[->+++<<+++>]<<]>-----.>->"
					+ "+++..+++.>-.<<+[>[+>+]>>]<--------------.>>.+++.------.--------.>+.>+";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBrackets() {
		interpreter = new Interpreter(badBrackets, NO_STD_IN, MEMORY_WRAP_OFF, null);
		interpreter.run();
		assertEquals("ERROR: Loop brackets paired incorrectly.\n", interpreter.getResult());
	}

	@Test
	public void testUnusedInput() {
		interpreter = new Interpreter("", unusedInput, MEMORY_WRAP_OFF, null);
		interpreter.run();
		assertEquals("WARNING: Unused input data.\n", interpreter.getResult());
	}
	
	@Test
	public void testInsufficientInput() {
		interpreter = new Interpreter(",", NO_STD_IN, MEMORY_WRAP_OFF, null);
		interpreter.run();
		assertEquals("ERROR: Insufficient input data.\n", interpreter.getResult());
	}

	@Test
	public void testMemoryWrapOff() {
		interpreter = new Interpreter(memoryWrap, NO_STD_IN, MEMORY_WRAP_OFF, null);
		interpreter.run();
		assertEquals("ERROR: Memory Underflow at character 0\n", interpreter.getResult());
	}

	@Test
	public void testMemoryWrapOn() {
		interpreter = new Interpreter(memoryWrap, NO_STD_IN, MEMORY_WRAP_ON, null);
		interpreter.run();
		assertEquals("!", interpreter.getResult());
	}

	@Test
	public void testHelloWorld() {
		interpreter = new Interpreter(helloWorld, NO_STD_IN, MEMORY_WRAP_OFF, null);
		interpreter.run();
		assertEquals("Hello World!", interpreter.getResult());
	}

	@Test
	public void testExtendedMode() {
		//TODO
	}

}

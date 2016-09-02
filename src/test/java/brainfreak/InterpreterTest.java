package test.java.brainfreak;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.brainfreak.Controller;
import main.java.brainfreak.GUI;
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
	private String deletionLoopTest;
	private String helloWorldMemoryDump;

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
		deletionLoopTest = "++++++++++:[-]:";
		helloWorldMemoryDump = ">++++++++[-<+++++++++>]<.>>+>-[+]++>++>+++[>[->+++<<+++>]<<"
				+ "]>-----.>->+++..+++.>-.<<+[>[+>+]>>]<--------------.>>.+++.------.--------.>+.>+.#";
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

		interpreter = new Interpreter(null);
		interpreter.setCode("]");
		interpreter.run();
		assertEquals("ERROR: Loop brackets paired incorrectly.\n", interpreter.getResult());

		interpreter = new Interpreter(null);
		interpreter.setCode("[");
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

		interpreter = new Interpreter(null);
		interpreter.setCode(helloWorld);
		interpreter.setMemorySize(1);
		interpreter.run();
		assertEquals("ERROR: Memory Overflow at character 0\n", interpreter.getResult());
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

	@Test
	public void testDebugInfo() {
		interpreter = new Interpreter(null);
		interpreter.setCode("");
		interpreter.run();
		assertTrue(interpreter.getDebugInfo().startsWith("Executed 0 commands in "));
	}

	@Test
	public void testDeleteLoop() {
		interpreter = new Interpreter(null);
		interpreter.setCode(deletionLoopTest);
		interpreter.setExtendedMode(true);
		interpreter.run();
		assertEquals("10 0 ", interpreter.getResult());
	}

	@Test
	public void testInput() {
		interpreter = new Interpreter(null);
		interpreter.setCode(",.>,.>,.>,.");
		interpreter.setInput("84 101 115 116");
		interpreter.run();
		assertEquals("Test", interpreter.getResult());

		interpreter = new Interpreter(null);
		interpreter.setCode(",.");
		interpreter.setInput("-1");
		interpreter.run();
		assertEquals("", interpreter.getResult());
	}

	@Test
	public void testMemoryDump() {
		interpreter = new Interpreter(new Controller(new GUI()));
		interpreter.setCode(helloWorldMemoryDump);
		interpreter.setMemoryDump(true);
		interpreter.run();
		byte[] memoryDumpSnapshot = interpreter.getMemoryDumpSnapshot();
		assertNotNull(memoryDumpSnapshot);

		assertEquals(72, memoryDumpSnapshot[0]);
		assertEquals(87, memoryDumpSnapshot[2]);
		assertEquals(100, memoryDumpSnapshot[4]);
	}

	@Test
	public void testMemoryDumpOff() {
		interpreter = new Interpreter(new Controller(new GUI()));
		interpreter.setCode(helloWorldMemoryDump);
		interpreter.setMemoryDump(false);
		interpreter.run();
		assertNull(interpreter.getMemoryDumpSnapshot());
	}

	@Test
	public void testExtraMemoryDumpChar() {
		interpreter = new Interpreter(new Controller(new GUI()));
		interpreter.setCode("#" + helloWorldMemoryDump);
		interpreter.setMemoryDump(true);
		interpreter.run();
		byte[] extraMemoryDumpChar = interpreter.getMemoryDumpSnapshot();
		assertNotNull(extraMemoryDumpChar);

		assertEquals("Hello World!\n" + "\nWARNING: Extra memory dump character.\n", interpreter.getResult());

		assertEquals(0, extraMemoryDumpChar[0]);
		assertEquals(0, extraMemoryDumpChar[2]);
		assertEquals(0, extraMemoryDumpChar[4]);
	}

	@Test
	public void testMultipleWarnings() {
		interpreter = new Interpreter(new Controller(new GUI()));
		interpreter.setCode(helloWorldMemoryDump + "#");
		interpreter.setInput(unusedInput);
		interpreter.setMemoryDump(true);
		interpreter.run();
		assertEquals(
				"Hello World!\n" + "\n" + "WARNING: Unused input data.\n" + "WARNING: Extra memory dump character.\n",
				interpreter.getResult());

		interpreter = new Interpreter(new Controller(new GUI()));
		interpreter.setCode(helloWorldMemoryDump + "#]");
		interpreter.setInput(unusedInput);
		interpreter.setMemoryDump(true);
		interpreter.run();
		assertEquals("ERROR: Loop brackets paired incorrectly.\n" + "\n" + "WARNING: Unused input data.\n"
				+ "WARNING: Extra memory dump character.\n", interpreter.getResult());
	}

}

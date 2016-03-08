package test.java.brainfreak;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.brainfreak.Controller;
import main.java.brainfreak.GUI;

public class ControllerTest {
	
	private String squareNumbers;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		squareNumbers = "++++[>+++++<-]>[<+++++>-]+<+[>[>+>+<<-]++>>[<<+>>-]>>>[-]++>[-]+\n" + 
				">>>+[[-]++++++>>>]<<<[[<++++++++<++>>-]+<.<[>----<-]<]<<[>>>>>[>>>[-]+++\n" +
				"++++++<[>-<-]+++++++++>[-[<->-]+[<<<]]<[>+<-]>]<<-]<<-]";
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testController() {
		GUI gui = new GUI();
		Controller controller = new Controller(gui);
		controller.setMemoryFieldText("1000");
		assertEquals(1000, gui.getMemoryFieldValue());
		
		controller.resetMemoryFieldText();
		assertEquals(30000, gui.getMemoryFieldValue());
		
		controller.setCodeAreaText(squareNumbers);
		gui.clickRunButton();
		
		controller.setResultAreaText("test");
		assertEquals("test", gui.getResultAreaText());
		
		gui.setResultAreaText("reset");
		gui.resetComboBox();
		gui.setSizeLarge();
		assertEquals("reset", gui.getResultAreaText());
		
		assertFalse(controller.isInDebugMode());
	}

}

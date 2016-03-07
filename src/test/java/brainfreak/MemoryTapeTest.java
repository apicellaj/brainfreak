package test.java.brainfreak;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.brainfreak.MemoryTextPanel;

public class MemoryTapeTest {
	
	MemoryTextPanel textPanel;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMemoryPanel() {
		textPanel = new MemoryTextPanel();
		textPanel.setMemoryFieldText("1234567890");
		assertEquals("123456789", textPanel.getMemoryFieldText());
		
		textPanel.setMemoryFieldText("");
		assertEquals("0", textPanel.getMemoryFieldText());
		
		textPanel.setMemoryFieldText("abcdef");
		assertEquals("0", textPanel.getMemoryFieldText());
		
		textPanel.setMemoryFieldText("a1b2c3d4");
		assertEquals("1234", textPanel.getMemoryFieldText());
	}

}

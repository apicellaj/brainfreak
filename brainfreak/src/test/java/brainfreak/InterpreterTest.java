package test.java.brainfreak;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.brainfreak.Interpreter;

public class InterpreterTest {
  
  private String badBrackets = "][]";
  private Interpreter interpreter = new Interpreter(badBrackets, "", false, null);

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
  public void test() {
    interpreter.run();
    assertEquals("ERROR: Loop brackets paired incorrectly.\n", interpreter.getResult());
  }

}

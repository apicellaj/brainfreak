package brainfreak;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
//import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GUI {
    
    private JTextArea codeArea;
    private JTextArea inputArea;
    private JTextArea resultArea;
    
    void createAndShowGui() {
	//TODO: Add unit testing
	JFrame frame = new JFrame();
	JPanel rightPanel = new JPanel();
	rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
	JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
	//TODO: maybe make a string pool for localization, probably not necessary
	JLabel codeLabel = new JLabel("Please enter your code below:");
	JLabel inputLabel = new JLabel("Enter standard input (if any):");
	JLabel resultLabel = new JLabel("Result:");
	//TODO: make text area scrollable (look it up)
	codeArea = new JTextArea(20,1000);
	inputArea = new JTextArea(3,1000);
	resultArea = new JTextArea(3,1000);
	resultArea.setEditable(false);
	codeArea.setAutoscrolls(true);
	//TODO: add separate KeyListener inner class, might look nicer
	codeArea.addKeyListener(new KeyListener() {
	    private boolean shiftIsPressed = false;

	    @Override
	    public void keyTyped(KeyEvent e) {}

	    @Override
	    public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
		    shiftIsPressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER && shiftIsPressed) {
		    launchInterpreter();
		}
	    }

	    @Override
	    public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
		    shiftIsPressed = false;
		}
	    }
	    
	});
	
	// TODO: Add Halt button
	
	JButton runButton = new JButton("Run");
    	runButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		launchInterpreter();
	    }
    	});
    	//**JButton haltButton = new JButton("Halt");
    	//frame.setResizable(false);

    	centerPanel.add(codeLabel);
        centerPanel.add(codeArea);
        centerPanel.add(inputLabel);
        centerPanel.add(inputArea);
        centerPanel.add(resultLabel);
        centerPanel.add(resultArea);
    
        frame.getContentPane().add(BorderLayout.WEST, Box.createRigidArea(new Dimension(100,0)));
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        
        
        //**JComboBox<String> samplePrograms = createComboBox();
        //**ComboBoxRenderer renderer = new ComboBoxRenderer();
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.add(runButton);
        //**rightButtonPanel.add(haltButton);
        
        //rightPanel.add(runButton);
        rightPanel.add(rightButtonPanel);
        //rightPanel.add(BorderLayout.WEST, Box.createRigidArea(new Dimension(0,400)));
        //**rightPanel.add(samplePrograms);
        //rightPanel.add(BorderLayout.WEST, Box.createRigidArea(new Dimension(0,400)));
        frame.getContentPane().add(BorderLayout.EAST, rightPanel);
        
        frame.setSize(1200, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle("Brainfreak Interpreter");

        /*
         HELLO WORLD
         ++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.
    	
    	More bug prone HELLO WORLD
        >++++++++[-<+++++++++>]<.>>+>-[+]++>++>+++[>[->+++<<+++>]<<]>-----.>->
    	+++..+++.>-.<<+[>[+>+]>>]<--------------.>>.+++.------.--------.>+.>+.
    	
    	square numbers from 1 to 10000
    	++++[>+++++<-]>[<+++++>-]+<+[
            >[>+>+<<-]++>>[<<+>>-]>>>[-]++>[-]+
            >>>+[[-]++++++>>>]<<<[[<++++++++<++>>-]+<.<[>----<-]<]
            <<[>>>>>[>>>[-]+++++++++<[>-<-]+++++++++>[-[<->-]+[<<<]]<[>+<-]>]<<-]<<-
        ]
        
        
        fibonacci
        +++++++++++
        >+>>>>++++++++++++++++++++++++++++++++++++++++++++
        >++++++++++++++++++++++++++++++++<<<<<<[>[>>>>>>+>
        +<<<<<<<-]>>>>>>>[<<<<<<<+>>>>>>>-]<[>++++++++++[-
        <-[>>+>+<<<-]>>>[<<<+>>>-]+<[>[-]<[-]]>[<<[>>>+<<<
        -]>>[-]]<<]>>>[>>+>+<<<-]>>>[<<<+>>>-]+<[>[-]<[-]]
        >[<<+>>[-]]<<<<<<<]>>>>>[+++++++++++++++++++++++++
        +++++++++++++++++++++++.[-]]++++++++++<[->-<]>++++
        ++++++++++++++++++++++++++++++++++++++++++++.[-]<<
        <<<<<<<<<<[>>>+>+<<<<-]>>>>[<<<<+>>>>-]<-[>>.>.<<<
        [-]]<<[>>+>+<<<-]>>>[<<<+>>>-]<<[<+>-]>[<+>-]<<<-]
         */
        
        /*   Divide by two:
            *   Divides the standard input by two and prints out the remainder.
            *
            *   ;[>+<--[>>+>+<<<-]>>[-[>[<<<+>>>-]<[-]]>[->+<]<]<<]>:>>>:
            *
            *   Fibonacci:
            *   Uses Fibonacci-like summation to calculate n terms beginning with
            *   numbers a and b. Input in the form of: n a b
            *
            *   ;>;>;<<[->>[-<+>>+<]<[->+<]>:>[-<<+>>]<<<]
    	*/
        
        // TODO: future testing here
        //http://www.hevanet.com/cristofd/brainfuck/tests.b
    }
    
    /*
    private JComboBox<String> createComboBox() {
	String[] comboBoxChoices = {"None", "Hello World!", "Square Numbers", "Fibonacci"};
	JComboBox<String> comboBox = new JComboBox<>(comboBoxChoices);
	comboBox.setPreferredSize(new Dimension(200,10));
	comboBox.setOpaque(true);
	return comboBox;
    }*/
  
    private void launchInterpreter() {
	//TODO: change the regex here to be customizable based on which check boxes are toggled (brainfreak++)
	final String bfCode = codeArea.getText().replaceAll("[^\\>\\<\\+\\-\\.\\,\\:\\;\\[\\]]", "").trim();
	final String stdIn =  inputArea.getText().trim();
	resultArea.setText(new Interpreter(bfCode, stdIn).getResult());
    }
    
}
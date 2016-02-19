package brainfreak;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class GUI extends JFrame {
    
	private static final long serialVersionUID = 1783527103493287966L;
	
	private JTextArea codeArea;
    private JTextArea inputArea;
    private JTextArea resultArea;
    
    void createAndShowGui() {
		//TODO: Add unit testing
    	//TODO: maybe make a string pool for localization, probably not necessary
    	//TODO: future testing here ->  http://www.hevanet.com/cristofd/brainfuck/tests.b
    	//TODO: make text area scrollable (look it up)
    	
		JFrame frame = new JFrame();
		
		JPanel rightPanel = new JPanel();
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel comboPanel = new JPanel();
	    
		JLabel codeLabel = new JLabel("Please enter your code below:");
		JLabel inputLabel = new JLabel("Enter standard input (if any):");
		JLabel resultLabel = new JLabel("Result:");
		JLabel sampleProgramsLabel = new JLabel("Sample programs:");
		
		JButton runButton = new JButton("Run");
		//**JButton haltButton = new JButton("Halt");
		
		codeArea = new JTextArea(20,70);
		inputArea = new JTextArea(3,70);
		resultArea = new JTextArea(3,70);
		
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
	
    	runButton.addActionListener(new ActionListener() {
	    @Override
		    public void actionPerformed(ActionEvent e) {
				launchInterpreter();
		    }
    	});
    	
    	centerPanel.add(codeLabel);
        centerPanel.add(codeArea);
        centerPanel.add(inputLabel);
        centerPanel.add(inputArea);
        centerPanel.add(resultLabel);
        centerPanel.add(resultArea);
        
        ExampleList el = new ExampleList(this);
        JComboBox<String> sampleProgramsComboBox = el.createComboBox();
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.add(runButton);
        //**rightButtonPanel.add(haltButton);
        
        JButton cheatSheetButton = new JButton("ASCII Table");
        cheatSheetButton.addActionListener(new ActionListener() {
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				AsciiCheatSheet.getInstance();
			}
        	
        });
        
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setPreferredSize(new Dimension(300,800));
        rightPanel.setBorder(BorderFactory.createEtchedBorder());
        rightPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20, 20, 20, 20), new EtchedBorder()));
        rightPanel.add(rightButtonPanel);
        
        comboPanel.setPreferredSize(new Dimension(100,100));
        comboPanel.setBorder(BorderFactory.createEtchedBorder());
        comboPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20, 20, 20, 20), new EtchedBorder()));
        
        comboPanel.add(sampleProgramsLabel);
        comboPanel.add(sampleProgramsComboBox);
        comboPanel.add(cheatSheetButton);
        rightPanel.add(comboPanel);
        
        frame.getContentPane().add(BorderLayout.EAST, rightPanel);
        frame.getContentPane().add(BorderLayout.WEST, Box.createRigidArea(new Dimension(100,0)));
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        //frame.setSize(1280, 720);
        frame.setSize(960, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Brainfreak Interpreter");
        
    }
    
    public void setCodeText(String text) {
    	codeArea.setText(text);
    }
    
    private void launchInterpreter() {
		//TODO: change the regex here to be customizable based on which check boxes are toggled (brainfreak++)
		final String bfCode = codeArea.getText().replaceAll("[^\\>\\<\\+\\-\\.\\,\\:\\;\\[\\]]", "").trim();
		final String stdIn =  inputArea.getText().trim();
		resultArea.setText(new Interpreter(bfCode, stdIn).getResult());
    }
    
}
package main.java.brainfreak;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class GUI extends JFrame {
    
	private static final long serialVersionUID = 1783527103493287966L;
	
	private JTextArea codeArea;
    private JTextArea inputArea;
    private JTextArea resultArea;
    private JLabel debugDisplayLabel;
    private JButton runButton;
    private JButton stopButton;
    private JButton resetButton;
    private JButton cheatSheetButton;
    private JCheckBox extendedModeCheckBox;
    private JCheckBox debugModeCheckBox;
    private JCheckBox memoryWrapCheckBox;
    private MemoryTextPanel memoryTextPanel;
    private ExampleList exampleList;
    
    public GUI() {
    	createAndShowGui();
    }
    
    private void createAndShowGui() {
  	//TODO: maybe make a string pool for localization, probably not necessary
  	//TODO: add ability to resize frame, enums for various preset lengths
    	
		JFrame frame = new JFrame();
		
		JPanel rightPanel = new JPanel();
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel debugPanel = new JPanel();
	    
		JLabel codeLabel = new JLabel("Enter your code below:");
		JLabel inputLabel = new JLabel("Enter standard input (if any):");
		JLabel resultLabel = new JLabel("Output:");
		JLabel sampleProgramsLabel = new JLabel("Sample programs:");
		JLabel extendedBfLabel = new JLabel("Add support for ';' and ':'");
		JLabel debugModeLabel = new JLabel("Enable debug mode\t\t\t\t\t");
		JLabel memoryWrapLabel = new JLabel("Enable memory wrap\t\t\t\t\t");
		JLabel memorySizeLabel = new JLabel("Enter number of memory cells:");
		debugDisplayLabel = new JLabel(" ");
		
		extendedModeCheckBox = new JCheckBox();
		debugModeCheckBox = new JCheckBox();
		memoryWrapCheckBox = new JCheckBox();
		
		runButton = new JButton("Run");
		stopButton = new JButton("Stop");
		resetButton = new JButton("Clear");
		
		codeArea = new JTextArea(19,50);
		inputArea = new JTextArea(2,50);
		resultArea = new JTextArea(4,50);
		
		resultArea.setEditable(false);
		codeArea.setAutoscrolls(true);
		codeArea.setLineWrap(true);
		inputArea.setLineWrap(true);
		resultArea.setLineWrap(true);

		JScrollPane codeAreaScrollPane = new JScrollPane(codeArea);
		JScrollPane inputAreaScrollPane = new JScrollPane(inputArea);
		JScrollPane resultAreaScrollPane = new JScrollPane(resultArea);
    	
    	centerPanel.add(codeLabel);
    	centerPanel.add(codeAreaScrollPane);
        centerPanel.add(inputLabel);
        centerPanel.add(inputAreaScrollPane);
        centerPanel.add(resultLabel);
        centerPanel.add(resultAreaScrollPane);
        centerPanel.add(debugDisplayLabel);

        cheatSheetButton = new JButton("ASCII Table");
        
        exampleList = new ExampleList(this);
        JComboBox<String> sampleProgramsComboBox = exampleList.createComboBox();
        
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.add(sampleProgramsLabel);
        rightButtonPanel.add(sampleProgramsComboBox);
        rightButtonPanel.add(runButton);
        rightButtonPanel.add(stopButton);
        rightButtonPanel.add(resetButton);
        rightButtonPanel.add(cheatSheetButton);
        
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));
        rightPanel.setPreferredSize(new Dimension(300,800));
        rightPanel.setBorder(BorderFactory.createEtchedBorder());
        rightPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20, 20, 20, 20), new EtchedBorder()));
        rightPanel.add(rightButtonPanel);
        
        debugPanel.setPreferredSize(new Dimension(100,100));
        debugPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        debugPanel.setBorder(BorderFactory.createEtchedBorder());
        debugPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(20, 20, 20, 20), new EtchedBorder()));
        
        debugPanel.add(extendedModeCheckBox);
        debugPanel.add(extendedBfLabel);
        debugPanel.add(debugModeCheckBox);
        debugPanel.add(debugModeLabel);
        debugPanel.add(memoryWrapCheckBox);
        debugPanel.add(memoryWrapLabel);
        debugPanel.add(memorySizeLabel);
        
        memoryTextPanel = new MemoryTextPanel();
        debugPanel.add(memoryTextPanel);
        
        rightPanel.add(debugPanel);
        
        frame.getContentPane().add(BorderLayout.EAST, rightPanel);
        frame.getContentPane().add(BorderLayout.WEST, Box.createRigidArea(new Dimension(50,0)));
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        //frame.setSize(1280, 720);
        frame.setSize(960, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Brainfreak Interpreter");
    }
    
    public boolean isInDebugMode() {
    	return debugModeCheckBox.isSelected();
    }
    
    public boolean hasExtendedSupport() {
    	return extendedModeCheckBox.isSelected();
    }
    
    public boolean hasMemoryWrap() {
    	return memoryWrapCheckBox.isSelected();
    }
    
    public void resetComboBox() {
    	exampleList.reset();
    }
    
    public void setInputAreaText(String text) {
    	inputArea.setText(text);
    }

    public String getInputAreaText() {
		return inputArea.getText();
	}
    
    public void setCodeText(String text) {
    	codeArea.setText(text);
    }
    
    public String getCodeText() {
    	return codeArea.getText();
    }
    
    public void setResultAreaText(String text) {
    	resultArea.setText(text);
    }
    
    public String getResultAreaText() {
    	return resultArea.getText();
    }
    
    public void setDebugDisplayLabel(String text) {
    	debugDisplayLabel.setText(text);
    }
    
    public void setMemoryFieldText(String text) {
    	memoryTextPanel.setMemoryFieldText(text);
    }
    
    public int getMemoryFieldValue() {
    	return Integer.parseInt(memoryTextPanel.getMemoryFieldText());
    }
    
    public void resetMemoryFieldText() {
    	memoryTextPanel.resetMemoryFieldText();
    }
    
    public void clickRunButton() {
    	runButton.doClick();
    }
    
    public void addRunButtonListener(ActionListener actionListener) {
    	runButton.addActionListener(actionListener);
    }
    
    public void addStopButtonListener(ActionListener actionListener) {
    	stopButton.addActionListener(actionListener);
    }
    
    public void addReturnKeyListener(KeyListener keyListener) {
    	codeArea.addKeyListener(keyListener);
    }
    
    public void addResetButtonListener(ActionListener actionListener) {
    	resetButton.addActionListener(actionListener);
    }
    
    public void addCheatSheetButtonActionListener(ActionListener actionListener) {
    	cheatSheetButton.addActionListener(actionListener);
    }

}
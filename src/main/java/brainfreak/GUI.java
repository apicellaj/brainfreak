package main.java.brainfreak;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class GUI extends JFrame {
    
	private static final long serialVersionUID = 1783527103493287966L;
	
	private JFrame frame;
	private JTextArea codeArea;
    private JTextArea inputArea;
    private JTextArea resultArea;
    private JScrollPane codeAreaScrollPane;
    private JScrollPane inputAreaScrollPane;
    private JScrollPane resultAreaScrollPane;
    private JLabel debugDisplayLabel;
    private JButton runButton;
    private JButton stopButton;
    private JButton resetButton;
    private JRadioButton smallSizeRadioButton;
    private JRadioButton largeSizeRadioButton;
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
    	
		frame = new JFrame();
		
		JPanel rightPanel = new JPanel();
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel optionsPanel = new JPanel();
	    
		JLabel codeLabel = new JLabel("Enter your code below:");
		JLabel inputLabel = new JLabel("Enter standard input (if any):");
		JLabel resultLabel = new JLabel("Output:");
		JLabel extendedBfLabel = new JLabel("Add support for ';' and ':'");
		JLabel debugModeLabel = new JLabel("Enable debug mode");
		JLabel memoryWrapLabel = new JLabel("Enable memory wrap");
		JLabel memorySizeLabel = new JLabel("memory cell(s)");
		debugDisplayLabel = new JLabel(" ");
		
		extendedModeCheckBox = new JCheckBox();
		debugModeCheckBox = new JCheckBox();
		memoryWrapCheckBox = new JCheckBox();
		
		runButton = new JButton("Run");
		stopButton = new JButton("Stop");
		resetButton = new JButton("Clear");
		
		ButtonGroup sizeButtonGroup = new ButtonGroup();
		smallSizeRadioButton = new JRadioButton("Small");
		largeSizeRadioButton = new JRadioButton("Large");
		sizeButtonGroup.add(smallSizeRadioButton);
		sizeButtonGroup.add(largeSizeRadioButton);
		smallSizeRadioButton.setSelected(true);

		JPanel sizeButtonPanel = new JPanel();
		sizeButtonPanel.setBorder(BorderFactory.
				createTitledBorder(BorderFactory.createEtchedBorder(), "Frame Size", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
		sizeButtonPanel.add(smallSizeRadioButton);
		sizeButtonPanel.add(largeSizeRadioButton);
		
		codeArea = new JTextArea();
		inputArea = new JTextArea();
		resultArea = new JTextArea();
		
		resultArea.setEditable(false);
		codeArea.setAutoscrolls(true);
		codeArea.setLineWrap(true);
		inputArea.setLineWrap(true);
		resultArea.setLineWrap(true);

		codeAreaScrollPane = new JScrollPane(codeArea);
		inputAreaScrollPane = new JScrollPane(inputArea);
		resultAreaScrollPane = new JScrollPane(resultArea);
    	
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
        JPanel sampleProgramsPanel = new JPanel();
        sampleProgramsPanel.setBorder(BorderFactory.
				createTitledBorder(BorderFactory.createEmptyBorder(), "Sample Programs", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        sampleProgramsPanel.add(sampleProgramsComboBox);
        
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.add(runButton);
        rightButtonPanel.add(stopButton);
        rightButtonPanel.add(resetButton);
        rightButtonPanel.add(cheatSheetButton);
        
        optionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        optionsPanel.setPreferredSize(new Dimension(300,30));
        optionsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Options", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION));
        
        JPanel extendedModePanel = new JPanel();
        extendedModePanel.add(extendedModeCheckBox);
        extendedModePanel.add(extendedBfLabel);
        
        JPanel debugModePanel = new JPanel();
        debugModePanel.add(debugModeCheckBox);
        debugModePanel.add(debugModeLabel);
        
        JPanel memoryWrapPanel = new JPanel();
        memoryWrapPanel.add(memoryWrapCheckBox);
        memoryWrapPanel.add(memoryWrapLabel);
        
        JPanel memorySizePanel = new JPanel();
        memoryTextPanel = new MemoryTextPanel();
        memorySizePanel.add(memoryTextPanel);
        memorySizePanel.add(memorySizeLabel);
        
        optionsPanel.add(extendedModePanel);
        optionsPanel.add(debugModePanel);
        optionsPanel.add(memoryWrapPanel);
        optionsPanel.add(memorySizePanel);

        GridBagLayout gbLayout = new GridBagLayout();
        rightPanel.setLayout(gbLayout);
        rightPanel.setPreferredSize(new Dimension(300,800));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.LINE_START;
        
        gbc.ipady = 15;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(sampleProgramsPanel, gbc);
        
        gbc.ipady = 30;;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        rightPanel.add(rightButtonPanel, gbc);
        
        gbc.ipady = 5;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        rightPanel.add(sizeButtonPanel, gbc);
        
        gbc.ipady = 125;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        rightPanel.add(optionsPanel, gbc);
        
        setSizeSmall();
        frame.getContentPane().add(BorderLayout.EAST, rightPanel);
        frame.getContentPane().add(BorderLayout.WEST, Box.createRigidArea(new Dimension(50,0)));
        frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setTitle("Brainfreak Interpreter");
    }
    
    public void setSizeSmall() {
    	frame.setPreferredSize(new Dimension(960,540));
    	codeAreaScrollPane.setPreferredSize(new Dimension(600,250));
    	inputAreaScrollPane.setPreferredSize(new Dimension(600,50));
		resultAreaScrollPane.setPreferredSize(new Dimension(600,100));
		frame.pack();
    }
    
    public void setSizeLarge() {
    	frame.setPreferredSize(new Dimension(1280,720));
    	codeAreaScrollPane.setPreferredSize(new Dimension(900,400));
    	inputAreaScrollPane.setPreferredSize(new Dimension(900,50));
		resultAreaScrollPane.setPreferredSize(new Dimension(900,140));
    	frame.pack();
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
    
    public void addSmallSizeRadioButtonListener(ActionListener actionListener) {
    	smallSizeRadioButton.addActionListener(actionListener);
    }
    
    public void addLargeSizeRadioButtonListener(ActionListener actionListener) {
    	largeSizeRadioButton.addActionListener(actionListener);
    }
    
    public void addCheatSheetButtonActionListener(ActionListener actionListener) {
    	cheatSheetButton.addActionListener(actionListener);
    }

}
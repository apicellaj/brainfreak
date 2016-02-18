package brainfreak;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;

public class ExampleList extends JComboBox<String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7396118846630445003L;
	
	final GUI gui;
	final JComboBox<String> comboBox;

	ExampleList(GUI gui) {
		String[] sampleProgramNames = {"None", "Hello World!", "Square Numbers", "Fibonacci"};
		comboBox = new JComboBox<>(sampleProgramNames);
		this.gui = gui;
	}
	
	public JComboBox<String> createComboBox() {
		comboBox.addItemListener(new Handler());
		comboBox.setPreferredSize(new Dimension(200,40));
		comboBox.setSize(new Dimension(200,30));
		return comboBox;
	}
	
	private class Handler implements ItemListener {
		
		private final String HELLO_WORLD = 
				">++++++++[-<+++++++++>]<.>>+>-[+]++>++>+++[>[->+++<<+++>]<<]>-----.>->\n" +
				"+++..+++.>-.<<+[>[+>+]>>]<--------------.>>.+++.------.--------.>+.>+.";
		
		private final String SQUARE_NUMBERS = 
				"++++[>+++++<-]>[<+++++>-]+<+[\n" +
				">[>+>+<<-]++>>[<<+>>-]>>>[-]++>[-]+\n" +
				">>>+[[-]++++++>>>]<<<[[<++++++++<++>>-]+<.<[>----<-]<]\n" +
				"<<[>>>>>[>>>[-]+++++++++<[>-<-]+++++++++>[-[<->-]+[<<<]]<[>+<-]>]<<-]<<-]";
		
		private final String FIBONACCI =
				"+++++++++++\n" + 
				">+>>>>++++++++++++++++++++++++++++++++++++++++++++\n" + 
				">++++++++++++++++++++++++++++++++<<<<<<[>[>>>>>>+>\n" + 
				"+<<<<<<<-]>>>>>>>[<<<<<<<+>>>>>>>-]<[>++++++++++[-\n" + 
				"<-[>>+>+<<<-]>>>[<<<+>>>-]+<[>[-]<[-]]>[<<[>>>+<<<\n" + 
				"-]>>[-]]<<]>>>[>>+>+<<<-]>>>[<<<+>>>-]+<[>[-]<[-]]\n" + 
				">[<<+>>[-]]<<<<<<<]>>>>>[+++++++++++++++++++++++++\n" + 
				"+++++++++++++++++++++++.[-]]++++++++++<[->-<]>++++\n" + 
				"++++++++++++++++++++++++++++++++++++++++++++.[-]<<\n" + 
				"<<<<<<<<<<[>>>+>+<<<<-]>>>>[<<<<+>>>>-]<-[>>.>.<<<\n" + 
				"[-]]<<[>>+>+<<<-]>>>[<<<+>>>-]<<[<+>-]>[<+>-]<<<-]";
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			
			if (e.getStateChange() == ItemEvent.SELECTED) {
				switch(e.getItem().toString()) {
				case "None" : deleteText();
					break;
				case "Hello World!" : helloWorld();
					 break;
				case "Square Numbers" : squareNumbers();
					break;
				case "Fibonacci" : fibonacci();
					break;
				}
			}
			
		}
		
		private void deleteText() {
			gui.setCodeText("");
		}
		
		private void helloWorld() {
			gui.setCodeText(HELLO_WORLD);
		}
		
		private void squareNumbers() {
			gui.setCodeText(SQUARE_NUMBERS);
		}
		
		private void fibonacci() {
			gui.setCodeText(FIBONACCI);
		}
		
	}

}

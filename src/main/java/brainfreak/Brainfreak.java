package main.java.brainfreak;

import javax.swing.SwingUtilities;

public class Brainfreak {
	
    public static void main(String args[]) {
    	
    	SwingUtilities.invokeLater(new Runnable() {
    	    public void run() {
    	    	new Controller(new GUI());
    	    }
		});

	}

}
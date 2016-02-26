package brainfreak;

import javax.swing.SwingUtilities;

public class Brainfreak {
	
    public static void main(String args[]) {
    	
    	SwingUtilities.invokeLater(new Runnable() {
    	    public void run() {
    	    	final GUI gui = new GUI();
    			final Controller controller = new Controller(gui);
    	    }
    	});
		
    }

}
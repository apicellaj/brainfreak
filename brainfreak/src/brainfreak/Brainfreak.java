package brainfreak;

public class Brainfreak {
	  
    public static void main(String args[]) {
		final GUI gui = new GUI();
		final Interpreter interpreter = new Interpreter();
		final Controller controller = new Controller(gui, interpreter);
    }

}
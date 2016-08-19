public class Main
{
	public static void main(String[] args) {
		Gui1 g1 = new Gui1();
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	g1.createAndShowGUI();
            }
        });
    }
}
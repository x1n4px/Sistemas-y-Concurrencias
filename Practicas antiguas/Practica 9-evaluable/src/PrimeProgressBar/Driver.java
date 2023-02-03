package PrimeProgressBar;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


@SuppressWarnings("serial")
public class Driver extends JFrame {


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		//To Complete
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JFrame frame = new JFrame();
				Panel panel = new Panel();
				
				Controlador controlador = new Controlador(panel);
				panel.setcon
			}
		});
		
		
	}
	
	public static void createFrame() {
		try {
			JFrame mainFrame = new JFrame(Panel.messages.getString("title"));
			//setBounds defines frame dimensios. Do not call pack method.
			mainFrame.setBounds(100, 100, 450, 300);
			//Complete
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
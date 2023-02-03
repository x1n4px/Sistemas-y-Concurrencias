package AmigosPublish;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Principal {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				JFrame frame = new JFrame();
            	Panel panel = new Panel();
            	
            	//Controlador es el Listener que va "escuchar" los eventos asociados a la interfaz
            	Controlador controlador = new Controlador(panel);
            	panel.setControlador(controlador);            	
            	
            	frame.setTitle("Números Amigos");
            	
            	frame.setContentPane(panel);
                frame.pack();
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
	}

}

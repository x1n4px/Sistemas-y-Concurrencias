package ejercicio1;

import javax.sound.sampled.Control;
import javax.swing.*;

public class Principal {
    public static void crearGUI(JFrame ventana) {
        Panel panel = new Panel();
        Controller ctr = new Controller(panel);
        panel.controlador(ctr);
        ventana.setContentPane(panel);
        ventana.setVisible(true);
        ventana.pack();
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame ventana = new JFrame("GUI Simple");
                crearGUI(ventana);
            }
        });
    }
}
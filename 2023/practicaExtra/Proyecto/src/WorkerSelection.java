import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class WorkerSelection implements Runnable {
    private MainFrame mainFrame;
    private Controller controller;
    private int[] numeros;
    private int[] numerosOrdenacion;

    public WorkerSelection(Controller controller, MainFrame mainFrame){
        this.mainFrame = mainFrame;
        this.controller = controller;

    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        int[] nuevaLista = Arrays.copyOf(controller.getNumeros(), controller.getNumeros().length);
        int N = nuevaLista.length;
        StringBuilder texto = new StringBuilder();

        for (int i = 0; i < N - 1; i++) {
            if (controller.isEsCancelado()) { // Verificar si se ha solicitado la cancelación
                break; // Salir del bucle si se ha cancelado
            }
            int menor = i;
            for (int j = i + 1; j < N; j++) {
                if (nuevaLista[j] < nuevaLista[menor]) {
                    menor = j;
                }
            }
            int aux = nuevaLista[i];
            nuevaLista[i] = nuevaLista[menor];
            nuevaLista[menor] = aux;

            int progreso = (int) (((double) (i + 1) / (N - 1)) * 100); // Calcular el porcentaje de progreso
            mainFrame.getProgressBarSelectionSort().setValue(progreso);


            texto.append(nuevaLista[i]).append(", ");
            mainFrame.getTextPane2().setText(texto.toString());
            mainFrame.getTextPane2().repaint();
        }

         long endTime = System.currentTimeMillis();
        float elapsedTime = endTime - startTime;
        String informacion = String.valueOf("List sorted in " + elapsedTime) + "ms";
        mainFrame.getSelectionSortTime().setText(informacion);


    }

}

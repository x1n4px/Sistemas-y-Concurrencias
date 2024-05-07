import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.Arrays;

public class WorkerBubble implements Runnable {
    private MainFrame mainFrame;
    private Controller controller;
    private int[] numeros;
    private int[] numerosOrdenacion;
    private int[] numerosOrdenacionB;

    public WorkerBubble(MainFrame mainFrame, Controller controller){
        this.mainFrame = mainFrame;
        this.controller = controller;

    }

    @Override
    public void run() {
        long startTime2 = System.currentTimeMillis();
        int[] nuevaLista = Arrays.copyOf(controller.getNumeros(), controller.getNumeros().length);
        StringBuilder texto = new StringBuilder();
        int valor = 0;
        int N = controller.getNumeros().length;

        for (int i = 0; i < N - 1; i++) {
            if (controller.isEsCancelado()) { // Verificar si se ha solicitado la cancelación
                break; // Salir del bucle si se ha cancelado
            }
            for (int j = N - 1; j > i; j--) { // Decrementar j en cada iteración
                if (nuevaLista[j] < nuevaLista[j - 1]) {
                    int aux = nuevaLista[j];
                    nuevaLista[j] = nuevaLista[j - 1];
                    nuevaLista[j - 1] = aux;
                    valor = aux;
                }

                int progreso = (int) (((double) (i + 1) / (N - 1)) * 100); // Calcular el porcentaje de progreso
                mainFrame.getProgressBarBubbleSortB().setValue(progreso);


            }

                texto.append(nuevaLista[i]).append(", ");
                mainFrame.getTextPane3().setText(texto.toString());
                mainFrame.getTextPane3().repaint();


        }
        long endTime2 = System.currentTimeMillis();
        float elapsedTime = endTime2 - startTime2;
        String informacion = String.valueOf("List sorted in " + elapsedTime) + "ms";
        mainFrame.getTiempoBurbuja().setText(informacion);


    }
/*
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == mainFrame.getSortButton()){
            mainFrame.getTiempoBurbuja().setText("sorting");
           mainFrame.getProgressBarBubbleSortB().setMaximum(100);
            mainFrame.getProgressBarBubbleSortB().setMinimum(0);
           mainFrame.getProgressBarBubbleSortB().setStringPainted(true);


            numerosOrdenacionB = ordenacionBurbuja(controller.getNumeros());


           // StringBuilder texto = new StringBuilder();
           /* for (int numero : numerosOrdenacionB) {
                texto.append(numero).append(", ");
            }*/
            //  mainFrame.getTextPane3().setText(texto.toString());
     /*   }
    }


    public int[] ordenacionBurbuja(int[] lista) {
        long startTime2 = System.currentTimeMillis();
        int[] nuevaLista = Arrays.copyOf(lista, lista.length);
        StringBuilder texto = new StringBuilder();
        int valor = 0;
        int N = lista.length;

        for (int i = 0; i < N - 1; i++) {
            for (int j = N - 1; j > i; j--) { // Decrementar j en cada iteración
                if (nuevaLista[j] < nuevaLista[j - 1]) {
                    int aux = nuevaLista[j];
                    nuevaLista[j] = nuevaLista[j - 1];
                    nuevaLista[j - 1] = aux;
                    valor = aux;
                }

                int progreso = (int) (((double) (i + 1) / (N - 1)) * 100); // Calcular el porcentaje de progreso
                mainFrame.getProgressBarBubbleSortB().setValue(progreso);



            }


             texto.append(nuevaLista[i]).append(", ");
            mainFrame.getTextPane3().setText(texto.toString());
            mainFrame.getTextPane3().repaint();
        }

        long endTime2 = System.currentTimeMillis();
        float elapsedTime = endTime2 - startTime2;
        String informacion = String.valueOf("List sorted in " + elapsedTime / 100) + "s";
        mainFrame.getTiempoBurbuja().setText(informacion);

        return nuevaLista;
    }
*/

}

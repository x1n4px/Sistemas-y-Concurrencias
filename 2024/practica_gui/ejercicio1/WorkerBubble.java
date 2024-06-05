package ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class WorkerBubble extends SwingWorker<List<Integer>, Void> {
    private List<Integer> l;

    private Panel panel;

    private long bubbleSortTime;

    public WorkerBubble(List<Integer> l, Panel panel) {

        this.l = l;

        this.panel = panel;

    }

    private List<Integer> ordenPorBurbuja(List<Integer> l) {

        int[] array = new int[l.size()];

        List<Integer> lista = new ArrayList<Integer>();

        int cont = 0;

        for (Integer num : l) {

            array[cont] = num;

            cont++;

        }

        long startTimeBubbleSort = System.currentTimeMillis();

        for (int i = 0; i < l.size() - 1; i++) {

            for (int j = l.size() - 1; j > i; j--) {

                if (array[j] < array[j - 1]) {

                    int aux = array[j];

                    array[j] = array[j - 1];

                    array[j - 1] = aux;

                }

            }

        }

        bubbleSortTime = System.currentTimeMillis() - startTimeBubbleSort;

        for (Integer num2 : array) {

            lista.add(num2);

        }

        return lista;

    }

    @Override

    protected List<Integer> doInBackground() throws Exception {

        return ordenPorBurbuja(l);

    }

    public void done() {

        try {

            panel.writeTextAreaBubble(this.get());

            panel.messageAreaBubble("List sorted in " + bubbleSortTime + " ms.");

        } catch (InterruptedException | ExecutionException e) {

            e.printStackTrace();

        }

    }
}

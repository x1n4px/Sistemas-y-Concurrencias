package ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

public class WorkerSelection extends SwingWorker<List<Integer>, Void> {
    private List<Integer> l;

    private Panel panel;

    private long selectionSortTime;

    public WorkerSelection(List<Integer> l, Panel panel) {

        this.l = l;

        this.panel = panel;

    }

    private List<Integer> ordenPorSeleccion(List<Integer> l) {

        int[] array = new int[l.size()];

        List<Integer> lista = new ArrayList<Integer>();

        int cont = 0;

        for (Integer num : l) {

            array[cont] = num;

            cont++;

        }

        long startTimeSelectionSort = System.currentTimeMillis();

        for (int i = 0; i < l.size() - 1; i++) {

            int menor = i;

            for (int j = i + 1; j < l.size(); j++) {

                if (array[j] < array[menor]) {

                    menor = j;

                }

            }

            int aux = array[i];

            array[i] = array[menor];

            array[menor] = aux;

        }

        selectionSortTime = System.currentTimeMillis() - startTimeSelectionSort;

        for (Integer num2 : array) {

            lista.add(num2);

        }

        return lista;

    }

    @Override

    protected List<Integer> doInBackground() throws Exception {

        return ordenPorSeleccion(l);

    }

    public void done() {

        try {

            panel.writeTextAreaSelection(this.get());

            panel.messageAreaSelection("List sorted in " + selectionSortTime + "ms.");

        } catch (InterruptedException | ExecutionException e) {

            e.printStackTrace();

        }

    }
}

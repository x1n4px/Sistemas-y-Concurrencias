package ejercicio1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller implements ActionListener {
    private List<Integer> numerosAleatorios(int n) {

        List<Integer> lista = new ArrayList<Integer>();

        Random r = new Random();

        for (int i = 0; i < n; i++) {

            lista.add(r.nextInt(60000));

        }

        return lista;

    }

    private WorkerSelection worker;

    private WorkerBubble worker2;

    private Panel panel;

    private List<Integer> lista;

    public Controller(Panel panel) {

        this.panel = panel;

    }

    @Override

    public void actionPerformed(ActionEvent e) {

        // TODO Auto-generated method stub

        if (e.getActionCommand().equals("Create")) {

            try {

                panel.clearArea();

                int size = panel.getTam();

                lista = numerosAleatorios(size);

                panel.writeTextArea(lista);

                panel.messageArea("List created");

                panel.clearAreaSelection();

                panel.clearAreaBubble();

                panel.enableSortButton();

            } catch (NumberFormatException exc) {

            }

        } else if (e.getActionCommand().equals("Sort")) {

            panel.messageAreaSelection("Sorting the list");

            worker = new WorkerSelection(lista, panel);

            worker.execute();

            panel.messageAreaBubble("Sorting the list");

            worker2 = new WorkerBubble(lista, panel);

            worker2.execute();

            panel.initIndexes();

        }

    }
}

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

    private TwinSort twinSort;
    private CousinSort cousinSort;
    private SixSort sixSort;

    private Panel panel;

    private List<Integer> lista;

    public Controller(Panel panel) {

        this.panel = panel;

    }

    @Override

    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("Cancel")){
            panel.mensaje("Cancelado");
            twinSort.cancel(true);
            cousinSort.cancel(true);
            sixSort.cancel(true);
        }else{
            panel.limpiaAreaTwin();
            panel.limpiaAreaCousin();
            panel.limpiaAreaSexy();
            panel.mensaje("List created");

            twinSort = new TwinSort(panel, panel.numero1());
            twinSort.execute();

            cousinSort = new CousinSort(panel, panel.numero2());
            cousinSort.execute();

            sixSort = new SixSort(panel, panel.numero3());
            sixSort.execute();
        }



    }
}

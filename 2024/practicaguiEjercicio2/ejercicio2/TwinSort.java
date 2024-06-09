package ejercicio1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TwinSort extends SwingWorker<List<Primos>, Void> {
    private List<Primos> l;
    private Panel panel;
    private long sixSortTime;

    private Integer numberOfIt;

    public TwinSort(Panel panel, Integer numberOfIt) {
        this.panel = panel;
        this.numberOfIt = numberOfIt;
        panel.mensajeTwin("Calculando primos twin...");
        panel.limpiaAreaTwin();
        panel.limpiaAreaCousin();
        panel.limpiaAreaSexy();
    }

    public static boolean esPrimo(int numero) {
        if (numero <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(numero); i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }

    private List<Primos> orderByTwinPack(List<Primos> l) {

        List<Primos> lit = new ArrayList<>();
        int count = 0;
        int numero = 2;

        long start = System.currentTimeMillis();
        while (count < numberOfIt) {
            if (esPrimo(numero) && esPrimo(numero + 2)) {
                lit.add(new Primos(numero, numero + 2));
                panel.escribePrimosTwin(new Primos(numero,numero+2), count);
                count++;
            }

            numero++;

        }
        long end = System.currentTimeMillis();
        sixSortTime = end - start;

        return lit;
    }

    @Override
    protected List<Primos> doInBackground() throws Exception {
        return orderByTwinPack(l);
    }

    public void done() {
        try {
            if(this.isCancelled()) {
                panel.mensajeTwin("Cancelado primos twin");
            }else{
                panel.mensajeTwin("List sort in " + sixSortTime + " milliseconds");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

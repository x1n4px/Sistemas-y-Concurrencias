package ejercicio1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SixSort extends SwingWorker<List<Primos>, Void> {

    private List<Primos> l;
    private Panel panel;
    private long sixSortTime;

    private int numberOfIt;

    public SixSort(Panel panel, Integer number) {

        this.panel = panel;
        this.numberOfIt = number;
        panel.mensajeTwin("Calculando primos sexy...");
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

    private List<Primos> orderBySixPack(List<Primos> l) {

        List<Primos> lit = new ArrayList<>();
        int count = 0;
        int numero = 2;

        long start = System.currentTimeMillis();
        while (count < numberOfIt) {
            if (esPrimo(numero) && esPrimo(numero + 6)) {
                lit.add(new Primos(numero, numero + 6));
                panel.escribePrimosSexy(new Primos(numero, numero + 6), count);
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
        return orderBySixPack(l);
    }

    public void done() {
        try {
            if(this.isCancelled()) {
                panel.mensajeSexy("Cancelado primos twin");
            }else{
                panel.mensajeSexy("List sort in " + sixSortTime + " milliseconds");

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

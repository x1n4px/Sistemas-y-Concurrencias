package ejercicio1;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CousinSort extends SwingWorker<List<Primos>, Void> {
    private List<Primos> l;
    private Panel panel;
    private long sixSortTime;
    private int numberOfIt;

    public CousinSort( Panel panel, Integer numberOfIt) {

        this.panel = panel;
        this.numberOfIt = numberOfIt;
        panel.mensajeTwin("Calculando primos cousin...");
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

    private List<Primos> orderByCousinPack(List<Primos> l) {
        List<Primos> lit = new ArrayList<>();
        int count = 0;
        int numero = 2;

        long start = System.currentTimeMillis();
        while (count < numberOfIt) {
            if (esPrimo(numero) && esPrimo(numero + 4)) {
                lit.add(new Primos(numero, numero + 4));
                panel.escribePrimosCousin(new Primos(numero,numero+4), count);
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
        return orderByCousinPack(l);
    }

    public void done() {
        try {
            if(!this.isCancelled()) {
                panel.mensajeCousin("Cancelado primos twin");
            }else{
                panel.mensajeCousin("List sort in " + sixSortTime + " milliseconds");

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

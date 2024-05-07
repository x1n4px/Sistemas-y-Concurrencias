package viajeTren;

import java.util.concurrent.Semaphore;

public class Tren {
    private final int N = 10; // Número de asientos por vagón
    private int[] pasajeros;
    private Semaphore mutex;
    private Semaphore puedenSubir;
    private Semaphore[] puedenBajar;
    private Semaphore empezarViaje;

    public Tren() {
        pasajeros = new int[2];
        pasajeros[0] = 0;
        pasajeros[1] = 0;
        mutex = new Semaphore(1);
        puedenSubir = new Semaphore(1);
        puedenBajar = new Semaphore[2];
        puedenBajar[0] = new Semaphore(0);
        puedenBajar[1] = new Semaphore(0);
        empezarViaje = new Semaphore(0);
    }

    public void viaje(int id) throws InterruptedException {
        puedenSubir.acquire();
        mutex.acquire();

        if (pasajeros[0] < N) {
            System.out.println("pasajero " + id + " ha subido al vagón 1");
            pasajeros[0]++;

            mutex.release();
            if (pasajeros[1] < N) puedenSubir.release();

            puedenBajar[0].acquire();
            mutex.acquire();
            System.out.println("pasajero " + id + " ha bajado del vagón 1");
            pasajeros[0]--;

            if (pasajeros[0] == 0) puedenBajar[1].release();
            else puedenBajar[0].release();
        } else {
            System.out.println("pasajero " + id + " ha subido al vagón 2");
            pasajeros[1]++;

            mutex.release();
            if (pasajeros[1] < N) puedenSubir.release();
            else empezarViaje.release();

            puedenBajar[1].acquire();
            mutex.acquire();
            System.out.println("pasajero " + id + " ha bajado del vagón 2");
            pasajeros[1]--;

            if (pasajeros[1] == 0) {
                System.out.println("**********************************");
                puedenSubir.release();
            } else puedenBajar[1].release();
        }

        mutex.release();
    }

    public void empiezaViaje() throws InterruptedException {
        empezarViaje.acquire();
        System.out.println("        Maquinista:  empieza el viaje");
    }

    public void finViaje() throws InterruptedException {
        System.out.println("        Maquinista:  fin del viaje");
        puedenBajar[0].release();
    }
}

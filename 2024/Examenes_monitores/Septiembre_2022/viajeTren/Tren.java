package viajeTren; // Declaración del paquete al que pertenece la clase

import java.util.concurrent.Semaphore; // Importación de la clase Semaphore del paquete java.util.concurrent

public class Tren { // Declaración de la clase Tren
	private final int TAM_VAGON = 5;
    private int asientosVagon1Restantes = TAM_VAGON;
    private int asientosVagon2Restantes = TAM_VAGON;

	private boolean esperaVagon1 = true;
    private boolean esperaVagon2 = true;

    private boolean enViaje = false;
    private boolean bajando = false;
    private boolean montandose = true;

	public synchronized void viaje(int id) throws InterruptedException { // Declaración del método viaje que recibe como
		while (enViaje) {
            wait(); // Se bloquea a los pasajeros mientras el tren está en viaje
        }

        if (asientosVagon1Restantes > 0) {
            asientosVagon1Restantes--;
            System.out.println("Pasajero " + id + " ha subido al vagon 1");
            while (esperaVagon1) {
                wait(); // Se bloquea el vagon 1 mientras está esperando
            }

            asientosVagon1Restantes++;
            System.out.println("Pasajero " + id + " ha bajado del vagon 1");

            if (asientosVagon1Restantes == TAM_VAGON) {
                esperaVagon2 = false;
                notifyAll(); // Se avisa al vagon 2 para que empiecen a entrar
            }
        } else { // Vagon 2
            asientosVagon2Restantes--;
            System.out.println("Pasajero " + id + " ha subido al vagon 2");
            if (asientosVagon2Restantes == 0) {
                montandose = false;
                enViaje = true;
                notifyAll();
            }

            while (esperaVagon2) {
                wait(); // Se bloquea el vagon 2 mientras está esperando
            }

            asientosVagon2Restantes++;
            System.out.println("Pasajero " + id + " ha bajado del vagon 2");

            if (asientosVagon2Restantes == TAM_VAGON) {
                enViaje = false;
                montandose = true;
                bajando = false;
                esperaVagon1 = true;
                esperaVagon2 = true;
                notifyAll();
                System.out.println("*++++++++++++++++++++++*");
            }
        }
    }

	 // Método para que el maquinista empiece el viaje
    public synchronized void empiezaViaje() throws InterruptedException {
        while (montandose || bajando) {
            wait();
        }
        System.out.println("Maquinista: Empieza el viaje");
    }

    // Método para que el maquinista finalice el viaje
    public synchronized void finViaje() throws InterruptedException {
        System.out.println("Maquinista: Fin del viaje");
        bajando = true;
        esperaVagon1 = false;
        notifyAll(); // Se avisa a todos los pasajeros del vagon 1 para que empiecen a bajar
    }
}

package viajeTren;

import java.util.concurrent.Semaphore;

public class Tren {

	final int TAM_VAGON = 5;
	int numPasPrimerVagon = 0;
	int numPasSegundoVagon = 0;
	Semaphore puedeSubirPrimerVagon = new Semaphore(1);
	Semaphore puedeSubirSegundoVagon = new Semaphore(0);
	Semaphore mutex = new Semaphore(1);
	Semaphore puedeComenzarViaje = new Semaphore(0);
	Semaphore puedeBajar = new Semaphore(0);

	public void viaje(int id) throws InterruptedException {

		
		mutex.acquire();
		if (numPasPrimerVagon < TAM_VAGON) {
			puedeSubirPrimerVagon.acquire();
			numPasPrimerVagon++;
			System.out.println("        Pasajero " + id + " sube al primer vagon. Hay " + numPasPrimerVagon
					+ " pasajeros en el primer vagon");
			if (numPasPrimerVagon == TAM_VAGON)
				puedeSubirSegundoVagon.release();
			puedeSubirPrimerVagon.release();
		} else {
			puedeSubirSegundoVagon.acquire();
			numPasSegundoVagon++;
			System.out.println("        Pasajero " + id + " sube al segundo vagon. Hay " + numPasSegundoVagon
					+ " pasajeros en el segundo vagon");
			if (numPasSegundoVagon == TAM_VAGON)
				puedeComenzarViaje.release();
			puedeSubirSegundoVagon.release();
		}
		mutex.release();
	}

	public void empiezaViaje() throws InterruptedException {
		puedeComenzarViaje.acquire();
		System.out.println("        Maquinista:  empieza el viaje");
	}

	public void finViaje() throws InterruptedException {
		System.out.println("        Maquinista:  fin del viaje");
        // Espera a que todos los pasajeros bajen en orden
        for (int i = 0; i < TAM_VAGON * 2; i++) {
            puedeBajar.acquire();
        }
        // Reinicia el estado del tren para el próximo viaje
        numPasPrimerVagon = 0;
        numPasSegundoVagon = 0;
        // Reinicia los semáforos para el próximo viaje
        puedeComenzarViaje = new Semaphore(0);
        puedeSubirPrimerVagon = new Semaphore(1);
        puedeSubirSegundoVagon = new Semaphore(0);
	}

	public boolean estaLleno() {
		return numPasPrimerVagon == TAM_VAGON && numPasSegundoVagon == TAM_VAGON;

	}
}

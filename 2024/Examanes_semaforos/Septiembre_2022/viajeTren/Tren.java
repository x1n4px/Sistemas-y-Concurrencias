package viajeTren;

import java.util.concurrent.Semaphore;

public class Tren {

	int nPasajeros = 5;
	int nPrimerVagon = 0;
	int nSegundoVagon = 0;

	Semaphore mutex = new Semaphore(1);
	Semaphore empiezaViaje = new Semaphore(0);
	Semaphore puedenSubir = new Semaphore(0);
	Semaphore esperaVagon1 = new Semaphore(0);
	Semaphore esperaVagon2 = new Semaphore(0);

	public void viaje(int id) throws InterruptedException {
		mutex.acquire();
		if (nSegundoVagon < nPasajeros) {
			puedenSubir.release();
		}
		mutex.release();
		puedenSubir.acquire();
		mutex.acquire();
		if (nPrimerVagon < nPasajeros) {
			nPrimerVagon++;
			System.out.println("	Pasajero " + id + " sube al primer vagon");
			mutex.release();
			esperaVagon1.acquire();
			mutex.acquire();
			System.out.println("	Pasajero " + id + " baja del primer vagon");
			nPrimerVagon--;
		} else {
			nSegundoVagon++;
			System.out.println("	Pasajero " + id + " sube al segundo vagon");
			if(nSegundoVagon == nPasajeros) {
				empiezaViaje.release();
			}
			mutex.release();
			esperaVagon2.acquire();
			mutex.acquire();
			System.out.println("	Pasajero " + id + " baja del segundo vagon");
			nSegundoVagon--;
		}

		if(nPrimerVagon > 0) {
			esperaVagon1.release();
		}
		if(nPrimerVagon == 0 && nSegundoVagon > 0) {
			esperaVagon2.release();
		}
		if(nSegundoVagon == 0) {
			System.out.println("***********************************");
			puedenSubir.release();
		}
		mutex.release();
	}

	void empiezaViaje() throws InterruptedException {
		empiezaViaje.acquire();
		System.out.println("	Maquinista: empieza el viaje");
	}

	public void finViaje() throws InterruptedException {
		System.out.println("	Maquinista: fin del viaje");
		esperaVagon1.release();
	}

}

package sensores;

import java.util.concurrent.Semaphore;

public class Mediciones {
	int numSensores;

	Semaphore mutex;
	Semaphore esperoPorSensores;
	Semaphore esperoPorTrabajador;

	public Mediciones() {
		numSensores = 0;
		mutex = new Semaphore(1);
		esperoPorSensores = new Semaphore(0);
		esperoPorTrabajador = new Semaphore(0);
	}

	public void nuevaMedicion(int id) throws InterruptedException {
		// el sensor id deja una nueva medición
		mutex.acquire();
		numSensores++;

		System.out.println("Sensor " + id + " deja sus mediciones. Num sensores: " + numSensores);

		if (numSensores == 3) {
			esperoPorSensores.release();
		}

		mutex.release();

		esperoPorTrabajador.acquire();

		mutex.acquire();

		numSensores--;

		if (numSensores != 0) {
			esperoPorTrabajador.release();
		}

		mutex.release();
	}

	public void recogerMediciones() throws InterruptedException {
		// el worker coge las tres mediciones
		esperoPorSensores.acquire();
		System.out.println("El worker recoge las mediciones");
	}

	public void finTrabajo() throws InterruptedException {
		// lo llama el worker cuando ha procesado las mediciones
		System.out.println("Worker ha procesado las mediciones");
		esperoPorTrabajador.release();
	}

	// CS-Worker: No puede recoger las mediciones hasta que no están las tres
	// CS-Sensor-i: No puede medir hasta que el worker no ha procesado
	// las medidas de la interación anterior

}

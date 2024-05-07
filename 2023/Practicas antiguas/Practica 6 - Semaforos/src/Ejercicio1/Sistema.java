package Ejercicio1;

import java.util.concurrent.Semaphore;

public class Sistema {
	private int medidas[];
	private int nSen;
	// sincronizacion y mutex
	private Semaphore puedeProcesar;
	private Semaphore puedePonerMedida[];
	private Semaphore mutex;

	public Sistema() {
		medidas = new int[3];
		mutex = new Semaphore(1, true);
		puedeProcesar = new Semaphore(0, true);
		puedePonerMedida = new Semaphore[3];
		nSen = 0;
		for (int i = 0; i < 3; i++)
			puedePonerMedida[i] = new Semaphore(1, true);
	}

	public static void main(String[] args) {
		Sistema s = new Sistema();
		Trabajador t = new Trabajador(s);
		Sensor sensors[] = new Sensor[3];
		for (int i = 0; i < 3; i++)
			sensors[i] = new Sensor(i, s);

		t.start();
		for (int i = 0; i < 3; i++)
			sensors[i].start();

		// opcional - interrumpir al resto de hebras
		try {
			Thread.sleep(2000);
			t.interrupt();
			for (int i = 0; i < 3; i++)
				sensors[i].interrupt();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void procesar() throws InterruptedException {
		// pre
		puedeProcesar.acquire();
		mutex.acquire(); // no es necesario
		// sc
		System.out.println("medida 0: " + medidas[0] + " medida 1: " + medidas[1] + " medida 2: " + medidas[2]);

		// post
		for (int i = 0; i < 3; i++)
			puedePonerMedida[i].release();
		mutex.release();
	}

	public void ponerMedida(int id, int dato) throws InterruptedException {
		// pre
		puedePonerMedida[id].acquire();
		mutex.acquire();
		// SC
		medidas[id] = dato;
		nSen++;
		// post
		if (nSen == 3) {
			nSen = 0;
			puedeProcesar.release();
		} else{
			System.out.println("Aun faltan sensores por enviar sus datos");
		}
		mutex.release();
	}

}

package pr6.sensores;

import java.util.concurrent.Semaphore;

public class Mediciones {
	// CS-Worker: No puede recoger las mediciones hasta que no están las tres
	// CS-Sensor-i: No puede medir hasta que el worker no ha procesado
	// las medidas de la interación anterior
	private int numMediciones = 0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore esperaWorker = new Semaphore(0, true); // empieza bloqueado hasta que haya 3 mediciones
	private Semaphore esperaSensor = new Semaphore(1, true);

	public void nuevaMedicion(int id) throws InterruptedException {
		// el sensor id deja una nueva medición
		mutex.acquire();
		System.out.println("Sensor " + id + " deja sus mediciones");
		numMediciones++;
		if (numMediciones == 3)
			esperaWorker.release(); // desbloqueamos worker
		mutex.release();
		
		esperaSensor.acquire();// Si no va fuera del mutex en cuanto entra la primera hebra se 
							   //queda pillado porque nunca llega a desbloquear la variable compartida
			
		mutex.acquire();
		numMediciones--;
		if (numMediciones > 0)
			esperaSensor.release();
		mutex.release();

	}

	public void recogerMediciones() throws InterruptedException {
		// el worker coge las tres mediciones
		esperaWorker.acquire();
		System.out.println("Worker ha recogido las mediciones");
	}

	public void finTrabajo() throws InterruptedException {
		// lo llama el worker cuando ha procesado las mediciones
		esperaSensor.release();
		System.out.println("Worker ha procesado las mediciones");

	}
	//Primero la excl mutua 
	//Despues la/s condiciones de sincronizacion
	//ultimo como vamos a despertar a las hebras(del tiron o cascada(que la primera vaya despertando a las demas))

}

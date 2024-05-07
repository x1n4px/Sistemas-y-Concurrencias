package viajeTren;


import java.util.concurrent.Semaphore;

public class Tren {
	private Semaphore pasajerosSemaphore;
	private Semaphore maquinistaSemaphore;
	private Semaphore mutex;
	private int pasajerosEnVagon;

	public Tren(){
		pasajerosSemaphore = new Semaphore(0);
		maquinistaSemaphore = new Semaphore(0);
		mutex = new Semaphore(1);
		pasajerosEnVagon = 0;
	}



	public void viaje(int id) throws InterruptedException {
		mutex.acquire(); // Bloquea el acceso a las variables compartidas

		if (maquinistaSemaphore.availablePermits() == 0) { // Si el tren no está en marcha
			if (pasajerosEnVagon < 10) { // Si hay espacio en el primer vagón
				pasajerosEnVagon++;
				System.out.println("Pasajero " + id + " ha subido al vagón.");
			}
			if (pasajerosEnVagon== 2 * 5) { // Si el tren está completo
				maquinistaSemaphore.release(); // Avisa al maquinista para que empiece el viaje
			}
		}

		mutex.release(); // Libera el acceso a las variables compartidas

		maquinistaSemaphore.acquire(); // Espera a que el maquinista empiece el viaje

		System.out.println("Pasajero " + id + " está haciendo el viaje.");
		Thread.sleep(2000); // Simulación de tiempo de viaje

		mutex.acquire(); // Bloquea el acceso a las variables compartidas

		if (id < 10) {
			pasajerosEnVagon--;
			System.out.println("Pasajero " + id + " ha bajado del vagón.");
		}

		if (pasajerosEnVagon == 0 ) { // Si todos los pasajeros se han bajado
			System.out.println("Maquinista: Fin del viaje");
			maquinistaSemaphore.release(); // Avisa al maquinista de que el viaje ha terminado
		}

		mutex.release(); // Libera el acceso a las variables compartidas


	}

	void empiezaViaje() throws InterruptedException {
		System.out.println("Maquinista: Esperando a que el tren esté lleno...");
		maquinistaSemaphore.acquire(); // Espera a que el tren esté lleno
		System.out.println("Maquinista: Comienza el viaje");
		Thread.sleep(1000); // Simulación de tiempo de inicio del viaje

	}


	public void finViaje() throws InterruptedException {
		System.out.println("Maquinista: Fin del viaje");
		mutex.acquire(); // Bloquea el acceso a las variables compartidas

		if (pasajerosEnVagon == 0) { // Si no quedan pasajeros en el tren
			maquinistaSemaphore.release(); // Avisa al maquinista de que el viaje ha terminado
		}

		mutex.release(); // Libera el acceso a las variables compartidas
	}

}

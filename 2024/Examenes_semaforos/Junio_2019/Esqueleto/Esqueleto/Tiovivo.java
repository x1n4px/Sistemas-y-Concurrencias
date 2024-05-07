import java.util.concurrent.Semaphore;

public class Tiovivo {
	private int nPasajeros = 0, N;
	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore finViaje = new Semaphore(1, true);
	private Semaphore lleno = new Semaphore(0, true);
	private Semaphore puedoBajar = new Semaphore(0, true);

	public Tiovivo(int cantidad) {
		this.N = cantidad;
	}

	public void subir(int id) throws InterruptedException {
		finViaje.acquire(); // pasajero espera al fin del viaje
		mutex.acquire(); // Exclusion mutua sobre nPasajeros
		nPasajeros++;
		System.out.println("El pasajero " + id + " se sube al tiovivo. Hay " + nPasajeros + " pasajeros.");
		if (nPasajeros < N) {
			finViaje.release(); // si no esta lleno, se libera el semaforo para que otro pasajero pueda subir
		} else {
			lleno.release(); // si esta lleno, se libera el semaforo para que el tiovivo pueda empezar a girar
		}
		mutex.release();
	}

	public void bajar(int id) throws InterruptedException {
		puedoBajar.acquire(); // pasajero espera a que el tiovivo se detenga
		mutex.acquire(); // Exclusion mutua sobre nPasajeros
		nPasajeros--;
		System.out.println("El pasajero " + id + " se baja del tiovivo. Hay " + nPasajeros + " pasajeros.");
		if(nPasajeros > 0) {
			puedoBajar.release(); // si no es el ultimo pasajero, se libera el semaforo para que otro pueda bajar
		} else {
			finViaje.release(); // si es el ultimo pasajero, se libera el semaforo para que el tiovivo pueda empezar a girar
		}
		mutex.release();
	}

	public void esperaLleno() throws InterruptedException {
		lleno.acquire(); // tiovivo espera a que se llene
		System.out.println("El tiovivo esta lleno. Comienza el viaje.");
	}

	public void finViaje() throws InterruptedException {
		System.out.println("El viaje ha terminado. Los pasajeros pueden bajar.");
		puedoBajar.release(); // tiovivo se detiene y los pasajeros pueden bajar
	}
}

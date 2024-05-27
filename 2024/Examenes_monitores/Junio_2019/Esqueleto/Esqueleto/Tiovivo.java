import java.util.concurrent.Semaphore;

public class Tiovivo {
	private int max, pasajeros = 0;
	boolean bajar = false, espera = false, viaje = false, viajeFinaliza = false;

	public Tiovivo(int cantidad) {
		this.max = cantidad;
	}

	public void subir(int id) throws InterruptedException {
		while (espera) {
			wait();
		}
		System.out.println("Pasajero " + id + " sube al tiovivo.");
		if (pasajeros == max) {
			espera = true;
			viaje = true;
			notifyAll();
		}
	}

	public void bajar(int id) throws InterruptedException {
		while(!bajar) {
			wait();
		
		}
		pasajeros--;
		System.out.println("Pasajero " + id + " baja del tiovivo.");
		if(pasajeros == 0) {
			espera = false;
			bajar = false;
			notifyAll();
		}
	}

	public void esperaLleno() throws InterruptedException {
		while (!viaje) {
			wait();
		}
		System.out.println("El tiovivo esta lleno. Comienza el viaje.");
		viajeFinaliza = true;
		viaje = false;
		notifyAll();
	}

	public void finViaje() throws InterruptedException {
		while(!viajeFinaliza) {
			wait();
		}
		System.out.println("El viaje ha terminado. Los pasajeros pueden bajar.");
		bajar = true;
		viajeFinaliza = false;
		notifyAll();
	}
}

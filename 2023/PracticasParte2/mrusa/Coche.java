package mrusa;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.*;

public class Coche implements Runnable {
	private int tam;
	private int contadorPasajeros;
	boolean vueltaMontana;

	public Coche(int tam) {
		this.tam = tam;
		this.contadorPasajeros = 0;
		vueltaMontana = false;
	}

	public synchronized void subir(int id) throws InterruptedException {
		while(contadorPasajeros > tam || !vueltaMontana){
			wait();
		}
		contadorPasajeros++;
		System.out.println("El pasajero " + id + " ha entrado en el coche."
				+ "Clientes en el coche: " + contadorPasajeros);
		notifyAll();
	}

	public synchronized void bajar(int id) throws InterruptedException {
		while(contadorPasajeros <= 0 || !vueltaMontana){
			wait();
		}
		contadorPasajeros--;
		System.out.println("El pasajero " + id + " se ha bajado de el coche."
				+ "Clientes en el coche: " + contadorPasajeros);
		notifyAll();

	}

	private synchronized void esperaLleno() throws InterruptedException {
		while(contadorPasajeros > tam){
			System.out.println("El coche tiene " + tam +  " plazas, estan llenas: "+contadorPasajeros);
			wait();
		}
		System.out.println("Coche lleno, inicia la vuelta");
		vueltaMontana = true;
		contadorPasajeros = 0;
		System.out.println("Vuelta completada, pasajeros se han bajado");
	}

	public void run() {
		while (true)
			try {
				this.esperaLleno();
				Thread.sleep(200);

			} catch (InterruptedException ie) {
			}

	}
}
// tam pasajeros se suben al coche->el coche da un viaje
// ->tam pasajeros se bajan del coche->......

// CS-Coche: Coche no se pone en marcha hasta que no está lleno
// CS-Pas1: Pasajero no se sube al coche hasta que no hay sitio para el.
// CS-Pas2: Pasajero no se baja del coche hasta que no ha terminado el viaje

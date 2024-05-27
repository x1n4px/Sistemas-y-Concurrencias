import java.util.concurrent.Semaphore;

public class Convoy {

	int nFurgonetas;
	private int idLider = -1, cnt = 0;
	private boolean todasSeguidor = false, liderLlega = false, todasAbandonan = false;

	public Convoy(int tam) {
		nFurgonetas = tam;

	}

	/**
	 * Las furgonetas se unen al convoy
	 * La primera es la lider, el resto son seguidoras
	 **/
	public synchronized int unir(int id) throws InterruptedException {
		cnt++;
		if (cnt == 1) {
			idLider = id;
			System.out.println("Furgoneta " + id + " lider: unida al convoy");
		} else {
			System.out.println("Furgoneta " + id + " seguidora: unida al convoy");
		}
		if (nFurgonetas == cnt) {
			todasSeguidor = true;
			notifyAll();
		}
		if (id == idLider) {
			while (!todasSeguidor) {
				wait();
			}
		} else {
			while (!liderLlega) {
				wait();
			}
		}
		return idLider;
	}

	/**
	 * La furgoneta lider espera a que todas las furgonetas se unan al convoy
	 * Cuando esto ocurre calcula la ruta y se pone en marcha
	 */
	public synchronized void calcularRuta(int id) throws InterruptedException {
		todasSeguidor = false;
		System.out.println("** Furgoneta " + id + " lider:  ruta calculada, nos ponemos en marcha **");
		System.out.println("Furgoneta " + id + " lider:  llegamos al destino");
		liderLlega = true;
		notifyAll();
	}

	/**
	 * La furgoneta lider avisa al las furgonetas seguidoras que han llegado al
	 * destino y deben abandonar el convoy
	 * La furgoneta lider espera a que todas las furgonetas abandonen el convoy
	 **/
	public synchronized void destino(int id) throws InterruptedException {
		while(!todasAbandonan) {
			wait();
		}
		System.out.println("Furgoneta " + id + " lider:  hemos llegado al destino, abandonamos el convoy");
		todasAbandonan = false;
	}

	/**
	 * Las furgonetas seguidoras hasta que la lider avisa de que han llegado al
	 * destino
	 * y abandonan el convoy
	 **/
	public synchronized void seguirLider(int id) throws InterruptedException {
		cnt--;
		System.out.println("Furgoneta " + id + " seguidora:  abandonamos el convoy");
		if(cnt == 1) {
			todasAbandonan = true;
			liderLlega = false;
			notifyAll();
		}
	}

	/**
	 * Programa principal. No modificar
	 **/
	public static void main(String[] args) {
		final int NUM_FURGO = 10;
		Convoy c = new Convoy(NUM_FURGO);
		Furgoneta flota[] = new Furgoneta[NUM_FURGO];

		for (int i = 0; i < NUM_FURGO; i++)
			flota[i] = new Furgoneta(i, c);

		for (int i = 0; i < NUM_FURGO; i++)
			flota[i].start();
	}

}

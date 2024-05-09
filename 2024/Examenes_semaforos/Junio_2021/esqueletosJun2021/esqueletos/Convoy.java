import java.util.concurrent.Semaphore;

public class Convoy {

	int nFurgonetas;
	private volatile int contFurgonetas;
	int lider;
	Semaphore mutex;
	Semaphore esperaLider;
	Semaphore inicioRuta;
	Semaphore entranFurgonetas;
	Semaphore puedoSalir;

	public Convoy(int tam) {
		nFurgonetas = tam;
		contFurgonetas = 0;
		lider = 0;
		mutex = new Semaphore(1, true);
		esperaLider = new Semaphore(0, true);
		inicioRuta = new Semaphore(0, true);
		entranFurgonetas = new Semaphore(1, true);
		puedoSalir = new Semaphore(0, true);
	}

	/**
	 * Las furgonetas se unen al convoy
	 * La primera es la lider, el resto son seguidoras
	 **/
	public int unir(int id) throws InterruptedException {
		entranFurgonetas.acquire();
		mutex.acquire();
		contFurgonetas++;
		if (contFurgonetas == 1) {
			System.out.println("Furgoneta " + id + " lider");
			lider = id;
			entranFurgonetas.release();
		} else {
			System.out.println("Furgoneta " + id + " seguidora");
			if (contFurgonetas == nFurgonetas) {
				inicioRuta.release();
			} else {
				entranFurgonetas.release();
			}
		}
		mutex.release();
		return lider;
	}

	/**
	 * La furgoneta lider espera a que todas las furgonetas se unan al convoy
	 * Cuando esto ocurre calcula la ruta y se pone en marcha
	 */
	public void calcularRuta(int id) throws InterruptedException {
		inicioRuta.acquire();
		System.out.println("** Furgoneta " + id + " lider:  ruta calculada, nos ponemos en marcha **");
	}

	/**
	 * La furgoneta lider avisa al las furgonetas seguidoras que han llegado al
	 * destino y deben abandonar el convoy
	 * La furgoneta lider espera a que todas las furgonetas abandonen el convoy
	 **/
	public void destino(int id) throws InterruptedException {
		System.out.println("** Furgoneta " + id + " lider: hemos llegado al destino **");
		puedoSalir.release();
		esperaLider.acquire();
		System.out.println("** Furgoneta " + id + " lider abandona el convoy **");
	}

	/**
	 * Las furgonetas seguidoras hasta que la lider avisa de que han llegado al
	 * destino
	 * y abandonan el convoy
	 **/
	public void seguirLider(int id) throws InterruptedException {
		puedoSalir.acquire();
		mutex.acquire();
		contFurgonetas--;

		if (contFurgonetas == 1) {
			esperaLider.release();
		} else {
			puedoSalir.release();
		}
		System.out.println("Furgoneta " + id + " abandona el convoy");
		mutex.release();
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

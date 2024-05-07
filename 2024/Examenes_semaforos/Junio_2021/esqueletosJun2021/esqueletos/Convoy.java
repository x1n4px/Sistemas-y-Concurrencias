public class Convoy {

	private int numFurgonetaMax;
	private int currentFurgoneta;
	private boolean hayLider = false;
	private int idLider = -1;
	private boolean destinoArrived = false;
	private int numOutFurg;

	public Convoy(int tam) {
		numFurgonetaMax = tam;
		currentFurgoneta = 0;
		numOutFurg = 0;
	
	}

	/**
	 * Las furgonetas se unen al convoy
	 * La primera es la lider, el resto son seguidoras
	 **/
	public synchronized int unir(int id)  throws InterruptedException{
		// TODO: Poner los mensajes donde corresponda
		if(!hayLider) {
			currentFurgoneta++;
			idLider = id;
			hayLider = true;
			notifyAll();
			System.out.println("** Furgoneta " + id + " lidera del convoy **");

		}else{
			while(!hayLider){
				wait();
			}
			currentFurgoneta++;
			notifyAll();
			System.out.println("Furgoneta " + id + " seguidora");
		}

		
		return idLider;
	}

	/**
	 * La furgoneta lider espera a que todas las furgonetas se unan al convoy
	 * Cuando esto ocurre calcula la ruta y se pone en marcha
	 */
	public synchronized void calcularRuta(int id)  throws InterruptedException{
		while(currentFurgoneta != numFurgonetaMax) {
			wait();
		}
		System.out.println("** Furgoneta " + id + " lider:  ruta calculada, nos ponemos en marcha **");
	}

	/**
	 * La furgoneta lider avisa al las furgonetas seguidoras que han llegado al
	 * destino y deben abandonar el convoy
	 * La furgoneta lider espera a que todas las furgonetas abandonen el convoy
	 **/
	public synchronized void destino(int id)  throws InterruptedException{
		destinoArrived = true;
		notifyAll();
		while(numOutFurg != numFurgonetaMax -1) {
			wait();
		}

		System.out.println("** Furgoneta " + id + " lider abandona el convoy **");
	}

	/**
	 * Las furgonetas seguidoras hasta que la lider avisa de que han llegado al
	 * destino
	 * y abandonan el convoy
	 **/
	public synchronized void seguirLider(int id) throws InterruptedException {
		while(!destinoArrived) {
			wait();
		}
		numOutFurg++;
		notify();
		System.out.println("Furgoneta " + id + " abandona el convoy");
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

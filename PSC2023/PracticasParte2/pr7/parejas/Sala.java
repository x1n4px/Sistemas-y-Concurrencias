package pr7.parejas;

public class Sala {

	private boolean hayHombre = false;
	private boolean hayMujer = false;
	private boolean puerta = true;
	private boolean hayPareja = false;

	/**
	 * un hombre llega a la sala para formar una pareja si ya hay otra mujer en la
	 * sala o si aún no hay un hombre
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void llegaHombre(int id) throws InterruptedException {
		//si hay un hombre o la puerta esta cerrada no entra, espera.
		while (hayHombre || !puerta)
			wait();
		hayHombre = true;
		System.out.println("Ha entrado el hombre " + id);
		
		// hay pareja
		if (hayMujer) {
			puerta = false;
			hayPareja = true;
			System.out.println("Hay una cita");
			notifyAll();
		} else {
			//Si un segundo hombre llega mientras no hay una pareja tiene que esperar, 
			//porque significa que ya hay un hombre dentro
			while (!hayPareja)
				wait();	
		}
		
		//Sale el hombre
		hayHombre = false;
		// Sala vacia
		if (!hayHombre && !hayMujer) {
			hayPareja = false;
			puerta = true;
			notifyAll();
		}
	}

	/**
	 * una mujer llega a la sala para formar una pareja debe esperar si ya hay otra
	 * mujer en la sala o si aún no hay un hombre
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void llegaMujer(int id) throws InterruptedException {
		while (hayMujer || !puerta)
			wait();
		hayMujer = true;
		System.out.println("Ha entrado la mujer " + id);

		if (hayHombre) {
			puerta = false;
			hayPareja = true;
			System.out.println("Hay una cita");
			notifyAll();
		} else {
			//Si una segunda mujer llega mientras no hay una pareja tiene que esperar, 
			//porque significa que ya hay una mujer dentro
			while (!hayPareja)
				wait();
			
		}

		hayMujer = false;
		if (!hayHombre && !hayMujer) {
			hayPareja = false;
			puerta = true;
			notifyAll();
		}
	}
}

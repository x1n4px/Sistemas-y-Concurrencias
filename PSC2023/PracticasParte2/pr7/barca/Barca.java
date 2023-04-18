package pr7.barca;

public class Barca extends Thread {

	private static final int C = 4;
	private int numAndroid = 0;
	private int numIphone = 0;
	private boolean puerta = true;
	private boolean viaje = false;

	/**
	 * Un estudiante con m�vil android llama a este m�todo cuando quiere cruzar
	 * el r�o. Debe esperarse a montarse en la barca de forma segura, y a llegar a
	 * la otra orilla del antes de salir del m�todo
	 * 
	 * @param id del estudiante android que llama al m�todo
	 * @throws InterruptedException
	 */

	public synchronized void android(int id) throws InterruptedException {
		while (!puerta || numAndroid + numIphone == C || numIphone == 3 || (numIphone == 1 && numAndroid == 2))
			wait();
		numAndroid++;
		System.out.println("El android " + id + " se ha montado en la barca.");
		if (numAndroid + numIphone == C) {
			puerta = false;
			System.out.println("Llegamos a la otra orilla.");
			viaje = true;
			notifyAll();
		} else {
			while (!viaje)
				wait();
		}
		numAndroid--;
		if (numAndroid + numIphone == 0) {
			viaje = false;
			puerta = true;
			notifyAll();
		}
	}

	/**
	 * Un estudiante con movil android llama a este m�todo cuando quiere cruzar el
	 * rio. Debe esperarse a montarse en la barca de forma segura, y a llegar a la
	 * otra orilla del antes de salir del metodo
	 * 
	 * @param id del estudiante android que llama al m�todo
	 * @throws InterruptedException
	 */

	public synchronized void iphone(int id) throws InterruptedException {
		while (!puerta || numAndroid + numIphone == C || numAndroid == 3 || (numIphone == 2 && numAndroid == 1))
			wait();
		numIphone++;
		System.out.println("El iphone " + id + " se ha montado en la barca.");
		if (numAndroid + numIphone == C) {
			puerta = false;
			System.out.println("Llegamos a la otra orilla.");
			viaje = true;
			notifyAll();

		} else {
			while (!viaje)
				wait();
		}
		numIphone--;
		if (numAndroid + numIphone == 0) {
			viaje = false;
			puerta = true;
			notifyAll();
		}

	}

}

//CS-IPhone: no puede subirse a la barca hasta que esté en modo subida, haya sitio 
// y no sea peligroso

//CS-Android: no puede subirse a la barca hasta que esté en modo subida, haya sitio 
//y no sea peligroso

//CS-Todos: no pueden bajarse de la barca hasta que haya terminado el viaje
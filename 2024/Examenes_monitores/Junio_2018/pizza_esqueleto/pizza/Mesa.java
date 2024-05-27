package pizza;

import java.util.LinkedList;

public class Mesa {

	boolean llevarNuevaPizza = false, llamado = false, primeroCome = false, aComer = false, pagada = false;
	int raciones = 0;

	/**
	 * 
	 * @param id
	 *           El estudiante id quiere una ración de pizza.
	 *           Si hay una ración la coge y sigue estudiante.
	 *           Si no hay y es el primero que se da cuenta de que la mesa está
	 *           vacía
	 *           llama al pizzero y
	 *           espera hasta que traiga una nueva pizza. Cuando el pizzero trae la
	 *           pizza
	 *           espera hasta que el estudiante que le ha llamado le pague.
	 *           Si no hay pizza y no es el primer que se da cuenta de que la mesa
	 *           está vacía
	 *           espera hasta que haya un trozo para él.
	 * @throws InterruptedException
	 * 
	 */
	public synchronized void nuevaRacion(int id) throws InterruptedException {
		while (llamado && !primeroCome) {
			System.out.println("Estudiante " + id + " espera a que haya pizza");
			wait();

		}

		if (!llamado && raciones == 0) {
			System.out.println("Estudiante " + id + " llama al pizzero");
			llamado = true;
			llevarNuevaPizza = true;
			aComer = false;
			primeroCome = false;
			notifyAll();
		}
		if(raciones == 0) {
			System.out.println("Estudiante " + id + " espera a que haya pizza");
			while(!aComer) {
				wait();
			}
			aComer = false;
		}

		raciones--;
		System.out.println("Estudiante " + id + " coge una ración de pizza");
		if(raciones == 7) {
			primeroCome = true;
			notifyAll();
		}
	}

	/**
	 * El pizzero entrega la pizza y espera hasta que le paguen para irse
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void nuevoCliente() throws InterruptedException {
		while(!pagada) {
			wait();
		}
		System.out.println("\t ...Pizza pagada");
		llevarNuevaPizza = false;
		aComer = true;
		raciones = 8;
		pagada = false;
		llamado = false;
		notifyAll();
	}

	/**
	 * El pizzero espera hasta que algún cliente le llama para hacer una pizza y
	 * llevársela a domicilio
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void nuevaPizza() throws InterruptedException {
		while(!llevarNuevaPizza) {
			wait();
		}
		System.out.println("\t Nueva pizza en camino");
		pagada = true;
		notifyAll();
	}

}

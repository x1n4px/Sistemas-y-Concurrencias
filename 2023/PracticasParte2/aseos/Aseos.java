package aseos;

import java.util.concurrent.Semaphore;

public class Aseos {

	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está
	 * trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando
	 * o
	 * está esperando para poder limpiar los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 */
	private int numClientesEnAseos;
	private boolean equipoLimpiezaPresente;
	public Aseos() {
		numClientesEnAseos = 0;
		equipoLimpiezaPresente = false;
	}
	public synchronized void entroAseo(int id) throws InterruptedException {
		while (equipoLimpiezaPresente) {
			wait();
		}
		numClientesEnAseos++;
		System.out.println("El cliente " + id + " ha entrado en el baño."
				+ "Clientes en el aseo: " + numClientesEnAseos);
		notifyAll();

	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public synchronized void salgoAseo(int id) throws InterruptedException {
		numClientesEnAseos--;
		System.out.println("El cliente " + id + " ha salido del baño."
				+ "Clientes en el aseo: " + numClientesEnAseos);
		notifyAll();
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que
	 * no
	 * haya ningún cliente.
	 * 
	 * @throws InterruptedException
	 * 
	 */
	public synchronized void entraEquipoLimpieza() throws InterruptedException {
		while (equipoLimpiezaPresente || numClientesEnAseos > 0) {
			wait();
		}
		equipoLimpiezaPresente = true;
		System.out.println("El equipo de limpieza está trabajando.");

	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando sale de los aseos
	 * 
	 * @throws InterruptedException
	 * 
	 * 
	 */
	public synchronized void saleEquipoLimpieza() throws InterruptedException {
		equipoLimpiezaPresente = false;
		System.out.println("El equipo de limpieza ha terminado.");
		notifyAll();
	}
}

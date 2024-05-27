package aseos;

import java.util.concurrent.Semaphore;

public class Aseos {
	private int cnt = 0;
	private boolean estanLimpiando = false;
	private boolean personasDentro = false;
 
	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza est�
	 * trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza est� trabajando
	 * o
	 * est� esperando para poder limpiar los aseos
	 * @throws InterruptedException 
	 * 
	 */
	public synchronized void entroAseo(int id) throws InterruptedException {
		while(estanLimpiando) {
			wait();
		} 
		cnt++;
		if(cnt == 1) {
			personasDentro = true;
		}
		System.out.println("Cliente " + id + " ha entrado en los aseos");
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 */
	public synchronized void salgoAseo(int id) throws InterruptedException {
		cnt--;
		System.out.println("Cliente " + id + " ha salido de los aseos");
		if(cnt == 0) {
			personasDentro = false;
			notifyAll();
		}
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
	 * CS: El equipo de trabajo est� solo en los aseos, es decir, espera hasta que
	 * no
	 * haya ning�n cliente.
	 * 
	 */
	public synchronized void entraEquipoLimpieza() throws InterruptedException {
		while(personasDentro) {
			wait();
		}
		estanLimpiando = true;
		System.out.println("El equipo de limpieza ha entrado en los aseos");
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando sale de los aseos
	 * 
	 * 
	 */
	public synchronized void saleEquipoLimpieza() throws InterruptedException {
		estanLimpiando = false;
		System.out.println("El equipo de limpieza ha salido de los aseos");
		notifyAll();
	}	
}

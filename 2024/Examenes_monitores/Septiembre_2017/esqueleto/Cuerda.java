package esqueleto;

import java.util.concurrent.Semaphore;

public class Cuerda {
	int nMonoNS = 0, nMonoSN = 0;
	boolean usandoNS = false, usandoSN = false;

	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * 
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public void entraDireccionNS(int id) throws InterruptedException {
		while (usandoSN || nMonoNS >= 3) {
			wait();
		}
		nMonoNS++;
		if (nMonoNS == 1) {
			usandoNS = true;
		}
		System.out.println("Babuino " + id + " entra en la cuerda en dirección Norte-Sur");
	}

	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * 
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public void entraDireccionSN(int id) throws InterruptedException {
		while (usandoNS || nMonoSN >= 3) {
			wait();
		}
		nMonoNS++;
		if (nMonoSN == 1) {
			usandoSN = true;
		}
		System.out.println("Babuino " + id + " entra en la cuerda en dirección Sur-Norte");
	}

	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección
	 * Norte-Sur
	 * 
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public void saleDireccionNS(int id) throws InterruptedException {
		nMonoNS--;
		System.out.println("Babuino " + id + " sale de la cuerda en dirección Norte-Sur");
		if(nMonoNS == 0) {
			usandoNS = false;
		}
		notifyAll();

	}

	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección
	 * Sur-Norte
	 * 
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public void saleDireccionSN(int id) throws InterruptedException {
		nMonoSN--;
		System.out.println("Babuino " + id + " sale de la cuerda en dirección Sur-Norte");
		if(nMonoSN == 0) {
			usandoSN = false;
		}
		notifyAll();
	}

}

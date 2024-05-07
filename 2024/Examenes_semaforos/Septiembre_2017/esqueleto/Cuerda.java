package esqueleto;

import java.util.concurrent.Semaphore;

public class Cuerda {
	int nMonoNS = 0, nMonoSN = 0;
	Semaphore haySitio1 = new Semaphore(1, true);
	Semaphore haySitio2 = new Semaphore(1, true);
	Semaphore turno = new Semaphore(1, true);
	Semaphore mutex1 = new Semaphore(1, true);
	Semaphore mutex2 = new Semaphore(1, true);

	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionNS(int id) throws InterruptedException{
		haySitio1.acquire();
		mutex1.acquire();
		nMonoNS++;
		if(nMonoNS == 1) {
			turno.acquire();
		}
		System.out.println("El babuino " + id + " entra en la cuerda en dirección Norte-Sur. Hay " + nMonoNS + " babuinos en la cuerda en dirección Norte-Sur.");
		if(nMonoNS < 3) {
			haySitio1.release();
		}
		mutex1.release();
	}
	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón  colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionSN(int id) throws InterruptedException{
		haySitio2.acquire();
		mutex2.acquire();
		nMonoSN++;
		if(nMonoSN == 1) {
			turno.acquire();
		}
		System.out.println("El babuino " + id + " entra en la cuerda en dirección SUR-NORTE. Hay " + nMonoSN + " babuinos en la cuerda en dirección SUR-NORTE.");
		if(nMonoSN < 3) {
			haySitio2.release();
		}
		mutex2.release();
	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionNS(int id) throws InterruptedException{
			mutex1.acquire();
			nMonoNS--;
			System.out.println("El babuino " + id + " sale de la cuerda en dirección Norte-Sur. Hay " + nMonoNS + " babuinos en la cuerda en dirección Norte-Sur.");
			if(nMonoNS == 2) {
				haySitio1.release();
			}
			if(nMonoNS == 0) {
				turno.release();
			}
			mutex1.release();
	}
	
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionSN(int id) throws InterruptedException{
		mutex2.acquire();
		nMonoSN--;
		System.out.println("El babuino " + id + " sale de la cuerda en dirección Sur-Norte. Hay " + nMonoSN + " babuinos en la cuerda en dirección Sur-Norte.");
		if(nMonoSN == 2) {
			haySitio2.release();
		}
		if(nMonoSN == 0) {
			turno.release();
		}
		mutex2.release();
	}	
		
}

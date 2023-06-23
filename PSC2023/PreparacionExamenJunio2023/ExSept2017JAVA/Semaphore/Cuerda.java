package esqueleto;


import java.util.concurrent.Semaphore;

public class Cuerda {
	private int nMonosNS = 0;
	private int nMonosSN = 0;
	private Semaphore haySitio1 = new Semaphore(1,true);
	private Semaphore haySitio2 = new Semaphore(1, true);
	private Semaphore turno = new Semaphore(1, true);
	private Semaphore mutex1 = new Semaphore(1, true);
	private Semaphore mutex2 = new Semaphore(1, true);

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
		nMonosNS++;
		if(nMonosNS == 1) {
			turno.acquire();
		}
		System.out.println("Entra el mono "+id+" en direccion Norte-Sur. Hay "+ nMonosNS+ " monos Norte-Sur");
		if(nMonosNS < 3) {
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
		nMonosSN++;
		if(nMonosSN == 1) {
			turno.acquire();
		}
		System.out.println("Entra el mono "+id+" en direccion Sur-Norte. Hay "+ nMonosSN+ " monos Sur-Norte");
		if(nMonosSN < 3) {
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
		nMonosNS--;
		System.out.println("Sale el mono " +id+" en direccion Norte-Sur. Hay "+nMonosNS+" monos Norte-Sur");
		if(nMonosNS == 2){
			haySitio1.release();
		}
		if(nMonosNS == 0) {
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
		nMonosSN--;
		System.out.println("Sale el mono " +id+" en direccion Sur-Norte. Hay "+nMonosSN+" monos Sur-Norte");
		if(nMonosSN == 2){
			haySitio2.release();
		}
		if(nMonosSN == 0) {
			turno.release();
		}
		mutex2.release();
	}	
		
}

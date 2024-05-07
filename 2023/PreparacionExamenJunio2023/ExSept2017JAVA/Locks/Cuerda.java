package esqueleto;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cuerda {
	private int nMonosNS = 0;
	private int nMonosSN = 0;
	private Lock lock = new ReentrantLock();
	private Condition okNS = lock.newCondition();
	private Condition okSN = lock.newCondition();


	/**
	 * Utilizado por un babuino cuando quiere cruzar el cañón colgándose de la
	 * cuerda en dirección Norte-Sur
	 * Cuando el método termina, el babuino está en la cuerda y deben satisfacerse
	 * las dos condiciones de sincronización
	 * @param id del babuino que entra en la cuerda
	 * @throws InterruptedException
	 */
	public  void entraDireccionNS(int id) throws InterruptedException{
		lock.lock();
		try{
			while (nMonosSN > 0 || nMonosNS == 3) {
				okNS.await();
			}
			nMonosNS++;
			System.out.println("Entra el mono " +id+" en direccion Norte-Sur. Hay "+nMonosNS+" monos Norte-Sur");
		}finally {
			lock.unlock();
		}
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
		lock.lock();
		try{
			while (nMonosNS > 0 || nMonosSN == 3) {
				okSN.await();
			}
			nMonosSN++;
			System.out.println("Entra el mono " +id+" en direccion Sur-Norte. Hay "+nMonosNS+" monos Sur-Norte");
		}finally {
			lock.unlock();
		}
	}
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Norte-Sur
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionNS(int id) throws InterruptedException{
		lock.lock();
		try{
			nMonosNS--;
			System.out.println("Sale el mono " +id+" en direccion Norte-Sur. Hay "+nMonosNS+" monos Norte-Sur");
			if(nMonosNS == 0) {
				okSN.signalAll();
			}else{
				okNS.signal();
			}
		}finally {
			lock.unlock();
		}
	}
	
	/**
	 * Utilizado por un babuino que termina de cruzar por la cuerda en dirección Sur-Norte
	 * @param id del babuino que sale de la cuerda
	 * @throws InterruptedException
	 */
	public  void saleDireccionSN(int id) throws InterruptedException{
		lock.lock();
		try{
			nMonosSN--;
			System.out.println("Sale el mono " +id+" en direccion Sur-Norte. Hay "+nMonosSN+" monos Sur-Norte");
			if(nMonosSN == 0) {
				okNS.signalAll();
			}else{
				okSN.signal();
			}
		}finally {
			lock.unlock();
		}
	}	
		
}

package guarderia.Locks;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Guarderia {

	private int nBebe;
	private int nAdulto;
	private Lock lock;
	private Condition bebePuedeEntrar;
	private Condition adultoPuedeSalir;

	public Guarderia() {
		nBebe = 0;
		nAdulto = 0;
		lock = new ReentrantLock();
		bebePuedeEntrar = lock.newCondition();
		adultoPuedeSalir = lock.newCondition();
	}

	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void entraBebe(int id) {
		lock.lock();
		try {
			while(nBebe >= 3*nAdulto || nAdulto == 0) {
				try{
					bebePuedeEntrar.await();
				}catch (InterruptedException e ){
					e.printStackTrace();
				}
			}
			nBebe++;
			System.out.println("Entra un nuevo bebe "+id  +" --> nAdulto: "+nAdulto+", nBebes: "+nBebe);
		}finally {
			lock.unlock();
		}
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public void saleBebe(int id) throws InterruptedException{
		lock.lock();
		try {
			nBebe--;
			System.out.println("Sale el bebe "+id+" --> nAdulto: "+nAdulto+", nBebes: "+nBebe);
			bebePuedeEntrar.signalAll(); // Notificar a los bebés que pueden intentar entrar
		} finally {
			lock.unlock();
		}
	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException{
		lock.lock();
		try {
			nAdulto++;
			System.out.println("Entra el adulto "+id+" --> nAdulto: "+nAdulto+", nBebes: "+nBebe);
			adultoPuedeSalir.signalAll(); // Notificar a los adultos que pueden intentar salir
		} finally {
			lock.unlock();
		}

	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void saleAdulto(int id) throws InterruptedException{
		lock.lock();
		try {
			while (nBebe > 3*(nAdulto-1)) {
				try{
					adultoPuedeSalir.await();
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}
			nAdulto--;
			System.out.println("Sale el adulto "+id+" --> nAdulto: "+nAdulto+", nBebes: "+nBebe);
			adultoPuedeSalir.signalAll();
		}finally {
			lock.unlock();
		}
		
	}

}

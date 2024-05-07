package guarderia.Semaphore;


import java.util.concurrent.Semaphore;

public class Guarderia {

	private int numAdultos;
	private int numBebe;

	private Semaphore BebePuedeEntrar;
	private Semaphore AdultoPuedeSalir;
	private Semaphore mutex;


	public Guarderia() {
		numAdultos = 0;
		numBebe = 0;
		BebePuedeEntrar = new Semaphore(0);
		AdultoPuedeSalir = new Semaphore(0);
		mutex = new Semaphore(1);
	}

	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void entraBebe(int id) throws InterruptedException{
		mutex.acquire();
		if(numBebe+1 <= 3*numAdultos) {
			BebePuedeEntrar.release();
		}
		mutex.release();
		BebePuedeEntrar.acquire();
		mutex.acquire();
		numBebe++;
		System.out.println("Entra el nuevo bebe "+id+" --> Adultos: "+numAdultos+", Bebes: "+numBebe);
		mutex.release();
 	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public void saleBebe(int id) throws InterruptedException{
		mutex.acquire();
		numBebe--;
		System.out.println("Sale el nuevo bebe "+id+" --> Adultos: "+numAdultos+", Bebes: "+numBebe);
		mutex.release();

	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException{
		mutex.acquire();
		numAdultos++;
		System.out.println("Entra el nuevo Adulto "+id+" --> Adultos: "+numAdultos+", Bebes: "+numBebe);
		mutex.release();
	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void saleAdulto(int id) throws InterruptedException{
		mutex.acquire();
		if(numBebe <= 3*(numAdultos-1)){
			AdultoPuedeSalir.release();
		}
		mutex.release();
		AdultoPuedeSalir.acquire();
		mutex.acquire();
		numAdultos--;
		System.out.println("Sale el nuevo Adulto "+id+" --> Adultos: "+numAdultos+", Bebes: "+numBebe);
		mutex.release();
	}

}

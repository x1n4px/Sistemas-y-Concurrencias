package guarderia;


import java.util.concurrent.Semaphore;

public class Guarderia {


	private int nBebe;
	private int nAdulto;
	private Semaphore bebePuedeEntrar;
	private Semaphore adultoPuedeSalir;


	public Guarderia() {
		nBebe = 0;
		nAdulto = 0;
		bebePuedeEntrar = new Semaphore(0);
		adultoPuedeSalir = new Semaphore(0);
	}
	
	/**
	 * Un bebe que quiere entrar en la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro entrar, es decir, hasta que 
	 * cuado entre haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void entraBebe(int id) throws InterruptedException{
		if(nBebe >= 3 * nAdulto || nAdulto == 0) {
			bebePuedeEntrar.acquire();
		}
		nBebe++;
		System.out.println("Entra un nuevo bebé " + id + " --> nAdulto: " + nAdulto + ", nBebes: " + nBebe);
	}
	/**
	 * Un bebe que quiere irse de la guarderia llama a este metodo * 
	 */
	public void saleBebe(int id) throws InterruptedException{
		nBebe--;
		System.out.println("Sale el bebé " + id + " --> nAdulto: " + nAdulto + ", nBebes: " + nBebe);
		if(nBebe < 3*nAdulto) {
			bebePuedeEntrar.release();
		}

	}
	/**
	 * Un adulto que quiere entrar en la guarderia llama a este metodo * 
	 */
	public void entraAdulto(int id) throws InterruptedException{
		nAdulto++;
		System.out.println("Entra el adulto " + id + " --> nAdulto: " + nAdulto + ", nBebes: " + nBebe);
		if(nBebe < 3*nAdulto) {
			bebePuedeEntrar.release();
		}
	}
	
	/**
	 * Un adulto que quiere irse  de la guarderia llama a este metodo.
	 * Debe esperar hasta que sea seguro salir, es decir, hasta que
	 * cuando se vaya haya, al menos, 1 adulto por cada 3 bebes
	 * 
	 */
	public void saleAdulto(int id) throws InterruptedException{
		if(nBebe > 3*(nAdulto-1)) {
			adultoPuedeSalir.acquire();
		}
		nAdulto--;
		System.out.println("Sale el adulto " + id + " --> nAdulto: " + nAdulto + ", nBebes: " + nBebe);
		adultoPuedeSalir.release();
	}

}

package aseos;

import java.util.concurrent.Semaphore;

public class Aseo {

	int nHombres = 0, nMujeres = 0;
	Semaphore mutex1 = new Semaphore(1, true);
	Semaphore mutex2 = new Semaphore(1, true);
	Semaphore turno = new Semaphore(1, true);
	
	/**
	 * El hombre id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay alguna mujer en ese
	 * momento en el aseo
	 */
	public void llegaHombre(int id) throws InterruptedException{
		System.out.println("Hombre " + id + " quiere entrar");
		mutex1.acquire();
		nHombres++;
		if(nHombres == 1){ // si hay un hombre dentro, no puede entrar ninguna mujer
			turno.acquire(); // bloquea a las mujeres
		}
		System.out.println("Hombre " + id + " entra. Hay " + nHombres + " hombres y " + nMujeres + " mujeres");
		mutex1.release();
	}
	/**
	 * La mujer id quiere entrar en el aseo. 
	 * Espera si no es posible, es decir, si hay algun hombre en ese
	 * momento en el aseo
	 */
	public void llegaMujer(int id) throws InterruptedException{
		System.out.println("Hombre " + id + " quiere entrar");
		mutex2.acquire();
		nMujeres++;
		if(nMujeres == 1){ // si hay una mujer dentro, no puede entrar ningun hombre
			turno.acquire(); // bloquea a los hombres
		}
		System.out.println("Mujer " + id + " entra. Hay " + nHombres + " hombres y " + nMujeres + " mujeres");
		mutex2.release();
	}
	/**
	 * El hombre id, que estaba en el aseo, sale
	 */
	public void saleHombre(int id)throws InterruptedException{
		mutex1.acquire();
		nHombres--;
		System.out.println("Hombre " + id + " sale. Hay " + nHombres + " hombres y " + nMujeres + " mujeres");
		if(nHombres == 0){ // si no hay hombres dentro, las mujeres pueden entrar
			turno.release(); // desbloquea a las mujeres
		}
		mutex1.release();
	}
	
	public void saleMujer(int id)throws InterruptedException{
		mutex2.acquire();
		nMujeres--;
		System.out.println("Mujer " + id + " sale. Hay " + nHombres + " hombres y " + nMujeres + " mujeres");
		if(nMujeres == 0){ // si no hay mujeres dentro, los hombres pueden entrar
			turno.release(); // desbloquea a los hombres
		}
		mutex2.release();
	}
}

package pr6.pajaritos;

import java.util.concurrent.Semaphore;

public class Nido {
	private static final int MAX = 10;
	private int numB = 0;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore hayEspacio = new Semaphore(1, true);
	private Semaphore hayComida = new Semaphore(0, true);

	public void come(int id) throws InterruptedException {
		// el bebe id coge un bichito del nido
		hayComida.acquire();
		mutex.acquire();
		numB--;
		System.out.println("El bebe " + id + " ha comido un bichito. Quedan " + numB);
		if (numB > 0)
			hayComida.release();
		else
			hayEspacio.release();
		/*
		if(numB>0)hayComida.release();
		if(numB<MAX)hayEspacio.release();
		
		if(numB==MAX-1)hayEspacio.release();
		 */
		
		mutex.release();
	}

	public void nuevoBichito(int id) throws InterruptedException {
		// el papa/mama id deja un nuevo bichito en el nido
		hayEspacio.acquire();
		mutex.acquire();		 
		numB++;
		System.out.println("El papa " + id + " ha a�adido un bichito. Hay " + numB);
		if(numB>0)hayComida.release();
		if(numB<MAX)hayEspacio.release();
		
		mutex.release();
	}
}

//CS-Bebe-i: No puede comer del nido si está vacío

//CS-Papa/Mama: No puede poner un bichito en el nido si está lleno

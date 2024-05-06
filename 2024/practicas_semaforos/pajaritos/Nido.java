package pajaritos;

import java.util.concurrent.Semaphore;

public class Nido {

	static final int B = 3;
	int bichitos = 0;
	Semaphore mutex = new Semaphore(1);
	Semaphore bebes = new Semaphore(0);
	Semaphore padres = new Semaphore(1);
	
	public void come(int id) throws InterruptedException{
		//el bebe id coge un bichito del nido
		bebes.acquire();
		mutex.acquire();
		bichitos--;

		System.out.println("El bebé "+id+" ha comido un bichito. Quedan "+bichitos);
		
		if(bichitos == B - 1) {
			padres.acquire();
		}
		if(bichitos > 0) {
			bebes.release();
		}
		mutex.release();
	}
	
	public void nuevoBichito(int id) throws InterruptedException {
		//el papa/mama id deja un nuevo bichito en el nido
		padres.acquire();
		mutex.acquire();
		bichitos++;
		
		System.out.println("El papá "+id+" ha añadido un bichito. Hay "+bichitos);
		
		if(bichitos < B) {
			padres.release();
		}
		if(bichitos == 1) {
			bebes.release();
		}
		mutex.release();
	}
}

//CS-Bebe-i: No puede comer del nido si está vacío
//CS-Papa/Mama: No puede poner un bichito en el nido si está lleno

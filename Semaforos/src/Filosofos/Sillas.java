package Filosofos;
import java.util.concurrent.*;
public class Sillas {//recursos
	
	private int sillasLibres = 4;
	private Semaphore mutex = new Semaphore(1);//protege a sillasLibres
	private Semaphore hayLibres = new Semaphore(1);
	//hayLibres = 1 sii sillasLibres > 0,hayLibres = 0 sii sillasLibres = 0
	public void cojoSilla(int id) throws InterruptedException {
		hayLibres.acquire();
		mutex.acquire();
		sillasLibres--;
		System.out.println("El filosofo "+id+" coge una silla. Quedan "+sillasLibres);
		if(sillasLibres>0) {
			hayLibres.release();
		}
		mutex.release();
	}

	
	public void sueltoSilla(int id) throws InterruptedException {
		mutex.acquire();
		sillasLibres++;
		System.out.println("El filosofo "+id+" devuelve una silla. Quedan "+sillasLibres);
		if(sillasLibres==1) {
			hayLibres.release();
		}
		mutex.release();
		
	}

}
//CS- No puedo coger una silla hasta que no hay una disponible
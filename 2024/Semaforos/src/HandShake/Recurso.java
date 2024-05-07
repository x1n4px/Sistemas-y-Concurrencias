package HandShake;

import java.util.concurrent.Semaphore;

public class Recurso {

	private int hanLlegado;
	private Semaphore mutex = new Semaphore(1);
	
	private Semaphore sic = new Semaphore(0);
	
	public void sincronizar(int id)throws InterruptedException{
		mutex.acquire();
		hanLlegado++;
		System.out.println("P"+id+" llega a la cita");
		if(hanLlegado == 2) {
			sic.release();
			hanLlegado--;
		}else {
			mutex.release();
			sic.acquire();
			mutex.acquire();
			hanLlegado--;
		}
		System.out.println("P"+id+" --- sincronizado con P2");
		mutex.release();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}

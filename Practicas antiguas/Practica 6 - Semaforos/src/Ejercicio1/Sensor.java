package Ejercicio1;
import java.util.Random;

public class Sensor extends Thread {
	private Sistema sis;
	private int id;
	private Random r;
	
	public Sensor(int id, Sistema s) {
		this.id = id;
		sis = s;
		r = new Random();
	}
	
	public void run() {
		
			try {
				while(!this.isInterrupted()) {
				sis.ponerMedida(id, r.nextInt(20));
				Thread.sleep(r.nextInt(50) +10);				
				}
			} catch (InterruptedException e) {

		}
	}
	

}

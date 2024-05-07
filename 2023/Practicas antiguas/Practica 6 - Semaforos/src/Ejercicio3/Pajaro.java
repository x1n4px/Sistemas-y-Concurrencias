package Ejercicio3;
import java.util.Random;

public class Pajaro extends Thread {
	private int id;
	private Nido nido;
	private Random r;
	
	public Pajaro(int id, Nido n) {
		this.id = id;
		nido = n;
		r =new Random();
	}
	
	
	public void run() {
		try {
			while(!this.isInterrupted()) {
				Thread.sleep(r.nextInt(10)+10);
				nido.depositarBicho(id);
			}
		}catch(InterruptedException e) {
			
		}
	}
}

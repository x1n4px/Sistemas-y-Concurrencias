package Ejercicio3;
import java.util.Random;

public class Polluelo extends Thread {
	private int id;
	private Nido nido;
	private Random r;
	
	public Polluelo(int id, Nido n) {
		this.id = id;
		nido = n;
		r = new Random();
	}
	
	
	public void run() {
		try {
			while(!this.isInterrupted()) {
				Thread.sleep(r.nextInt(30)+50);
				nido.comerBicho(id);
				
			}
		}catch(InterruptedException e ) {}
	}
	
	
}

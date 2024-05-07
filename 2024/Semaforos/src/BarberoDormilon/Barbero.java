package BarberoDormilon;
import java.util.*;
public class Barbero extends Thread{
	private Barberia b;
	private Random r = new Random();
	
	public Barbero(Barberia b){
		this.b = b;
	}
	
	public void run() {
		while(true) {
			try {
				b.pelar();
				Thread.sleep(r.nextInt(50));
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}
		
		
	}
	
}

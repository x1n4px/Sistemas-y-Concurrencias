package BarberoDormilon;

import java.util.Random;

public class Entorno extends Thread {

	private Barberia b;
	private Random r;
	
	public Entorno(Barberia b) {
		this.b = b;
	}
	
	
	public void run() {
		while(true) {
			try {
				b.nuevoCliente();
				Thread.sleep(200);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		}
		
		
	}
	
	
}

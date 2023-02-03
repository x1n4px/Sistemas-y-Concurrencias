package Ejercicio4;

import java.util.Random;

public class Cocinero extends Thread {
	private IOlla n;
	
	private static Random r = new Random();
	
	public Cocinero(IOlla n) {
		// TODO Auto-generated constructor stub
		this.n = n;
		
	}
	
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(r.nextInt(20));
				n.nuevoExplorador();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
		}
		
		
		
	}
	
}

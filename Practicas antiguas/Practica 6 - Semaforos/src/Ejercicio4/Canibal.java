package Ejercicio4;

import java.util.Random;

public class Canibal extends Thread{

	private IOlla n;
	private int id;
	private static Random r = new Random();
	
	public Canibal(IOlla n, int id) {
		// TODO Auto-generated constructor stub
		this.n = n;
		this.id = id;
	}
	
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(r.nextInt(200));
				n.comeRacion(id);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
		}
		
		
		
	}
	
	
}

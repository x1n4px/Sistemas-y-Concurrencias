package LectoresSemaforos;

import java.util.Random;

public class Lector extends Thread{
	private int id;
	private GestorBD gestor;
	private Random r;
	
	public Lector(int id,GestorBD gestor) {
		this.id = id;
		this.gestor = gestor;
		r = new Random();
	}
	
	public void run() {
		while(true) {
			try {
				gestor.entraLector(id);
				Thread.sleep(r.nextInt(100));
				gestor.saleLector(id);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		
		
	}
	
}

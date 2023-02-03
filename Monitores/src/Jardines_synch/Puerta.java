package Jardines_synch;

public class Puerta extends Thread{
	private int id;
	Jardin c;
	
	public Puerta(int id, Jardin j) {
		this.id = id;
		this.c = j;
		
	}
	
	public void run() {
		for (int i = 0; i < 1000; i++) {
			c.inc(id);
		}
	}
	
	
	
	
}

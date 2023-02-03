package Jardines_synch_static;

public class Puerta extends Thread{
	private int id;
	Jardin c;
	
	public Puerta(int id) {
		this.id = id;
		c = new Jardin();
		
	}
	
	public void run() {
		for (int i = 0; i < 1000; i++) {
			Jardin.inc2(id);//llamada a metodo estatico
			c.inc(id);//llamada a metodo no estatico que accede a variable estatica
		}
	}
	
	
	
	
}

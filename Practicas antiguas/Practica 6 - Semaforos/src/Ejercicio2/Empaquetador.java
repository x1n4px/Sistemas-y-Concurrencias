package Ejercicio2;
import java.util.*;
public class Empaquetador extends Thread{

	private Cadena c;
	private Random r;
	private int id;
	
	public Empaquetador(Cadena c,int id) {
		this.c = c;
		this.id = id;
		r = new Random();
	}
	
	public void run() {
		
		while(true) {
			try {
				c.extraerProductor(id);
				Thread.sleep(r.nextInt(1000)+20);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

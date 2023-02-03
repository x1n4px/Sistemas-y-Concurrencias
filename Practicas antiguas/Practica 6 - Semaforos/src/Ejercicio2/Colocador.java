package Ejercicio2;
import java.util.*;
public class Colocador extends Thread {

	private Cadena c;
	private Random r;
	
	public Colocador(Cadena c) {
		this.c = c;
		r = new Random();
	}
	
	public void run() {
		
		while(true) {
			try {
				Thread.sleep(r.nextInt(200)+20);
				c.nuevoProducto(r.nextInt(3));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	
	
}

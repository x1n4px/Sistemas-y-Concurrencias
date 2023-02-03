package Aseo;

import java.util.Random;

public class Limpieza extends Thread{
	private AseoSemaforo aseo;
	private Random r;
	
	public Limpieza(AseoSemaforo aseo) {
		this.aseo = aseo;
		this.r = new Random();
	}
	
	public void run() {
		try {
			while(true) {
				aseo.entraLimpieza();
				Thread.sleep(100+r.nextInt(100));//Limpia el aseo
				aseo.saleLimpieza();
				Thread.sleep(100+r.nextInt(100));//espera al siguiente turno de limpieza
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}
	
}

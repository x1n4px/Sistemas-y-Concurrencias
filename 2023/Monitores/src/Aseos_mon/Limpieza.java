package Aseos_mon;
import java.util.*;
public class Limpieza extends Thread {
	private AseoMon aseo;
	private Random r;
	
	public Limpieza(AseoMon aseo) {
		this.aseo = aseo;
		r = new Random();
		
	}
	
	
	public void run() {
		try {
			while(true) {
				aseo.entraLimpieza();
				Thread.sleep(100+r.nextInt(100)); // limpia el aseo
				aseo.saleLimpieza();
				Thread.sleep(100+r.nextInt(100));//espera al siguiente turno de limpieza
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
}

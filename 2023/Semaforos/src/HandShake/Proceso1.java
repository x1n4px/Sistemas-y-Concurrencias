package HandShake;

import java.util.Random;

public class Proceso1 extends Thread{
	Recurso r;
	Random rdm;
	
	public Proceso1(Recurso r) {
		this.r = r;
		rdm = new Random();
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(rdm.nextInt(200));
				r.sincronizar(2);
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}

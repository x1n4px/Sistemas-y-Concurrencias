package HandShake;

import java.util.Random;

public class Proceso2 extends Thread{
	Recurso r;
	Random rdm;
	
	public Proceso2(Recurso r) {
		this.r = r;
		rdm = new Random();
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(rdm.nextInt(100));
				r.sincronizar(1);
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}

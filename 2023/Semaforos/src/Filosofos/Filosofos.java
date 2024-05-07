package Filosofos;

import java.util.Random;

public class Filosofos extends Thread{

	private int id;
	private static Random r = new Random();
	private Tenedor izda,der;
	private Sillas silla;
	public Filosofos(int id,Tenedor izda,Tenedor der,Sillas s) {
		this.id = id;
		this.der = der;
		this.izda = izda;
		this.r= new Random();
		this.silla = s;
	}
	
	
	public void run() {
		while(true) {
			//pensando
			try {
				Thread.sleep(r.nextInt(10));//pensando
				silla.cojoSilla(id);
				izda.cojoTenedor(id);
				der.cojoTenedor(id);
				Thread.sleep(r.nextInt(10));//comiendo
				silla.sueltoSilla(id);
				izda.devuelvoTenedor(id);
				der.devuelvoTenedor(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//comiendo
			
		}
		
		
	}
	
	
}

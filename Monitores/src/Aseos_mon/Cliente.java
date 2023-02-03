package Aseos_mon;

import java.util.Random;

public class Cliente extends Thread{
	private AseoMon aseo;
	private Random r;
	private int id;
	
	public Cliente(AseoMon aseo,int id) {
		this.aseo = aseo;
		this.id = id;
		r = new Random();
		
	}
	
	
	public void run() {
		try {
			while(true) {
				
				Thread.sleep(r.nextInt(50)); // Da un paseo por el centro comercial
				aseo.entraCliente(id);
				Thread.sleep(r.nextInt(50));//Pasa un tiempo en el aseo
				aseo.saleCliente(id);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	
}

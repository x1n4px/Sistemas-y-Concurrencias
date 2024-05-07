package Aseo;

import java.util.Random;

public class Cliente extends Thread{
	private AseoSemaforo aseo;
	private Random r;
	private int id;
	
	public Cliente(AseoSemaforo aseo,int id) {
		this.aseo = aseo;
		this.r = new Random();
		this.id = id;
	}
	
	public void run() {
		try {
			while(true) {
				Thread.sleep(r.nextInt(50));//Da un paseo por el centro comercial
				aseo.entraCliente(id);
				Thread.sleep(r.nextInt(50));//pasa un tiempo por el aseo
				aseo.saleCliente(id);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
	}
}

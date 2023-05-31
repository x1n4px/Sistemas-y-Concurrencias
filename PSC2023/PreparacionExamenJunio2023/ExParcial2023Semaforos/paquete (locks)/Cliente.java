package paquete;

import java.util.Random;

public class Cliente extends Thread{

	private int id;
	private Gestor gestor;
	private Random r = new Random();
	
	public Cliente(Gestor g, int id) {
		this.gestor = g;
		this.id = id;
	}	
	public void run() {
		try {
			Thread.sleep(2000);
			int peso = r.nextInt(10)+5;
			while (gestor.entregaPaquete(id, peso).compareTo(EstadoPaquete.REINTENTAR)==0)
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

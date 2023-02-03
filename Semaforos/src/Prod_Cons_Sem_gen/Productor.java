package Prod_Cons_Sem_gen;

import java.util.Random;

public class Productor extends Thread{
	private Random r = new Random();
	private Buffer buffer;
	private int iter;
	
	public Productor(Buffer b, int n) {
		buffer = b;
		iter = n;
	}
	
	public void run() {
		int dato;
		try {
			for(int i=0;i<iter;i++) {
				dato = r.nextInt(100);
				System.out.println("Dato producido: "+dato);
				buffer.producir(dato);
			}
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
}

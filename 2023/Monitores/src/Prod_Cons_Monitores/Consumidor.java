package Prod_Cons_Monitores;

public class Consumidor extends Thread {
	private Buffer buffer;
	private int iter;
	
	public Consumidor(Buffer b, int n) {
		buffer = b;
		iter = n;
	}
	
	public void run() {
		
		try {
			int elem;
			for(int i=0;i<iter;i++) {
				elem = buffer.extraer();
				System.out.println("Elemento leido: "+elem);
			}
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
}

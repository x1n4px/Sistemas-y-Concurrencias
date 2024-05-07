package Prod_Cons_Sem_gen;

public class Consumidor extends Thread{
	
		private Buffer buffer;
		private int iter;
		
		public Consumidor(Buffer b, int n) {
			buffer = b;
			iter = n;
		}
		
		public void run() {
			int elem;
			try {
				for(int i=0;i<iter;i++) {
					elem = buffer.extraer();
					System.out.println("Elemento extraido: "+elem);
				}
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			
		}
		
}

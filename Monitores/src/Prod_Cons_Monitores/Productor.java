package Prod_Cons_Monitores;

public class Productor extends Thread {

	private Buffer buffer;
	private int iter;
	
	public Productor(Buffer b, int n) {
		buffer = b;
		iter = n;
	}
	
	public void run() {
		try {
			for(int i=0;i<iter;i++) {
				buffer.insertar(i);
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	
}

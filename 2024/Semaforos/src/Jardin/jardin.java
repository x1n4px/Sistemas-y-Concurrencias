package Jardin;
import java.util.concurrent.Semaphore;

public class jardin {

	private int visitantes;
	private Semaphore mutex;
	
	
	public jardin() {
		visitantes=0;
		
	}
	
	public int getVisitantes() {
		return visitantes;
	}
	
	public void nuevoVisitante(int id)throws InterruptedException{
		mutex.acquire();//pre protocolo
		//mutex vale 0
		visitantes++;
		System.out.println("Nuevo visitante P"+id);
		mutex.release(); //post-protocolo
		//mutex vale 1
	}
	
	
	//programa principal que modela el jardin
	public static void main(String[] args) {
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}





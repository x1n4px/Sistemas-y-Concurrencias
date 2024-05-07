package Filosofos;
import java.util.concurrent.*;;
public class Tenedor {//un recurso
	private int id;
	public Tenedor(int id) {
		this.id = id;
	}
	private Semaphore estaLibre = new Semaphore(1);
	
	
	public void cojoTenedor(int id)throws InterruptedException {
		estaLibre.acquire();
		System.out.println("El filosofo "+id+ " tiene el tenedor "+this.id);
	}
	
	public void devuelvoTenedor(int id) {
		estaLibre.release();
	}
	
	
}

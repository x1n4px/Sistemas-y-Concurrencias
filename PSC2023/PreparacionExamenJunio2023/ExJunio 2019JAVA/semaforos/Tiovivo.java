import java.util.concurrent.Semaphore;

public class Tiovivo {
	private int nPasajeros = 0, N;

	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore finViaje = new Semaphore(1, true);
	private Semaphore lleno = new Semaphore(0, true);
	private Semaphore puedobajar = new Semaphore(0, true);

	public Tiovivo(int cantidad) {
		N = cantidad;
	}

	public void subir(int id) throws InterruptedException
	{
 		finViaje.acquire();
		mutex.acquire();
		nPasajeros++;
		System.out.println("El pasajero "+id+" se suba al tiovido. Hay "+nPasajeros+ " pasajeros.");
		if(nPasajeros < N) {
			finViaje.release();
		}else{
			lleno.release();
		}
		mutex.release();
	}
	
	public void bajar(int id) throws InterruptedException
	{
		puedobajar.acquire();
		mutex.acquire();
		nPasajeros--;
		System.out.println("El pasajero "+id+" baja del tiovivo. Hay "+nPasajeros+" pasajeros");
		if(nPasajeros > 0) {
			puedobajar.release();
		}else{
			finViaje.release();
		}
		mutex.release();
	}
	
	public void esperaLleno () throws InterruptedException
	{
		lleno.acquire();
		System.out.println("El tiovivo ya esta lleno. Se pone en marcha");
	}
	
	public void finViaje () throws InterruptedException
	{
		System.out.println("El tiovivo ha parado. Ya se pueden bajar los pasajeros");
		puedobajar.release();
	}
}

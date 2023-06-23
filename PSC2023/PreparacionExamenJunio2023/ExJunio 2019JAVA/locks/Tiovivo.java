import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tiovivo {
		private int capacidad, pasajeros;
		private boolean puedeSubir, puedeBajar, darViaje;

		private Lock lock;
		private Condition condicionSubida, condicionBajada, conditionViaje;

		public Tiovivo(int cantidad) {
			capacidad = cantidad;
			pasajeros = 0;
			puedeBajar = false;
			puedeSubir = true;
			darViaje = false;
			lock = new ReentrantLock();
			condicionBajada = lock.newCondition();
			condicionSubida = lock.newCondition();
			conditionViaje = lock.newCondition();
		}
	public void subir(int id) throws InterruptedException
	{
		lock.lock();
		try{
			while (!puedeSubir) {
				condicionSubida.await();
			}
			pasajeros++;
			System.out.println("El pasajero "+ id +" ha subido");
			if(pasajeros == capacidad) {
				puedeSubir = false;
				darViaje = true;
				conditionViaje.signal();
			}

		} finally {
			lock.unlock();
		}
	}
	
	public void bajar(int id) throws InterruptedException
	{
		lock.lock();
		try{
			while (!puedeBajar) {
				condicionBajada.await();
			}
			pasajeros--;
			System.out.println("\t\t\tEl pasajero "+id+" ha bajado");
			if(pasajeros == 0) {
				puedeSubir = true;
				puedeBajar = false;
				condicionSubida.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}
	
	public void esperaLleno () throws InterruptedException
	{
		lock.lock();
		try{
			while (!darViaje) {
				conditionViaje.await();
			}
			System.out.println("****** EMPIEZA EL VIAJE ******\n\n\n");
		} finally {
			lock.unlock();
		}
	}
	
	public void finViaje () 
	{
		lock.lock();
		try{
			System.out.println("^^^^^ EL VIAJE HA FINALIDO ^^^^^");
			puedeBajar = true;
			darViaje = false;
			condicionBajada.signalAll();
		}finally {
			lock.unlock();
		}
	}
}

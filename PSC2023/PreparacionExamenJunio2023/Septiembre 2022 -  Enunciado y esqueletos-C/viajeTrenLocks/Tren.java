package viajeTren;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tren {
	private Lock lock;
	private Condition pasajerosCondition;
	private Condition maquinistaCondition;
	private int pasajerosEnVagon;
	public Tren(){
		lock = new ReentrantLock();
		pasajerosCondition = lock.newCondition();
		maquinistaCondition = lock.newCondition();
		pasajerosEnVagon = 0;
	}
	public void viaje(int id) throws InterruptedException {
		lock.lock();
		if(pasajerosEnVagon < 10){
			pasajerosEnVagon++;
			System.out.println("Pasajero "+id+" ha subido al vagon");
		}
		if(pasajerosEnVagon == 2*5){
			maquinistaCondition.signal();
		}
		try{
			pasajerosCondition.await();
		}finally {
			lock.unlock();
		}
		System.out.println("Pasajero "+id+" esta haciendo el viaje");
		Thread.sleep(2000);
		lock.lock();
		if(id < 10){
			pasajerosEnVagon--;
			System.out.println("Pasajero "+id+" ha bajado del vagon");

		}
		lock.unlock();
	}

	public void empiezaViaje() throws InterruptedException {
		lock.lock();
 		maquinistaCondition.await();
		System.out.println("Maquinista: empieza el viaje");
		Thread.sleep(1000);
		lock.unlock();
	}
	public void finViaje() throws InterruptedException  {
		lock.lock();
		if(pasajerosEnVagon == 0){
			pasajerosCondition.signal();
		}
		System.out.println("        Maquinista:  fin del viaje");
		lock.unlock();
	}
}

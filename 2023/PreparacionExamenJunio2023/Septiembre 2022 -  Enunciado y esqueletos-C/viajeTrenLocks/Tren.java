package viajeTren;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tren {

	private int TAM_VAGON = 5;
	private int asientosVagon1Restantes = TAM_VAGON;
	private int asientosVagon2Restantes = TAM_VAGON;
	private Lock lock = new ReentrantLock();
	private Condition cVagon1 = lock.newCondition();
	private boolean esperaVagon1 = true;

	private Condition cVagon2 = lock.newCondition();
	private boolean esperaVagon2 = true;

	private Condition cEsperaPasajeros = lock.newCondition();
	private boolean enViaje = false;

	private Condition cEsperaMaquinista = lock.newCondition();
	private boolean bajando = false;
	private boolean montandose = true;



	public void viaje(int id) throws InterruptedException {
		lock.lock();
		try {
			while(enViaje) {
				cEsperaPasajeros.await(); // Se bloquea a los pasajeros
			}

			if(asientosVagon1Restantes > 0) {
				asientosVagon1Restantes--;
				System.out.println("Pasajero " + id + " ha subido al vagon 1");
				while (esperaVagon1){
					cVagon1.await(); // Se bloquea el vagon 1
				}

				asientosVagon1Restantes++;
				System.out.println("Pasajero "+id+" ha subido al vagon 1");

				if(asientosVagon1Restantes == TAM_VAGON) {
					esperaVagon2 = false;
					cVagon2.signalAll();
					//Se avisa al vagon 2 para que empicen a entrar
				}
			}else{ //Vagon 2
				asientosVagon2Restantes--;
				System.out.println("Pasajero " + id + " ha subido al vagon 2");
				if(asientosVagon2Restantes == 0) {
					montandose = false;
					enViaje = true;
					cEsperaMaquinista.signal();
				}

				while (esperaVagon1){
					cVagon2.await();
				}

				asientosVagon2Restantes++;
				System.out.println("Pasajero " + id + " ha bajado del vagon 2");

				if(asientosVagon2Restantes == TAM_VAGON) {
					enViaje = false;
					montandose = true;
					bajando = false;
					esperaVagon1 = true;
					esperaVagon2 = true;
					cEsperaPasajeros.signalAll();
					System.out.println("*++++++++++++++++++++++*");
				}
			}
		} finally {
			lock.unlock();
		}
	}

	public void empiezaViaje() throws InterruptedException {
		lock.lock();
		try {
			while (montandose || bajando) {
				cEsperaMaquinista.await();
			}
			System.out.println("Maquinista: Empieza el viaje");
		} finally {
			lock.unlock();
		}
	}

	public void finViaje() throws InterruptedException {
		lock.lock();
		try {
			System.out.println("Maquinista: fin del viaje");
			bajando = true;
			esperaVagon1 = false;
			cVagon1.signalAll();
		} finally {
			lock.unlock();
		}
	}
}

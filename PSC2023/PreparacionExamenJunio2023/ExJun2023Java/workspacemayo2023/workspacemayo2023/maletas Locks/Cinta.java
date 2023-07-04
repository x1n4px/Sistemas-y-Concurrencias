package maletas;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cinta {
	private int maletasP = 0, maletasT = 0;
	private Lock lockCinta;
	private Lock lockMaletas;
	private Condition hayPrimera;
	private Condition hayTurista;
	private boolean PrimeraQuiereRetirar = false;

	public Cinta() {
		lockMaletas = new ReentrantLock();
		lockCinta = new ReentrantLock();
		hayPrimera = lockMaletas.newCondition();
		hayTurista = lockMaletas.newCondition();
	}
	
	public void poner(boolean primeraClase) throws InterruptedException {
		lockMaletas.lock();
		try{
			if(primeraClase){
				maletasP++;
				System.out.println("Generador: maleta de primera. maletasP: " +maletasP +" maletasT: " +maletasT);
				if(maletasP == 1) {
					hayPrimera.signalAll();
				}
			}else{
				maletasT++;
				System.out.println("Generador: maleta de turista. maletasP: " +maletasP +" maletasT: " +maletasT);
				if(maletasT ==1){
					hayTurista.signalAll();
				}
 			}
		}finally {
			lockMaletas.unlock();
		}
	}
	

	public void qRetirarPrimera(int pasajeroId) throws InterruptedException {
		lockMaletas.lock();
		try{
			PrimeraQuiereRetirar = true;
			if(maletasP == 0) {
				hayPrimera.await();
			}
			System.out.println("El pasajero " + pasajeroId + " puede retira una maleta de primera clase. Maletas Primera: " + maletasP);

		}finally {
			lockMaletas.unlock();
		}
	}
	
	public void qRetirarTurista(int pasajeroId) throws InterruptedException {
		lockMaletas.lock();
		try{
			if(maletasT == 0 || PrimeraQuiereRetirar) {
				hayTurista.await();
			}
			System.out.println("El pasajero " + pasajeroId + " puede retira una maleta de Turista clase. Maletas Primera: " + maletasT);

		}finally {
			lockMaletas.unlock();
		}
	}
	
	public void fRetirarPrimera(int pasajeroId) throws InterruptedException {
		System.out.println("***Pas. Primera " + pasajeroId + " puede coger la maleta.");
		lockMaletas.lock();
		try{
			while(maletasP == 0) {
				hayPrimera.await();
			}
			maletasP--;
			PrimeraQuiereRetirar = false;
			if(maletasP == 0 && maletasT > 0) {
 				hayTurista.signalAll();
			}else{
				hayPrimera.signalAll();
			}
		}finally {
			lockMaletas.unlock();
		}
	}
	public void fRetirarTurista(int pasajeroId) throws InterruptedException {
		System.out.println("***Pas. Turista " + pasajeroId + " puede coger la maleta.");
		lockMaletas.lock();
		try{
			while(maletasT == 0) {
				hayTurista.await();
			}
			maletasT--;
			if(maletasP == 0 && maletasP > 0) {
				hayPrimera.signalAll();
 			}else{
				if(maletasP > 0 && PrimeraQuiereRetirar){
					hayPrimera.signalAll();
				}else{
					hayTurista.signalAll();
				}

			}
		}finally {
			lockMaletas.unlock();
		}
	}

}

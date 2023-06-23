package maletas;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cinta {

	private int maletasPrimera, maletasTurista, primeraEsperando;
	private boolean pasajeroRetirando;
	private Lock lock;
	private Condition primeraDisponible;
	private Condition turistaDisponible;
	public Cinta() {
		maletasPrimera = 0;
		maletasTurista = 0;
		primeraEsperando = 0;
		pasajeroRetirando = false;
		lock = new ReentrantLock();
		primeraDisponible = lock.newCondition();
		turistaDisponible = lock.newCondition();
	}
	
	public void poner(boolean primeraClase) throws InterruptedException {
		lock.lock();
		try{
			//Si (entra una maleta de Primera clase)
			if(primeraClase) {
				//Aumentamos el numero de maletas de Primera clase
				maletasPrimera++;
				//Si hay alguien de Primera esperando por su maleta
				if(primeraEsperando >  0) {
					//Le avisamos
					primeraDisponible.signal();
				}
			}else{
				//Si (entra una maleta de clase Turista)
				maletasTurista++;
			}
			System.out.println("Se ha puesto una maleta en la cinta.");
		}finally {
			lock.unlock();
		}
	}
	

	public void qRetirarPrimera(int pasajeroId) throws InterruptedException {
		lock.lock();
		try{
			//Hay alguien de Primera clase esperando
			primeraEsperando++;
			//Mientras (no haya maletas de Primera clase ó haya alguien recogiendo su maleta)
			while (maletasPrimera == 0 || pasajeroRetirando) {
				//los de Primera clase esperan
				primeraDisponible.await();
			}
			//el pasajero retira su maleta
			pasajeroRetirando = true;
			System.out.println("El pasajero " + pasajeroId + " retira una maleta de primera clase.");

		}finally {
			//retiramos un pasajero de Primera clase que estaba esperando por su maleta
			primeraEsperando--;
			lock.unlock();
		}
	}
	
	public void qRetirarTurista(int pasajeroId) throws InterruptedException {
		lock.lock();
		try{
			//Mientras(no haya maleta Turista ó haya alguien de Primera clase esperando ó haya otro pasajero recogiendo su maleta)
 			while (maletasTurista == 0 || primeraEsperando > 0 || pasajeroRetirando) {
				 //pasajero de clase Turista espera
				turistaDisponible.await();
			}
			 //el pasajero ya puede retirar su maleta
			 pasajeroRetirando = true;
			System.out.println("El pasajero " + pasajeroId + " retira una maleta de clase turista.");
		}finally {
			lock.unlock();
		}
	}
	
	public void fRetirarPrimera(int pasajeroId) throws InterruptedException {
		lock.lock();
		try{
			//El pasajero de Primera clase va a retirar su maleta
			maletasPrimera--;
			pasajeroRetirando = false;
			//Avisa a todos los pasajeros de que pueden recoger su maleta, ya que nadie espera
			turistaDisponible.signal();
			primeraDisponible.signal();
			System.out.println("El pasajero " + pasajeroId + " ha recogido su maleta de primera clase.");
		}finally {
			lock.unlock();
		}
	}
	public void fRetirarTurista(int pasajeroId) throws InterruptedException {
		lock.lock();
		try {
			//El pasajero de clase Turista recoge su maleta
			maletasTurista--;
			pasajeroRetirando = false;
			//Se avisan a los de primera clase de que pueden recoger su maleta
			primeraDisponible.signal();
			System.out.println("El pasajero " + pasajeroId + " ha recogido su maleta de clase turista.");
		} finally {
			lock.unlock();
		}
	}

}

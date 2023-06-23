package maletas;

import java.util.concurrent.Semaphore;

public class Cinta {

	private int maletasPrimera, maletasTurista;
	private Semaphore mutex;
	private Semaphore primeraDisponible;
	private Semaphore turistaDisponible;
	private Semaphore primeraEsperando;
	private Semaphore turistaEsperando;

	public Cinta() {
		maletasTurista = 0;
		maletasPrimera = 0;
		mutex = new Semaphore(1);
		primeraDisponible = new Semaphore(0);
		turistaDisponible = new Semaphore(0);
		primeraEsperando = new Semaphore(0);
		turistaEsperando = new Semaphore(0);

	}
	
	public void poner(boolean primeraClase) throws InterruptedException {
		mutex.acquire();
		if(primeraClase) {
			maletasPrimera++;
			if(primeraEsperando.availablePermits() > 0) {
				primeraDisponible.release();
			}
			System.out.println("Se ha puesto una maleta de primera en la cinta. " + maletasPrimera);

		}else{
			maletasTurista++;
			if (turistaEsperando.availablePermits() > 0 && primeraEsperando.availablePermits() == 0) {
				turistaDisponible.release(); // Señalizar a un pasajero de clase turista
			}
			System.out.println("Se ha puesto una maleta de turista en la cinta. " + maletasTurista);
		}

		mutex.release();
	}
	

	public void qRetirarPrimera(int pasajeroId) throws InterruptedException {
		primeraEsperando.release();
		primeraDisponible.acquire();
		mutex.acquire();
		maletasPrimera--;
		System.out.println("El pasajero " + pasajeroId + " retira una maleta de primera clase. Maletas Primera: " + maletasPrimera);
		mutex.release();
	}

	public void qRetirarTurista(int pasajeroId) throws InterruptedException {
		turistaEsperando.release();
		turistaDisponible.acquire();
		mutex.acquire();
		maletasTurista--;
		System.out.println("El pasajero " + pasajeroId + " retira una maleta de clase turista. Maletas Turista: " + maletasTurista);
		mutex.release();
	}
	
	public void fRetirarPrimera(int pasajeroId) throws InterruptedException {

		mutex.acquire();
		System.out.println("El pasajero " + pasajeroId + " ha recogido su maleta de primera clase. Maletas Primera: " + maletasPrimera);
		mutex.release(); 	}
	public void fRetirarTurista(int pasajeroId) throws InterruptedException {

		mutex.acquire();
		System.out.println("El pasajero " + pasajeroId + " ha recogido su maleta de clase turista. Maletas Turista: " + maletasTurista);
		mutex.release(); 	}

}

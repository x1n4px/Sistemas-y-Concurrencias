package maletas;

import java.util.concurrent.Semaphore;

public class Cinta {

	private int maletasP, maletasT;
 	private Semaphore mutexMaletas, mutexCinta, hayPrimera, hayTurista;

	public Cinta() {
		maletasT = 0;
		maletasP = 0;
		mutexMaletas = new Semaphore(1, true);
		mutexCinta = new Semaphore(1, true);
		hayPrimera = new Semaphore(0, true);
		hayTurista = new Semaphore(0, true);

	}
	
	public void poner(boolean primeraClase) throws InterruptedException {
		mutexMaletas.acquire();
		if(primeraClase) {
			maletasP++;
			System.out.println("Generador: maleta de primera. maletasP: " +maletasP +" maletasT: " +maletasT);
			if(maletasP ==1)
				hayPrimera.release();
		}else {
			maletasT++;
			System.out.println("Generador: maleta de turista. maletasP: " +maletasP +" maletasT: " +maletasT);
			if(maletasT ==1)
				hayTurista.release();
		}
		mutexMaletas.release();
	}
	

	public void qRetirarPrimera(int pasajeroId) throws InterruptedException {
		hayPrimera.acquire(); //Solo entra uno de primera
		mutexCinta.acquire(); //Solo entra uno de los dos
		System.out.println("El pasajero " + pasajeroId + " puede retira una maleta de primera clase. Maletas Primera: " + maletasP);
 	}

	public void qRetirarTurista(int pasajeroId) throws InterruptedException {
		hayTurista.acquire();
		mutexCinta.acquire();
		System.out.println("El pasajero " + pasajeroId + " puede retira una maleta de Turista clase. Maletas Primera: " + maletasT);


	}
	
	public void fRetirarPrimera(int pasajeroId) throws InterruptedException {
		System.out.println("***Pas. Primera " + pasajeroId + " puede coger la maleta.");
		mutexMaletas.acquire(); // mutex con la hebra generador
		maletasP--;
		if (maletasP>0){
			mutexMaletas.release();
			mutexCinta.release();
			hayPrimera.release(); //Se quedan maletas, dejamos entrar a otro.
		}else{
			mutexMaletas.release();
			mutexCinta.release();
		}
	}
	public void fRetirarTurista(int pasajeroId) throws InterruptedException {
		System.out.println("***Pas. Turista " + pasajeroId + " puede coger la maleta.");
		mutexMaletas.acquire(); // mutex con la hebra generador
		maletasT--;
		if (maletasP>0){
			mutexMaletas.release();
			mutexCinta.release();
			hayPrimera.release(); //Se quedan maletas, dejamos entrar a otro.
		}else{
			mutexMaletas.release();
			mutexCinta.release();
		}
	}

}

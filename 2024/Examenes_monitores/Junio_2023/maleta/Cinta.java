package Junio_2023.maleta;

import java.util.concurrent.locks.*;

public class Cinta {

	int maletasPrimera = 0, maletasTurista = 0, primeraEsperando = 0;
	boolean hayRetirando = false;

	public void poner(boolean primeraClase) throws InterruptedException {
		if (primeraClase) {
			maletasPrimera++;
			System.out.println("Maleta de primera clase puesta");
		} else {
			maletasTurista++;
			System.out.println("Maleta de clase turista puesta");
		}
		notifyAll();
	}

	public void qRetirarPrimera(int pasajeroId) throws InterruptedException {

		System.out.println("Pasajero de primera clase " + pasajeroId + " quiere retirar su maleta");
		primeraEsperando++;
		while (maletasPrimera == 0 || hayRetirando) {
			wait();
		}
		System.out.println("Pasajero de primera clase " + pasajeroId + " ha retirado su maleta");
		hayRetirando = true;
	}

	public void qRetirarTurista(int pasajeroId) throws InterruptedException {
		while (maletasTurista == 0 || hayRetirando || primeraEsperando > 0)
			wait();
		System.out.println("Pas. turista " + pasajeroId + " entra a la cinta.");
		hayRetirando = true;
	}

	public void fRetirarPrimera(int pasajeroId) throws InterruptedException {
		maletasPrimera--;
		System.out.println("*** Pas. primera " + pasajeroId + " coge su maleta ***");

		hayRetirando = false;
		notifyAll();
	}

	public void fRetirarTurista(int pasajeroId) throws InterruptedException {
		maletasTurista--;
		System.out.println("Pas. turista " + pasajeroId + " coge su maleta");

		hayRetirando = false;
		notifyAll();
	}

}

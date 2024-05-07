package Piedra_Papel_Tijera;

import java.util.concurrent.*;

public class Mesa extends Thread {

	// 0 - piedra, 1 - papel, 2 - tijera
	private int[] jugadas = new int[3]; // num de piedras, papeles y tijeras
	private int numJugadas = 0;
	private Semaphore mutex = new Semaphore(1, true);

	private Semaphore espera = new Semaphore(0, true);// cerrado sii aun no han mostrado su jugada todos los jugadores

	private Semaphore siguiente = new Semaphore(1, true); // cerrado sii hay un jugador poniendo jugada

	public int nuevaJugada(int id, int juego) throws InterruptedException {
		int aux = -1;
		// CS: podemos jugar una nueva ronda
		siguiente.acquire();
		mutex.acquire();

		numJugadas++;
		jugadas[juego]++;
		System.out.println("Jugador " + id + " pone " + juego);

		if (numJugadas < 3) {// aun faltan jugadores
			mutex.release();
			siguiente.release();

			// CS2: bloqueo hasta que el resto pongan su jugada
			espera.acquire();
			aux = gana(); // cuando despierta en ganador ya esta actualizado ese valor
		} else { // todas las jugadas listas -> determinar ganador
					// calcula el ganador
			inicializa(); // resetea las variables del juego
			aux = gana();
		}
		// Despertar en cascada
		numJugadas--;
		if (numJugadas > 0) {
			espera.release();
		} else { // el ultimo indica quien ha ganado y abre el semaforo para la siguiente ronda
			System.out.println("FIN DEL JUEGO " + gana());
			System.out.println("*****************************");
			siguiente.release();
		}
		mutex.release();
		return aux;
	}

	private int gana() {
		if ((jugadas[0] == 2) && jugadas[1] == 1) {
			return 1;
		} else if ((jugadas[1] == 2) && jugadas[2] == 1) {
			return 2;
		} else if ((jugadas[0] == 1) && jugadas[2] == 2) {
			return 0;
		} else {
			return -1;// empate
		}
	}

	private void inicializa() {
		for (int i = 0; i < 3; i++) {
			jugadas[i] = 0;
		}
	}

}

package semaforosEsqueleto;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mesa {

	private boolean nuevaPartida, todosHanTirado, ganador;
	private int jugadores, devolver;
	private int caras, cruces, gandorCaras, ganadorCruces, tiradas;
	private final Lock lock = new ReentrantLock();
	private final Condition todosHanTiradoCondition = lock.newCondition();

	public Mesa(int N) {
		nuevaPartida = true;
		jugadores = N;
		caras = 0;
		cruces = 0;
		ganadorCruces = -1;
		gandorCaras = -1;
		tiradas = 0;
		todosHanTirado = false;
		ganador = false;
		devolver = N;
	}

	/**
	 * @param id   del jugador que llama al método
	 * @param cara : true si la moneda es cara, false en otro caso
	 * @return un valor entre 0 y N. Si devuelve N es que ningún jugador ha ganado y hay que repetir la partida.
	 * Si devuelve un número menor que N, es el id del jugador ganador.
	 * <p>
	 * Un jugador llama al método nuevaJugada cuando quiere poner el resultado de su tirada (cara o cruz) sobre la mesa.
	 * El jugador deja su resultado y, si no es el último, espera a que el resto de los jugadores pongan sus jugadas sobre la mesa.
	 * El último jugador comprueba si hay o no un ganador, y despierta al resto de jugadores
	 */
	public int nuevaJugada(int id, boolean cara) throws InterruptedException {
		lock.lock();
		try {
			while (!nuevaPartida) {
				todosHanTiradoCondition.await();
			}

			tiradas++;
			if (cara) {
				caras++;
				System.out.println("Jugador " + id + " tira cara y ya hay " + caras);
				gandorCaras = id;
			} else {
				cruces++;
				System.out.println("Jugador " + id + " tira cruz y ya hay " + cruces);
				ganadorCruces = id;
			}

			if (tiradas == jugadores && (caras == 1 || cruces == 1)) {
				ganador = true;
			}

			if (tiradas == jugadores) {
				nuevaPartida = false;
				todosHanTirado = true;
				todosHanTiradoCondition.signalAll();
			}

			while (!todosHanTirado) {
				todosHanTiradoCondition.await();
			}

			tiradas--;
			if (tiradas == 0) {
				if (ganador) {
					if (caras == 1) {
						System.out.println("-> Ganador jugador " + gandorCaras);
						devolver = gandorCaras;
						return gandorCaras;
					} else {
						System.out.println("-> Ganador jugador " + ganadorCruces);
						devolver = ganadorCruces;
						return ganadorCruces;
					}
				} else {
					System.out.println("     NUEVA PARTIDA      ");
					nuevaPartida = true;
					todosHanTirado = false;
					todosHanTiradoCondition.signalAll();
					caras = 0;
					cruces = 0;
				}
			}
			return devolver;
		} finally {
			lock.unlock();
		}
	}
}

package juego;

import java.util.concurrent.*;

public class Mesa {
	// 0 - piedra, 1 - papel, 2 - tijeras
	int N = 3;
	int numJugadas;
	int[] jugadas;
	Semaphore puedoAnalizar = new Semaphore(0, true);
	Semaphore puedoTirar = new Semaphore(1, true);

	public Mesa() {
		numJugadas = 0;
		jugadas = new int[N];
	}

	/**
	 * 
	 * @param jug   jugador que llama al m�todo (0,1,2)
	 * @param juego jugada del jugador (0-piedra,1-papel, 2-tijeras)
	 * @return si ha habido un ganador en esta jugada se devuelve
	 *         la jugada ganadora
	 *         o -1, si no ha habido ganador
	 * @throws InterruptedException
	 * 
	 *                              El jugador que llama a este m�todo muestra su
	 *                              jugada, y espera a que
	 *                              est�n la de los otros dos.
	 *                              Hay dos condiciones de sincronizaci�n
	 *                              CS1- Un jugador espera en el m�todo hasta que
	 *                              est�n las tres jugadas
	 *                              CS2- Un jugador tiene que esperar a que finalice
	 *                              la jugada anterior para
	 *                              empezar la siguiente
	 * 
	 */
	public int nuevaJugada(int jug, int juego) throws InterruptedException {
		puedoTirar.acquire();
		int jugadorGanador = N;
		numJugadas++;
		jugadas[jug] = juego;

		if (juego == 0) {
			System.out.println("Jugador " + jug + " tira piedra. Jugadas restantes: " + (numJugadas));
		} else if (juego == 1) {
			System.out.println("Jugador " + jug + " tira papel. Jugadas restantes: " + (numJugadas));
		} else {
			System.out.println("Jugador " + jug + " tira tijeras. Jugadas restantes: " + (numJugadas));
		}

		if (numJugadas < N) { // Aun falta gente por tirar
			puedoTirar.release();
			puedoAnalizar.acquire();
		} else {
			int ganador = seleccionarGanador(jugadas);
			if (ganador == N) {
				System.out.println("Empate");
			} else {
				int jugador = 0;
				for (int i = 0; i < jugadas.length; i++) {
					if (jugadas[i] == ganador) {
						jugador = i;
						break;
					}
				}
				System.out.println("Jugador " + jugador + " gana la partida");
			}

		}

		jugadorGanador = seleccionarGanador(jugadas);
		if (numJugadas > 1) {
			numJugadas--;
			System.out.println("El jugador " + jug + " se va. Quedan: " + numJugadas);
			puedoAnalizar.release();
		} else {
			numJugadas--;
			System.out.println("El jugador " + jug + " se va. Quedan: " + numJugadas);
			puedoTirar.release();
		}

		return 0;
	}

	public int seleccionarGanador(int[] jugadas) {

		int nPiedra = 0, nPapel = 0, nTijeras = 0;

		for (int i = 0; i < jugadas.length; i++) {
			if (jugadas[i] == 0) {
				nPiedra++;
				if (nPiedra == 1)
					return 0; // Jugada ganadora: Piedra
			} else if (jugadas[i] == 1) {
				nPapel++;
				if (nPapel == 1)
					return 1; // Jugada ganadora: Papel
			} else {
				nTijeras++;
				if (nTijeras == 1)
					return 2; // Jugada ganadora: Tijeras
			}
		}

		return jugadas.length;

	}
}

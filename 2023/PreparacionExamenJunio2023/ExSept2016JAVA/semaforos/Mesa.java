package semaforosEsqueleto;

import java.util.concurrent.*;
public class Mesa {

	int N;
	int nJugadas;
	boolean[] jugadas;
	Semaphore puedeEntrar, analizarJugada;

	public Mesa(int N){
		this.N = N;
		nJugadas = 0;
		jugadas = new boolean[N];
		puedeEntrar = new Semaphore(1);
		analizarJugada = new Semaphore(0);
	}
	
	
	
	/**
	 * 
	 * @param id del jugador que llama al m�todo
	 * @param cara : true si la moneda es cara, false en otro caso
	 * @return un valor entre 0 y N. Si devuelve N es que ning�n jugador 
	 * ha ganado y hay que repetir la partida. Si  devuelve un n�mero menor que N, es el id del
	 * jugador ganador.
	 * 
	 * Un jugador llama al m�todo nuevaJugada cuando quiere poner
	 * el resultado de su tirada (cara o cruz) sobre la mesa.
	 * El jugador deja su resultado y, si no es el �ltimo, espera a que el resto de 
	 * los jugadores pongan sus jugadas sobre la mesa.
	 * El �ltimo jugador comprueba si hay o no un ganador, y despierta
	 * al resto de jugadores
	 *  
	 */
	public int nuevaJugada(int id,boolean cara) throws InterruptedException{
		//PONER JUGADAS
		puedeEntrar.acquire();
		nJugadas++;
		jugadas[id] = cara;

		if(cara) System.out.println("El jugador "+id+" ha sacado Cara. Jugadas: "+nJugadas+ "/"+N);
		else System.out.println("El jugador "+id+" ha sacado Cruz. Jugadas: "+nJugadas+ "/"+N);

		//ANALIZAR JUGADAS
		if(nJugadas < N) {
			//Si no han jugado todos esperan
			puedeEntrar.release();
			analizarJugada.acquire();
		}else{
			//El ultimo analiza la jugada
			if(seleccionarGanador(jugadas) == N) {
				System.out.println("\t\t\t El jugador "+id+" analiza la jugada. No gana nadie");
			}else{
				System.out.println("\t\t\t El jugador "+id+" analiza la jugada. Ha ganado el jugador "+ seleccionarGanador(jugadas));

			}
		}

		//LOS JUGADORES SE VAN
		if(nJugadas > 1) {
			//Si no es el ultimo en irse
			nJugadas--;
			System.out.println(" El jugador "+ id +" se va. Quedan "+nJugadas+"/"+N);
			//DESPERTAR A LOS JUGADORES EN CASCADA
			analizarJugada.release();
		}else{
			//Si es el ultimo en irse
			nJugadas--;
			System.out.println(" El jugador "+ id +" se va. Quedan "+nJugadas+"/"+N);
			//Hay que despertar a los nuevos jugadores en el caso de que nadie haya ganado
			puedeEntrar.release();
		}
		return seleccionarGanador(jugadas);
	}

	private int seleccionarGanador(boolean[] jugadas) {
		int nCara = 0, nCruz = 0, ganadorCara = 0, ganadorCruz = 0;
		for (int i=0; i< N; i++) {
			if(jugadas[i]) {
				nCara++;
				ganadorCara = i;
			}else{
				nCruz++;
				ganadorCruz = i;
			}
		}
		if(nCara == 1) {
			return ganadorCara;
		}else if(nCruz == 1) {
			return ganadorCruz;
		}else{
			return N;
		}
	}
}

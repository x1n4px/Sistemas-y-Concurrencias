package juego;

import java.util.concurrent.*;
public class Mesa {
    //0 - piedra, 1 - papel, 2 - tijeras
	
	/**
	 * 
	 * @param jug jugador que llama al método (0,1,2)
	 * @param juego jugada del jugador (0-piedra,1-papel, 2-tijeras)
	 * @return  si ha habido un ganador en esta jugada se devuelve 
	 *          la jugada ganadora
	 *         o -1, si no ha habido ganador
	 * @throws InterruptedException
	 * 
	 * El jugador que llama a este método muestra su jugada, y espera a que 
	 * estén la de los otros dos. 
	 * Hay dos condiciones de sincronización
	 * CS1- Un jugador espera en el método hasta que estén las tres jugadas
	 * CS2- Un jugador tiene que esperar a que finalice la jugada anterior para
	 *     empezar la siguiente
	 * 
	 */
	public int nuevaJugada(int jug,int juego) throws InterruptedException{
		
		return 0;
	}
}

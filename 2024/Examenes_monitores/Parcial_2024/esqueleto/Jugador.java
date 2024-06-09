package Parcial_2024.esqueleto;

public class Jugador extends Thread {
	private Torneo c; //Recurso compartido
	private int id;   //Identificador del jugador
	
	public Jugador(int i, Torneo c) {
		this.id = i;
		this.c = c;
	}

	@Override
	public void run() {
		try {
			boolean finalizar = false, equipoFormado;
			while (!finalizar){
				/* El jugador entra a la pista de padel para jugar un partido
				 * 	- Devuelve true si se ha podido formar un equipo de 4 jugadores
				 *  - Devuelve false si no ha sido posible formar un equipo de 4 jugadores
				 */
				equipoFormado = c.entrarEnPista(id);
				if (equipoFormado) { 
					//Si se ha podido formar equipo se juega el partido
					Thread.sleep(1000);
					/* El jugador indica que el partido ha finalizado
					 * 	- Devuelve true si el torneo ha finalizado
					 *  - Devuelve false si el torneo aun no ha finalizado
					 */ 
					finalizar = c.finPartido(id);
				}else {
					finalizar = true;
				}
			};
		} catch (InterruptedException e) { e.printStackTrace(); }
	}

}

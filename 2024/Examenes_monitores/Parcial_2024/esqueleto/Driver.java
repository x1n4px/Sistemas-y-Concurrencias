package Parcial_2024.esqueleto;
public class Driver {

	public static void main(String[] args) throws InterruptedException {
		final int MAXJug = 10;
		Torneo tp = new Torneo(MAXJug); //Crea un torneo con MAXJug jugadores
		
		//Crea MAXJug jugadores e inicia la ejecución de las hebras
		Jugador jugadores [] = new Jugador[MAXJug];
		for (int i=0; i<MAXJug; i++) {
			jugadores[i] = new Jugador(i, tp);
			jugadores[i].start();
		}
		
		//Se espera a que terminen todas las hebras
		for (int i=0; i<MAXJug; i++) {
			jugadores[i].join();
		}		
	}
}
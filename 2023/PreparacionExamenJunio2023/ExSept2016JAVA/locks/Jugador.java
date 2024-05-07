package semaforosEsqueleto;

import java.util.Random;

public class Jugador extends Thread{
	
	private static Random r = new Random();
	private Mesa mesa;
	private int id, N;
	
	
	public Jugador(int id,Mesa mesa, int N){
		this.id = id;
		this.mesa = mesa;
		this.N = N;
	}

	
	public void run(){
		boolean cara = r.nextBoolean();
		int ganador = N;
		while (ganador == N){
			try {
				ganador = mesa.nuevaJugada(id, cara);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			cara = r.nextBoolean();
		}

	}
}

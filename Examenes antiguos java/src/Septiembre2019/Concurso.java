package Septiembre2019;

import java.util.concurrent.Semaphore;

public class Concurso {

	private static final int NINGUN_GANADOR = -1;
	
	private int[] numTiradas;//numTiradas[id] = nº veces que ha tirado la moneda Jugadorid en este juego
	private int[] numCaras;//numCaras[id] = nºveces que le ha salido cara a Jugadorid en este juego
	private Semaphore mutex; //protege a numTiradas y numCaras
	
	private Semaphore esperando;//Donde se bloquea una hebra esperando que la otra agote sus tiradas
	
	private int[] numJuegos;//numJuegos[id] = nºjuegos ganados por Jugadorid
	
	public Concurso() {
		this.numTiradas = new int[2];//---ningun jugador ha tirado
		this.numCaras = new int[2];//--ningun jugador ha obtenido cara
		
		this.mutex = new Semaphore(1);//--se puede acceder a las puntuaciones
		this.esperando = new Semaphore(0);//--Para que la primera hebra que complete sus tiradas se bloquee
		
		this.numJuegos = new int[2];//--Ningun jugador ha ganado ningun juego
	}
	
	
	
	public void tirarMoneda(int id,boolean cara) throws InterruptedException {
		mutex.acquire();
		
		numTiradas[id]++;
		
		if(cara) numCaras[id]++;
		
		if(numTiradas[id]<10) {//Me falta por tirar
			mutex.release();
		}else {
			if(numTiradas[0]+numTiradas[1] == 20	) {//el otro tambien las ha hecho todas sus tiradas
				int ganador = calculaGanador();
				System.out.println("Juego "+(numJuegos[0]+numJuegos[1]+1)+": ");
				if(ganador != NINGUN_GANADOR) {
					System.out.println("Ha ganado la partida el jugador "+ganador+" con "+numCaras[ganador]+" caras");
					numJuegos[ganador]++;
				}else {
				System.out.println("El juego ha empatado");
			}
			//reinicia el juego
			numTiradas[0] =0;
			numTiradas[1] = 0;
			numCaras[0] = 0;
			numCaras[1]=0;
			
			esperando.release();//Para despertar a la otra
			mutex.release();
		}else {
			mutex.release();
			esperando.acquire();
		}
		}
	}	
	
	



	private int calculaGanador() {
		if(numCaras[0]> numCaras[1]) {
			return 0;
		}else if(numCaras[1]>numCaras[0]) {
			return 1;
		
		}else {
			return NINGUN_GANADOR;
		}
		
	}



	public boolean concursoTerminado()throws InterruptedException {
		mutex.acquire();
		if(numJuegos[0]-numJuegos[1] >= 3) {
			System.out.println("Final del concurso. Ha ganado el jugador 0");
			mutex.release();
			return true;
		}else if(numJuegos[1]-numJuegos[0] >= 3) {
			System.out.println("Final del concurso. Ha ganado el jugador 1");
			mutex.release();
			return true;
		}else {
			mutex.release();
			return false;
		}
	}
}
package paseo_2v;

import java.util.concurrent.Semaphore;

public class Barca {
	private int N;
	private int maxMenor;
	private int numMenor = 0, numMayor = 0;

	private Semaphore mutex = new Semaphore(1);
	private Semaphore subirMenor = new Semaphore(1);
	private Semaphore subirMayor = new Semaphore(0);
	private Semaphore barquero = new Semaphore(0);
	private Semaphore bajaMenor = new Semaphore(0);
	private Semaphore bajaMayor = new Semaphore(0);



	public Barca(int N,int menoresEnBarca) {
		this.N = N;
		this.maxMenor=menoresEnBarca;
 	}
	/*
	 *
	 * Nuevo para la segunda versión:
	 * El menor id no puede subirse a la Barca hasta que haya sitio para él,
	 * y se hayan bajado los pasajeros del paseo anterior (si no es el primer paseo)
	 *
	 * El menor id se sube a la Barca. Si es el último pasajero avisa al Barquero
	 * de que la Barca está completa.
	 * A continuación, espera hasta que finalice el viaje.
	 * Cuando lo avisan se baja de la Barca
	 *
	 *
	 *
	 */
	public void  subeMenor(int id) throws InterruptedException {
 		subirMenor.acquire(); //bloqueas subir menor
		 mutex.acquire(); //nadie moleste
			 numMenor++; //aumentamos el numero de menores
			 System.out.println("El menor "+id+" se ha subido a la barca ");
			 if(numMenor != maxMenor){ //Si aun puede entrar un menor
				 subirMenor.release(); //Desbloqueamos a subirMenor
			 }else{
				 subirMayor.release(); //Si ya no pueden entrar mas menores, dejamos entrar a los mayores
			 }
		mutex.release();
		 bajaMenor.acquire();
		 mutex.acquire();
			 numMenor--;
			 System.out.println("El menor "+id+" se ha bajado de la barca");
			 if(numMenor == 0){
				 bajaMayor.release();
			 }else{
				 bajaMenor.release();
			 }
		 mutex.release();
	}

	/*
	 *
	 * Nuevo para la segunda versión:
	 * El Adulto id no puede subirse a la Barca hasta que haya sitio para él y
	 *  -se han bajado los pasajeros del paseo anterior (si no es el primer paseo)
	 *  -se han subido los numMenor menores a la Barca
	 *
	 * El adulto id se sube a la Barca. Si es el último pasajero avisa al Barquero
	 * de que la Barca está completa.
	 * A continuación, espera hasta que finalice el viaje.
	 * Cuando lo avisan se baja de la Barca
	 */
	public void subeAdulto(int id)   throws InterruptedException{
		subirMayor.acquire();
		 mutex.acquire();
		 numMayor++;
		System.out.println("El adulto "+id+" se ha subido a la barca");
		if(numMayor+numMenor == N){
			barquero.release();
		}else{
			subirMayor.release();
		}
		 mutex.release();

		 bajaMayor.acquire();
		 mutex.acquire();
		 numMayor--;
		System.out.println("El adulto "+id+" se ha bajado de la barca");
		 if(numMayor == 0){
			 subirMenor.release();
		 }else{
			 bajaMayor.release();
		 }
		mutex.release();
	}
	/*
	 * El barquero espera hasta que la Barca tenga N pasajeros para
	 * comenzar el viaje
	 */
	public void esperoLleno()  throws InterruptedException {
		barquero.acquire();
		mutex.acquire();
		System.out.println("Comienza el viaje!!!");
		//TODO
	}

	/*
	 * Cuando termina el viaje avisa a los pasajeros que se pueden bajar.
	 * Debe garantizarse que siempre se bajan primero los pasajeros menores.
	 */
	public void finViaje()  throws InterruptedException{
 		System.out.println("Fin del viaje!!!");
		 bajaMenor.release();
		 mutex.release();
	}
}

//CS-2v-Menor: Tiene que esperar si no hay sitio libre, o si aún no se han bajado los pasajeros del paseo anterior (si no es el primer paseo).
//CS-2v-Adulto: No puede subirse a la Barca hasta que no se han subido los Menores.


package Ejercicio3;
import java.util.concurrent.Semaphore;


public class Nido {
	private int plato;
	private final int B = 6;
	//
	private Semaphore mutex;
	private Semaphore hayHueco; //sinc pajaros adultos
	private Semaphore hayBicho;  //sinc polluelos 
	
	
	public Nido() {
		plato = 0;
		hayHueco = new Semaphore(1, true);
		hayBicho = new Semaphore(0,true);
		mutex = new Semaphore(1, true);
	}
	
	public void depositarBicho(int id) throws InterruptedException { //Pajaros adultos
		//pre
		hayHueco.acquire();
		mutex.acquire();
		//SC
		plato++;
		System.out.println("Pajaro "+id+" pone bicho "+ plato);
		//post
		if(plato < B) // podemos seguir poniendo bicho
			hayHueco.release();
		if(plato == 1)// el plato antes estaba vacio y el semaforo hayBichos estaba cerrado
			hayBicho.release();
		mutex.release();
	}
	
	
	public void comerBicho(int id) throws InterruptedException {
		//pre
		hayBicho.acquire();
		mutex.acquire();
		//SC
		plato--;
		System.out.println("Polluelo "+id+" come. Plato "+plato);
		//Post
		if(plato > 0)
			hayBicho.release();
		if(plato ==B -1)
			hayHueco.release();
		
		mutex.release();
		
	}	
	public static void main(String[] args) {
		Nido n = new Nido();
		Pajaro p0 = new Pajaro(0, n);
		Pajaro p1 = new Pajaro(1, n);
		Polluelo polluelos [] = new Polluelo[5];
		for(int i = 0; i < 5; i++)
			polluelos[i] = new Polluelo(i, n);
		
		p0.start(); p1.start();
		for(int i = 0; i < 5; i++)
			polluelos[i].start();

		
		
	}

}

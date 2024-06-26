package Prod_Cons_Sem_gen;

import java.util.concurrent.Semaphore;

public class Buffer {

	private int p;  // posicion por la que se va produciendo
	private int c; //posicion por la que se va consumiendo
	private int[] elem;
	private int numHuecos; //numero de huecos -> numeroDatos = elem.length -numHuecos
	
	Semaphore hayEspacio; // Bin - condicion de sincronizacion del productor
	Semaphore hayDatos; // Bin - condicion de sincronizacion del consumidor
	Semaphore mutex; //Bin - acceso en exclusion mutua al array circular y tambien al numHuecos
	
	public Buffer(int N) {
		p = 0;
		c = 0;
		elem = new int[N];
		numHuecos = N; //array vacio ==> numDatos = 0
		hayEspacio = new Semaphore(N,true);//binario-bloquea cuando nHuecos = 0
		hayDatos = new Semaphore(0,true); //binario - bloquea cuando nDatos == 0 ==> nHuecos == N
		mutex = new Semaphore(1,true);
	}
	
	//PRODUCTOR
	public void producir(int e)throws InterruptedException{
		hayEspacio.acquire(); //si avanza es que el array no esta lleno
		mutex.acquire();
		//aqui mutex es 0
		//SC
		elem[p] = e;
		System.out.println("Produce "+e+" ,pos: "+p);
		p = (p+1)%elem.length;
		//
		mutex.release();
		//aqui mutex es 1
		hayDatos.release();//Indica al consumidor que hay un dato mas
	}
	
	
	public int extraer()throws InterruptedException{
		int elemento;
		
		hayDatos.acquire();//si avanza es que el array no esta vacio
		
		mutex.acquire();
		
		//SC
		elemento = elem[c];
		System.out.println("\t\tConsume "+elemento+" ,pos: "+c);
		c = (c+1)%elem.length;
		//
		mutex.release();
		//aqui mutex es 1
		hayEspacio.release();//indica al productor que hay un hueco mas
		return elemento;
		
		
		
	}
	
	
	
	public static void main(String[] args) {
		Buffer b = new Buffer(10);
		Productor p = new Productor(b, 30);
		Consumidor c = new Consumidor(b, 30);
		
		p.start();
		c.start();
	}
	
	
	
	
	
	
	
	
	
	
}


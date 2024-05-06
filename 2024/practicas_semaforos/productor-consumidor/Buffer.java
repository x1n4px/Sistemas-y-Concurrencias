package PRODUCTOR_CONSUMIDOR;
import java.util.concurrent.*;

import javax.swing.text.html.parser.Element;
public class Buffer { //Recurso
	
	private int[] elem;
	private int p = 0;//indice de los productores
	private int c = 0;//indice del consumidor
	private int numHuecos = 0;
	//semaforo que controle la espera del consumidor hasta que el buffer este lleno
	//semaforo que controle la espera del productor hasta que el buffer este vacio
	Semaphore hayEspacio;
	Semaphore hayDatos;
	Semaphore mutex;
	//Hay dos productores y un solo consumidor

	//Tiene que haber exclusión mutua
	//CS-C: El consumidor debe esperar hasta que el buffer esté lleno para empezar a consumir
	//CS-P1: Los productores no pueden almacenar datos en el buffer si está lleno.
	//CS-P2: Los productores deben esperar a que el consumidor haya vaciado completamente el buffer para empezar a almacenar nuevos datos.
	
	
	public Buffer(int tam){
		elem = new int[tam];
		p = 0;
		c = 0;
		numHuecos = tam;
		hayEspacio = new Semaphore(1, true);
		hayDatos = new Semaphore(0, true);
		mutex = new Semaphore(1, true);
	}
	

	
	public void almacenar(int dato,int id) throws InterruptedException{
		hayEspacio.acquire();
		mutex.acquire();
		elem[p] = dato;
		
		
		System.out.println("El productor "+ id+ " deja "+dato);

		p = (p+1)%elem.length;
		numHuecos--;

		if(elem.length - numHuecos == 1){
			hayDatos.release();
		}
		if(numHuecos > 0) {
			hayEspacio.release();
		}
		
		mutex.release();
		
	}
	
	public int extraer() throws InterruptedException{
		//utilizado el consumidor 
		int elemento = 0;
		hayDatos.acquire();
		mutex.acquire();
		
		elemento = elem[c];
		System.out.println("               El consumidor extrae "+dato);
		c = (c+1)%elem.length;
		numHuecos++;

		if(numHuecos == 1) {
			hayEspacio.release();
		}
		if(elem.length-numHuecos > 0) {
			hayDatos.release();
		}

		mutex.release();
		return elemento;
	}

}

//CS-Prod: espero hasta que el buffer no está lleno
//CS-Cons: espero hasta que el buffer no esté vacío



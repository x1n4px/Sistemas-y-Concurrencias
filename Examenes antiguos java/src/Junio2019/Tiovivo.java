package Junio2019;

import java.util.concurrent.Semaphore;

/*
 * El tiovivo de la feria de Málaga tiene capacidad para 5
pasajeros, cada uno ocupa un caballito de la atracción.
El operario de la atracción se encarga de parar el tiovivo
para que se bajen los pasajeros. Cuando no quedan más
pasajeros, da paso a nuevos pasajeros que estaban
esperando en la cola. Cuando todas las plazas están
ocupadas, el operario pone en marcha el tiovivo para que
todos disfruten del paseo.
Los pasajeros esperan en la cola hasta que la atracción
está parada y vacía para poder subir. Una vez que
conseguido un caballito, permanecen en la atracción
hasta que termina el paseo y el operario ha parado la
atracción.


Se pide implementar dos soluciones de este sistema, una basada en semáforos binarios y otra en
locks o métodos sincronizados. Para ello se proporcionan las siguientes clases:
- Principal: Crea e inicializa los diferentes componentes del sistema.
- Pasajero: modela el comportamiento de un pasajero utilizando los métodos proporcionados por
el recurso compartido Tiovivo.
- Operario: modela el comportamiento del operario del tiovivo utilizando los métodos
proporcionados por el recurso compartido Tiovivo.
- Tiovivo (Semáforos binarios 3 ptos / Locks o sincronizados 3 ptos): modela el recurso
compartido y proporciona los siguientes métodos:
o public void subir(int id): método usado por un pasajero que quiere subir a
la atracción. Un pasajero no puede subirse hasta que el tiovivo está parado, no haya
pasajeros del paseo anterior y haya espacio.
o public void bajar(int id): método usado por un pasajero que quiere bajar
de la atracción. Un pasajero no puede bajar de la atracción hasta que no dé un paseo y
el tiovivo haya parado.
o public void esperaLleno(): método usado por el operario para que los
pasajeros que estaban en la cola puedan subirse hasta completar todas las plazas.
Cuando no quedan plazas libres el tiovivo se pone en marcha.
o public void finViaje(): método usado por el operario parar el tiovivo y dar
paso a que los pasajeros que estaban dentro puedan bajarse.
Notas:
1. La única documentación que se puede utilizar es el API de Java disponible en Eclipse.
2. Hay que utilizar los ficheros fuentes/proyecto que se proporcionan
3. Hay que subir al campus virtual (del grupo) la implementación de la clase Tiovivo.
 */
public class Tiovivo {
	
	private int caballitos;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore hayHueco = new Semaphore(1);
	private Semaphore barrera = new Semaphore(0);
	private Semaphore viaje = new Semaphore(0);
	private Semaphore salida = new Semaphore(0);
	
	
	public Tiovivo(int c) {
		caballitos = c;
	}
	
	
	public void subir(int id)throws InterruptedException	{	
		barrera.acquire();//0
		mutex.acquire();
		caballitos--;
		System.out.println("El pasajero "+id+" se ha subido. Quedan "+caballitos+" huecos");
		mutex.release();
		hayHueco.release();		
	}
	
	public void bajar(int id) throws InterruptedException{
		salida.acquire();
		mutex.acquire();
		caballitos++;
		System.out.println("El pasajero "+id+" se ha bajado.Quedan "+(5-caballitos)+" ocupados");
		mutex.release();
		if(caballitos < 5) {
			salida.release();
		}else {
			System.out.println("Los pasajeros se pueden volver a subir");
			hayHueco.release();
		}
	}
	
	public void esperaLleno ()throws InterruptedException{
		hayHueco.acquire();
		if(caballitos > 0) {
			barrera.release();
		}else {
			System.out.println("--Inicio del viaje--");
			viaje.release();
		}		
	}
	
	public void finViaje () throws InterruptedException	{
		if(caballitos == 0) {
			System.out.println("--Fin del viaje--\n");
			salida.release();
		}
	}
}

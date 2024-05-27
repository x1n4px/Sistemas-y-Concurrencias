package viajeTren; // Declaración del paquete al que pertenece la clase

import java.util.concurrent.Semaphore; // Importación de la clase Semaphore del paquete java.util.concurrent

public class Tren { // Declaración de la clase Tren
 	
 
	public void viaje(int id) throws InterruptedException { // Declaración del método viaje que recibe como parámetro el id del pasajero y puede lanzar una excepción InterruptedException
 		 
	}

	void empiezaViaje() throws InterruptedException { // Declaración del método empiezaViaje que puede lanzar una excepción InterruptedException
 		System.out.println("	Maquinista: empieza el viaje"); // Impresión en consola del mensaje indicando que el maquinista ha empezado el viaje
	}

	public void finViaje() throws InterruptedException { // Declaración del método finViaje que puede lanzar una excepción InterruptedException
		System.out.println("	Maquinista: fin del viaje"); // Impresión en consola del mensaje indicando que el maquinista ha terminado el viaje
 	}
}

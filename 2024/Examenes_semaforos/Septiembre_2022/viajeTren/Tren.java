package viajeTren; // Declaración del paquete al que pertenece la clase

import java.util.concurrent.Semaphore; // Importación de la clase Semaphore del paquete java.util.concurrent

public class Tren { // Declaración de la clase Tren

	int nPasajeros = 5; // Declaración de la variable nPasajeros con valor 5, que representa la capacidad máxima de pasajeros en cada vagón
	int nPrimerVagon = 0; // Declaración de la variable nPrimerVagon con valor 0, que representa la cantidad de pasajeros en el primer vagón
	int nSegundoVagon = 0; // Declaración de la variable nSegundoVagon con valor 0, que representa la cantidad de pasajeros en el segundo vagón

	Semaphore mutex = new Semaphore(1); // Declaración del semáforo mutex con un permiso inicial de 1, para garantizar la exclusión mutua
	Semaphore empiezaViaje = new Semaphore(0); // Declaración del semáforo empiezaViaje con un permiso inicial de 0, para controlar el inicio del viaje
	Semaphore puedenSubir = new Semaphore(0); // Declaración del semáforo puedenSubir con un permiso inicial de 0, para controlar cuándo los pasajeros pueden subir al tren
	Semaphore esperaVagon1 = new Semaphore(0); // Declaración del semáforo esperaVagon1 con un permiso inicial de 0, para controlar la espera de los pasajeros que están en el primer vagón
	Semaphore esperaVagon2 = new Semaphore(0); // Declaración del semáforo esperaVagon2 con un permiso inicial de 0, para controlar la espera de los pasajeros que están en el segundo vagón

	public void viaje(int id) throws InterruptedException { // Declaración del método viaje que recibe como parámetro el id del pasajero y puede lanzar una excepción InterruptedException
		mutex.acquire(); // Adquisición del semáforo mutex para garantizar la exclusión mutua
		if (nSegundoVagon < nPasajeros) { // Comprobación si el segundo vagón no está lleno
			puedenSubir.release(); // Liberación del semáforo puedenSubir para permitir que los pasajeros suban al tren
		}
		mutex.release(); // Liberación del semáforo mutex

		puedenSubir.acquire(); // Adquisición del semáforo puedenSubir para que el pasajero actual pueda subir al tren
		mutex.acquire(); // Adquisición del semáforo mutex
		if (nPrimerVagon < nPasajeros) { // Comprobación si el primer vagón no está lleno
			nPrimerVagon++; // Incremento del contador de pasajeros en el primer vagón
			System.out.println("	Pasajero " + id + " sube al primer vagon"); // Impresión en consola del mensaje indicando que el pasajero sube al primer vagón
			mutex.release(); // Liberación del semáforo mutex
			esperaVagon1.acquire(); // Adquisición del semáforo esperaVagon1 para esperar a que el pasajero baje del primer vagón
			mutex.acquire(); // Adquisición del semáforo mutex
			System.out.println("	Pasajero " + id + " baja del primer vagon"); // Impresión en consola del mensaje indicando que el pasajero baja del primer vagón
			nPrimerVagon--; // Decremento del contador de pasajeros en el primer vagón
		} else { // Si el primer vagón está lleno
			nSegundoVagon++; // Incremento del contador de pasajeros en el segundo vagón
			System.out.println("	Pasajero " + id + " sube al segundo vagon"); // Impresión en consola del mensaje indicando que el pasajero sube al segundo vagón
			if(nSegundoVagon == nPasajeros) { // Comprobación si el segundo vagón está lleno
				empiezaViaje.release(); // Liberación del semáforo empiezaViaje para indicar que el tren puede empezar el viaje
			}
			mutex.release(); // Liberación del semáforo mutex
			esperaVagon2.acquire(); // Adquisición del semáforo esperaVagon2 para esperar a que el pasajero baje del segundo vagón
			mutex.acquire(); // Adquisición del semáforo mutex
			System.out.println("	Pasajero " + id + " baja del segundo vagon"); // Impresión en consola del mensaje indicando que el pasajero baja del segundo vagón
			nSegundoVagon--; // Decremento del contador de pasajeros en el segundo vagón
		}

		if(nPrimerVagon > 0) { // Comprobación si todavía hay pasajeros en el primer vagón
			esperaVagon1.release(); // Liberación del semáforo esperaVagon1 para permitir que otro pasajero baje del primer vagón
		}
		if(nPrimerVagon == 0 && nSegundoVagon > 0) { // Comprobación si no hay pasajeros en el primer vagón pero hay pasajeros en el segundo vagón
			esperaVagon2.release(); // Liberación del semáforo esperaVagon2 para permitir que otro pasajero baje del segundo vagón
		}
		if(nSegundoVagon == 0) { // Comprobación si no hay pasajeros en el segundo vagón
			System.out.println("***********************************"); // Impresión en consola del mensaje indicando que el segundo vagón está vacío
			puedenSubir.release(); // Liberación del semáforo puedenSubir para permitir que los pasajeros suban al tren
		}
		mutex.release(); // Liberación del semáforo mutex
	}

	void empiezaViaje() throws InterruptedException { // Declaración del método empiezaViaje que puede lanzar una excepción InterruptedException
		empiezaViaje.acquire(); // Adquisición del semáforo empiezaViaje para esperar a que el tren empiece el viaje
		System.out.println("	Maquinista: empieza el viaje"); // Impresión en consola del mensaje indicando que el maquinista ha empezado el viaje
	}

	public void finViaje() throws InterruptedException { // Declaración del método finViaje que puede lanzar una excepción InterruptedException
		System.out.println("	Maquinista: fin del viaje"); // Impresión en consola del mensaje indicando que el maquinista ha terminado el viaje
		esperaVagon1.release(); // Liberación del semáforo esperaVagon1 para permitir que los pasajeros que están en el primer vagón bajen
	}
}

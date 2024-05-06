package agua; // Declaración de un paquete llamado "agua"

import java.util.concurrent.*; // Importación de clases relacionadas con la concurrencia en Java

public class GestorAgua { // Declaración de la clase GestorAgua
	
	private int nH = 0; // Variable para almacenar la cantidad de átomos de hidrógeno
	private int nO = 0; // Variable para almacenar la cantidad de átomos de oxígeno
	private Semaphore mutex = new Semaphore(1, true); // Semáforo para garantizar la exclusión mutua
	private Semaphore entraH = new Semaphore(1, true); // Semáforo para controlar la entrada de átomos de hidrógeno
	private Semaphore entraO = new Semaphore(1, true); // Semáforo para controlar la entrada de átomos de oxígeno
	private Semaphore formarAgua = new Semaphore(0, true); // Semáforo para coordinar la formación de agua

	public void hListo(int id) throws InterruptedException { // Método para indicar que un átomo de hidrógeno está listo para formar agua
		entraH.acquire(); // Se adquiere el semáforo entraH para controlar el acceso a la sección crítica de entrada de átomos de hidrógeno
		mutex.acquire(); // Se adquiere el semáforo mutex para garantizar la exclusión mutua
		nH++; // Se incrementa el contador de átomos de hidrógeno
		System.out.println("El átomo " + id + " de Hidrogeno esta esperando para formar una molecula de agua. Hay " + nH + " átomos de Hidrogeno y " + nO + " átomos de Oxigeno."); // Se imprime un mensaje indicando la cantidad de átomos de hidrógeno y oxígeno
		if (nH < 2) { // Si no hay suficientes átomos de hidrógeno para formar agua
			entraH.release(); // Se libera el semáforo entraH para permitir la entrada de más átomos de hidrógeno
		} else if (nO == 1) { // Si hay suficientes átomos de hidrógeno y oxígeno para formar agua
			System.out.println("Ya hay " + nH + " átomos de Hidrogeno y " + nO + " átomos de Oxigeno, se forma una molecula de agua."); // Se imprime un mensaje indicando que se formó una molécula de agua
			formarAgua.release(); // Se libera el semáforo formarAgua para permitir la formación de agua
		}
		mutex.release(); // Se libera el semáforo mutex
		formarAgua.acquire(); // Se adquiere el semáforo formarAgua para esperar a que se forme agua
		mutex.acquire(); // Se adquiere nuevamente el semáforo mutex para garantizar la exclusión mutua

		nH--; // Se decrementa el contador de átomos de hidrógeno

		if (nH + nO == 0) { // Si no hay átomos de hidrógeno ni oxígeno restantes
			entraH.release(); // Se libera el semáforo entraH para permitir la entrada de más átomos de hidrógeno
			entraO.release(); // Se libera el semáforo entraO para permitir la entrada de más átomos de oxígeno
		} else { // Si aún hay átomos de hidrógeno u oxígeno restantes
			formarAgua.release(); // Se libera el semáforo formarAgua para permitir la formación de más agua
		}
		mutex.release(); // Se libera nuevamente el semáforo mutex
	}
	
	public void oListo(int id) throws InterruptedException { // Método para indicar que un átomo de oxígeno está listo para formar agua
		entraO.acquire(); // Se adquiere el semáforo entraO para controlar el acceso a la sección crítica de entrada de átomos de oxígeno
		mutex.acquire(); // Se adquiere el semáforo mutex para garantizar la exclusión mutua

		nO++; // Se incrementa el contador de átomos de oxígeno
		System.out.println("El átomo " + id + " de Oxigeno esta esperando para formar una molecula de agua. Hay " + nH + " átomos de Hidrogeno y " + nO + " átomos de Oxigeno."); // Se imprime un mensaje indicando la cantidad de átomos de hidrógeno y oxígeno
		if (nH == 2) { // Si hay suficientes átomos de hidrógeno y oxígeno para formar agua
			System.out.println("Ya hay " + nH + " átomos de Hidrogeno y " + nO + " átomos de Oxigeno, se forma una molecula de agua."); // Se imprime un mensaje indicando que se formó una molécula de agua
			formarAgua.release(); // Se libera el semáforo formarAgua para permitir la formación de agua
		}
		mutex.release(); // Se libera el semáforo mutex
		formarAgua.acquire(); // Se adquiere el semáforo formarAgua para esperar a que se forme agua
		mutex.acquire(); // Se adquiere nuevamente el semáforo mutex para garantizar la exclusión mutua

		nO--; // Se decrementa el contador de átomos de oxígeno
		if (nH + nO == 0) { // Si no hay átomos de hidrógeno ni oxígeno restantes
			entraH.release(); // Se libera el semáforo entraH para permitir la entrada de más átomos de hidrógeno
			entraO.release(); // Se libera el semáforo entraO para permitir la entrada de más átomos de oxígeno
		} else { // Si aún hay átomos de hidrógeno u oxígeno restantes
			formarAgua.release(); // Se libera el semáforo formarAgua para permitir la formación de más agua
		}
		mutex.release(); // Se libera nuevamente el semáforo mutex
	}
}

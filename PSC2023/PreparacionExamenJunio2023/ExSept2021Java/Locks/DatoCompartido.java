package Locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DatoCompartido {
	private int dato; // Dato a procesar
	private int nProcesadores; // Numero de procesadores totales
	private int procPend; // Numero de procesadores pendientes de procesar el dato

	private boolean datoGenerado = false;
	private boolean hayOtroProcesando = false;
	private boolean[] procesadoOno;

	private Lock lock;
	private Condition generadorCondition;
	private Condition procesadorCondition;

	/* Recibe como parámetro el número de procesadores que tienen que manipular
	 * cada dato generado. Debe ser un número mayor que 0.
	 */
	public DatoCompartido(int nProcesadores) {
		this.nProcesadores = nProcesadores;
		procPend = nProcesadores;
		procesadoOno = new boolean[this.nProcesadores];
		for (int i = 0; i < procesadoOno.length; i++) procesadoOno[i] = false;

		lock = new ReentrantLock();
		generadorCondition = lock.newCondition();
		procesadorCondition = lock.newCondition();
	}

	/*  El Generador utiliza este método para almacenar un nuevo dato a procesar.
	 *  Una vez almacenado el dato se debe avisar a los procesadores de que se ha
	 *  almacenado un nuevo dato.
	 *
	 *  Por último, el Generador tendrá que esperar en este método a que todos los
	 *  procesadores terminen de procesar el dato. Una vez que todos terminen,
	 *  se devolverá el resultado del procesamiento, permitiendo al Generador
	 *  la generación de un nuevo dato.
	 *
	 *  CS_Generador: Una vez generado un dato, el Generador espera a que todos los procesadores
	 *  terminen antes de generar el siguiente dato
	 */
	public int generaDato(int d) throws InterruptedException {
		lock.lock();
		try {
			dato = d;
			System.out.println("Dato a procesar: " + dato);

			System.out.println("Numero de procesadores pendientes: " + procPend);

			datoGenerado = true;

			generadorCondition.signalAll();

			while (procPend > 0) {
				procesadorCondition.await();
			}

			datoGenerado = false;

			procPend = nProcesadores;

			for (int i = 0; i < procesadoOno.length; i++) procesadoOno[i] = false;

			procesadorCondition.signalAll();

			return dato;
		} finally {
			lock.unlock();
		}
	}

	/*  El Procesador con identificador id utiliza este método para leer el
	 *  dato que debe procesar (el dato se devuelve como valor de retorno del método).
	 *  Deberá esperarse si no hay datos nuevos para procesar
	 *  o si otro procesador está manipulando el dato.
	 *
	 *  CS1_Procesador: Espera si no hay un nuevo dato que procesar.
	 *                  Esto puede ocurrir porque el generador aún no haya almacenado
	 *                  ningún dato o porque el Procesador ya haya procesado el dato
	 *                  almacenado en ese momento en el recurso compartido.
	 *  CS2_Procesador: Espera a que el dato esté disponible para poder procesarlo
	 *                  (es decir, no hay otro Procesador procesando al dato)
	 */
	public int leeDato(int id) throws InterruptedException {
		lock.lock();
		try {
			while (!datoGenerado || hayOtroProcesando || procesadoOno[id]) {
				generadorCondition.await();
			}

			hayOtroProcesando = true;
			procesadoOno[id] = true;

			procesadorCondition.signalAll();

			return dato;
		} finally {
			lock.unlock();
		}
	}

	/*  El Procesador con identificador id almacena en el recurso compartido el resultado
	 *  de haber procesado el dato. Una vez hecho esto actuará de una de las dos formas siguientes:
	 *
	 *  (1) Si aún hay procesadores esperando a procesar el dato los avisará,
	 *  (2) Si él era el último procesador avisará al Generador de que han terminado.
	 *
	 */
	public void actualizaDato(int id, int datoActualizado) {
		lock.lock();
		try {
			dato = datoActualizado;
			System.out.println("    Procesador " + id + " ha procesado el dato. Nuevo dato: " + dato);
			procPend--;
			hayOtroProcesando = false;

			procesadorCondition.signalAll();

			System.out.println("Numero de procesadores pendientes: " + procPend);
		} finally {
			lock.unlock();
		}
	}
}

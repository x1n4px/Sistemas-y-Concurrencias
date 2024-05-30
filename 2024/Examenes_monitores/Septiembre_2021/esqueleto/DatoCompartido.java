import java.util.concurrent.Semaphore;

public class DatoCompartido {
	private int dato = 0; // Dato a procesar
	private int nProcesadores; // Numero de procesadores totales
	private int procPend = 0; // Numero de procesadores pendientes de procesar el dato

	boolean hayDato = false, procesandoDato = false;
	int contProc = 0;

	/*
	 * Recibe como par�metro el n�mero de procesadores que tienen que manipular
	 * cada dato generado. Debe ser un n�mero mayor que 0.
	 */
	public DatoCompartido(int nProcesadores) {
		this.nProcesadores = nProcesadores;

	}

	/*
	 * El Generador utiliza este metodo para almacenar un nuevo dato a procesar.
	 * Una vez almacenado el dato se debe avisar a los procesadores de que se ha
	 * almacenado un nuevo dato.
	 * 
	 * Por ultimo, el Generador tendra que esperar en este metodo a que todos los
	 * procesadores terminen de procesar el dato. Una vez que todos terminen,
	 * se devolvera el resultado del procesamiento, permitiendo al Generador
	 * la generacion de un nuevo dato.
	 * 
	 */
	public synchronized int generaDato(int d) throws InterruptedException {
		while (hayDato) {
			wait(); // Esperar hasta que el dato anterior haya sido procesado por todos

		}

		dato = d;
		hayDato = true;
		procPend = nProcesadores;
		contProc = 0;
		procesandoDato = false;

		notifyAll();

		while (procPend > 0) {
			wait(); // Esperar hasta que todos los procesadores terminen de procesar el dato
		}

		hayDato = false;
		notifyAll();

		return dato;

	}

	/*
	 * El Procesador con identificador id utiliza este metodo para leer el
	 * dato que debe procesar (el dato se devuelve como valor de retorno del
	 * metodo).
	 * Debera esperarse si no hay datos nuevos para procesar
	 * o si otro procesador esta manipulando el dato.
	 */
	public synchronized int leeDato(int id) throws InterruptedException {
		while (!hayDato || procesandoDato) {
			wait(); // Esperar hasta que haya un dato nuevo para procesar
		}
		procesandoDato = true;
		return dato;
	}

	/*
	 * El Procesador con identificador id almacena en el recurso compartido el
	 * resultado
	 * de haber procesado el dato. Una vez hecho esto actuara de una de las dos
	 * formas siguientes:
	 * 
	 * (1) Si aun hay procesadores esperando a procesar el dato los avisara,
	 * (2) Si el era el ultimo procesador avisara al Generador de que han terminado.
	 * 
	 */
	public synchronized void actualizaDato(int id, int datoActualizado) {
		dato = datoActualizado;
		contProc++;
		procesandoDato = false;

		if (contProc < nProcesadores) {
			notifyAll(); // Avisar a los demas procesadores de que han terminado
		} else {
			procPend = 0;
			notifyAll(); // Avisar al generador de que han terminado
		}
	}
}

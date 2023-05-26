package Semaphore;

public class DatoCompartido {
	private int dato; //Dato a procesar
	private int nProcesadores; //Numero de procesadores totales
	private int procPend; //Numero de procesadores pendientes de procesar el dato

	private boolean datoGenerado = false;
	private boolean hayOtroProcesando = false;
	private boolean [] procesadoOno;
	
	
	/* Recibe como par�metro el n�mero de procesadores que tienen que manipular 
	 * cada dato generado. Debe ser un n�mero mayor que 0. 
	 */
	public DatoCompartido(int nProcesadores) {
		this.nProcesadores = nProcesadores;
		procPend = nProcesadores;
		procesadoOno = new boolean[this.nProcesadores];
		for (int i = 0; i< procesadoOno.length; i++) procesadoOno[i] = false;
	}
	
	/*  El Generador utiliza este metodo para almacenar un nuevo dato a procesar. 
	 *  Una vez almacenado el dato se debe avisar a los procesadores de que se ha 
	 *  almacenado un nuevo dato. 
	 *  
	 *  Por ultimo, el Generador tendra que esperar en este metodo a que todos los 
	 *  procesadores terminen de procesar el dato. Una vez que todos terminen, 
	 *  se devolvera el resultado del procesamiento, permitiendo al Generador 
	 *  la generacion de un nuevo dato.
	 * 
	 *  CS_Generador: Una vez generado un dato, el Generador espera a que todos los procesadores 
	 *  terminen antes de generar el siguiente dato
	 */
	public synchronized int generaDato(int d) throws InterruptedException {
		//COMPLETAR y colocar los mensajes en el lugar apropiado dentro del c�digo
		dato = d;
		System.out.println("Dato a procesar: " + dato);
		
		System.out.println("Numero de procesadores pendientes: " + procPend);

		datoGenerado = true;

		notifyAll();
		while(procPend > 0){
			wait();
		}

		datoGenerado = false;

		procPend = nProcesadores;

		for (int i=0; i<procesadoOno.length; i++) procesadoOno[i] = false;

		notifyAll();

		return dato;
	}

	/*  El Procesador con identificador id utiliza este metodo para leer el 
	 *  dato que debe procesar (el dato se devuelve como valor de retorno del metodo). 
	 *  Debera esperarse si no hay datos nuevos para procesar 
	 *  o si otro procesador esta manipulando el dato. 
	 * 
	 *  CS1_Procesador: Espera si no hay un nuevo dato que procesar. 
	 *                  Esto puede ocurrir porque el generador aun no haya almacenado 
	 *                  ningun dato o porque el Procesador ya haya procesado el dato 
	 *                  almacenado en ese momento en el recurso compartido.
	 *  CS2_Procesador: Espera a que el dato este disponible para poder procesarlo 
	 *                  (es decir, no hay otro Procesador procesando al dato)
	 */
	public synchronized int leeDato(int id) throws InterruptedException {
		while(!datoGenerado || hayOtroProcesando || procesadoOno[id]){
			wait();
		}
		hayOtroProcesando = true;
		procesadoOno[id] = true;
		notifyAll();
		return dato;
	}
	
	/*  El Procesador con identificador id almacena en el recurso compartido el resultado 
	 *  de haber procesado el dato. Una vez hecho esto actuara de una de las dos formas siguientes: 
	 *  
	 *  (1) Si aun hay procesadores esperando a procesar el dato los avisara, 
	 *  (2) Si el era el ultimo procesador avisara al Generador de que han terminado. 
	 * 
	 */
	public synchronized void actualizaDato(int id, int datoActualizado) {
		//COMPLETAR y colocar los mensajes en el lugar apropiado dentro del c�digo
		dato = datoActualizado;
		System.out.println("	Procesador " + id + " ha procesado el dato. Nuevo dato: " + dato);
		procPend--;
		hayOtroProcesando = false;
		notifyAll();
		System.out.println("Numero de procesadores pendientes: " + procPend);

	}
}

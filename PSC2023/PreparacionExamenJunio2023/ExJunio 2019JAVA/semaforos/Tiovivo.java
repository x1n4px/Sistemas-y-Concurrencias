import java.util.concurrent.Semaphore;

public class Tiovivo {
	private int nPasajeros = 0, N;

	private Semaphore mutex = new Semaphore(1, true);
	//Es un semáforo binario utilizado para controlar el inicio y fin del viaje en el tiovivo.
	private Semaphore finViaje = new Semaphore(1, true);
	//Es un semáforo inicializado en 0 y se utiliza para indicar que el tiovivo está lleno y listo para comenzar el viaje
	private Semaphore lleno = new Semaphore(0, true);
	//Es un semáforo inicializado en 0 y se utiliza para permitir que los pasajeros bajen del tiovivo
	private Semaphore puedobajar = new Semaphore(0, true);

	public Tiovivo(int cantidad) {
		N = cantidad;
	}

	public void subir(int id) throws InterruptedException
	{
 		finViaje.acquire(); // El pasajero espera el fin del viaje
		mutex.acquire(); //Exclusion mutua sobre nPasajeros
		nPasajeros++;
		System.out.println("El pasajero "+id+" se suba al tiovido. Hay "+nPasajeros+ " pasajeros.");
		if(nPasajeros < N) { //En caso de que no se llene el vagon, se pueden seguir montando
			finViaje.release();
		}else{ //En caso contrario, el vagon esta lleno, y comienza el viaje
			lleno.release();
		}
		mutex.release();
		/*
		Método utilizado por un pasajero para subirse al tiovivo. Adquiere un permiso del semáforo finViaje
		para esperar el inicio del viaje. Luego adquiere el semáforo mutex para acceder de manera exclusiva
		a la variable nPasajeros. Incrementa nPasajeros en 1 y muestra un mensaje indicando que el pasajero
		con el ID proporcionado se ha subido al tiovivo. Si el número de pasajeros es menor que el límite
		máximo (N), se libera un permiso en el semáforo finViaje para permitir que más pasajeros suban al
		tiovivo. De lo contrario, se libera un permiso en el semáforo lleno para indicar que el tiovivo está
		lleno y listo para comenzar el viaje. Finalmente, se libera el semáforo mutex para permitir el acceso
		 de otros hilos a los recursos compartidos
		 */
	}
	
	public void bajar(int id) throws InterruptedException
	{
		puedobajar.acquire();//El pasajero espera que sea seguro bajar del vagon
		mutex.acquire();//Exclusion mutua de nPasajeros
		nPasajeros--;
		System.out.println("El pasajero "+id+" baja del tiovivo. Hay "+nPasajeros+" pasajeros");
		if(nPasajeros > 0) {
			puedobajar.release(); //Permite a los pasajeros bajarse del vagon
		}else{
			finViaje.release(); //Indica que el viaje ha terminado y se pueden volver a subir
		}
		mutex.release();
	}
	
	public void esperaLleno () throws InterruptedException
	{
		lleno.acquire(); //Bloquea al pasajero que se ha montado en el tiovivo esperando a empezar el viaje
		System.out.println("El tiovivo ya esta lleno. Se pone en marcha");
	}
	
	public void finViaje () throws InterruptedException
	{
		System.out.println("El tiovivo ha parado. Ya se pueden bajar los pasajeros");
		puedobajar.release(); //Permite a los pasajeros bajarse del vagon
	}
}

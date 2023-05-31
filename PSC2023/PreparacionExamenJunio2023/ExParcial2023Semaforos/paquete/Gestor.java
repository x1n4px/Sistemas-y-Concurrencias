package paquete;

import java.util.concurrent.Semaphore;

public class Gestor {
	private int[] pesoSoportado; //Carga total que puede soportar
	private int[] cargaActual; //Carga actual del conductor
	private int[] numeroPaquetes; //Numero de paquetes que transporta un conductor
	private Semaphore mutex = new Semaphore(1); //Semaforo para la exclusion mutua
	private Semaphore[] esperoEntregado; //Semaforo en el que los clientes esperan a que se entregue su paquete
	private Semaphore[] puedoRepartir; //Semaforo que gestiona cuando un conductor puede salir a repartir
	private boolean cierreInstalaciones; //True si cerradas, false si no
	private int numConductores;
	private float capacidad;

	public Gestor(int numConductores, 	float capacidad){
		this.numConductores = numConductores;
		this.capacidad = capacidad;
		pesoSoportado = new int[numConductores];
		cargaActual = new int[numConductores];
		numeroPaquetes = new int[numConductores];
		cierreInstalaciones = false;

		esperoEntregado = new Semaphore[numConductores];
		for (int i=0;i<esperoEntregado.length;i++){
			esperoEntregado[i] = new Semaphore(0, true);
		}
		puedoRepartir = new Semaphore[numConductores];
		for (int i=0;i<puedoRepartir.length;i++){
			puedoRepartir[i] = new Semaphore(0);
		}

	}
	public EstadoPaquete entregaPaquete(int id, int peso) throws InterruptedException {
		int i = 0;
		mutex.acquire();
		if (!cierreInstalaciones){
			// Itero buscando sitio, si ya ha salido a repartir, su cargaActual será igual al peso soportado.
			while(i < numConductores && (peso+cargaActual[i]) > pesoSoportado[i]) i++;

			//Si hay sitio, te dejan pasar, si no, te vas y ya volverás.
			if (i<numConductores){

				//Añado el peso del paquete
				cargaActual[i]+=peso;

				//Incremento el número de paquetes que puede transportar.
				numeroPaquetes[i]++;

				//Calculo la carga que le queda.
				int carga=pesoSoportado[i]-cargaActual[i];
				System.out.println("Paquete " + id + " con peso " + peso + " reparte conductor " + i + ", tiene carga disp." + carga );

				//Si el paquete que se está cargando hace que la cumpla con la capacidad mínima que establece la empresa...se notifica al conductor para que salga a repartir.
				if(cargaActual[i]>pesoSoportado[i]*capacidad){
					System.out.println("Conductor " + i + " está cargado");
					cargaActual[i]=pesoSoportado[i]; //Cerramos para que no se sumen más.
					puedoRepartir[i].release();
				}

				mutex.release();

				//Espera a que se entrege, y parte del despertar en cascada.
				esperoEntregado[i].acquire();
				//Paquete entregado
				System.out.println("	Paquete " + id + " ha sido entregado");
				mutex.acquire();

				//El que se despierta, reduce en 1 el número de paquetes que tiene y si siguen quedando, despierta a otro
				numeroPaquetes[i]--;
				if (numeroPaquetes[i]>0)
					esperoEntregado[i].release();
				else{
					//Si no, resetamos la carga, siempre y cuando no esté cerrado, en cuyo caso lo dejamos al máximo para evitar que coja nuevos paquetes.
					if (!cierreInstalaciones) cargaActual[i]=0;
				}
				mutex.release();
				return EstadoPaquete.ENTREGADO;
			}
			else{
				System.out.println("		No hay hueco " + id + " con peso " + peso);
				mutex.release();
				return EstadoPaquete.REINTENTAR;
			}
		}
		else{
			System.out.println("Cerrado el negocio, no se puede entregar " + id + " con peso " + peso);
			mutex.release();
			return EstadoPaquete.SINSERVICIO;
		}
	}
		
	

	public void puedoTransportar(int id, int peso) throws InterruptedException {
		mutex.acquire();
		pesoSoportado[id] = peso;
		System.out.println("Nuevo conductor " + id + " , puede transportar " + peso + " kilos.");
		mutex.release();
	}
	public void cargarPaquetes(int id) throws InterruptedException {
		puedoRepartir[id].acquire();
		System.out.println("Conductor " + id + " sale de las instalaciones");

	}
	public void repartir(int id) {
		System.out.println("Conductor " + id + " reparte");
		esperoEntregado[id].release();
	}
	public void fin() throws InterruptedException {
		mutex.acquire();
		System.out.println("Las instalaciones se cierran");
 		cierreInstalaciones = true;

		 //Lanzamos los otros conductores para que se repartan lo que tienen, si no han sido ya lanzados
		for (int i=0; i < numConductores; i++){
			if(cargaActual[i] > 0 && (cargaActual[i] <= pesoSoportado[i]*capacidad)){
				System.out.println("Conductor " + i + " sale en ultimo reparto");
				puedoRepartir[i].release();
			}
		}
		mutex.release();
	}
	public boolean estaCerrado() throws InterruptedException {
		boolean aux;
		mutex.acquire();
		aux = cierreInstalaciones;
		mutex.release();
		return aux;
	}
}

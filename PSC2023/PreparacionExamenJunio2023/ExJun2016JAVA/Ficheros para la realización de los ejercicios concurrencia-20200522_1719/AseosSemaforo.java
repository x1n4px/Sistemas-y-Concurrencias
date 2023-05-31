package aseos;

import java.util.concurrent.Semaphore;

public class AseosSemaforo {

	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando o
	 * está esperando para poder limpiar los aseos
	 *
	 */
	private int nClientes;
	private Semaphore mutex;              // Mutex para garantizar exclusión mutua al acceder a nClientes
	private Semaphore mutex3;             // Mutex para garantizar exclusión mutua en el uso de limpiezaEsperando

	private Semaphore dentro;             // Controla la entrada al área de los aseos (solo un cliente o equipo de limpieza a la vez)
	private Semaphore limpiezaEsperando;  // Indica si el equipo de limpieza está esperando para poder limpiar los aseos

	public AseosSemaforo() {
		mutex = new Semaphore(1, true);
		mutex3 = new Semaphore(1, true);
		dentro = new Semaphore(1, true);
		limpiezaEsperando = new Semaphore(1, true);
	}

	public void entroAseo(int id) throws InterruptedException {
		mutex3.acquire();
		limpiezaEsperando.acquire();  // El cliente espera si el equipo de limpieza está trabajando o esperando

		mutex.acquire();
		nClientes++;
		if(nClientes == 1){
			dentro.acquire();  // El primer cliente adquiere el control del área de los aseos
		}
		System.out.println("Entra cliente. Total Clientes: "+nClientes);
		mutex.release();
		limpiezaEsperando.release();
		mutex3.release();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 *
	 */
	public void salgoAseo(int id) throws InterruptedException {
		mutex.acquire();
		nClientes--;
		if(nClientes == 0){
			dentro.release();  // El último cliente libera el control del área de los aseos
		}
		System.out.println("Sale Cliente. Total clientes: "+nClientes);
		mutex.release();
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que no
	 * haya ningún cliente.
	 *
	 */
	public void entraEquipoLimpieza() throws InterruptedException {
		System.out.println("Equipo de limpieza quiere entrar");
		limpiezaEsperando.acquire();  // El equipo de limpieza espera si hay clientes dentro de los aseos
		dentro.acquire();             // El equipo de limpieza adquiere el control del área de los aseos
		System.out.println("Entra equipo de limpieza. Total clientes: "+nClientes);
	}

	/**
	 * Utilizado por el Equipo de Limpieza cuando sale de los aseos
	 *
	 *
	 */
	public void saleEquipoLimpieza(){
		System.out.println("Sale equipo de limpieza. Total clientes: "+nClientes);
		limpiezaEsperando.release();  // El equipo de limpieza indica que ha salido y está disponible para limpiar
		dentro.release();             // El equipo de limpieza libera el control del área de los aseos
	}
}

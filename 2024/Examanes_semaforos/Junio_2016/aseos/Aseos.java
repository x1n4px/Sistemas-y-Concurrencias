package aseos;

import java.util.concurrent.Semaphore;

public class Aseos {
	private int nClientes;
	private Semaphore mutex;	// Mutex para garantizar exclusión mutua al acceder a nClientes
	private Semaphore mutex3;	// Mutex para garantizar exclusión mutua en el uso de limpiezaEsperando
	private Semaphore dentro; 	// Controla la entrada al área de los aseos (solo un cliente o equipo de limpieza a la vez)
	private Semaphore limpiezaEsperando; // Indica si el equipo de limpieza está esperando para poder limpiar los aseos

	public Aseos() {
		nClientes = 0;
		mutex = new Semaphore(1, true);
		mutex3 = new Semaphore(1, true);
		dentro = new Semaphore(1, true);
		limpiezaEsperando = new Semaphore(0, true);
	}
	
	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza est� trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza est� trabajando o
	 * est� esperando para poder limpiar los aseos
	 * 
	 */
	public void entroAseo(int id) throws InterruptedException {
		mutex3.acquire();
		limpiezaEsperando.acquire();
		mutex.acquire();
		nClientes++;
		if(nClientes == 1) {
			dentro.acquire();
		}
		System.out.println("Entra cliente. Total clientes dentro: " + nClientes);
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
		if(nClientes == 0) {
			dentro.release();
		}
		System.out.println("Sale cliente. Total clientes dentro: " + nClientes);
		mutex.release();
	}
	
	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos 
	 * CS: El equipo de trabajo est� solo en los aseos, es decir, espera hasta que no
	 * haya ning�n cliente.
	 * 
	 */
    public void entraEquipoLimpieza() throws InterruptedException {
		System.out.println("Equipo de limpieza entra en los aseos");
		limpiezaEsperando.acquire();
		dentro.acquire();
		System.out.println("Equipo de limpieza limpiando los aseos");
	}
    
    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos 
	 * 
	 * 
	 */
    public void saleEquipoLimpieza() throws InterruptedException {
    	System.out.println("Equipo de limpieza sale de los aseos");
		limpiezaEsperando.release();
		dentro.release();
	}
}

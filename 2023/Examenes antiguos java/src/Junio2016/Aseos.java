package Junio2016;

import java.util.concurrent.Semaphore;

public class Aseos {
	
	
	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza está trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza está trabajando o
	 * está esperando para poder limpiar los aseos
	 * 
	 */
	private int nClientes;
	private Semaphore mutex;
	private Semaphore mutex3;
	
	private Semaphore dentro;
	private Semaphore limpiezaEsperando;
	
	public Aseos() {
		mutex = new Semaphore(1,true);
		mutex3 = new Semaphore(1,true);
		dentro = new Semaphore(1,true);
		limpiezaEsperando = new Semaphore(1,true);
	}
	
	public void entroAseo(int id) throws InterruptedException{
	mutex3.acquire();
	limpiezaEsperando.acquire();
	
	mutex.acquire();
	nClientes++;
	if(nClientes==1) {
		dentro.acquire();
	}
	
	System.out.println("Entra Cliente. Total clientes: "+nClientes);
	
	mutex.release();
	
	
	limpiezaEsperando.release();
	mutex3.release();
	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * @throws InterruptedException 
	 * 
	 */
	public void salgoAseo(int id) throws InterruptedException{
		mutex.acquire();
		nClientes--;
		if(nClientes==0) {
			dentro.release();
		}
		System.out.println("Sale Cliente.Total clientes: "+nClientes);
		
		
		mutex.release();
	}
	
	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos 
	 * CS: El equipo de trabajo está solo en los aseos, es decir, espera hasta que no
	 * haya ningún cliente.
	 * @throws InterruptedException 
	 * 
	 */
    public void entraEquipoLimpieza() throws InterruptedException{
		System.out.println("Equipo de limpieza quiere entrar");
		limpiezaEsperando.acquire();
		dentro.acquire();
		System.out.println("Entra equipo de limpieza. Total clientes: "+nClientes);
	}
    
    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos 
	 * 
	 * 
	 */
    public void saleEquipoLimpieza(){
    	System.out.println("Sale equipo de limpieza. Total clientes: "+nClientes);
    	limpiezaEsperando.release();
    	dentro.release();
	}
}

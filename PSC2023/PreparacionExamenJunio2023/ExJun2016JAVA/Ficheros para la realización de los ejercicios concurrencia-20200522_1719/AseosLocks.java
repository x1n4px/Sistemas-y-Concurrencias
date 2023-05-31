package aseos;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AseosLocks {
	
	
	/**
	 * Utilizado por el cliente id cuando quiere entrar en los aseos
	 * CS Version injusta: El cliente espera si el equipo de limpieza est� trabajando
	 * CS Version justa: El cliente espera si el equipo de limpieza est� trabajando o
	 * est� esperando para poder limpiar los aseos
	 * 
	 */

	private Lock lock;
	private int numClientes;
	private boolean equipoTrabajando;
	private boolean equipoEsperando;


	public AseosLocks(){
		lock = new ReentrantLock();
		numClientes = 0;
		equipoEsperando = false;
		equipoTrabajando = false;
	}



	public void entroAseo(int id){
		lock.lock();
		try{
			while(equipoTrabajando || equipoEsperando){
				try{
					wait();
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}
			numClientes++;
		}finally {
			lock.unlock();
		}

	}

	/**
	 * Utilizado por el cliente id cuando sale de los aseos
	 * 
	 */
	public void salgoAseo(int id){
		lock.lock();
		try{
			numClientes--;
			if(numClientes == 0){
				notifyAll();
			}
		}finally {
			lock.unlock();
		}
	}
	
	/**
	 * Utilizado por el Equipo de Limpieza cuando quiere entrar en los aseos 
	 * CS: El equipo de trabajo est� solo en los aseos, es decir, espera hasta que no
	 * haya ning�n cliente.
	 * 
	 */
    public void entraEquipoLimpieza(){
		lock.lock();
		try{
			while(numClientes > 0 || equipoTrabajando) {
				try {
					equipoEsperando = true;
					wait();
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}
			equipoEsperando = false;
			equipoTrabajando = true;
		}finally {
			lock.unlock();
		}
	}
    
    /**
	 * Utilizado por el Equipo de Limpieza cuando  sale de los aseos 
	 * 
	 * 
	 */
    public void saleEquipoLimpieza(){
    	lock.lock();
		try{
			equipoTrabajando = false;
			notifyAll();
		}finally {
			lock.unlock();
		}
	}
}

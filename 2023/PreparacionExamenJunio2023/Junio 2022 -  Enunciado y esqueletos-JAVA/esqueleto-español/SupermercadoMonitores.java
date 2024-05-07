package Supermercado;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SupermercadoMonitores implements Supermercado{

	private Cajero permanente;
	private int clientesEsperando;
	private int cajerosActivos;
	private boolean cerrado;
	private Lock lock;
	private Condition esperaCajero;
	private Condition esperaCliente;


	
	
	public SupermercadoMonitores() throws InterruptedException {
		permanente = new Cajero(this, true); //crea el primer cajero, el permanente
		permanente.start();
		
		clientesEsperando = 0;
		cajerosActivos = 1;
		cerrado = false;
		lock = new ReentrantLock();
		esperaCajero = lock.newCondition();
		esperaCliente = lock.newCondition();
	}
	
	@Override
	public void fin() throws InterruptedException {
		lock.lock();
		try{
			cerrado = true;
			esperaCliente.signalAll();
			esperaCajero.signalAll();
		}finally {
			lock.unlock();
		}
		
	}

	@Override
	public void nuevoCliente(int id) throws InterruptedException {
		lock.lock();
		try{
			if(cerrado){
				return;
			}

			clientesEsperando++;
			if(cajerosActivos == 0) {
				esperaCajero.signal();
			}else if( clientesEsperando > 3*cajerosActivos) {
				cajerosActivos++;
				new Cajero(this, false).start();
			}
			esperaCliente.await();
			clientesEsperando--;
		}finally {
			lock.unlock();
		}
		
	}

	@Override
	public boolean permanenteAtiendeCliente(int id) throws InterruptedException {
		lock.lock();
		try{
			while(!cerrado && clientesEsperando == 0) {
				esperaCajero.await();
			}
			if(cerrado && clientesEsperando == 0) {
				return false;
			}
			System.out.println("Cajero permanente atiende al cliente " + id);

			esperaCliente.signal();

			return true;
		}finally {
			lock.unlock();
		}
	}

	@Override
	public boolean ocasionalAtiendeCliente(int id) throws InterruptedException {
		lock.lock();
		try{
			if(clientesEsperando == 0) {
				return false;
			}
			System.out.println("Cajero ocasional " + id + " atiende a un cliente");

			esperaCliente.signal();
			return true;
		}finally {
			lock.unlock();
		}
	}

}

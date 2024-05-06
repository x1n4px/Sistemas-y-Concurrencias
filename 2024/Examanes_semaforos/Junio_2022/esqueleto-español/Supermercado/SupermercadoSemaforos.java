package Supermercado;

import java.util.concurrent.Semaphore;

public class SupermercadoSemaforos implements Supermercado {

	private Cajero permanente;
	int nClientes = 0;
	boolean estaAbierto;

	Semaphore mutex = new Semaphore(1);
	Semaphore esperaCajero = new Semaphore(0);
	Semaphore esperaCliente = new Semaphore(1);

	public SupermercadoSemaforos() throws InterruptedException {
		permanente = new Cajero(this, true); // crea el primer cajero, el permanente
		permanente.start();
		estaAbierto = true;

	}

	@Override
	public void fin() throws InterruptedException {
		estaAbierto = false;
		System.out.println("Cerrado");
		esperaCajero.release();
	}

	@Override
	public void nuevoCliente(int id) throws InterruptedException {
		if (estaAbierto) {
			mutex.acquire();
			nClientes++;
			System.out.println("Nuevo cliente " + id + ". Hay " + nClientes + " clientes en la cola");
			if (nClientes == 1) {
				esperaCajero.release();
			} else if (nClientes > 3 * Cajero.numCajeros()) {
				Cajero ocasional = new Cajero(this, false);
				System.out.println("Nuevo cajero ocasional");
				ocasional.start();
			}
			mutex.release();
			esperaCliente.acquire();
		} else {
			System.out.println("El supermercado esta cerrado. El cliente " + id + " se va sin comprar");

		}
	}

	@Override
	public boolean permanenteAtiendeCliente(int id) throws InterruptedException {
		boolean atendiendo = true;
		mutex.acquire();
		if (nClientes == 0) {
			mutex.release();
			System.out.println("El cajero permanente no puede atender al cliente " + id);
			mutex.acquire();
			if (nClientes > 0) {
				nClientes--;
				esperaCliente.release();
				System.out.println(
						"Cajero permanente atiende a un cliente. Quedan " + nClientes + " clientes en la cola");
			}
		} else {
			nClientes--;
			esperaCliente.release();
			System.out.println("Cajero permanente atiende a un cliente. Quedan " + nClientes + " clientes en la cola");
		}

		if(nClientes == 0) {
			if(!estaAbierto) {
				atendiendo	= false;
			}
		}
		mutex.release();

		return atendiendo;
	}

	@Override
	public boolean ocasionalAtiendeCliente(int id) throws InterruptedException {
		boolean atendiendo = true;
		mutex.acquire();
		if(nClientes == 0) {
			atendiendo = false;
			System.out.println("No hay mas cliente. El cajero ocasional " + id + " se va");
		}else{
			nClientes--;
			esperaCliente.release();
			System.out.println("Cajero ocasional " + id + " atiende a un cliente. Quedan " + nClientes + " clientes en la cola");
		}
		mutex.release();
		return atendiendo;
	}

}

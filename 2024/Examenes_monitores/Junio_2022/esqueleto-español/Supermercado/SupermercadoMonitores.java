package Supermercado;

public class SupermercadoMonitores implements Supermercado{

	private Cajero permanente;
	private Cajero ocasional = null;
	private int clientes = 0, cajeros = 0;
	private boolean cajeroEspera = true, clienteEspera = false, superAbierto = true;
	
	
	public SupermercadoMonitores() throws InterruptedException {
		permanente = new Cajero(this, true); //crea el primer cajero, el permanente
		permanente.start();
		
	}
	
	@Override
	public synchronized void fin() throws InterruptedException {
		superAbierto = false;
		System.out.println("Supermercado cerrado");
		while(clientes > 0) {
			wait();
		}
		
	}

	@Override
	public synchronized void nuevoCliente(int id) throws InterruptedException {
		if(superAbierto) {
			if(cajeroEspera) {
				cajeroEspera = false;
				notifyAll();
			}
			clientes++;
			System.out.println("Cliente " + id + " entra en el supermercado");
			if(clientes >= 3* cajeros) {
				ocasional = new Cajero(this, false);
				ocasional.start();
				cajeros++;
			}
			while (clienteEspera) {
				wait();
			}
		}
	}

	@Override
	public synchronized boolean permanenteAtiendeCliente(int id) throws InterruptedException {
		clienteEspera = false;
		notifyAll();
		if(clientes == 0 && !superAbierto) {
			System.out.println("Cajero permanente cierra");
			notifyAll();
			return false;
		} else if (clientes > 0) {
			clientes--;
			System.out.println("Cajero permanente atiende al cliente " + id);
		} else {
			System.out.println("Cajero permanente espera a que llegue un cliente");
			while(cajeroEspera) {
				wait();
			
			}
		}
		return true;
	}

	@Override
	public synchronized boolean ocasionalAtiendeCliente(int id) throws InterruptedException {
		if(clientes == 0) {
			cajeros--;
			System.out.println("No hay clientes. Cajero " + id + " cierra");
			notifyAll();
			return false;
		} else {
			clientes--;
			System.out.println("Cajero ocasional atiende al cliente " + id);
			return true;
		}
	}

}

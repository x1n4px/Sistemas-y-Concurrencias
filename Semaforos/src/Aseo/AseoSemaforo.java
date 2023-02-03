package Aseo;

import java.util.concurrent.Semaphore;

public class AseoSemaforo {

	private int numClientes;
	//private boolean limpiando; //true si equipo de limpieza dentro
	private Semaphore mutex; //proteger a numClientes
	
	//version injusta
	private Semaphore entraAseo;//cerrado sii hay alguien dentro -> cierra el paso al otro tipo de hebras
	
	//version justa
	//boolean equipoQuiereEntrar; //No necesitamos booleanos los semaforos controlas las CS
	//private Semaphore clientesPuedenPasar;
	private Semaphore equipoQuiereEntrar; //Cerrado sii limpieza quiere entrar o esta dentro
	private Semaphore mutex3; //nuevo - para que se despierte antes al equipo de limpieza
	
	public AseoSemaforo() {
		mutex = new Semaphore(1,true);
		mutex3 = new Semaphore(1,true);
		entraAseo = new Semaphore(1,true);
		numClientes = 0;
		//equipoQuiereEntrar = false;
		equipoQuiereEntrar = new Semaphore(1,true);
	}
	
	
	
	public void entraLimpieza() throws InterruptedException {
		equipoQuiereEntrar.acquire(); //quiere entrar al aseo
		System.out.println("Equipo esperando a que el aseo este libre");
		entraAseo.acquire(); //sii esta abierto significa que aseo libre
		System.out.println("Equipo dentro");
	}

	public void saleLimpieza() {
		System.out.println("Sale equipo");
		equipoQuiereEntrar.release();
		entraAseo.release();
	}

	public void entraCliente(int id) throws InterruptedException {
		mutex3.acquire();
		equipoQuiereEntrar.acquire();
		mutex.acquire();
		numClientes++;
		if(numClientes == 1) {
			entraAseo.acquire();
		}
		System.out.println("\t\t Entra Cliente: "+id);
		mutex.release();
		equipoQuiereEntrar.release();
		mutex3.release();
		
	}

	public void saleCliente(int id) throws InterruptedException {
		mutex.acquire();
		numClientes--;
		if(numClientes == 0	) {
			entraAseo.release();
		}
		System.out.println("\t\tSale Cliente: "+id);
		mutex.release();
	}

	public static void main(String[] args) {
		AseoSemaforo aseo = new AseoSemaforo();
		Limpieza l = new Limpieza(aseo);
		Cliente c[] = new Cliente[10];
		for(int i=0;i<10;i++) {
			c[i] = new Cliente(aseo, i);
		}
		l.start();
		for(int i=0;i<10;i++) {
			c[i].start();
		}
	}
	
	
	
}

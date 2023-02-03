package Handshake;


public class Recurso {
	private boolean llegaP1, llegaP2;
	public synchronized void sincronizaConP2() throws InterruptedException {		
		System.out.println("P1 llega a la cita");
		llegaP1 = true;//P1 indica que ha llegado 
		
		notify();//si P2 estaba esperando a P1, desbloquea a P2
		
		if(!llegaP2)
			wait(); //si P2 no ha llegado espera  
		System.out.println("P1 -- sincronizado con P2");
		llegaP1=false;
	}

	public synchronized void sincronizaConP1() throws InterruptedException {		
		System.out.println("P2 llega a la cita");
		llegaP2 = true;//P2 indica que ha llegado 
		
		notify();//si P1 estaba esperando a P2, desbloquea a P1
		
		if(!llegaP1)
			wait();//siP1 no ha llegado espera 
		System.out.println("P2 -- sincronizado con P1");
		llegaP2=false;//P2 indica que ha terminado la sincronización
	}

}


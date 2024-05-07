package Ejercicio1;
public class Trabajador extends Thread {
	private Sistema sis;
	
	public Trabajador(Sistema s) 
	{
		sis =s;
	}
	
	public void run() {
		
			try {
				while(!this.isInterrupted()) {
				sis.procesar();
				}
			} catch (InterruptedException e) {
			}

	}

}

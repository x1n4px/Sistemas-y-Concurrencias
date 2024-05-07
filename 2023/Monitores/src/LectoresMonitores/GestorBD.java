package LectoresMonitores;
public class GestorBD {

	
	private int nLectores;//num de lectores que estan en la BD	
	
	private boolean escribiendo = false;//true sii hay un escritor en BD
	
	public synchronized void entraEscribir(int id) throws InterruptedException {
				
		//CS - si hay otro escritor o lectores en BD este escritor se bloquea
		while(escribiendo || nLectores >0)
			wait();		
		
		escribiendo = true; //Cambio la CS para el paso de otros escritores o lectores
		
		System.out.println("Escritor"+ id+" entra en BD, lectores dentro "+nLectores);
	}
	public synchronized void saleEscribir(int id) {
		
		System.out.println("Escritor"+id+ " sale de BD, lectores dentro "+nLectores);	
		escribiendo = false; //Cambio la  CS para el paso de otros escritores o lectores
		notifyAll();	// Despierta a lectores y escritores	
	}

	public synchronized void entraLeer(int id) throws InterruptedException {

		//CS - si hay escritores los lectores se bloquean
		while(escribiendo)		
			wait();
		
		nLectores++;						
		
		System.out.println("Lector "+id+" en BD, lectores dentro "+nLectores);
	}
	public synchronized void saleLeer(int id){
		nLectores--;
		System.out.println("Lector "+id+" en BD, lectores dentro "+nLectores);
		
		if(nLectores==0)
			notify(); //Despierta a un escritor
		
	}

}

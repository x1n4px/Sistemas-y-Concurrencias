package Junio2018_Agua;

public class Oxigeno extends Thread {

	private GestorAgua gestor;
	private int id;
	
	public Oxigeno(GestorAgua gestor,int id) {
		// TODO Auto-generated constructor stub
			this.gestor = gestor;
			this.id = id;
	}
	
	public void run() {
		
		while(true) {
			try {
				gestor.oListo(id);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
			
		}
		
		
		
		
	}
	
	
}

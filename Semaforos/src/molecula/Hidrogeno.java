package molecula;

public class Hidrogeno extends Thread {

	private GestorAgua gestor;
	private int id;
	
	public Hidrogeno(GestorAgua gestor,int id) {
		// TODO Auto-generated constructor stub
			this.gestor = gestor;
			this.id = id;
	}
	
	public void run() {
		
		while(true) {
			try {
				gestor.hListo(id);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
			
		}
		
		
		
		
	}
}

package Jardines_synch;

public class Jardin {

	//el miembro es estatico - se "comparte" entre todos los objetos jardin
	private static int personas;
	//¿Como lo modificamos en exclusion mutua?
	
	
	public Jardin() {
		personas = 0;
	}
	
	public synchronized static void inc(int id) {
		personas++;
		System.out.println("inc2 - puerta "+id+". Total: "+personas);
	}
	
	public static void main(String[] args) {
		Jardin j = new Jardin()	;
		Puerta p1 = new Puerta(1,j);
		Puerta p2 = new Puerta(2,j);
		p1.start();
		p2.start();
		
		try {
			p1.join();
			p2.join();
			System.out.println("Numero total de personas: "+Jardin.personas);
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}

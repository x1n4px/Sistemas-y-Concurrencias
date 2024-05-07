package Jardines_synch_static;

public class Jardin {

	//el miembro es estatico - se "comparte" entre todos los objetos jardin
	private static int personas = 0;
	//¿Como lo modificamos en exclusion mutua?
	
	//opcion 1 - usar un bloque synchronized sobre la clase en vez de sobre un objeto
	public void inc(int id) {
		synchronized (Jardin.class) {
			personas++;
			System.out.println("inc - puerta "+id+". Total: "+personas);
		}
	}
	
	//opcion 2 - usar un metodo estatico y syncrhonized
	public synchronized static void inc2(int id) {
		personas++;
		System.out.println("inc2 - puerta "+id+". Total: "+personas);
	}
	
	public static void main(String[] args) {
		Puerta p1 = new Puerta(1);
		Puerta p2 = new Puerta(2);
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

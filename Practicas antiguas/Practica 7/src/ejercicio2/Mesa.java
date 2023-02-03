package ejercicio2;
public class Mesa {
	private int ingrediente; //ingrediente q falta 0 : tabaco, 1: cerillas, 2: papel, -1 vacio
	private boolean fumando;
	
	public Mesa() {
		ingrediente = -1;
		fumando = false;
	}

	public synchronized  void poneIngrediente(int ing) throws InterruptedException {
		
		while(ingrediente !=-1 || fumando) {
			System.out.println("Agente- espera");
			wait();
			}
		//sc
		ingrediente = ing;
		System.out.println("Agente- falta ingrediente "+ ing);
		notifyAll();
		
	}

	public synchronized void quiereFumar(int id) throws InterruptedException {
		//CS 
		System.out.println("Fumado "+id+" quiere a fumar");
		while(ingrediente != id)
			wait();
		//
		System.out.println("Fumado "+id+" empieza a fumar");
		ingrediente = -1;
		fumando  = true;
		
	}

	public synchronized void terminaFumar(int id) {
		fumando = false;
		System.out.println("Fumado "+id+" termina");
		notifyAll();
		
	}

	public static void main(String[] args) {
		Mesa m = new Mesa();
		Agente a = new Agente(m);
		Fumador fumadores[] = new Fumador[3];
		for(int i = 0; i < 3; i++)
			fumadores[i] = new Fumador(i, m);
		
		a.start();
		for(int i = 0; i < 3; i++)
			fumadores[i].start();

	}
}

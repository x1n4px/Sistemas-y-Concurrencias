package fumadores;


public class Mesa {
	
	
	public void qFumar(int id) {
		
		System.out.println("Fumador "+id+" coge los ingredientes");
		
	}
	public void finFumar(int id) {
		System.out.println("Fumador "+id+" ha terminado de fumar");
		
	}
	public void nuevosIng(int ing) throws InterruptedException { // se pasa el ingrediente que no se pone
		
		System.out.print("El agente ha puesto los ingredientes ");
		if (ing==0) System.out.println("1 y 2");
		else if (ing == 1) System.out.println("0 y 2");
		else System.out.println("0 y 1");
		
	}

}

//CS-Fumador i: No puede fumar hasta que el fumador anterior no ha terminado
// de fumar y sus ingredientes están sobre la mesa
//CS-Agente: no puede poner nuevos ingredientes hasta que el fumador anterior 
//no ha terminado de fumar

package Prod_Cons_Monitores;

public class Buffer {
	//VARIABLES
	private final static int N = 10;
	private int p; //posicion por la que se va produciendo;
	private int c; //posicion por la que se va consumiendo;
	private int[] elem;
	private int nelem;
	
	public Buffer() {
		p = 0;
		c = 0;
		elem = new int[N];
		nelem = 0;
	}
	
	
	
	
	
	
	
	public synchronized void insertar(int e) throws InterruptedException {
		//CS --- El productor no puede insertar si el buffer esta lleno
		while(nelem == N) {
			System.out.println("Buffer lleno, productor entra en suspension");
			wait();
		}
		//---
		elem[p] = e;
		System.out.println("Elemento insertado "+e+" en posicion "+p);
		p = (p+1)%N;
		notify();
	}

	public synchronized int extraer() throws InterruptedException {
		int elemento;
		//CS --- El consumidor no puede extraer si el buffer esta vacio
		while(nelem == 0) {
			System.out.println("Buffer vacio, consumidor entra en suspension");
			wait();
		}
		//----
		elemento = elem[c];
		c = (c+1)%N;
		nelem--;
		notify();
		System.out.println("Elemento leido "+elemento);
		return elemento;
	}

}

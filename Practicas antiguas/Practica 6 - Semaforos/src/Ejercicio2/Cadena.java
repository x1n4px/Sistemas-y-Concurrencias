package Ejercicio2;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
public class Cadena {

	private int N = 8;
	private int[] cadena;
	private int numProd;
	private Semaphore mutex;
	private Semaphore[] hayProd;
	private Semaphore hayHuecos;
	
	public Cadena() {
		cadena = new int[N];
		mutex = new Semaphore(1,true);
		hayProd = new Semaphore[3];
		hayHuecos = new Semaphore(N);
		
		for (int i = 0; i < cadena.length; i++) {
			cadena[i] = -1; //cadena vacía
		}
		for (int i = 0; i < hayProd.length; i++) {
			hayProd[i] = new Semaphore(0,true);
		}
		
	}
	
	public void nuevoProducto(int id) throws InterruptedException{
		//pre
		hayHuecos.acquire();
		mutex.acquire();
		
		//SC
		numProd++;
		int i = 0;
		while (cadena[i]!=-1) { // cadena no este vacia
			i++;
		}
		cadena[i] = id;
		System.out.println("Colocador pone "+id+" "+Arrays.toString(cadena));
		System.out.println("NumProductos: "+numProd);
		//post
		hayProd[id].release();
		mutex.release();
		
		
		
	}
	
	
	public void extraerProductor(int id)throws InterruptedException{
		
		//pre
		hayProd[id].acquire();;
		mutex.acquire();
		//SC
		numProd--;
		int i=0;
		while(cadena[i]!=id) {
			i++;
		}
		cadena[i] = -1;
		System.out.println("Empaquetador: "+id+" extrae. "+Arrays.toString(cadena));
		System.out.println("NumProd: "+numProd);
		//post
		hayHuecos.release();
		mutex.release();
		
		
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		Cadena c = new Cadena();
		Colocador col = new Colocador(c);
		Empaquetador[] emp = new Empaquetador[3];
		for (int i = 0; i < emp.length; i++) {
			emp[i] = new Empaquetador(c, i);
		}
		
		col.start();

		for (int i = 0; i < emp.length; i++) {
			emp[i].start();
		}
		
		
		
	}
	
}

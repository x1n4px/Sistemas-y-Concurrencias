package BarberoDormilon;
import java.util.concurrent.*;
public class Barberia {

	private int n=0;//buffer-no de clientes esperando
	private Semaphore mutex = new Semaphore(1);//protege a n. sem. binaria
	private Semaphore espera = new Semaphore(0);//buffer esta vacio. sem binaria
	//la mayor parte del tiempo vale 0
	private boolean primera = true;
	
	public void nuevoCliente() throws InterruptedException	{
		//lo llama el entorno
		mutex.acquire();
		n++;//llega un nuevo cliente
		System.out.println("llega cliente "+n);
		if(n==1) espera.release(); //espera es binario
		mutex.release();
	}
	
	
	public void pelar() throws InterruptedException {
		//lo llama el barbero
		if(primera) {
			espera.acquire(); 
			primera = false;
		}
		mutex.acquire();
		//n > 0
		n--;
		int m = n;
		System.out.println("Pelo cliente "+n);
		mutex.release();
		if(m==0) espera.acquire();//saco acquire en sc
		
	}
	
}

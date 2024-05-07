package Ejercicio4;

import java.util.concurrent.Semaphore;

public class Olla1 implements IOlla{

	private int TamRac = 10;
	private int olla = 0;
	private Semaphore ollaVacia = new Semaphore(1);
	private Semaphore ollaNoVacia = new Semaphore(0);
	
	public void nuevoExplorador() throws InterruptedException{
		
		ollaVacia.acquire();
		olla = TamRac;
		System.out.println("Cocinero llena olla "+olla);
		ollaNoVacia.release();
	}
	
	public void comeRacion(int id)throws InterruptedException{
		
		ollaNoVacia.acquire();
		olla--;
		System.out.println("Canibal "+id+" come racion "+olla);
		if(olla == 0) {
			ollaVacia.release();
		}else {
			ollaNoVacia.release();
		}
		
		
	}

	
	
	
	
	
	
	
	
	
}

package LectoresSemaforos;

import java.util.concurrent.*;


public class GestorBD {

	private Semaphore escribiendo = new Semaphore(1); //CS-Esc
	private int nLectores = 0;
	private int nEsc = 0;
	private Semaphore mutex1 = new Semaphore(1);//para nLectores
	private Semaphore mutex2 = new Semaphore(1);//para nEsc
	private Semaphore leyendo = new Semaphore(1);//para justicia
	private Semaphore mutex3 = new Semaphore(1);
	public void entraLector(int id)throws InterruptedException{
		mutex3.acquire();
		leyendo.acquire();
		mutex1.acquire();
		nLectores++;
		System.out.println("Entra lector "+id+" Hay "+nLectores+" lectores");
		if(nLectores==1) escribiendo.acquire();
		mutex1.release();
		leyendo.release();
		mutex3.release();
		
	}
	
	
	public void entraEscritor(int id)throws InterruptedException{
		mutex2.acquire();
		nEsc++; //E2
		if(nEsc==1) leyendo.acquire();
		mutex2.release();
		escribiendo.acquire();
		System.out.println("Entra escritor "+id+" Hay "+nEsc+" Escritores");
		
		
		
	}
	
	
	
	public void saleLector(int id)throws InterruptedException{
		mutex1.acquire();
		nLectores--;
		System.out.println("Sale lector "+id+" Hay "+nLectores+" lectores");
		if(nLectores == 0) escribiendo.release();
		mutex1.release();
		
	}
	
	
	
	public void saleEscritor(int id)throws InterruptedException{
		System.out.println("Sale escritor "+id+" Hay"+nLectores+ " lectores");
		escribiendo.release();
		mutex2.acquire();
		nEsc--;
		if(nEsc==0) leyendo.release();
		mutex2.release();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

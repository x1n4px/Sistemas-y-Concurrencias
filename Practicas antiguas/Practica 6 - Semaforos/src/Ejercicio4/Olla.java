package Ejercicio4;

import java.util.concurrent.Semaphore;

public class Olla implements IOlla{

	private int TamRac = 10;
	private int olla = 0;
	private boolean esperandoCan = false;
	private boolean esperandoCoc = false;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore esperaVacia = new Semaphore(0);
	private Semaphore esperaLlena = new Semaphore(0);
	private Semaphore siguienteCanibal = new Semaphore(1);
	
	
	@Override
	public void nuevoExplorador() throws InterruptedException {
		// TODO Auto-generated method stub
		mutex.acquire();
		if(olla!=0) {
			esperandoCoc = true;
			mutex.release();
			esperaVacia.acquire();
			mutex.acquire();
		}
		olla = TamRac;
		System.out.println("Cocinero llena la olla "+olla);
		if(esperandoCan) {
			esperandoCan = false;
			esperaLlena.release();
		}
		mutex.release();
	}
	
	
	@Override
	public void comeRacion(int id) throws InterruptedException {
		// TODO Auto-generated method stub
		siguienteCanibal.acquire();
		mutex.acquire();
		if(olla==0) {
			if(esperandoCoc) {
				esperandoCoc = false;
				esperaVacia.release();
			}
			esperandoCoc = true;
			mutex.release();
			esperaLlena.acquire();
			mutex.release();
		}
		olla--;
		System.out.println("Canibal come racion "+olla);
		mutex.release();
		siguienteCanibal.release();
	}
	
	
	
	
}

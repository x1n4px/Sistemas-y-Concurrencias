package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import mon.AseoMon;
import mon.Cliente;
import mon.Limpieza;

public class AseoLock {

	private int numClientes;
	private boolean tocaLimpiar;
	private ReentrantLock l;
	private Condition cClientes,cLimpieza;
	
	
	public AseoLock() {
		numClientes = 0;
		tocaLimpiar = false;
		l = new ReentrantLock(true);
		cClientes = l.newCondition();
		cLimpieza = l.newCondition();
		
	}
	
	public void entraCLiente(int id)throws InterruptedException{
		l.lock();
		try {
			while(tocaLimpiar) {
				cClientes.await();
			}
			numClientes++;
			System.out.println("Cliente "+id+" entra.Total: "+numClientes);	
		}finally {
			l.unlock();
		}	
	}
	
	
	
	public void saleCLiente(int id)throws InterruptedException{
		l.lock();
		try {
			numClientes--;
			if(numClientes==0) {
				cLimpieza.signal();
			}
			System.out.println("\t\t Cliente "+id+" sale.Total: "+numClientes);			
		}finally {
			l.unlock();
		}
			
	}
	
	public void entraLimpieza()throws InterruptedException{
		l.lock();
		try {
			tocaLimpiar = true;
			System.out.println("TOCA LIMPIAR");
			while(numClientes>0) {
				cLimpieza.await();
			}
			System.out.println("Equipo limpieza dentro");
			
			
		}finally {
			l.unlock();
		}
		
	}
	
	public void saleLimpieza(){
		l.lock();
		try {
			tocaLimpiar = false;
			cClientes.signalAll();
			System.out.println("Equipo limpieza fuera");
			
			
			
		}finally {
			l.unlock();
		}
		
	}
		
	
	
	public static void main(String[] args) {
		AseoMon aseo = new AseoMon();
		Limpieza limpieza = new Limpieza(aseo);
		Cliente clientes[] = new Cliente [10];
		for(int i=0;i<10;i++) {
			clientes[i] = new Cliente(aseo,i);
		}
		limpieza.start();
		for(int i = 0; i< 10; i++) {
			clientes[i].start();
		}
	}
		
		
	
	
	
	
	
	
	
	
	
	
	
}

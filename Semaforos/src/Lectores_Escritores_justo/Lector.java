package Lectores_Escritores_justo;

import java.util.Random;

public class Lector extends Thread{
	private int id;
	private GestorBD gestor;
	private Random r;
	
	public Lector(int id,GestorBD gestor) {
		this.id = id;
		this.gestor = gestor;
		r = new Random();
	}
	
	public void run() {
		int dato;
		while(true) {
			try {
				dato = gestor.leer();
				System.out.println("Lector "+id+" ha leido "+dato);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	
}

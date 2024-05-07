package Lectores_Escritores_justo;
import java.util.Random;

public class Escritor extends Thread {
	private GestorBD bd;
	private int id;
	private Random r;
	
	
	public Escritor(int id, GestorBD bd)
	{
		this.bd = bd;
		this.id = id;
		r= new Random();
	}
	
	public void run()
	{
		int dato;
		try {
			while(true) {		
				dato = r.nextInt(1000);
				System.out.println("Escritor "+id+" va a escribir dato "+dato);
				bd.escribir(dato);
			}
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}

}

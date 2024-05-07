package LectoresSemaforos;
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
				bd.entraEscritor(id);
				Thread.sleep(100 + r.nextInt(100));
				bd.saleEscritor(id);
			}
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}

}

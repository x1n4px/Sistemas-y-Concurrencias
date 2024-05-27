package LectoresMonitores;
public class Lector extends Thread {
	private int id;
	private GestorBD bd;
	
	public Lector(int id, GestorBD bd)
	{
		this.bd = bd;
		this.id = id;
	}
	
	public void run()
	{	
		try {
			while(true)
			{
				bd.entraLeer(id);
				Thread.sleep(100);
				bd.saleLeer(id);
			}
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
}

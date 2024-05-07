package Jardines_Petterson;
public class Puerta extends Thread {
	private int iter;
	private Jardin c;
	private int id;
	public Puerta(Jardin c, int iter, int id)
	{
		this.iter = iter;
		this.c = c;
		this.id = id;
	}
	
	public void run()
	{
		for(int i=0; i< iter; i++)
		{				
			c.inc(id);			
		}
	}
	
	
}

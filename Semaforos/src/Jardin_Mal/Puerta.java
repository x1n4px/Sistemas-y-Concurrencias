package Jardin_Mal;
public class Puerta extends Thread {
	private int iter;
	Jardin c;
	public Puerta(Jardin c, int iter)
	{
		this.iter = iter;
		this.c = c;
	}
	
	public void run()
	{
		for(int i=0; i< iter; i++)
		{
			c.nuevoVisitante();
		}
	}
}

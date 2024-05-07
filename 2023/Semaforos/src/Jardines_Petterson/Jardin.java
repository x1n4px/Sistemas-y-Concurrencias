package Jardines_Petterson;
public class Jardin {
	//Variables para Peterson
	volatile private boolean f0,f1;
	volatile private int turno;
	
	private int visitantes;
	
	public void preProt0() {
		f0 = true;
		turno =1;
		while(f1 && turno==1) Thread.yield();
	}
	public void preProt1() {
		f1 = true;
		turno =0;
		while(f0 && turno==0) Thread.yield();
	}
	public void postProt0()
	{
		f0 = false;
	}
	public void postProt1()
	{
		f1 = false;
	}
	public int getCont() {
		return visitantes;
	}
	public void inc(int id)
	{
		//pre
		if(id==0)
			preProt0();
		else
			preProt1();
		//SC
		visitantes ++;
		System.out.println("Visitante por puerta "+id);
		//post
		if(id==0)
			postProt0();
		else
			postProt1();
		
	}
	
	public static void main(String args[])
	{
		Jardin c = new Jardin();
		Puerta p1 = new Puerta(c, 1000,0);
		Puerta p2 = new Puerta(c, 1000,1);
		
		p1.start();
		p2.start();
		
		try {
			p1.join();
			p2.join();
			System.out.println("Número total de visitantes "+c.getCont());
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
		
	}
}

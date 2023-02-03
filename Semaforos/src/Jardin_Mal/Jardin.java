package Jardin_Mal;
public class Jardin {
	private int visitantes;
	
	public int getVisitantes() {
		return visitantes;
	}
	public void nuevoVisitante() //incrementa en 1 los visitantes
	{
		visitantes ++;
	}
	
	public static void main(String args[])
	{
		Jardin c = new Jardin();
		Puerta p1 = new Puerta(c, 1000);
		Puerta p2 = new Puerta(c, 1000);
		
		p1.start();
		p2.start();
		
		try {
			p1.join();
			p2.join();
			System.out.println("Número total de visitantes "+c.getVisitantes());
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
		
	}
}

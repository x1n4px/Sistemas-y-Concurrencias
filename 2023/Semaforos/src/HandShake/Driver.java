package HandShake;

public class Driver {

	public static void main(String[] args) {
		Recurso r = new Recurso();
		Proceso1 p1 = new Proceso1(r);
		Proceso2 p2 = new Proceso2(r);
		
		p1.start();
		p2.start();
	}
	
	
}
